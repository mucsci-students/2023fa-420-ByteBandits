# 2023fa-420-ByteBandits

# **Wordy Wasps** - A Word Puzzle Game by ByteBandits

## Table of Contents

1. [Introduction](#introduction)
2. [Building and Running the Game](#building-and-running-the-game)
3. [Game Commands](#game-commands)
4. [Getting Started](#getting-started)
5. [Gameplay](#gameplay)
6. [Saving and Loading](#saving-and-loading)
7. [Help](#help)
8. [Exiting the Game](#exiting-the-game)
9. [Design Patterns Used](#design-patterns-used)
10. [Authors](#authors)
11. [Contact](#contact)

## Introduction

Welcome to Wordy Wasps, an exciting word puzzle game! Challenge your vocabulary and word-building skills with our unique game. Form words using a given set of letters, following these rules:

- Words must contain at least 4 letters.
- Words must include the required letter.
- Letters can be used more than once.

Dive into the world of Wordy Wasps and start buzzing!

## Building and Running the Game

### Prerequisites
- Ensure Java is installed on your system.

### Instructions

### Linux and macOS:
- Ensure that homebrew is installed.
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
- Install gradle.
```bash
brew install gradle
```
- Build and run the program through gradle.
```bash
gradle clean build
gradle run                                  # For GUI mode
gradle --console plain run --args='--cli'   # For CLI mode
```
### Windows:

```bash
./gradlew clean build
./gradlew run                               # For GUI mode
./gradlew --console plain run --args='--cli' # For CLI mode
```

Alternatively:

1. Clone or download this repository.
2. Navigate to the project directory: cd 2023fa-420-ByteBandits
3. Compile Java source files: javac *.java
4. Run the game: java master

## Game Commands
Here are the available commands to navigate and play Wordy Wasps:

- /newpuzzle: Start a new puzzle.
- /basepuzzle: Restart the current puzzle with the same set of letters.
- /showpuzzle: Display the current set of 7 letters.
- /foundwords: Show the words you've already found.
- /guess: Enter a word you think is valid.
- /shuffle: Shuffle the 7 letters to get a new arrangement.
- /savepuzzle: Save the current puzzle for later (only puzzle and associated word).
- /savecurr: Save your game progress (includes points and found words).
- /loadpuzzle: Load a previously saved puzzle.
- /showstatus: Display your current game status.
- /help: Display help information.
- /exit: Quit the game.

## Getting Started
Download and run Wordy Wasps on your preferred platform. Follow the on-screen instructions to start a new game or load a saved one. Use the commands above to interact with the game.

## Gameplay
Initiate a new puzzle with /newpuzzle to receive seven unique letters. Harness your vocabulary to craft words containing the required letter. Use /guess to input your word guesses. Longer words grant more points! Monitor your progress via /showstatus and /foundwords.

## Saving and Loading
Wordy Wasps offers features to save and load games:
/savepuzzle: Preserve the current puzzle without game status.
/savecurr: Save your current game progress.
/loadpuzzle: Restore a previously saved puzzle.

## Help
For assistance or inquiries about the game, invoke the /help command.

## Exiting the Game
To conclude your game session, simply utilize the /exit command.

## Design Patterns Used
- Builder-  This design pattern is used to implement the rank dialogue popup in the game.
- MVC- This design pattern is used to implement the CLI version of this game.
- Strategy- This design pattern is used to implement the building of the New Puzzle button and building of the Custom Puzzle button in the GUI.
- Factory- This design pattern is used to provide method declarations for obtaining resources, specifically dictionary and game data.
- Singleton - This design pattern is used to have only one instance in our CLI MVC Controller
- Decorator - This design pattern is used to do our creation of what our puzzle looks like in our CLI


## Authors
- Logan Wasmer
- Ilynd Rapant
- Jose De La Cruz
- Joshua Dawson

## Contact
Thank you for playing Wordy Wasps! We hope you relish the wordy challenge. Should you stumble upon any issues or have suggestions, kindly get in touch with our team.

