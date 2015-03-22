# Dynamic Image #

Since **[XDocReport 0.9.1](XDocReport091.md)**, it's possible to manage **dynamic images** for ODT document. If you wish manage a dynamic image, you must :

  * [design you ODT](#Design_ODT_Dynamic_Image.md) document to insert **dynamic image** :
    * insert a "template" image (any image).
    * set a name (ex: logo) for the image.
  * [register an instance of fr.opensagres.xdocreport.document.images.](#Create.md)**IImageProvider** in the model context with a name (ex: logo) which is enable to write binary of the image in the generated ODT report.

In this section we will design a ODT document with a "template" dynamic image like this:

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage.png).

After report generation, the "template" dynamic image

![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png).

will be replaced with a real logo image :

![http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png](http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png).

to generate this report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage_Out.png).

## Design ODT Dynamic Image ##

Insert a template image (ex: ![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png)) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage.png).

and set the image name with **logo**:

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage5.png)

If you don't know how to do that, please read [Manage Dynamic Image](ODTDesignReport#Manage_Dynamic_Image.md) section.

## Create ODTProjectWithVelocityAndImage ##

Now we can create a Java Main which will replace the "template" image with the real logo image.

At first you must indicate to XDocReport that the image linked to the image named with **logo** must be replaced with another image coming from the context. To do that, you must register fields metadata like this:

```
// Create fields metadata to manage image
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("logo");
report.setFieldsMetadata(metadata);
```

Therefore context must be populated with the binary of the image by using an instance of fr.opensagres.xdocreport.document.images.**IImageProvider**. For example you can do that:

```
IImageProvider logo = new ClassPathImageProvider(ODTProjectWithVelocityAndImage.class, "logo.png");
context.put("logo", logo);
```

In this example image is stored in the same package than ODTProjectWithVelocityAndImage class. It exists another implementation of IImageProvider : if you want load image from another mean (like file). Please read [IImageProvider section](#IImageProvider.md).

Create fr.opensagres.xdocreport.samples.ODTandvelocity.**ODTProjectWithVelocityAndImage** class like this :

```
package fr.opensagres.xdocreport.samples.ODTandvelocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.ClassPathImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.samples.ODTandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class ODTProjectWithVelocityAndImage {

	public static void main(String[] args) {
		try {
			// 1) Load ODT file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = ODTProjectWithVelocityAndImage.class
					.getResourceAsStream("ODTProjectWithVelocityAndImage.ODT");
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(
					in, TemplateEngineKind.Velocity);

			// 2) Create fields metadata to manage image
			FieldsMetadata metadata = new FieldsMetadata();
			metadata.addFieldAsImage("logo");
			report.setFieldsMetadata(metadata);

			// 3) Create context Java model
			IContext context = report.createContext();
			Project project = new Project("XDocReport");
			context.put("project", project);
			IImageProvider logo = new ClassPathImageProvider(
					ODTProjectWithVelocityAndImage.class, "logo.png");
			context.put("logo", logo);

			// 4) Generate report by merging Java model with the ODT
			OutputStream out = new FileOutputStream(new File(
					"ODTProjectWithVelocityAndImage_Out.ODT"));

			report.process(context, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

Run **ODTProjectWithVelocityAndImage** Java class and **ODTProjectWithVelocityAndImage\_Out.ODT** will be generated in your project home :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTProjectWithVelocityAndImage_Out.png).

# IImageProvider #

XDocReport provides several implementation of IImageProvider :

| **Class** | **Description** |
|:----------|:----------------|
| fr.opensagres.xdocreport.document.images.**ClassPathImageProvider** | Implementation which load binary image from classpath of the given Class. |
| fr.opensagres.xdocreport.document.images.**FileImageProvider** | Implementation which load binary image from a file. |
| fr.opensagres.xdocreport.document.images.**ByteArrayImageProvider** | Implementation which load binary image from byte array or input stream. This implementation is This provider is useful when image content can change by calling ByteArrayImageProvider#setImageByteArray(byte[.md](.md)) ByteArrayImageProvider#setImageStream(InputStream)).|

## ClassPathImageProvider ##

Here a sample to load image "logo.png" stored in the package of the class ODTProjectWithVelocityAndImage :

```
IImageProvider logo = new ClassPathImageProvider(ODTProjectWithVelocityAndImage.class, "logo.png");
context.put("logo", logo);
```

You can find samples with ClassPathImageProvider with Freemarker in the [ODTProjectWithFreemarkerAndImage.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.odtandfreemarker/src/fr/opensagres/xdocreport/samples/odtandfreemarker/ODTProjectWithFreemarkerAndImage.java?repo=samples) and samples with ClassPathImageProvider with Velocity in the [ODTProjectWithVelocityrAndImage.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.odtandvelocity/src/fr/opensagres/xdocreport/samples/odtandvelocity/ODTProjectWithVelocityAndImage.java?repo=samples).

and find JUnit TestCase in the [ClassPathImageProviderTestCase.java](http://code.google.com/p/xdocreport/source/browse/document/fr.opensagres.xdocreport.document/src/test/java/fr/opensagres/xdocreport/document/images/ClassPathImageProviderTestCase.java)

## FileImageProvider ##

Here a sample to load image "logo.png" from the file D:/logo.png :

```
IImageProvider logo = new FileImageProvider(new File("D:/logo.png"));
context.put("logo", logo);
```

You can find JUnit TestCase in the [FileImageProviderTestCase.java](http://code.google.com/p/xdocreport/source/browse/document/fr.opensagres.xdocreport.document/src/test/java/fr/opensagres/xdocreport/document/images/FileImageProviderTestCase.java)

## ByteArrayImageProvider ##

Here a sample to load image "logo.png" from a byte array (or input  stream):

```
byte[] imageByteArray = ...
IImageProvider logo = new ByteArrayImageProvider(imageByteArray);
context.put("logo", logo);
```

You can change the content byte array (or input  stream) like this:

```
byte[] imageByteArray = ...
IImageProvider logo = new ByteArrayImageProvider(imageByteArray);
// Change image content
byte[] newImageByteArray = ...
logo.setImageByteArray(newImageByteArray);
```

You can find find JUnit TestCase in the [ByteArrayImageProviderTestCase.java](http://code.google.com/p/xdocreport/source/browse/document/fr.opensagres.xdocreport.document/src/test/java/fr/opensagres/xdocreport/document/images/ByteArrayImageProviderTestCase.java)


# Customize size of image #

At this step, image size inserted in the generated report take the same size than the "template" image. Since **[XDocReport 0.9.3](XDocReport093.md)**, it's possible to customize the image size.

This section will be based on the [ODTProjectWithFreemarkerAndImage.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.odtandfreemarker/src/fr/opensagres/xdocreport/samples/odtandfreemarker/ODTProjectWithFreemarkerAndImage.java?repo=samples) sample.

This sample is based on this image logo (to see the real size) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png](http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png)

## Use the "template" image size ##

By default, the size of the inserted image take the same size of the "template" image :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("logo");
...
// Image with the "template" image size
IImageProvider logo = new ClassPathImageProvider(ODTProjectWithFreemarkerAndImage.class, logo.png");
context.put("logo", logo);
```

The generated report display the logo with the "template" size:

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageTemplateSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageTemplateSize.png)

## Use the original image size ##

If you wish display the logo with the original size, you must do like this :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("originalSizeLogo");
...
// Image with original size
boolean useImageSize = true;
IImageProvider originalSizeLogo = new ClassPathImageProvider(ODTProjectWithFreemarkerAndImage.class, "logo.png", 
useImageSize);
context.put("originalSizeLogo", originalSizeLogo);
```

The generated report display the logo with the original size:

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageOriginalSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageOriginalSize.png)

## Set a size for the image ##

You can set a size (width and height) in a pixel unit like this :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("forcedSizeLogo");
...
// Image with width/height forced
IImageProvider forcedSizeLogo = new ClassPathImageProvider(ODTProjectWithFreemarkerAndImage.class, logo.png");
forcedSizeLogo.setSize(400f, 100f);
context.put("forcedSizeLogo", forcedSizeLogo);
```

The generated report display the logo with the forced size:

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageForcedSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageForcedSize.png)

## Set a width and compute the height ##

You can set a width in a pixel unit and compute the height with ratio like this :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("ratioSizeLogo");
...
// Image with width forced and height computed with ratio
IImageProvider ratioSizeLogo = new ClassPathImageProvider(ODTProjectWithFreemarkerAndImage.class, "logo.png");
ratioSizeLogo.setUseImageSize(true);
ratioSizeLogo.setWidth(400f);
ratioSizeLogo.setResize(true);
context.put("ratioSizeLogo", ratioSizeLogo);
```

The generated report display the logo with the forced width and computed height :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageRatioSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageRatioSize.png)

It's important to set IImageProvider#setResize(true) :

```
ratioSizeLogo.setResize(true);
```

otherwise the height will not be computed and will use the "template" image height.



# Optional Image #

Since **[XDocReport 0.9.7](XDocReport097.md)**, it's possible to manage **optional image**. Optional image means that:

  * the IImageProvider **field is null** (ex : the image provider is not putted in the context).
  * the IImageProvider **field is NOT null** but it returns a **null stream image**.

## Behaviours ##

Imagine you have logo image template like this :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageImageTemplate.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageImageTemplate.png)

When logo image is null (IImageProvider is null or image stream of IImageProvider is null), you wish:

  * throws an error and break the report generation :

```
Caused by: fr.opensagres.xdocreport.core.XDocReportException: Image provider for field [logo] cannot be null!
	at fr.opensagres.xdocreport.document.images.AbstractImageRegistry.processNullImage(AbstractImageRegistry.java:106)
```

  * or remove the "image" template in the generated report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageRemoveImageTemplate.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageRemoveImageTemplate.png)

  * keep the "image" template in the generated report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageImageTemplate.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTDynamicImageImageTemplate.png)

Those 3 behaviours can be managed by using the following enum :

```
package fr.opensagres.xdocreport.template.formatter;

public enum NullImageBehaviour
{

    ThrowsError, RemoveImageTemplate, KeepImageTemplate
    
}
```

This enum gives you the capability to configure the behaviour of the null image for :

  * every fields image:

```
FieldsMetadata metadata = report.createFieldsMetadata();
metadata.setBehaviour( NullImageBehaviour.ThrowsError )
```

  * for a field image:

```
FieldsMetadata metadata = report.createFieldsMetadata();
metadata.addFieldAsImage( "logo", NullImageBehaviour.ThrowsError );
```

### Samples ###

You can find samples :

  * with odt and Velocity in the [ODTProjectWithVelocityAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.odtandvelocity/src/fr/opensagres/xdocreport/samples/odtandvelocity/ODTProjectWithVelocityAndImageWithoutImageProvider.java?repo=samples)

  * with odt and Freemarker in the [ODTProjectWithFreemarkerAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.odtandfreemarker/src/fr/opensagres/xdocreport/samples/odtandfreemarker/ODTProjectWithFreemarkerAndImageWithoutImageProvider.java?repo=samples)

## Use InputStream/byte[.md](.md)/file instead of IImageProvider ##

Using IImageProvider in your POJO model could be annoying because POJO have a **big dependencies to XDocreport**. Sometimes you prefer using your own Image POJO or use Java Type like InputStream, byte[.md](.md) or File.

Since **[XDocReport 0.9.7](XDocReport097.md)** you can avoid using IImageProvider :

```
public class Project {
  
  public IImageProvider getLogo() {
    return ...;
  }
}
```

and using any Java Class. Here an example with InputStream :

```
public class Project {
  
  public InputStream getLogo() {
    return ...;
  }
}
```

### Samples ###

You can find samples :

  * with odt and Velocity in the [ODTProjectWithVelocityAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.odtandvelocity/src/fr/opensagres/xdocreport/samples/odtandvelocity/ODTProjectWithVelocityAndImageWithoutImageProvider.java?repo=samples)

  * with odt and Freemarker in the [ODTProjectWithFreemarkerAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.odtandfreemarker/src/fr/opensagres/xdocreport/samples/odtandfreemarker/ODTProjectWithFreemarkerAndImageWithoutImageProvider.java?repo=samples)