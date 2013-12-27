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
- You will notice after importing that the SampleAppEngine may have an error marked on the project.
	* Right click on SampleAppEngine project and select properties
	* Expand Google and select App Engine
	* Check the "Use Google App Engine"
	* Check "Enable local HRD support"
	* Uncheck "Use Datanucleus JDO/JPA to access the datastore"
	* Click OK
