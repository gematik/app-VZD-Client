# AdministrationApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**changeProvider1**](AdministrationApi.md#changeProvider1) | **POST** /changeProvider | Anbieter wechseln
[**putNotificationInformation1**](AdministrationApi.md#putNotificationInformation1) | **POST** /NotificationInformation | Benachrichtigungsadresse fuer Geraeteautorisierung aktualisieren


<a name="changeProvider1"></a>
# **changeProvider1**
> ResponseDTO changeProvider1(changeProviderRequestDTO)

Anbieter wechseln

Umsetzung Operation I_FdV::changeProvicer (A_18047)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.AdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    AdministrationApi apiInstance = new AdministrationApi(defaultClient);
    ChangeProviderRequestDTO changeProviderRequestDTO = new ChangeProviderRequestDTO(); // ChangeProviderRequestDTO | 
    try {
      ResponseDTO result = apiInstance.changeProvider1(changeProviderRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdministrationApi#changeProvider1");
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
 **changeProviderRequestDTO** | [**ChangeProviderRequestDTO**](ChangeProviderRequestDTO.md)|  | [optional]

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

<a name="putNotificationInformation1"></a>
# **putNotificationInformation1**
> ResponseDTO putNotificationInformation1(notificationInformationRequestDTO)

Benachrichtigungsadresse fuer Geraeteautorisierung aktualisieren

Umsetzung Operation I_FdV::putNotificationInformation (A_18063); Hinterlegt eine Benachrichtigungsadresse fuer das Aktenkonto.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.AdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    AdministrationApi apiInstance = new AdministrationApi(defaultClient);
    NotificationInformationRequestDTO notificationInformationRequestDTO = new NotificationInformationRequestDTO(); // NotificationInformationRequestDTO | 
    try {
      ResponseDTO result = apiInstance.putNotificationInformation1(notificationInformationRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AdministrationApi#putNotificationInformation1");
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
 **notificationInformationRequestDTO** | [**NotificationInformationRequestDTO**](NotificationInformationRequestDTO.md)|  | [optional]

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

