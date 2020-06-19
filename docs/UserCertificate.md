

# UserCertificate

Jeder Verzeichniseintrag muss mindestens ein Zertifikat enthalten.
## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**dn** | [**DistinguishedName**](DistinguishedName.md) |  | 
**entryType** | **String** | Eintragstyp Wird vom VZD anhand der in dem Zertifikat enthaltenen OID (Extension Admission, Attribut ProfessionOID) und der Spalte Eintragstyp in Tab_VZD_Mapping_Eintragstyp_und_ProfessionOID automatisch eingetragen. Siehe auch [gemSpecOID]# Tab_PKI_402 und Tab_PKI_403 |  [optional] [readonly]
**telematikID** | **String** | TelematikID Wird beim Anlegen des Eintrags vom VZD aus dem Zertifikat übernommen (Feld registrationNumber der Extension Admission). |  [optional] [readonly]
**professionOID** | **List&lt;String&gt;** |  |  [optional] [readonly]
**usage** | [**List&lt;UsageEnum&gt;**](#List&lt;UsageEnum&gt;) | Nutzungskennzeichnung kann pro Zertifikat mehrfach vergeben werden. Vorgegebener Wertebereich [KOM-LE, ePA]. Obligatorisch für LEI und KTR mit vorgegebenem Wert usage&#x3D;ePA |  [optional]
**userCertificate** | **String** | Zertifikat im DER Format. Base64 kodiert. Die pflegende Stelle erhält das Zertifikat vom TSP oder falls das nicht möglich ist wird ein Ersatzverfahren abgestimmt. |  [optional]
**description** | **String** | Dieses Attribut ermöglicht das Zertifikat zu beschreiben, um die Administration des VZD Eintrags zu vereinfachen. |  [optional]



## Enum: List&lt;UsageEnum&gt;

Name | Value
---- | -----
KOM_LE | &quot;KOM-LE&quot;
EPA | &quot;ePA&quot;



