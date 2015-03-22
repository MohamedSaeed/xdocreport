# Simple/Hyperlink field #

The [Docx Reporting in Java Main](DocxReportingJavaMain.md) section show you how to manage simple field with Mergefield. To se a field in the docx document to merge with a Java data model, you can use :

  * Mergefield.
  * Hyperlink if you wish display hyperlink.

And fill it with value which follow syntax of the choosen template engine :

  * **$project.Name** for [Velocity](http://velocity.apache.org/).
  * **${project.name}** for [Freemarker](http://freemarker.sourceforge.net/).