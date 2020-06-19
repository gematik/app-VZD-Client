# ConfigurationApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getConfigurationEntries1**](ConfigurationApi.md#getConfigurationEntries1) | **GET** /configuration | Gesamte Konfiguration lesen
[**updateConfigurationEntries1**](ConfigurationApi.md#updateConfigurationEntries1) | **PUT** /configuration | Konfigurationseintrag aendern


<a name="getConfigurationEntries1"></a>
# **getConfigurationEntries1**
> List&lt;ConfigurationEntry&gt; getConfigurationEntries1(uid)

Gesamte Konfiguration lesen

Umsetzung Operation I_FdV_Management::getConfiguration (A_18067); Liefert alle Konfigurationseintraege, die dem Filter entsprechen. Als Filter ist configurationEntryId moeglich.   Wird kein Filter angegeben, dann werden alle Eintraege aus der Konfiguration zurueckgegeben.

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.ConfigurationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    ConfigurationApi apiInstance = new ConfigurationApi(defaultClient);
    String uid = "uid_example"; // String | Bezeichner eines Konfigurationseintrages (configurationEntryId)
    try {
      List<ConfigurationEntry> result = apiInstance.getConfigurationEntries1(uid);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ConfigurationApi#getConfigurationEntries1");
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
 **uid** | **String**| Bezeichner eines Konfigurationseintrages (configurationEntryId) | [optional]

### Return type

[**List&lt;ConfigurationEntry&gt;**](ConfigurationEntry.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | successful operation; Es werden alle Konfigurationseintraege zurueckgegeben.; Wenn nach einer uid gesucht wurde, wird genau dieser Eintrag zurueckgegeben.; Falls der Konfigurationseintrag vorab nicht gesetzt wurde, wird ein Leerstring zurückgegeben. |  -  |
**400** | Invalid ID supplied |  -  |
**404** | Entry not found |  -  |

<a name="updateConfigurationEntries1"></a>
# **updateConfigurationEntries1**
> ResponseDTO updateConfigurationEntries1(configurationEntry)

Konfigurationseintrag aendern

Umsetzung Operation I_FdV_Management::setConfiguration (A_18066); Setzt einen Konfigurationseintrag

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.ConfigurationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    ConfigurationApi apiInstance = new ConfigurationApi(defaultClient);
    ConfigurationEntry configurationEntry = new ConfigurationEntry(); // ConfigurationEntry | 
    try {
      ResponseDTO result = apiInstance.updateConfigurationEntries1(configurationEntry);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ConfigurationApi#updateConfigurationEntries1");
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
 **configurationEntry** | [**ConfigurationEntry**](ConfigurationEntry.md)|  | [optional]

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

