package fr.opensagres.xdocreport.remoting.resources.services.ws.client;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import fr.opensagres.xdocreport.remoting.resources.services.MockResourcesService;
import fr.opensagres.xdocreport.remoting.resources.services.ws.server.JAXWSResourcesServiceImpl;

public class MockCXFNonSpringServlet
    extends CXFNonSpringServlet
{

    @Override
    public void init( ServletConfig sc )
        throws ServletException
    {
        super.init( sc );
        String address = "/resources";
        Endpoint e= javax.xml.ws.Endpoint.publish( address,
                                       new JAXWSResourcesServiceImpl(
                                                                      new MockResourcesService(
                                                                                                JAXWSResourcesServiceClientTestCase.resourcesDir ) ) );


        SOAPBinding binding = (SOAPBinding)e.getBinding();
        binding.setMTOMEnabled(true);
        System.err.println(e);


    }
}
