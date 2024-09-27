# vaadin-flow-x6
This repository contains a Vaadin Flow add-on for the web component for the popular Javascript diagramming library X6 (also developed by Neotropic SAS)

## Requirement:
- Ensure you are using **Java 11**.
  
## Running the component demo

1. Navigate to the x6-flow project.
   
    ```bash
        cd [path to your x6-flow project]
    ```

2. Set the **JAVA_HOME** variable and execute Maven:
   
    ```bash
        JAVA_HOME=[path to your Java 11] ./mvnw --no-transfer-progress clean install
    ```

3. Navigate to the x6-flow-demo project.
   
    ```bash
        cd [path to your x6-flow-demo project]
    ```

4. execute maven:
   
    ```bash
        JAVA_HOME=[path to your Java 11] ./mvnw --no-transfer-progress clean install
    ```

> **Note**
>You can also run these commands from your preferred IDE if it supports terminal or command execution.
>

5. Run your application.
   
   you'll be able to find the demo at: http://localhost:8080/
