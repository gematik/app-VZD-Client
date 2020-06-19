

# ConfigurationEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**configurationEntryId** | [**ConfigurationEntryIdEnum**](#ConfigurationEntryIdEnum) | Schluesselwerte fuer die in A_15292 definierten Parameter, die durch den Nutzer fuer die Konfiguration des FdV eingegeben werden.; OwnerInsurantId - Versicherten-ID des Aktenkontoinhabers, Teil der Akten-ID; OwnerFqdnProvider - FQDN Anbieter ePA-Aktensystem des Aktenkontoinhabers; OwnerDeviceName - Gerätename des GdV; In der Testtreiber-Konfiguration können 2 Vertretungen eingerichtet werden.; Representation1Name - Name des zu Vertretenden; Representation1InsurantId - Versicherten-ID des zu Vertretenden, Teil der Akten-ID; Representation1FqdnProvider - FQDN Anbieter ePA-Aktensystem des zu Vertretenden; Notifcation - Benachrichtigungen aktivieren; NotificationPeriod - Benachrichtigungszeitraum; ShowPermissionOnAddDocuments - Dokumente einstellen Berechtigte anzeigen; UseEGK (boolean) - gibt an, ob fuer die Authentisierung die eGK oder die alternative kryptographische Versichertenidentitaet genutzt wird. | 
**configurationEntryValue** | **String** | Wert fuer den Konfigurationsparameter | 



## Enum: ConfigurationEntryIdEnum

Name | Value
---- | -----
OWNERINSURANTID | &quot;OwnerInsurantId&quot;
OWNERFQDNPROVIDER | &quot;OwnerFqdnProvider&quot;
OWNERDEVICENAME | &quot;OwnerDeviceName&quot;
REPRESENTATION1NAME | &quot;Representation1Name&quot;
REPRESENTATION1INSURANTID | &quot;Representation1InsurantId&quot;
REPRESENTATION1FQDNPROVIDER | &quot;Representation1FqdnProvider&quot;
REPRESENTATION2NAME | &quot;Representation2Name&quot;
REPRESENTATION2INSURANTID | &quot;Representation2InsurantId&quot;
REPRESENTATION2FQDNPROVIDER | &quot;Representation2FqdnProvider&quot;
NOTIFCATION | &quot;Notifcation&quot;
NOTIFICATIONPERIOD | &quot;NotificationPeriod&quot;
SHOWPERMISSIONONADDDOCUMENTS | &quot;ShowPermissionOnAddDocuments&quot;
USEEGK | &quot;UseEGK&quot;



