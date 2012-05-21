package utils;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class ReadCData
{
	public static void main(String[] args) throws Exception
	{
		File file = new File("new.xml");
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = builder.parse(file);

		NodeList nodes = doc.getElementsByTagName("messages");
		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			NodeList title = element.getElementsByTagName("message");
			Element line = (Element) title.item(0);
			System.out.println("Message: " + getCharacterDataFromElement(line));
		}
	}

	public static String getCharacterDataFromElement(Element e)
	{
		Node child = e.getFirstChild();
		if (child instanceof CharacterData)
		{
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
}
