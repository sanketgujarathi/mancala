# MANCALA

This is the solution for bol.com coding assignment.

## Getting Started

To run the application on your local machine, clone this repository.

Ensure you have jdk1.8 installed on your system.

### Prerequisites

Run the following command to build the application jar.

```
maven clean package
```

This command will create the application jar in your target directory.

### Running the application

Navigate to the target directory where the jar was build. Run the following command to start the application

```
java -jar mancala 1.0-SNAPSHOT.jar
```

Open the link http://localhost:8080/mancala/index.html

![Welcome Screen](/img/welcome.jpg?raw=true "Welcome Screen")


Click on the New Game button to start the game.

Players should click on the pits in their area to make a move.

Players turn will be indicated in the UI.

Game ends when all the pits belonging to one of the players are empty


## Running the tests

Run the following command to run the automated unit tests.

```
maven clean test
```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Sanket Gujarathi**

