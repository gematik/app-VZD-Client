# VZD-Client

## Changelog

### 1.6.4

#### Bugfixes

-   Nummerierung der Command-Datei wird nun wieder von 1 aufwärts zählend ergänzt und gibt nicht nur den Wert 1 zurück

-   Fehlgeschlagene Aufrufe wegen internen Errors werden nun geloggt

-   ReadSync mapped nun auf den korrekten Typ

-   Leere Listen werden nicht mehr als Suchparameter weitergegeben

#### Features

-   Ein `userCertificate` kann nun auch mit Zeilenumbrüchen in die `CommandDatei` geschrieben werden

-   Im Logverzeichnis wird zusätzlich zum Log eine csv-Datei über alle Kommandos und Erfolg/Misserfolg angelegt. Diese sammelt alle abgesendeten Requests → [LogCsv](VZDCL_GettingStarted.adoc#_log_csv)

-   Chunking von großen Abfragen ist möglich → [Chunking](VZDCL_GettingStarted.adoc#_chunking)

-   Chunking von/bis ist möglich

-   Mit dem Einlesen einer .txt kann nun nicht nur eine Liste von uids verwerten sondern auch eine Liste an TelematikIds → [ListSuche](VZDCL_GettingStarted.adoc#_listsuche)

### 1.6.3

Entspricht der Spezifikation nach Version 1.6.3

#### Features

-   DeleteCert → wieder aktiv

-   changeDateTime → Dateistruktur Anpassung

### 1.6.2

Entspricht der Spezifikation nach Version 1.6.2

#### Bugfixes

-   SaveModifiy → Holder, MaxKOMLEadr und ProfessionOID werden aus dem eingegebenen Command übernommen

-   Read Parameter angepasst und vollständig

-   UnixTimestamps werden in OffsetDateTime umgewandelt und werfen keine Exception mehr.
    Damit werden alle Einträge geloggt.
    Es wird eine Warnung ausgegeben, falls ein solcher Eintrag gelesen wird.

#### Features

-   Suchen nach einer Liste von Uids → siehe [UidSuche](VZDCL_GettingStarted.adoc#_uidsuche)

### 1.6.0

Entspricht der Spezifikation nach Version 1.6.0

#### Features

-   Neue Parameter (professionOID & entryType) → [Read](VZDCL_GettingStarted.adoc#_read_directory_entry_certificate)

-   Neue Opperation → siehe [ServerInfo](VZDCL_GettingStarted.adoc#_get_server_info)

-   Umgenennung → Owner zu Holder

## Introduction

Der VZD-Client dient als Werkzeug um einen Verzeichnisdienst mit Einträgen
so wie Zertifikaten zu füllen, zu bearbeiten, zu löschen oder auszulesen.

## API Documentation

Generated API docs are available at <https://gematik.github.io/app-VZD-Client>.

## License

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

## Getting Started

Im Folgenden wird erklärt wie der `VZD-Client` herunterzuladen und zu verwenden ist.

### VZD-Client herunterladen

Der `VZD-Client` kann als git repository oder von Maven Central heruntergeladen werden.

-   Git

<!-- -->

    git clone https://github.com/gematik/app-VZD-Client.git

-   Maven Central

<!-- -->

    https://search.maven.org/artifact/de.gematik.ti.epa/VZD-CLient

### Build setup

Zum Compilieren des `VZD-Clients` wird das Verwenden von Java11 und gradle 7.1 empfohlen.

Nach dem Compilieren wird eine .zip-Datei in den build/distributions Ordner abgelegt.
Dieser enthält eine ausführbare .bat-Datei.
Die dazugehörige .jar-Datei befindet sich im Ordner build/libs.

## Handling

### Starten des VZD-Clients

In der erstellten oder heruntergeladenen .zip-Datei befinden sich 2 ausführbare .bat-Dateien.

-   startVzdClient.bat → Startet den `VZD-Client` mit den angegebenen [Parameter](#_parameter).

-   startVzdC\_Log.bat → Diese .bat-Datei erwartet als ersten Parameter den gewünschten OutputPath für das Logfile.
    Danach werden die [Parameter](#_parameter) angeführt.

Beim Verwenden der .jar-Datei kann das Log-Level und der Pfad des Logs wie in [Logging](#Logging) verwendet werden.

### Parameter

Zum Bedienen des `VZD-Clients` müssen mindestens 2 Parameter überreicht werden.
Dies geschieht in der Kommandozeile über die Parameter:

Pflicht:

-   -c &lt;Pfad zur Credentials-Datei&gt; (.txt-Datei)

-   -p &lt;Pfad zu Grundeinstellungen&gt; (.txt-Datei)

Optional:

-   -b &lt;Pfad zur Command-Datei&gt; (.xml-Datei / .txt-Datei) ← überschreibt eventuelle Angaben in den Grundeinstellungen

-   -h &lt;ProxyHost IP&gt; ← überschreibt eventuelle Angaben in den Grundeinstellungen

-   -d &lt;Proxy Port&gt; ← überschreibt eventuelle Angaben in den Grundeinstellungen

-   -t &lt;syncTimeout&gt; ← setzt manuell ein maximalen Timeout (default 120s) in Sekunden (überschreibt eventuelle Angaben in den Grundeinstellungen)

-   -version ← gibt die verwendete Version des VZD-Clients aus.
    (Eventuelle Aktionen werden nicht durchgeführt)

### Dateistruktur

1.  **Credentials-Datei:** Diese Datei beinhaltet die User-ID und das Passwort.
    Diese müssen wie folgt in eine .txt-Datei geschrieben werden (die Reihenfolge ist hierbei nicht relevant):

    -   id=&lt;UserId&gt;

    -   secret=&lt;Passwort&gt;

2.  **Grundeinstellungen:** Diese Datei beinhaltet einen BasePath (IP-Adresse oder URL zum anzusprechenden Server), einen RetryingOAuthPath (IP Adresse oder URL zum OAuth2 Token Server) und optional einen CommandsPath (Pfad zur lokalen .xml-Datei) in der die auszuführenden Operationen definiert, eine Angabe wie lang der maximale Timeout bei der Sync Operation sein soll (Default 120 Sekunden) sind sowie ein Proxy-Host einen dazugehörigen Proxy-Port.
    Außerdem können Angaben zum parallelen Ausführen gemacht werden.
    Eine genauere Beschreibung wie sich die Angaben auf den Programmablauf auswirken, kann unter [Paralleles Ausführen](#_paralleles_ausführen) nachgelesen werden.
    Eine weitergehende Beschreibung der Kommandodatei findet sich unter [Bedienung](#Bedienung).
    Die Angaben werden wie folgt in eine .txt-Datei geschrieben (die Reihenfolge ist hierbei nicht relevant):

    -   base=&lt;URL or IP zum Server&gt;

    -   retryingOAuth=&lt;URL or IP zum Server&gt;

    -   proxyHost=&lt;ProxyHost IP&gt;

    -   proxyPort=&lt;Proxy Port&gt;

    -   commands=&lt;Pfad zur .xml-Datei&gt;

    -   syncTimeout=&lt;Maximaler Timeout in Sekunden für das Lesen von Einträgen&gt;

    -   maxOperation=&lt;Anzahl an Operationsarten&gt;

    -   maxExecutionsPerOperation=&lt;Anzahl an parallelen Operationen&gt;

    -   logCommands=&lt;boolean default → false&gt;

    -   trustStorePath=&lt;Pfad zu einem spezifischem TrustStore (Default ist hinterlegt)&gt;

    -   chunked=&lt;boolean default → false&gt;

    -   chunkSize=&lt;Anzahl `Commands` pro Logfile default → 100&gt;

    -   chunkFrom=&lt;Start `Command` - Nummer für chuncked default → 1&gt;

    -   chunkTill=&lt;Ende `Command` - Nummer für chuncked default → 999999999999999999&gt;

    -   listType=&lt;Art Einträge in der Liste beim Einlesen im .txt Format default → "uid" [Anleitung hier!](VZDCL_GettingStarted.adoc#_listsuche)&gt;

Mit dem setzen des `logCommands` wird vor jeder Ausführung eines Befehls dieser vorher geloggt.
Dies erleichtert das Debuggen.

### Bedienung

Die Definition der Kommandos geschieht durch eine .xml-Datei.
Dieser Datei liegt ein generelles Schema zugrunde, welches durch die commands.xsd in der .zip-Datei eingesehen werden kann.
Hierbei ist der Name der Operation verpflichtend.
Eine Angabe einer "commandId" ist nicht notwendig, kann jedoch wahlweise gesetzt werden.
Sollte sie nicht gesetzt werden, wird der Client eine ID vergeben, bei der er von 1 an durch iteriert.
Dies dient der späteren Zuordnung welche Antwort zu welchem Kommando gehört.

Abhängig davon welche Operation durchgeführt werden soll, sind gewisse Angaben verpflichtend und manche optional.
Sollte ein Parameter angegeben werden und für das betreffende Kommando nicht verwendet werden können, so wird dieser ignoriert.
In `gemSpec_VZD` wird in `Kapitel 4.6.1` Operationen der Schnittstelle I\_Directory\_Administration beschrieben, welches Kommando welche Parameter berücksichtigt und welche verpflichtend benötigt werden.

Eine Beispiel .xml-Datei wie Command-Datei aussehen kann befindet sich im Ordner bspData (`Beispiel_Command_Datei.xml`).
Diese Datei gibt einen Einblick welche Parameter für die einzelnen Operationen gesetzt werden können.

Diese Date stellt keine funktionsfähige Command-Datei dar.
Die Parameter müssen angepasst werden.

### Logging

Das Logging geschieht auf der Konsole, sowie in einem File.
Es wird immer ein File erstellt.
Sollte kein spezieller Ort für das Logging angegeben werden, wird das Log im java.io.temp Ordner abgelegt.
Standardmäßig ist das Log-Level in der Konsole "INFO" und im File "DEBUG".

#### Logging mit .bat-Datei

Um das Log-Level der Konsole zu beeinflussen kann ein weiterer Parameter beim Starten der .bat-Datei angegeben werden:

(case sensitive)

-   -info ← Level INFO

-   -trace ← Level TRACE

-   -error ← Level ERROR

-   -debug ← Level DEBUG

Beispiel Aufruf

    <workspace>/startVzdC_Log.bat <log_outputdir> -c <workspace>/credentials.txt -p <workspace>/config.txt -debug

#### Logging mit jar Datei

Um hier Einfluss auf die Logeigenschaften zu nehmen, muss der Java-VM die entsprechenden Systemvariablen übergeben werden.

-   l4j.lvl=&lt;LogLevel&gt; → für das Log-Level

-   l4j.logDir=&lt;LogDir&gt; → für das Log-Output-Directroy

Beispiel Aufruf

    java -Dl4j.lvl=DEBUG -Dl4j.logDir=<log_outputdir> -jar <workspace>/vzd<version>.jar <outputdir> -c <workspace>/credentials.txt -p <workspace>/config.txt

#### Chunking

Mit den Parametern unter [Dateistruktur](VZDCL_GettingStarted.adoc#Dateistruktur) beschrieben kann das Chunking eingeschaltet werden, indem `chunked` der Wert `true` übergeben wird.
Im Logfile Ordner wird ein Unterordner mit der aktuellen Zeit angelegt.
Die ChunkSize legt fest wie viele Einträge pro Logfile in einer Datei landen sollen.
Mit `chunkFrom` und `chunkTill` kann angegeben werden von welchem `Command` bis zu welchem `Command` ausgeführt werden soll.

#### Log-CSV

Die csv-Log Datei wird im Logverzeichnis abgelegt und beim Ausführen jedes `Commands` erweitert.
Sie beinhaltet:

-   Date

-   Time

-   SystemUser (Logfile kann auch für Kooperation auf einem Shared Laufwerk abgelegt werden)

-   Credential\_User

-   TelematikID

-   ExecutionCommand

-   ExecutionStatus

-   Data

Achtung, die Datei darf nicht u.a. mit Exel geöffnet sein.
Andernfalls wirft der Client einen Fehler, dieser führt nicht zum Abbruch der Auftragreihe

### Ausführen des VZD-Clients mit Proxy

Um den VZD-Client über einen Proxy starten, müssen wie unter [Dateistruktur](#Dateistruktur) unter Grundeinstellungen Angaben in der Konfigurationsdatei zu Host und Port angegeben werden.

Außerdem ist es möglich den Host über den Parameter "-h" und den Port über "-d" direkt in der Kommandozeile zu übergeben.
Sollte dies gemacht werden, so überschreibt dies die Einstellungen, die eventuell in der Configurationsdatei angegeben wurden.

### Paralleles Ausführen

Durch das Konfigurationsfile können Einstellungen getätigt werden, die die Ausführung der Operationen des `VZD-Client` beschleunigen (Wie diese Angaben korrekt getätigt werden, dann unter [Parameter](#_parameter) nachgelesen werden).

Der `VZD-Client` kann mehrere Operationsarten (AddDirectory, ModifyDirectory etc.) gleichzeitig ausführen.
Hierzu dient die Angabe maxOperations.
Der Maximalwert dieser Angabe liegt bei 8. Es kann keinen Einfluss auf die Reihenfolge genommen werden.

Die Einstellung maxExecutionsPerOperation beschreibt wie viele gleichartige Operationen gleichzeitig ausgeführt werden.
Diese Zahl ist aufgrund von Ressourcenplanung auf 20 limitiert.

## Abarbeiungsflows

Der Workflow ist in der Abbildung [figure\_title](#Abarbeitungsworkflow) abzulesen.
Alle Operationen müssen über ein Commandfile, wie in 2.3 Bedienung beschrieben, übergeben werden und werden vom `VZD-Client` ausgelesen.
Bevor sie ausgeführt werden, wird abhängig der Operation eine Validitätsprüfung durchgeführt.
Genauere Angaben der Validitätsprüfung, können unter der jeweiligen Operation unter [Abarbeiungsflows](#Abarbeiungsflows) eingesehen werden.

Sollte diese bei einem Command fehlschlagen, so wird die Durchführung abgebrochen.
Der Workflow unterscheidet zwischen „Modify“, „Add“, „Read“ und „Delete“.
In der Abarbeitung gibt es jedoch leichte Unterschiede, die unter
[Abarbeiungsflows](#Abarbeiungsflows) behandelt werden.Vor dem Ausführen des Requests wird überprüft, ob der OAuth2 Token noch Gültigkeit besitzt.
Anschließend wird der Request ausgeführt und das Ergebnis in ein Logfile geschrieben, das ausgewertet werden kann.

![Abarbeitungsworkflow (deprecated)](../images/Workfows_VZD.png)

## Operationen

### Add Directory Entry / Certificate

#### Validitätsprüfung

Erforderliche Einträge für ein Add Directory Entry ist mindestens ein `UserCertificate`.
Jedes dieser `UserCertificate` muss mindesten eine `telematikID` oder ein `userCertificate` beinhalten.
Alle weiteren Angaben sind optional.

Beim Ausführen eines Add Directory Entry Certificate werden für jedes Zertifikat die `uids` mit der im `BaseDirectoryEntry` angegebenen `uid`
verglichen.
Sollte die `uid` abweichen so gilt das Kommando als nicht valide.

#### Ablauf

Bevor ein Add Directory Entry ausgeführt wird überprüft der Client, ob bereits ein Eintrag existiert.
Sollte dies der Fall sein, wird anstelle eines Add- ein Modify Directory Entry ausgeführt.

Bei einem Add Directory Entry Certificate werden die angegebenen Parameter an den `VZD` gesendet.

Bei erfolgreicher Durchführung wird jeweils die Antwort `distinguished name` des Servers in den Log geschrieben.

### Read Directory Entry / Certificate

#### Validitätsprüfung

Es wird nur überprüft, ob mindestens eins der Attribute nach denen gesucht werden kann angegeben wurde.

Mögliche Parameter für das Lesen eines Entries sind die in der folgenden Funktion genannt:

**GemDirectoryEntryAdministrationApi.java.**

      public Call readDirectoryEntryCall(String uid, String givenName, String sn, String cn,
          String displayName, String streetAddress,
          String postalCode, String countryCode, String localityName, String stateOrProvinceName,
          String title, String organization, String otherName,
          String telematikID, String telematikIDSubStr, String specialization, String domainID,
          String holder, String personalEntry,
          String dataFromAuthority, String professionOID, String entryType, Boolean baseEntryOnly,
          final ApiCallback _callback)

Mögliche Parameter für das Lesen eines Zertifikats (hierbei wird nur das Element `UserCertificate` betrachtet):

**GemDirectoryEntryAdministrationApi.java.**

      public okhttp3.Call readDirectoryCertificatesCall(String uid, String certificateEntryID,
          String entryType, String telematikID, String professionOID, String usage,
          final ApiCallback _callback)

#### Ablauf

Alle in dem Kommando genannten Parameter werden zusammengefügt und als Request an den `VZD` gesendet.
Bei erfolgreicher Anfrage wird eine Liste aller auf die Suchkriterien zutreffende Einträge in das Log geschrieben.

### Read Directory Entry Sync

Ist equivalent mit [Read Directory Entry / Certificate](#_read_directory_entry_certificate), mit Außnahme, dass die Begrenzung von 100 maximalen Einträgen ausgesetzt ist.
Die parallele Ausführung dieser Operation wird vom Server aus Performancegründen eingeschränkt.
Diese Operation soll nur genutzt werden, wenn mehr als 100 Suchergebnisse benötigt werden.

### Modify Directory Entry

#### Validitätsprüfung

Bei einem Modify Directory Entry wird die Angabe einer uid vom `VZD-Client` überprüft.
Sollte versucht werden dieses Kommando auszuführen, wird dies zu einem Fehler führen.

#### Ablauf

Bevor ein Modify Directory Entry ausgeführt überprüft der Client, ob bereits ein Eintrag zu der uid existiert.
Sollte dies nicht der Fall sein, so wird anstelle eines Modify- ein Add Directory Entry ausgeführt.
Bei erfolgreicher Durchführung wird der jeweils zurückgelieferte `distinguished name` ins Log geschrieben.

ACHTUNG:

Wenn ein Modify Directory Entry durchgeführt wird, werden alle veränderbaren Attribute, die in dem Kommando nicht gesetzt wurden auf null gesetzt.
Sollte nur ein Attribut überschrieben werden, kann die Funktion [Save Modify Directory Entry](#_save_modify_directory_entry) verwendet werden.

Zertificate können nicht modifiziert werden

### Delete Directory Entry / Certificate

#### Validitätsprüfung

Bei einem Delete Directory Entry Kommando wird die Angabe einer uid vom `VZD-Client` überprüft.
Anschließend wird der Löschbefehl gesendet.

Bei einem Delete Directory Entry Certificate Kommando wird die uid aus dem `UserCertificate` aus allen Zertifikaten ausgelesen.
Dieses muss mit der uid im `BaseDirectorEntry` und innerhalb eines Commandos überein stimmen, sofern angegeben.
Sobald an einer Stelle eine angegeben wurde wird diese verwendet.
Außerdem muss ein `cn` im `UserCertificate` angegeben werden.
Sollte eine dieser Voraussetzungen nicht zutreffen, bricht der VZD die Bearbeitung ab.

#### Ablauf

Sollte der Request erfolgreich sein, so wird die gelöschte uid als Bestätigung in das Log geschrieben.

### Get Server Info

#### Validitätsprüfung

Es muss nur der Name der Opperation angegeben werden.

#### Ablauf

Es wird ein Get-Request gegen den Verzeichnisdienst gestellt und die zurückkommende Antwort ausgegeben.

### Save Modify Directory Entry

Dies ist eine ergänzende Funktion die nicht in der Spezifikation festgehalten ist.
Sie soll den Umgang erleichtern und die Fehleranfälligkeit verringern.
Sie kann dazu genutzt werden einzelne Attribute eines Eintrages zu überschreiben ohne die restlichen Attribute zu löschen.

#### Validitätsprüfung

Die Validitätsprüfung ist deckungsgleich zu der unter [Modify Directory Entry](#_modify_directory_entry)

#### Ablauf

Im gegensatz zu dem Modify-Directory-Entry-Befehl wird der Eintrag bei dieser Operation nicht komplett mit den angegebenen Daten ersetzt.
Der
`VZD-Client` führt vorher eine Readoperation durch und vergleicht die Daten die unterschiedlich sind und ersetzt die im Command angegebenen Daten.

### ListSuche

Dies ist die einzige Funktion die eine .txt-Datei als Befehlsdatei entgegen nimmt.
Jede Zeile repräsentiert hier eine Uid/Telematikid auf die ein [Read](#_read_directory_entry_certificate) ausgeführt wird. Es kann immer nur entweder Uid oder TelematikIds in einer Datei sein. Der Typ muss in der [Configurationsdatei](VZDCL_GettingStarted.adoc#Dateistruktur) angegeben werden.

#### Validitätsprüfung

Es gibt keine Validitätsprüfung.
Jede Zeile in der Textdatei wird als Uid/TelematikId eingelesen.

#### Ablauf

Jede Zeile in der Textdatei wird als Uid eingelesen und zu dieser ein [Read](#_read_directory_entry_certificate) ausgeführt.
