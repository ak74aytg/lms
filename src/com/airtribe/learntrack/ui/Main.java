package com.airtribe.learntrack.ui;

import java.util.List;
import java.util.Scanner;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

public class Main {
    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public Main() {
        scanner = new Scanner(System.in);
        studentService = new StudentService();
        courseService = new CourseService();
        enrollmentService = new EnrollmentService(studentService, courseService);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        boolean running = true;
        System.out.println("Welcome to LearnTrack!");
        while (running) {
            showMainMenu();
            try {
                int choice = readInt("Choose an option: ");
                switch (choice) {
                    case 1:
                        studentMenu();
                        break;
                    case 2:
                        courseMenu();
                        break;
                    case 3:
                        enrollmentMenu();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Option not found. Please choose a menu number.");
                }
            } catch (InvalidInputException exception) {
                System.out.println("Input error: " + exception.getMessage());
            }
        }
        scanner.close();
        System.out.println("Goodbye!");
    }

    private void showMainMenu() {
        System.out.println("\n=== LearnTrack Main Menu ===");
        System.out.println("1. Student management");
        System.out.println("2. Course management");
        System.out.println("3. Enrollment management");
        System.out.println("0. Exit");
    }

    private void studentMenu() throws InvalidInputException {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student");
            System.out.println("5. Deactivate student");
            System.out.println("6. Activate student");
            System.out.println("0. Back");
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        printStudents();
                        break;
                    case 3:
                        printStudent(readInt("Student ID: "));
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        studentService.deactivateStudent(readInt("Student ID: "));
                        System.out.println("Student deactivated.");
                        break;
                    case 6:
                        studentService.activateStudent(readInt("Student ID: "));
                        System.out.println("Student activated.");
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Option not found.");
                }
            } catch (EntityNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void addStudent() throws InvalidInputException {
        String firstName = readText("First name: ");
        String lastName = readText("Last name: ");
        String email = readText("Email: ");
        String batch = readText("Batch: ");
        Student student = studentService.addStudent(firstName, lastName, email, batch);
        System.out.println("Student added with ID " + student.getId() + ".");
    }

    private void updateStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readInt("Student ID: ");
        String firstName = readText("New first name: ");
        String lastName = readText("New last name: ");
        String email = readText("New email: ");
        String batch = readText("New batch: ");
        studentService.updateStudent(id, firstName, lastName, email, batch);
        System.out.println("Student updated.");
    }

    private void printStudents() {
        if (studentService.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : studentService.listStudents()) {
            System.out.println(student);
        }
    }

    private void printStudent(int id) throws EntityNotFoundException {
        System.out.println(studentService.findStudentById(id));
    }

    private void courseMenu() throws InvalidInputException {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add course");
            System.out.println("2. View all courses");
            System.out.println("3. Search course by ID");
            System.out.println("4. Deactivate course");
            System.out.println("5. Activate course");
            System.out.println("0. Back");
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        printCourses();
                        break;
                    case 3:
                        System.out.println(courseService.findCourseById(readInt("Course ID: ")));
                        break;
                    case 4:
                        courseService.deactivateCourse(readInt("Course ID: "));
                        System.out.println("Course deactivated.");
                        break;
                    case 5:
                        courseService.activateCourse(readInt("Course ID: "));
                        System.out.println("Course activated.");
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Option not found.");
                }
            } catch (EntityNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void addCourse() throws InvalidInputException {
        String name = readText("Course name: ");
        String description = readText("Description: ");
        int duration = readInt("Duration in weeks: ");
        Course course = courseService.addCourse(name, description, duration);
        System.out.println("Course added with ID " + course.getId() + ".");
    }

    private void printCourses() {
        if (courseService.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courseService.listCourses()) {
            System.out.println(course);
        }
    }

    private void enrollmentMenu() throws InvalidInputException {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll student in course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. View all enrollments");
            System.out.println("4. Mark enrollment completed");
            System.out.println("5. Cancel enrollment");
            System.out.println("0. Back");
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1:
                        enrollStudent();
                        break;
                    case 2:
                        printStudentEnrollments(readInt("Student ID: "));
                        break;
                    case 3:
                        printEnrollments(enrollmentService.listEnrollments());
                        break;
                    case 4:
                        enrollmentService.markCompleted(readInt("Enrollment ID: "));
                        System.out.println("Enrollment marked completed.");
                        break;
                    case 5:
                        enrollmentService.cancelEnrollment(readInt("Enrollment ID: "));
                        System.out.println("Enrollment cancelled.");
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Option not found.");
                }
            } catch (EntityNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void enrollStudent() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created with ID " + enrollment.getId() + ".");
    }

    private void printStudentEnrollments(int studentId) throws EntityNotFoundException {
        printEnrollments(enrollmentService.listEnrollmentsForStudent(studentId));
    }

    private void printEnrollments(List<Enrollment> enrollments) {
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollment);
        }
    }

    private int readInt(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Please enter a whole number.");
        }
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

