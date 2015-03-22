# OdfDocument 2 PDF #

[org.odftoolkit.odfdom.converter.pdf](http://code.google.com/p/xdocreport/source/browse/#git%2Fthirdparties-extension%2Forg.odftoolkit.odfdom.converter.pdf) provides the ODF 2 PDF converter based on ODF-DOM and iText.

You can test this converter with the REST Converter service http://xdocreport-converter.opensagres.cloudbees.net/

# Download #

Download this converter with :

  * maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.odftoolkit.odfdom.converter.pdf</artifactId>
  <version>${XDOCREPORT_VERSION}</version>
</dependency>
```

  * download the [odt.converters-${XDOCREPORT\_VERSION}-sample.zip](http://code.google.com/p/xdocreport/downloads/list)

where ${XDOCREPORT\_VERSION} is the XDocReport version (ex : 1.0.0).

# Sample #

Here a sample to convert odftoolkit.odfdom.doc.OdfDocument to PDF format :

```
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;

...

// 1) Load ODT into ODFDOM OdfTextDocument 
InputStream in= new FileInputStream(new File("HelloWord.odt"));
OdfTextDocument document = OdfTextDocument.loadDocument(in);

// 2) Prepare Pdf options
PdfOptions options = PdfOptions.create();

// 3) Convert OdfTextDocument to PDF via IText
OutputStream out = new FileOutputStream(new File("HelloWord.pdf"));
PdfConverter.getInstance().convert(document, out, options);
```

# Pdf settings #

## Font encoding ##

```
// iText during conversion process has to access fonts with proper encoding. Otherwise some
// diactric characters may be not converted properly (missing from the resulting pdf)
// The default encoding is unicode, which should be correct for most configurations.
// It is also possible to set it explicitely
PdfOptions options = PdfOptions.create().fontEncoding("windows-1250");
```

## IFontProvider ##

Customize the creation of font (usefull for Chinese character).

## IPdfWriterConfiguration ##

[XDocReport 1.0.4](https://code.google.com/p/xdocreport/wiki/XDocReport104) provides fr.opensagres.xdocreport.itext.extension.**IPdfWriterConfiguration** which gives you the capability to customize the iText PDFWriter. Here a sample which set PDF/A Conformance level :

```
org.odftoolkit.odfdom.converter.pdf.PdfOptions options = new PdfOptions();
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

If you wish to use org.odftoolkit.odfdom.converter.pdf.PdfOptions with IXDocReport#convert, you must use fr.opensagres.xdocreport.converter.Options#subOptions like this :
```
org.odftoolkit.odfdom.converter.pdf.PdfOptions pdfOptions = ...
Options options = Options.getTo(ConverterTypeTo.PDF).via(ConverterTypeVia.ODFDOM).subOptions(pdfOptions);

IXDocReport report = ...
report.convert(context, options, out);
```

# Limitations #
Odf->pdf converter uses iText library internally. Odf constructs (paragraphs, tables, lists, ...) are translated to iText equivalents. After translation iText generates pdf. Not every odf element can be translated to iText equivalent because of iText architectural limitations. Try avoid the following constructs in an odf document:
  * graphics frame and text flow around graphics - generally not supported with the exception of images. Graphics frame content is ignored and will not appear in translated pdf. This may also disrupt text flow around the graphics frame. The exception is an image in commonly used format (bmp, png, jpeg, gif, tiff, wmf). An image will be shown in pdf. However its positioning and text flow around an image may be different than in the original document.
  * tab stops are not supported (except of a limited support in a table of content). Tab stops used in text will be ignored in translated pdf. The flow of text will be continuous and text fragments will not be aligned to tab stops. As a special exception page number in a table of contents will be right aligned as expected.
  * table border different than single line (ie. double line border) is not supported. Double line borders will be replaced by single line borders. This may be fixed in the future