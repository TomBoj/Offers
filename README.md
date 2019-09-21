Offers
======

Building and running
------
This SpringBoot application is built with Gradle.

To build the application, simply run:

`./gradlew clean build`

To start the application, simply run:

`./gradlew bootRun`

By default, the application will run on http://localhost:8081.

This port is configured in `src/main/resources/application.properties`

Endpoints
------
For simplicity, there are only 5 exposed REST endpoints, these are:

* GET **/products** - returns list of all products
* GET **/products/{productId}** - returns a single product
* GET **/offers** - returns list of all offers
* GET **/offers/{offerId}** - returns a single offer
* PUT **/offers** - creates a new offer

Creating an offer
------
To create an offer, you need to post a valid json object to the above endpoint.
The required JSON format is:

```json
{
	"productIds" : ["0", "1"],
	"description" : "test offer",
	"price" : "1.99",
	"startDate" : "2019-12-03T10:15:30",
	"endDate" : "2019-12-10T10:15:30"
}
```
The only validation done on this data is
* check that endDate > startDate - returns 400
* checks to make sure the given products exist - returns 404

Any other problems with the data will likely result in a 500 error

UI
------
To make it easier to interact with the REST endpoints, I have created a very simple frontend at `ui/index.html`

This page simply has a couple of tables to view all of the offers and products, and a form to create a new offer.

Notes and assumptions
------
* The automatic updating of offer statuses is done by a scheduled task that runs every 5 seconds. As such, you may need to wait to rerun a query or refresh the UI in order to see the change in status.
* I have not disallowed the fetching of expired, cancelled, or pending offers, I've simply incorporated the status into the object. This means we could put any restriction (either frontend or backend) on these object in future development.
* For simplicity I have limited an offer to only include a product once, so you cannot add multiple quantities of a single product to an offer.
* For now, offers can only be created, returned or cancelled; I have not gone as far as to allow modifying an existing offer.
