# XDocReport Process #

docx/odt files are a zip wich contains several entries :

  * XML files like content.xml for odt, word/document.xml for docx
  * images.

Here a screen about docx unzipped :

![http://wiki.xdocreport.googlecode.com/git/screenshots/XDocReportProcess_DocxUnzipped.png](http://wiki.xdocreport.googlecode.com/git/screenshots/XDocReportProcess_DocxUnzipped.png)

Here a screen about odt unzipped :

![http://wiki.xdocreport.googlecode.com/git/screenshots/XDocReportProcess_ODTUnzipped.png](http://wiki.xdocreport.googlecode.com/git/screenshots/XDocReportProcess_ODTUnzipped.png)

When docx or odt report is generated, there is 3 steps that is done :

  * **orginal** : this step load docx/odt in a Java structure XDocArchive which store each entry of the docx/odt.
  * **preprocesed** : this step modify some XML entries (content.xml for odt, word/document.xml for docx) to replace mergefield/input field code with static content, add #foreach automaticly before row...A new XDocArchive is created with the XML entries modified.
  * **generated** : this step consist to use preprocessed XDocArchive and merge some XML entry (content.xml for odt, word/document.xml for docx) with Java model with template engine (Velocity/Freemarker) to generate report.