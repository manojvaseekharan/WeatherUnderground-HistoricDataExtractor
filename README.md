Weather Underground - Historic Weather Extractor
=========

A very simple Java program that takes Latitude & Longitude as input and outputs a CSV file containing detailed historic weather data as an CSV file.

Typically you would use Weather Underground's API to access their data, but this program circumvents that.

Command Line Arguments
---
Compile using the command: *javac Main.java*. Then, use the following command, the terms in square brackets i.e [] are to be filled in with your input.

*java Main [startDate] [endDate] [latitude] [longitude]*
* startDate - the startDate of your query, in format YYYY/MM/DD
* endDate - the endDate of your query, in format YYYY/MM/DD *(non-inclusive)*.
* latitude/longitude

Example:

```java Main 01/01/2013 02/01/2013 51.5247725 -0.1334268```

