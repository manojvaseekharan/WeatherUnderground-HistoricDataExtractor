Weather Underground - Historic Weather Extractor
=========

A very simple Java program that outputs a CSV file of weather data for the past two years for each Barclays Cycle Hire rack.

Typically you would use Weather Underground's API to access their data, but this program circumvents that.

Command Line Arguments
---
Compile using the command: *javac Main.java*. Then pass the stations.xml file, the start index, and the end index.

```java Main stations.xml 0 20``` - this will present you with the weather data for the first 19 bikes in the stations.xml file. 

For reference, there are 746 bike racks.

