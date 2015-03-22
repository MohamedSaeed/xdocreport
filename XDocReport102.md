# XDocReport 1.0.2 #

Released May 21, 2013

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

The XDocReport team provides release bundles hosted on the **[Downloads](http://code.google.com/p/xdocreport/downloads/list)** tab in ZIP. As XDocReport is modular, it exists a ZIP per configuration. For instance if you wish manage Docx with Velocity and convert your report to PDF, XHTML, you must download **[docxandvelocity.converters-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)**. This ZIP contains required XDocReport and dependant (POI,...) JARs and source code with sample.

**Attention for PDF conversion**: for license raison, **we cannot set itext used for PDF in the samples** ZIP. So if you wish manage PDF conversion , please [download itext-2.1.7.jar](http://search.maven.org/#artifactdetails|com.lowagie|itext|2.1.7|jar) at hand on the Maven repository.

Here an array of XDocReport download according to you wish to do :

#### Microsoft Office ####

| **Document** | **Template** | **Converter** |
|:-------------|:-------------|:--------------|
| Docx       | Freemarker |     | **[docxandfreemarker-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |       | **[docxandvelocity-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |     PDF/XHTML      | **[docxandvelocity.converters-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| PPTX       | Velocity |              | **[pptxandvelocity-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

#### Open/Libre Office ####

| **Document** | **Template** | **Converter** |
|:-------------|:-------------|:--------------|
| ODP       | Velocity |      | **[odpandvelocity-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| ODT       | Velocity |      | **[odtandvelocity-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| ODT       | Velocity |     PDF/XHTML      | **[odtandvelocity.converters-1.0.2-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

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
  <version>1.0.2</version>
</dependency>
```


If you wish use MS PowerPoint pptx :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.pptx</artifactId>
  <version>1.0.2</version>
</dependency>
```

If you wish use OpenOffice odt :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.odt</artifactId>
  <version>1.0.2</version>
</dependency>
```

#### Template Dependency ####

If you wish use Freemarker :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.freemarker</artifactId>
  <version>1.0.2</version>
</dependency>
```

If you wish use Velocity :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.velocity</artifactId>
  <version>1.0.2</version>
</dependency>
```

#### Converter Dependency ####

If you wish convert docx 2 PDF/XHTML :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.docx.xwpf</artifactId>
  <version>1.0.2</version>
</dependency>
```

If you wish convert odt 2 PDF/XHTML  :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.odt.odfdom</artifactId>
  <version>1.0.2</version>
</dependency>
```

#### Tools Dependency ####

Tools gives you the capability to generate report with Java main command. It is used by xdrtools.bat and xdrtools.sh for the preview feature in the [XDocReport MS Word macro](http://code.google.com/p/xdocreport/wiki/DocxReportingQuickStart) and [XDocReport OOo extension macro](http://code.google.com/p/xdocreport/wiki/ODTReportingQuickStart).

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.tools</artifactId>
  <version>1.0.2</version>
</dependency>
```

#### REST/SOAP Remoting Resources Dependency ####

API :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources</artifactId>
  <version>1.0.2</version>
</dependency>
```

JAX-RS/JAX-WS Client :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.client</artifactId>
  <version>1.0.2</version>
</dependency>
```

JAX-RS/JAX-WS Server:

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.server</artifactId>
  <version>1.0.2</version>
</dependency>
```

## New Features ##

### docx->pdf converter ###

  * Fix problem "alignement is lost after pdf generation" see [issue 207](http://code.google.com/p/xdocreport/issues/detail?id=207)

### Field Metadata annotation ###
  * Manage FieldsMetadata with @annotations : see
[issue 180](https://code.google.com/p/xdocreport/issues/detail?id=180)

It's now very easy to customize the behaviour of a fieldmedata by adding an annotation on the field of a Pojo.

```java

@FieldMetadata(syntaxKind="html", description="HTML Formatted long text")
public String getComment() {
return comment;
}
```

### Google App Engine ###

See [XDocReportGAE](http://code.google.com/p/xdocreport/wiki/XDocReportGAE).

  * Fix "itext-gae instead of itext" see [issue 197](http://code.google.com/p/xdocreport/issues/detail?id=197).
  * Fix "Generating PDF from ODT in appengine" see [issue 209](http://code.google.com/p/xdocreport/issues/detail?id=209).
  * "Specify encoding via GET parameters for converter" : see [issue 212](http://code.google.com/p/xdocreport/issues/detail?id=212).
  * "xml parse exception when using SyntaxKind.NoEscape" : see [issue 213](http://code.google.com/p/xdocreport/issues/detail?id=213).
  * "XDocReport doesn't merge the fields with docx, odt which are zipped with Java code by using ZipEntry name '\' instead of using '/'": see [issue 234](http://code.google.com/p/xdocreport/issues/detail?id=234).

Text styling :

  * Manage text-align for docx text styling : see
[issue 243](https://code.google.com/p/xdocreport/issues/detail?id=243)
  * Manage text-align for odt text styling : see
[issue 244](https://code.google.com/p/xdocreport/issues/detail?id=244)