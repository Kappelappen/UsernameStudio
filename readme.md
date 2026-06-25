
# UsernameStudio

UsernameStudio is a Java Swing desktop application that generates unique usernames based on styles, word lists, and user-defined rules.

## features

- Generate usernames from style-based word lists
- Customize output using:
  - Word count slider
  - Numbers toggle
  - Underscore separator
  - Symbol toggle
  - Capitalization option
- Copy generated username to clipboard
- Save usernames as favorites
- View generation history
- SQLite database for persistent storage

## How it works

UsernameStudio combines random words from a selected style and applies user-defined rules to generate unique usernames.


## 🗂 Database

The app uses SQLite with three main tables:

- `style` – username categories
- `word` – words used for generation
- `usernames` – generated history and favorites

## Tech Stack

- Java
- Swing (GUI)
- SQLite
- JDBC

## status

This is an early version (v0.1). Core functionality is implemented, and the project is actively evolving.

## Roadmap

- Words editor UI
- Favorites panel improvements
- Export usernames
- Better generation modes (creative / random / strict)
- UI polish

## Author

Built as a personal learning project in Java Swing.