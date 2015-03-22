# Quick Start #

Since [XDocReport 0.9.5](XDocReport095.md) provides XDocReport Tools and you can design your docx report with MS Word macro **XDocReport.dotm**. This section explains how to :

  * **design a docx report** with MS Word macro **XDocReport.dotm**.
  * **preview the generated docx report** with this macro .
  * **integrate your docx report in a Java Main** by replacing mergefield with  values coming from Java model.

The MS Word macro XDocReport.dotm **helps you to design** the docx report, but it is **not required** to generate docx reporting in your Java application. This macro is very useful to design docx report but it doesn't manage advanced reporting (like if condition, complex loop).

We answer to read [Docx Reporting](DocxReporting.md) section for more explanation about docx reporting to know which JARs you must use (Maven is adwiced for instance).

In this section we will design the following docx report by using the MS Word **XDocReport.dotm** macro :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview1.png)

We will preview with MS Word **XDocReport.dotm** macro and generate with Java main this docx :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview2.png)

# XDocReport Tools #

Since [XDocReport 0.9.5](XDocReport095.md), XDocReport provides Tools that you can [download](http://code.google.com/p/xdocreport/downloads/list) in the Zip **fr.opensagres.xdocreport.document.tools-xxx-distribution.zip**. This zip contains 3 folders :

  * **bin** : which contains the bat file **xdrtools.bat** which is required for the preview fetaure of the macro. This bat calls XDocReport JARs from the **lib** folder.
  * **lib** : which contains the whole JAR required to generate docx report with Freemarker or Velocity syntax.
  * **macro** : which contains the MS Word **XDocReport.dotm** macro.

Download this zip, and unzip it in a folder (in this section, zip is unzipped to **C:\XDocReport**):

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_UnzipTools.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_UnzipTools.png)

# Java Project quickstart #

## Create xdocreport-quickstart-docx ##

Create you a Java Project with your favorite IDE and copy/paste the JARs from the **lib** folder (coming from thz zip) to your Java Project and add JARs in your classpath. In this section **xdocreport-quickstart-docx** Eclipse Java project is created :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_EclipseProjectLib.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_EclipseProjectLib.png)

## Java Model ##

Create you your Java model classes. In this section we will create Project and Developer model. Create fr.opensagres.xdocreport.quickstart.model.**Project** class like this:

```
package fr.opensagres.xdocreport.quickstart.model;

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

Create fr.opensagres.xdocreport.quickstart.model.**Developer** class like this:

```
package fr.opensagres.xdocreport.quickstart.model;

public class Developer {

	private final String name;
	private final String lastName;
	private final String mail;

	public Developer(String name, String lastName, String mail) {
		this.name = name;
		this.lastName = lastName;
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMail() {
		return mail;
	}

}
```

## Generate XML fields ##

The MS Word macro need XML fields. You can generate it from the Java model. Create the fr.opensagres.xdocreport.quickstart.**GenerateXMLFields** class like this:

```
package fr.opensagres.xdocreport.quickstart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.quickstart.model.Developer;
import fr.opensagres.xdocreport.quickstart.model.Project;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class GenerateXMLFields {

  public static void main(String[] args) throws XDocReportException,IOException {
    
    // 1) Create FieldsMetadata by setting Velocity as template engine
    FieldsMetadata fieldsMetadata = new FieldsMetadata(TemplateEngineKind.Velocity.name());
		
    // 2) Load fields metadata from Java Class
    fieldsMetadata.load("project", Project.class);
    // Here load is called with true because model is a list of Developer.
    fieldsMetadata.load("developers", Developer.class, true);
    
    // 3) Generate XML fields in the file "project.fields.xml".
    // Extension *.fields.xml is very important to use it with MS Macro XDocReport.dotm
    // FieldsMetadata#saveXML is called with true to indent the XML.
    File xmlFieldsFile = new File("project.fields.xml");
    fieldsMetadata.saveXML(new FileOutputStream(xmlFieldsFile), true);
  }
}
```

If you run the Java main and you refresh your Eclipse Project, **project.fields.xml** is generated:

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_GenerateXMLFields.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_GenerateXMLFields.png)

Here the content of the project.fields.xml :

```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<fields templateEngineKind="Velocity" >
	<description><![CDATA[]]></description>
	<field name="project.Name" list="false" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
	<field name="developers.LastName" list="true" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
	<field name="developers.Mail" list="true" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
	<field name="developers.Name" list="true" imageName="" syntaxKind="">
		<description><![CDATA[]]></description>
	</field>
</fields>
```

# Design docx with MS Macro XDocReport.dotm #

At this step we can **design the docx report with the MS Word XDocReport.dotm** by using the generated XML fields **project.fields.xml**. Before using this macro you need to install and configure it.

## Installation and Configuration ##

### Installation ###

By default MS Word forbid to execute Macro, so if you open **XDocReport.dotm** with MS Word you will able to not to execute the macro. You have several means to fix this problem. The easy mean is to copy/paste the **macro/XDocReport.dotm** in the **STARTUP** folder of MS Word **C:\Documents and Settings\$USER\_NAME\Application Data\Microsoft\Word\STARTUP** where **$USER\_NAME** is your Windows user :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_CopyMacroToSTARTUP.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_CopyMacroToSTARTUP.png)

Close your MS Word if it is opened and reopen it (no need to open the XDocReport.dotm). When MS Word opens, it loads macros comming from the **STARTUP** folder. When your MS Word will be open you will see the following **XDocReport menu** :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroXDocReportMenu.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroXDocReportMenu.png)

### Configuration ###

The configuration of the MS Word **XDocReport.dotm** must be done for :

  * **using the XML fields dialog** which help you to insert quickly the mergefield of your model.
  * **preview** the result of the merge between the designed docx and values (coming from JSON data file).

#### Fields/Settings ####

The fields dialog display in a treeview the fields coming from
**xxx.fields.xml** files. You must configure the directory which contains those files. In our case we have one file **project.fields.xml** in our Eclipse Project **xdocreport-quickstart-docx**. Click on **Settings** button of **Design** category :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_DesignSettingsMenu.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_DesignSettingsMenu.png)

and **select the directory which contains the project.fields.xml** and click on **Save** button. If the directory is correct, you will see this message:

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroFieldsSettings.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroFieldsSettings.png)

If you click on **Fields** button of **Design** category :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_DesignFieldsMenu.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_DesignFieldsMenu.png)

the fields dialog opens and treeview is populated with fields coming from the **project.fields.xml** :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroFieldsDialog.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroFieldsDialog.png)

#### Preview/Settings ####

Since [XDocReport 0.9.5](http://code.google.com/p/xdocreport/wiki/XDocReport095), XDocReport provides the capability to generate a report with **Java command** by calling fr.opensagres.xdocreport.document.tools.**Main** class. The preview feature use this Java command by calling the **xdrtools.bat** which call fr.opensagres.xdocreport.document.tools.**Main**.

So you must **configure the directory which contains** the **xdrtools.bat**. To do that, click on **Settings** button of **Preview** category :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_PreviewSettingsMenu.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_PreviewSettingsMenu.png)

and **select the directory which contains** the **xdrtools.bat** and click on **Save** button. If the directory is correct, you will see this message:

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreviewSettings.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreviewSettings.png)

## Design docx with Fields Dialog ##

Open the **Fields Dialog** with **Fields** button on **Design** category. Now you can design your docx.

### Insert Field ###

If you wish insert a field to replace (mergefield is used), **double click on the treeview node** or click on **Insert Field** button :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertField.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertField.png)

### Insert Field List in a Table ###

If you wish generate table from a Java List, you must mark the fiels name as "list" with FieldsMetadata (see [here](http://code.google.com/p/xdocreport/wiki/DocxReportingJavaMainListFieldInTable) for more information. Here **developers.Mail** is marked as **list**, so you can insert directly the **developers.Mail** in a **table cell** like a simple field that we have done below (Insert Field section).

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInTable.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInTable.png)

### Insert Field List in a Paragraph ###

You can manage loop for fields anywhere (see [here](http://code.google.com/p/xdocreport/wiki/DocxReportingJavaMainListFieldAnywhere) for more information). To do that,select fields marked as list and click on "Insert List" button :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInPargraph1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInPargraph1.png)

the List Dialog opens and you can change the item name list if you want. The dialog preview the 3 mergefield which will be added (start loop + body loop+end loop) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInPargraph2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInPargraph2.png)

If you click on OK button, **3 mergefields will be added** (start loop + body loop+end loop) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInPargraph3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroInsertListFieldInPargraph3.png)

### Preview ###

When you are designing your docx, it's possible to **preview the result of the generated docx**. To do that, click on **Preview** button to open the preview generated docx:

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview1.png)

As you can notice, mergefields are replaced with some values. Those values comes from a JSON file. If you go at to the folder which contains the **project.fields.xml**, you will see that there is **project.fields.xml-data** which appears :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview2.png)

This folder contains the data of the **project.fields.xml** (each xxx.fields.xml are a data folder which is named with xxx.fields.xml-data). If you go to the **project.fields.xml-data**  folder you will see the **default.json" file which was generated by the fr.opensagres.xdocreport.document.tools.**Main**class by using the XML fields file**project.fields.xml**:**

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview3.png)

This JSON files emulate your data coming from Java Model. If you edit the project.fields.xml-data/**default.json** you will see the JSON data which is used by the macro for the preview :

```
{
 "project": {"Name": "Name_Value"},
 "developers": [
  {
   "Name": "Name_Value0",
   "Mail": "Mail_Value0",
   "LastName": "LastName_Value0"
  },
  {
   "Name": "Name_Value1",
   "Mail": "Mail_Value1",
   "LastName": "LastName_Value1"
  },
  {
   "Name": "Name_Value2",
   "Mail": "Mail_Value2",
   "LastName": "LastName_Value2"
  },
  {
   "Name": "Name_Value3",
   "Mail": "Mail_Value3",
   "LastName": "LastName_Value3"
  },
  {
   "Name": "Name_Value4",
   "Mail": "Mail_Value4",
   "LastName": "LastName_Value4"
  },
  {
   "Name": "Name_Value5",
   "Mail": "Mail_Value5",
   "LastName": "LastName_Value5"
  },
  {
   "Name": "Name_Value6",
   "Mail": "Mail_Value6",
   "LastName": "LastName_Value6"
  },
  {
   "Name": "Name_Value7",
   "Mail": "Mail_Value7",
   "LastName": "LastName_Value7"
  },
  {
   "Name": "Name_Value8",
   "Mail": "Mail_Value8",
   "LastName": "LastName_Value8"
  },
  {
   "Name": "Name_Value9",
   "Mail": "Mail_Value9",
   "LastName": "LastName_Value9"
  }
 ]
}
```

You can modify the **default.json**  with your values like this :

```
{
 "project": {"Name": "XDocReport"},
 "developers": [
  {
   "Name": "ZERR",
   "Mail": "angelo.zerr@gmail.com",
   "LastName": "Angelo"
  },
  {
   "Name": "Leclercq",
   "Mail": "pascal.leclercq@gmail.com",
   "LastName": "Pascal"
  }
 ]
}
```

If you launch the preview, you will see the preveiw generated docx like this:

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview4.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroPreview4.png)

### Error Preview ###

When you design your report, you must pay attention to follow syntax of the template engine to manage fields to replace, loop, condition, etc. For instance if you type just for Velocity engine the directive

```
 #end
```

in the Docx, Velocity will throw an error, because there is not start if, foreach, etc.

The XDocReport.dotm is enable to display in a dialog box the stack trace of the report process if there is error. To test it, type just

```
 #end
```

in your Docx :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_ErrorPreview1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_ErrorPreview1.png)

If you click on Preview menu, the popup dialog with error appears with stack trace thrown by Velocity :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_ErrorPreview2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_ErrorPreview2.png)

# Reporting with Java model #

Now your docx report is ready, and you can use it with XDocReport. Save your docx with **project.docx** name into the **xdocreport-quickstart-docx/src/fr/opensagres/xdocreport/quickstart** folder.

Create the Java Main fr.opensagres.xdocreport.quickstart.**GenerateDocxReport** like this :

```
package fr.opensagres.xdocreport.quickstart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.quickstart.model.Developer;
import fr.opensagres.xdocreport.quickstart.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class GenerateDocxReport {

	public static void main(String[] args) throws IOException,
			XDocReportException {
		// 1) Load Docx file by filling Velocity template engine and cache
		// it to the registry
		InputStream in = GenerateDocxReport.class
				.getResourceAsStream("project.docx");
		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,
				TemplateEngineKind.Velocity);

		// 2) Create fields metadata to manage lazy loop (#forech velocity)
		// for table row.
		// 1) Create FieldsMetadata by setting Velocity as template engine
		FieldsMetadata fieldsMetadata = report.createFieldsMetadata();
		// 2) Load fields metadata from Java Class
		fieldsMetadata.load("project", Project.class);
		// Here load is called with true because model is a list of Developer.
		fieldsMetadata.load("developers", Developer.class, true);

		// 3) Create context Java model
		IContext context = report.createContext();
		Project project = new Project("XDocReport");
		context.put("project", project);
		// Register developers list
		List<Developer> developers = new ArrayList<Developer>();
		developers
				.add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com"));
		developers.add(new Developer("Leclercq", "Pascal",
				"pascal.leclercq@gmail.com"));
		context.put("developers", developers);

		// 4) Generate report by merging Java model with the Docx
		OutputStream out = new FileOutputStream(new File("project_out.docx"));
		report.process(context, out);

	}

}
```

If you run this Java Main, it will generate you the docx **project\_out.docx** in the **xdocreport-quickstart-docx** folder:

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview2.png)