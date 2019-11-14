# ROCK-PAPER-SCISSORS

This is the solution for IMC coding assignment.

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
java -jar rock-paper-scissors-1.0-SNAPSHOT.jar
```

Follow the instructions displayed on the console to play the game.

```
Welcome to the game of Rock-Paper-Scissors!
Enter number of turns you wish to play:
2
Enter your name:
john
Round 1/2
Player JOHN enter your move(ROCK | PAPER | SCISSORS):
paper
JOHN's move: PAPER
CPU's move: SCISSORS
Player CPU wins the round
Round 2/2
Player JOHN enter your move(ROCK | PAPER | SCISSORS):
rock
JOHN's move: SCISSORS
CPU's move: PAPER
Player JOHN wins the round
Final Score is:
CPU:1
JOHN:1
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

