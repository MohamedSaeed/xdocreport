# Loop for fields #

Loop for fields is managed with loop directive of the template engine :

  * **#foreach** directive for [Velocity](http://velocity.apache.org/).
  * [[#list](http://freemarker.sourceforge.net/docs/ref_directive_list.html) directive for [Freemarker](http://freemarker.sourceforge.net/) (with square bracket mode).

If you wish **manage loop for fields** :

  * [Loop for fields in bulleted list with FieldsMetadata](PPTXReportingJavaMainListInBulletedList.md) is the mean to generate bulleted list from a collection of Java objects. In this case you must use FieldsMetada to generate automaticly the loop directive of the template engine, because it's not possible to set the loop directive before/after the item of the bulleted list with MS PowerPoint.