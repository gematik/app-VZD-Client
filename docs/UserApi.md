# UserApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**login1**](UserApi.md#login1) | **POST** /login | Login Aktensession
[**logout1**](UserApi.md#logout1) | **POST** /logout | Logout Aktensession


<a name="login1"></a>
# **login1**
> ResponseDTO login1(requestDTO)

Login Aktensession

Umsetzung Operation I_FdV::login A_18045; Login in zwei Varianten; Falls die insurantID uebergeben wird, dann referenziert die insurantID die AUT-Identitaet des Nutzers, welche ueber eine eGK oder einen Signaturdienst (Konfigurationsparameter UseEGK) verfuegbar ist. Falls keine insurantID übergeben wird, dann wird eine PKCS12-Datei uebergeben. Das C.CH.AUT Zertifikat und der private Schluessel aus der PKCS12-Datei werden im Testtreiber genutzt (bspw. Signatur bei der Authentisierung und der Schluesselerzeugung mit SGD).

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    UserApi apiInstance = new UserApi(defaultClient);
    RequestDTO requestDTO = new RequestDTO(); // RequestDTO | 
    try {
      ResponseDTO result = apiInstance.login1(requestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#login1");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **requestDTO** | [**RequestDTO**](RequestDTO.md)|  | [optional]

### Return type

[**ResponseDTO**](ResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

<a name="logout1"></a>
# **logout1**
> ResponseDTO logout1(requestDTO)

Logout Aktensession

Umsetzung Operation I_FdV::logout A_18046; Logout wird fuer eine per InsurantID (KVNR) referenzierte Identitaet ausgeloest.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    UserApi apiInstance = new UserApi(defaultClient);
    RequestDTO requestDTO = new RequestDTO(); // RequestDTO | 
    try {
      ResponseDTO result = apiInstance.logout1(requestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#logout1");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **requestDTO** | [**RequestDTO**](RequestDTO.md)|  | [optional]

### Return type

[**ResponseDTO**](ResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

