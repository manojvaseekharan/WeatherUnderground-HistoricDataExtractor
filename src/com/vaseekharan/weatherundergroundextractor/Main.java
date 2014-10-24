package com.vaseekharan.weatherundergroundextractor;

public class Main {

	public static void main(String[] args) {
		
		String postcode = Helpers.obtainPostcodeFromLatLong(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
		String date = args[0];
		String dateUpperLimit = args[1];
		boolean headersAdded = false;
		while(date.equals(dateUpperLimit) == false)
		{
			System.out.println(date);
			String url = "http://wunderground.com/"+Helpers.getHistoricDataURL(postcode)+date+"/DailyHistory.html?req_city=NA&req_state=NA&req_statename=NA";
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
			Helpers.writeToFile(args[2], args[3],commaDelimitedFile);
			date = Helpers.getNextDate(date);
			System.out.println("Next date = " + date);
			System.out.println("----------");
		}
	}
}
