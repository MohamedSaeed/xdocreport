# Frequently Asked Questions #

## What's is XDocReport? ##

XDocReport is reporting tool to merge XML document created with [MS Office](http://office.microsoft.com/fr-fr/) (docx) or [OpenOffice](http://www.openoffice.org/) (odt), [LibreOffice](http://www.libreoffice.org/) (odt) with a Java model to generate report and convert it if you need to another format (PDF, XHTML...).

## How I set my fields to replace in the document? ##

XDocReport use template engine (Velocity/Freemarker) to replace fields with values coming from Java model. The best practice is to use
  * MergeField for docx. For more information please go at [Docx Design Report](DocxDesignReport.md) section.
  * InputField for odt. For more information please go at [ODT Design Report](ODTDesignReport.md) section.

XDocReport implement by default Freemarker and Velocity template engine.

## How to manage loop? ##

Loop are managed with loop directive depending on template engine used :

  * Velocity : use #foreach directive.
  * Freemarker : use [#list directive.

If you want manage loop for table row, the loop directive is generated automaticly because it's impossible to set in the document the loop directive before a row. You must set a fields with template engine and use FieldsMetadata to generate automaticly #foreach or [#list template engine directive before the row table.

For more information, please go at

  * [List Field for docx](DocxReportingJavaMainListField.md) if you are using docx.
  * [List Field for odt](ODTReportingJavaMainListField.md) if you are using odt.

## Can I convert my generated report to another format? ##

Yes. You can convert ODT or DOCX generated report to another format XHTML, PDF. There is some limitations like Drawing converter which is not managed. Goal of converter is to convert commons widgets (Table, Paragraph...) used in the basic report.

## Can I just use converter? ##

Yes it's possible.

You can too use just POI and just ODFDOM converter projects if you wish.

## Can I play with XDocReport now? ##

Yes it's possible, you can try and play with XDocReport with the online WEB Application http://xdocreport.opensagres.cloudbees.net/

## What is the difference with [Birt](http://www.eclipse.org/birt/phoenix/), [JasperReports](http://jasperforge.org/projects/jasperreports) like reporting tool ? ##

  * XDocReport advantage : create your report with MS Word or OpenOffice. With [Birt](http://www.eclipse.org/birt/phoenix/), [JasperReports](http://jasperforge.org/projects/jasperreports) you need to use Report designer. Imagine you wish just change static content in tyour document; with XDocReport you can do it with MS Word or OpenOffice.

  * XDocReport disadvantage : [Birt](http://www.eclipse.org/birt/phoenix/), [JasperReports](http://jasperforge.org/projects/jasperreports) are very powerfull to generate report (design report with pixel unit) and it can generate a complex report to many format.

## Similar projects ##

### Paid products ###

  * [docmosis](http://www.docmosis.com/)
  * [Aspose.Words for Java](http://www.aspose.com/java/word-component.aspx)
  * [DocXperT](http://docxpert.eu/). It exists too a free version.

#### Open source projects ####

#### JODReports ####

[JODReports](http://jodreports.sourceforge.net/) has the same goal of XDocReport : generate ODT report from ODT model document by using Freemarker syntax. Here differences between XDocReport and JODReports :

  1. **modularity** :
    * JODReport manage only Freemarker syntax. With XDocReport you can choose your syntaxe by selecting template engine. By default you can use Freemarker, Velocity syntax, but you can implements your own template engine if you need.
    * JODReport manage only ODT. XDocReport manage docx too, but you can implements another format if you like.
  1. **converter** : can convert ODT,DOCX to another format (PDF, XHTML).
  1. **performance** : XDocReport improve performance by implementing Freemarker/Velocity cache.
  1. **WEB context** : XDocReport provides support for WEB context.
  1. **OSGi context** : XDocReport are plugins+fragments.
  1. **Text Styling** : you can use HTML, Wiki syntax etc to style the text value (bold, italic etc).

## Can I use XDocReport in WEB context? ##

Yes

## Can I use XDocReport in OSGi context? ##

Yes

## Can I use XDocReport in GAE context? ##

Yes

## Can I use XDocReport in Struts2 context? ##

Yes