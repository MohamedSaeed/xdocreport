# XDocReport 1.0.0 #

Released December 02, 2012

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

The XDocReport team provides release bundles hosted on the **[Downloads](http://code.google.com/p/xdocreport/downloads/list)** tab in ZIP. As XDocReport is modular, it exists a ZIP per configuration. For instance if you wish manage Docx with Velocity and convert your report to PDF, XHTML, you must download **[docxandvelocity.converters-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)**. This ZIP contains required XDocReport and dependant (POI,...) JARs and source code with sample.

**Attention for PDF conversion**: for license raison, **we cannot set itext used for PDF in the samples** ZIP. So if you wish manage PDF conversion , please [download itext-2.1.7.jar](http://search.maven.org/#artifactdetails|com.lowagie|itext|2.1.7|jar) at hand on the Maven repository.

Here an array of XDocReport download according to you wish to do :

#### Microsoft Office ####

| **Document** | **Template** | **Converter** |
|:-------------|:-------------|:--------------|
| Docx       | Freemarker |     | **[docxandfreemarker-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |       | **[docxandvelocity-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |     PDF/XHTML      | **[docxandvelocity.converters-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| PPTX       | Velocity |              | **[pptxandvelocity-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

#### Open/Libre Office ####

| **Document** | **Template** | **Converter** |
|:-------------|:-------------|:--------------|
| ODP       | Velocity |      | **[odpandvelocity-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| ODT       | Velocity |      | **[odtandvelocity-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| ODT       | Velocity |     PDF/XHTML      | **[odtandvelocity.converters-1.0.0-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

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
  <version>1.0.0</version>
</dependency>
```


If you wish use MS PowerPoint pptx :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.pptx</artifactId>
  <version>1.0.0</version>
</dependency>
```

If you wish use OpenOffice odt :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.odt</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### Template Dependency ####

If you wish use Freemarker :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.freemarker</artifactId>
  <version>1.0.0</version>
</dependency>
```

If you wish use Velocity :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.velocity</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### Converter Dependency ####

If you wish convert docx 2 PDF/XHTML :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.docx.xwpf</artifactId>
  <version>1.0.0</version>
</dependency>
```

If you wish convert odt 2 PDF/XHTML  :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.odt.odfdom</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### Tools Dependency ####

Tools gives you the capability to generate report with Java main command. It is used by xdrtools.bat and xdrtools.sh for the preview feature in the [XDocReport MS Word macro](http://code.google.com/p/xdocreport/wiki/DocxReportingQuickStart) and [XDocReport OOo extension macro](http://code.google.com/p/xdocreport/wiki/ODTReportingQuickStart).

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.tools</artifactId>
  <version>1.0.0</version>
</dependency>
```

#### REST/SOAP Remoting Resources Dependency ####

API :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources</artifactId>
  <version>1.0.0</version>
</dependency>
```

JAX-RS/JAX-WS Client :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.client</artifactId>
  <version>1.0.0</version>
</dependency>
```

JAX-RS/JAX-WS Server:

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.server</artifactId>
  <version>1.0.0</version>
</dependency>
```

## New Features ##

  * manage JDK6+JDK5 : see [issue 133](http://code.google.com/p/xdocreport/issues/detail?id=133)
  * provides new simple API reporting : see [issue 162](http://code.google.com/p/xdocreport/issues/detail?id=162)

Improvement for HTML text styling :

  * fix problem with "Merging HTML styled text double in DOCX files with velocity" : see [issue 134](http://code.google.com/p/xdocreport/issues/detail?id=134)
  * Manage underline with HTML Text styling for docx and odt by using the "u" HTML tag : see [issue 135](http://code.google.com/p/xdocreport/issues/detail?id=135)
  * Manage strike with HTML Text styling for docx and odt by using the "strike" HTML tag : see [issue 136](http://code.google.com/p/xdocreport/issues/detail?id=136)

Improvement for docx->PDF converter :

  * fix problem with "docx->pdf converter problem with text direction in a table cell" : see [issue 146](http://code.google.com/p/xdocreport/issues/detail?id=146)
  * fix problem docx->pdf converter doesn't manage page number : see [issue 166](http://code.google.com/p/xdocreport/issues/detail?id=166).
  * fix problem "docx->pdf converter problem with switch of orientation"  : see [issue 141](http://code.google.com/p/xdocreport/issues/detail?id=141).
  * fix problem "Vertical alignment is not managed" : [issue 2](http://code.google.com/p/xdocreport/issues/detail?id=2).
  * fix problem "Paragraph spaces are not correctly rendered in Word arrays" [5](issue.md).
  * fix problem "docx->pdf converter doesn't manage vMerge for table cell" [issue 184](http://code.google.com/p/xdocreport/issues/detail?id=184).
  * fix problem "table headers are not repeat accross pages for docx->pdf converter" : see [issue 185](http://code.google.com/p/xdocreport/issues/detail?id=185).
  * fix problem "docx->pdf converter doesn't manage hyperlink" : see [issue 167](http://code.google.com/p/xdocreport/issues/detail?id=167).
  * fix problem "docx->pdf converter doesn't manage internal hyperlink (with bookmark)" : see [issue 187](http://code.google.com/p/xdocreport/issues/detail?id=187).
  * fix problem "docx->pdf converter problem with image in the header/footer" : see [issue 139](http://code.google.com/p/xdocreport/issues/detail?id=139).

Improvement for docx->xhtml converter :

  * "docx->xhtml converter should extract image files" : see [issue 189](http://code.google.com/p/xdocreport/issues/detail?id=189).

Improvement for ODT->PDF converter :

  * fix problem with "table headers are not repeat accross pages for odt->pdf converter" : see [issue 158](http://code.google.com/p/xdocreport/issues/detail?id=158)
  * fix problem with "page number is not managed for odt->pdf converter" : see [issue 157](http://code.google.com/p/xdocreport/issues/detail?id=157)
  * fix problem with "table of content styling ODF->pdf converter" : see [issue 101](http://code.google.com/p/xdocreport/issues/detail?id=101)
  * fix problem with "table content mixed in ODT->pdf converter" : see [issue 182](http://code.google.com/p/xdocreport/issues/detail?id=182)
  * fix problem with "internal link from table of content does not work in ODT-pdf converter" : see [issue 183](http://code.google.com/p/xdocreport/issues/detail?id=183)
  * fix problem with "ODT->PDF Conversion: Picture Description shows up in PDF " : see [issue 191](http://code.google.com/p/xdocreport/issues/detail?id=191)
  * fix problem with "ODT->PDF Conversion: Header/Footer table column spacing is ignored in PDF" : see [issue 192](http://code.google.com/p/xdocreport/issues/detail?id=192)
  * fix problem with "ODT->PDF Conversion: Header/Footer table column spacing is ignored in PDF" : see [issue 193](http://code.google.com/p/xdocreport/issues/detail?id=193)

Improvement for ODT->xhtml converter :

  * "odt->xhtml converter should extract image files" : see [issue 190](http://code.google.com/p/xdocreport/issues/detail?id=190).

Fix somes bugs :

  * fix problem with "Parsing trouble when using SAXParser from Oracle jar" : see [issue 150](http://code.google.com/p/xdocreport/issues/detail?id=150)
  * fix problem with "html link with multiple url parameters conflicts with reference" : see [issue 147](http://code.google.com/p/xdocreport/issues/detail?id=147)
  * fix problem "&amp; in label of hyperlink - Follow-up to [issue 147](https://code.google.com/p/xdocreport/issues/detail?id=147)" : see [issue 154](http://code.google.com/p/xdocreport/issues/detail?id=154)
  * fix problem "Can't use "&amp;" or "&lt;" with html in docx" : see [issue 155](http://code.google.com/p/xdocreport/issues/detail?id=155)
  * fix problem "FieldExtractor incorrectly identifies fields in free text" : see [issue 186](http://code.google.com/p/xdocreport/issues/detail?id=186)

Reporting :

  * Manage '\n' and '\t' natively for odt and docx report : see [isuse 181](http://code.google.com/p/xdocreport/issues/detail?id=181)