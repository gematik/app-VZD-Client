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

package de.gematik.ti.epa.vzd.gemClient.api;

import de.gematik.ti.epa.vzd.client.api.DirectoryEntryAdministrationApi;
import de.gematik.ti.epa.vzd.client.invoker.ApiCallback;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Pair;
import de.gematik.ti.epa.vzd.client.invoker.auth.OAuth;
import de.gematik.ti.epa.vzd.client.model.BaseDirectoryEntry;
import de.gematik.ti.epa.vzd.client.model.CreateDirectoryEntry;
import de.gematik.ti.epa.vzd.gemClient.invoker.GemApiClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;

/**
 * Overrides all functions of DirectoryEntryAdministration api that build calls for the different commands to add the OAuth2 Token to the header
 */
public class GemDirectoryEntryAdministrationApi extends DirectoryEntryAdministrationApi {

  private GemApiClient localVarApiClient;

  public GemDirectoryEntryAdministrationApi(GemApiClient apiClient) {
    this.localVarApiClient = apiClient;
  }

  @Override
  public Call addDirectoryEntryCall(CreateDirectoryEntry createDirectoryEntry,
      ApiCallback _callback) throws ApiException {
    Object localVarPostBody = createDirectoryEntry;

    // create path and map variables
    String localVarPath = "/DirectoryEntries";

    List<Pair> localVarQueryParams = new ArrayList<>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<>();
    Map<String, String> localVarHeaderParams = new HashMap<>();
    Map<String, String> localVarCookieParams = new HashMap<>();
    Map<String, Object> localVarFormParams = new HashMap<>();
    final String[] localVarAccepts = {
        "application/json"
    };
    final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null) {
      localVarHeaderParams.put("Accept", localVarAccept);
    }

    final String[] localVarContentTypes = {
        "application/json"
    };
    final String localVarContentType = localVarApiClient
        .selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);
    // add OAuth2 token for authorization
    final OAuth oAuth2Token = (OAuth) localVarApiClient.getAuthentication("OAuth");
    localVarHeaderParams.put("Authorization", "Bearer " + oAuth2Token.getAccessToken());

    String[] localVarAuthNames = new String[]{"OAuth2"};
    return localVarApiClient
        .buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams,
            localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams,
            localVarAuthNames, _callback);
  }

  @Override
  public Call deleteDirectoryEntryCall(String uid, ApiCallback _callback)
      throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/DirectoryEntries/{uid}"
        .replaceAll("\\{" + "uid" + "\\}", localVarApiClient.escapeString(uid.toString()));

    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    final String[] localVarAccepts = {
        "application/json;charset=UTF-8"
    };
    final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null) {
      localVarHeaderParams.put("Accept", localVarAccept);
    }
    // add OAuth2 token for authorization
    final OAuth oAuth2Token = (OAuth) localVarApiClient.getAuthentication("OAuth");
    localVarHeaderParams.put("Authorization", "Bearer " + oAuth2Token.getAccessToken());

    final String[] localVarContentTypes = {

    };
    final String localVarContentType = localVarApiClient
        .selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[]{"OAuth2"};
    return localVarApiClient
        .buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams,
            localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams,
            localVarAuthNames, _callback);
  }

  @Override
  public Call modifyDirectoryEntryCall(String uid, BaseDirectoryEntry baseDirectoryEntry,
      ApiCallback _callback) throws ApiException {
    Object localVarPostBody = baseDirectoryEntry;

    // create path and map variables
    String localVarPath = "/DirectoryEntries/{uid}/baseDirectoryEntries"
        .replaceAll("\\{" + "uid" + "\\}", localVarApiClient.escapeString(uid.toString()));

    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    final String[] localVarAccepts = {
        "application/json"
    };
    final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null) {
      localVarHeaderParams.put("Accept", localVarAccept);
    }
    // add OAuth2 token for authorization
    final OAuth oAuth2Token = (OAuth) localVarApiClient.getAuthentication("OAuth");
    localVarHeaderParams.put("Authorization", "Bearer " + oAuth2Token.getAccessToken());

    final String[] localVarContentTypes = {
        "application/json"
    };
    final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[]{"OAuth2"};
    return localVarApiClient
        .buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
  }

  @Override
  public Call readDirectoryEntryCall(String uid, String givenName, String sn, String cn,
      String displayName, String streetAddress, String postalCode, String localityName,
      String stateOrProvienceName, String title, String organization, String otherName,
      String specialization, String domainID, String personalEntry, String dataFromAuthority,
      ApiCallback _callback) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/DirectoryEntries";

    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    if (uid != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("uid", uid));
    }

    if (givenName != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("givenName", givenName));
    }

    if (sn != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("sn", sn));
    }

    if (cn != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("cn", cn));
    }

    if (displayName != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("displayName", displayName));
    }

    if (streetAddress != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("streetAddress", streetAddress));
    }

    if (postalCode != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("postalCode", postalCode));
    }

    if (localityName != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("localityName", localityName));
    }

    if (stateOrProvienceName != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("stateOrProvienceName", stateOrProvienceName));
    }

    if (title != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("title", title));
    }

    if (organization != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("organization", organization));
    }

    if (otherName != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("otherName", otherName));
    }

    if (specialization != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("specialization", specialization));
    }

    if (domainID != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("domainID", domainID));
    }

    if (personalEntry != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("personalEntry", personalEntry));
    }

    if (dataFromAuthority != null) {
      localVarQueryParams.addAll(localVarApiClient.parameterToPair("dataFromAuthority", dataFromAuthority));
    }

    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    final String[] localVarAccepts = {
        "application/json"
    };
    final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
    if (localVarAccept != null) {
      localVarHeaderParams.put("Accept", localVarAccept);
    }
    // add OAuth2 token for authorization
    final OAuth oAuth2Token = (OAuth) localVarApiClient.getAuthentication("OAuth");
    localVarHeaderParams.put("Authorization", "Bearer " + oAuth2Token.getAccessToken());

    final String[] localVarContentTypes = {

    };
    final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[]{"OAuth2"};
    return localVarApiClient
        .buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams,
            localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
  }
}
