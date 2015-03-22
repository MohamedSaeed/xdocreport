[XDocReport 1.0.0](XDocReport100.md) provides a new simple API to generate reporting with the helper fr.opensagres.xdocreport.document.[XDocReport](http://code.google.com/p/xdocreport/source/browse/document/fr.opensagres.xdocreport.document/src/main/java/fr/opensagres/xdocreport/document/XDocReport.java) class.

# Report processes #

Before explaining how to use this new API, you must understand the report process generation which is composed with 2 steps :

  * load of the report template.
  * generate report.

## Load of the report template ##

To load a report template, XDocReport needs 3 datas :

  * the (docx, odt, etc) input stream. This data is required.
  * the template engine (Velocity, Freemarker). This data is required.
  * the fields metadata to manage for instance the lazy loop for table, the dynamic image etc. This data is not required.

## Generate report ##

Once the report template is loaded you can generate the report by merging Java context values with the report template.

# Simple API to generate report #

## Without cache ##

In this case the template report is every time loaded before the report generation. This mean is not good for performance.

### Generate report ###

```
// the 3 data used to load template report
InputStream in = new FileInputStream( new File( "template.docx" ) );
String templateEngineKind = TemplateEngineKind.Freemarker.name();
FieldsMetadata metadata = null;

// the context used to merge value with the template report
Map<String, Object> contextMap = new HashMap<String, Object>();
contextMap.put( "name", "world" );

// generate report
OutputStream out = new FileOutputStream( new File( "report.docx" ) );
XDocReport.generateReport( in, templateEngineKind, metadata, contextMap, out );
```

NOTE: In this sample you must add the well docx JARs.

### Generate report and convert it to PDF/XHTML ###

```
// the 3 data used to load template report
InputStream in = new FileInputStream( new File( "template.docx" ) );
String templateEngineKind = TemplateEngineKind.Freemarker.name();
FieldsMetadata metadata = null;

// the context used to merge value with the template report
Map<String, Object> contextMap = new HashMap<String, Object>();
contextMap.put( "name", "world" );

// generate report and convert it to PDF
OutputStream out = new FileOutputStream( new File( "report.pdf" ) );
Options options = Options.getTo(ConverterTypeTo.PDF);
XDocReport.generateReportAndConvert(  in, templateEngineKind, metadata, contextMap, options, out );
```

NOTE: In this sample you must add the well docx PDF converter JARs.

## With cache ##

You can improve a lot the performance of the report generation by doing one time the load of the template report and cache it the report. To do that you can use

  * controller.
  * report loader.

### With Controller ###

To use controller, you must create a class which implements the fr.opensagres.xdocreport.document.dispatcher.**IXDocReportController** per template report.

Here a sample controller class  :

```
package test;

import java.io.IOException;
import java.io.InputStream;

import fr.opensagres.xdocreport.document.dispatcher.AbstractXDocReportController;
import fr.opensagres.xdocreport.document.dispatcher.IXDocReportController;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class ReportLoaderForOneReport
    extends AbstractXDocReportController
{

    public static final IXDocReportController INSTANCE = new ReportLoaderForOneReport();

    public ReportLoaderForOneReport()
    {
        super( TemplateEngineKind.Freemarker, null );
    }

    @Override
    public InputStream getSourceStream()
        throws IOException
    {
        return new FileInputStream( new File( "template.docx" ) );
    }

    @Override
    protected FieldsMetadata createFieldsMetadata()
    {
        return null;
    }

}
```

#### Generate report ####

Here a sample which uses the controller class to generate 2 reports :

```
// the 3 data used to load template report
String reportId = "MyReportId";
IXDocReportController controller = ReportLoaderForOneReport.INSTANCE;

// generate the first report : here the template report is loaded
Map<String, Object> context1Map = new HashMap<String, Object>();
context1Map.put( "name", "world" );
OutputStream out1 = new FileOutputStream( new File( "report.docx" ) );
XDocReport.generateReport( reportId, controller, context1Map, out1 );

// generate the second report : here the template report is already loaded, the report generation time is improved
Map<String, Object> context2Map = new HashMap<String, Object>();
context2Map.put( "name", "people" );
OutputStream out2 = new FileOutputStream( new File( "report2.docx" ) );
XDocReport.generateReport( reportId, controller, context2Map, out2 );
```

#### Generate report and convert it to PDF/XHTML ####

```
// the 3 data used to load template report
String reportId = "MyReportId";
IXDocReportController controller = ReportLoaderForOneReport.INSTANCE;

// PDF options
Options options = Options.getTo( ConverterTypeTo.PDF );

// generate the first report and convert it to PDF : here the template report is loaded
Map<String, Object> context1Map = new HashMap<String, Object>();
context1Map.put( "name", "world" );
OutputStream out1 = new FileOutputStream( new File( "report1.pdf" ) );            
XDocReport.generateReportAndConvert( reportId, controller, context1Map, options, out1 );

// generate the second report and convert it to PDF : 
// here the template report is already loaded, the report generation time is improved
Map<String, Object> context2Map = new HashMap<String, Object>();
context2Map.put( "name", "people" );
OutputStream out2 = new FileOutputStream( new File( "report2.docx" ) );
XDocReport.generateReportAndConvert( reportId, controller, context2Map, options, out2 );
```

### With ReportLoader ###

To use report loader, you must create a class which implements the fr.opensagres.xdocreport.document.dispatcher.**IXDocReportLoader** which can manage your whole template report.

Here a sample report loader class  :

```
package test;

import java.io.IOException;
import java.io.InputStream;

import fr.opensagres.xdocreport.document.dispatcher.IXDocReportLoader;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class ReportLoaderForAllReport
    implements IXDocReportLoader
{

    public static final IXDocReportLoader INSTANCE = new ReportLoaderForAllReport();

    @Override
    public InputStream getSourceStream( String reportId )
        throws IOException
    {
        if ( "MyReportId".equals( reportId ) )
        {
            return new FileInputStream( new File( "template.docx" ) );
        }
        // here manage other report
        return null;
    }

    @Override
    public String getTemplateEngineKind( String reportId )
    {
        if ( "MyReportId".equals( reportId ) )
        {
            return TemplateEngineKind.Freemarker.name();
        }
        // here manage other report
        return null;
    }

    @Override
    public FieldsMetadata getFieldsMetadata( String reportId )
    {
        if ( "MyReportId".equals( reportId ) )
        {
            return null;
        }
        // here manage other report
        return null;
   }

}
```

#### Generate report ####

Here a sample which uses the report loader class to generate 2 reports :

```
// the 3 data used to load template report
String reportId = "MyReportId";
IXDocReportLoader reportLoader = ReportLoaderForAllReport.INSTANCE;

// generate the first report : here the template report is loaded
Map<String, Object> context1Map = new HashMap<String, Object>();
context1Map.put( "name", "world" );
OutputStream out1 = new FileOutputStream( new File( "report.docx" ) );
XDocReport.generateReport( reportId, reportLoader, context1Map, out1 );

// generate the second report : here the template report is already loaded, the report generation time is
// improved
Map<String, Object> context2Map = new HashMap<String, Object>();
context2Map.put( "name", "people" );
OutputStream out2 = new FileOutputStream( new File( "report2.docx" ) );
XDocReport.generateReport( reportId, reportLoader, context2Map, out2 );
```

#### Generate report and convert it to PDF/XHTML ####

```
// the 3 data used to load template report
String reportId = "MyReportId";
IXDocReportLoader reportLoader = ReportLoaderForAllReport.INSTANCE;

// PDF options
Options options = Options.getTo( ConverterTypeTo.PDF );

// generate the first report and convert it to PDF : here the template report is loaded
Map<String, Object> context1Map = new HashMap<String, Object>();
context1Map.put( "name", "world" );
OutputStream out1 = new FileOutputStream( new File( "report1.pdf" ) );
XDocReport.generateReportAndConvert( reportId, reportLoader, context1Map, options, out1 );

// generate the second report and convert it to PDF : here the template report is already loaded, the report
// generation time is improved
Map<String, Object> context2Map = new HashMap<String, Object>();
context2Map.put( "name", "people" );
OutputStream out2 = new FileOutputStream( new File( "report2.docx" ) );
XDocReport.generateReportAndConvert( reportId, reportLoader, context2Map, options, out2 );
```