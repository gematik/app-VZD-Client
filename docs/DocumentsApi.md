# DocumentsApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteDocuments1**](DocumentsApi.md#deleteDocuments1) | **POST** /deleteDocuments | Dokumente aus dem Aktenkonto loeschen
[**findDocuments1**](DocumentsApi.md#findDocuments1) | **POST** /findDocuments | Dokumente und Submission Sets in einem Aktenkonto finden
[**retrieveDocuments1**](DocumentsApi.md#retrieveDocuments1) | **POST** /retrieveDocuments | Dokumente aus Aktenkonto herunterladen
[**storeDocuments1**](DocumentsApi.md#storeDocuments1) | **POST** /storeDocuments | Dokumente in ein Aktenkonto laden


<a name="deleteDocuments1"></a>
# **deleteDocuments1**
> ResponseDTO deleteDocuments1(documentsRequestDTO)

Dokumente aus dem Aktenkonto loeschen

Umsetzung Operation I_FdV::deleteDocuments (A_18061); Loescht die Dokumente mit dem im Request angegebenen Ids aus dem Aktenkonto. Die Ids werden mittels findDocuments ermittelt.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DocumentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DocumentsApi apiInstance = new DocumentsApi(defaultClient);
    DocumentsRequestDTO documentsRequestDTO = new DocumentsRequestDTO(); // DocumentsRequestDTO | 
    try {
      ResponseDTO result = apiInstance.deleteDocuments1(documentsRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DocumentsApi#deleteDocuments1");
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
 **documentsRequestDTO** | [**DocumentsRequestDTO**](DocumentsRequestDTO.md)|  | [optional]

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

<a name="findDocuments1"></a>
# **findDocuments1**
> FindDocumentsResponseDTO findDocuments1(findDocumentsRequestDTO)

Dokumente und Submission Sets in einem Aktenkonto finden

Umsetzung Operation I_FdV::findDocuments (A_18059); Die fuer die Suchoperation zu verwendende Stored Query wird durch den Parameter vorgegeben. Falls dieser nicht angegeben ist, muss eine geeignete Stored Query gewaehlt werden.; Wenn die Suchparameter ein SubmissionSet adressieren, dann soll der Response die Metadaten der im SubmissionSet enthaltenen Dokumente (unter Beachtung ggf. zusätzlich angegebenener Suchkriterien zu Dokumenten) beinhalten.; Der Response enthält Metadaten zu Dokumenten aber nicht die Dokumente selbst.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DocumentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DocumentsApi apiInstance = new DocumentsApi(defaultClient);
    FindDocumentsRequestDTO findDocumentsRequestDTO = new FindDocumentsRequestDTO(); // FindDocumentsRequestDTO | 
    try {
      FindDocumentsResponseDTO result = apiInstance.findDocuments1(findDocumentsRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DocumentsApi#findDocuments1");
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
 **findDocumentsRequestDTO** | [**FindDocumentsRequestDTO**](FindDocumentsRequestDTO.md)|  | [optional]

### Return type

[**FindDocumentsResponseDTO**](FindDocumentsResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

<a name="retrieveDocuments1"></a>
# **retrieveDocuments1**
> RetrieveDocumentsResponseDTO retrieveDocuments1(documentsRequestDTO)

Dokumente aus Aktenkonto herunterladen

Umsetzung Operation I_FdV::getDocuments (A_18060); Laedt die Dokumente mit den im Request angegebenen Ids aus dem Aktenkonto. Die Ids werden mittels findDocuments ermittelt.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DocumentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DocumentsApi apiInstance = new DocumentsApi(defaultClient);
    DocumentsRequestDTO documentsRequestDTO = new DocumentsRequestDTO(); // DocumentsRequestDTO | 
    try {
      RetrieveDocumentsResponseDTO result = apiInstance.retrieveDocuments1(documentsRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DocumentsApi#retrieveDocuments1");
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
 **documentsRequestDTO** | [**DocumentsRequestDTO**](DocumentsRequestDTO.md)|  | [optional]

### Return type

[**RetrieveDocumentsResponseDTO**](RetrieveDocumentsResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

<a name="storeDocuments1"></a>
# **storeDocuments1**
> ResponseDTO storeDocuments1(storeDocumentRequestDTO)

Dokumente in ein Aktenkonto laden

Umsetzung Operation I_FdV::putDocuments (A_18058)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.DocumentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    DocumentsApi apiInstance = new DocumentsApi(defaultClient);
    StoreDocumentRequestDTO storeDocumentRequestDTO = new StoreDocumentRequestDTO(); // StoreDocumentRequestDTO | 
    try {
      ResponseDTO result = apiInstance.storeDocuments1(storeDocumentRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DocumentsApi#storeDocuments1");
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
 **storeDocumentRequestDTO** | [**StoreDocumentRequestDTO**](StoreDocumentRequestDTO.md)|  | [optional]

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

