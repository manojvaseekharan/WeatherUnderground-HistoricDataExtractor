package com.vaseekharan.weatherundergroundextractor;

public class Worker implements Runnable {

	String latitude;
	String longitude;

	public Worker(String lat, String longi) {
		this.latitude = lat;
		this.longitude = longi;
	}

	public void run() {
		getAndSaveCSVFile();
	}

	public void getAndSaveCSVFile() {

		String postcode = Helpers.obtainPostcodeFromLatLong(
				Double.parseDouble(latitude), Double.parseDouble(longitude));
		String dateLowerLimit = "2012/01/01";
		String dateUpperLimit = "2014/10/25";
		boolean headersAdded = false;
		while (dateLowerLimit.equals(dateUpperLimit) == false) {
			System.out.println(dateLowerLimit);
			String url = "http://wunderground.com/"
					+ Helpers.getHistoricDataURL(postcode)
					+ dateLowerLimit
					+ "/DailyHistory.html?req_city=NA&req_state=NA&req_statename=NA";
			System.out.println(url);
			String commaDelimitedFile = Helpers.getCommaDelimitedFile(url
					+ "&format=1");
			commaDelimitedFile = commaDelimitedFile
					.substring(commaDelimitedFile.indexOf('\n') + 1);
			if (headersAdded == false) {
				headersAdded = true;
			} else {
				commaDelimitedFile = commaDelimitedFile
						.substring(commaDelimitedFile.indexOf('\n') + 1);
			}
			System.out.println(commaDelimitedFile);
			Helpers.writeToFile(latitude, longitude, commaDelimitedFile);
			dateLowerLimit = Helpers.getNextDate(dateLowerLimit);
			System.out.println("Next date = " + dateLowerLimit);
			System.out.println("----------");
		}
	}

}
