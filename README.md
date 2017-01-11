Google App Engine / CQRS with Event Sourcing
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
* Java 7
* [Maven](https://maven.apache.org/download.cgi) (at least 3.3.9)
* [Google Cloud SDK](https://cloud.google.com/sdk/) (aka gcloud)


## Maven

### Run Locally

    mvn appengine:run
    
    