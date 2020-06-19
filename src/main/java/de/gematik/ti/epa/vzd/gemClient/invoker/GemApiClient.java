/*
 * Copyright (c) 2020 gematik GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gematik.ti.epa.vzd.gemClient.invoker;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.JSON;
import de.gematik.ti.epa.vzd.client.invoker.auth.Authentication;
import de.gematik.ti.epa.vzd.client.invoker.auth.HttpBasicAuth;
import de.gematik.ti.epa.vzd.client.invoker.auth.OAuth;
import de.gematik.ti.epa.vzd.client.invoker.auth.OAuthFlow;
import de.gematik.ti.epa.vzd.client.invoker.auth.RetryingOAuth;
import de.gematik.ti.epa.vzd.gemClient.exceptions.GemClientException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import okhttp3.OkHttpClient;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GemApiClient extends ApiClient {

  private static final Logger LOG = LoggerFactory.getLogger(GemApiClient.class);

  private String retryingOAuthPath;
  private Map<String, Authentication> authentications;
  private LocalDateTime tokenvalidationDate;

  public GemApiClient() {
    init();
    authentications = Collections.unmodifiableMap(authentications);
  }

  /*
   * Constructor for ApiClient to support access token retry on 401/403 configured with client ID
   */
  public GemApiClient(final String clientId) {
    this(clientId, null, null);
  }

  /*
   * Constructor for ApiClient to support access token retry on 401/403 configured with client ID and additional parameters
   */
  public GemApiClient(final String clientId, final Map<String, String> parameters) {
    this(clientId, null, parameters);
  }

  /*
   * Constructor for ApiClient to support access token retry on 401/403 configured with client ID, secret, and additional parameters
   */
  public GemApiClient(final String clientId, final String clientSecret,
      final Map<String, String> parameters) {
    init();

    final RetryingOAuth retryingOAuth = new RetryingOAuth(retryingOAuthPath, clientId,
        OAuthFlow.application, clientSecret, parameters);
    authentications.put("OAuth2", retryingOAuth);
    getHttpClient().interceptors().add(retryingOAuth);

    // Prevent the authentications from being modified.
    authentications = Collections.unmodifiableMap(authentications);
  }

  /**
   * The function <code> getProgressInterceptor </code> comes from the generated class ApiClient and maybe have to be set on public again.
   * <p>
   * The OAuth2 token is stored in authentications.get("OAuth")
   */
  private void init() {
    ConfigHandler configHandler = ConfigHandler.getInstance();
    setBasePath(configHandler.getBasePath());
    retryingOAuthPath = configHandler.getRetryingOAuthPath();

    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    // Function have to be set public when client is regenerated
    builder.addNetworkInterceptor(getProgressInterceptor());
    setHttpClient(builder.build());

    setVerifyingSsl(true);

    setJSON(new JSON());

    // Set default User-Agent.
    setUserAgent("OpenAPI-Generator/1.0.0/java");

    authentications = new HashMap<>();
    authentications
        .put("HttpBasicAuth", getHttpBasicAuthFromFile(configHandler.getCredentialPath()));
    try {
      authentications.put("OAuth", getNewOAuth2Token());
    } catch (OAuthSystemException | OAuthProblemException e) {
      throw new ExceptionInInitializerError("Error while getting Token");
    }
  }

  /**
   * Requests an OAuth2 Token with Username and Password
   *
   * @return
   * @throws OAuthSystemException
   * @throws OAuthProblemException
   */
  private OAuth getNewOAuth2Token() throws OAuthProblemException, OAuthSystemException {
    LOG.debug("Trying to get new access token");
    HttpBasicAuth baseAuth = (HttpBasicAuth) getAuthentications().get("HttpBasicAuth");

    OAuthClientRequest request = OAuthClientRequest
        .tokenLocation(retryingOAuthPath)
        .setClientId(baseAuth.getUsername())
        .setClientSecret(baseAuth.getPassword())
        .setGrantType(GrantType.CLIENT_CREDENTIALS)
        .buildBodyMessage();

    request.setHeader("Accept", "application/json");

    OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
    OAuthAccessTokenResponse oAuthResponse = oAuthClient
        .accessToken(request, OAuthJSONAccessTokenResponse.class);

    JsonObject jObj = new JsonParser().parse(oAuthResponse.getBody()).getAsJsonObject();
    OAuth oAuth = new OAuth();
    oAuth.setAccessToken(jObj.get("access_token").toString().replaceAll("\"", ""));
    setTokenValidation(jObj.get("expires_in").toString());
    LOG.debug("Requesting new OAuth2 token successful");
    return oAuth;
  }

  /**
   * Sets the time - 10% until the token expires. This ensures that the token is every time valid
   *
   * @param expires_in is an String with numbers. For example 3600 equals 1 hour.
   */
  private void setTokenValidation(String expires_in) {
    int seconds = Integer.parseInt(expires_in);
    int secureSeconds = (int) (seconds * 0.90);
    tokenvalidationDate = LocalDateTime.now().plusSeconds(secureSeconds);
  }

  /**
   * Checks if the token is still valid. If not request a new one
   *
   * @return
   */
  public boolean validateToken() {
    if (LocalDateTime.now().isBefore(tokenvalidationDate)) {
      return true;
    }
    try {
      getNewOAuth2Token();
    } catch (OAuthProblemException | OAuthSystemException e) {
      throw new GemClientException("Requesting a new OAuth2 token failed.", e);
    }
    return LocalDateTime.now().isBefore(tokenvalidationDate);
  }

  /**
   * Reads the credentialFile and stores the client_id and client_secret
   *
   * @param arg
   * @return
   */
  private HttpBasicAuth getHttpBasicAuthFromFile(String arg) {
    String client_id = "";
    String client_secret = "";
    File file = new File(arg);

    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line = br.readLine();
      while (line != null) {
        String[] param = line.split("=");
        switch (param[0]) {
          case "id":
            client_id = param[1];
            break;
          case "secret":
            client_secret = param[1];
            break;
          default:
            break;
        }
        line = br.readLine();
      }
      HttpBasicAuth basicAuth = new HttpBasicAuth();
      basicAuth.setPassword(client_secret);
      basicAuth.setUsername(client_id);
      return basicAuth;
    } catch (IOException e) {
      LOG.error(
          "The named file on path " + file.getAbsolutePath() + " could not be accessed");
      throw new IllegalArgumentException(
          "The named file on path " + file.getAbsolutePath() + " could not be accessed");
    }
  }

  // <editor-fold desc="Getter & Setter">

  /**
   * Get authentications (key: authentication name, value: authentication).
   *
   * @return Map of authentication objects
   */
  @Override
  public Map<String, Authentication> getAuthentications() {
    return authentications;
  }

  /**
   * Get authentication for the given name.
   *
   * @param authName The authentication name
   * @return The authentication, null if not found
   */
  @Override
  public Authentication getAuthentication(final String authName) {
    return authentications.get(authName);
  }

  // </editor-fold>
}
