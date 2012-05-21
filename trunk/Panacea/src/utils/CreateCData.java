package utils;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;

public class CreateCData
{
	public static void main(String[] args)
	{
		String xml = "<root>" + "   <messages>" + "       <message></message>"
				+ "   </messages>" + "</root>";

		SAXBuilder builder = new SAXBuilder();
		try
		{
			Document document = builder.build(new StringReader(xml));
			Element root = document.getRootElement();
			Element comments = root.getChild("messages");

			Element comment = comments.getChild("message");
			comment.setContent(new CDATA("Hello World"));
			Writer writer = new FileWriter(new File("new.xml"));
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(document, writer);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}