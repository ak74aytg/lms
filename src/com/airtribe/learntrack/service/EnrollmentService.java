package com.airtribe.learntrack.service;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;

public class EnrollmentService {
    private final List<Enrollment> enrollments = new ArrayList<Enrollment>();
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId)
            throws EntityNotFoundException, InvalidInputException {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);
        if (!student.isActive()) {
            throw new InvalidInputException("The student is inactive.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("The course is inactive.");
        }
        if (hasActiveEnrollment(studentId, courseId)) {
            throw new InvalidInputException("The student is already enrolled in this course.");
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(), studentId, courseId);
        enrollments.add(enrollment);
        return enrollment;
    }

    public List<Enrollment> listEnrollmentsForStudent(int studentId) throws EntityNotFoundException {
        studentService.findStudentById(studentId);
        List<Enrollment> result = new ArrayList<Enrollment>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                result.add(enrollment);
            }
        }
        return result;
    }

    public void markCompleted(int enrollmentId) throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        ensureActive(enrollment);
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
    }

    public void cancelEnrollment(int enrollmentId) throws EntityNotFoundException, InvalidInputException {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        ensureActive(enrollment);
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
    }

    public List<Enrollment> listEnrollments() {
        return new ArrayList<Enrollment>(enrollments);
    }

    private Enrollment findEnrollmentById(int id) throws EntityNotFoundException {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        throw new EntityNotFoundException("Enrollment with ID " + id + " was not found.");
    }

    private boolean hasActiveEnrollment(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId
                    && enrollment.getCourseId() == courseId
                    && enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }

    private void ensureActive(Enrollment enrollment) throws InvalidInputException {
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("Only an active enrollment can be changed.");
        }
    }
}

