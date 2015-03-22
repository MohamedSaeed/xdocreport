# Converter - Java Main #

XDocReport give you the capability to **convert generated odt report to another format** like **XHTML** and **PDF**.

XDocReport converter are modular, it means that you can choose the converter implementation and implement your own converter if you need.

XDocReport provides several converter implementation (2 PDF via FOP with XSL, 2 PDF via IText with ODFDOM...) After developing several implementation we choose to keep **2 PDF via IText with ODFDOM** because it's more performant and it's more easy to develop Java code instead of developing XSL.

If you wish to :

  * use ODT with Freemarker and convert report To PDF/XHTML, you could [download](http://code.google.com/p/xdocreport/downloads/list) 	**odtandfreemarker.converter-`*`-sample.zip**.
  * use ODT with Velocity and convert report To PDF/XHTML, you could [download](http://code.google.com/p/xdocreport/downloads/list) **odtandvelocity.converter-`*`-sample.zip**.


# 1. Add XDocReport JARs #

Add first you must add in your Java project the same JARs explained at [Java Main section](ODTReportingJavaMain#1._Add_XDocReport_JARs.md).

## ODT Converter JARs ##

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.converter.odt.odfdom | PDF/XHTML XDocReport converter implementation | Fragment -> fr.opensagres.xdocreport.converter |

## ODT Converter JARs ##

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| org.odftoolkit.odfdom.converter | ODFDOM converter | Bundle |
| fr.opensagres.xdocreport.itext.extension | XHTML+IText extension |

## ODFDOM JARs ##

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| odfdom-java-0.8.7.jar | ODFDOM |

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
Options options = Options.getTo(ConverterTypeTo.XHTML).via(ConverterTypeVia.ODFDOM);
report.convert(context, options, out);
```

## 2. Simple field 2 XHTML ##

```
package fr.opensagres.xdocreport.samples.odtandvelocity;

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
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

import org.odftoolkit.odfdom.converter.xhtml.XHTMLOptions;

public class ODTProjectWithVelocity2XHTML {

	public static void main(String[] args) {
		try {
			// 1) Load ODT file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = ODTProjectWithVelocity2XHTML.class
					.getResourceAsStream("ODTProjectWithVelocity.odt");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);

			// 3) Generate report by merging Java model with the ODT
			OutputStream out = new FileOutputStream(new File(
					"ODTProjectWithVelocity_Out.html"));
			Options options = Options.getTo(ConverterTypeTo.XHTML).via(
					ConverterTypeVia.ODFDOM);
			// you can pass an optional XHTMLOptions instance
			options.subOptions(XHTMLOptions.create().generateCSSComments(true));
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
package fr.opensagres.xdocreport.samples.odtandvelocity;

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
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class ODTProjectWithVelocityListXHTML {

	public static void main(String[] args) {
		try {
			// 1) Load ODT file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = ODTProjectWithVelocityListXHTML.class
					.getResourceAsStream("ODTProjectWithVelocityList.odt");
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
			
			// 4) Generate report by merging Java model with the ODT
			OutputStream out = new FileOutputStream(new File(
					"ODTProjectWithVelocityList_Out.html"));
			Options options = Options.getTo(ConverterTypeTo.XHTML).via(
					ConverterTypeVia.ODFDOM);
			report.convert(context, options, out);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

# 2 PDF via IText with ODFDOM #

Instead of doing :

```
report.process(context, out);
```

you must do :

```
Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM);
report.convert(context, options, out);
```

## Simple field 2 PDF ##

```
package fr.opensagres.xdocreport.samples.odtandvelocity;

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
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

import org.odftoolkit.odfdom.converter.itext.PDFViaITextOptions;

public class ODTProjectWithVelocity2PDF {

	public static void main(String[] args) {
		try {
			// 1) Load ODT file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = ODTProjectWithVelocity2PDF.class
					.getResourceAsStream("ODTProjectWithVelocity.odt");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);

			// 3) Generate report by merging Java model with the ODT
			OutputStream out = new FileOutputStream(new File(
					"ODTProjectWithVelocity_Out.pdf"));
			Options options = Options.getTo(ConverterTypeTo.PDF).via(
					ConverterTypeVia.ODFDOM);
			// you can pass an optional PDFViaITextOptions instance
			options.subOptions(PDFViaITextOptions.create().fontEncoding("windows-1252"));
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
package fr.opensagres.xdocreport.samples.odtandvelocity;

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
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class ODTProjectWithVelocityList2PDF {

	public static void main(String[] args) {
		try {
			// 1) Load ODT file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = ODTProjectWithVelocityList2PDF.class
					.getResourceAsStream("ODTProjectWithVelocityList.odt");
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
			
			// 4) Generate report by merging Java model with the ODT
			OutputStream out = new FileOutputStream(new File(
					"ODTProjectWithVelocityList_Out.pdf"));
			Options options = Options.getTo(ConverterTypeTo.PDF).via(
					ConverterTypeVia.ODFDOM);
			report.convert(context, options, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```