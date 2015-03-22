# Docx Reporting in WEB Application with Servlet #

This section explains how manage XDocReport in WEB Application with Servlet mode. This mode require to implement fr.opensagres.xdocreport.document.web.**AbstractProcessXDocReportServlet** with 2 methods :

```
/**
 * Returns input stream of the report to load identified with
 * <code>reportId</code>.
 * 
 * @param reportId
 *            report id.
 * @param request
 *            Http servlet request context.
 * @return
 * @throws IOException
 * @throws XDocReportException
 */
 protected abstract InputStream getSourceStream(String reportId,
			HttpServletRequest request) throws IOException, XDocReportException;

/**
 * Put the Java model in the context for the report <code>reportId</code>.
 * 
 * @param context
 *            XDocReport context to register Java data model.
 * @param reportId
 *            report id.
 * @param request
 *            Http servlet request context.
 * @throws IOException
 * @throws XDocReportException
 */
 protected abstract void populateContext(IContext context, String reportId,
			HttpServletRequest request) throws IOException, XDocReportException;
```

Steps section are :

  1. [Add XDocReport JARs](#1._Add_XDocReport_JARs.md) in your classpath project.
  1. [Create Java model](#2._Create_Java_model.md) create your Java model.
  1. [Design docx report](#3._Design_Docx_report.md) design your docx report with MS Word by using Velocity syntax to set fields to replace.
  1. [Create servlet](#4._Create_Servlet.md) to implement fr.opensagres.xdocreport.document.web.**AbstractProcessXDocReportServlet**.
  1. [Declare servlet](#5._Declare_Servlet_in_web.xml.md) in web.xml.
  1. [Test WEB Reporting](#6._Test_WEB_Reporting.md) with [GET](#GET_URLs.md) and [POST](#POST_URLs.md) urls to generate report into WEB context.

Here screen of the Dynamic WEB Eclipse Project (if you are using Eclipse WTP) that you will have at end of this section :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeWorkspace.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeWorkspace.png)

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

Save your document with name **DocxProjectWithVelocity.docx** in the package **fr.opensagres.xdocreport.samples.docxandvelocity.web.servlet**.

# 4. Create Servlet #

Create fr.opensagres.xdocreport.samples.docxandvelocity.web.servlet.**MyReportServlet** class like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity.web.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.XDocReportNotFoundException;
import fr.opensagres.xdocreport.document.web.AbstractProcessXDocReportServlet;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;

public class MyReportServlet extends
		AbstractProcessXDocReportServlet {

  public static final String REPORT_ID = "DocxProjectWithVelocity.docx";

  @Override
  protected InputStream getSourceStream(String reportId,
  		HttpServletRequest request) throws IOException, XDocReportException {
    if (REPORT_ID.equals(reportId)) {
      return MyReportServlet.class.getResourceAsStream("DocxProjectWithVelocity.docx");
    }
    // else manage another report
    throw new XDocReportNotFoundException(reportId);
  }

  @Override
  protected void populateContext(IContext context, String reportId,
			HttpServletRequest request) throws IOException, XDocReportException {
    if (REPORT_ID.equals(reportId)) {
      String name = request.getParameter("name");
      Project project = new Project(name);
      context.put("project", project);
    }
    // else manage another report
  }
}
```

You can notice that reportId parameter is used well input stream and populate context according the reportId. When you will add new report, you will add some code to manage that and servlet code will grow. To avoid that, XDocReport suggest you to use [Dispatcher mode](DocxReportingWEBApplicationDispatcher.md).

# 5. Declare Servlet in web.xml #

Declare the Servlet in your **web.xml** like this :

```
<servlet>
  <servlet-name>MyReportServlet</servlet-name>
  <servlet-class>fr.opensagres.xdocreport.samples.docxandvelocity.web.servlet.MyReportServlet</servlet-class>
</servlet>

<servlet-mapping>
  <servlet-name>MyReportServlet</servlet-name>
  <url-pattern>/reportServlet/*</url-pattern>
</servlet-mapping>
```

## init-param ##

The Servlet can be customized with several parameters (init-param) :

| **param-name** | **param-value** |
|:---------------|:----------------|
| encoding   | set request encoding (ex : UTF-8). It will call HttpServletRequest#setCharacterEncoding(encoding)). According server this feature will work or not (ex : for Tomcat you must configure your server.xml with useBodyEncodingForURI="true" in the Connector) |
| forceEncoding   | true|false : if true it will force the encoding declared below even if HttpServletRequest#getCharacterEncoding() is not null.|
| cacheOriginalDocument   | true|false if true the original docx content will be stored in memory. It's usefull if you wish provides in your WEB Application download original docx. If you need not this feature, don't use it to free memory. |

Here sample with thoses parameters :

```
<servlet>
	<servlet-name>MyReportServlet</servlet-name>
	<servlet-class>fr.opensagres.xdocreport.samples.docxandvelocity.web.servlet.MyReportServlet</servlet-class>
	<init-param>
		<param-name>encoding</param-name>
		<param-value>UTF-8</param-value>
	</init-param>
	<init-param>
		<param-name>forceEncoding</param-name>
		<param-value>true</param-value>
	</init-param>
	<!-- DONT USE THIS PARAMETER IF YOU NEED NOT DOWNLOAD ORIGINAL DOCUMENT IN YOUR WEB APPLICATION -->
	<init-param>
		<param-name>cacheOriginalDocument</param-name>
		<param-value>true</param-value>
	</init-param>
</servlet>
```

# 6. Test WEB Reporting #

At this step you can test your WEB Application. Start your server. Following URLs assume that base URL is [http://localhost:8080/docxandvelocity/](http://localhost:8080/docxandvelocity/)

In this sample the report servlet will be available with [http://localhost:8080/docxandvelocity/reportServlet](http://localhost:8080/docxandvelocity/reportServlet)

The fr.opensagres.xdocreport.document.web.**AbstractProcessXDocReportServlet** servlet support GET and POST.

This servlet must be called with several HTTP parameters :

| **HTTP Parameters name** | **Description** |
|:-------------------------|:----------------|
| reportId            |  This parameter is required for every request. It defines the report id tu use for report generation |
| dispatch            |  This parameters support 3 values : **download** : to download report generation (or entry from the docx), **view** : to view report generation (or entry from the docx) (same thing than download but without dialog box Save as),  **remove** : to remove cached report from the registry  |
| processState |  can have values **original** to load original docx (works only if cacheOriginalDocument is setted to true, **preprocessed** to load preprocessed docx and **generated** to obtain generated report. For more information about process state please go at [XDocReport Process](XDocReportProcess.md) |
| entryName | set the entry name of docx that you wish download or view . For docx you can set entryName=word/document.xml to display the content of this file and specify processState to understand process XDocReport and debugging a problem |

At this step you can generate report by typping URL in your browser. Following URLs use base URL [http://localhost:8080/docxandvelocity/](http://localhost:8080/docxandvelocity/)

## GET URLs ##

Here we will test GET URLs by typing directly URL in the browser.

### Generate and download report ###

Here we wish replace $project.Name with XDocReport value (name HTTP parameter). URL to generate and download report is like this :

[http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport](http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport)

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBDownload1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBDownload1.png)

And if you open the generated docx you will see :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png)

Note : this URL is the same like dispatch=download and processState=generated:

[http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&dispatch=download&processState=generated&name=XDocReport](http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&dispatch=download&processState=generated&name=XDocReport)

### Generate and view report ###

[http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&dispatch=view&name=XDocReport](http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&dispatch=view&name=XDocReport)

In this case it's the same thing that download because browser don't support docx. This view dispatch works for instance when you will convert docx 2 PDF/XHTML or when you will view entry.

### Generate entryName ###

To understand [XDocReport Process](XDocReportProcess.md), you can use **entryName** and **processState** parameters. Here we will download teh **word/document.xml**  entry from teh docx. To do that we will add in the URL **entryName=word/document.xml**.

#### processState=original ####

Here we display the original entry word/document.xml. To display original entry you must set in the web.xml :

```
<init-param>
	<param-name>cacheOriginalDocument</param-name>
	<param-value>true</param-value>
</init-param>
```

Go at
[http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&entryName=word/document.xml&processState=original](http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&entryName=word/document.xml&processState=original)

You will see the following XML which describes Project : $project.Name which is a mergefield :
```
<w:p w:rsidR="00971880" w:rsidRPr="00526F78" w:rsidRDefault="00526F78">
	<w:pPr>
		<w:rPr>
			<w:color w:val="FF0000" />
		</w:rPr>
	</w:pPr>
	<w:r>
		<w:t xml:space="preserve">Project : </w:t>
	</w:r>
	<w:r w:rsidR="00274816">
		<w:rPr>
			<w:color w:val="FF0000" />
		</w:rPr>
		<w:fldChar w:fldCharType="begin" />
	</w:r>
	<w:r w:rsidR="00274816">
		<w:rPr>
			<w:color w:val="FF0000" />
		</w:rPr>
		<w:instrText xml:space="preserve"> MERGEFIELD  $project.Name </w:instrText>
	</w:r>
	<w:r w:rsidR="00274816">
		<w:rPr>
			<w:color w:val="FF0000" />
		</w:rPr>
		<w:fldChar w:fldCharType="separate" />
	</w:r>
	<w:r w:rsidR="00274816">
		<w:rPr>
			<w:noProof />
			<w:color w:val="FF0000" />
		</w:rPr>
		<w:t>«$project.Name»</w:t>
	</w:r>
	<w:r w:rsidR="00274816">
		<w:rPr>
			<w:color w:val="FF0000" />
		</w:rPr>
		<w:fldChar w:fldCharType="end" />
	</w:r>
</w:p>
```

#### processState=preprocessed ####

Here we display the second setp preprocessed (processStep=preprocessed). This step is done juste one time to replace mergefield with static content, add #foreach before row...

Go at
[http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&entryName=word/document.xml&processState=preprocessed](http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&entryName=word/document.xml&processState=preprocessed)

You will see the following XML where you can notive mergefield code doesn't exist :

```
<w:p w:rsidR="00971880" w:rsidRPr="00526F78" w:rsidRDefault="00526F78">
	<w:pPr>
		<w:rPr>
			<w:color w:val="FF0000" />
		</w:rPr>
	</w:pPr>
	<w:r>
		<w:t xml:space="preserve">Project : </w:t>
	</w:r>
	<w:r w:rsidR="00274816">
		<w:rPr>
			<w:noProof />
			<w:color w:val="FF0000" />
		</w:rPr>
		<w:t>$project.Name</w:t>
	</w:r>
</w:p>
```

#### processState=generated ####

This step is the last step to generate report. Preprocessed step is done once time and store XML document modified in the XDocArchive Java structure. When generated step is done it use the cached preprocessed XDocArchive to generate report.

If you wish replace $projext.Name with XDocReport, go at
[http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&entryName=word/document.xml&processState=generated&name=XDocReport](http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocity.docx&entryName=word/document.xml&processState=generated&name=XDocReport)

You will see :


```
<w:p w:rsidR="00971880" w:rsidRPr="00526F78" w:rsidRDefault="00526F78">
	<w:pPr>
		<w:rPr>
			<w:color w:val="FF0000" />
		</w:rPr>
	</w:pPr>
	<w:r>
		<w:t xml:space="preserve">Project : </w:t>
	</w:r>
	<w:r w:rsidR="00274816">
		<w:rPr>
			<w:noProof />
			<w:color w:val="FF0000" />
		</w:rPr>
		<w:t>XDocReport</w:t>
	</w:r>
</w:p>
```

$project.Name is modified with XDocReport by using Velocity template engine.

## POST URLs ##

To test POST, create the JSP reportSevlet.jsp in your WEB Application with the following form content which contains data model waited by the report, the report id and another parameters like dispatch, processState and entryName :

```
<form name="reportServletForm"
	action="<%=request.getContextPath()%>/reportServlet" method="post">
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

If you go at [http://localhost:8080/docxandvelocity/reportServlet.jsp](http://localhost:8080/docxandvelocity/reportServlet.jsp) you will see this form :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormServlet.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormServlet.png)

Now you can play with differents parameters explained in the [GET URLs](#GET_URLs.md) section.