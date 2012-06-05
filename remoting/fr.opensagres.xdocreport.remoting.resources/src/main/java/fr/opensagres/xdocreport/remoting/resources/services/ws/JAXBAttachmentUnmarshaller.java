package fr.opensagres.xdocreport.remoting.resources.services.ws;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.xml.bind.attachment.AttachmentUnmarshaller;

import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;

//from org.apache.cxf.jaxb.attachment.JAXBAttachmentUnmarshaller
public class JAXBAttachmentUnmarshaller extends AttachmentUnmarshaller {
    private static final Logger LOG = LogUtils.getL7dLogger(JAXBAttachmentUnmarshaller.class);

    private Collection<Attachment> attachments;

    public JAXBAttachmentUnmarshaller(Collection<Attachment> attachments) {
        super();
        this.attachments = attachments;
    }

    @Override
    public DataHandler getAttachmentAsDataHandler(String contentId) {
        return new DataHandler(AttachmentUtil.getAttachmentDataSource(contentId, attachments));
    }

    @Override
    public byte[] getAttachmentAsByteArray(String contentId) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            InputStream is = AttachmentUtil.getAttachmentDataSource(contentId, attachments)
                                           .getInputStream();
            IOUtils.copy(is, bos);
            is.close();
            bos.close();
        } catch (IOException e) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("ATTACHMENT_READ_ERROR", LOG), e);
        }
        return bos.toByteArray();
    }

    @Override
    public boolean isXOPPackage() {
        return attachments != null;
    }

}
