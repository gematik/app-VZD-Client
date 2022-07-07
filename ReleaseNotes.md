# Release 1.6.4
# 1.6.4  

Ist nun mit gradle 7.1 baubar.
  
 ## Bugfixes  
  
- Nummerierung der Command-Datei wird nun wieder von 1 aufwärts zählend ergänzt und gibt nicht nur den Wert 1 zurück  
- Fehlgeschlagene Aufrufe wegen internen Errors werden nun geloggt  
- ReadSync mapped nun auf den korrekten Typ  
- Leere Listen werden nicht mehr als Suchparameter weitergegeben  
  
## Features  
  
- Ein `userCertificate` kann nun auch mit Zeilenumbrüchen in die `CommandDatei` geschrieben werden  
- Im Logverzeichnis wird zusätzlich zum Log eine csv-Datei über alle Kommandos und Erfolg/Misserfolg angelegt. Diese sammelt alle abgesendeten Requests 
- Chunking von großen Abfragen ist möglich 
- Chunking von/bis ist möglich  
- Mit dem Einlesen einer .txt kann nun nicht nur eine Liste von uids verwerten sondern auch eine Liste an TelematikIds 

# Release 1.6.3
## 1.6.3

Entspricht der Spezifikation nach Version 1.6.3

### Features

- DeleteCert -> wieder aktiv
- changeDateTime -> Dateistruktur Anpassung
- Suchen nach einer Liste von Uids -> siehe link:VZDCL_GettingStarted.adoc#_uidsuche[UidSuche]
- Neue Parameter (professionOID & entryType) -> link:VZDCL_GettingStarted.adoc#_read_directory_entry_certificate[Read]
- Neue Opperation -> siehe link:VZDCL_GettingStarted.adoc#_get_server_info[ServerInfo]
- Umgenennung -> Owner zu Holder

### Bugfixes

- SaveModifiy -> Holder, MaxKOMLEadr und ProfessionOID werden aus dem eingegebenen Command übernommen
- Read Parameter angepasst und vollständig
- UnixTimestamps werden in OffsetDateTime umgewandelt und werfen keine Exception mehr. Damit werden alle Einträge geloggt. Es wird eine Warnung ausgegeben, falls ein solcher Eintrag gelesen wird.

