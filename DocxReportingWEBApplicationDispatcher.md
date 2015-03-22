# Docx Reporting in WEB Application with Dispatcher #

This section explains how manage XDocReport in WEB Application with Dispatcher mode. This mode require to :

  * create a controller which manage a report (returns the docx input stream, populate context....)
  * create a dispatcher which register the controller.
  * declare in the web.xml the dispatcher to use.

Steps section are :

  1. [Add XDocReport JARs](#1._Add_XDocReport_JARs.md) in your classpath project.
  1. [Create Java model](#2._Create_Java_model.md) create your Java model.
  1. [Design docx report](#3._Design_Docx_report.md) design your docx report with MS Word by using Velocity syntax to set fields to replace.
  1. [Create controller](#4._Create_Controller.md) to implement fr.opensagres.xdocreport.document.web.dispatcher.**AbstractXDocReportWEBController**. Goal of controller is to manage a report (returns the docx input stream, populate context....).
  1. [Create Dispatcher](#5._Create_Dispatcher_Create.md) to register the controller in the dispatcher.
  1. [Register the dispatcher in web.xml](#6._Register_dispatcher_in_web.xml.md).
  1. [Test WEB Reporting](#6._Test_WEB_Reporting.md) with [GET](#POST_URLs.md) and [POST](#POST_URLs.md) urls to generate report into WEB context.

Here screen of the Dynamic WEB Eclipse Project (if you are using Eclipse WTP) that you will have at end of this section :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBDispatcherModeWorkspace.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBDispatcherModeWorkspace.png)

# 1. Add XDocReport JARs #

Add the same JARs in your **WEB-INF/lib** explained at [Java Main section](DocxReportingJavaMain#1._Add_XDocReport_JARs.md).

# 2. Create Java model #

Create the same Java model explained at [Create Java model section](DocxReportingJavaMain#2._Create_Java_model.md) :

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

Create the same docx report with MS Word explained at [Design docx report section](DocxReportingJavaMain#3._Design_Docx_report.md) with the content :

`Project: $project.Name`

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png)

Save your docx in the package **fr.opensagres.xdocreport.samples.docxandvelocity.web.dispatcher**

# 4. Create Controller #

Create fr.opensagres.xdocreport.samples.docxandvelocity.web.dispatcher.**DocxProjectWithVelocityController** like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity.web.dispatcher;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.web.dispatcher.AbstractXDocReportWEBController;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class DocxProjectWithVelocityController extends
		AbstractXDocReportWEBController {

  public static final String REPORT_ID = "DocxProjectWithVelocity.docx";

  public DocxProjectWithVelocityController() {
    super(TemplateEngineKind.Velocity, DocumentKind.DOCX);
  }

  @Override
  public InputStream getSourceStream() {
    return DocxProjectWithVelocityController.class.getResourceAsStream("DocxProjectWithVelocity.docx");
  }

  @Override
  protected FieldsMetadata createFieldsMetadata() {
    return null;
  }

  @Override
  public void populateContext(IContext context, IXDocReport report, HttpServletRequest request) {
    String name = request.getParameter("name");
    Project project = new Project(name);
    context.put("project", project);
  }
}
```

This controller manage the **DocxProjectWithVelocity.docx** report to load it, set fields metadata (to manage #foreach for row table) and populate context with Java Model.

# 5. Create Dispatcher #

At this step, teh below controller must be registered in a dispatcher. To do that, create fr.opensagres.xdocreport.samples.docxandvelocity.web.dispatcher.**MyDispatcher** like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity.web.dispatcher;

import fr.opensagres.xdocreport.document.dispatcher.BasicXDocReportDispatcher;
import fr.opensagres.xdocreport.document.web.dispatcher.IXDocReportWEBController;

public class MyDispatcher extends BasicXDocReportDispatcher<IXDocReportWEBController> {

  public MyDispatcher() {
    register(DocxProjectWithVelocityController.REPORT_ID, new DocxProjectWithVelocityController());
    // register another controller....to manage another report
  }
}
```

# 6. Register dispatcher in web.xml #

At this step, the dispatcher must be registered to the fr.opensagres.xdocreport.document.web.dispatcher.**ProcessDispatcher** servlet. To do that, add this servlet declaration in your web.xml :

```
<servlet>
  <servlet-name>MyDispatcherReportServlet</servlet-name>
  <servlet-class>fr.opensagres.xdocreport.document.web.dispatcher.ProcessDispatcherXDocReportServlet</servlet-class>
  <init-param>
    <param-name>dispatchers</param-name>
    <param-value>fr.opensagres.xdocreport.samples.docxandvelocity.web.dispatcher.MyDispatcher</param-value>
  </init-param>
</servlet>

<servlet-mapping>
  <servlet-name>MyDispatcherReportServlet</servlet-name>
  <url-pattern>/reportDispatcher/*</url-pattern>
</servlet-mapping>
```

## init-param ##

This servlet can be configured with the same parameters than [Servlet mode](DocxReportingWEBApplicationServlet.md). For more information go at [init-param section](DocxReportingWEBApplicationServlet#init-param.md).

# 6. Test WEB Reporting #

At this step you can test your WEB Application. Start your server. Following URLs assume that base URL is [http://localhost:8080/docxandvelocity/](http://localhost:8080/docxandvelocity/)

In this sample the report servlet will be available with [http://localhost:8080/docxandvelocity/reportDispatcher](http://localhost:8080/docxandvelocity/reportDispatcher)

The fr.opensagres.xdocreport.document.web.dispatcher.**ProcessDispatcherXDocReportServlet** servlet support GET and POST.

## GET URLs ##

To test GET URLs, please go at [GET URLs Servlet mode](DocxReportingWEBApplicationServlet#GET_URLs.md) section and replace in the URL reportServlet with reportDispatcher.

## POST URLs ##

To test POST, create the JSP **reportDispatcher.jsp** in your WEB Application with the following form content which contains data model waited by the report, the report id and another parameters like dispatch, processState and entryName :

```
<form name="reportDispatcherForm"
	action="<%=request.getContextPath()%>/reportDispatcher" method="post">
<table>
	<!--  Data Model -->
	<tr>
		<td>Project (data model) :</td>
		<td><input type="text" name="name" value="XDocReport" /></td>
	</tr>
	<!--  reportId HTTP parameter -->
	<tr>
		<td>Report :</td>
		<td><select name="reportId">
			<option value="DocxProjectWithVelocity.docx" >DocxProjectWithVelocity.docx</option>
		</select></td>
	</tr>
	<!--  processState HTTP parameter -->
	<tr>
		<td>Process state :</td>
		<td><select name="processState">
			<option value="original" >original</option>
			<option value="preprocessed" >preprocessed</option>
			<option value="generated" selected="selected">generated</option>
		</select></td>
	</tr>
	<!--  dispatch HTTP parameter -->
	<tr>
		<td>Dispatch :</td>
		<td><select name="dispatch">
			<option value="download" >download</option>
			<option value="view" >view</option>
		</select></td>
	</tr>
	<!--  entryName HTTP parameter -->
	<tr>
		<td>Entry name :</td>
		<td><select name="entryName">
			<option value=""></option>
			<option value="word/document.xml">word/document.xml</option>
		</select></td>
	</tr>
	<!-- Generate report -->
	<tr>
		<td colspan="2"><input type="submit" value="OK"></td>
	</tr>
</table>
</form>
```

If you go at [http://localhost:8080/docxandvelocity/reportDispatcher.jsp](http://localhost:8080/docxandvelocity/reportDispatcher.jsp) you will see this form :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormDispatcher.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormDispatcher.png)

Now you can play with differents parameters explained in the [GET URLs](#GET_URLs.md) section.