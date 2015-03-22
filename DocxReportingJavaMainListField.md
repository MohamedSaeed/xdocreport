# Loop for fields #

Loop for fields is managed with loop directive of the template engine :

  * **#foreach** directive for [Velocity](http://velocity.apache.org/).
  * [[#list](http://freemarker.sourceforge.net/docs/ref_directive_list.html) directive for [Freemarker](http://freemarker.sourceforge.net/) (with square bracket mode).

If you wish **manage loop for fields** :

  * [Loop for fields in table row with FieldsMetadata](DocxReportingJavaMainListFieldInTable.md) is the easiest mean to generate table row from a collection of Java objects. In this case you must use FieldsMetada to generate automaticly the loop directive of the template engine, because it's not possible to set the loop directive before/after the row table with MS Word.
  * **in anywhere** to generate loop of content, please read [loop for fields in anywhere](DocxReportingJavaMainListFieldAnywhere.md).
  * **advanced table** to generate advanced table row like set a color for odd row and another color for even row, please read [loop for fields in advanced table](DocxReportingJavaMainListFieldAdvancedTable.md).