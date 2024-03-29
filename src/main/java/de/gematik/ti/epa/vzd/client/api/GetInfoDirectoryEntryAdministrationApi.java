/*
 * I_Directory_Administration
 * REST Schnittstelle zur Pflege der Verzeichniseinträge. Über diese Schnittstelle können Verzeichniseinträge inklusive Zertifikaten erzeugt, aktualisiert und gelöscht werden. Die Administration von Fachdaten erfolgt über Schnittstelle I_Directory_Application_Maintenance und wird durch die Fachanwendungen durchgeführt. Lesender Zugriff auf die Fachdaten ist mit Operation getDirectoryEntries in vorliegender Schnittstelle möglich.
 *
 * The version of the OpenAPI document: 1.6.3
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package de.gematik.ti.epa.vzd.client.api;

import com.google.gson.reflect.TypeToken;
import de.gematik.ti.epa.vzd.client.invoker.ApiCallback;
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.ApiResponse;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.Pair;
import de.gematik.ti.epa.vzd.client.model.InfoObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetInfoDirectoryEntryAdministrationApi {

  private ApiClient localVarApiClient;

  public GetInfoDirectoryEntryAdministrationApi() {
    this(Configuration.getDefaultApiClient());
  }

  public GetInfoDirectoryEntryAdministrationApi(ApiClient apiClient) {
    this.localVarApiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return localVarApiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.localVarApiClient = apiClient;
  }

  /**
   * Build call for getInfo
   *
   * @param _callback Callback for upload/download progress
   * @return Call to execute
   * @throws ApiException If fail to serialize the request body object
   * @http.response.details <table summary="Response Details" border="1">
   * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
   * <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
   * <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
   * <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
   * </table>
   */
  public okhttp3.Call getInfoCall(final ApiCallback _callback) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/";

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

    final String[] localVarContentTypes = {

    };
    final String localVarContentType = localVarApiClient.selectHeaderContentType(
        localVarContentTypes);
    localVarHeaderParams.put("Content-Type", localVarContentType);

    String[] localVarAuthNames = new String[]{"OAuth2"};
    return localVarApiClient
        .buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams,
            localVarPostBody, localVarHeaderParams,
            localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
  }

  @SuppressWarnings("rawtypes")
  private okhttp3.Call getInfoValidateBeforeCall(final ApiCallback _callback) throws ApiException {

    okhttp3.Call localVarCall = getInfoCall(_callback);
    return localVarCall;

  }

  /**
   * Diese Operation liefert Metadaten über diese Schnittstelle Liefert die Metadaten aus dem Info
   * Object diese OpenAPI Spezifikation und ergänzt sie.
   *
   * @return InfoObject
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
   *                      response body
   * @http.response.details <table summary="Response Details" border="1">
   * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
   * <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
   * <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
   * <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
   * </table>
   */
  public InfoObject getInfo() throws ApiException {
    ApiResponse<InfoObject> localVarResp = getInfoWithHttpInfo();
    return localVarResp.getData();
  }

  /**
   * Diese Operation liefert Metadaten über diese Schnittstelle Liefert die Metadaten aus dem Info
   * Object diese OpenAPI Spezifikation und ergänzt sie.
   *
   * @return ApiResponse&lt;InfoObject&gt;
   * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the
   *                      response body
   * @http.response.details <table summary="Response Details" border="1">
   * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
   * <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
   * <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
   * <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
   * </table>
   */
  public ApiResponse<InfoObject> getInfoWithHttpInfo() throws ApiException {
    okhttp3.Call localVarCall = getInfoValidateBeforeCall(null);
    Type localVarReturnType = new TypeToken<InfoObject>() {
    }.getType();
    return localVarApiClient.execute(localVarCall, localVarReturnType);
  }

  /**
   * Diese Operation liefert Metadaten über diese Schnittstelle (asynchronously) Liefert die
   * Metadaten aus dem Info Object diese OpenAPI Spezifikation und ergänzt sie.
   *
   * @param _callback The callback to be executed when the API call finishes
   * @return The request call
   * @throws ApiException If fail to process the API call, e.g. serializing the request body object
   * @http.response.details <table summary="Response Details" border="1">
   * <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
   * <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
   * <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
   * <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
   * </table>
   */
  public okhttp3.Call getInfoAsync(final ApiCallback<InfoObject> _callback) throws ApiException {

    okhttp3.Call localVarCall = getInfoValidateBeforeCall(_callback);
    Type localVarReturnType = new TypeToken<InfoObject>() {
    }.getType();
    localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
    return localVarCall;
  }
}
