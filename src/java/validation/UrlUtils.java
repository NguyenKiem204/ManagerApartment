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
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("filter-mapping");
            doc.appendChild(rootElement);
            
            Element filterNameElement = doc.createElement("filter-name");
            filterNameElement.setTextContent(filterName);
            rootElement.appendChild(filterNameElement);
            for (String urlPattern : urlPatterns) {
                Element urlPatternElement = doc.createElement("url-pattern");
                urlPatternElement.setTextContent(urlPattern);
                rootElement.appendChild(urlPatternElement);
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
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));
            
            NodeList filterNameList = document.getElementsByTagName("filter-name");
            String filterName = filterNameList.item(0).getTextContent();
            NodeList urlPatternList = document.getElementsByTagName("url-pattern");
            List<String> urlPatterns = new ArrayList<>();
            for (int i = 0; i < urlPatternList.getLength(); i++) {
                urlPatterns.add(urlPatternList.item(i).getTextContent());
            }
            
            return new FilterMapping(filterName, urlPatterns);
        } catch (Exception ex) {
            Logger.getLogger(UrlUtils.class.getName()).log(Level.SEVERE, null, ex);
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