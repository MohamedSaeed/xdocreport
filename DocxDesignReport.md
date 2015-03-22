# Docx Design Report #

XDocReport give you the capability to **create report with MS Word docx**. Fields to replace must follow **Freemarker/Velocity syntax**. Typing directly field name can work, but you can have trouble if you style the field. So it is advisable to **use MergeField to set fields to replace**.

To add **MergeField** in your docx report you can :

  * [Use standard MS Word MergeField](#Create_with_MS_Word.md) creation.
  * [Use Macro](DocxDesignReportMacro.md) which display in a dialog box fields available for the model.

To [manage Dynamic Image](#Manage_Dynamic_Image.md) in your docx report you must :

  * insert a "template" image (any image).
  * set a name for the image by using Bookmark.

For the "template" image you can for instance use this image :
![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png).

# Create MergeField with MS Word #

This section explains how to add MergeField **$project.Name** after Project: content. Here steps to add this MergeField :

  * Do **Ctrl+F9** after Project: content :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField1.png)

  * MS Word generate you **{ }**. Select it and click on right mouse to open contextual menu and select **Field modification...** (Modification du champs...) menu:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField2.png)

  * this action opens the Field dialog. Select **MergeField tree item** (ChampsFusion) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField3.png)

  * Select **MergeField tree item** (Champs de fusion) and type **$project.Name** in the field name (Nom du champs) and click on OK button :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField4.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField4.png)

  * MergeField is created :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField5.png)

  * Now you can style it if you wish. To do that, select the whole mergefield :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField6.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField6.png)

  * Style it as you wish :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField7.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateMergeField7.png)

# Manage Dynamic Image #

This section explains how to add Dynamic Image **$logo** in a docx document. Here steps to add this dynamic image :

  * go to the menu **Insert/Image** (Insertion/Image) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage1.png)

  * this action opens the dialog to select an image (in this screenshot, template.png ![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png) is selected) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage2.png)

  * after clicking on **Insert** button, image is inserted in the document. Now you must name the image. To do that, we must link with the image a bookmark named with **logo**. To do that, select the image, click on **Insert/Bookmark** (Insertion/Signet) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage3.png)

  * this action opens the bookmark dialog box. Type **logo** in the **bookmark name** input field and on **Add** button :

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage4.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage4.png)

Dynamic image is now available with **logo** name.

  * You can go to the Bookmark to select the linked image with Bookmark Dialog:

![http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/DocxCreateDynamicImage5.png)