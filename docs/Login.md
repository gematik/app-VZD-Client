

# Login

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**account** | **String** | referenziert eine Aktensession, in der die Operation ausgefuehrt werden soll; Wenn keine Aktensession besteht, dann muss fuer dieses Aktenkonto (entspricht OwnerInsurantId oder RepresentationXInsurantId in der Konfiguration) eine Aktensession eroeffnet werden (implizites Login). | 
**insurantId** | **String** | Versicherten-ID des Nutzers (Aktenkontoinhaber oder Vertreter) des FdV; Wenn dieser Parameter gesetzt ist, dann wird für die Authentisierung entsprechend dem Konfigurationsparameter useEGK eine angebundene eGK oder der Signaturdienst genutzt.; Wenn  dieser Parameter gesetzt ist, dann werden die Parameter pkcs12, passwordPrivateKey und passwordKeyStore ignoriert. |  [optional]
**pkcs12** | **byte[]** | C.CH.AUT-Zertifikat des Nutzers mit private Key im pkcs12 Format; Aus dem C.CH.AUT-Zertifikat wird die Versicherten-ID des Nutzers (Aktenkontoinhaber oder Vertreter) bestimmt.; Mit dem private Key werden die Signaturen bei der Authentisierung und der Schluesselerzeugung (SGD) erstellt |  [optional]
**passwordPrivateKey** | **String** | Passwort für private Key in pkcs12 |  [optional]
**passwordKeyStore** | **String** |  |  [optional]



