package Protocol;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLProtocol {
	 private static String Username, ip, port, status, message, filename, content,Password;
	    
	    public XMLProtocol(String _Username, String _ip, String _port, String _status) {
	        Username = _Username;
	        ip = _ip;
	        port = _port;
	        status = _status;
	    }
	    
	    public XMLProtocol(String _Username, String _message) {
	        Username = _Username;
	        message = _message;
	    }
	    
	    public XMLProtocol(String _filename, String _port, String _content) {
	        filename = _filename;
	        port = _port;
	        content = _content;
	    }
	    
	    public static String write(String type) {
	        try {
	            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	            Document doc = docBuilder.newDocument(); 
	            if(type.equals("register")) {//dang ky
	            	Element register = doc.createElement("REGISTER");
	            	doc.appendChild(register);
	            	Element user_name = doc.createElement("USER_NAME");
	            	user_name.setTextContent(Username);
	            	register.appendChild(user_name);
	            	Element pass_word = doc.createElement("PASSWORD");
	            	pass_word.setTextContent(Password);
	            	register.appendChild(pass_word);
	            }
	            
	            if(type.equals("login"))  {
	                Element session = doc.createElement("SESSION");
	                doc.appendChild(session);
	            
	                Element peername = doc.createElement("PEER_NAME"); 
	                peername.setTextContent(Username);
	                session.appendChild(peername);
	            
	                Element _port = doc.createElement("PORT"); 
	                _port.setTextContent(port);
	                session.appendChild(_port);
	            }
	            else if(type.equals("alive")) {
	                Element session = doc.createElement("SESSION_KEEP_ALIVE");
	                doc.appendChild(session);
	                
	                Element peername = doc.createElement("PEER_NAME");
	                peername.setTextContent(Username);
	                session.appendChild(peername);
	                
	                Element _status = doc.createElement("STATUS");
	                _status.setTextContent(status);
	                session.appendChild(_status);
	            }
	            else if(type.equals("deny")) {
	                Element session = doc.createElement("SESSION_DENY");
	                doc.appendChild(session);
	            }
	            else if(type.equals("accept")) {
	                Element session = doc.createElement("SESSION_ACCEPT");
	                doc.appendChild(session);
	                
	                Element peername = doc.createElement("PEER_NAME");
	                peername.setTextContent(Username);
	                session.appendChild(peername);
	                
	                Element _ip = doc.createElement("IP");
	                _ip.setTextContent(ip);
	                session.appendChild(_ip);
	                
	                Element _port = doc.createElement("PORT");
	                _port.setTextContent(port);
	                session.appendChild(_port);
	            }
	            else if(type.equals("chat_req")) {
	                Element chat = doc.createElement("CHAT_REQ");
	                doc.appendChild(chat);
	                
	                Element peername = doc.createElement("PEER_NAME");
	                peername.setTextContent(Username);
	                chat.appendChild(peername);
	            }
	            else if(type.equals("chat_deny")) {
	                Element chat = doc.createElement("CHAT_DENY");
	                doc.appendChild(chat);
	            }
	            else if(type.equals("chat_accept")) {
	                Element chat = doc.createElement("CHAT_ACCEPT");
	                doc.appendChild(chat);
	            }
	            else if(type.equals("chat_msg")) {
	                Element chat = doc.createElement("CHAT_MSG");
	                chat.setTextContent(message);
	                doc.appendChild(chat);
	            }
	            else if(type.equals("chat_close")) {
	                Element chat = doc.createElement("CHAT_CLOSE");
	                doc.appendChild(chat);
	            }
	            else if(type.equals("file_req")) {
	                Element file = doc.createElement("FILE_REQ");
	                file.setTextContent(filename);
	                doc.appendChild(file);
	            }
	            else if(type.equals("file_req_noack")) {
	                Element file = doc.createElement("FILE_REQ_NOACK");
	                doc.appendChild(file);
	            }
	            else if(type.equals("file_req_ack")) {
	                Element file = doc.createElement("FILE_REQ_ACK");
	                doc.appendChild(file);
	                
	                Element _port = doc.createElement("PORT");
	                _port.setTextContent(port);
	                file.appendChild(_port);
	            }
	            else if(type.equals("file_data_begin")) {
	                Element file = doc.createElement("FILE_DATA_BEGIN");
	                doc.appendChild(file);
	            }
	            else if(type.equals("file_data")) {
	                Element file = doc.createElement("FILE_DATA");
	                file.setTextContent(content);
	                doc.appendChild(file);
	            }
	            else if(type.equals("file_data_end")) {
	                Element file = doc.createElement("FILE_DATA_END");
	                doc.appendChild(file);
	            }
	            
	            return docToString(doc);
	        }
	        catch(ParserConfigurationException | DOMException ex) {
	            ex.printStackTrace();
	        } catch (Exception ex) {
	            Logger.getLogger(XMLProtocol.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return null;
	    }
	    
	     public static String docToString(Document _doc) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource((Node) _doc), new StreamResult(writer));
		String output = writer.getBuffer().toString();
		return output;
	    }
}
