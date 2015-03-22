# Loop for fields - WEB Application - Servlet mode #

In this section we add fields list like explained in the [Java Main - List Field section](DocxReportingJavaMainListField.md)

# 1. Loop for fields in table row #

Here we add a Table in the docx to generate list of developers in a table to generate for instance this report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeList_Out.png)

## 1.1 Create Java model - Developer ##

Create fr.opensagres.xdocreport.samples.docxandvelocity.model.**Developer** class like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity.model;

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

## 1.2. Design Docx Table ##

The Java List of developers (Developer#getName(), Developer#getLastName(), Developer#getMail()) will be registered with _developers_ key. Add a table with 3 cells which contains merge fields :

  * $developers.Name
  * $developers.LastName
  * $developers.Mail

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityList.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityList.png)

Save your document with name **DocxProjectWithVelocityList.docx** in the package **fr.opensagres.xdocreport.samples.docxandvelocity.web.servlet**.

## 1.3. Modify MyReportServlet ##

Modify MyReportServlet to add new report **DocxProjectWithVelocityList** like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.XDocReportNotFoundException;
import fr.opensagres.xdocreport.document.web.AbstractProcessXDocReportServlet;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Developer;
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class MyReportServlet extends AbstractProcessXDocReportServlet {

	public static final String REPORT_ID = "DocxProjectWithVelocity.docx";
	public static final String REPORT_LIST_ID = "DocxProjectWithVelocityList.docx";

	@Override
	protected InputStream getSourceStream(String reportId,
			HttpServletRequest request) throws IOException, XDocReportException {
		if (REPORT_ID.equals(reportId)) {
			return MyReportServlet.class
					.getResourceAsStream("DocxProjectWithVelocity.docx");
		}
		if (REPORT_LIST_ID.equals(reportId)) {
			return MyReportServlet.class
					.getResourceAsStream("DocxProjectWithVelocityList.docx");
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
		} else if (REPORT_LIST_ID.equals(reportId)) {
			String name = request.getParameter("name");
			Project project = new Project(name);
			context.put("project", project);

			int nbDevelopers = 0;
			try {
				nbDevelopers = Integer.parseInt(request
						.getParameter("nbDevelopers"));
			} catch (Throwable e) {

			}
			List<Developer> developers = new ArrayList<Developer>(nbDevelopers);
			for (int i = 0; i < nbDevelopers; i++) {
				developers.add(new Developer("Name" + i, "LastName" + i, "Mail"
						+ i));
			}
			context.put("developers", developers);
		}
		// else manage another report
	}

	@Override
	protected FieldsMetadata getFieldsMetadata(String reportId,
			HttpServletRequest request) {
		if (REPORT_LIST_ID.equals(reportId)) {
			FieldsMetadata metadata = new FieldsMetadata();
			metadata.addFieldAsList("developers.Name");
			metadata.addFieldAsList("developers.LastName");
			metadata.addFieldAsList("developers.Mail");
			return metadata;
		}
		return super.getFieldsMetadata(reportId, request);
	}

}
```

The new report wait two HTTP parameters for populate data model :

  * **name** which is the project name.
  * **nbDevelopers** which is the number of developpers to display in the table.

The method getFieldsMetadata is override to for the new report to manage #foreach automaticly before the row table.

You can notice that Servlet code grows to manage this new report. With [Dispatcher mode](DocxReportingWEBApplicationDispatcher.md) you will not have this problem.

## 1.4. Test WEB Reporting ##

### GET URLs ###

If you want replace $project.Name with XDocReport and generate 10 row of developers go at this URL :

[http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocityList.docx&name=XDocReport&nbDevelopers=10](http://localhost:8080/docxandvelocity/reportServlet?reportId=DocxProjectWithVelocityList.docx&name=XDocReport&nbDevelopers=10)

It generate report like this :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeList_Out.png)

### POST URLs ###

To test POST URLs you can modify the JSP [reportServlet.jsp](DocxReportingWEBApplicationServlet#POST_URLs.md) by adding nbDeveloppers combo and the new reportId DocxProjectWithVelocityList.docx in the reportId combo :

```
<form name="reportServletForm"
	action="<%=request.getContextPath()%>/reportServlet" method="post">
<table>
	<!--  Data Model -->
	<tr>
		<td>Project (data model) :</td>
		<td><input type="text" name="name" value="XDocReport" /></td>
	</tr>
	<tr>
		<td>Nb developers (data model) :</td>
		<td><select name="nbDevelopers">
			<option value="0">0</option>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
		</select></td>
	</tr>
	<!--  reportId HTTP parameter -->
	<tr>
		<td>Report :</td>
		<td><select name="reportId">
			<option value="DocxProjectWithVelocity.docx">DocxProjectWithVelocity.docx</option>
			<option value="DocxProjectWithVelocityList.docx">DocxProjectWithVelocityList.docx</option>
		</select></td>
	</tr>
	<!--  processState HTTP parameter -->
	<tr>
		<td>Process state :</td>
		<td><select name="processState">
			<option value="original">original</option>
			<option value="preprocessed">preprocessed</option>
			<option value="generated" selected="selected">generated</option>
		</select></td>
	</tr>
	<!--  dispatch HTTP parameter -->
	<tr>
		<td>Dispatch :</td>
		<td><select name="dispatch">
			<option value="download">download</option>
			<option value="view">view</option>
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

If you go at [http://localhost:8080/docxandvelocity/reportServlet.jsp](http://localhost:8080/docxandvelocity/reportServlet.jsp) you can choose nbDevelopers and DocxProjectWithVelocityList.docx as report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormServletList.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormServletList.png)

and generate with OK button this report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBServletModeList_Out.png)