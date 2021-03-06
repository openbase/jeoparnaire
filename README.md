# Jeoparnaire

[![Build Status](https://travis-ci.org/openbase/jeoparnaire.svg?branch=master)](https://travis-ci.org/openbase/jeoparnaire?branch=master)
[![Build Status](https://travis-ci.org/openbase/jeoparnaire.svg?branch=latest-stable)](https://travis-ci.org/openbase/jeoparnaire?branch=latest-stable)

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

**Select a category or use the random generator to select a quest:**
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-overview.png)

**The quest will be displayed:**
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-quest.png)

**All connected clients can vote now:**
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-client.png)

**After the voting is finished the quest results are displayed:**
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-quest-result.png)

**Followed by the resulting points:**
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-points-quest.png)

**Finally the global raning of all voter is shown:**
![](https://raw.githubusercontent.com/openbase/jeoparnaire/master/docs/images/jeoparnaire-point-global.png)

## Voting hints

* Parallel voting, first vote first win
  * The first 10 correct votes earn points
* Negative points (25% of quest points) for wrong votes

## Controlling

### Server
  * `Enter` Confirm Quest / Next
    * Note: During the voting you need to confirme more than ones by pressing `Enter` if the vote is not finished yet.
      * Not finished means in this case there are still some points left for correct votes.
  * `Space` Random quest selection in the overview 
  * `Mouse` Can be used to select a quest in the overview
  
  Note: A hand "presenter" to control the server is supported as well.

### Client
  * Vote via the W-A-S-D keys on your keyboard which are mapped to A-B-C-D votes
    * `W` is mapped to answer A
    * `E` is mapped to answer B
    * `S` is mapped to answer C
    * `D` is mapped to answer D

## Requirements

A Java 11 (or higher) Runtime Environment (JRE) is required on the server and on each client machine:
* https://jp.azul.com/downloads/zulu-community/?&version=java-11-lts&architecture=x86-64-bit&package=jre

## Installation

1. Create a new game folder (e.g. jeoparnaire)
2. Download the client and server binaries into the game folder:
   * server: https://github.com/openbase/jeoparnaire/releases/download/v1.0.0/jeoparnaire-1.0.0.jar
   * client: https://github.com/openbase/jeoparnaire/releases/download/v1.0.0/jeoparnaire-client-1.0.0.jar
3. Create a new `JeoparnaireResource` folder within the game folder and place here all images and videos you want to use for the game.
4. Download the game configuration template and store it in the `JeoparnaireResource` folder as well:
   * https://raw.githubusercontent.com/openbase/jeoparnaire/master/src/main/resources/JeoparnaireGameConfig.xml
     * Download via link menu -> "Save link as..."

## Configuration

Modify the JeoparnaireGameConfig.xml file:
* Add our categories, quest and answers.
* Link your images and videos

``` xml
<Quest points="200" question="How many people live on earth 2016?">
    <Execution type="pre" command="showimage ${RESOURCES}/earth.jpg"/> <!-- optional: will be displayed before the question is asked -->
    <Execution type="post" command="showimage ${RESOURCES}/people.jpg"/> <!-- optional: will be displayed after the vote is finished -->
    <Execution type="final" command="showimage ${RESOURCES}/earth-with-people.png"/> <!-- optional: will be displayed after the correct answer is given -->
    <Answer text="7,442 billion "/> <!-- first answer needs to be the right one -->
    <Answer text="8,500 billion "/> <!-- wrong answer -->
    <Answer text="6,028 billion "/> <!-- wrong answer -->
    <Answer text="2,275 billion "/> <!-- wrong answer -->
</Quest>
```

## Startup

### Windows
* Just execute ``jeoparnaire-x.x.x.jar`` to start the server.
* Just execute ``jeoparnaire-client-x.x.x.jar`` to start the voting client.

### Linux
* server: ``java -jar jeoparnaire-x.x.x.jar``
* client: ``java -jar jeoparnaire-client-x.x.x.jar``

## Contribution
* Feel free to report [new Issues](https://github.com/openbase/jeoparnaire/issues/new)!
* If you are developer and you want to contribute to Jeoparnaire
    * Fork Jeoparnaire apply your features or fixes and create pull requests.
    * For long term contribution just apply for an openbase membership via support@openbase.org
