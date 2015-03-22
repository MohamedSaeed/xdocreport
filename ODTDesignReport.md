# ODT Design Report #

XDocReport give you the capability to **create report with OppeOffice ODT**. Fields to replace must follow **Freemarker/Velocity syntax**. Typing directly field name can work, but you can have trouble if you style the field. So it is advisable to **use InputField to set fields to replace**.

To add InputField in your ODT report you can :

  * [Use standard OppenOffice Input Field](#Create_Field_with.md) creation.
  * [Use Macro](ODTDesignReportMacro.md) which display in a dialog box fields available for the model.

To [manage Dynamic Image](#Manage_Dynamic_Image.md) in your ODT report you must :

  * insert a "template" image (any image).
  * set a name for the image.

For the "template" image you can for instance use this image :
![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png).

# Create Field with OppenOffice #

This section explain how add Field **$project.Name** after Project: content. Here steps to add this Field :

  * Do **Ctrl+F2** after Project: content or go at **Insert / Fields / Other..** menu :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField1.png)

  * this action opens Fields dialog. Click on the **Functions** (Fonctions) tab and select **Input field** (Champs de saisie)  :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField2.png)

  * Click on **Insert** (Insérer) button to open Input Field dialog, and type in the textarea the field name **$project.Name** :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField3.png)

  * click on OK, field is added to your document :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField4.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField4.png)

  * Now you can style it if you wish. To do that, select the whole field :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField5.png)

  * Style it as you wish :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField6.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateField6.png)

# Manage Dynamic Image #

This section explains how to add Dynamic Image **$logo** in a ODT document. Here steps to add this dynamic image :

  * go to the menu **Insert/Image/File...** (Insertion/Image/A partir d'un fichier...) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage1.png)

  * this action opens the dialog to select an image (in this screenshot, template.png ![http://wiki.xdocreport.googlecode.com/git/screenshots/template.png](http://wiki.xdocreport.googlecode.com/git/screenshots/template.png) is selected) :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage2.png)

**after clicking on**Open**button, image is inserted in the document :**

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage3.png)

Now you must name the image. To do that, select the image, click on right button and select **Image...** menu item :

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage4.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage4.png)

  * this action opens the **Image** dialog box. Go to the **Options** tab and fill the field name with **logo** value:

![http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ODTCreateDynamicImage5.png)

Click on **OK** button. Dynamic image is now available with **logo** name.