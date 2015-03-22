# XWPFDocument 2 PDF #

[org.apache.poi.xwpf.converter.pdf](http://code.google.com/p/xdocreport/source/browse/#git%2Fthirdparties-extension%2Forg.apache.poi.xwpf.converter.pdf) provides the DOCX 2 Pdf converter based on Apache POI XWPF and iText.

You can test this converter with the REST Converter service http://xdocreport-converter.opensagres.cloudbees.net/

# Download #

Download this converter with :

  * maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.apache.poi.xwpf.converter.pdf</artifactId>
  <version>${XDOCREPORT_VERSION}</version>
</dependency>
```

  * download the [docx.converters-${XDOCREPORT\_VERSION}-sample.zip](http://code.google.com/p/xdocreport/downloads/list)

where ${XDOCREPORT\_VERSION} is the XDocReport version (ex : 1.0.0).

# Sample #

Here a sample to convert org.apache.poi.xwpf.usermodel.**XWPFDocument** to Pdf format :

```
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;

...

// 1) Load DOCX into XWPFDocument
InputStream in= new FileInputStream(new File("HelloWord.docx"));
XWPFDocument document = new XWPFDocument(in);

// 2) Prepare Pdf options
PdfOptions options = PdfOptions.create();

// 3) Convert XWPFDocument to Pdf
OutputStream out = new FileOutputStream(new File("HelloWord.pdf"));
PdfConverter.getInstance().convert(document, out, options);
```

# Pdf Settings #

## Font encoding ##

```
// iText during conversion process has to access fonts with proper encoding. Otherwise some
// diactric characters may be not converted properly (missing from the resulting pdf)
// The converter uses underlying operating system encoding as the default value.
// It is also possible to set it explicitely
PdfOptions options = PdfOptions.create().fontEncoding("windows-1250");
```

## IFontProvider ##

Customize the creation of font (usefull for Chinese character).

## IPdfWriterConfiguration ##

[XDocReport 1.0.4](https://code.google.com/p/xdocreport/wiki/XDocReport104) provides fr.opensagres.xdocreport.itext.extension.**IPdfWriterConfiguration** which gives you the capability to customize the iText PDFWriter. Here a sample which set PDF/A Conformance level :

```
org.apache.poi.xwpf.converter.pdf.PdfOptions options = new PdfOptions();
options.setConfiguration( new IPdfWriterConfiguration()
{
            
  public void configure( PdfWriter writer )
  {
    writer.setPDFXConformance( PdfWriter.PDFA1A );
  }
});
PdfConverter.getInstance().convert( document, out, options );
```

## Use PdfOptions with IXDocReport#convert ##

If you wish to use org.apache.poi.xwpf.converter.pdf.PdfOptions with IXDocReport#convert, you must use  fr.opensagres.xdocreport.converter.Options#subOptions like this :

```
org.apache.poi.xwpf.converter.pdf.PdfOptions pdfOptions = ...
Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.XWPF).subOptions(pdfOptions);

IXDocReport report = ...
report.convert(context, options, out);
```

# Limitations #

  * draw/shape graphics are not converted.
  * have some bugs with tab stop.
  * table border should be improved.
  * page number are not managed.