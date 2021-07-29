# DirectoryApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**findHcpos1**](DirectoryApi.md#findHcpos1) | **POST** /findHcpos | Suche nach Leistungserbringerinstitutionen im Verzeichnisdienst
[**findInsurances1**](DirectoryApi.md#findInsurances1) | **POST** /findInsurances | Suche nach Kostentraegern im Verzeichnisdienst


<a name="findHcpos1"></a>
# **findHcpos1**
> FindDirectoryResponseDTO findHcpos1(findDirectoryRequestDTO)

Suche nach Leistungserbringerinstitutionen im Verzeichnisdienst

Umsetzung Operation I_FdV::findHcp (A_18048)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DirectoryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DirectoryApi apiInstance = new DirectoryApi(defaultClient);
    FindDirectoryRequestDTO findDirectoryRequestDTO = new FindDirectoryRequestDTO(); // FindDirectoryRequestDTO | 
    try {
      FindDirectoryResponseDTO result = apiInstance.findHcpos1(findDirectoryRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DirectoryApi#findHcpos1");
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
 **findDirectoryRequestDTO** | [**FindDirectoryRequestDTO**](FindDirectoryRequestDTO.md)|  | [optional]

### Return type

[**FindDirectoryResponseDTO**](FindDirectoryResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

<a name="findInsurances1"></a>
# **findInsurances1**
> FindDirectoryResponseDTO findInsurances1(findDirectoryRequestDTO)

Suche nach Kostentraegern im Verzeichnisdienst

Umsetzung Operation I_FdV::findInsurance (A_18051)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DirectoryApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DirectoryApi apiInstance = new DirectoryApi(defaultClient);
    FindDirectoryRequestDTO findDirectoryRequestDTO = new FindDirectoryRequestDTO(); // FindDirectoryRequestDTO | 
    try {
      FindDirectoryResponseDTO result = apiInstance.findInsurances1(findDirectoryRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DirectoryApi#findInsurances1");
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
 **findDirectoryRequestDTO** | [**FindDirectoryRequestDTO**](FindDirectoryRequestDTO.md)|  | [optional]

### Return type

[**FindDirectoryResponseDTO**](FindDirectoryResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

