# ProtocolApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getProtocol1**](ProtocolApi.md#getProtocol1) | **POST** /protocol | Zugriffsprotokoll lesen


<a name="getProtocol1"></a>
# **getProtocol1**
> ProtocolResponseDTO getProtocol1(requestDTO)

Zugriffsprotokoll lesen

Umsetzung Operation I_FdV::getProtocol (A_18062); Liefert alle Eintraege aus dem §291a und Verwaltungsprotokoll fuer das Aktenkonto.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.ProtocolApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    ProtocolApi apiInstance = new ProtocolApi(defaultClient);
    RequestDTO requestDTO = new RequestDTO(); // RequestDTO | 
    try {
      ProtocolResponseDTO result = apiInstance.getProtocol1(requestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ProtocolApi#getProtocol1");
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

[**ProtocolResponseDTO**](ProtocolResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

