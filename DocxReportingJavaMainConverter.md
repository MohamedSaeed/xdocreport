# Converter - Java Main #

XDocReport give you the capability to **convert generated docx report to another format** like **XHTML** and **PDF**.

XDocReport converter are modular, it means that you can choose the converter implementation and implement your own converter if you need.

XDocReport provides several converter implementation (2 PDF via FOP with XSL, 2 PDF via IText with POI-HWPF...) After developing several implementation we choose to keep **2 PDF via IText with POI-HWPF** because it's more performant and it's more easy to develop Java code instead of developing XSL.

If you wish to :

  * use Docx with Freemarker and convert report To PDF/XHTML, you could [download](http://code.google.com/p/xdocreport/downloads/list) 	**docxandfreemarker.converter--`*`-sample.zip**.
  * use Docx with Velocity and convert report To PDF/XHTML, you could [download](http://code.google.com/p/xdocreport/downloads/list) **docxandvelocity.converter-`*`-sample.zip**.

# 1. Add XDocReport JARs #

Add first you must add in your Java project the same JARs explained at [Java Main section](DocxReportingJavaMain#1._Add_XDocReport_JARs.md).

## Docx Converter JARs ##

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.converter.docx.xwpf | PDF/XHTML XDocReport converter implementation | Fragment -> fr.opensagres.xdocreport.converter |

## Docx Converter JARs ##

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| org.apache.poi.xwpf.converter | POI converter | Bundle |
| fr.opensagres.xdocreport.itext.extension | XHTML+IText extension |

## POI JARs ##

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| poi-3.7.jar | POI |
| poi-ooxml-3.7.jar | POI OOXML |
| xmlbeans-2.3.0.jar | XML Beans |
| dom4j-1.6.1.jar | DOM4J |
| ooxml-schemas-1.1.jar | OOXML Schemas |

## IText JARs ##

If you wish generate PDF, add IText JARs :

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| [itext-2.1.7.jar](http://search.maven.org/#artifactdetails|com.lowagie|itext|2.1.7|jar) | IText PDF |  Bundle |

# XHTML #

Instead of doing :

```
report.process(context, out);
```

you must do :

```
Options options = Options.getTo(ConverterTypeTo.XHTML).via(ConverterTypeVia.POI);
report.convert(context, options, out);
```

## 2. Simple field 2 XHTML ##

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class DocxProjectWithVelocity2XHTML {

	public static void main(String[] args) {
		try {
			// 1) Load Docx file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = DocxProjectWithVelocity2XHTML.class
					.getResourceAsStream("DocxProjectWithVelocity.docx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);

			// 3) Generate report by merging Java model with the Docx
			OutputStream out = new FileOutputStream(new File(
					"DocxProjectWithVelocity_Out.html"));
			// report.process(context, out);
			Options options = Options.getTo(ConverterTypeTo.XHTML).via(
					ConverterTypeVia.XWPF);
			report.convert(context, options, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

## 3. List field 2 XHTML ##

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class DocxProjectWithVelocityListXHTML {

	public static void main(String[] args) {
		try {
			// 1) Load Docx file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = DocxProjectWithVelocityListXHTML.class
					.getResourceAsStream("DocxProjectWithVelocityList.docx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			// Register project
			Project project = new Project("XDocReport");
			context.put("project", project);
			// Register developers list
			List<Developer> developers = new ArrayList<Developer>();
			developers.add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com"));
			developers.add(new Developer("Leclercq", "Pascal", "pascal.leclercq@gmail.com"));
			context.put("developers", developers);

			// 3) Create fields metadata to manage lazy loop (#forech velocity) for table row. 
			FieldsMetadata metadata = new FieldsMetadata();
			metadata.addFieldAsList("developers.Name");
			metadata.addFieldAsList("developers.LastName");
			metadata.addFieldAsList("developers.Mail");
			report.setFieldsMetadata(metadata);
			
			// 4) Generate report by merging Java model with the Docx
			OutputStream out = new FileOutputStream(new File(
					"DocxProjectWithVelocityList_Out.html"));
			//report.process(context, out);
			Options options = Options.getTo(ConverterTypeTo.XHTML).via(
					ConverterTypeVia.XWPF);
			report.convert(context, options, out);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

# 2 PDF via IText with POI-XWPF #

Instead of doing :

```
report.process(context, out);
```

you must do :

```
Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF);
report.convert(context, options, out);
```

## Simple field 2 PDF ##

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class DocxProjectWithVelocity2PDF {

	public static void main(String[] args) {
		try {
			// 1) Load Docx file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = DocxProjectWithVelocity2PDF.class
					.getResourceAsStream("DocxProjectWithVelocity.docx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);

			// 3) Generate report by merging Java model with the Docx
			OutputStream out = new FileOutputStream(new File(
					"DocxProjectWithVelocity_Out.pdf"));
			// report.process(context, out);
			Options options = Options.getTo(ConverterTypeTo.PDF).via(
					ConverterTypeVia.XWPF);
			report.convert(context, options, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

## List field 2 PDF ##

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class DocxProjectWithVelocityList2PDF {

	public static void main(String[] args) {
		try {
			// 1) Load Docx file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = DocxProjectWithVelocityList2PDF.class
					.getResourceAsStream("DocxProjectWithVelocityList.docx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			// Register project
			Project project = new Project("XDocReport");
			context.put("project", project);
			// Register developers list
			List<Developer> developers = new ArrayList<Developer>();
			developers.add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com"));
			developers.add(new Developer("Leclercq", "Pascal", "pascal.leclercq@gmail.com"));
			context.put("developers", developers);

			// 3) Create fields metadata to manage lazy loop (#forech velocity) for table row. 
			FieldsMetadata metadata = new FieldsMetadata();
			metadata.addFieldAsList("developers.Name");
			metadata.addFieldAsList("developers.LastName");
			metadata.addFieldAsList("developers.Mail");
			report.setFieldsMetadata(metadata);
			
			// 4) Generate report by merging Java model with the Docx
			OutputStream out = new FileOutputStream(new File(
					"DocxProjectWithVelocityList_Out.pdf"));
			// report.process(context, out);
			Options options = Options.getTo(ConverterTypeTo.PDF).via(
					ConverterTypeVia.XWPF);
			report.convert(context, options, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```