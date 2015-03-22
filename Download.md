XDocReport is **modular** (designed with OSGi fragment) for 3 kinds of components :

  * **document**: you can document kind (docx, odt etc) for reporting feature.
  * **template**: you can choose the template engine Freemarker, Velocity for reporting feature.
  * **converter** : you can choose the converter (odt->pd, docx->pdf, etc) implementation.

Modular architecture gives you the capability to add new feature (ex: implement your own converter) to XDocReport but it requires that you must set the well JARs (converter implementation, freemarker template engine implementation etc) in your classpath.

It could be complex to know which XDocReport and dependencies JARs you must add in your classpath. To resolve this problem, you can

  * download samples.
  * use maven.

# Download Samples #

The [Download](http://code.google.com/p/xdocreport/downloads/list) link gives you several samples about using XDocReport.

## Tools ##

If you wish benefit from [macro MS Word](http://code.google.com/p/xdocreport/wiki/DocxReportingQuickStart) or [OpenOffice oxt](http://code.google.com/p/xdocreport/wiki/ODTReportingQuickStart) to help you to design report and benefit from preview, please download **fr.opensagres.xdocreport.document.tools-xxx-distribution.zip**

## Reporting ##

Here zip that you can [download](http://code.google.com/p/xdocreport/downloads/list) if you wish to use reporting feature with or without converter:

| Document Kind | Template Engine | Converter | ZIP to download |
|:--------------|:----------------|:----------|:----------------|
| DOCX          |  Velocity       | -         | docxandvelocity-xxx-sample.zip |
| DOCX          |  Freemarker     | -         | docxandfreemarker-xxx-sample.zip |
| DOCX          |  Velocity       | PDF/XHTML | docxandvelocity.converters-xxx-sample.zip |
| PPTX          |  Velocity       | -         | pptxandvelocity-xxx-sample.zip |
| ODT           |  Velocity       | -         | odtandvelocity-xxx-sample.zip |
| ODT           |  Freemarker     | -         | odtandfreemarker-xxx-sample.zip |
| ODT           |  Velocity       | PDF/XHTML | odtandvelocity.converters-xxx-sample.zip |

## Converter ##

Here zip that you can [download](http://code.google.com/p/xdocreport/downloads/list) if you wish to convert only docx or odt to another format :

| Document Kind | Converter | ZIP to download |
|:--------------|:----------|:----------------|
| DOCX          | PDF/XHTML | docx.converters-xxx-sample.zip |
| ODT           | PDF/XHTML | odt.converters-xxx-sample.zip |

# Maven repository artifacts #

The preferred method of "using" XDocReport is via Maven artifact repositories. Many build tools are able to interoperate with Maven repositories including Maven, Ivy, Gradle, etc.

XDocReport **releases** are available on [central Maven Repository](http://search.maven.org/#search|ga|1|xdocreport).

For people who want to use -SNAPSHOT dependencies (non stable). You will have to add :
```
<repository>
	<id>sonatype</id>
	<snapshots>
		<enabled>true</enabled>
	</snapshots>
	<url>http://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
```

Here a sample pom to download XDocReport 1.0.3-SNAPSHOT :

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>test</groupId>
	<artifactId>test</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<repositories>
		<repository>
			<id>sonatype</id>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
			<url>http://oss.sonatype.org/content/repositories/snapshots/</url>
		</repository>
	</repositories>

	<dependencies>
		<dependency>
			<groupId>fr.opensagres.xdocreport</groupId>
			<artifactId>xdocreport</artifactId>
			<version>1.0.3-SNAPSHOT</version>
		</dependency>

	</dependencies>

</project>
```

Note that, here it will just download the XDocReport JAR without dependencies.

See : http://maven.apache.org/pom.html#Repositories for full documentation of this tag.

Alternatively, advanced maven users may update their settings.xml.

As explained below XDocReport is modular. You must choose :

  * Your Document dependency
  * Your Template dependency
  * Your Converter dependency

We will use XDOCREPORT\_VERSION constant for the XDocReport Version (ex: 1.0.0).

## Document Dependency ##

If you wish use MS Word docx :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.docx</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```


If you wish use MS PowerPoint pptx :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.pptx</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

If you wish use OpenOffice odt :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.odt</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

## Template Dependency ##

If you wish use Freemarker :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.freemarker</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

If you wish use Velocity :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.template.velocity</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

## Converter Dependency ##

If you wish convert docx 2 PDF/XHTML by using Apache POI + iText :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.docx.xwpf</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

If you wish convert docx 2 PDF/XHTML by using docx4j + XSL-FO and FOP:

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>  <artifactId>fr.opensagres.xdocreport.converter.docx.docx4j</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

If you wish convert odt 2 PDF/XHTML  :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.converter.odt.odfdom</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

#### Tools Dependency ####

Tools gives you the capability to generate report with Java main command. It is used by xdrtools.bat and xdrtools.sh for the preview feature in the [XDocReport MS Word macro](http://code.google.com/p/xdocreport/wiki/DocxReportingQuickStart) and [XDocReport OOo extension macro](http://code.google.com/p/xdocreport/wiki/ODTReportingQuickStart).

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.document.tools</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

#### REST/SOAP Remoting Resources Dependency ####

API :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

JAX-RS/JAX-WS Client :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.client</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

JAX-RS/JAX-WS Server:

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>fr.opensagres.xdocreport.remoting.resources.services.server</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```