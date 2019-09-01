# Url shortening service
Service provides functionality of building short aliases for original URLs.
Short alias can be built for custom domains or for default service domain which is set in service settings.

For example, original url https://search.yahoo.com/search?p=short+url&fr=yfp-t&fp=1&toggle=1&cop=mss&ei=UTF-8 can be 
shortened as http://example.com/1g9xsFCyOuXC22ETjwveTP

Short url consists of domain and uniqueId. UniqueId is a base62-encoded identifier based on unique sequence id, service 
instance id, current nano-time and partition, calculated from original url. That means uniqueId is the unique identifier through 
all instances of the service. 
The same partition id can be extracted from short url or calculated form original url, that allows us to split storing of url bindings
through different storage instances for different partitions (in current implementation data for all partitions stores into the same storage).

For demonstration purposes the service store url bindings into local H2 database

## System requirements
Java 11 installed

## Run service from source code
To build jar archive with the service you need to run the following command from the root directory of the project\
```./gradlew build```\
To execute service run following command form the root directory of the project\
```java -jar build/libs/url-shortener-0.0.1-SNAPSHOT.jar```
