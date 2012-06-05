package fr.opensagres.xdocreport.remoting.resources.services.ws;

import java.util.Collection;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.namespace.QName;

import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.message.Attachment;


//from org.apache.cxf.jaxb.attachment.JAXBAttachmentMarshaller
public class JAXBAttachmentMarshaller extends AttachmentMarshaller {

    private int threshold = 5 * 1024;
    private Collection<Attachment> atts;
    private boolean isXop;
    private QName lastElementName;

    public JAXBAttachmentMarshaller(Collection<Attachment> attachments, Integer mtomThreshold) {
        super();
        if (mtomThreshold != null) {
            threshold = mtomThreshold.intValue();
        }
        atts = attachments;
        isXop = attachments != null;
    }

    public QName getLastMTOMElementName() {
        return lastElementName;
    }

    public String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNS,
                                    String elementLocalName) {

        Attachment att = AttachmentUtil.createMtomAttachment(
                             isXop, mimeType, elementNS, data, offset, length, threshold);
        if (att != null) {
            atts.add(att);
            lastElementName = new QName(elementNS, elementLocalName);
            return "cid:" + att.getId();
        } else {
            return null;
        }
    }

    public String addMtomAttachment(DataHandler handler, String elementNS, String elementLocalName) {

        Attachment att = AttachmentUtil.createMtomAttachmentFromDH(isXop, handler, elementNS, threshold);
        if (att != null) {
            atts.add(att);
            lastElementName = new QName(elementNS, elementLocalName);
            return "cid:" + att.getId();
        } else {
            return null;
        }
    }

    @Override
    public String addSwaRefAttachment(DataHandler handler) {
        String id = UUID.randomUUID() + "@opensagres.fr";
        AttachmentImpl att = new AttachmentImpl(id, handler);
        att.setXOP(false);
        atts.add(att);
        return id;
    }

    public void setXOPPackage(boolean xop) {
        this.isXop = xop;
    }

    @Override
    public boolean isXOPPackage() {
        return isXop;
    }
}



