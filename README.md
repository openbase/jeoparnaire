# Jeoparnaire

An open source network voting game based on the idea of jeopardy and who wants to be a millionaire.
Perfect for birthday and christmas parties, anniversary celebrations or just for having fun with friends.

## Features 

* Questions are individually configurable via config file
  * Plain text questions
  * Images
  * Videos (e.g. vlc used as player)
  * Include any external application
* Java based display server
* Java based voting client
  * Up to 50 clients can be connected for voting

## Preview

Select a category or use the random generator to select a quest.
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-overview.png)

The quest will be displayed
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-quest.png)

all connected client can start the voting...
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-client.png)

After the voting is finished the quest results are displayed.
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-quest-result.png)

Followed by the resulting points
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-points-quest.png)

and the global raning of all voter.
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-point-global.png)

## Voting hints

* Parallel voting, first vote first win
  * The first 10 correct votes earn points
* Negative points (25% of quest points) for wrong votes
* Vote controlling
  * Click the buttons to vote (not recommended)
  * The W-A-S-D keys are mapped to A-B-C-D votes
  
## Installation

Requirements: A Java Runtime Environment (JRE) is installed on your system:
* http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

1. Create a new game folder (e.g. jeoparnaire)
2. Download the client and server binaries into the game folder:
   * server: https://github.com/openbase/jeoparnaire/releases/download/v1.0.0/jeoparnaire-1.0.0.jar
   * client: https://github.com/openbase/jeoparnaire/releases/download/v1.0.0/jeoparnaire-client-1.0.0.jar
3. Create a new resource folder within the game folder and place here all images and videos you want to use for the game.
4. Download the game configuration template and store thoses in the resource folder as well:
   * https://raw.githubusercontent.com/openbase/jeoparnaire/master/src/main/resources/JeoparnaireGameConfig.xml

## Configuration

Modify the JeoparnaireGameConfig.xml file:
* Add our categories, quest and answers.
* Link your images and videos

TODO: more details

## Startup

### Windows

Just execute ``jeoparnaire-1.0.0.jar`` to start the server.
Just execute ``jeoparnaire-client-1.0.0.jar`` to start the voting client.

### Linux

server: ``java -jar jeoparnaire-1.0.0.jar``
client: ``java -jar jeoparnaire-client-1.0.0.jar``
