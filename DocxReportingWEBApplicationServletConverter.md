# Converter - WEB Application - Servlet Mode #

In this section we convert the generated docx to another format (PDF/XHTML) like explained  at [Java Main Converter section](DocxReportingJavaMainConverter.md) but in WEB context.

Converter in WEB Application doesn't require Java code, you must just :

  * [Add converter JARs](#1._Add_XDocReport_JARs.md) in your **WEB-INF/lib** according the converter implementation that you wish use.
  * [Add HTTP parameter converter](#2._Test_WEB_Reporting.md) in the URL reporting by specifying the converter id.

For instance at the end of this section the URL [http://localhost:8080/docxandvelocity-converter/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport&converter=PDF\_XWPF](http://localhost:8080/docxandvelocity-converter/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport&converter=PDF_XWPF)

where docx report is :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity.png)

will generate this PDF :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBConverter2PDF.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBConverter2PDF.png)

# 1. Add XDocReport JARs #

Add the same JARs in your **WEB-INF/lib** explained at [Java Main Converter section](DocxReportingJavaMainConverter#1._Add_XDocReport_JARs.md).

# 2. Test WEB Reporting #

At this step you can test your WEB Application. Start your server. Following URLs assume that base URL is [http://localhost:8080/docxandvelocity-converter/](http://localhost:8080/docxandvelocity-converter/)

In this sample the report servlet will be available with [http://localhost:8080/docxandvelocity-converter/reportServlet](http://localhost:8080/docxandvelocity-converter/reportServlet)

Here we use **fr.opensagres.xdocreport.converter.docx.xwpf** converter implementation which provides 2 converters :

| **Converter ID** | **Description** |
|:-----------------|:----------------|
| PDF\_XWPF | Convert docx 2 PDF via IText by using POI _XWPFDocument_ |
| XHTML\_XWPF | Convert docx 2 XHTML via POI by using POI _XWPFDocument_ |

The converter ID follow the pattern
`ToType '_' Via`

Where ToType is the format like PDF, XHTML and Via the means which is used for the conversion (FOP, IText...).

## GET URLs ##

If you go at URL [http://localhost:8080/docxandvelocity-converter/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport](http://localhost:8080/docxandvelocity-converter/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport)

This URL generate this docx :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_Out.png)

To convert this generated report docx 2 another format, you must just add HTTP parameter converter with converter id. For instance if you wish convert the generated report 2 PDF (via IText) go at the URL [http://localhost:8080/docxandvelocity-converter/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport&converter=PDF\_XWPF](http://localhost:8080/docxandvelocity-converter/reportServlet?reportId=DocxProjectWithVelocity.docx&name=XDocReport&converter=PDF_XWPF)

This URL generate this PDF :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBConverter2PDF.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBConverter2PDF.png)

Note : you can notice that PDF generation is very slow for the first time. But don't panic, it's just for the first PDF generation. Retry the PDF generation and you will see that performance is better.

## POST URLs ##

You can modify the [reportServlet.jsp](DocxReportingWEBApplicationServlet#POST_URLs.md) to add HTML combo converter :

```
<!--  converter HTTP parameter -->
<tr>
	<td>Converter :</td>
	<td><select name="converter">
		<option value="">-- No conversion --</option>
		<option value="PDF_XWPF">2 PDF via IText</option>
		<option value="XHTML_XWPF">2 XHTML via XWPF (POI)</option>
	</select></td>
</tr>
```

Here the full form HTML :

```
<form name="reportServletForm"
	action="<%=request.getContextPath()%>/reportServlet" method="post">
<table>
	<!--  Data Model -->
	<tr>
		<td>Project (data model) :</td>
		<td><input type="text" name="name" value="XDocReport" /></td>
	</tr>
	<tr>
		<td>Nb developers (data model) :</td>
		<td><select name="nbDevelopers">
			<option value="0">0</option>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
		</select></td>
	</tr>
	<!--  reportId HTTP parameter -->
	<tr>
		<td>Report :</td>
		<td><select name="reportId">
			<option value="DocxProjectWithVelocity.docx">DocxProjectWithVelocity.docx</option>
			<option value="DocxProjectWithVelocityList.docx">DocxProjectWithVelocityList.docx</option>
		</select></td>
	</tr>
	<!--  converter HTTP parameter -->
	<tr>
		<td>Converter :</td>
		<td><select name="converter">
			<option value="">-- No conversion --</option>
			<option value="PDF_XWPF">2 PDF via IText</option>
			<option value="XHTML_XWPF">2 XHTML via XWPF (POI)</option>
		</select></td>
	</tr>
	<!--  processState HTTP parameter -->
	<tr>
		<td>Process state :</td>
		<td><select name="processState">
			<option value="original">original</option>
			<option value="preprocessed">preprocessed</option>
			<option value="generated" selected="selected">generated</option>
		</select></td>
	</tr>
	<!--  dispatch HTTP parameter -->
	<tr>
		<td>Dispatch :</td>
		<td><select name="dispatch">
			<option value="download">download</option>
			<option value="view">view</option>
		</select></td>
	</tr>
	<!--  entryName HTTP parameter -->
	<tr>
		<td>Entry name :</td>
		<td><select name="entryName">
			<option value=""></option>
			<option value="word/document.xml">word/document.xml</option>
		</select></td>
	</tr>
	<!-- Generate report -->
	<tr>
		<td colspan="2"><input type="submit" value="OK"></td>
	</tr>
</table>
</form>
```

If you go at [http://localhost:8080/docxandvelocity-converter/reportServlet.jsp](http://localhost:8080/docxandvelocity-converter/reportServlet.jsp) you will see this form :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormConverterServlet.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBFormConverterServlet.png)

You can select "2 PDF via IText" converter and click on OK button to generate the PDF :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBConverter2PDF.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocity_WEBConverter2PDF.png)