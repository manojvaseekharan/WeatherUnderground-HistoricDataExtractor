package com.vaseekharan.weatherundergroundextractor;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Helpers {
	
	public static String obtainPostcodeFromLatLong(double latitude, double longitude)
	{
		String stringURL = "http://uk-postcodes.com/latlng/"+latitude+","+longitude+".csv";
		try {
			URL urlURL = new URL(stringURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlURL.openStream()));
			String inputLine;
	        inputLine = in.readLine();
	        in.close();
	        String result = filterOutPostcode(inputLine);
	        return result;
		} catch (IOException e) {
			
		}
		return null;
		
	}
	
	private static String filterOutPostcode(String input)
	{
		String result = input.substring(0,input.indexOf(','));
		System.out.println(result);
		return result;
	}

	public static String getHistoricDataURL(String postcode)
	{
		int counter = 0;
		postcode = postcode.replaceAll(" ", "+");
		String stringURL = "http://www.wunderground.com/cgi-bin/findweather/hdfForecast?query=" + postcode;
		URL urlURL;
		try {

			urlURL = new URL(stringURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlURL.openStream()));
			String inputLine;
			 while ((inputLine = in.readLine()) != null)
			 {
				 if(inputLine.contains("<a href=\"/history/airport"))
				 {
					 if (counter == 1)
					 {
						 String result = inputLine.substring(inputLine.indexOf('"')+1, inputLine.length());
						 result = result.substring(0, result.indexOf('"'));
						 result = result.substring(0, result.indexOf("2014"));
						 return result;
						 
					 }
					 else
					 {
						 counter++;
					 }
					 
				 }
			 }
		            
		}catch (IOException e) {

		}
		return null;
		
		
	}

	public static String getCommaDelimitedFile(String url)
	{
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String inputLine;
			String result = "";
			 while ((inputLine = in.readLine()) != null)
			 {
				 try {
					inputLine = inputLine.substring(0,inputLine.length()-6);
				} catch (StringIndexOutOfBoundsException e) {

				}
				 result += inputLine;
				 result+= "\n";
			 }
			 return result;
		} catch (IOException e) {
	
		}
		return null;
	}

	public static String getNextDate(String currentDate)
	{
		Integer year = Integer.parseInt(currentDate.substring(0,4));
		Integer month = Integer.parseInt(currentDate.substring(5,7));
		Integer day = Integer.parseInt(currentDate.substring(8,10));
		
		if (year % 100 == 0 && year % 400 == 0 && day == 28)
		{
			return new String (String.valueOf(year) + "/" +"02/29");
		}
		else if (year % 100 == 0 && year % 400 != 0 && day == 28)
		{
			return new String (String.valueOf(year) + "/" +"03/01");
		}
		
		if (year % 100 == 0 && year % 400 == 0 && day == 29)
		{
			return new String (String.valueOf(year) + "/" + "03/01");
		}
		
		//end of year
		if (month == 12 && day == 31)
		{
			return new String(String.valueOf(year+1) + "/01/01");
		}
		
		if (month == 2)
		{
			if (day == 28)
			{
				return new String (year + "/03/01");
			}
			else
			{
				if(day < 9)
				{
					return new String (year + "/02/0" + String.valueOf(day+1));
				}
				else
				{
					return new String (year + "/02/" + String.valueOf(day+1));
				}
				
			}
		}
		
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
		{
			if (day == 31)
			{
				if(month < 9)
				{
					return new String(year + "/" + "0" + String.valueOf(month+1) + "/01");
				}
				else
				{
					return new String(year + "/" + String.valueOf(month+1) + "/01");
				}
			}
			else
			{
				if(month <= 9)
				{
					if(day < 9)
					{
						return new String(year + "/" + "0" + String.valueOf(month) + "/0" + String.valueOf(day+1));
					}
					else
					{
						return new String(year + "/" + "0" + String.valueOf(month) + "/" + String.valueOf(day+1));
					}
			
				}
				else
				{
					if(day < 9)
					{
						return new String(year + "/" + String.valueOf(month) + "/0" + String.valueOf(day+1));
					}
					else
					{
						return new String(year + "/" + String.valueOf(month) + "/" + String.valueOf(day+1));
					}
					
				}
			}
		}
		
		if (month == 4 || month == 6 || month == 9 || month == 11)
		{
			if (day == 30)
			{
				if(month < 9)
				{
					return new String(year + "/" + "0" + String.valueOf(month+1) + "/01");
				}
				else
				{
					return new String(year + "/" + String.valueOf(month+1) + "/01");
				}
			}
			else
			{
				if(month <= 9)
				{
					if(day < 9)
					{
						return new String(year + "/" + "0" + String.valueOf(month) + "/0" + String.valueOf(day+1));
					}
					else
					{
						return new String(year + "/" + "0" + String.valueOf(month) + "/" + String.valueOf(day+1));
					}
					
				}
				else
				{
					if(day < 9)
					{
						return new String(year + "/" + String.valueOf(month) + "/0"+  String.valueOf(day+1));
					}
					else
					{
						return new String(year + "/" + String.valueOf(month) + "/"+  String.valueOf(day+1));
					}
					
				}
			}
		}
		
		return null;
	}
	
	public static void writeToFile(String latitude, String longitude, String commaDelimitedFile)
	{
		try {
			FileWriter fw = new FileWriter("./"+latitude + " " + longitude +".csv",true);
			fw.write(commaDelimitedFile);
			fw.close();
		} catch (IOException e) {
			System.out.println("ERROR OCCURED with writing to file");
		}
		
		
	}
	
}
