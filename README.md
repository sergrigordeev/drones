## Drones



---




### Build

For building project  please use:

    mvn clean install
---

### Run with docker

    docker-compose up

### Run jar file
-  cd .\drones-service\container\
-  java -jar .\target\drones-container.jar


#### Predefined drones
- **Serial Number**: SBL_SN_1, **model**: LIGHTWEIGHT, **max weight**: 150, **state**: IDLE
- **Serial Number**: SBL_SN_2', **model**: MIDDLEWEIGHT, **max weight**: 250,  **state**: DELIVERING
- **Serial Number**: SBL_SN_3', **model**: CRUISERWEIGHT, **max weight**: 350,  **state**: RETURNING
- **Serial Number**: SBL_SN_4', **model**: HEAVYWEIGHT, **max weight**: 450,  **state**: IDLE     
- **Serial Number**: SBL_SN_5', **model**: HEAVYWEIGHT, **max weight**: 450,  **state**: LOADING

Drones **SBL_SN_2** & **SBL_SN_5** have loaded meds









