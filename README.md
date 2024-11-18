# Directory Statistics Collector

A Java application that uses the `SimpleFileVisitor` class to traverse directories, collect statistics, and display detailed information about subfolders, files, and directory creation times in a human-readable format.

## Features
- Traverses a directory structure recursively.
- Calculates and displays:
  - Number of subfolders in each directory.
  - Number of files in each directory.
  - Total size of all files in a directory (in bytes).
  - Creation time of the directory in a formatted, human-readable manner.
- Handles exceptions for inaccessible files or directories.

## Example Output
- [Files and Paths] -> Subfolders: 6, Files: 9, Total Size: 46310 bytes, Creation Time: [2024-10-14 16:30:37]
- [Copies] -> Subfolders: 5, Files: 9, Total Size: 29407 bytes, Creation Time: [2024-09-19 00:23:12]

## Requirements
- **Java 8** or higher

## How It Works
1. Implements a custom visitor class, `StatsVisitor`, extending `SimpleFileVisitor<Path>`.
2. Uses the following methods:
   - `preVisitDirectory`: Resets counters and captures creation time for top-level directories.
   - `visitFile`: Counts files and accumulates their sizes.
   - `postVisitDirectory`: Outputs directory statistics once all files and subfolders are visited.
3. Formats the creation time of directories using `DateTimeFormatter`.
