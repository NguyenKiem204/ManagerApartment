package validation;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class UrlUtils {
    public static String serializeFilterMapping(String filterName, List<String> urlPatterns) {
        try {
            if (filterName == null || filterName.trim().isEmpty()) {
                throw new IllegalArgumentException("Filter name cannot be empty");
            }
            
            if (urlPatterns == null || urlPatterns.isEmpty()) {
                throw new IllegalArgumentException("URL patterns list cannot be empty");
            }
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("filter-mapping");
            doc.appendChild(rootElement);
            
            Element filterNameElement = doc.createElement("filter-name");
            filterNameElement.setTextContent(filterName);
            rootElement.appendChild(filterNameElement);
            
            for (String urlPattern : urlPatterns) {
                if (urlPattern != null && !urlPattern.trim().isEmpty()) {
                    Element urlPatternElement = doc.createElement("url-pattern");
                    urlPatternElement.setTextContent(urlPattern.trim());
                    rootElement.appendChild(urlPatternElement);
                }
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            
            return writer.getBuffer().toString();
        } catch (Exception ex) {
            Logger.getLogger(UrlUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static FilterMapping deserializeFilterMapping(String xmlString) {
        try {
            if (xmlString == null || xmlString.trim().isEmpty()) {
                return null;
            }
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));
            
            NodeList filterNameList = document.getElementsByTagName("filter-name");
            if (filterNameList.getLength() == 0) {
                return null;
            }
            
            String filterName = filterNameList.item(0).getTextContent();
            if (filterName == null || filterName.trim().isEmpty()) {
                return null;
            }
            
            NodeList urlPatternList = document.getElementsByTagName("url-pattern");
            List<String> urlPatterns = new ArrayList<>();
            
            for (int i = 0; i < urlPatternList.getLength(); i++) {
                String pattern = urlPatternList.item(i).getTextContent();
                if (pattern != null && !pattern.trim().isEmpty()) {
                    urlPatterns.add(pattern.trim());
                }
            }
            
            if (urlPatterns.isEmpty()) {
                return null;
            }
            
            return new FilterMapping(filterName, urlPatterns);
        } catch (Exception ex) {
            Logger.getLogger(UrlUtils.class.getName()).log(Level.SEVERE, "Error deserializing filter mapping", ex);
            return null;
        }
    }

    public static class FilterMapping {
        private int id;
        private String filterName;
        private List<String> urlPatterns;
        
        public FilterMapping(String filterName, List<String> urlPatterns) {
            this.id = -1;
            this.filterName = filterName;
            this.urlPatterns = urlPatterns;
        }
        
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
        public String getFilterName() {
            return filterName;
        }
        
        public List<String> getUrlPatterns() {
            return urlPatterns;
        }
    }
}