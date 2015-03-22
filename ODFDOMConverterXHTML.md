# OdfDocument 2 XHTML #

[org.odftoolkit.odfdom.converter.xhtml](http://code.google.com/p/xdocreport/source/browse/#git%2Fthirdparties-extension%2Forg.odftoolkit.odfdom.converter.xhtml) provides the ODF 2 XHTML converter based on ODF-DOM.

You can test this converter with the REST Converter service http://xdocreport-converter.opensagres.cloudbees.net/

# Download #

Download this converter with :

  * maven :

```
<dependency>
  <groupId>fr.opensagres.xdocreport</groupId>
  <artifactId>org.odftoolkit.odfdom.converter.xhtml</artifactId>
  <version>XDOCREPORT_VERSION</version>
</dependency>
```

where XDOCREPORT\_VERSION is the XDocReport version (ex : 1.0.0).

  * download the [odt.converters-xxx-sample.zip](http://code.google.com/p/xdocreport/downloads/list)

# Sample #

Here a sample to convert odftoolkit.odfdom.doc.OdfDocument to XHTML format :

```
import org.odftoolkit.odfdom.converter.xhtml.XHTMLOptions;
import org.odftoolkit.odfdom.converter.xhtml.XHTMLConverter;

...

// 1) Load ODT into ODFDOM OdfTextDocument 
InputStream in= new FileInputStream(new File("HelloWord.odt"));
OdfTextDocument document = OdfTextDocument.loadDocument(in);

// 2) Prepare XHTML options (here we set the IURIResolver to load images from a "Pictures" folder)
XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("Pictures")));

// 3) Convert OdfTextDocument to XHTML
OutputStream out = new FileOutputStream(new File("HelloWord.htm"));
XHTMLConverter.getInstance().convert(document, out, options);
```

# XHTML Settings #

  * IURIResolver : manage uri of image in the generated xhtml.
  * IImageExtractor  : extract the image.