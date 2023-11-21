package com.compozent_internship;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Student {
	private String studentId;
	private String name;
	private int age;
	private double gpa;
	private int attendance;

	// Constructors, getters, and setters

	public Student(String studentId, String name, int age, double gpa) {
		this.studentId = studentId;
		this.name = name;
		this.age = age;
		this.gpa = gpa;
		this.attendance = 0; // Default attendance
	}

	public String getStudentId() {
		return studentId;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public int getAttendance() {
		return attendance;
	}

	public void updateAttendance() {
		this.attendance++;
	}

	// Additional method to calculate CGPA
	public double calculateCGPA() {
		return gpa; // For simplicity, assuming CGPA is the same as GPA
	}

	// Additional method to calculate percentage
	public double calculatePercentage() {
		return (gpa / 4.0) * 100.0; // Assuming GPA is on a 4.0 scale
	}
}

class SchoolAdmin {
	private String username;
	private String password;

	public SchoolAdmin(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean authenticate(String enteredUsername, String enteredPassword) {
		return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
	}
}

public class StudentRecordSystem {
	private Map<String, Student> studentRecords;
	private SchoolAdmin admin;

	public StudentRecordSystem() {
		this.studentRecords = new HashMap<>();
		this.admin = new SchoolAdmin("admin", "admin123"); // Set admin username and password
	}

	public void addStudent(String studentId, String name, int age, double gpa) {
		Student newStudent = new Student(studentId, name, age, gpa);
		studentRecords.put(studentId, newStudent);
		System.out.println("Student added successfully!");

	}

	public void viewStudentDetails(String studentId) {
		if (studentRecords.containsKey(studentId)) {
			Student student = studentRecords.get(studentId);
			// Print student details
			System.out.println("\nStudent ID: " + student.getStudentId());
			System.out.println("Name: " + student.getName());
			System.out.println("Age: " + student.getAge());
			System.out.println("GPA: " + student.getGpa());
			System.out.println("CGPA: " + student.calculateCGPA());
			System.out.println("Percentage: " + student.calculatePercentage() + "%");
			System.out.println("Attendance: " + student.getAttendance());
		} else {
			System.out.println("Student not found!");
		}
	}

	public void updateStudentGpa(String studentId, double newGpa) {
		if (studentRecords.containsKey(studentId)) {
			Student student = studentRecords.get(studentId);
			student.setGpa(newGpa);
			System.out.println("GPA updated successfully!");
		} else {
			System.out.println("Student not found!");
		}
	}

	public void markAttendance(String studentId, String adminUsername, String adminPassword) {
		if (studentRecords.containsKey(studentId)) {
			// Ensure only the admin can mark attendance
			if (admin.authenticate(adminUsername, adminPassword)) {
				Student student = studentRecords.get(studentId);
				student.updateAttendance();
				System.out.println("Attendance marked successfully!");
			} else {
				System.out.println("Invalid admin credentials. Cannot mark attendance.");
			}
		} else {
			System.out.println("Student not found!");
		}
	}

	public static void main(String[] args) {
		StudentRecordSystem recordSystem = new StudentRecordSystem();
		Scanner scanner = new Scanner(System.in);

		boolean exitProgram = false;

		while (!exitProgram) {
			System.out.println("\nWelcome to the Student Record System!");
			System.out.println("1. Admin Login");
			System.out.println("2. Student Login");
			System.out.println("3. Exit");
			System.out.println("Enter your choice:");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1:
				// Admin login
				System.out.println("Enter admin username:");
				String adminUsername = scanner.nextLine();
				System.out.println("Enter admin password:");
				String adminPassword = scanner.nextLine();

				if (recordSystem.admin.authenticate(adminUsername, adminPassword)) {
					System.out.println("Admin login successful!");

					// Admin functionalities...
					boolean exitAdmin = false;

					while (!exitAdmin) {
						System.out.println("\n1. Add Student");
						System.out.println("2. View Student Details");
						System.out.println("3. Update Student GPA");
						System.out.println("4. Mark Attendance");
						System.out.println("5. Return to Main Page");
						System.out.println("Enter your choice:");

						int adminChoice = scanner.nextInt();
						scanner.nextLine(); // Consume the newline character

						switch (adminChoice) {
						case 1:
							// Add Student
							System.out.println("Enter Student ID:");
							String studentId = scanner.nextLine();
							System.out.println("Enter Student Name:");
							String studentName = scanner.nextLine();
							System.out.println("Enter Student Age:");
							int studentAge = scanner.nextInt();
							System.out.println("Enter Student GPA:");
							double studentGpa = scanner.nextDouble();
							scanner.nextLine(); // Consume the newline character

							recordSystem.addStudent(studentId, studentName, studentAge, studentGpa);
							break;

						case 2:
							// View Student Details
							System.out.println("Enter Student ID:");
							String viewStudentId = scanner.nextLine();

							recordSystem.viewStudentDetails(viewStudentId);
							break;

						case 3:
							// Update Student GPA
							System.out.println("Enter Student ID:");
							String updateGpaId = scanner.nextLine();
							System.out.println("Enter new GPA:");
							double newGpa = scanner.nextDouble();
							scanner.nextLine(); // Consume the newline character

							recordSystem.updateStudentGpa(updateGpaId, newGpa);
							break;

						case 4:
							// Mark Attendance
							System.out.println("Enter Student ID:");
							String markAttendanceId = scanner.nextLine();

							recordSystem.markAttendance(markAttendanceId, adminUsername, adminPassword);
							break;

						case 5:
							// Return to Main Page
							exitAdmin = true;
							break;

						default:
							System.out.println("Invalid choice. Try again.");
						}
					}
				} else {
					System.out.println("Invalid admin credentials. Exiting...");
				}
				break;

			case 2:
				// Student login
				System.out.println("Enter student ID:");
				String studentId = scanner.nextLine();

				// Student functionalities...
				recordSystem.viewStudentDetails(studentId);

				break;

			case 3:
				// Exit Program
				exitProgram = true;
				break;

			default:
				System.out.println("Invalid choice. Try again.");
			}
		}

		// Close the scanner
		scanner.close();
	}
}
