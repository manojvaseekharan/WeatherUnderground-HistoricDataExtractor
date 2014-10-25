package com.vaseekharan.weatherundergroundextractor;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException{
		Main main = new Main();
		main.parseXMLAndGetCSV(args[0], args[1], args[2]);
	}
	
	private void parseXMLAndGetCSV(String xmlFilePath, String lowBound, String upperBound)
	{

		File fXmlFile = new File(xmlFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("station");
			for (int temp = Integer.parseInt(lowBound); temp < Integer.parseInt(upperBound); temp++) 
			{
				Node nNode = nList.item(temp);	 
			    NodeList eElements = nNode.getChildNodes();
			    String latitude = eElements.item(0).getTextContent();
			    String longitude = eElements.item(1).getTextContent();
			    Worker w = new Worker(latitude,longitude);
			    Thread t = new Thread(w);
			    t.start();
			}
		} catch (ParserConfigurationException | SAXException |  IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
}
