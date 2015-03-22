# Loop for fields in table row with template engine script #

[Loop for fields in table row with FieldsMetadata](ODTReportingJavaMainListFieldInTable.md) is **the easiest mean to generate table row** from a Java object collection, because when you design your report with MS Word you need not to write some loop script (Freemarker, Velocity....): XDocReport use FieldsMetadata to generate loop script with lazy mode.

[Loop for fields in table row with FieldsMetadata](ODTReportingJavaMainListFieldInTable.md) is the **better solution for a secretary** who wish design the report.

However [Loop for fields in table row with FieldsMetadata](ODTReportingJavaMainListFieldInTable.md) mean is limited if you wish generate more **powerful report** like :

  * manage a **[color for odd/even row](#Manage_a_color_for_odd/even_row.md)** in a table :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableOddEven.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableOddEven.png)

  * manage **[a table in a loop](#Manage_a_table_in_a_loop.md)** (ex : loop for developers and generate a table to display roles per developer):

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableTableInLoop.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableTableInLoop.png)

Advanced Table is the mean where you write loop script (Freemarker, Velocity) in the report. This mean is more powerful than [FieldsMetadata](ODTReportingJavaMainListFieldInTable.md) but more complex to write because you must learn Freemarker or Velocity.

[Loop for fields in table row with script](ODTReportingJavaMainListFieldAdvancedTable.md) is the **better solution for a developer** who wish design the report by customizing the table.

Since [XDocReport 0.9.2](http://code.google.com/p/xdocreport/wiki/XDocReport092), XDocReport provides @before-row and @after-row features to insert template engine script before/after a table row.

## Problem: before/after row loop ##

To manage loop, XDocReport use template engine script. Loop for fields use syntax of the template engine. For instance if you wish generate list of name of developers you can write that with Velocity :

```
#foreach($d in $developers)
  $d.Name
#end
```

And write that with Freemarker :

```
[#list developers as d]
  ${d.name}
[/#list]
```

Problem with loop for table row is that it's not possible to set the start/end directive loop of the template engine before and after the table row with MS Word.

## Solution: use @before-row @after-row ##

To resolve the previous problem, XDocReport provides 2 tokens :

  * **@before-row**_SCRIPT_ : if a MergeField which is inserted in a table cell, contains at first this token, XDocReport will move the _SCRIPT_ before the row. For instance with Velocity you can write :

```
@before-row#foreach($d in $developers)
```

And write that with Freemarker :

```
@before-row[#list developers as d]
```

  * **@after-row**_SCRIPT_ : if a MergeField which is inserted in a table cell, contains at first this token, XDocReport will move the _SCRIPT_ before the row. For instance with Velocity you can write :

```
@after-row#end
```

And write that with Freemarker :

```
@after-row[/#list]
```

## Token configuration ##

If you don't like the notation @before-row and @after-row, you can customize it with FieldsMetadata. Here a sample to use @row instead of @before-row and @/row instead of @after-row :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.setBeforeRowToken("@row");
metadata.setAfterRowToken("@/row");

IXDocReport report = ...			
report.setFieldsMetadata(metadata);
```

After that you can write :

```
@row#foreach($d in $developers)
@/row#end
```

instead of writing :

```
@before-row#foreach($d in $developers)
@after-row#end
```

## UseCases ##

You can find following samples in the [odtandvelocity-\*-sample.zip](http://code.google.com/p/xdocreport/downloads/list) and [odtandfreemarker-\*-sample.zip](http://code.google.com/p/xdocreport/downloads/list) or see the odt report [ODTTableWithoutFieldsMetadataWithVelocity.odt](http://xdocreport.googlecode.com/git/samples/fr.opensagres.xdocreport.samples.odtandvelocity/src/fr/opensagres/xdocreport/samples/odtandvelocity/ODTTableWithoutFieldsMetadataWithVelocity.odt) and [ODTTableWithoutFieldsMetadataWithFreemarker.odt](http://xdocreport.googlecode.com/git/samples/fr.opensagres.xdocreport.samples.odtandfreemarker/src/fr/opensagres/xdocreport/samples/odtandfreemarker/ODTTableWithoutFieldsMetadataWithFreemarker.odt).

### Simple loop for table row ###

If you wish generate **simple table row** of Developer (without FieldsMetadata) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableSimpleTable.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableSimpleTable.png)

You must write this report if you wish use Velocity :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableSimpleTableReport.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableSimpleTableReport.png)

Here Velocity scripts used in this sample :

```
------------------------------------------------------------------------
@before-row#foreach($d in $developers)         | $d.LastName   | $d.Mail
$d.Name                                        |               |
@after-row#end                                 |               |
------------------------------------------------------------------------
```

Each Velocity scripts must be inserted in a Mergefield.  After XDocReport processing, you will do that:


```
#foreach($d in $developers)
------------------------------------------------------------------------
$d.Name         | $d.LastName   | $d.Mail
------------------------------------------------------------------------
#end
```

Here Freemarker scripts used in this sample :

```
------------------------------------------------------------------------
@before-row[#list developers as d] | ${d.lastName}   | ${d.mail}
${d.name}                          |                 |
@after-row[/#list]                 |                 |
------------------------------------------------------------------------
```

Each Freemarker scripts must be inserted in a Mergefield. After XDocReport processing, you will do that:

```
[#list developers as d]
------------------------------------------------------------------------
${d.name}         | ${d.lastName}   |  ${d.mail}
------------------------------------------------------------------------
[/#list]
```

### Manage a **color for odd/even row** ###

If you wish generate **odd/even color for table row** of Developer :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableOddEven.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableOddEven.png)

The table contains 2 rows (a blue and white row) which are displayed or not according the row index.

You must write this report if you wish use Velocity :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableOddEvenReport.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableOddEvenReport.png)

Here Velocity scripts used in this sample :

```
------------------------------------------------------------------------
@before-row#foreach($d in $developers) #if( 0 == $velocityCount%2) | $d.LastName   | $d.Mail
$d.Name                                                            |               |
@after-row#else                                                    |               |
------------------------------------------------------------------------
$d.Name                                                            | $d.LastName   | $d.Mail
@after-row#end #end                                                |               |
------------------------------------------------------------------------
```

Each Velocity scripts must be inserted in a Mergefield. The Velocity  if condition :

```
#if( 0 == $velocityCount%2)
```

is used to check if row is odd or even and generate the blue or white row. After XDocReport processing, you will do that:

```
#foreach($d in $developers) #if( 0 == $velocityCount%2)
------------------------------------------------------------------------
$d.Name         | $d.LastName   | $d.Mail
------------------------------------------------------------------------
#else
------------------------------------------------------------------------
$d.Name         | $d.LastName   | $d.Mail
------------------------------------------------------------------------
#end #end
```

Here Freemarker scripts used in this sample :

```
------------------------------------------------------------------------
@before-row[#list developers as d] [#if d_index % 2 == 0] | ${d.lastName}   | ${d.mail}
${d.name}                                                 |                 |
@after-row[#else]                                         |                 |
------------------------------------------------------------------------
${d.name}                                                 | ${d.lastName}   | ${d.mail}
@after-row[/#if][/#list]                                  |                 |
------------------------------------------------------------------------
```

Each Freemarker scripts must be inserted in a Mergefield. The Freemarker if condition :

```
 [#if d_index % 2 == 0]
```

is used to check if row is odd or even and generate the blue or white row. After XDocReport processing, you will do that:

```
[#list developers as d] [#if d_index % 2 == 0]
------------------------------------------------------------------------
${d.name}         | ${d.lastName}   |  ${d.mail}
------------------------------------------------------------------------
[#else]
------------------------------------------------------------------------
${d.name}         | ${d.lastName}   |  ${d.mail}
------------------------------------------------------------------------
[/#if][/#list]
```

### Manage a table in a loop ###

If you wish generate  **a table in a loop** (ex : loop for developers and generate a table to display roles per developer) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableTableInLoop.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableTableInLoop.png)

You must write this report if you wish use Velocity :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableTableInLoopReport.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTAdvancedTableTableInLoopReport.png)

Here Velocity scripts used in this sample :

```
#foreach($d in $developers)

Identity
Name : $d.Name
Last Name : $d.LastName
Email : $d.Mail
Roles

------------------------------------------------------------------------
Roles
------------------------------------------------------------------------
@before-row#foreach($r in $d.Roles)
$r.Name
@after-row#end
------------------------------------------------------------------------

#end
```

After XDocReport processing, you will do that:

```
#foreach($d in $developers)

Identity
Name : $d.Name
Last Name : $d.LastName
Email : $d.Mail
Roles

------------------------------------------------------------------------
Roles
#foreach($r in $d.Roles)
------------------------------------------------------------------------
$r.Name
------------------------------------------------------------------------
#end
#end
```

Here Freemarker scripts used in this sample :

```
[#list developers as d]

Identity
Name : ${d.name}
Last Name : ${d.lastName}
Email : ${d.mail}
Roles

------------------------------------------------------------------------
Roles
------------------------------------------------------------------------
@before-row[#list d.roles  as r]
${r.name}
@after-row[/#list]
------------------------------------------------------------------------

[/#list]
```

After XDocReport processing, you will do that:

```
[#list developers as d]

Identity
Name : ${d.name}
Last Name : ${d.lastName}
Email : ${d.mail}
Roles

------------------------------------------------------------------------
Roles
[#list d.roles  as r]
------------------------------------------------------------------------
${r.name}
------------------------------------------------------------------------
[/#list]
[/#list]
```