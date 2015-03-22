# PPTX Reporting in Java Main #

This section explains step by step how to generate a report created with MS PowerPoint pptx by using Velocity syntax to set fields to replace. You can download [pptxandvelocity-\*-sample.zip](http://code.google.com/p/xdocreport/downloads) which is the project explained in this section.

PPTX reporting follow the same idea than [PPTX reporting](PPTXReportingJavaMain.md), except than you don't use Megefield to set field (because Mergefield doesn't exist in pptx), but you type directly your field name in the pptx slide. PPTX reporting is less powerfull than pptx reporting because you cannot use if, loop directives (because text typed are escaped by MS PowerPoint and XDocReport unespace only the field name and not the directive).

Here we will create a basic pptx document _PPTXProjectWithVelocity.pptx :_

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity.png)

The _$project.Name_ is a text and project is Java model Project class which define _Project#getName()_. The field name start with _project_ because we will register the Java Model with _project_ key in the context like this :

```
Project project = new Project("XDocReport");
context.put("project", project);
```

After report process we will generate the document _PPTXProjectWithVelocity\_Out.pptx_ :

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_Out.png)

Steps section are :

  1. [Add XDocReport JARs](#1._Add_XDocReport_JARs.md) in your classpath project.
  1. [Create Java model](#2._Create_Java_model.md) create your Java model.
  1. [Design pptx report](#3._Design_pptx_report.md) design your pptx report with MS PowerPoint by using Velocity syntax to set fields to replace.
  1. [Generate pptx report](#4._Generate_pptx_report.md) generate pptx report by using XDocReport API.

If you wish to :

  * use PPTX with Freemarker, you could [download](http://code.google.com/p/xdocreport/downloads/list) 	**pptxandfreemarker-`*`-sample.zip**.
  * use PPTX with Velocity, you could [download](http://code.google.com/p/xdocreport/downloads/list) **pptxandvelocity-`*`-sample.zip**.


# 1. Add XDocReport JARs #

XDocReport is very modular. It means that you can choose your document type (odt, pptx...) as source report and use template engine syntax to set fields name (Freemarker/Velocity...).

In our case we will generate report from pptx with Velocity. So we need to set well JARs in your classpath.

## Required JARs ##

For any document type and template engine you need to add following JARs :

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.core | XDocReport Core | Bundle |
| fr.opensagres.xdocreport.document | Document type API  | Bundle |
| fr.opensagres.xdocreport.template | Template engine API | Bundle |
| fr.opensagres.xdocreport.converter | Converter API | Bundle |

## PPTX Document JAR ##

In this section we wish using pptx as document source for report, so you need to add PPTX document implementation JAR:

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.document.pptx | PPTX Document implementation  | Fragment -> fr.opensagres.xdocreport.document |

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

Now you can create your Java model that you wish use in your pptx report. In this section we will use Java Project model like this :

```
package fr.opensagres.xdocreport.samples.pptxandvelocity.model;

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

# 3. Design PPTX report #

At this step you can create your pptx report with MS PowerPoint with the content :

```
Project: $project.Name
```

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity.png)

For the field to replace _$project.Name_, in the pptx case, type directly the field**in your document.**

You can note for this sample that you can style field.

# 4. Generate PPTX report #

Once you have create pptx, save it in your project. In this sample the pptx report was saved with **PPTXProjectWithVelocity.pptx** name in the **fr.opensagres.xdocreport.samples.pptxandvelocity** package of the Java project.

Create Java class PPTXProjectWithVelocity to load **PPTXProjectWithVelocity.pptx** and generate report in file **PPTXProjectWithVelocity\_Out.pptx** like this :

```
package fr.opensagres.xdocreport.samples.pptxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.pptxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class PPTXProjectWithVelocity {

  public static void main(String[] args) {
    try {
      // 1) Load PPTX file by filling Velocity template engine and cache it to the registry
      InputStream in = PPTXProjectWithVelocity.class.getResourceAsStream("PPTXProjectWithVelocity.pptx");
      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);

      // 2) Create context Java model
      IContext context = report.createContext();
      Project project = new Project("XDocReport");
      context.put("project", project);

      // 3) Generate report by merging Java model with the PPTX
      OutputStream out = new FileOutputStream(new File("PPTXProjectWithVelocity_Out.pptx"));
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

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_Workspace.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_Workspace.png)

Run **PPTXProjectWithVelocity** Java class and **PPTXProjectWithVelocity\_Out.pptx** will be generated in your project home :

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_WorkspaceOut.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_WorkspaceOut.png)

If you open  **PPTXProjectWithVelocity\_Out.pptx** you will see :

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocity_Out.png)

# 5. Test Performance #

XDocReport use Velocity/Freemarker cache to improve a lot the performance. To test performance, create  fr.opensagres.xdocreport.samples.pptxandvelocity.**PPTXProjectWithVelocityTestPerf** class like this which display time process for the first and second report generation :

```
package fr.opensagres.xdocreport.samples.pptxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.pptxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class PPTXProjectWithVelocityTestPerf {

	public static void main(String[] args) {
		try {

			String reportId = "MyId";

			// 1) Load PPTX file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = PPTXProjectWithVelocityTestPerf.class
					.getResourceAsStream("PPTXProjectWithVelocity.pptx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, reportId, TemplateEngineKind.Velocity);

			// 2) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);

			// 3) Generate report by merging Java model with the PPTX
			OutputStream out = new FileOutputStream(new File(
					"PPTXProjectWithVelocity_Out.pptx"));
			long start = System.currentTimeMillis();
			report.process(context, out);
			System.out.println("Report processed in "
					+ (System.currentTimeMillis() - start) + " ms");

			// 4) Regenerate report
			IXDocReport report2 = XDocReportRegistry.getRegistry().getReport(
					reportId);
			out = new FileOutputStream(new File(
					"PPTXProjectWithVelocity_Out.pptx"));
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

Run **PPTXProjectWithVelocityTestPerf** Java class and **PPTXProjectWithVelocity\_Out.pptx** will be generated twice and console display time process :

```
Report processed in 250 ms
Report processed in 16 ms
```

You can notice that the second report generation is very quick. This time is explained with use of velocity cache which cache the XML entry to merge with Java model.

Here report is loaded by filling an id :
```
String reportId = "MyId";

// 1) Load PPTX file by filling Velocity template engine and cache
// it to the registry
InputStream in = PPTXProjectWithVelocityTestPerf.class.getResourceAsStream("PPTXProjectWithVelocity.pptx");
```

The report is retrieved form the registry with this code :

```
IXDocReport report2 = XDocReportRegistry.getRegistry().getReport(reportId);
```

# 6. More features #

  * [List Field](PPTXReportingJavaMainListField.md), if you wish manage loop for fields.