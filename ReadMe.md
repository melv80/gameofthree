# Getting Started

### Documentation
This project contains a small Game called **Game of Three** that is played between two micro services.

#### Goal
Goal was to implement a game with two independent units - the players - communicating with each other using an API.

The game is played as followed:
When a player starts, it incepts a random number and sends it to the secon player as an approach of starting the game.
THe receiving player can now choose between adding one of {-1, 0, 1} to get to a number that is divisible by 3.
Divide it by three. The resulting whole number is then sent back to the original sender.

The same rules are applied until one player reaches the number 1 (after division).

*Small tip: For quick verification if a number is dividable by 3, you might calculate the sum of digits and check if it is dividable by 3.*

Examples:

Player 1: starts with 56

Player 2: 56 -> ADDS ONE(57), DIV 3-> 19

Player 1: 19 -> SUB ONE(18), DIV 3-> 6

Player 2: 6  -> NOTHING(6), DIV 3-> 2

Player 1: 2  -> ADDS ONE(3), DIV 3-> 1 -> WIN

#### How to Build

Project is a standard maven project see [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

#### How to Run

The project can be configured via spring boot application.properties, see file for further documentation.
You should start two instances and configure them in a way set they find each other:

1. -Dserver.port=99  -Dgame.local=http://localhost:99/api/v1  -Dgame.remote=http://localhost:100/api/v1 -Dgame.player-name=Bob
2. -Dserver.port=100 -Dgame.local=http://localhost:100/api/v1 -Dgame.remote=http://localhost:99/api/v1 -Dgame.player-name=Barbara

#### Design choices
I wanted to try out Spring Boot and Thyme Leaf to provide a neat UI, instead of implementing a pure socket based solution.
 
Services are configurable via config file and not automatically discovered, to play you need to configure them manually. 
 
The game can be played by generating GameEvents and send them to a service endpoint. Upon start the application tries to emit a
**ReadyToPlay** event which sets the reply for future events. 

All game output is sent to a logger named **GameManager** and can be configured via logback.xml. 

#### Web Interface
You can initiate a new game via web interface [http://localhost:99/](http://localhost:99/) of each service and watch them play. Reload the page to check for opponent.


#### Possible improvements

Since I limited the time I wanted to work on the project, there is still some room for improvement:
- A history of played games could be stored for each player
- UI for connecting to an endpoint
- A UI for user input, but since the user can only make one active choice namely the starting number, I omitted that and decided to configure the starting number via
  config file.
- generate a UI event if a player has "connected".


### References 
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.7.RELEASE/maven-plugin/)

