

# DocumentWithMetadata

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**author** | [**List&lt;Author&gt;**](Author.md) |  |  [optional]
**classCode** | **String** | Grobe Klassifizierung des Dokuments, einem Code des in [IHE-ITI-VS] definierten Value Sets fuer DocumentEntry.classCode |  [optional]
**comments** | **String** | Ergaenzende Hinweise in Freitext |  [optional]
**confidentialityCode** | **List&lt;String&gt;** |  |  [optional]
**creationTime** | [**OffsetDateTime**](OffsetDateTime.md) | Erstellungszeitpunkt des Dokuments; date-time notation as defined by RFC 3339, section 5.6, for example, 2017-07-21T17:32:28Z |  [optional]
**eventCodeList** | **List&lt;String&gt;** | Ereignisse, die zur Erstellung des Dokuments geführt haben.; ein Code des in [IHE-ITI-VS] definierten Value Sets fuer DocumentEntry.eventCodeList oder aus der Tabelle in A_17540 |  [optional]
**formatCode** | **List&lt;String&gt;** |  |  [optional]
**hash** | **String** |  |  [optional]
**healthcareFacilityTypeCode** | **String** | Art der Einrichtung, in der das dokumentierte Ereignis stattgefunden hat.; ein Code des in [IHE-ITI-VS] definierten Value Sets fuer DocumentEntry.healthcareFacilityTypeCode |  [optional]
**languageCode** | **String** | Sprache, in der das Dokument abgefasst ist.; ein Code des in [IHE-ITI-VS] definierten Value Sets fuer DocumentEntry.languageCode |  [optional]
**legalAuthenticator** | **String** | Rechtlich Verantwortlicher fuer das Dokument |  [optional]
**mimeType** | [**MimeTypeEnum**](#MimeTypeEnum) | MIME-Type des Dokuments |  [optional]
**practiceSettingCode** | **String** | Art der Fachrichtung der erstellenden Einrichtung, in der das dokumentiere Ereignis stattgefunden hat.; ein Code des in [IHE-ITI-VS] definierten Value Sets fuer DocumentEntry.practiceSettingCode oder aus der Tabelle in A_16944 |  [optional]
**referenceIdList** | **List&lt;String&gt;** | Liste von IDs, mit denen das Dokument assoziiert wird |  [optional]
**serviceStartTime** | [**OffsetDateTime**](OffsetDateTime.md) | Zeitpunkt, an dem das im Dokument dokumentierte (Behandlungs-)Ereignis begonnen wurde.; date-time notation as defined by RFC 3339, section 5.6, for example, 2017-07-21T17:32:28Z |  [optional]
**serviceStopTime** | [**OffsetDateTime**](OffsetDateTime.md) | Zeitpunkt, an dem das im Dokument dokumentierte (Behandlungs-)Ereignis beendet wurde.; date-time notation as defined by RFC 3339, section 5.6, for example, 2017-07-21T17:32:28Z |  [optional]
**size** | **Integer** | Groesse des Dokuments in Bytes |  [optional]
**title** | **String** | Titel des Dokuments |  [optional]
**typeCode** | **String** | Art des Dokumentes; ein Code des in [IHE-ITI-VS] definierten Value Sets fuer DocumentEntry.typeCode |  [optional]
**uniqueId** | **String** | Eindeutige, aktenweite Kennung des Dokuments |  [optional]
**uri** | **String** | Dateiname |  [optional]
**doc** | **byte[]** | Dokument (Base64 kodiert) |  [optional]



## Enum: MimeTypeEnum

Name | Value
---- | -----
APPLICATION_PDF | &quot;application/pdf&quot;
IMAGE_JPEG | &quot;image/jpeg&quot;
IMAGE_TIFF | &quot;image/tiff&quot;
TEXT_PLAIN | &quot;text/plain&quot;
TEXT_RTF | &quot;text/rtf&quot;
APPLICATION_MSWORD | &quot;application/msword&quot;
APPLICATION_MSEXCEL | &quot;application/msexcel&quot;
APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT | &quot;application/vnd.oasis.opendocument.text&quot;
APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET | &quot;application/vnd.oasis.opendocument.spreadsheet&quot;
APPLICATION_XML | &quot;application/xml&quot;
APPLICATION_HL7_V3 | &quot;application/hl7-v3&quot;



