# PermissionsApi

All URIs are relative to *https://gematik.de/fdv*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addPermissionHcpo1**](PermissionsApi.md#addPermissionHcpo1) | **POST** /permissionHcpo | Berechtigung fuer LEI erteilen
[**addPermissionInsurance1**](PermissionsApi.md#addPermissionInsurance1) | **POST** /permissionInsurance | Berechtigung fuer einen Kostentraeger erteilen
[**addPermissionRepresentative1**](PermissionsApi.md#addPermissionRepresentative1) | **POST** /permissionRepresentative | Berechtigung fuer einen Vertreter erteilen
[**changePermissionHcpo1**](PermissionsApi.md#changePermissionHcpo1) | **PUT** /permissionHcpo | Berechtigung fuer eine LEI aendern
[**deletePermissionHcpo1**](PermissionsApi.md#deletePermissionHcpo1) | **DELETE** /permissionHcpo | Berechtigung fuer eine LEI loeschen
[**deletePermissionInsurance1**](PermissionsApi.md#deletePermissionInsurance1) | **DELETE** /permissionInsurance | Berechtigung fuer einen Kostentraeger loeschen
[**deletePermissionRepresentative1**](PermissionsApi.md#deletePermissionRepresentative1) | **DELETE** /permissionRepresentative | Berechtigung fuer einen Vertreter loeschen
[**getPermissions1**](PermissionsApi.md#getPermissions1) | **POST** /permissions | Alle Berechtigungen lesen


<a name="addPermissionHcpo1"></a>
# **addPermissionHcpo1**
> ResponseDTO addPermissionHcpo1(permissionHcpoDTO)

Berechtigung fuer LEI erteilen

Umsetzung Operation I_FdV::grantPermissionHcp (A_18049)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    PermissionHcpoDTO permissionHcpoDTO = new PermissionHcpoDTO(); // PermissionHcpoDTO | 
    try {
      ResponseDTO result = apiInstance.addPermissionHcpo1(permissionHcpoDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#addPermissionHcpo1");
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
 **permissionHcpoDTO** | [**PermissionHcpoDTO**](PermissionHcpoDTO.md)|  | [optional]

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

<a name="addPermissionInsurance1"></a>
# **addPermissionInsurance1**
> ResponseDTO addPermissionInsurance1(permissionInsuranceDTO)

Berechtigung fuer einen Kostentraeger erteilen

Umsetzung Operation I_FdV::grantPermissionInsurance (A_18052)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    PermissionInsuranceDTO permissionInsuranceDTO = new PermissionInsuranceDTO(); // PermissionInsuranceDTO | 
    try {
      ResponseDTO result = apiInstance.addPermissionInsurance1(permissionInsuranceDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#addPermissionInsurance1");
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
 **permissionInsuranceDTO** | [**PermissionInsuranceDTO**](PermissionInsuranceDTO.md)|  | [optional]

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

<a name="addPermissionRepresentative1"></a>
# **addPermissionRepresentative1**
> ResponseDTO addPermissionRepresentative1(permissionRepresentativeDTO)

Berechtigung fuer einen Vertreter erteilen

Umsetzung Operation I_FdV::grantPermissionRepresentative (A_18050)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    PermissionRepresentativeDTO permissionRepresentativeDTO = new PermissionRepresentativeDTO(); // PermissionRepresentativeDTO | 
    try {
      ResponseDTO result = apiInstance.addPermissionRepresentative1(permissionRepresentativeDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#addPermissionRepresentative1");
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
 **permissionRepresentativeDTO** | [**PermissionRepresentativeDTO**](PermissionRepresentativeDTO.md)|  | [optional]

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

<a name="changePermissionHcpo1"></a>
# **changePermissionHcpo1**
> ResponseDTO changePermissionHcpo1(permissionHcpoDTO)

Berechtigung fuer eine LEI aendern

Umsetzung Operation I_FdV::changePermissionHcp (A_18054)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    PermissionHcpoDTO permissionHcpoDTO = new PermissionHcpoDTO(); // PermissionHcpoDTO | 
    try {
      ResponseDTO result = apiInstance.changePermissionHcpo1(permissionHcpoDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#changePermissionHcpo1");
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
 **permissionHcpoDTO** | [**PermissionHcpoDTO**](PermissionHcpoDTO.md)|  | [optional]

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

<a name="deletePermissionHcpo1"></a>
# **deletePermissionHcpo1**
> deletePermissionHcpo1(body)

Berechtigung fuer eine LEI loeschen

Umsetzung Operation I_FdV::deletePermissionHcp (A_18055)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    DeletePermissionDTO body = new DeletePermissionDTO(); // DeletePermissionDTO | 
    try {
      apiInstance.deletePermissionHcpo1(body);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#deletePermissionHcpo1");
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
 **body** | [**DeletePermissionDTO**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | successful operation |  -  |
**400** | Invalid ID supplied |  -  |
**404** | Entry not found |  -  |

<a name="deletePermissionInsurance1"></a>
# **deletePermissionInsurance1**
> deletePermissionInsurance1(body)

Berechtigung fuer einen Kostentraeger loeschen

Umsetzung Operation I_FdV::deletePermissionInsurance (A_18057)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    DeletePermissionDTO body = new DeletePermissionDTO(); // DeletePermissionDTO | 
    try {
      apiInstance.deletePermissionInsurance1(body);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#deletePermissionInsurance1");
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
 **body** | [**DeletePermissionDTO**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | successful operation |  -  |
**400** | Invalid ID supplied |  -  |
**404** | Entry not found |  -  |

<a name="deletePermissionRepresentative1"></a>
# **deletePermissionRepresentative1**
> deletePermissionRepresentative1(body)

Berechtigung fuer einen Vertreter loeschen

Umsetzung Operation I_FdV::deletePermissionRepresentative (A_18056)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    DeletePermissionDTO body = new DeletePermissionDTO(); // DeletePermissionDTO | 
    try {
      apiInstance.deletePermissionRepresentative1(body);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#deletePermissionRepresentative1");
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
 **body** | [**DeletePermissionDTO**](.md)|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | successful operation |  -  |
**400** | Invalid ID supplied |  -  |
**404** | Entry not found |  -  |

<a name="getPermissions1"></a>
# **getPermissions1**
> PermissionsResponseDTO getPermissions1(requestDTO)

Alle Berechtigungen lesen

Umsetzung Operation I_FdV::getPermissions (A_18053)

### Example
```java
// Import classes:
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiClient;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.ApiException;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.Configuration;
import de.gematik.ti.epa.fdv.testtreiber.client.invoker.models.*;
import de.gematik.ti.epa.fdv.testtreiber.client.api.PermissionsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://gematik.de/fdv");

    PermissionsApi apiInstance = new PermissionsApi(defaultClient);
    RequestDTO requestDTO = new RequestDTO(); // RequestDTO | 
    try {
      PermissionsResponseDTO result = apiInstance.getPermissions1(requestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PermissionsApi#getPermissions1");
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

[**PermissionsResponseDTO**](PermissionsResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**0** | default response |  -  |

