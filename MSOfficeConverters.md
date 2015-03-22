# MS Office Converters #

## Docx Converters ##

Here the list of XDocReport fr.opensagres.xdocreport.converter.**IConverter** implemented for docx which can be used with ConverterRegistry? :

### Stable Docx Converter ###

|ID|Output format | OSGi fragment (Git Link) | Dependencies| Status|
|:-|:-------------|:-------------------------|:------------|:------|
|Docx 2 PDF via POI-IText|PDF|[fr.opensagres.xdocreport.converter.docx.xwpf](http://code.google.com/p/xdocreport/source/browse/#git%2Fconverter%2Ffr.opensagres.xdocreport.converter.docx.xwpf)| Apache POI + iText|Stable|
|Docx 2 XHTML via POI|XHTML|[fr.opensagres.xdocreport.converter.docx.xwpf](http://code.google.com/p/xdocreport/source/browse/#git%2Fconverter%2Ffr.opensagres.xdocreport.converter.docx.xwpf)| Apache POI|Stable|
|Docx 2 PDF via docx4j|XHTML|[fr.opensagres.xdocreport.converter.docx.docx4j](http://code.google.com/p/xdocreport/source/browse/#git%2Fconverter%2Ffr.opensagres.xdocreport.converter.docx.docx4j)| docx4j + FOP|Stable|
|Docx 2 XHTML via docx4j|XHTML|[fr.opensagres.xdocreport.converter.docx.docx4j](http://code.google.com/p/xdocreport/source/browse/#git%2Fconverter%2Ffr.opensagres.xdocreport.converter.docx.docx4j)| docx4j|Stable|

After several experimentation (see converters below), we decided to keep "Docx 2 PDF via POI-IText" because "Docx 2 PDF via FOP" converter are more slowly than "Docx 2 PDF via POI-IText" because steps are docx -> XSL-FO -> FO -> FOP.

[fr.opensagres.xdocreport.converter.docx.xwpf](http://code.google.com/p/xdocreport/source/browse/#git%2Fconverter%2Ffr.opensagres.xdocreport.converter.docx.xwpf) is based on the [org.apache.poi.xwpf.converter](XWPFConverter.md) which is enable to convert :

  * docx to PDF.
  * docx to XHTML.

Here the process of [org.apache.poi.xwpf.converter](XWPFConverter.md) converter for PDF conversion :

  1. load docx in Java Structure [Apache POI - HWPF](http://poi.apache.org/hwpf/index.html) org.apache.poi.xwpf.usermodel.**XWPFDocument**
  1. loop for each POI Java model (paragraph, table, etc) and create for each POI model a PDF [iText](http://itextpdf.com/) model.
### Experimental Docx Converter ###

Here converters use FOP and XSL to transform document.xml (by using styles.xml) from the docx to FO or XHTML.

|ID|Output format | OSGi fragment (Git Link) | Status|
|:-|:-------------|:-------------------------|:------|
|Docx 2 PDF via FOP|PDF|[fr.opensagres.xdocreport.converter.fop.docx](http://code.google.com/p/xdocreport/source/browse/#git%2Fsandbox%2Ffr.opensagres.xdocreport.converter.fop.docx)|Experimental |
|Docx 2 FO via XSL-FO|FO|[fr.opensagres.xdocreport.converter.fop.docx](http://code.google.com/p/xdocreport/source/browse/#git%2Fsandbox%2Ffr.opensagres.xdocreport.converter.fop.docx)|Experimental |
|Docx 2 XHTML via XSL|XHTML|[fr.opensagres.xdocreport.converter.fop.docx](http://code.google.com/p/xdocreport/source/browse/#git%2Fsandbox%2Ffr.opensagres.xdocreport.converter.fop.docx)|Experimental |