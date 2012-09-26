package de.itemis.jee6.generator;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.xpand2.output.FileHandle;
import org.eclipse.xpand2.output.PostProcessor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class XmlBeautifier implements PostProcessor
{
	protected String[] fileExtensions = new String[] { ".xml", ".xsl", ".xsd", ".wsdd", ".wsdl" };

	@Override
	public void beforeWriteAndClose(FileHandle handle)
	{
		if (isXmlFile(handle.getAbsolutePath()))
		{
			try
			{
				handle.setBuffer(format(handle.getBuffer().toString().trim()));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void afterClose(FileHandle impl)
	{
		// This method intentionally left blank
	}

	protected boolean isXmlFile(final String absolutePath) {
		for (int i = 0; i < fileExtensions.length; i++)
			if (absolutePath.endsWith(fileExtensions[i].trim()))
				return true;
		return false;
	}

	protected String format(final String unformattedXml) throws IOException, ParserConfigurationException, SAXException
	{
        final Document document = parseXmlFile(unformattedXml);
        final OutputFormat format = new OutputFormat(document);
        final Writer out = new StringWriter();

        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        final XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(document);

        return out.toString();
    }

	protected Document parseXmlFile(final String in) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(in));
        return db.parse(is);
	}
}