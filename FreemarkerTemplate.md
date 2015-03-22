# Freemarker #

See [http://freemarker.org/](http://freemarker.org/)

# How to configure Freemarker? #

Freemarker can be configurated with instance of **freemarker.template.Configuration**. For instance you can configure [Error handling](http://freemarker.org/docs/pgui_config_errorhandling.html) to manage null value.

To configure Freemarker with XDocReport you must get the Configuration instance. To do that you must

  1. create a class (ex : fr.opensagres.xdocreport.MyFreemarkerConfiguration) which implements **fr.opensagres.xdocreport.document.discovery.ITemplateEngineInitializerDiscovery**.
  1. register with SPI this class by creating the file **META-INF/services/fr.opensagres.xdocreport.document.discovery.ITemplateEngineInitializerDiscovery** with the name of you class :

```
fr.opensagres.xdocreport.MyFreemarkerConfiguration
```

This file should be in your classpath (you can for instance host it in the src/META-INF/services/ of your project).

Here a sample code which configures the [Customizing the behavior regarding TemplatException-s](http://freemarker.org/docs/pgui_config_errorhandling.html#autoid_45) :

```
package fr.opensagres.xdocreport;

import java.io.IOException;

import fr.opensagres.xdocreport.document.discovery.ITemplateEngineInitializerDiscovery;
import fr.opensagres.xdocreport.template.ITemplateEngine;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.freemarker.FreemarkerTemplateEngine;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class MyFreemarkerConfiguration
    implements ITemplateEngineInitializerDiscovery
{

    class MyTemplateExceptionHandler
        implements TemplateExceptionHandler
    {
        public void handleTemplateException( TemplateException te, Environment env, java.io.Writer out )
            throws TemplateException
        {
            try
            {
                out.write( "[ERROR: " + te.getMessage() + "]" );
            }
            catch ( IOException e )
            {
                throw new TemplateException( "Failed to print error message. Cause: " + e, env );
            }
        }
    }

    public String getId()
    {
        return MyFreemarkerConfiguration.class.getName();
    }

    public String getDescription()
    {
        return null;
    }

    public String getDocumentKind()
    {
        return null;
    }

    public void initialize( ITemplateEngine templateEngine )
    {
        if ( TemplateEngineKind.Freemarker.name().equals( templateEngine.getKind() ) )
        {
            Configuration cfg = ( (FreemarkerTemplateEngine) templateEngine ).getFreemarkerConfiguration();
            cfg.setTemplateExceptionHandler( new MyTemplateExceptionHandler() );
        }
    }

}
```