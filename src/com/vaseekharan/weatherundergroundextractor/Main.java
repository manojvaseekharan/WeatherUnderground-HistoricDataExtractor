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
		main.parseXMLAndGetCSV(args[0]);
	}
	
	private void parseXMLAndGetCSV(String xmlFilePath)
	{
		
		File fXmlFile = new File(xmlFilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("station");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);	 
			    NodeList eElements = nNode.getChildNodes();
			    String latitude = eElements.item(0).getTextContent();
			    String longitude = eElements.item(1).getTextContent();
			    getAndSaveCSVFile(latitude, longitude);
			    
			}
		} catch (ParserConfigurationException | SAXException |  IOException e) {
			e.printStackTrace();
		}
		
	}

	public void getAndSaveCSVFile(String latitude, String longitude) {
		
		String postcode = Helpers.obtainPostcodeFromLatLong(Double.parseDouble(latitude), Double.parseDouble(longitude));
		String dateLowerLimit = "2012/01/01";
		String dateUpperLimit = "2014/10/25";
		boolean headersAdded = false;
		while(dateLowerLimit.equals(dateUpperLimit) == false)
		{
			System.out.println(dateLowerLimit);
			String url = "http://wunderground.com/"+Helpers.getHistoricDataURL(postcode)+dateLowerLimit+"/DailyHistory.html?req_city=NA&req_state=NA&req_statename=NA";
			System.out.println(url);
			String commaDelimitedFile = Helpers.getCommaDelimitedFile(url+"&format=1");
			commaDelimitedFile = commaDelimitedFile.substring(commaDelimitedFile.indexOf('\n')+1);
			if(headersAdded == false)
			{
				headersAdded = true;
			}
			else
			{
				commaDelimitedFile = commaDelimitedFile.substring(commaDelimitedFile.indexOf('\n')+1);
			}
			System.out.println(commaDelimitedFile);
			Helpers.writeToFile(latitude, longitude,commaDelimitedFile);
			dateLowerLimit = Helpers.getNextDate(dateLowerLimit);
			System.out.println("Next date = " + dateLowerLimit);
			System.out.println("----------");
		}
	}
	
}
