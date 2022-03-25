
# Racetrack 

The intention of this project was to build the popular paper game 
Racetrack in a Java environement.

To play the game please read the rules on Wikipedia and also read the 
section *Setup* below.

[Wikipedia Link](https://en.wikipedia.org/wiki/Racetrack_(game)) for exact description of the game rules. 
## Documentation

[GitHub Link](https://github.zhaw.ch/PM2-IT21aWIN-fame-rayi-wahl/gruppe06-gamma-projekt1-racetrack
)

The project is build with Gradle and available on GitHub through the link above. 
To start the game open the GitHub Repository in your IDE and either start it via Gradle run or
go to the class ConsoleApp and start the main method.

The tests which reflect the game rules and the guarantee of the funcionality of it
are available [here](https://github.zhaw.ch/PM2-IT21aWIN-fame-rayi-wahl/gruppe06-gamma-projekt1-racetrack/tree/main/src/test/java/ch/zhaw/pm2/racetrack).
The tests are based on JUnit 5. 

Class diagram: [here](https://github.zhaw.ch/PM2-IT21aWIN-fame-rayi-wahl/gruppe06-gamma-projekt1-racetrack/tree/main/diagram)
## Setup

__Track file__

In the game flow you get asked to choose a track file. This is 
file for the game board.
The name of the available default tracks:

    1. challenge.txt
    2. oval-anticlock-right.txt
    3. oval-clock-up.txt
    4. quater-mile.txt 

Feel free to upload your own track file in the correct directory.
Make sure the track fits the rules: 

    1. The track must be rectangular 
    2. The car ID (A, B, C, ...) must be identifying -> unique in the whole track 
    3. The finish line is oriented in one defined direction (see default tracks)

__Move Strategy__

In the game flow you get asked to choose a so called Move Strategy. 
There are three possible options:

    1. DO_NOT_MOVE: The car remains it position the whole game 
    2. USER: Individual moving the car: see USER paragraph below
    3. MOVE_LIST: pre-defined moves: see MOVE_LIST paragraph below 


*DO_NOT_MOVE:*

The car does not move which means that the car remains it start position
the whole game and can not be changed.

---

*USER desciprion:*

If this strategy got choosed you can move the car by your own with the 
nine possible move vectors: 

    1. DOWN_LEFT
    2. DOWN
    3. DOWN_RIGHT
    4. LEFT 
    5. NONE 
    6. RIGHT 
    7. UP_LEFT 
    8. UP 
    9. UP_RIGHT

---

*MOVE_LIST description:*

If this strategy got choosed you can not take influence of the movement 
of the car. The file contains move commands and the car moves along 
this commands. 




## 🚀 About Us
We are a small team of three persons calles 'Gamma'. We are studing 
computer science students at the Zurich University of Applied Science.
