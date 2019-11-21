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

Follow the instructions displayed on the console to play the game.

```
Welcome to the game of Mancala!
Enter your name:
p1
Enter your name:
p2
Mancala board initialised...
Mancala game initialised...
**************Current Board**************
Mancala2 --> [0, 6, 6, 6, 6, 6, 6]
             [6, 6, 6, 6, 6, 6, 0] <-- Mancala1
Player P1 Choose a pit(1-6):
1
**************Current Board**************
Mancala2 --> [0, 6, 6, 6, 6, 6, 6]
             [0, 7, 7, 7, 7, 7, 1] <-- Mancala1
Player P1 Choose a pit(1-6):
```


## Running the tests

Run the following command to run the automated unit tests.

```
maven clean test
```


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Sanket Gujarathi**

