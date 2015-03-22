# Docx Reporting in WEB Application #

XDocReport give you WEB support to generate report to docx and convert it to another format like PDF and XHTML (or another format if you implement your own XDocReport converter).

You can find Docx Reporting in WEB Application in the [online demo](http://xdocreport.opensagres.cloudbees.net/).

There is 2 means to manage XDocReport report generation in WEB context :

  * [Servlet mode](DocxReportingWEBApplicationServlet.md) : here you implement the XDocReport servlet fr.opensagres.xdocreport.document.web.**AbstractProcessXDocReportServlet** and you manage each reports in the Servlet. XDocReport adwice to use the second means called [Dispatcher mode](DocxReportingWEBApplicationDispatcher.md).
  * [Dispatcher mode](DocxReportingWEBApplicationDispatcher.md) : here you use fr.opensagres.xdocreport.document.web.dispatcher.**ProcessDispatcherXDocReportServlet** and you register Dispatcher which dispatch to controller (IXDocReportWEBController) which manage a report. XDocReport adwice to use this mode in order to have a better design to manage reporting.