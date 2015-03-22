# Docx Reporting in Java Main #

This section explains step by step how generate a report created with MS Word docx by using Velocity syntax to set fields to replace. You can download [docxandvelocity-\*-sample.zip](http://code.google.com/p/xdocreport/downloads) which is the project explained in this section.

We will create a basic docx document _DocxProjectWithVelocity.docx_ :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png)

The _$project.Name_ is a merge field and project is Java model Project class which define _Project#getName()_. The field name start with _project_ because we will register the Java Model with _project_ key in the context like this :

```
Project project = new Project("XDocReport");
context.put("project", project);
```

After report process we will generate the document _DocxProjectWithVelocity\_Out.docx_ :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png)

Steps section are :

  1. [Add XDocReport JARs](#1._Add_XDocReport_JARs.md) in your classpath project.
  1. [Create Java model](#2._Create_Java_model.md) create your Java model.
  1. [Design docx report](#3._Design_Docx_report.md) design your docx report with MS Word by using Velocity syntax to set fields to replace.
  1. [Generate docx report](#4._Generate_docx_report.md) generate docx report by using XDocReport API.

If you wish to :

  * use Docx with Freemarker, you could [download](http://code.google.com/p/xdocreport/downloads/list) 	**docxandfreemarker-`*`-sample.zip**.
  * use Docx with Velocity, you could [download](http://code.google.com/p/xdocreport/downloads/list) **docxandvelocity-`*`-sample.zip**.


# 1. Add XDocReport JARs #

XDocReport is very modular. It means that you can choose your document type (odt, docx...) as source report and use template engine syntax to set fields name (Freemarker/Velocity...).

In our case we will generate report from docx with Velocity. So we need to set well JARs in your classpath. Here screen of your Eclipse project (if you are using Eclipse) after adding XDocReport JARs :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_AddJARs.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_AddJARs.png)

## Required JARs ##

For any document type and template engine you need to add following JARs :

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.core | XDocReport Core | Bundle |
| fr.opensagres.xdocreport.document | Document type API  | Bundle |
| fr.opensagres.xdocreport.template | Template engine API | Bundle |
| fr.opensagres.xdocreport.converter | Converter API | Bundle |

## Docx Document JAR ##

In this section we wish using docx as document source for report, so you need to add Docx document implementation JAR:

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.document.docx | Docx Document implementation  | Fragment -> fr.opensagres.xdocreport.document |

Note for OSGi : this JAR is a fragment linked to the bundle OSGi fr.opensagres.xdocreport.document.

## Velocity JARs ##

In this section we wish using Velocity as syntax to set fields, so you need to add Velocity template engine implementation and Velocity JARs.

### Velocity Template JAR ###

Add Velocity template engine implementation JAR:

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.template.velocity  | Velocity template engine implementation  | Fragment -> fr.opensagres.xdocreport.template |

Note for OSGi : this JAR is a fragment linked to the bundle OSGi fr.opensagres.xdocreport.template.

### Velocity JARs ###

Add Velocity JARs :

| **JARs name** | **Description** |
|:--------------|:----------------|
| velocity-1.7 | Velocity |
| commons-collections-3.2.1 | Commons Collection |
| commons-lang-2.4 | Commons Lang |
| oro-2.0.8 | ORO  |

# 2. Create Java model #

Now you can create your Java model that you wish use in your docx report. In this section we will use Java Project model like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity.model;

public class Project {

  private final String name;

  public Project(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
```

# 3. Design Docx report #

At this step you can create your docx report with MS Word with the content :

```
Project: $project.Name
```

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png)

For the field to replace _$project.Name_, **DON'T type directly the field**  in your document otherwise you could have some troubles because MS Word escape your content.

You must **use [MergeField](DocxDesignReport#Create_with_MS_Word.md)** (Champs de fusion) and **not type directly** _$project.Name_ in your documentation.

If you wish use another template engine directive to manage condition, loop... like :

  * [#if, #foreach](http://velocity.apache.org/engine/releases/velocity-1.7/user-guide.html#Directives) ... for [Velocity](http://velocity.apache.org/).
  * [[#if, [#list...](http://freemarker.sourceforge.net/docs/ref_directives.html) for [Freemarker](http://freemarker.sourceforge.net/).

It's the same thing than field,  : DON'T type directly the directive**in your document but use [MergeField](DocxDesignReport#Create_with_MS_Word.md).**

You can note for this sample that you can style field. If you don't know how create [MergeField](DocxDesignReport#Create_with_MS_Word.md), please see [Docx Design Report](DocxDesignReport.md) section.

NOT : for Freemarker, the square bracket syntax ([#if instead of <#if) must be used to have valid XML.

# 4. Generate docx report #

Once you have create docx, save it in your project. In this sample the docx report was saved with **DocxProjectWithVelocity.docx** name in the **fr.opensagres.xdocreport.samples.docxandvelocity** package of the Java project.

Create Java class DocxProjectWithVelocity to load **DocxProjectWithVelocity.docx** and generate report in file **DocxProjectWithVelocity\_Out.docx** like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class DocxProjectWithVelocity {

  public static void main(String[] args) {
    try {
      // 1) Load Docx file by filling Velocity template engine and cache it to the registry
      InputStream in = DocxProjectWithVelocity.class.getResourceAsStream("DocxProjectWithVelocity.docx");
      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);

      // 2) Create context Java model
      IContext context = report.createContext();
      Project project = new Project("XDocReport");
      context.put("project", project);

      // 3) Generate report by merging Java model with the Docx
      OutputStream out = new FileOutputStream(new File("DocxProjectWithVelocity_Out.docx"));
      report.process(context, out);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (XDocReportException e) {
      e.printStackTrace();
    }
  }
}
```

Your Eclipse project looks like this :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Workspace.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Workspace.png)

Run **DocxProjectWithVelocity** Java class and **DocxProjectWithVelocity\_Out.docx** will be generated in your project home :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WorkspaceOut.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WorkspaceOut.png)

If you open  **DocxProjectWithVelocity\_Out.docx** you will see :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png)

# 5. Test Performance #

XDocReport use Velocity/Freemarker cache to improve a lot the performance. To test performance, create  fr.opensagres.xdocreport.samples.docxandvelocity.**DocxProjectWithVelocityTestPerf** class like this which display time process for the first and second report generation :

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class DocxProjectWithVelocityTestPerf {

	public static void main(String[] args) {
		try {

			String reportId = "MyId";

			// 1) Load Docx file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = DocxProjectWithVelocityTestPerf.class
					.getResourceAsStream("DocxProjectWithVelocity.docx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, reportId, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);

			// 3) Generate report by merging Java model with the Docx
			OutputStream out = new FileOutputStream(new File(
					"DocxProjectWithVelocity_Out.docx"));
			long start = System.currentTimeMillis();
			report.process(context, out);
			System.out.println("Report processed in "
					+ (System.currentTimeMillis() - start) + " ms");

			// 4) Regenerate report
			IXDocReport report2 = XDocReportRegistry.getRegistry().getReport(
					reportId);
			out = new FileOutputStream(new File(
					"DocxProjectWithVelocity_Out.docx"));
			start = System.currentTimeMillis();
			report2.process(context, out);
			System.out.println("Report processed in "
					+ (System.currentTimeMillis() - start) + " ms");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

Run **DocxProjectWithVelocityTestPerf** Java class and **DocxProjectWithVelocity\_Out.docx** will be generated twice and console display time process :

```
Report processed in 250 ms
Report processed in 16 ms
```

You can notice that the second report generation is very quick. This time is explained with use of velocity cache which cache the XML entry to merge with Java model.

Here report is loaded by filling an id :
```
String reportId = "MyId";

// 1) Load Docx file by filling Velocity template engine and cache
// it to the registry
InputStream in = DocxProjectWithVelocityTestPerf.class.getResourceAsStream("DocxProjectWithVelocity.docx");
```

The report is retrieved form the registry with this code :

```
IXDocReport report2 = XDocReportRegistry.getRegistry().getReport(reportId);
```

# 6. More features #

  * [List Field](DocxReportingJavaMainListField.md), if you wish manage loop for fields.
  * [Converter (PDF/XHTML)](DocxReportingJavaMainConverter.md), if you wish convert report 2 PDF/XHTML.