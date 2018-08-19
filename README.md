# PSL Internship challenge

### Author: Brayan Andrés Henao (bryanhenao96@gmail.com) - Universidad Icesi

## How to play the game?
I decide to compile the whole source code in a JAR file, so it's more easy to run. For this game, you need to have Java (8) installed and then use the following command:

```console
java -jar PSL_Mineswipper_Challenge.jar
```
Then you will see something like this: 
![](https://i.imgur.com/AyAkNtJ.png)
 
 You need to enter the input in the below order, separated by a blank space between them:   
 1) The number of rows in the board.  
 2) The number of columns in the board.  
 3) The number of mines in the board.
 
Here is an example of the input for an 8 x 8 board with 5 mines:
![](https://i.imgur.com/WikCH6V.png)
 
## How to compile from source?
If you want to compile the project by yourself, you need to have Java (8) in your path and follow the next steps:
 1) Move to the source directory
 ```console
 cd src/
 ```
 2) Use the java compiler to compile the main class (Game). It also compiles all the needed classes.  
 ```console
 javac ./model/Game.java
 ```
 3) And then run it.
 ```console
 java model.Game
 ```
 

### Start playing minesweeper and have fun!


