# What is a XDocReport Dumper? #

Since [XDocReport 1.0.3](XDocReport103.md), XDocReport provides the capability to dump a report. A dumper is enable to dump the whole context when a report is generated :

  * the document template of the report.
  * the data context which can be exported as JSON.
  * the fields metadata which can be exported as XML.
  * generates a Java Main class which generates a report by using the dumped document templates, data context and fields metadata.

## I don't understand, a sample dumper please ##

Ok, take a sample with this report :

http://xdocreport.opensagres.cloudbees.net/processReport?reportId=DocXHelloWorldWithFreemarker.docx&name=world&dispatch=load&converter=PDF_XWPF

The template DocXHelloWorldWithFreemarker.docx contains Hello ${name}. To replace name variable you can fill it with the HTTP parameter name like this :

http://xdocreport.opensagres.cloudbees.net/processReport?reportId=DocXHelloWorldWithFreemarker.docx&name=world

Now you can generate a dump about this report and context :

  * in a Java Main with this url :
http://xdocreport.opensagres.cloudbees.net/processReport?reportId=DocXHelloWorldWithFreemarker.docx&name=world&dumper=JavaMain

  * in a Eclipse Project with this url :
http://xdocreport.opensagres.cloudbees.net/processReport?reportId=DocXHelloWorldWithFreemarker.docx&name=world&dumper=EclipseProject

  * in a Maven Project with this url :
http://xdocreport.opensagres.cloudbees.net/processReport?reportId=DocXHelloWorldWithFreemarker.docx&name=world&dumper=MavenProject

# Why dump a report? #

There is 2 reasons why XDocReport team has developped dumper feature :

## Application in production and Maintenance ##

Imagine that you use XDocReport in an application in production. A user creates a bug with a report (reports crashes, reports is not well generated), and you must fixes the problem.

To fix this issue, you must retrieve :

  * the data coming from the application in production mode.
  * the template document from the database.

Retrieving this context can be very hard (you have not the right to export the database, you must export the whole database of the production because the data model is very complex).

The XDocReport dumper can be help you in this situation. You go at in the application in production mode and instead of generate a report, you generate a dump (eclipse project, simple Java main, maven project, etc) with the XDocReport dump feature.

## Reporting issues for XDocReport ##

When XDocReport user creates reporting XDocReport issues, it's sometimes hard for them to attach in the issue the context (hard to get the dat amodel) which causes the problem and they must creates a Java main with their case which causes the problem.

The XDocReport dumper can be help them in this situation.

So when you create an issue about reporting please attach a "maven project" dump.

# How it works? #

## Dumper kind ##

At first, XDocReport provides 3 kinds of dumper (but you can create your own dumper if you need) :

### Java Main dumper ###

The **Java Main dumper** dumper generates a simply Java Main which generates a report :

  * the template document (binary) is encoded as base64 String.
  * the data context is encoded  as JSON.
  * the fields metadata is encoded as XML.

### Eclipse project dumper ###

The **Eclipse project dumper** generates an Eclipse project with src folder which contains :

  * a simply Java Main which generates a report.
  * the template document as file.
  * the data context as JSON file.
  * the fields metadata as XML file.

### Maven project dumper ###

The **Maven project dumper** : TODO.

## Dumper code ##

## In WebApp ##

If you use the XDocReport servlet fr.opensagres.xdocreport.document.web.**AbstractProcessXDocReportServlet**, you can call the servlet with HTTP request dumper parameter :

  * dumper=JavaMain to generate Java main dumper.
  * dumper=EclipseProject to generate Eclipse project dumper.
  * dumper=MavenProject to generate Mavenproject dumper.

You can see that in the Web App demo at [http://xdocreport.opensagres.cloudbees.net/processReport.jsp](http://xdocreport.opensagres.cloudbees.net/processReport.jsp).

If you select a report, the dump combo appears and you can select the dump type (Java main, Eclipse project, Maven project etc) :

Click on Report button will generate the dump.



## with Java code ##

To generate report, you do like this :

```
IXDocReport report = ...
IContext context = report.createContext();
// populate context

// generate the report
OutputStream out = ...
report.process( context, options, out );
```

To generate the dump report with Java Main :

```
IXDocReport report = ...

// IMPORTANT : cache the original document to use dump.
report.setCacheOriginalDocument( true );

IContext context = report.createContext();
// populate context

// dump the report as Java Main in the console.
OutputStream out = System.out;
JavaMainDumperOptions options = new JavaMainDumperOptions();
report.dump( context, options, out );
```