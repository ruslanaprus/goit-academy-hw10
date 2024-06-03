# Multi-Utility Data Handler

This is solution for GoIT Academy Module 10 [Input-output streams. Work with files] hometask.

## Overview

Data Handler is a versatile toolkit designed to handle a variety of file data management tasks. This project includes packages for phone book management, user data handling, and word frequency analysis, offering a comprehensive solution for different data processing needs.

## Packages

### 1. Phone Book (task 1)

**Package:** `org.example.phonebook`

This package contains classes related to phone book functionality. The classes provide methods to:

- Read phone numbers from a file.
- Validate phone numbers according to specified patterns.
- Print phone numbers.
- Add new phone numbers to a file.

**Key Features:**

- **Validation:** Ensures phone numbers conform to specific patterns.
- **File Operations:** Read from and write to phone book files.
- **Resource Sharing:** Using singleton pattern ensures that there's only one copy of list of phone numbers and that file operations are coordinated properly.

### 2. User Data Controller (task 2)

**Package:** `org.example.users`

This package provides functionality to manage user data. It uses Google's Gson library for JSON processing and Java NIO for file operations. It includes the ability to:

- Read user data from text files.
- Convert user data to JSON format.
- Write user data to JSON files.

**Key Features:**

- **Format Conversion:** Convert between text and JSON formats.
- **File Operations:** Handle user data files efficiently.

### 3. Word Frequency Analysis (task 3)

**Package:** `org.example.wordcounter`

This package contains classes for performing word frequency analysis on .txt files. It processes long text files efficiently, making it suitable for analyzing large documents.
It provides functionality to:

- Read words from a file.
- Calculate the frequency of each word.
- Sort words by frequency.
- Write the frequency results to a file.

**Key Features:**

- **Frequency Calculation:** Accurately count word occurrences. Frequency count is case-insensitive
- **Sorting:** The calculated word frequencies are stored in a TreeMap for natural ordering by keys. Words can be organized based on their frequency.
- **Output:** Generate file with word frequency data.

## Getting Started

### Prerequisites

To build and run this project, ensure you have the following installed:

- **Java Development Kit (JDK) 21**: The project is compiled with Java 21.
- **Apache Maven 3.8.0 or higher**: Used for building the project and managing dependencies.
- **Git**: For cloning the repository.

### Required Dependencies

The project requires the following libraries, which are managed by Maven:

- **JUnit Jupiter Engine 5.9.2**: For running unit tests.
- **Gson 2.11.0**: For converting Java objects to JSON and vice versa.

You do not need to manually install these dependencies; Maven will handle them for you.
### Installation

1. Clone the repository or download the `goit-academy-hw10` file and include it in your Java project.
2. Navigate to the project directory: `cd goit-academy-hw10`
3. Build the project using your preferred build tool (e.g., Maven, Gradle).

## Usage

### `phonebook` Usage Example

```java
// Create an instance of NumberHandler
NumberHandler numberHandler = NumberHandler.getInstance("path/to/phone_numbers.txt");

// Print valid phone numbers
numberHandler.printValidPhoneNumbers();

// Add a valid phone number
try {
    numberHandler.addValidPhoneNumber("123-456-7890");
} catch (InvalidPhoneNumberException e) {
    System.err.println(e.getMessage());
}
```

### `users` Usage Example

```java
try {
// Read user data from a text file and add it to the list   
    List<User> users = readUsersFromFile("path/to/input.txt");
    
// Convert user data to JSON format and write it in the file
    writeJsonToFile(users, "path/to/output.json");
} catch (IOException e) {
    e.printStackTrace();
}
```

### `wordcounter` Usage Example

```java
// Read text from the file
List<String> words = readWordsFromFile("path/to/words.txt");

// Calculate frequency of each word from the text
Map<String, Integer> frequencyMap = calculateWordFrequency(words);

// Sort the list of words by frequency in descending order and write result in the file
writeResultsToFile(sortByFrequency(frequencyMap), "path/to/frequency_output.txt");
```


