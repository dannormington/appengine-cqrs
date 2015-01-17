Google App Engine / CQRS
=========================

CQRS pattern implemented using Google App Engine technologies. The example code includes both a base set of classes as well as a sample domain model, commands, events, and set of Cloud Endpoints to interact.

This implementation is based off of Greg Young's Simple CQRS C# example
located at https://github.com/gregoryyoung/m-r

##Sample App Notes
- Command and event registration is located in the ApplicationContextListener class.

##App Engine Technologies
- Datastore
	* https://developers.google.com/appengine/docs/java/datastore/
	* App Engine Datastore is a schemaless object datastore providing
	  robust, scalable storage for your web application, with the
	  following features:

		* No planned downtime
		* Atomic transactions
		* High availability of reads and writes
		* Strong consistency for reads and ancestor queries
		* Eventual consistency for all other queries
- Task Queue
	* https://developers.google.com/appengine/docs/java/taskqueue/
	* This example used the DeferredTask type to handle events. This could easily be switched to uri based tasks that communicate with servlets if that is your preference.
- Google Cloud Endpoints
	* https://developers.google.com/appengine/docs/java/endpoints/


##Required SDKs, Plugins, and Libraries
- Install Google Plugin for Eclipse and Google App Engine SDK
	* From Eclipse:
		* Help -> Install New Software...
		* Enter in the url based upon your version of Eclipse
			* Juno (4.2) - http://dl.google.com/eclipse/plugin/4.2
			* Kepler (4.3) - http://dl.google.com/eclipse/plugin/4.3
		* Check "Google Plugin For Eclipse"
		* Expand SDKs
		* Check Google App Engine Java SDK
			* This example was developed using version 1.8.8 but this isn't required.
- Java SE 7
	* http://www.oracle.com/technetwork/java/javase/downloads/index.html
- google-gson
	* https://code.google.com/p/google-gson/

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
