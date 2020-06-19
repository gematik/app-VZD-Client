

# BaseDirectoryEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**dn** | [**DistinguishedName**](DistinguishedName.md) |  | 
**givenName** | **String** | HBA: Vorname, obligatorisch, wird aus dem Zertifikat übernommen / SMC-B: nicht verwendet |  [optional] [readonly]
**sn** | **String** | HBA: Name, obligatorisch, wird aus dem Zertifikat übernommen / SMC-B: nicht verwendet |  [optional] [readonly]
**cn** | **String** | HBA: Vorname und Nachname / SMC-B: Bezeichner: Name Wird vom VZD aus dem Zertifikatsattribut commonName übernommen. |  [readonly]
**displayName** | **String** | Anzeigename, kann geändert werden. Dieses Attribut wird genutzt um den Namen der Organisation gegenüber dem Anwender darzustellen (Verwendung als Filter-Attribut um die Suche einzuschränken und bei der Darstellung des Ergebnisses). Der Wert wird von der pflegenden Stelle festgelegt. |  [optional]
**streetAddress** | **String** | Straße und Hausnummer Der Wert wird von der pflegenden Stelle festgelegt |  [optional]
**postalCode** | **String** | Postleitzahl Der Wert wird von der pflegenden Stelle festgelegt |  [optional]
**localityName** | **String** | Ort Der Wert wird von der pflegenden Stelle festgelegt |  [optional]
**stateOrProvienceName** | **String** | Bundesland Der Wert wird von der pflegenden Stelle festgelegt |  [optional]
**title** | **String** | HBA: Titel, optional / SMC-B: nicht verwendet |  [optional]
**organization** | **String** | Organisation Der Wert wird von der pflegenden Stelle festgelegt |  [optional]
**otherName** | **String** | Anderer Name. Wird vom VZD aus dem Zertifikatsattribut otherName übernommen. |  [optional]
**specialization** | **List&lt;String&gt;** | Fachgebiet Der Wert wird von der pflegenden Stelle festgelegt |  [optional]
**domainID** | **List&lt;String&gt;** | Ärzte: Betriebsstättennummer Der Wert wird aus dem Zertifikat übernommen (Attribut organizationName) |  [optional]
**personalEntry** | **Boolean** | Wird vom VZD eingetragen / Wert &#x3D;&#x3D; TRUE, wenn alle Zertifikate den entryType 1 haben (Berufsgruppe), Wert &#x3D;&#x3D; FALSE sonst |  [optional] [readonly]
**dataFromAuthority** | **Boolean** | Wird vom VZD eingetragen / Wert &#x3D;&#x3D; TRUE, wenn der Verzeichnisdienst_Eintrag von dem Kartenherausgeber geschrieben wurde, Wert &#x3D;&#x3D; FALSE sonst |  [optional] [readonly]



