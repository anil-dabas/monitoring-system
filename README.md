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

