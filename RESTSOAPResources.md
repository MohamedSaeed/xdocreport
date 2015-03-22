# REST/SOAP Services #

With XDocReport you can design your ODT/Docx rport with MS Word or OpenOffice/LibreOffice. XDocReport provides [MS Word macro](DocxReportingQuickStart.md) and [Ooo extension](ODTReportingQuickStart.md) to help you to design your report.

If you use XDocReport in your Web Application, you must develop upload/download of the ODT/Docx "template" report, but imagine you can do that with MS Word or OpenOffice/LibreOffice.

With XDocReport it's possible : since [XDocReport 0.9.7](XDocReport097.md), XDocReport provides OOo extension and MS Word macro which gives you the capability to display  in a dialog macro the "template" report of your repository, download and upload it. The macro uses REST/SOAP Services to communicate to your "cloud" repository.

# WebApp Demo & Ooo extension #

The online WebApp demo at http://xdocreport.opensagres.cloudbees.net/processReport.jsp provides a REST and SOAP resources service which gives you the capability to list the ODT/Docx used in the demo :

![http://wiki.xdocreport.googlecode.com/git/screenshots/WebAppDemoTemplateList.png](http://wiki.xdocreport.googlecode.com/git/screenshots/WebAppDemoTemplateList.png)

If you use OOo extension, and you connect to the REST Service with the URL http://xdocreport.opensagres.cloudbees.net/cxf you will see the list of ODT/Docx used in the WebApp demo in the Ooo dialog :

![http://wiki.xdocreport.googlecode.com/git/screenshots/OooRESTDownload.png](http://wiki.xdocreport.googlecode.com/git/screenshots/OooRESTDownload.png)

With this dialog, you can connect to a "cloud" repository and :

  * **display list of resources** (odt, docx, etc) coming from your repository (ex: folder of Web Application, etc...).
  * **download a resource**.
  * **upload a resource**.

You can play with the "template" of the WebApp demo, download an ODT, change it and upload it and generate the report with the WebApp demo to see your change.

# WebApp Demo & REST/SOAP Services #

The WebApp demo deploy a REST and SOAP resources service which list the templates used in the demo. JAX-RS (for REST) and JAX-WS (for SOAP) are used and CXF is used as JAX-RS/JAX-WS implementation. If you go to the URL http://xdocreport.opensagres.cloudbees.net/cxf/services you will see the 2 deployed services and links about WADL and WSDL.

# REST #

The REST service WebApp demo is deployed at http://xdocreport.opensagres.cloudbees.net/cxf/and provides several services :

  * http://xdocreport.opensagres.cloudbees.net/cxf/name : returns the repository name as text.
  * http://xdocreport.opensagres.cloudbees.net/cxf/root : returns the resources (docx, odt) as JSON :

```
{"resource":{"children":{"children":[{"name":"ODTBig.odt","type":1},{"name":"ODTProjectWithVelocity.odt","type":1},{"name":"ODTHelloWorldWithVelocity.odt","type":1},{"name":"ODTCV.odt","type":1},{"name":"DocxCV.docx","type":1},{"name":"DocxBig.docx","type":1},{"name":"DocXHelloWorldWithFreemarker.docx","type":1},{"name":"DocXHelloWorldWithVelocity.docx","type":1},{"name":"ODTLettreRelance.odt","type":1},{"name":"ODTProjectWithFreemarker.odt","type":1},{"name":"ODTHelloWorldWithVelocityTest.odt","type":1},{"name":"ODTTextStylingWithFreemarker.odt","type":1},{"name":"ODTHelloWorldWithFreemarker.odt","type":1},{"name":"DocxLettreRelance.docx","type":1},{"name":"DocxStructures.docx","type":1},{"name":"ODTStructures.odt","type":1}],"name":"Opensagres","type":0},"name":"resources","type":0}}
```

  * http://xdocreport.opensagres.cloudbees.net/cxf/download/{resourceId) : to download a resource. Ex : http://xdocreport.opensagres.cloudbees.net/cxf/download/Opensagres____ODTProjectWithVelocity.odt download the ODTProjectWithVelocity.odt.
  * http://xdocreport.opensagres.cloudbees.net/cxf/upload (with POST).

# JAXRSResourcesService Interface #

```
package fr.opensagres.xdocreport.remoting.resources.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.opensagres.xdocreport.remoting.resources.domain.BinaryData;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesException;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;

@Path( "/" )
public interface JAXRSResourcesService
    extends ResourcesService
{

    @GET
    @Path( "/name" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.TEXT_PLAIN )
    String getName();

    @GET
    @Path( "/root" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    Resource getRoot()
        throws ResourcesException;

    @GET
    @Path( "/download/{resourceId}" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.WILDCARD )
    BinaryData download( @PathParam( "resourceId" )
    String resourceId )
        throws ResourcesException;

    @POST
    @Path( "/upload" )
    @Consumes( MediaType.APPLICATION_JSON )
    void upload( BinaryData data )
        throws ResourcesException;

}
```

# SOAP #

JAXWSResourcesService Interface

```
package fr.opensagres.xdocreport.remoting.resources.services.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import fr.opensagres.xdocreport.remoting.resources.domain.BinaryData;
import fr.opensagres.xdocreport.remoting.resources.domain.Filter;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesException;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;

@WebService( name = "ResourcesService", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
public interface JAXWSResourcesService
    extends ResourcesService
{

    /**
     * Returns the repository name.
     * 
     * @return
     */
    @WebMethod( operationName = "getName", action = "urn:GetName" )
    @RequestWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.GetName", localName = "getName", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    @ResponseWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.GetNameResponse", localName = "getNameResponse", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    String getName();

    /**
     * Returns the root resource.
     * 
     * @return
     */
    @WebMethod( operationName = "getRoot", action = "urn:GetRoot" )
    @RequestWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.GetRoot", localName = "getRoot", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    @ResponseWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.GetRootResponse", localName = "getRootResponse", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    Resource getRoot()
        throws ResourcesException;

    @WebMethod( operationName = "getRoot1", action = "urn:GetRoot1" )
    @RequestWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.GetRoot1", localName = "getRoot1", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    @ResponseWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.GetRootResponse1", localName = "getRootResponse1", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    Resource getRoot( @WebParam( name = "arg0" )
    Filter filter )
        throws ResourcesException;

    @WebMethod( operationName = "download", action = "urn:Download" )
    @RequestWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.Download", localName = "download", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    @ResponseWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.DownloadResponse", localName = "downloadResponse", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    List<BinaryData> download( @WebParam( name = "arg0" )
    List<String> resourceIds )
        throws ResourcesException;

    /**
     * Download the content of the given unique resource id.
     * 
     * @param resourcePath the unique resource id.
     * @return the byte array of the content of the given resourcePath.
     */
    @WebMethod( operationName = "download1", action = "urn:Download1" )
    @RequestWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.Download1", localName = "download1", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    @ResponseWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.DownloadResponse1", localName = "downloadResponse1", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    BinaryData download( @WebParam( name = "arg0" )
    String resourceId )
        throws ResourcesException;

    @WebMethod( operationName = "upload", action = "urn:Upload" )
    @RequestWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.Upload", localName = "upload", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    @ResponseWrapper( className = "fr.opensagres.xdocreport.remoting.resources.services.ws.client.jaxws.UploadResponse", localName = "uploadResponse", targetNamespace = "http://services.resources.remoting.xdocreport.opensagres.fr/" )
    void upload( @WebParam( name = "arg0" )
    BinaryData data )
        throws ResourcesException;
}
```