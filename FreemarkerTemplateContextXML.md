# Freemarker - XML Context #

In this section we will explain how to use with [Freemarker](http://freemarker.sourceforge.net/), XML data as context instead of Pojo. We suggest you to read the official Freemarker [XML Processing Guide](http://freemarker.sourceforge.net/docs/xgui.html) to understand more how to manage XML data as context with Freemarker.

To explain that, we will describe the [XML docx sample on Git](http://code.google.com/p/xdocreport/source/browse?repo=samples#git%2Fsamples%2Ffr.opensagres.xdocreport.samples.docxandfreemarker%2Fsrc%2Ffr%2Fopensagres%2Fxdocreport%2Fsamples%2Fdocxandfreemarker%2Fxml). You can find the same sample with [XML odt sample on Git](https://code.google.com/p/xdocreport/source/browse?repo=samples#git%2Fsamples%2Ffr.opensagres.xdocreport.samples.odtandfreemarker%2Fsrc%2Ffr%2Fopensagres%2Fxdocreport%2Fsamples%2Fodtandfreemarker%2Fxml).

This sample is based on the **DocxXMLProjectWithFreemarkerList.docx**:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxXMLProjectWithFreemarkerList.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxXMLProjectWithFreemarkerList.png)

and XML data **project.xml**:

```
<?xml version="1.0" encoding="UTF-8"?>
<project>
  <name>XDocReport</name>
  <developer name="ZERR" lastName="Angelo" 
             mail="angelo.zerr@gmail.com" />
  <developer name="Leclercq" lastName="Pascal"
             mail="pascal.leclercq@gmail.com" />
</project>
```

The docx contains **${doc.project.name}** which is a merge field and **doc** is the key of the below XML DOM registered in the context:

```
InputStream projectInputStream = DocxXMLProjectWithFreemarkerList.class.getResourceAsStream( "project.xml" );
InputSource projectInputSource = new InputSource( projectInputStream );
freemarker.ext.dom.NodeModel project = freemarker.ext.dom.NodeModel.parse( projectInputSource );
context.put( "doc", project );
```

After report process the **DocxXMLProjectWithFreemarkerList\_Out.docx** is generated:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxXMLProjectWithFreemarkerList_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxXMLProjectWithFreemarkerList_Out.png)

Here the full code DocxXMLProjectWithFreemarkerList :

```
package fr.opensagres.xdocreport.samples.docxandfreemarker.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.docxandfreemarker.model.Developer;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class DocxXMLProjectWithFreemarkerList
{

    public static void main( String[] args )
    {
        try
        {
            // 1) Load Docx file by filling Freemarker template engine and cache
            // it to the registry
            InputStream in =
                DocxXMLProjectWithFreemarkerList.class.getResourceAsStream( "DocxXMLProjectWithFreemarkerList.docx" );
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport( in, TemplateEngineKind.Freemarker );

            // 2) Create fields metadata to manage lazy loop (#forech velocity)
            // for table row.
//            FieldsMetadata metadata = report.createFieldsMetadata();
//            metadata.addFieldAsList( "doc.project.developer.@name" );
//            metadata.addFieldAsList( "developers.lastName" );
//            metadata.addFieldAsList( "developers.mail" );

            // 3) Create context Java model
            IContext context = report.createContext();
            InputStream projectInputStream = DocxXMLProjectWithFreemarkerList.class.getResourceAsStream( "project.xml" );
            InputSource projectInputSource = new InputSource( projectInputStream );
            freemarker.ext.dom.NodeModel project = freemarker.ext.dom.NodeModel.parse( projectInputSource );
            context.put( "doc", project );
            
            // 4) Generate report by merging Java model with the Docx
            OutputStream out = new FileOutputStream( new File( "DocxXMLProjectWithFreemarkerList_Out.docx" ) );
            report.process( context, out );

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        catch ( XDocReportException e )
        {
            e.printStackTrace();
        }
        catch ( SAXException e )
        {
            e.printStackTrace();
        }
        catch ( ParserConfigurationException e )
        {
            e.printStackTrace();
        }
    }
}
```

## Some explanations ##

### Display text node ###

The project name is declared like this:

```
<project>
  <name>XDOcReport</name>
  ...
</project>
```

To display this name we must write :

```
${doc.project.name}
```

### Loop for elements ###

The XML data contains developer list declared like this:

```
<project>
  ...
  <developer name="ZERR" lastName="Angelo" 
             mail="angelo.zerr@gmail.com" />
  <developer name="Leclercq" lastName="Pascal"
             mail="pascal.leclercq@gmail.com" />
</project>
```

To display name of each developer, we need use Freemarker syntax like this :

```
[#list doc.project.developer as d]
  ${d.@name}
[/#list]
```

### Display attribute ###

As developer name is an attribute you must use @ notation:

```
${d.@name}
```


### List in Table ###

You cannot use FieldsMetadata to generate lazy loop  for table row (like explained [here](http://code.google.com/p/xdocreport/wiki/DocxReportingJavaMainListFieldInTable)). This problem comes from that field name has several level with dot (we should fix this problem!)). So to generate loop for table row, you must use @before-row/@after-row :

```
------------------------------------------------------------------------
@before-row[#list doc.project.developer as d] | ${d.lastName} |${d.mail}
${d.name}                                     |               |
@after-row[/#list]                            |               |
------------------------------------------------------------------------
```

For more information about @before-row/@after-row, please read [here](http://code.google.com/p/xdocreport/wiki/DocxReportingJavaMainListFieldAdvancedTable).