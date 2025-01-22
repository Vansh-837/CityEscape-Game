# CityEscape-Game
A fun maze game! Do try to beat it!

CityEscapeGame is a thrilling 2D Java game where you play as a thief escaping the cops while navigating through a vibrant metropolitan city. Collect diamonds and nitro boosts, avoid hurdles like spikes and potholes, and use your skills to outrun the cops!

Game Rules:

You have 5 minutes to finish the game, otherwise you lose.
You need to collect all the diamonds to complete the game.
If you collect a special reward Nitro, time to finish game will increase by 1 minute.
If you hit a pothole, you will loose and game ends.
If you hit a spike, time to complete game will decrease by 1 minute.
If any of two cops catch you, you loose the game instantly.
Build, Run and Test using IDE:

Build and run instructions:

Clone the repo down to your local machine with git pull (SSH key or HTTP link) Open the repo folder in your IDE/ Code editor Compile the code via main class, or hit run while selecting the folder src/main/java in VScode (Eclipse works too!) (If you want to do this manually: you can compile using the JAR file instead of running from main. The JAR can be found in the GameJAR directory after you compile it)

Once you are done building this project, Simply run the CityEscape class to start the game. Play and try to beat the Cops !!

Testing instructions:

After bulding and running, just open the IDE again and find your way to src/test/java Thereafter, select the logic or UI package to view the logic and UI tests respectively. If you want to see in depth branch coverage and test coverage, run mvn clean test and go to target/site/jacoco If you have live server you can click on the index.html file and go live to see the jacoco report!

Build, Run and Test and Read documentation using maven

Instructions:

Clone the repo down to your local machine with git pull (SSH key or HTTP link).
Open the terminal and go to the project folder.
Run command "mvn package" - this will create a build along with javadoc documentation and will also run tests and create a test report.
Run command "java -jar target/CityEscapeGame-1.0-SNAPSHOT.jar" to launch the game.
You can also read test cases coverage information by running command "open target/site/jacoco/index.html" on macos or "start target/site/jacoco/index.html" on windows computer.
To read javadoc documentation of this project, run the command "open target/site/apidocs/index.html" on macos or "start target/site/apidocs/index.html" on windows computer.
