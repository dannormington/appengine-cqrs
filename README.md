Google App Engine / CQRS
=========================

CQRS pattern implemented using Google App Engine technologies

##Required SDKs
- Google App Engine (This example uses 1.8.8)
- Java SE 7

##Import Instructions for Eclipse
- Right click in package explorer and select Import
- Browse to path where you cloned repository
- Import both SimpleCQRS and SampleAppEngine
- You will notice after importing that the SampleAppEngine project may have the standard red "x" error displayed. To fix this issue take the following steps:
	* Right click on SampleAppEngine project and select properties
	* Expand Google and select App Engine
	* Check the "Use Google App Engine"
	* Check "Enable local HRD support"
	* Uncheck "Use Datanucleus JDO/JPA to access the datastore"
	* Click OK
