Weather Underground - Historic Weather Extractor
=========

A very simple Java program that outputs a CSV file of weather data for the past two years for each Barclays Cycle Hire rack.

Typically you would use Weather Underground's API to access their data, but this program circumvents that.

Command Line Arguments
---
Compile using the command: *javac Main.java*. Then pass the stations.xml file as an argument.

*java Main stations.xml*

And blam! After many hours, you should have a CSV file for all 700+ bike racks with detailed weather data. Enjoy!

