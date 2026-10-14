package com.airtribe.learntrack.service;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

public class StudentService {
    private final List<Student> students = new ArrayList<Student>();

    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        InputValidator.requireText(firstName, "First name");
        InputValidator.requireText(lastName, "Last name");
        InputValidator.requireText(email, "Email");
        InputValidator.requireText(batch, "Batch");

        Student student = new Student(IdGenerator.getNextStudentId(), firstName.trim(), lastName.trim(),
                email.trim(), batch.trim());
        students.add(student);
        return student;
    }

    public void removeStudent(int id) throws EntityNotFoundException {
        deactivateStudent(id);
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(false);
    }

    public void activateStudent(int id) throws EntityNotFoundException {
        Student student = findStudentById(id);
        student.setActive(true);
    }

    public void updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {
        Student student = findStudentById(id);
        InputValidator.requireText(firstName, "First name");
        InputValidator.requireText(lastName, "Last name");
        InputValidator.requireText(email, "Email");
        InputValidator.requireText(batch, "Batch");

        student.setFirstName(firstName.trim());
        student.setLastName(lastName.trim());
        student.setEmail(email.trim());
        student.setBatch(batch.trim());
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student with ID " + id + " was not found.");
    }

    public List<Student> listStudents() {
        return new ArrayList<Student>(students);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }
}

