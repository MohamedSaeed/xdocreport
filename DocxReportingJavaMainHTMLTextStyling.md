# HTML Text Styling #

To style text with HTML syntax, you need tell to XDocReport that you wish to use HTML syntax for the field. You can

  * test the HTML text styling at http://xdocreport.opensagres.cloudbees.net/processReport?reportId=DocxTextStylingWithFreemarker.docx&dispatch=load&converter=PDF_XWPF
  * see demo at http://xdocreport.opensagres.cloudbees.net/textStyling.jsp?reportId=docx

To do that you must use FieldsMetadata like this :

```
FieldsMetadata metadata = report.createFieldsMetadata();
metadata.addFieldAsTextStyling("comments", SyntaxKind.Html);
```

You can find samples [DocxTextStylingWithFreemarker.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandfreemarker/src/fr/opensagres/xdocreport/samples/docxandfreemarker/DocxTextStylingWithFreemarker.java?repo=samples) and [DocxTextStylingWithVelocity.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandvelocity/src/fr/opensagres/xdocreport/samples/docxandvelocity/DocxTextStylingWithVelocity.java?repo=samples)

You can try it on the online demo :

  * click [here](http://xdocreport.opensagres.cloudbees.net/processReport?dispatch=load&reportId=DocxTextStylingWithFreemarker.docx) and click on "Report" button to generate docx report which contains "comments" field and is replaced which XHTML content.
  * click [here](http://xdocreport.opensagres.cloudbees.net/processReport?dispatch=load&converter=XHTML_XWPF&reportId=DocxTextStylingWithFreemarker.docx) which generates Docx report which contains "comments" field and is replaced which XHTML content and convert it to HTML.
  * click [here](http://xdocreport.opensagres.cloudbees.net/processReport?dispatch=load&converter=PDF_XWPF&reportId=DocxTextStylingWithFreemarker.docx) which generates Docx report which contains "comments" field and is replaced which XHTML content and convert it to PDF.

## Simple sample ##

### Design the docx ###

Create a docx DocxTextStylingWithVelocity.docx with mergefield nammed "comment" like this :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxTextStylingExplanation1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxTextStylingExplanation1.png)

### Java code ###

Create a Java main class like this:

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.core.document.SyntaxKind;

public class DocxTextStylingWithVelocity {

	public static void main(String[] args) {
		try {
			// 1) Load Docx file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = DocxTextStylingWithVelocity.class
					.getResourceAsStream("DocxTextStylingWithVelocity.docx");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create fields metadata to manage text styling
			FieldsMetadata metadata = report.createFieldsMetadata();
			metadata.addFieldAsTextStyling("comments",
					SyntaxKind.Html);
		
			// 3) Create context Java model
			IContext context = report.createContext();
			context.put("comments",
					"<i>Text</i> coming from <b>Java context</b>.");

			// 4) Generate report by merging Java model with the Docx
			OutputStream out = new FileOutputStream(new File(
					"DocxTextStylingWithVelocity_Out.docx"));
			report.process(context, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
```

This class will generate this report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxTextStylingExplanation3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxTextStylingExplanation3.png)

## Use Velocity/Freemarker in HTML ##

Since **[XDocReport 0.9.5](XDocReport095.md), you can use Velocity/Freemarker in the HTML content.**

To do that you must use FieldsMetadata like this (with true parameter) :

```
FieldsMetadata metadata = report.createFieldsMetadata();
metadata.addFieldAsTextStyling("comments", SyntaxKind.Html, true);
```

Imagine you have putted your context like this :

```
context.put("project", "XDocReport");
context.put("url", "http://code.google.com/p/xdocreport");
```

You can use **$project** and **$url** (for Velocity) and **${project}** and **${url}** (for Freemarker) in your html :

```
context.put("comments", "<a href=\"$url\">$project</a> ");
```

which in this case is the same thing than :

```
context.put("comments", "<a href=\"http://code.google.com/p/xdocreport\">XDocReport</a> ");
```

You can use any Velocity/Freemarker directives (ex:use #foreach/[#list) in your HTML to generate for instance  some ul/li list :

```
context.put("comments", "
<ul>
#set ($planets = ['Earth', 'Mars', 'Neptune'])
#foreach($p in $planets)
<li>$p</li>
#end
</ul>");
```

which in this case is the same thing than :

```
context.put("comments", "<ul><li>Earth</li><li>Mars</li><li>Neptune</li></ul>");
```

You can find samples [DocxTextStylingWithDirectiveWithFreemarker.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandfreemarker/src/fr/opensagres/xdocreport/samples/docxandfreemarker/DocxTextStylingWithDirectiveWithFreemarker.java?repo=samples) and [DocxTextStylingWithDirectiveWithVelocity.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandvelocity/src/fr/opensagres/xdocreport/samples/docxandvelocity/DocxTextStylingWithDirectiveWithVelocity.java?repo=samples)

## Supported styles ##

### Elements ###

| **Style** | **Syntax sample** | **Supported** |
|:----------|:------------------|:--------------|
| Bold    | `<b>`text`</b>` or `<strong>`text`</strong>` | Since **[XDocReport 0.9.3](XDocReport093.md)**|
| Italic    | `<i>`text`</i>` or `<em>`text`</em>` | Since **[XDocReport 0.9.3](XDocReport093.md)**|
| Underline    | `<u>`text`</u>` | Since **[XDocReport 1.0.0](XDocReport100.md)**|
| Strike    | `<s>`text`</s>` or `<strike>`text`</strike>` | Since **[XDocReport 1.0.0](XDocReport100.md)**|
| Paragraph | `<p>`a paragraph`</p>` | Since **[XDocReport 0.9.3](XDocReport093.md)**|
| Span    | `<span>`text`</span>` | Since **[XDocReport 1.0.2](XDocReport102.md)**|
| Heading | `<h1>`Title1`</h1>` | Since **[XDocReport 0.9.6](XDocReport096.md)**|
| Hyperlink    | `<a href="http://code.google.com/p/xdocreport">`XDocReport`</a>`| Since **[XDocReport 0.9.6](XDocReport096.md)**|
| Bullet list| `<ul><li>`item`</li></ul>`| Since **[XDocReport 0.9.6](XDocReport096.md)**|
| Numbered list| `<ol><li>`item`</li></ol>`| Since **[XDocReport 0.9.6](XDocReport096.md)**|
| Line break| `<br />`| Since **[XDocReport 0.9.8](XDocReport098.md)**|

### Attributes ###

| **Style** | **Syntax sample** | **Supported** |
|:----------|:------------------|:--------------|
| Page Break Before  | `<p style="page-break-before:always">`text`</p>` | Since **[XDocReport 0.9.8](XDocReport098.md)**|
| Page Break After   | `<p style="page-break-after:always">`text`</p>` | Since **[XDocReport 0.9.8](XDocReport098.md)**|
| Text Alignment   | `<p style="text-align:left|center|right|justify">`text`</p>` | Since **[XDocReport 1.0.2](XDocReport102.md)**|