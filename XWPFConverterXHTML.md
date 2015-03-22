# XWPFDocument 2 XHTML #

[org.apache.poi.xwpf.converter.xhtml](http://code.google.com/p/xdocreport/source/browse/#git%2Fthirdparties-extension%2Forg.apache.poi.xwpf.converter.xhtml) provides the DOCX 2 XHTML converter based on Apache POI XWPF.

You can test this converter with the REST Converter service http://xdocreport-converter.opensagres.cloudbees.net/

# Download #

Download this converter with :

  * maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.apache.poi.xwpf.converter.xhtml</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

where XDOCREPORT\_VERSION is the XDocReport version (ex : 1.0.0).

  * download the [docx.converters-xxx-sample.zip](http://code.google.com/p/xdocreport/downloads/list)

# Sample #

Here a sample to convert org.apache.poi.xwpf.usermodel.**XWPFDocument** to XHTML format :

```
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;

...

// 1) Load DOCX into XWPFDocument
InputStream in= new FileInputStream(new File("HelloWord.docx"));
XWPFDocument document = new XWPFDocument(in);

// 2) Prepare XHTML options (here we set the IURIResolver to load images from a "word/media" folder)
XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("word/media")));

// 3) Convert XWPFDocument to XHTML
OutputStream out = new FileOutputStream(new File("HelloWord.htm"));
XHTMLConverter.getInstance().convert(document, out, options);
```

# XHTML Settings #

## Manage image ##

If your docx have images and you wish display in the HTML you must configure 2 things with the XHTMLOptions : :

  * set an implementation of **IImageExtractor**  : the image extractor gives you the capability to extract images from the docx and stores it as you wish (File system, DB, in the Memory, etc) extract the image.
  * set an implementation of **IURIResolver** :this resolver manages uri of image in the generated xhtml.

You can see a sample with our JUnit [XHTMLConverterTestCase](https://code.google.com/p/xdocreport/source/browse/thirdparties-extension/org.apache.poi.xwpf.converter.xhtml/src/test/java/org/apache/poi/xwpf/converter/xhtml/XHTMLConverterTestCase.java)