# Overview #

With XDocReport, the docx reporting is done with MS Word by inserting mergefield to mark the field name to replace with a value. For the report designer user it's very difficult
to know which names fields it must use in the docx report. Types the mergefield name at hand can give some bugs because the field name is not well formmatted (don't follow the template engine syntax, is not well spelled).

Since [XDocReport 0.9.5](http://code.google.com/p/xdocreport/wiki/XDocReport095), an MS Word macro **XDocReport.dotm** is provided to help the report designer user to create docx report. This macro gives you 2 features :

  * display in a dialog box fields avaialble for the docx report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview1.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview1.png)

  * preview the result of the docx report :

![http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview2.png](http://wiki.xdocreport.googlecode.com/git/screenshots/QuickStart_MacroOverview2.png)

XDocReport 0.9.5 provides Tools to generate XML Fields and generate reporting from a Java Main. Those tools are used in the MS Word macro **XDocReport.dotm**.

For more information, please read [Quick Start](https://code.google.com/p/xdocreport/wiki/DocxReportingQuickStart) section.