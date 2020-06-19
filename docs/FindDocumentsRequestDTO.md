

# FindDocumentsRequestDTO

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**account** | [**Login**](Login.md) |  | 
**doc** | [**FindDocumentMetaData**](FindDocumentMetaData.md) |  |  [optional]
**query** | [**QueryEnum**](#QueryEnum) | optional; falls angegeben, ist die entsprechende Stored Query anzuwenden; falls nicht angegeben, ist eine entsprechend der Suchparameter geeignete Stored Query anzuwenden |  [optional]



## Enum: QueryEnum

Name | Value
---- | -----
FINDDOCUMENTS | &quot;FindDocuments&quot;
FINDSUBMISSIONSETS | &quot;FindSubmissionSets&quot;
FINDDOCUMENTSBYTITLE | &quot;FindDocumentsByTitle&quot;
GETALL | &quot;GetAll&quot;
GETDOCUMENTS | &quot;GetDocuments&quot;
GETSUBMISSIONSETS | &quot;GetSubmissionSets&quot;
GETSUBMISSIONSETANDCONTENTS | &quot;GetSubmissionSetAndContents&quot;
FINDDOCUMENTSBYREFERENCEID | &quot;FindDocumentsByReferenceId&quot;



