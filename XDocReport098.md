# XDocReport 0.9.8 #

Released July 16, 2012

## Prerequisites ##

Before downloading XDocReport JARs, you must understand that XDocReport is **modular** which is based on 3 modules :

  * **document** : this module defines the document kind you wish used as source (docx, odt, ...). Every **fr.opensagres.xdocreport.document....jar** manages this module.
  * **template** : this module defines the script syntax (Velocity, Freemarker) you wish use in the document source (odt, docx...) to set the fields name, script for manage loop of items, condition... Every **fr.opensagres.xdocreport.template...jar** manages this module.
  * **converter** : this module defines the capability to convert the generated report (docx, odt...) to another format (PDF, XHTML...). Every **fr.opensagres.xdocreport.converter...jar** manages this module.

To use XDocReport to manage reporting you **must download** :

  * required XDocReport API JARs :
    * **fr.opensagres.xdocreport.core**: XDocReport Core.
    * **fr.opensagres.xdocreport.document** : Document type API
    * **fr.opensagres.xdocreport.template**: Template engine API
    * **fr.opensagres.xdocreport.converter**: Converter API
  * the well XDocReport implementation of **document**, **template** and **converter** according to that you wish managed (odt, docx... with Freemarker/Velocity... and convert it with PDF, XHTML...).

## Downloads ##

XDocReport releases are consumable in 2 different formats:

### Release bundle ###

The XDocReport team provides release bundles hosted on the **[Downloads](http://code.google.com/p/xdocreport/downloads/list)** tab in ZIP. As XDocReport is modular, it exists a ZIP per configuration. For instance if you wish manage Docx with Velocity and convert your report to PDF, XHTML, you must download **[docxandvelocity.converters-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)**. This ZIP contains required XDocReport and dependant (POI,...) JARs and source code with sample.

**Attention for PDF conversion**: for license raison, **we cannot set itext used for PDF in the samples** ZIP. So if you wish manage PDF conversion , please [download itext-2.1.7.jar](http://search.maven.org/#artifactdetails|com.lowagie|itext|2.1.7|jar) at hand on the Maven repository.

Here an array of XDocReport download according to you wish to do :

#### Microsoft Office ####

| **Document** | **Template** | **Converter** |
|:-------------|:-------------|:--------------|
| Docx       | Freemarker |     | **[docxandfreemarker-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |       | **[docxandvelocity-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |     PDF/XHTML      | **[docxandvelocity.converters-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| PPTX       | Velocity |              | **[pptxandvelocity-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

#### Open/Libre Office ####

| ODP       | Velocity |      | **[odpandvelocity-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
|:----------|:---------|:-----|:------------------------------------------------------------------------------------------|
| ODT       | Velocity |      | **[odtandvelocity-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| ODT       | Velocity |     PDF/XHTML      | **[odtandvelocity.converters-0.9.8-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

### Maven repository artifacts ###

The preferred method of "using" XDocReport is via Maven artifact repositories. Many build tools are able to interoperate with Maven repositories including Maven, Ivy, Gradle, etc.

XDocReport releases are available on [central Maven Repository](http://search.maven.org/#search|ga|1|xdocreport).

As explained below XDocReport is modular. You must choose :

  * Your Document dependency
  * Your Template dependency
  * Your Converter dependency

#### Document Dependency ####

If you wish use MS Word docx :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.docx</artifactId>
  <version>0.9.8</version>
</dependency>
```


If you wish use MS PowerPoint pptx :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.docx</artifactId>
  <version>0.9.8</version>
</dependency>
```

If you wish use OpenOffice odt :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.odt</artifactId>
  <version>0.9.8</version>
</dependency>
```

#### Template Dependency ####

If you wish use Freemarker :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.freemarker</artifactId>
  <version>0.9.8</version>
</dependency>
```

If you wish use Velocity :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.velocity</artifactId>
  <version>0.9.8</version>
</dependency>
```

#### Converter Dependency ####

If you wish convert docx 2 PDF/XHTML :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.docx.xwpf</artifactId>
  <version>0.9.8</version>
</dependency>
```

If you wish convert odt 2 PDF/XHTML  :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.odt.odfdom</artifactId>
  <version>0.9.8</version>
</dependency>
```

#### Tools Dependency ####

Tools gives you the capability to generate report with Java main command. It is used by xdrtools.bat and xdrtools.sh for the preview feature in the [XDocReport MS Word macro](http://code.google.com/p/xdocreport/wiki/DocxReportingQuickStart) and [XDocReport OOo extension macro](http://code.google.com/p/xdocreport/wiki/ODTReportingQuickStart).

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.tools</artifactId>
  <version>0.9.8</version>
</dependency>
```

#### REST/SOAP Remoting Resources Dependency ####

API :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources</artifactId>
  <version>0.9.8</version>
</dependency>
```

JAX-RS/JAX-WS Client :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.client</artifactId>
  <version>0.9.8</version>
</dependency>
```

JAX-RS/JAX-WS Server:

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.server</artifactId>
  <version>0.9.8</version>
</dependency>
```

## New Features ##

XDocReport 0.9.8 is focus on REST Services /Web Services to download/upload "template" document (docx, odt) from a "cloud" repository (ex : WEB Application) with OpenOffice/LibreOffice (please read [REST/SOAP Services](http://code.google.com/p/xdocreport/wiki/RESTSOAPServices).

  * Fix "Lists not styled in ODF->pdf converter", see [issue 99](http://code.google.com/p/xdocreport/issues/detail?id=99) for more information
  * Fix "Some images are invisible in ODF->pdf converter", see [issue 100](http://code.google.com/p/xdocreport/issues/detail?id=100) for more information
  * Fix "Next header/footer styling in ODF->pdf converter", see [issue 102](http://code.google.com/p/xdocreport/issues/detail?id=102) for more information
  * Fix "Text on image missing ODF->pdf converter", see [issue 103](http://code.google.com/p/xdocreport/issues/detail?id=103) for more information
  * Fix "Bookmarks and hyperlink within the document (ODT) not getting converted into appropriate PDF behaviour", see [issue 114](http://code.google.com/p/xdocreport/issues/detail?id=114) for more information
  * Fix "Inline loop with @before-row", see [issue 97](http://code.google.com/p/xdocreport/issues/detail?id=97)
  * Manage with HTML text styling, page break before/after for [docx](http://code.google.com/p/xdocreport/wiki/DocxReportingJavaMainHTMLTextStyling#Attributes) and [odt](http://code.google.com/p/xdocreport/wiki/ODTReportingJavaMainHTMLTextStyling#Attributes), see [http://code.google.com/p/xdocreport/issues/detail?id=123 [issue 123](https://code.google.com/p/xdocreport/issues/detail?id=123)
  * Fix "Corrupt file message generating report with header/footer", see [issue 121](http://code.google.com/p/xdocreport/issues/detail?id=121)
  * Fix "Errors in odt->pdf conversion if odt file created by MsWord", see [issue 125](http://code.google.com/p/xdocreport/issues/detail?id=125) for more information
  * Fix "Text Styling with HTML and Umlauts causes defective XML (ODT and DOCX) files.", see [issue 129](http://code.google.com/p/xdocreport/issues/detail?id=129)
  * Fix "Not able to write the values in a PPTX table using velocity", see [issue 122](http://code.google.com/p/xdocreport/issues/detail?id=122)
  * "Manage br for HTML TextStyling with docx and odt" see [issue 130](http://code.google.com/p/xdocreport/issues/detail?id=130)