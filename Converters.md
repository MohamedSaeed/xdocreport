# Converter - Overview #

XDocReport converters give you the capability to convert **odt, docx files to another format (PDF, XHTML...)**. You can use those converters:

  * when you generate a (docx, odt) report and you wish **convert the generated report to another format (PDF, XHTML)**.
  * when you wish **only convert an odt, docx to another format (PDF, XHTML)**.

You can test our converters in the live demo at http://xdocreport-converter.opensagres.cloudbees.net/ which uses [REST Converter services](RESTSOAPConverters.md).

## Converter only ##

If you wish just convert :

  * odt->xhtml or odt->pdf, please see [ODF Converters](ODFConverters.md).
  * docx->xhtml or docx->pdf, please see [MS Office Converters](MSOfficeConverters.md)

## Converter only with ConverterRegistry ##

If you wish to manage several converters in your application (odt->pdf, docx->pdf etc), XDocReport provides a commons interface for converter fr.opensagres.xdocreport.converter.**IConverter**. Each implementation of IConverter are registered in the ConverterRegistry.

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

ConverterRegistry is modular (you can register any converters), you must understand that to use it, you must set the well JARs which provides IConverter implementation and JARs dependencies in your classpath. Please see [Download](Download.md) section for more information.

XDocReport provides several implementation of IConverter like

  * **fr.opensagres.xdocreport.converter.docx.xwpf** uses [XWPF Converter](XWPFConverter.md) which implements the docx converter with Apache POI and iText (for PDF).
  * **fr.opensagres.xdocreport.converter.docx.docx4j** which implements the docx converter with docx4j and XSL-FO.
  * **fr.opensagres.xdocreport.converter.odt.odfdom** uses [ODF-DOM Converter](ODFDOMConverter.md) which implements the odt converter with ODFDOM and iText (for PDF).

Note that those converters implementation are pure Java and not need to install some LibreOffice or MS Word.

But it's possible to implement other converter like [JODConverter](http://code.google.com/p/jodconverter/) which is powerfull but need to install LibreOffice.

## Report generation with Converter ##

The ConverterRegistry is used when a report must be converted to another format. Here a sample to generate report from ODT with Velocity and convert it to PDF format.

```
// 1) Load ODT file and set Velocity template engine and cache it to the registry			
ITemplateEngine templateEngine = VelocityTemplateEngine.getDefault();
InputStream in= new FileInputStream(new File("ODTHelloWordWithVelocity.odt"));
IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in,templateEngine);

// 2) Create Java model context 
IContext context = report.createContext();
context.put("name", "world");

// 3) Set PDF as format converter
Options options = Options.getTo(ConverterTypeTo.PDF);

// 3) Generate report by merging Java model with the ODT and convert it to PDF
OutputStream out = new FileOutputStream(new File("ODTHelloWordWithVelocity_Out.odt"));
report.convert(context, options, out);
```

# Converter Registry #

The 2 samples code wich use converter are based on **ConverterRegistry.getRegistry()** singleton to retrieve well converter switch Options from/to/via.

This registry must be filled with converter implementation. To use an implementation converter, just set the JAR converter in your classpath.

# Converter - Architecture #

XDocReport provides an OSGi bundle [fr.opensagres.xdocreport.converter](https://xdocreport.googlecode.com/svn/trunk/fr.opensagres.xdocreport.converter) which provides API for converter.

Implementation of converter (ODT 2 PDF via XSL-FO for instance) are OSGi fragments linked to the OSGi bundle [fr.opensagres.xdocreport.converter](https://xdocreport.googlecode.com/svn/trunk/fr.opensagres.xdocreport.converter).

**Important** : XDocReport can work without OSGi!!! It defines just MANIFEST.MF file in the OSGi bundle and fragments.

# Converter - Implementation #

XDocReport implements several converter for odt and docx :

  * please go at [ODF Converters](ODFConverters.md) for odt conversion.
  * please go at [MS Office Converters](MSOfficeConverters.md) for docx conversion.

You can test it if you wish with the online WEB Application http://xdocreport.opensagres.cloudbees.net/ and see registered converters at
http://xdocreport.opensagres.cloudbees.net/admin.jsp

You can test our converters in the live demo at http://xdocreport-converter.opensagres.cloudbees.net/ which uses [REST Converter services](RESTSOAPConverters.md).