# XDocReport with Spring MVC #

Since [1.0.4](XDocReport104.md), XDocReport provides a support for [Spring MVC](http://docs.spring.io/spring/docs/4.0.1.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/#mvc) with the Spring MVC View **org.springframework.web.servlet.view.xdocreport.XDocReportView** to generate report with docx, odt and convert it to another format PDF/XHTML if you wish.

## Project ##

This support can be find in the project [org.springframework.web.servlet.view.xdocreport](https://code.google.com/p/xdocreport/source/browse/#git%2Fthirdparties-extension%2Forg.springframework.web.servlet.view.xdocreport).

You can find a basic webapp with XDocReport & Spring MVC in the [reporting-webapp-springmvc](https://code.google.com/p/xdocreport/source/browse?repo=samples#git%2Freporting-webapp-springmvc) project.

## Maven ##

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.springframework.web.servlet.view.xdocreport</artifactId>
</dependency>
```

## Sample ##

### Generate report ###

With this support you can write for instance a Spring controller like this :

```
@RequestMapping( "/download/docx" )
public String docxReport( ModelMap model )
{
     Project project = new Project( "XDocReport" );
     model.addAttribute( "project", project );
     return "docxReport";
}
```


You can see this code in the [MainController.java](https://code.google.com/p/xdocreport/source/browse/reporting-webapp-springmvc/src/main/java/fr/opensagres/xdocreport/samples/reporting/springmvc/controllers/MainController.java?repo=samples).

this method of the controller call a Spring bean with id "docxReport" that you can declare with **org.springframework.web.servlet.view.xdocreport.XDocReportView** like this :

```
<bean id="docxReport"
  class="org.springframework.web.servlet.view.xdocreport.XDocReportView"
  p:url="classpath:DocxProjectWithVelocityList.docx"
  p:templateEngineId="Velocity" />
```

You can see this code in the [xdocreport-views.xml](https://code.google.com/p/xdocreport/source/browse/reporting-webapp-springmvc/src/main/webapp/WEB-INF/xdocreport-views.xml?repo=samples).

### Generate report and convert to PDF ###
```
@RequestMapping( "/download/pdf2" )
public String dynamicPdfReport( ModelMap model )
{
    Project project = new Project( "XDocReport" );
    model.addAttribute( "project", project );
    model.addAttribute( Options.getTo( ConverterTypeTo.PDF ).via( ConverterTypeVia.XWPF ) );
    return "docxReport";
}
```

### FieldsMetadata ###

```
package fr.opensagres.xdocreport.samples.reporting.springmvc.report;

import org.springframework.web.servlet.view.xdocreport.IXDocReportConfiguration;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.samples.reporting.springmvc.model.Developer;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class DocxProjectWithVelocityListConfiguration
    implements IXDocReportConfiguration
{

    public void configure( IXDocReport report )
        throws XDocReportException
    {
        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.load( "developers", Developer.class, true );
    }
}
```

And use DocxProjectWithVelocityListConfiguration like this :

```
<bean id="reportConfiguration"
class="fr.opensagres.xdocreport.samples.reporting.springmvc.report.DocxProjectWithVelocityListConfiguration" />

<bean id="docxReport"
 class="org.springframework.web.servlet.view.xdocreport.XDocReportView"
 p:url="classpath:fr/opensagres/xdocreport/samples/reporting/springmvc/report/DocxProjectWithVelocityList.docx"
 p:templateEngineId="Velocity" >
   <property name="configuration" ref="reportConfiguration" /> 
</bean>
```

### XDocReportView Extension ###

In some case, if you wish to modify some behaviour of
[XDocReportView](https://code.google.com/p/xdocreport/source/browse/thirdparties-extension/org.springframework.web.servlet.view.xdocreport/src/main/java/org/springframework/web/servlet/view/xdocreport/XDocReportView.java) you can extend it and override some methods.

For instance you can override XDocReportView#getContentDisposition to return an unique file name instead of using the given reportId :

```
package test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.xdocreport.XDocReportView;

import fr.opensagres.xdocreport.converter.MimeMapping;

public class MyMyXDocReportView extends XDocReportView {

	@Override
	protected String getContentDisposition(String reportId,
			MimeMapping mimeMapping, HttpServletRequest request) {
		String uniqueReportId = reportId + System.currentTimeMillis();
		return super.getContentDisposition(uniqueReportId, mimeMapping, request);
	}
}
```

and use it in your bean like this :

```
<bean id="reportConfiguration"
class="fr.opensagres.xdocreport.samples.reporting.springmvc.report.DocxProjectWithVelocityListConfiguration" />

<bean id="docxReport"
 class="test.MyXDocReportView"
 p:url="classpath:fr/opensagres/xdocreport/samples/reporting/springmvc/report/DocxProjectWithVelocityList.docx"
 p:templateEngineId="Velocity" >
   <property name="configuration" ref="reportConfiguration" /> 
</bean>
```