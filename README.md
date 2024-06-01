# Phone Number Utility

A utility class to handle phone numbers in Java. This utility provides methods to read, validate, print, and add phone numbers to a file.

## Usage

### Installation

Clone the repository or download the `NumberHandler.java` file and include it in your Java project.

### Usage Example

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
