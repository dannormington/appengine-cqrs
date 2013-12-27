Google App Engine / CQRS
=========================

CQRS pattern implemented using Google App Engine technologies

This implemenatation is based off of Greg Young's Simple CQRS example
located at https://github.com/gregoryyoung/m-r

##Required SDKs
- Google App Engine (This example was developed using version 1.8.8)
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

##Notes
This implemenation is still missing the message bus portion that will
publish domain events using GAE task or deferred queues.
