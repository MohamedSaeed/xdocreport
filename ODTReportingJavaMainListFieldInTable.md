# Loop for fields in table row with FieldsMetadata #

Set exactly the #foreach, [#list for table row in the document is impossible. To manage list in row table, you must :

  1. add a field name (ex : $name) in a cell of table row.
  1. specify that name is a list field with Java code by using FieldsMetadata.

In this case we wish generate Java list of developers in a table :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList_Out.png)

## 1.1 Create Java model - Developer ##

Create fr.opensagres.xdocreport.samples.odtandvelocity.model.**Developer** class like this :

```
package fr.opensagres.xdocreport.samples.odtandvelocity.model;

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

## 1.2. Design ODT Table ##

The Java List of developers (Developer#getName(), Developer#getLastName(), Developer#getMail()) will be registered with _developers_ key. Add a table with 3 cells which contains merge fields :

  * $developers.Name
  * $developers.LastName
  * $developers.Mail

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList.png)

Save your document with name **ODTProjectWithVelocityList.odt** in the package **fr.opensagres.xdocreport.samples.odtandvelocity**.

## 1.3 Create ODTProjectWithVelocityList ##

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

#foreach or [#list  must be added before the row, to generate this directive automaticly, you must use FieldsMetadata to tell which fields can generate #foreach in a row table :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsList("developers.Name");
metadata.addFieldAsList("developers.LastName");
metadata.addFieldAsList("developers.Mail");
report.setFieldsMetadata(metadata);
```

Create fr.opensagres.xdocreport.samples.odtxandvelocity.**ODTProjectWithVelocityList** class like this :
```
package fr.opensagres.xdocreport.samples.odtandvelocity;

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
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.odtandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class ODTProjectWithVelocityList {

	public static void main(String[] args) {
		try {
			// 1) Load ODT file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = ODTProjectWithVelocity.class
					.getResourceAsStream("ODTProjectWithVelocityList.odt");
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

			// 4) Generate report by merging Java model with the ODT
			OutputStream out = new FileOutputStream(new File(
					"ODTProjectWithVelocityList_Out.odt"));
			report.process(context, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

Run **ODTProjectWithVelocityList** Java class and **ODTProjectWithVelocityList\_Out.odt** will be generated in your project home :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityList_Out.png)