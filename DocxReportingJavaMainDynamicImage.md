# Dynamic Image #

Since **[XDocReport 0.9.1](XDocReport091.md)**, it's possible to manage **dynamic images** for docx document. If you wish manage a dynamic image, you must :

  * [design you docx](#Design_Docx_Dynamic_Image.md) document to insert **dynamic image** :
    * insert a "template" image (any image).
    * set a name (ex: logo) for the image by using Bookmark.
  * [register an instance of fr.opensagres.xdocreport.document.images.](#Create.md)**IImageProvider** in the model context with a name (ex: logo) which is enable to write binary of the image in the generated docx report.

In this section we will design a docx document with a "template" dynamic image like this:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage.png)

After report generation, the "template" dynamic image

![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png)

will be replaced with a real logo image :

![http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png](http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png)

to generate this report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage_Out.png)

## Design Docx Dynamic Image ##

Insert a template image (ex: ![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png)) in your docx and create a Bookmark linked to this image with the name **logo** :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage.png).

Please check that Bookmark **logo" is linked to the image :**

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage5.png)

If you don't know how to do that, please read [Manage Dynamic Image](DocxDesignReport#Manage_Dynamic_Image.md) section.

## Create DocxProjectWithVelocityAndImage ##

Now we can create a Java Main which will replace the "template" image with the real logo image.

At first you must indicate to XDocReport that the image linked to the bookmark named with **logo** must be replaced with another image coming from the context. To do that, you must register fields metadata like this:

```
// Create fields metadata to manage image
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("logo");
report.setFieldsMetadata(metadata);
```

Therefore context must be populated with the binary of the image by using an instance of fr.opensagres.xdocreport.document.images.**IImageProvider**. For example you can do that:

```
IImageProvider logo = new ClassPathImageProvider(DocxProjectWithVelocityAndImage.class, "logo.png");
context.put("logo", logo);
```

In this example image is stored in the same package than DocxProjectWithVelocityAndImage class. It exists another implementation of IImageProvider if you want load image from another mean (like file). Please read [IImageProvider section](#IImageProvider.md).

Create fr.opensagres.xdocreport.samples.docxandvelocity.**DocxProjectWithVelocityAndImage** class like this :

```
package fr.opensagres.xdocreport.samples.docxandvelocity;

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
import fr.opensagres.xdocreport.samples.docxandvelocity.model.Project;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class DocxProjectWithVelocityAndImage {

	public static void main(String[] args) {
		try {
			// 1) Load Docx file by filling Velocity template engine and cache
			// it to the registry
			InputStream in = DocxProjectWithVelocityAndImage.class
					.getResourceAsStream("DocxProjectWithVelocityAndImage.docx");
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
					DocxProjectWithVelocityAndImage.class, "logo.png");
			context.put("logo", logo);

			// 4) Generate report by merging Java model with the Docx
			OutputStream out = new FileOutputStream(new File(
					"DocxProjectWithVelocityAndImage_Out.docx"));

			report.process(context, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XDocReportException e) {
			e.printStackTrace();
		}
	}
}
```

Run **DocxProjectWithVelocityAndImage** Java class and **DocxProjectWithVelocityAndImage\_Out.docx** will be generated in your project home :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage_Out.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxProjectWithVelocityAndImage_Out.png).

# Name image different of the context #

Sometimes your image IImageProvider can be able to come from your object model. Imagine you have Project#getLogo() :

```
public class Project {

    ....
    public IImageProvider getLogo() {
      return logo; 
    }
}
```

Name of the bookmark should be **project.Logo**. Unfortunately, bookmark can have dot in the name. So you can manage a mapping between the bookmark name and the Java context model, by using FieldsMetada :

  1. you can name the bookmark with **logo** name.
  1. set the mapping between the Java model and the bookmark name like this :

```
// Create fields metadata to manage image
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("logo", "project.Logo");
report.setFieldsMetadata(metadata);
```

# IImageProvider #

XDocReport provides several implementation of IImageProvider :

| **Class** | **Description** |
|:----------|:----------------|
| fr.opensagres.xdocreport.document.images.**ClassPathImageProvider** | Implementation which load binary image from classpath of the given Class. |
| fr.opensagres.xdocreport.document.images.**FileImageProvider** | Implementation which load binary image from a file. |
| fr.opensagres.xdocreport.document.images.**ByteArrayImageProvider** | Implementation which load binary image from byte array or input stream. This implementation is This provider is useful when image content can change by calling ByteArrayImageProvider#setImageByteArray(byte[.md](.md)) ByteArrayImageProvider#setImageStream(InputStream)).|

## ClassPathImageProvider ##

Here a sample to load image "logo.png" stored in the package of the class DocxProjectWithVelocityAndImage :

```
IImageProvider logo = new ClassPathImageProvider(DocxProjectWithVelocityAndImage.class, "logo.png");
context.put("logo", logo);
```

You can find samples with ClassPathImageProvider with Freemarker in the [DocxProjectWithFreemarkerAndImage.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandfreemarker/src/fr/opensagres/xdocreport/samples/docxandfreemarker/DocxProjectWithFreemarkerAndImage.java?repo=samples) and samples with ClassPathImageProvider with Velocity in the [DocxProjectWithVelocityrAndImage.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandvelocity/src/fr/opensagres/xdocreport/samples/docxandvelocity/DocxProjectWithVelocityAndImage.java?repo=samples).

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

This section will be based on the [DocxProjectWithFreemarkerAndImage.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandfreemarker/src/fr/opensagres/xdocreport/samples/docxandfreemarker/DocxProjectWithFreemarkerAndImage.java?repo=samples) sample.

This sample is based on this image logo (to see the real size) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png](http://wiki.xdocreport.googlecode.com/git/screenshots/logo.png)

## Use the "template" image size ##

By default, the size of the inserted image take the same size of the "template" image :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("logo");
...
// Image with the "template" image size
IImageProvider logo = new ClassPathImageProvider(DocxProjectWithFreemarkerAndImage.class, logo.png");
context.put("logo", logo);
```

The generated report display the logo with the "template" size:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageTemplateSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageTemplateSize.png)

## Use the original image size ##

If you wish display the logo with the original size, you must do like this :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("originalSizeLogo");
...
// Image with original size
boolean useImageSize = true;
IImageProvider originalSizeLogo = new ClassPathImageProvider(DocxProjectWithFreemarkerAndImage.class, "logo.png", 
useImageSize);
context.put("originalSizeLogo", originalSizeLogo);
```

The generated report display the logo with the original size:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageOriginalSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageOriginalSize.png)

## Set a size for the image ##

You can set a size (width and height) in a pixel unit like this :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("forcedSizeLogo");
...
// Image with width/height forced
IImageProvider forcedSizeLogo = new ClassPathImageProvider(DocxProjectWithFreemarkerAndImage.class, logo.png");
forcedSizeLogo.setSize(400f, 100f);
context.put("forcedSizeLogo", forcedSizeLogo);
```

The generated report display the logo with the forced size:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageForcedSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageForcedSize.png)

## Set a width and compute the height ##

You can set a width in a pixel unit and compute the height with ratio like this :

```
FieldsMetadata metadata = new FieldsMetadata();
metadata.addFieldAsImage("ratioSizeLogo");
...
// Image with width forced and height computed with ratio
IImageProvider ratioSizeLogo = new ClassPathImageProvider(DocxProjectWithFreemarkerAndImage.class, "logo.png");
ratioSizeLogo.setUseImageSize(true);
ratioSizeLogo.setWidth(400f);
ratioSizeLogo.setResize(true);
context.put("ratioSizeLogo", ratioSizeLogo);
```

The generated report display the logo with the forced width and computed height :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageRatioSize.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageRatioSize.png)

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

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageImageTemplate.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageImageTemplate.png)

When logo image is null (IImageProvider is null or image stream of IImageProvider is null), you wish:

  * throws an error and break the report generation :

```
Caused by: fr.opensagres.xdocreport.core.XDocReportException: Image provider for field [logo] cannot be null!
	at fr.opensagres.xdocreport.document.images.AbstractImageRegistry.processNullImage(AbstractImageRegistry.java:106)
```

  * or remove the "image" template in the generated report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageRemoveImageTemplate.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageRemoveImageTemplate.png)

  * keep the "image" template in the generated report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageImageTemplate.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxDynamicImageImageTemplate.png)

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

  * with docx and Velocity in the [DocxProjectWithVelocityAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandvelocity/src/fr/opensagres/xdocreport/samples/docxandvelocity/DocxProjectWithVelocityAndImageWithoutImageProvider.java?repo=samples)

  * with docx and Freemarker in the [DocxProjectWithFreemarkerAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandfreemarker/src/fr/opensagres/xdocreport/samples/docxandfreemarker/DocxProjectWithFreemarkerAndImageWithoutImageProvider.java?repo=samples)

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

  * with docx and Velocity in the [DocxProjectWithVelocityAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandvelocity/src/fr/opensagres/xdocreport/samples/docxandvelocity/DocxProjectWithVelocityAndImageWithoutImageProvider.java?repo=samples)

  * with docx and Freemarker in the [DocxProjectWithFreemarkerAndImageWithoutImageProvider.java](http://code.google.com/p/xdocreport/source/browse/samples/fr.opensagres.xdocreport.samples.docxandfreemarker/src/fr/opensagres/xdocreport/samples/docxandfreemarker/DocxProjectWithFreemarkerAndImageWithoutImageProvider.java?repo=samples)