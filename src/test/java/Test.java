import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

public class Test {

	public static void main(String[] args) throws ParserConfigurationException{
		String xmlFileName = "testng.xml";
		
		
		List<XmlSuite> suite;
		try
		{
			TestNG testng = new TestNG();
		    suite = (List <XmlSuite>)(new Parser(xmlFileName).parse());
		    testng.setXmlSuites(suite);
		    testng.run();
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
		
	}
}
