# DefaultApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getProductInformation1**](DefaultApi.md#getProductInformation1) | **POST** /productinformation | Gibt die Informationen zur Produktinformation zurueck
[**ping1**](DefaultApi.md#ping1) | **POST** /ping | Prueft die Erreichbarkeit der Schnittstelle auf Anwendungsebene


<a name="getProductInformation1"></a>
# **getProductInformation1**
> ResponseProductInformationDTO getProductInformation1()

Gibt die Informationen zur Produktinformation zurueck

Umsetzung Operation I_FdV_Management::getProductInformation (A_18068)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      ResponseProductInformationDTO result = apiInstance.getProductInformation1();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getProductInformation1");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ResponseProductInformationDTO**](ResponseProductInformationDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

<a name="ping1"></a>
# **ping1**
> ResponsePingDTO ping1()

Prueft die Erreichbarkeit der Schnittstelle auf Anwendungsebene

Ping prueft die Erreichbarkeit der Schnittstelle auf Anwendungsebene. In der Response wird die Schnittstellenversion zurueckgegeben, was der Pruefung der Interoberabilitaet dient.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      ResponsePingDTO result = apiInstance.ping1();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#ping1");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**ResponsePingDTO**](ResponsePingDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

