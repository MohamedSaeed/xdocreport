# How to build XDocReport from source codes #

This section is intended for developers who wish to build XDocReport from source codes. It explain how to configure Eclipse environment, download and build source codes of XDocReport library.

# Prerequisite #

  * JDK 5+
  * The famous eclipse IDE (preferrably a recent version, we use 3.6 and 3.7).
  * a Git client. Install Eclipse RCP Indigo and you will nothing to install.
  * m2eclipse, this is a very powerfull tool for ising maven into eclipse.
    * http://m2eclipse.sonatype.org/

# Overview #

This guide describes:

  1. Configuration of Development Environment Eclipse
    * Installation of Git Plugin for Eclipse if you have not Eclipse RCP
    * Installation and configuration of Maven Plugin
  1. Checkout of XDocReport source codes from Git repository
  1. Build of source codes
  1. Execution of a simple example
  1. How to package XDocReport and export it in jar files

The first part shows the steps for the configuration of Eclipse environment to support the checkout of XDocReport source code from svn repository and build it.

## 1. Configuration of Development Environment Eclipse ##

The development environment is Eclipse IDE that provides many plugins that support the development work. The first step is to download the IDE from [official web site](http://www.eclipse.org/downloads/) and install it.

### Git Eclipse Plugin ###

If you use Eclipse RCP (and not JEE) Indigo, Git is already installed.

TODO : Explains how to install Git .

### Eclipse Maven Plugin (m2eclipse) ###

The maven eclipse plugin is m2eclipse and it requires that Eclipse run on installed JDK (not on simple JRE). For this reasons you can use the parameter with internal double quotes:

_-vm "path of jdk\bin\javaw.exe"_

for run Eclipse IDE with JDK.
The easiest way to be sure you'll always use a JDK is to modify your eclipse.ini file at the root of your eclipse installation folder.

Here you can find the reference on how to edit your eclipse.ini file :
http://wiki.eclipse.org/Eclipse.ini

My own eclipse.ini looks like this :
```
-startup
plugins/org.eclipse.equinox.launcher_1.2.0.v20110321-2120.jar
--launcher.library
plugins/org.eclipse.equinox.launcher.win32.win32.x86_1.1.100.v20110321
-showsplash
org.eclipse.platform
-vm
H:/devtools/Jdk/jdk1.6.0_18/bin/javaw.exe
--launcher.XXMaxPermSize
256m
--launcher.defaultAction
openFile
-vmargs
-Xms40m
-Xmx384m
```

Now, go to install maven plugin simply using the eclipse market place, from Main menu **Help -> Eclipse market place ...**. Into find field enter "m2eclipse" and select "Maven Integration for Eclipse" plugin by Sonatype Inc. and click on "install".  Install plugin and restart environment.

![http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_m2eclipse_marketplace.png](http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_m2eclipse_marketplace.png)

## 2. Checkout of XDocReport source codes from Git repository ##

### Checkout of XDocReport source codes ###

XDocReport is composed by many projects, all of them are organized on git repository with subfolders:

  * **core** folder
  * **converter** folder contains
    * the projects that perform the conversion from source input file (docx, odt) to output format. for example docx to pdf, docx to html
  * **integrationtests** folder
    * contains all XDocReport tests

Open an empty new workspace and from Eclipse main menu select **File -> Import ...**.

Open the "Git" node and select "_Projects from Git_".

![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_2.png)

Click on the "Next" button.

The "Import Projects From Git Dialog" opens.

Clique on the "Clone..." button

The following dialog displays:


![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_4.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_4.png)

Here is how to get the location URI:

Open a web browser and go to http://code.google.com/p/xdocreport/source/checkout

If you're not already signed in, just click on the following signin link and proceed.


![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_5.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_5.png)


The URI with your login as a prefix will be shown.



![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_6.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_6.png)



Just copy and paste it in the Eclipse dialog box (URI field) like in the screenshot below:




![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_7.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_7.png)


To complete this dialog box you'll need to fill your password in the password field.

Here is how to get it:

Click on the "googlecode.com password." link.


![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_8.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_8.png)


You'll be redirected on the google code password manager.



![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_9.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_9.png)

Copy and paste it on the password field in the still open Eclipse dialog box.



![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_10.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_10.png)

Then click on the "Next" button

![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_11.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_11.png)


Be sure "master" is checked and click on "Next" again


![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_12.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_12.png)

Now click on the "Browse" button in order to select the target directory where you want to checkout XDocReports then click on the "Finish" button.

Now your' back in the "Import Projects from git" wizard.

Click on the "Next" button.



![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_13.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_13.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_14.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_14.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_15.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_15.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_16.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_16.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_17.png](http://wiki.xdocreport.googlecode.com/git/screenshots/GitCloneXDocreport_17.png)

### Import source codes as Maven projects ###

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportMvnProjectXDocreport_1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportMvnProjectXDocreport_1.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportMvnProjectXDocreport_2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportMvnProjectXDocreport_2.png)

![http://wiki.xdocreport.googlecode.com/git/screenshots/ImportMvnProjectXDocreport_3.png](http://wiki.xdocreport.googlecode.com/git/screenshots/ImportMvnProjectXDocreport_3.png)

## 3. Build the source codes ##

After the downloading of all source codes, into project can be much more problems. Below I listed some problems.

### Faceted project problem ###

The problem is of project when you use a jdk greater than 1.5, in that case you should enable the JDK Compliance. Click with right mouse button on project and select properties from context menu. In "_Java Compiler_" enable the compliance with 1.5 jvm. Apply and rebuild project. In my case, I do this for all projects into workspace.

![http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_jvm_15_compliance.png](http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_jvm_15_compliance.png)

### XSL validation problem ###

This problem is with the two projects converter.docx and converter.odt and is relative to xml schema. Author of library suggest to disable validation for this files because are only for "_(possibly) future support_" and use are based on "Apache FOP".

Go on properties of both projects **Right click mouse button on project -> properties ...**. In validation section click on "_Enable project specific settings_" and in the list disable XSL validation and XML validation. After click on ok button, I suggest to redo validation  **Right click on project and from context menu select Validate option**.

![http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_xsl_validation.png](http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_xsl_validation.png)

For now the projects with xls and xml validations are:

  * converter.fop.docx
  * converter.fop.odt

## 4. Execution of a simple example ##

To test samples you must clone the samples git repository [http://code.google.com/p/xdocreport/source/checkout?repo=samples](http://code.google.com/p/xdocreport/source/checkout?repo=samples).

Developers of xdocreport library provides many examples to run for start with understanding of source codes.

### Run MSWord Docx Example ###

Open **xdocreport.examples project** and in source folder _src/main/java/fr.opensagres.xdocreport.examples.msword.docx_ run the  **DocxHelloWorldWithFreeMarker** java class. You can right click on java class and **Run as -> Java application**.

This example replace modify the input docx template replacing the ${name} tag with the name "world". After the running of code, you can refresh the project to show the "out" folder with output of processing.

![http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_docx_helloworld.png](http://xdocreport.googlecode.com/svn/wiki/screenshots/HowToBuildXDocReport_docx_helloworld.png)

## 5. How to package XDocReport and export in jar files ##