# XDocReport 1.0.4 #

Released February 20, 2014

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

The XDocReport team provides release bundles hosted on the **[Downloads](http://code.google.com/p/xdocreport/downloads/list)** tab in ZIP. As XDocReport is modular, it exists a ZIP per configuration. For instance if you wish manage Docx with Velocity and convert your report to PDF, XHTML, you must download **[docxandvelocity.converters-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)**. This ZIP contains required XDocReport and dependant (POI,...) JARs and source code with sample.

**Attention for PDF conversion**: for license raison, **we cannot set itext used for PDF in the samples** ZIP. So if you wish manage PDF conversion , please [download itext-2.1.7.jar](http://search.maven.org/#artifactdetails|com.lowagie|itext|2.1.7|jar) at hand on the Maven repository.

Here an array of XDocReport download according to you wish to do :

#### Microsoft Office ####

| **Document** | **Template** | **Converter** |
|:-------------|:-------------|:--------------|
| Docx       | Freemarker |     | **[docxandfreemarker-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |       | **[docxandvelocity-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| Docx       | Velocity |     PDF/XHTML      | **[docxandvelocity.converters-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| PPTX       | Velocity |              | **[pptxandvelocity-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

#### Open/Libre Office ####

| **Document** | **Template** | **Converter** |
|:-------------|:-------------|:--------------|
| ODP       | Velocity |      | **[odpandvelocity-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| ODT       | Velocity |      | **[odtandvelocity-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |
| ODT       | Velocity |     PDF/XHTML      | **[odtandvelocity.converters-1.0.4-sample.zip](http://code.google.com/p/xdocreport/downloads/list)** |

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
  <version>1.0.4</version>
</dependency>
```


If you wish use MS PowerPoint pptx :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.pptx</artifactId>
  <version>1.0.4</version>
</dependency>
```

If you wish use OpenOffice odt :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.odt</artifactId>
  <version>1.0.4</version>
</dependency>
```

#### Template Dependency ####

If you wish use Freemarker :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.freemarker</artifactId>
  <version>1.0.4</version>
</dependency>
```

If you wish use Velocity :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.velocity</artifactId>
  <version>1.0.4</version>
</dependency>
```

#### Converter Dependency ####

If you wish convert docx 2 PDF/XHTML :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.docx.xwpf</artifactId>
  <version>1.0.4</version>
</dependency>
```

If you wish convert odt 2 PDF/XHTML  :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.odt.odfdom</artifactId>
  <version>1.0.4</version>
</dependency>
```

#### Tools Dependency ####

Tools gives you the capability to generate report with Java main command. It is used by xdrtools.bat and xdrtools.sh for the preview feature in the [XDocReport MS Word macro](http://code.google.com/p/xdocreport/wiki/DocxReportingQuickStart) and [XDocReport OOo extension macro](http://code.google.com/p/xdocreport/wiki/ODTReportingQuickStart).

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.tools</artifactId>
  <version>1.0.4</version>
</dependency>
```

#### REST/SOAP Remoting Resources Dependency ####

API :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources</artifactId>
  <version>1.0.4</version>
</dependency>
```

JAX-RS/JAX-WS Client :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.client</artifactId>
  <version>1.0.4</version>
</dependency>
```

JAX-RS/JAX-WS Server:

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.server</artifactId>
  <version>1.0.4</version>
</dependency>
```

## New Features ##

  * "Switch source Java 1.6 to 1.5" : see  [issue 324](https://code.google.com/p/xdocreport/issues/detail?id=324) for more information.
  * "Generate <text:span text:style-name="XDocReport\_EmptyText" > instead of 

&lt;text:span &gt;

 for odt text styling." : see [issue 325](https://code.google.com/p/xdocreport/issues/detail?id=325) for more information.
  * "Give the capability to configure iText PdfWriter with IPdfWriterConfiguration." : see [issue 326](https://code.google.com/p/xdocreport/issues/detail?id=326) for more information.
  * "Give the capability to configure SAX ContentHandler of POI XHTML converter": see [issue 327](https://code.google.com/p/xdocreport/issues/detail?id=327) for more information.
  * "Customize cacheReport with Web support" : see [issue 358](https://code.google.com/p/xdocreport/issues/detail?id=358) for more information.
  * "Example on utilizing xdocreport with spring MVC and GAE" : see [issue 350](https://code.google.com/p/xdocreport/issues/detail?id=350) for more information.
  * "Simplify the use of Freemarker macro" :  see [issue 333](https://code.google.com/p/xdocreport/issues/detail?id=333) for more information.
  * "odt->pdf converter problem with "negative" paragraph first line hanging indent " : see [issue 366](https://code.google.com/p/xdocreport/issues/detail?id=366) for more information.
  * "NPE for docx->pdf converter with unknown hyperlink id." : see [issue 344](https://code.google.com/p/xdocreport/issues/detail?id=344) for more information.