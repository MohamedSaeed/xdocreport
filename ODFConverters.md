# ODF Converters #

## ODT Converters ##

### Stable ODT Converters ###

Here the list of XDocReport fr.opensagres.xdocreport.converter.**IConverter** implemented for odt which can be used with ConverterRegistry :

|Output format | OSGi fragment | Dependencies |Status|
|:-------------|:--------------|:-------------|:-----|
|XHTML |[fr.opensagres.xdocreport.converter.odt.odfdom](http://code.google.com/p/xdocreport/source/browse/#git%2Fconverter%2Ffr.opensagres.xdocreport.converter.odt.odfdom)| [ODF-DOM Converter](http://code.google.com/p/xdocreport/wiki/ODFDOMConverter)|Stable| |
|PDF |[fr.opensagres.xdocreport.converter.odt.odfdom](http://code.google.com/p/xdocreport/source/browse/#git%2Fconverter%2Ffr.opensagres.xdocreport.converter.odt.odfdom)|[ODF-DOM Converter](http://code.google.com/p/xdocreport/wiki/ODFDOMConverter) with iText|Stable| |

Here converters uses [ODF-DOM Converter](http://code.google.com/p/xdocreport/wiki/ODFDOMConverter) which loads odt in Java Structure ODFDOM org.odftoolkit.odfdom.doc.**OdfDocument** and generation is done by using this model.

Please read [Download](http://code.google.com/p/xdocreport/wiki/Download) section to use those converters.

### Expermimental ODT Converters ###

Those converter are just expermiental.

|ID|Output format | OSGi fragment | Status|
|:-|:-------------|:--------------|:------|
|ODT 2 PDF via FOP|PDF|[fr.opensagres.xdocreport.converter.fop.odt](http://code.google.com/p/xdocreport/source/browse/#git%2Fsandbox%2Ffr.opensagres.xdocreport.converter.fop.odt)|Experimental |
|ODT 2 FO via XSL-FO|FO|[fr.opensagres.xdocreport.converter.fop.odt](http://code.google.com/p/xdocreport/source/browse/#git%2Fsandbox%2Ffr.opensagres.xdocreport.converter.fop.odt)|Experimental |
|ODT 2 XHTML via XSL|XHTML|[fr.opensagres.xdocreport.converter.fop.odt](http://code.google.com/p/xdocreport/source/browse/#git%2Fsandbox%2Ffr.opensagres.xdocreport.converter.fop.odt)|Experimental |

Here converters use XSL to transform content.xml (by using styles.xml) from the odt to FO or XHTML.

"ODT 2 PDF via FOP" converter are a litlle slowly because steps are odt -> XSL-FO -> FO -> FOP. So we decided to give our energy to develop "ODT 2 PDF via ODFDOM-IText".