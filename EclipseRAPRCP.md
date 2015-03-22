# Eclipse RAP/RCP #

XDocReport provides an extensible Eclipse RAP/RCP to add it your report module (resume, etc) with Eclipse extension point in Web Application (Eclipse RAP) and Fat client (Eclipse RCP).

Following screenshot is a resume demo which defines a report module with Eclipse extension point to manage a resume application in a Desktop (RCP) and WEB Application (RAP) with the same code (single sourcing).

You can play with [Eclipse RAP demo online](http://xdocreport-rap.opensagres.cloudbees.net/xdocreport?startup=fr.opensagres.xdocreport.eclipse.ui.application).

## Web Resume demo with RAP ##

This section shows screen about the RAP demo which defines a report resume module to manage resume (search, create resume and generate a resume with XDocReport or another reporting engine).

### XDocReport Workspace ###

Here the XDocReport workspace. On the left there is the "Report Explorer" which displays different report module. This screen displays the resume report module:

![http://wiki.xdocreport.googlecode.com/git/screenshots//EclipseRAP_Workspace.png](http://wiki.xdocreport.googlecode.com/git/screenshots//EclipseRAP_Workspace.png)

You can add any report module with Eclipse extension point (ex: LettreRelance).

### New Resume ###

You can create a resume with the Resume -> New menuwhich opens a wizard to create a resume :

![http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_NewResumeWizard.png](http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_NewResumeWizard.png)

Finish button of teh wizard create a resume and open the Resume form.

### Resume Form ###

Here a screen about the resume form :

![http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_ResumeForm.png](http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_ResumeForm.png)

### Reporting ###

You can generate the resume report with report actions on the toolbar of the resume form :

![http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_GenerateReportFromForm.png](http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_GenerateReportFromForm.png)

After clicking on a report, the report is generated :

![http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_GeneratedDocxResume.png](http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_GeneratedDocxResume.png)

### Search Resume ###

You can search a resume with search resume dialog :

![http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_SearchResumeDialog.png](http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_SearchResumeDialog.png)

If you select several resume and click on OK button of the dialog, one report form is opened per selected resumes :

![http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_SearchResumeResult.png](http://wiki.xdocreport.googlecode.com/git/screenshots/EclipseRAP_SearchResumeResult.png)

## Fat client Resume demo with RCP ##

For RCP demo, it the same application than RAP.