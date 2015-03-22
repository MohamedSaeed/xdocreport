# What is XDocReport? #

**XDocReport** means **XML Document reporting**. It's Java API to merge XML document created with [MS Office](http://office.microsoft.com/fr-fr/) (docx, pptx) or [OpenOffice](http://www.openoffice.org/) (odt), [LibreOffice](http://www.libreoffice.org/) (odt) with a Java model to generate report and convert it if you need to another format (PDF, XHTML...). With XDocReport :

  1. you **create a document with MS Word (docx, pptx) or OpenOffice (odt, ods)**
  1. you **use [Velocity](http://velocity.apache.org/) or [Freemarker](http://freemarker.sourceforge.net/) syntax to set variable to replace**. For instance you can type in your document (Velocity syntax is used here) :
`Hello $name !`
  1. **replace variable by merging document with 'world' value coming from Java model** to generate report with the content :

`Hello world !`

Here a scheme which show you XDocReport processes to generate report from ODT file :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTHelloWordWithVelocityProcess.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTHelloWordWithVelocityProcess.png)

# XDocReport in Action #

You can try and play with XDocReport in the online

  * If you wish to generate reports from DOCX/ODT, you can play with the WEB Application at http://xdocreport.opensagres.cloudbees.net/
  * If you wish convert your DOCX to PDF/XHTML or ODT to PDF/XHTML, you can play with the Converter WEB Application at http://xdocreport-converter.opensagres.cloudbees.net/
  * Eclipse RAP Application at http://xdocreport-rap.opensagres.cloudbees.net/xdocreport?startup=fr.opensagres.xdocreport.eclipse.ui.application

## XDocReport  features ##

XDocReport is **modular** and very light (no need to install MS Office, LibreOffice in your server). You can **choose the XML document type to manage (docx, odt...)** and **choose the template engine** to use switch the syntax used in the document and choose converter if you need to converet report generation to another format (PDF...).

  1. you create your report with :
    * OpenOffice : odt,ods format are supported.
    * MS Word : docx format are supported.
  1. you use template engine switch the syntax  your prefer:
    * Freemarker : `Hello ${name} !`
    * Velocity : `Hello $name !`
  1. reporting generation is improved by caching template (coming from zipped XML document entry) when a second generation is done.
  1. debug process of report generation.
  1. Loop for table : TODO improve it.
  1. Manage dynamic images : TODO develop it.
  1. Manage XDocReport in OSGi env : TODO develop it.
  1. Manage XDocReport in WEB env : implements AbstractXDocReportServlet.
  1. Convert to another format : XDocReport provides several converter (ODT 2 PDF, Docx 2 PDF...) to convert document. Converter can be used just to convert document or after report generation to convert report to another format.

XDocReport is **modular** means too :

  1. you can implement your own template engine. By default Velocity and Freemarker are implemented.
  1. you can implement your own converter. XDocReport implements today ODT 2 PDF (via ODFDOM+iText and Docx 2 PDF (via POI+iText).

# XDocReport API #

XDocReport give you the capability to :

  1. generate report source format 2 source format  (ex : ODT 2 ODT).
  1. convert document to another format (ex : ODT 2 PDF).
  1. generate report and convert it to another format than source format.

## Report Generation ##

Imagine you have ODTHelloWordWithVelocity.odt which contains :

`Hello $name!`

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTHelloWordWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTHelloWordWithVelocity.png)

Use this code :

```
// 1) Load ODT file and set Velocity template engine and cache it to the registry			
InputStream in= new FileInputStream(new File("ODTHelloWordWithVelocity.odt"));
IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);

// 2) Create Java model context 
IContext context = report.createContext();
context.put("name", "world");

// 3) Generate report by merging Java model with the ODT
OutputStream out = new FileOutputStream(new File("ODTHelloWordWithVelocity_Out.odt"));
report.process(context, out);
```

This sample generate the ODTHelloWordWithVelocity\_Out.odt report with this content :

`Hello world!`

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTHelloWordWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTHelloWordWithVelocity_Out.png)

## Converter ##

Here a sample to convert ODT to PDF format.

```
// 1) Create options ODT 2 PDF to select well converter form the registry
Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);

// 2) Get the converter from the registry
IConverter converter = ConverterRegistry.getRegistry().getConverter(options);

// 3) Convert ODT 2 PDF
InputStream in= new FileInputStream(new File("ODTHelloWord.odt"));
OutputStream out = new FileOutputStream(new File("ODTHelloWord2PDF.pdf"));
converter.convert(in, out, options);					
```

NOTE : by default the ODT 2 PDF converter is done with ODT->XSL-FO->FOP.

## Report generation with Converter ##

Here a sample to generate report from ODT with Velocity and convert it to PDF format.

```
// 1) Load ODT file and set Velocity template engine and cache it to the registry			
InputStream in= new FileInputStream(new File("ODTHelloWordWithVelocity.odt"));
IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,TemplateEngineKind.Velocity);

// 2) Create Java model context 
IContext context = report.createContext();
context.put("name", "world");

// 3) Set PDF as format converter
Options options = Options.getTo(ConverterTypeTo.PDF);

// 3) Generate report by merging Java model with the ODT and convert it to PDF
OutputStream out = new FileOutputStream(new File("ODTHelloWordWithVelocity_Out.odt"));
report.convert(context, options, out);
```

NOTE : XDocReport provides default converter, but you can implement your own converter if you need.