/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package phonebook2023;

/**
 *
 * @author HP
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing a record



class Record {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String phoneNumber;

    // Constructor to initialize the record
    public Record(String firstName, String lastName, String address, String city, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    // Getters for the record fields
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Override toString() method to display the record
    @Override
    public String toString() {
        return "First Name: " + firstName + "\nLast Name: " + lastName +
                "\nAddress: " + address + "\nCity: " + city + "\nPhone Number: " + phoneNumber + "\n";
    }
}

// Class to manage records
public class Phonebook2023 {
    private static final String FILE_NAME = "records.txt";
    private List<Record> records;

    // Constructor to initialize the record list and load records from file
    public Phonebook2023() {
        records = new ArrayList<>();
        loadRecordsFromFile();
    }

    // Method to add a new record
    public void addRecord(Record record) {
        // Check if the phone number already exists
        if (findRecordByPhoneNumber(record.getPhoneNumber()) == null) {
            records.add(record);
            saveRecordsToFile();
            System.out.println("Record added successfully.");
        } else {
            System.out.println("Phone number already exists. Cannot add duplicate record.");
        }
    }

    // Method to delete a record based on the phone number
    public void deleteRecord(String phoneNumber) {
        Record recordToDelete = findRecordByPhoneNumber(phoneNumber);
        if (recordToDelete != null) {
            records.remove(recordToDelete);
            saveRecordsToFile();
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Record not found.");
        }
    }

    // Method to modify a record based on the phone number
    public void modifyRecord(String phoneNumber) {
        Record recordToModify = findRecordByPhoneNumber(phoneNumber);
        if (recordToModify != null) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Address: ");
            String address = scanner.nextLine();

            System.out.print("Enter City: ");
            String city = scanner.nextLine();

            recordToModify = new Record(firstName, lastName, address, city, phoneNumber);
            records.remove(recordToModify);
            records.add(recordToModify);
            saveRecordsToFile();

            System.out.println("Record modified successfully.");
        } else {
            System.out.println("Record not found.");
        }
    }

    // Method to search records based on various criteria
    public void searchRecords() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Search Options:");
            System.out.println("1. Search by Last Name");
            System.out.println("2. Search by City");
            System.out.println("3. Search by Phone Number");
            System.out.println("4. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();
                    searchByLastName(lastName);
                    break;
                case 2:
                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();
                    searchByCity(city);
                    break;
                case 3:
                    System.out.print("Enter Phone Number: ");
                    String phoneNumber = scanner.nextLine();
                    searchByPhoneNumber(phoneNumber);
                    break;
                case 4:
                    System.out.println("Quitting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    // Method to display all records in a grid format
    public void displayAllRecords() {
        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        int numColumns = 3; // Number of columns in the grid
        int numRows = (int) Math.ceil((double) records.size() / numColumns);

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                int index = row * numColumns + col;
                if (index < records.size()) {
                    System.out.println(records.get(index));
                }
            }
            System.out.println();
        }
    }

    // Method to count the number of records
    public int countRecords() {
        return records.size();
    }

    // Method to search records by last name
    private void searchByLastName(String lastName) {
        List<Record> searchResults = new ArrayList<>();

        for (Record record : records) {
            if (record.getLastName().equalsIgnoreCase(lastName)) {
                searchResults.add(record);
            }
        }

        displaySearchResults(searchResults);
    }

    // Method to search records by city
    private void searchByCity(String city) {
        List<Record> searchResults = new ArrayList<>();

        for (Record record : records) {
            if (record.getCity().equalsIgnoreCase(city)) {
                searchResults.add(record);
            }
        }

        displaySearchResults(searchResults);
    }

    // Method to search records by phone number
    private void searchByPhoneNumber(String phoneNumber) {
        Record record = findRecordByPhoneNumber(phoneNumber);

        if (record != null) {
            List<Record> searchResults = new ArrayList<>();
            searchResults.add(record);
            displaySearchResults(searchResults);
        } else {
            System.out.println("Record not found.");
        }
    }

    // Method to display search results
    private void displaySearchResults(List<Record> searchResults) {
        if (searchResults.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (Record record : searchResults) {
                System.out.println(record);
            }
        }
    }

    // Method to find a record by phone number
    private Record findRecordByPhoneNumber(String phoneNumber) {
        for (Record record : records) {
            if (record.getPhoneNumber().equals(phoneNumber)) {
                return record;
            }
        }
        return null;
    }
 // Method to load records from a file
    private void loadRecordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String firstName = data[0];
                String lastName = data[1];
                String address = data[2];
                String city = data[3];
                String phoneNumber = data[4];
                Record record = new Record(firstName, lastName, address, city, phoneNumber);
                records.add(record);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while loading records from file.");
        }
    }

    // Method to save records to a file
    private void saveRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Record record : records) {
                String line = record.getFirstName() + "," + record.getLastName() + "," +
                        record.getAddress() + "," + record.getCity() + "," + record.getPhoneNumber();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while saving records to file.");
        }
    }

    // Main method to run the record management system
    public static void main(String[] args) {
    Phonebook2023    Phonebook2023 = new Phonebook2023();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nRecord Management System");
            System.out.println("1. Add Record");
            System.out.println("2. Delete Record");
            System.out.println("3. Modify Record");
            System.out.println("4. Search Records");
            System.out.println("5. Display All Records");
            System.out.println("6. Count Records");
            System.out.println("7. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();

                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();

                    System.out.print("Enter Phone Number: ");
                    String phoneNumber = scanner.nextLine();

                    Record record = new Record(firstName, lastName, address, city, phoneNumber);
                    Phonebook2023.addRecord(record);

                    System.out.println("Record added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Phone Number: ");
                    phoneNumber = scanner.nextLine();
                    Phonebook2023.deleteRecord(phoneNumber);
                    break;

                case 3:
                    System.out.print("Enter Phone Number: ");
                    phoneNumber = scanner.nextLine();
                    Phonebook2023.modifyRecord(phoneNumber);
                    break;

                case 4:
                    Phonebook2023.searchRecords();
                    break;

                case 5:
                    Phonebook2023.displayAllRecords();
                    break;

                case 6:
                    int recordCount = Phonebook2023.countRecords();
                    System.out.println("Number of records: " + recordCount);
                    break;

                case 7:
                    System.out.println("Quitting...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
}