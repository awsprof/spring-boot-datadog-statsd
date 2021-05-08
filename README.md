#Spring Boot Metrics to Datadog through statsd
There are multiple ways of collecting and sending metrics. Spring boot can be made to send metrics straight to datadog, however the easiest method is to run a datadog agent docker container and use statsd to send it to the agent UDP port on 8125. Although I found a few examples of using statsd and dogstatsd, I could not come up with a single example that uses datadog agent within a docker container exposing 8125, using micrometer statsd with custom tagging.

I have created some custom timer metrics to pump to datadog as an example. Please feel free to add more as required in your own projects.

A word of warning - I was not able to expose both prometheus and statsd at the same time. Maybe when I have time, I will look into this.

## Installation
This project depends on my two earlier projects 
- [Datadog Agent as Docker Container](https://github.com/awsprof/datadog-docker-monitoring) and 
- [Spring Boot Service Mesh with Consul](https://github.com/awsprof/spring-boot-consul-service-discovery) 

Clone the **Datadog Agent as Docker Container** project first and run: 
```bash
docker-compose up -d
```
Access the Consul UI on [http://localhost:8500/ui/dc1/services](http://localhost:8500/ui/dc1/services)

Once the Consul service comes up, clone this repo and open using an IDE of your choice. I use [JetBrains IntelliJ](https://www.jetbrains.com) by the way.




## Project Information

There are two Spring Microservices as separate maven projects. 

The RestConsumer service exposes an endpoint on
```
http://localhost:20080/consumer/invoke/{user}
```
This service invokes another REST Service aptly named the RestService Service ;-)

The producer service exposes another endpoint which can be invoked directly for testing on
```
http://localhost:20090/producer/sayHello/{name}
```
However, the main point about this repo is that, the consumer Microservice looks up the producer Microservice using Consul dynamically instead of hardcoding the hostname or port.

Once you start both the Microservices, use the following commands to test the endpoints. 

This is the Direct Service Endpoint
```bash
curl http://localhost:20090/service/sayHello/Foo
```
This is the Consumer Endpoint that invokes the Service endpoint by looking up the Service via Consul
```bash
curl http://localhost:20080/consumer/invoke/Bar
```
## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)