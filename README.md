# Jeoparnaire

An open source network voting game based on the idea of jeopardy and who wants to be a millionaire.
Perfect for birthday and christmas parties, anniversary celebrations or just for having fun with friends.

## Features 

* questions are individually configurable via config file.
  * plain text questions (native java textfield)
  * images (native java image viewer)
  * videos (vlc)
  * include any external application
* java based display server
* java based voting client
  * up to 50 clients can be connection for voting.

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
  * The first 10 correct votes get points
* Negative points for wrong votes.
* Vote controlling
  * Click the buttons to vote (not recommended)
  * The W-A-S-D keys are mapped to A-B-C-D votes.
