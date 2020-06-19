# CertificateAdministrationApi

All URIs are relative to *https://to.be.defined*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addDirectoryEntryCertificate**](CertificateAdministrationApi.md#addDirectoryEntryCertificate) | **POST** /DirectoryEntries/{uid}/Certificates | Der Zertifikatseintrag wird im Verzeichnisdienst hinzugefügt und ist logisch über dn.uid mit dem übergeordneten Verzeichniseintrag verknüpft.
[**deleteDirectoryEntryCertificate**](CertificateAdministrationApi.md#deleteDirectoryEntryCertificate) | **DELETE** /DirectoryEntries/{uid}/Certificates/{certificateEntryID} | Zertifikatseintrag löschen Dem Verzeichniseintrag muss immer mindestens ein Zertifikat zugeordnet sein. Wenn nach dem Löschen kein Zertifikat mehr dem Verzeichniseintrag zugeordnet wäre, muss die delete Operation abgelehnt werden.
[**modifyDirectoryEntryCertificate**](CertificateAdministrationApi.md#modifyDirectoryEntryCertificate) | **PUT** /DirectoryEntries/{uid}/Certificates/{certificateEntryID} | Der Zertifikatseintrag wird mit den übergebenen Daten aktualisiert.
[**readDirectoryCertificates**](CertificateAdministrationApi.md#readDirectoryCertificates) | **GET** /DirectoryEntries/Certificates | Zertifikat lesen


<a name="addDirectoryEntryCertificate"></a>
# **addDirectoryEntryCertificate**
> DistinguishedName addDirectoryEntryCertificate(uid, userCertificate)

Der Zertifikatseintrag wird im Verzeichnisdienst hinzugefügt und ist logisch über dn.uid mit dem übergeordneten Verzeichniseintrag verknüpft.

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.CertificateAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    CertificateAdministrationApi apiInstance = new CertificateAdministrationApi(defaultClient);
    String uid = "uid_example"; // String | ID (dn.uid) vom übergeordneten Verzeichniseintrag
    UserCertificate userCertificate = new UserCertificate(); // UserCertificate | Datensatz für die Erzeugung des Eintrags Die Attribute müssen wie folgt belegt sein Attribut          Wert ------------------------------------------- dn.*              Nicht vorhanden (Adressierung erfolgt über uid in Path) telematikID       Nicht vorhanden (wird vom Verzeichnisdienst belegt) entryType         Nicht vorhanden (wird vom Verzeichnisdienst belegt) professionOID     Nicht vorhanden (wird vom Verzeichnisdienst belegt) usage             Kann optional belegt werden userCertificate   Muss vorhanden sein description       Kann optional belegt werden
    try {
      DistinguishedName result = apiInstance.addDirectoryEntryCertificate(uid, userCertificate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CertificateAdministrationApi#addDirectoryEntryCertificate");
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
 **uid** | **String**| ID (dn.uid) vom übergeordneten Verzeichniseintrag |
 **userCertificate** | [**UserCertificate**](UserCertificate.md)| Datensatz für die Erzeugung des Eintrags Die Attribute müssen wie folgt belegt sein Attribut          Wert ------------------------------------------- dn.*              Nicht vorhanden (Adressierung erfolgt über uid in Path) telematikID       Nicht vorhanden (wird vom Verzeichnisdienst belegt) entryType         Nicht vorhanden (wird vom Verzeichnisdienst belegt) professionOID     Nicht vorhanden (wird vom Verzeichnisdienst belegt) usage             Kann optional belegt werden userCertificate   Muss vorhanden sein description       Kann optional belegt werden |

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
**201** | Created Der Zertifikatseintrag wurde hinzugefügt. Zurückgegeben wird der distinguishedName des erzeugten Eintrags. |  -  |
**401** | Unauthorized Der WWW-Authenticate Header im Response muss auf OAuth gesetzt werden. |  -  |
**403** | Forbidden |  -  |
**405** | Invalid input |  -  |

<a name="deleteDirectoryEntryCertificate"></a>
# **deleteDirectoryEntryCertificate**
> deleteDirectoryEntryCertificate(uid, certificateEntryID)

Zertifikatseintrag löschen Dem Verzeichniseintrag muss immer mindestens ein Zertifikat zugeordnet sein. Wenn nach dem Löschen kein Zertifikat mehr dem Verzeichniseintrag zugeordnet wäre, muss die delete Operation abgelehnt werden.

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.CertificateAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    CertificateAdministrationApi apiInstance = new CertificateAdministrationApi(defaultClient);
    String uid = "uid_example"; // String | ID vom übergeordneten Verzeichniseintrag
    String certificateEntryID = "certificateEntryID_example"; // String | ID von dem zu löschenden Zertifikatseintrag
    try {
      apiInstance.deleteDirectoryEntryCertificate(uid, certificateEntryID);
    } catch (ApiException e) {
      System.err.println("Exception when calling CertificateAdministrationApi#deleteDirectoryEntryCertificate");
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
 **uid** | **String**| ID vom übergeordneten Verzeichniseintrag |
 **certificateEntryID** | **String**| ID von dem zu löschenden Zertifikatseintrag |

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
**404** | Certificate not found |  -  |

<a name="modifyDirectoryEntryCertificate"></a>
# **modifyDirectoryEntryCertificate**
> UserCertificate modifyDirectoryEntryCertificate(uid, certificateEntryID, userCertificate)

Der Zertifikatseintrag wird mit den übergebenen Daten aktualisiert.

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.CertificateAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    CertificateAdministrationApi apiInstance = new CertificateAdministrationApi(defaultClient);
    String uid = "uid_example"; // String | ID vom übergeordneten Verzeichniseintrag
    String certificateEntryID = "certificateEntryID_example"; // String | ID von dem Zertifikat
    UserCertificate userCertificate = new UserCertificate(); // UserCertificate | Datensatz für die Aktualisierung des Eintrags Die Attribute müssen wie folgt belegt sein Attribut          Wert ------------------------------------------- dn.*              Nicht vorhanden (Adressierung erfolgt über uid & certificateEntryID in Path). telematikID       Nicht vorhanden (wird vom Verzeichnisdienst belegt) entryType         Nicht vorhanden (wird vom Verzeichnisdienst belegt) professionOID     Nicht vorhanden (wird vom Verzeichnisdienst belegt) usage             Kann optional belegt werden. userCertificate   unverändert gegenüber VZD description       Kann optional belegt werden.
    try {
      UserCertificate result = apiInstance.modifyDirectoryEntryCertificate(uid, certificateEntryID, userCertificate);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CertificateAdministrationApi#modifyDirectoryEntryCertificate");
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
 **uid** | **String**| ID vom übergeordneten Verzeichniseintrag |
 **certificateEntryID** | **String**| ID von dem Zertifikat |
 **userCertificate** | [**UserCertificate**](UserCertificate.md)| Datensatz für die Aktualisierung des Eintrags Die Attribute müssen wie folgt belegt sein Attribut          Wert ------------------------------------------- dn.*              Nicht vorhanden (Adressierung erfolgt über uid &amp; certificateEntryID in Path). telematikID       Nicht vorhanden (wird vom Verzeichnisdienst belegt) entryType         Nicht vorhanden (wird vom Verzeichnisdienst belegt) professionOID     Nicht vorhanden (wird vom Verzeichnisdienst belegt) usage             Kann optional belegt werden. userCertificate   unverändert gegenüber VZD description       Kann optional belegt werden. |

### Return type

[**UserCertificate**](UserCertificate.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Der aktualisierte Zertifikatseintrag wird zurückgegeben. |  -  |
**400** | Invalid ID supplied or userCertificate changed |  -  |
**401** | Unauthorized Der WWW-Authenticate Header im Response muss auf OAuth gesetzt werden. |  -  |
**403** | Forbidden |  -  |
**404** | Certificate not found |  -  |
**405** | Invalid input |  -  |

<a name="readDirectoryCertificates"></a>
# **readDirectoryCertificates**
> List&lt;UserCertificate&gt; readDirectoryCertificates(uid, certificateEntryID, entryType, telematikID, professionOID, usage)

Zertifikat lesen

Liefert alle Zertifikate, die dem Filter (siehe &#39;parameters&#39;) entsprechen.

### Example
```java
// Import classes:
import de.gematik.ti.epa.vzd.client.invoker.ApiClient;
import de.gematik.ti.epa.vzd.client.invoker.ApiException;
import de.gematik.ti.epa.vzd.client.invoker.Configuration;
import de.gematik.ti.epa.vzd.client.invoker.auth.*;
import de.gematik.ti.epa.vzd.client.invoker.models.*;
import de.gematik.ti.epa.vzd.client.api.CertificateAdministrationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://to.be.defined");
    
    // Configure OAuth2 access token for authorization: OAuth2
    OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
    OAuth2.setAccessToken("YOUR ACCESS TOKEN");

    CertificateAdministrationApi apiInstance = new CertificateAdministrationApi(defaultClient);
    String uid = "uid_example"; // String | ID vom übergeordneten Verzeichniseintrag
    String certificateEntryID = "certificateEntryID_example"; // String | ID von dem Zertifikat (dn.cn vom Zertifikatseintrag) Wenn angegeben wird das adressierte (certificateEntryID) Zertifikat geliefert. Wenn nicht angegeben werden alle Zertifikate des übergeordneten Verzeichniseintrags geliefert.
    String entryType = "entryType_example"; // String | Erlaubt die Suche mit Hilfe des Attributs entryType.
    String telematikID = "telematikID_example"; // String | telematikID von dem Zertifikat Erlaubt die Suche nach Zertifikatseinträgen einer telematikID.
    String professionOID = "professionOID_example"; // String | Erlaubt die Suche mit Hilfe des Attributs professionOID. Der Verzeichniseintrag wird selektiert, wenn die angegebene professionOID im Attribut professionOID (array) des Zertifikatseintrags enthalten ist.
    String usage = "usage_example"; // String | Erlaubt die Suche mit Hilfe des Attributs usage. Der Verzeichniseintrag wird selektiert, wenn die angegebene usage im Attribut usage (array) des Zertifikatseintrags enthalten ist.
    try {
      List<UserCertificate> result = apiInstance.readDirectoryCertificates(uid, certificateEntryID, entryType, telematikID, professionOID, usage);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CertificateAdministrationApi#readDirectoryCertificates");
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
 **uid** | **String**| ID vom übergeordneten Verzeichniseintrag |
 **certificateEntryID** | **String**| ID von dem Zertifikat (dn.cn vom Zertifikatseintrag) Wenn angegeben wird das adressierte (certificateEntryID) Zertifikat geliefert. Wenn nicht angegeben werden alle Zertifikate des übergeordneten Verzeichniseintrags geliefert. | [optional]
 **entryType** | **String**| Erlaubt die Suche mit Hilfe des Attributs entryType. | [optional]
 **telematikID** | **String**| telematikID von dem Zertifikat Erlaubt die Suche nach Zertifikatseinträgen einer telematikID. | [optional]
 **professionOID** | **String**| Erlaubt die Suche mit Hilfe des Attributs professionOID. Der Verzeichniseintrag wird selektiert, wenn die angegebene professionOID im Attribut professionOID (array) des Zertifikatseintrags enthalten ist. | [optional]
 **usage** | **String**| Erlaubt die Suche mit Hilfe des Attributs usage. Der Verzeichniseintrag wird selektiert, wenn die angegebene usage im Attribut usage (array) des Zertifikatseintrags enthalten ist. | [optional]

### Return type

[**List&lt;UserCertificate&gt;**](UserCertificate.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | successful operation Es werden alle gefundenen Zertifikatseinträge zurückgegeben. |  -  |
**400** | Bad Request Wird zurückgegeben wenn mehr als 100 Einträge gefunden wurden. In diesem Fall müssen die Filter Parameter vom Client genauer belegt werden. |  -  |
**401** | Unauthorized Der WWW-Authenticate Header im Response muss auf OAuth gesetzt werden. |  -  |
**403** | Forbidden |  -  |
**404** | Certificate not found |  -  |

