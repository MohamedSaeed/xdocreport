# Simple/Hyperlink field #

The [ODT Reporting in Java Main](ODTReportingJavaMain.md) section show you how to manage simple field with input field. To se a field in the ODT document to merge with a Java data model, you can use :

  * Input-field.
  * Hyperlink if you wish display hyperlink.

And fill it with value which follow syntax of the choosen template engine :

  * **$project.Name** for [Velocity](http://velocity.apache.org/).
  * **${project.name}** for [Freemarker](http://freemarker.sourceforge.net/).