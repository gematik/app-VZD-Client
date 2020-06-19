# DirectoryEntryAdministrationApi

All URIs are relative to *https://to.be.defined*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addDirectoryEntry**](DirectoryEntryAdministrationApi.md#addDirectoryEntry) | **POST** /DirectoryEntries | Einen Eintrag zum Verzeichnisdienst hinzufügen
[**deleteDirectoryEntry**](DirectoryEntryAdministrationApi.md#deleteDirectoryEntry) | **DELETE** /DirectoryEntries/{uid} | Gesamten Verzeichniseintrag löschen
[**modifyDirectoryEntry**](DirectoryEntryAdministrationApi.md#modifyDirectoryEntry) | **PUT** /DirectoryEntries/{uid}/baseDirectoryEntries | Der Verzeichniseintrag (ohne Zertifikate und Fachdaten) wird mit den übergebenen Daten aktualisiert.
[**readDirectoryEntry**](DirectoryEntryAdministrationApi.md#readDirectoryEntry) | **GET** /DirectoryEntries | Gesamten Verzeichniseintrag lesen


<a name="addDirectoryEntry"></a>
# **addDirectoryEntry**
> DistinguishedName addDirectoryEntry(createDirectoryEntry)

Einen Eintrag zum Verzeichnisdienst hinzufügen

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.DirectoryEntryAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    DirectoryEntryAdministrationApi apiInstance = new DirectoryEntryAdministrationApi(defaultClient);
    CreateDirectoryEntry createDirectoryEntry = new CreateDirectoryEntry(); // CreateDirectoryEntry | Datensatz für den neuen Eintrag. Die Attribute müssen wie folgt belegt sein dn.*          Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) givenName     Nicht vorhanden (wird vom Verzeichnisdienst belegt) sn            Nicht vorhanden (wird vom Verzeichnisdienst belegt) cn            Nicht vorhanden (wird vom Verzeichnisdienst belegt) displayName   Kann optional belegt werden. streetAddress Kann optional belegt werden. postalCode    Kann optional belegt werden. localityName  Kann optional belegt werden. stateOrProvienceName  Kann optional belegt werden. title         Kann optional belegt werden. organization  Kann optional belegt werden. otherName     Kann optional belegt werden. specialization  Kann optional belegt werden. domainID      Kann optional belegt werden. personalEntry Nicht vorhanden (wird vom Verzeichnisdienst belegt) dataFromAuthority Nicht vorhanden (wird vom Verzeichnisdienst belegt) userCertificates  Kann optional belegt werden. dn.uid        Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) dn.dc         Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) dn.ou         Leer/nicht vorhanden (wird vom Verzeichnisdienst  belegt) dn.cn         Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) telematikID   Nicht vorhanden (wird vom Verzeichnisdienst belegt) entryType     Nicht vorhanden (wird vom Verzeichnisdienst belegt) professionOID Nicht vorhanden (wird vom Verzeichnisdienst belegt) usage         Kann optional belegt werden. userCertificate   Muss vorhanden sein (Format DER, Base64 kodiert) description   Kann optional belegt werden. Entsprechend gemSpec_VZD wird ein Teil der Attribute durch den Verzeichnisdienst automatisch mit Werten aus dem Zertifikat gefüllt. Wenn in dieser Operation Attribute - für die dies erlaubt ist - mit einem Wert belegt werden, wird dieser Wert im Verzeichniseintrag gespeichert (auch wenn der Wert durch den Verzeichnisdienst aus dem Zertifikat entnommen werden kann).
    try {
      DistinguishedName result = apiInstance.addDirectoryEntry(createDirectoryEntry);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DirectoryEntryAdministrationApi#addDirectoryEntry");
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
 **createDirectoryEntry** | [**CreateDirectoryEntry**](CreateDirectoryEntry.md)| Datensatz für den neuen Eintrag. Die Attribute müssen wie folgt belegt sein dn.*          Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) givenName     Nicht vorhanden (wird vom Verzeichnisdienst belegt) sn            Nicht vorhanden (wird vom Verzeichnisdienst belegt) cn            Nicht vorhanden (wird vom Verzeichnisdienst belegt) displayName   Kann optional belegt werden. streetAddress Kann optional belegt werden. postalCode    Kann optional belegt werden. localityName  Kann optional belegt werden. stateOrProvienceName  Kann optional belegt werden. title         Kann optional belegt werden. organization  Kann optional belegt werden. otherName     Kann optional belegt werden. specialization  Kann optional belegt werden. domainID      Kann optional belegt werden. personalEntry Nicht vorhanden (wird vom Verzeichnisdienst belegt) dataFromAuthority Nicht vorhanden (wird vom Verzeichnisdienst belegt) userCertificates  Kann optional belegt werden. dn.uid        Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) dn.dc         Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) dn.ou         Leer/nicht vorhanden (wird vom Verzeichnisdienst  belegt) dn.cn         Leer/nicht vorhanden (wird vom Verzeichnisdienst belegt) telematikID   Nicht vorhanden (wird vom Verzeichnisdienst belegt) entryType     Nicht vorhanden (wird vom Verzeichnisdienst belegt) professionOID Nicht vorhanden (wird vom Verzeichnisdienst belegt) usage         Kann optional belegt werden. userCertificate   Muss vorhanden sein (Format DER, Base64 kodiert) description   Kann optional belegt werden. Entsprechend gemSpec_VZD wird ein Teil der Attribute durch den Verzeichnisdienst automatisch mit Werten aus dem Zertifikat gefüllt. Wenn in dieser Operation Attribute - für die dies erlaubt ist - mit einem Wert belegt werden, wird dieser Wert im Verzeichniseintrag gespeichert (auch wenn der Wert durch den Verzeichnisdienst aus dem Zertifikat entnommen werden kann). |

### Return type

[**DistinguishedName**](DistinguishedName.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Created Der Verzeichniseintrag wurde angelegt. Zurückgegeben wird der distinguishedName des erzeugten Eintrags. |  -  |
**401** | Unauthorized Der WWW-Authenticate Header im Response muss auf OAuth gesetzt werden. |  -  |
**403** | Forbidden |  -  |
**405** | Invalid input |  -  |

<a name="deleteDirectoryEntry"></a>
# **deleteDirectoryEntry**
> deleteDirectoryEntry(uid)

Gesamten Verzeichniseintrag löschen

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.DirectoryEntryAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    DirectoryEntryAdministrationApi apiInstance = new DirectoryEntryAdministrationApi(defaultClient);
    String uid = "uid_example"; // String | ID von dem zu löschenden Verzeichniseintrag Gelöscht werden der Basis Verzeichniseintrag sowie alle dazugehörenden Zertifikate und Fachdaten.
    try {
      apiInstance.deleteDirectoryEntry(uid);
    } catch (ApiException e) {
      System.err.println("Exception when calling DirectoryEntryAdministrationApi#deleteDirectoryEntry");
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
 **uid** | **String**| ID von dem zu löschenden Verzeichniseintrag Gelöscht werden der Basis Verzeichniseintrag sowie alle dazugehörenden Zertifikate und Fachdaten. |

### Return type

null (empty response body)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Successful operation |  -  |
**400** | Invalid ID supplied |  -  |
**401** | Unauthorized Der WWW-Authenticate Header im Response muss auf OAuth gesetzt werden. |  -  |
**403** | Forbidden |  -  |
**404** | DirectoryEntry not found |  -  |

<a name="modifyDirectoryEntry"></a>
# **modifyDirectoryEntry**
> DistinguishedName modifyDirectoryEntry(uid, baseDirectoryEntry)

Der Verzeichniseintrag (ohne Zertifikate und Fachdaten) wird mit den übergebenen Daten aktualisiert.

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.DirectoryEntryAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    DirectoryEntryAdministrationApi apiInstance = new DirectoryEntryAdministrationApi(defaultClient);
    String uid = "uid_example"; // String | ID von dem Verzeichniseintrag
    BaseDirectoryEntry baseDirectoryEntry = new BaseDirectoryEntry(); // BaseDirectoryEntry | Datensatz für die Aktualisierung des Eintrags Die Attribute müssen wie folgt belegt sein dn.*          Nicht vorhanden (Adressierung erfolgt über uid in Path). givenName     Nicht vorhanden. sn            Nicht vorhanden. cn            Nicht vorhanden. displayName   Kann optional belegt werden. streetAddress Kann optional belegt werden. postalCode    Kann optional belegt werden. localityName  Kann optional belegt werden. stateOrProvienceName  Kann optional belegt werden. title         Kann optional belegt werden. organization  Kann optional belegt werden. otherName     Nicht vorhanden. specialization  Kann optional belegt werden. domainID      Kann optional belegt werden. personalEntry Nicht vorhanden. dataFromAuthority Nicht vorhanden
    try {
      DistinguishedName result = apiInstance.modifyDirectoryEntry(uid, baseDirectoryEntry);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DirectoryEntryAdministrationApi#modifyDirectoryEntry");
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
 **uid** | **String**| ID von dem Verzeichniseintrag |
 **baseDirectoryEntry** | [**BaseDirectoryEntry**](BaseDirectoryEntry.md)| Datensatz für die Aktualisierung des Eintrags Die Attribute müssen wie folgt belegt sein dn.*          Nicht vorhanden (Adressierung erfolgt über uid in Path). givenName     Nicht vorhanden. sn            Nicht vorhanden. cn            Nicht vorhanden. displayName   Kann optional belegt werden. streetAddress Kann optional belegt werden. postalCode    Kann optional belegt werden. localityName  Kann optional belegt werden. stateOrProvienceName  Kann optional belegt werden. title         Kann optional belegt werden. organization  Kann optional belegt werden. otherName     Nicht vorhanden. specialization  Kann optional belegt werden. domainID      Kann optional belegt werden. personalEntry Nicht vorhanden. dataFromAuthority Nicht vorhanden |

### Return type

[**DistinguishedName**](DistinguishedName.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Der Verzeichniseintrag wurde aktualisiert. |  -  |
**400** | Invalid ID supplied |  -  |
**401** | Unauthorized Der WWW-Authenticate Header im Response muss auf OAuth gesetzt werden. |  -  |
**403** | Forbidden |  -  |
**404** | DirectoryEntry not found |  -  |
**405** | Invalid input |  -  |

<a name="readDirectoryEntry"></a>
# **readDirectoryEntry**
> List&lt;DirectoryEntry&gt; readDirectoryEntry(uid, givenName, sn, cn, displayName, streetAddress, postalCode, localityName, stateOrProvienceName, title, organization, otherName, specialization, domainID, personalEntry, dataFromAuthority)

Gesamten Verzeichniseintrag lesen

Liefert alle zum Filter passenden Verzeichniseinträge. Die angegebenen Parameter werden mit logischen UND verknüpft.

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.DirectoryEntryAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    DirectoryEntryAdministrationApi apiInstance = new DirectoryEntryAdministrationApi(defaultClient);
    String uid = "uid_example"; // String | ID von dem Verzeichniseintrag (distinguishedName.uid)
    String givenName = "givenName_example"; // String | Erlaubt die Suche mit Hilfe des Attributs givenName.
    String sn = "sn_example"; // String | Erlaubt die Suche mit Hilfe des Attributs sn.
    String cn = "cn_example"; // String | Erlaubt die Suche mit Hilfe des Attributs cn.
    String displayName = "displayName_example"; // String | Erlaubt die Suche mit Hilfe des Attributs displayName.
    String streetAddress = "streetAddress_example"; // String | Erlaubt die Suche mit Hilfe des Attributs streetAddress.
    String postalCode = "postalCode_example"; // String | Erlaubt die Suche mit Hilfe des Attributs postalCode.
    String localityName = "localityName_example"; // String | Erlaubt die Suche mit Hilfe des Attributs localityName.
    String stateOrProvienceName = "stateOrProvienceName_example"; // String | Erlaubt die Suche mit Hilfe des Attributs stateOrProvienceName.
    String title = "title_example"; // String | Erlaubt die Suche mit Hilfe des Attributs title.
    String organization = "organization_example"; // String | Erlaubt die Suche mit Hilfe des Attributs organization.
    String otherName = "otherName_example"; // String | Erlaubt die Suche mit Hilfe des Attributs otherName.
    String specialization = "specialization_example"; // String | Erlaubt die Suche mit Hilfe des Attributs specialization. Der Verzeichniseintrag wird selektiert, wenn die angegebene domainID im Attribut domainID (array) des Verzeichniseintrags enthalten ist.
    String domainID = "domainID_example"; // String | Erlaubt die Suche mit Hilfe des Attributs domainID. Der Verzeichniseintrag wird selektiert, wenn die angegebene domainID im Attribut domainID (array) des Verzeichniseintrags enthalten ist.
    String personalEntry = "personalEntry_example"; // String | Erlaubt die Suche mit Hilfe des Attributs personalEntry.
    String dataFromAuthority = "dataFromAuthority_example"; // String | Erlaubt die Suche mit Hilfe des Attributs dataFromAuthority.
    try {
      List<DirectoryEntry> result = apiInstance.readDirectoryEntry(uid, givenName, sn, cn, displayName, streetAddress, postalCode, localityName, stateOrProvienceName, title, organization, otherName, specialization, domainID, personalEntry, dataFromAuthority);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DirectoryEntryAdministrationApi#readDirectoryEntry");
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
 **uid** | **String**| ID von dem Verzeichniseintrag (distinguishedName.uid) | [optional]
 **givenName** | **String**| Erlaubt die Suche mit Hilfe des Attributs givenName. | [optional]
 **sn** | **String**| Erlaubt die Suche mit Hilfe des Attributs sn. | [optional]
 **cn** | **String**| Erlaubt die Suche mit Hilfe des Attributs cn. | [optional]
 **displayName** | **String**| Erlaubt die Suche mit Hilfe des Attributs displayName. | [optional]
 **streetAddress** | **String**| Erlaubt die Suche mit Hilfe des Attributs streetAddress. | [optional]
 **postalCode** | **String**| Erlaubt die Suche mit Hilfe des Attributs postalCode. | [optional]
 **localityName** | **String**| Erlaubt die Suche mit Hilfe des Attributs localityName. | [optional]
 **stateOrProvienceName** | **String**| Erlaubt die Suche mit Hilfe des Attributs stateOrProvienceName. | [optional]
 **title** | **String**| Erlaubt die Suche mit Hilfe des Attributs title. | [optional]
 **organization** | **String**| Erlaubt die Suche mit Hilfe des Attributs organization. | [optional]
 **otherName** | **String**| Erlaubt die Suche mit Hilfe des Attributs otherName. | [optional]
 **specialization** | **String**| Erlaubt die Suche mit Hilfe des Attributs specialization. Der Verzeichniseintrag wird selektiert, wenn die angegebene domainID im Attribut domainID (array) des Verzeichniseintrags enthalten ist. | [optional]
 **domainID** | **String**| Erlaubt die Suche mit Hilfe des Attributs domainID. Der Verzeichniseintrag wird selektiert, wenn die angegebene domainID im Attribut domainID (array) des Verzeichniseintrags enthalten ist. | [optional]
 **personalEntry** | **String**| Erlaubt die Suche mit Hilfe des Attributs personalEntry. | [optional]
 **dataFromAuthority** | **String**| Erlaubt die Suche mit Hilfe des Attributs dataFromAuthority. | [optional]

### Return type

[**List&lt;DirectoryEntry&gt;**](DirectoryEntry.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | successful operation Es werden alle zu dem Filter Parametern passenden Verzeichniseinträge - inklusive Zertifikaten und Fachdaten - zurückgegeben. |  -  |
**400** | Bad Request Wird zurückgegeben wenn mehr als 100 Einträge gefunden wurden. In diesem Fall müssen die Filter Parameter vom Client genauer belegt werden. |  -  |
**401** | Unauthorized Der WWW-Authenticate Header im Response wird auf OAuth gesetzt. |  -  |
**403** | Forbidden |  -  |
**404** | DirectoryEntry not found |  -  |

