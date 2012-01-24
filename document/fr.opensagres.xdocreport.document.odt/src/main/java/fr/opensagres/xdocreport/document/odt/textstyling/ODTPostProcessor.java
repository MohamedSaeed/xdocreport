package fr.opensagres.xdocreport.document.odt.textstyling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import fr.opensagres.xdocreport.document.preprocessor.sax.BufferedDocument;
import fr.opensagres.xdocreport.document.preprocessor.sax.BufferedDocumentContentHandler;
import fr.opensagres.xdocreport.document.preprocessor.sax.BufferedElement;

public class ODTPostProcessor extends BufferedDocumentContentHandler<BufferedDocument> {

    List<BufferedElement> pHierarchy = new ArrayList<BufferedElement>();

    @Override
    public boolean doStartElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException {

        if ("p".equals(localName)) {
            pHierarchy = new Stack<BufferedElement>();
            BufferedElement elem = getCurrentElement();
            pHierarchy.add(elem);
        }
        else if ("span".equals(localName)) {
            BufferedElement elem = getCurrentElement();
            String style = elem.getAttributes().getValue("text:style-name");
            if (ODTDocumentHandler.PARAGRAPH_AUTOBREAK_END.equals(style)) {
                //elem.append("\n<!--Force reopen paragraph-->");
                reopenParagraph(elem);
                pHierarchy.add(0,elem);
            } else {
                pHierarchy.add(0,elem);
            }
        }
        return super.doStartElement(uri, localName, name, attributes);
    }

    @Override
    public void doEndElement(String uri, String localName, String name)
            throws SAXException {

        if ("p".equals(localName)) {
           //
        }
        else if ("span".equals(localName)) {
            BufferedElement elem = getCurrentElement();
            String style = elem.getAttributes().getValue("text:style-name");
            String id = elem.getAttributes().getValue("text:id");
            String txt = elem.getTextContent();
            if (ODTDocumentHandler.PARAGRAPH_AUTOBREAK_START.equals(style)) {
                //elem.getParent().append("\n<!--Force closing paragraph-->");
                if (pHierarchy.size()>0) { // XXX ???
                    pHierarchy.remove(0);
                }
                closeParagraph(elem.getParent());
            } else {
               if (pHierarchy.size()>0) { // XXX ???
                   pHierarchy.remove(0);
               }
            }
        }
        super.doEndElement(uri, localName, name);
    }


    protected void closeParagraph(BufferedElement elem) {

        Iterator<BufferedElement> itParents = pHierarchy.iterator();

        while (itParents.hasNext()) {
            BufferedElement parent = itParents.next();
            String tagName = parent.getStartTagElementName();
            if (itParents.hasNext()) {
                elem.append("</text:span>");
            } else {
                elem.append("</text:p>");
            }
        }
    }

    protected void reopenParagraph(BufferedElement elem) {
        boolean first = true;
        Iterator<BufferedElement> itParents = pHierarchy.iterator();
        while (itParents.hasNext()) {
            BufferedElement parent = itParents.next();
            String tagName = parent.getStartTagElementName();
            if (first) {
                elem.append("<text:p id=\"reopen\">");
            } else {
                elem.append("<text:span>");
            }
            first=false;
        }
    }




}
