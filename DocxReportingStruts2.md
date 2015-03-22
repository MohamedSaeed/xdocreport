# XDocReport with Struts2 #

XDocReport provides a Struts2 Result to generate report with docx, odt and convert it to another format PDF/XHTML if you wish. It provides [3 XDocReport result](#XDocReport_Result.md).

Imagine you have the docx report like this:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityList.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityList.png)

And you wish generate this report with Struts2 Action :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityList_Out.png)

Here a sample which use the the **xdocreport** result :

  * Here the Action class which populate the XDocReport context by implementing org.apache.struts2.views.xdocreport.**PopulateContextAware**_#populateContextIXDocReport report, IContext context)_ :

```
package example;

import org.apache.struts2.views.xdocreport.PopulateContextAware;

import com.opensymphony.xwork2.ActionSupport;

import example.model.Project;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;

public class DisplayProject extends ActionSupport  implements PopulateContextAware  {

  private Project project;
  private List<Developer> developers = new ArrayList<Developer>();

  public String execute() throws Exception {
    project = new Project("XDocReport");
    developers.add(new Developer("ZERR", "Angelo","angelo.zerr@gmail.com"));
    developers.add(new Developer("Leclercq", "Pascal",			"pascal.leclercq@gmail.com"));
    return SUCCESS;
  }
	
  public Project getProject() {
    return project;
  }
	  
  public List<Developer> getDevelopers() {
    return developers;
  }

  public void populateContext(IXDocReport report, IContext context)
			throws Exception {
    context.put("project", getProject());
    context.put("developers", getDevelopers());
  }
}
```

  * Here the declared Action :

```
<package name="example" namespace="/example" extends="xdocreport-default">

  <action name="DisplayProject" class="example.DisplayProject">
    <result name="success" type="xdocreport">
    <param name="location">/docx/DocxProjectWithVelocityList.docx</param>
    <param name="templateEngine">Velocity</param>
    <param name="fieldAsList">developers.Name,developers.LastName</param>
    </result>
  </action>
</package>
```

You can notice that :

  * **package** extends **xdocreport-default** to access to the default XDocReport result. If you don't want extends this package, redefine the result type in your package.
  * **result/@type="xdocreport"** is used here. This result require to implement org.apache.struts2.views.xdocreport.**PopulateContextAware** for the Action which use this result to populate context. If you don't want do that, you can use **lazy-xdocreport** or **lazy-commons-beanutils-xdocreport** result.
  * **location** : the report used is the docx stored in the WEB Application at /docx/DocxProjectWithVelocityList.docx.
  * **templateEngine**:  Velocity is used here.
  * **fieldAsList** : define the fields as list used in the docx Table to manage loop for the docx table.

# XDocReport Result #

Provides 3 results type in the **xdocreport-default** package :

```
<package name="xdocreport-default" extends="struts-default">
  <result-types>

    <!-- XDocReport Result Type with Action which must implement PopulateContextAware to populate context -->
    <result-type name="xdocreport" class="org.apache.struts2.views.xdocreport.XDocReportResult" />

    <!-- XDocReport Result Type with lazy method to populate context -->
    <result-type name="lazy-xdocreport" class="org.apache.struts2.views.xdocreport.LazyXDocReportResult" />

    <!-- XDocReport Result Type which use commons-beanutils.jar (PropertyUtils) to populate context -->
    <result-type name="lazy-commons-beanutils-xdocreport" class="org.apache.struts2.views.xdocreport.LazyCommonsBeanUtilsXDocReportResult" />
  </result-types>
</package>
```

Result parameters :

| **Parameters** | **Description** | Required? |
|:---------------|:----------------|:----------|
| **location**     | Location of the docx, odt report to use. | yes |
| **templateEngine** | Template engine to use (according the JAR which is added in the classpath, values are Velocity|Freemarker).  The template engine can use OGNL expression too (ex : #action.templateEngine will use the Action#getTemplateEngine() method). | yes|
| **fieldAsList** | Set fields name which must generate lazy loop for docx/odt table. | no |
| **converter** | Converter to use (according the JAR which is added in the classpath, values are static value PDF\_ITEXT|XHTML\_XWPF ). The converter can use OGNL expression too (ex : #action.converter will use the Action#getConverter() method). If converter is null, no conversion is done. | no|
| **trackLastModified** | true if docx, odt file which is used to load must be tracked to observe the document change. If document change, report will be reloaded with the new file. By default trackLastModified is false.| no |
| **download** | true if report must be downloaded (generate Content-Disposition:"attachment; filename... in the HTTP Header) and false otherwise. By default download is true. | no |

XDocReport is modular, it means that you must add the well JARs in your classpath to select well template engine and well converter.

To know which JARs you must add :

  * [Required+Template+Document JARs](DocxReportingJavaMain#1._Add_XDocReport_JARs.md) : explain which JAR you must add in your classpath to generate report and use templateEngine parameter (Velocity|Freemarker).
  * [Converter JARs](DocxReportingWEBApplicationServletConverter#1._Add_XDocReport_JARs.md) : explain which JAR you must add in your classpath to use converter parameter (PDF\_ITEXT|XHTML\_XWPF).

## Location ##

The docx, odt document to use to generate report can be stored in the Classpath, FILESYSTEM or WEBAPP.

### Classpath ###

User **classpath:** suffix. Example :

```
<action name="DisplayProject" class="example.DisplayProject">
  <result name="success" type="xdocreport">
    <param name="location">classpath:example/DocxProjectWithVelocity.docx</param>
    <param name="templateEngine">Velocity</param>
  </result>
</action>
```


### Filesystem ###

User **file:/** suffix. Example :

```
<action name="DisplayProject" class="example.DisplayProject">
  <result name="success" type="xdocreport">
    <param name="location">file:/C:/example/DocxProjectWithVelocity.docx</param>
    <param name="templateEngine">Velocity</param>
  </result>
</action>
```


### WEB content ###

By default it's WEB content.

```
<action name="DisplayProject" class="example.DisplayProject">
  <result name="success" type="xdocreport">
    <param name="location">/docx/DocxProjectWithVelocity.docx</param>
    <param name="templateEngine">Velocity</param>
  </result>
</action>
```

## OGNL expression ##

For the whole Result parameters, you can use OGNL expression instead of setting static value. For instance if you wish delegate the location value of the Action,

```
package example;

import com.opensymphony.xwork2.ActionSupport;

import example.model.Project;

public class OGNLDisplayProject extends ActionSupport {

  private Project project;

  public String execute() throws Exception {
    project = new Project("XDocReport");
    return SUCCESS;
  }

  public Project getProject() {
    return project;
  }

  public String getReportLocation() {
    return "/docx/DocxProjectWithVelocity.docx"
  }
  
  public void populateContext(IXDocReport report, IContext context)
			throws Exception {
    context.put("project", getProject());
  }
}
```

you can write this :

```
<param name="location">#action.reportLocation</param>
```

Here the full Action declared :

```
<action name="OGNLDisplayProject" class="example.OGNLDisplayProject">
  <result name="success" type="xdocreport">
    <param name="location">#action.reportLocation</param>
    <param name="templateEngine">Velocity</param>
  </result>
</action>
```

# Populate Context #

By default, the populate context get the Action which was called before the result type and loop for each getter method of this Action and put the instance of the getter with name of the getter (without get).

For instance if you have this Action class :

```
package example;

import com.opensymphony.xwork2.ActionSupport;

import example.model.Project;

public class DisplayProject extends ActionSupport {

  private Project project;

  public String execute() throws Exception {
    project = new Project("XDocReport");
    return SUCCESS;
  }

  public Project getProject() {
    return project;
  }
}
```

And you declare the in the struts.xml :

```

<action name="DisplayProject" class="example.DisplayProject">
  <result name="success" type="lazy-xdocreport">
    <param name="location">example/DocxProjectWithVelocity.docx</param>
    <param name="templateEngine">Velocity</param>
  </result>
</action>
```

If you go at this action, the XDocReport Result register in the context :

```
context.put("project", DisplayProject#getProject()); 
```

After you can write mergefield like this :

```
$project.getName
```

## Default XDocReport Result ##

Every XDocReport Result extends org.apache.struts2.views.xdocreport.**AbstractXDocReportResult**. XDocReport provides 3 implementation :

  * org.apache.struts2.views.xdocreport\*XDocReportResult**: which is standard implementation where Action must implement org.apache.struts2.views.xdocreport.**PopulateContextAware**to manage populate context.**

  * org.apache.struts2.views.xdocreport\*LazyXDocReportResult**: which is basic implementation to retrieve getter of the Action and put it to the XDocReport context.
  * org.apache.struts2.views.xdocreport\*LazyCommonsBeanUtilsXDocReportResult** : which is implementation to retrieve getter of the Action with commons-beanutils. It requires commons-beanutils.**.jar in your Classpath.**

## Custom XDocReport Result ##

You can implement your own org.apache.struts2.views.xdocreport.**AbstractXDocReportResult** to manage the populate as you which.

Here a sample of custom populate :

```
package example;

import org.apache.struts2.views.xdocreport.AbstractXDocReportResult;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

import example.model.Project;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;

public class CustomXDocReportResult extends AbstractXDocReportResult {

	@Override
	protected void populateContext(IXDocReport report, IContext context,
			String finalLocation, ActionInvocation invocation) throws Exception {

		// 1) Get Action from the Struts2 Stack
		ValueStack stack = invocation.getStack();
		Object action = stack.findValue("#action");

		if (action instanceof DisplayProject) {
			// Action is DisplayProject, populate the context with Project
			// instance
			DisplayProject displayProject = (DisplayProject) action;
			Project project = displayProject.getProject();
			context.put("projectName", project.getName());
		}

	}

}
```

After you can use your own result :

```
<result-types>
  <result-type name="basic-xdocreport" class="org.apache.struts2.views.xdocreport.BasicXDocReportResult" />
  <result-type name="custom-xdocreport" class="example.CustomXDocReportResult" />				
</result-types>

<action name="CustomDisplayProject" class="example.DisplayProject">
  <result name="success" type="custom-xdocreport">
    <param name="location">/docx/DocxProjectWithVelocity.docx</param>
    <param name="templateEngine">Velocity</param>
  </result>
</action>
```

You can use mergefield in your docx like this :

```
$projectName
```

instead of (by default) :

```
$project.Name
```

Not : you can do the same thing with org.apache.struts2.views.xdocreport.**PopulateContextAware**.