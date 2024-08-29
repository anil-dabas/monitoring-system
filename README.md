# Central Monitoring System 

## About 
- The project focuses monitoring the sensors in the warehouse and send the alert signal using a central service if the readings of the sensors are above a threshold 

## Assumptions 

- The sensors list that we want to simulate can be configured as well the default value is
    ``` h1,t1,h2,t2,h3,t3```
- The threshold values are assumed as
  1. Temperature - 35°C
  2. Humidity - 50%

- These values can be configured in the application.yaml file as well
  ``` 
    app:
    sensor:
      list: t1,h1,t2,h2,t3,h3,t4,h4,t5,h5,t6,h6,t7,h7,t8,h8,t9,h9
      threshold:
        temperature: 35
        humidity: 50
  ```


## Tech Involved 

- Java 17 
- Spring Web flux
- Java JMS
- SpringBoot


## How to Run the application 

1. Download the application from Git in a directory

2. Please keep the port 8080 free as the application will run on port 8080

3. Run below command from project root directory (use terminal or cmd)
   1. For Mac / Linux ```./gradlew bootRun```
4. Now the application will start and you will see the SpringBoot banner

## Details 

- I have created a ```SensorSimulator.java``` that generates the messages on UDP ports as per the requirements 
- In the ```WarehouseService.java``` I am listening to the messages on the ports and then publishing to JMS broker (SpringBoot Embedded broker) 
- In ```CentralMonitoringService``` I am consuming the messages and checking for the configured threshold of the temperature and humidity 
- If the threshold is breached I generate an alert (In this case I am printing on console) 
- 