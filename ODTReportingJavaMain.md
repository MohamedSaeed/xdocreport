# ODT Reporting in Java Main #

This section explains step by step how generate a report created with OpenOffice ODT by using Velocity syntax to set fields to replace.

We will create a basic odt document _ODTProjectWithVelocity.odt_ :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity.png)

The _$project.Name_ is an input field and project is Java model Project class which define _Project#getName()_. The field name start with _project_ because we will register the Java Model with _project_ key in the context like this :

```
Project project = new Project("XDocReport");
context.put("project", project);
```

After report process we will generate the document _ODTProjectWithVelocity\_Out.odt_ :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_Out.png)

Steps section are :

  1. [Add XDocReport JARs](#1._Add_XDocReport_JARs.md) in your classpath project.
  1. [Create Java model](#2._Create_Java_model.md) create your Java model.
  1. [Design odt report](#3._Design_ODT_report.md) design your odt report with OpenOffice by using Velocity syntax to set fields to replace.
  1. [Generate odt report](#4._Generate_odt_report.md) generate odt report by using XDocReport API.

If you wish to :

  * use ODT with Freemarker, you could [download](http://code.google.com/p/xdocreport/downloads/list) 	**odtandfreemarker-`*`-sample.zip**.
  * use ODT with Velocity, you could [download](http://code.google.com/p/xdocreport/downloads/list) **odtandvelocity-`*`-sample.zip**.

# 1. Add XDocReport JARs #

XDocReport is very modular. It means that you can choose your document type (docx, odt...) as source report and use template engine syntax to set fields name (Freemarker/Velocity...).

In our case we will generate report from odt with Velocity. So we need to set well JARs in your classpath. Here screen of your Eclipse project (if you are using Eclipse) after adding XDocReport JARs :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_AddJARs.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_AddJARs.png)

## Required JARs ##

For any document type and template engine you need to add following JARs :

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.core | XDocReport Core | Bundle |
| fr.opensagres.xdocreport.document | Document type API  | Bundle |
| fr.opensagres.xdocreport.template | Template engine API | Bundle |
| fr.opensagres.xdocreport.converter | Converter API | Bundle |
| commons-io-1.4 | Commons IO |

## ODT Document JAR ##

In this section we wish using odt as document source for report, so you need to add ODT document implementation JAR:

| **JARs name** | **Description** | **OSGi Bundle/Fragment** |
|:--------------|:----------------|:-------------------------|
| fr.opensagres.xdocreport.document.odt | ODT Document implementation  | Fragment -> fr.opensagres.xdocreport.document |

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

Now you can create your Java model that you wish use in your odt report. In this section we will use Java Project model like this :

```
package fr.opensagres.xdocreport.samples.odtandvelocity.model;

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

# 3. Design ODT report #

At this step you can create your odt report with OpenOffice with the content :

`Project: $project.Name`

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity.png)

For the field to replace _$project.Name_, it is advice to use MergeField (Champs de fusion) and not to type directly _$project.Name_ in your documentation (TODO : explain why).

You can note for this sample that you can style field. If you don't know how create Mergefield, please see [ODT Design Report](ODTDesignReport.md) section.

# 4. Generate odt report #

Once you have create odt, save it in your project. In this sample the odt report was saved with **ODTProjectWithVelocity.odt** name in the **fr.opensagres.xdocreport.samples.odtandvelocity** package of the Java project.

Create Java class ODTProjectWithVelocity to load **ODTProjectWithVelocity.odt** and generate report in file **ODTProjectWithVelocity\_Out.odt** like this :

```
package fr.opensagres.xdocreport.samples.odtandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class ODTProjectWithVelocity {

  public static void main(String[] args) {
    try {
      // 1) Load ODT file by filling Velocity template engine and cache it to the registry
      InputStream in = ODTProjectWithVelocity.class.getResourceAsStream("ODTProjectWithVelocity.odt");
      IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);

      // 2) Create context Java model
      IContext context = report.createContext();
      Project project = new Project("XDocReport");
      context.put("project", project);

      // 3) Generate report by merging Java model with the ODT
      OutputStream out = new FileOutputStream(new File("ODTProjectWithVelocity_Out.odt"));
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

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_Workspace.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_Workspace.png)

Run **ODTProjectWithVelocity** Java class and **ODTProjectWithVelocity\_Out.odt** will be generated in your project home :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_WorkspaceOut.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_WorkspaceOut.png)

If you open  **ODTProjectWithVelocity\_Out.odt** you will see :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocity_Out.png)

# More features #

  * [List Field](ODTReportingJavaMainListField.md), if you wish manage loop for fields.
  * [Converter (PDF/XHTML)](ODTReportingJavaMainConverter.md), if you wish convert report 2 PDF/XHTML.