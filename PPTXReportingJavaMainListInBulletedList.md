# Loop for fields in bulleted list with FieldsMetadata #

Set exactly the #foreach, [#list for item of bulleted list in the document is impossible. To manage list in item of bulleted list, you must :

  1. add a field name (ex : $name) in a item of bulleted list.
  1. specify that name is a list field with Java code by using FieldsMetadata.

In this case we wish generate Java list of developers in a bulleted list :

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocityList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocityList_Out.png)

## 1.1 Create Java model - Developer ##

Create fr.opensagres.xdocreport.samples.pptxandvelocity.model.**Developer** class like this :

```
package fr.opensagres.xdocreport.samples.pptxandvelocity.model;

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

## 1.2. Design PPTX Table ##

The Java List of developers (Developer#getName(), Developer#getLastName(), Developer#getMail()) will be registered with _developers_ key. Create a bulleted list with the 2 names :

  * $developers.Name
  * $developers.LastName

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocityList.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocityList.png)

Save your document with name **PPTXProjectWithVelocityList.pptx** in the package **fr.opensagres.xdocreport.samples.pptxandvelocity**.

## 1.3 Create PPTXProjectWithVelocityList ##

Now we can create a Java Main which generate list of Developer with Java List of Developer :

```
// Register developers list
List<Developer> developers = new ArrayList<Developer>();
developers.add(new Developer("ZERR", "Angelo", "angelo.zerr@gmail.com"));
developers.add(new Developer("Leclercq", "Pascal", "pascal.leclercq@gmail.com"));
context.put("developers", developers);
```


NOTE : you can do the same thing without Developer model like this :

```
List<Map<String,String>> developers = new ArrayList<Map<String,String>>();
Map<String,String> developer1 = new HashMap<String, String>();
developer1.put("Name", "ZERR");
developer1.put("LastName", "Angelo");
developer1.put("Mail", "angelo.zerr@gmail.com");
developers.add(developer1);
Map<String,String> developer2 = new HashMap<String, String>();
developer2.put("Name", "Leclercq");
developer2.put("LastName", "Pascal");
developer2.put("Mail", "pascal.leclercq@gmail.com");
developers.add(developer2);
context.put("developers", developers);
```

#foreach or [#list  must be added before the start of bulleted list, to generate this directive automaticly, you must use FieldsMetadata to tell which fields can generate #foreach in a row table :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsList("developers.Name");
metadata.addFieldAsList("developers.LastName");
metadata.addFieldAsList("developers.Mail");
report.setFieldsMetadata(metadata);
```

Create fr.opensagres.xdocreport.samples.pptxandvelocity.**PPTXProjectWithVelocityList** class like this :
```
package fr.opensagres.xdocreport.samples.pptxandvelocity;

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
import fr.opensagres.xdocreport.samples.pptxandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.pptxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class PPTXProjectWithVelocityList {

	public static void main(String[] args) {
		try {
			// 1) Load PPTX file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = PPTXProjectWithVelocity.class
					.getResourceAsStream("PPTXProjectWithVelocityList.pptx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create fields metadata to manage lazy loop (#forech velocity)
			// for table row.
			FieldsMetadata metadata = new FieldsMetadata();
			metadata.addFieldAsList("developers.Name");
			metadata.addFieldAsList("developers.LastName");
			metadata.addFieldAsList("developers.Mail");
			report.setFieldsMetadata(metadata);

			// 3) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);
			// Register developers list
			List<Developer> developers = new ArrayList<Developer>();
			developers.add(new Developer("ZERR", "Angelo",
					"angelo.zerr@gmail.com"));
			developers.add(new Developer("Leclercq", "Pascal",
					"pascal.leclercq@gmail.com"));
			context.put("developers", developers);

			// 4) Generate report by merging Java model with the PPTX
			OutputStream out = new FileOutputStream(new File(
					"PPTXProjectWithVelocityList_Out.pptx"));
			report.process(context, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

Run **PPTXProjectWithVelocityList** Java class and **PPTXProjectWithVelocityList\_Out.pptx** will be generated in your project home :

![http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocityList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/PPTXProjectWithVelocityList_Out.png)