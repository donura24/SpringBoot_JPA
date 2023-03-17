package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(StudentRepository studentRepository) {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            boolean continueEnteringStudents = true;
            while (continueEnteringStudents) {
                String firstName;
                while (true) {
                    System.out.print("Enter student first name: ");
                    if (scanner.hasNext("[a-zA-Z]+")) {
                        firstName = scanner.nextLine();
                        break;
                    } else {
                        System.out.println("Invalid input. First name must contain only letters.");
                        scanner.nextLine();
                    }
                }

                String lastName;
                while (true) {
                    System.out.print("Enter student last name: ");
                    if (scanner.hasNext("[a-zA-Z]+")) {
                        lastName = scanner.nextLine();
                        break;
                    } else {
                        System.out.println("Invalid input. Last name must contain only letters.");
                        scanner.nextLine();
                    }
                }

                String email;
                while (true) {
                    System.out.print("Enter student email: ");
                    email = scanner.nextLine();
                    if (email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Email must be in the format user@domain.com");
                    }
                }

                int age;
                while (true) {
                    System.out.print("Enter student age: ");
                    if (scanner.hasNextInt()) {
                        age = scanner.nextInt();
                        scanner.nextLine();
                        if (age > 0) {
                            break;
                        } else {
                            System.out.println("Age must be greater than 0");
                        }
                    } else {
                        System.out.println("Invalid input. Age must be an integer.");
                        scanner.nextLine();
                    }
                }

                Student student = new Student(firstName, lastName, email, age);
                try {
                    studentRepository.save(student);
                    System.out.println("Student saved successfully");
                } catch (Exception e) {
                    System.out.println("Error saving student: " + e.getMessage());
                }

                System.out.print("Do you want to enter another student? (y/n): ");
                String answer = scanner.nextLine();
                if (!answer.equalsIgnoreCase("y")) {
                    continueEnteringStudents = false;
                }
            }
            scanner.close();
        };
    }

}
