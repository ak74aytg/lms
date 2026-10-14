package com.airtribe.learntrack.service;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

public class CourseService {
    private final List<Course> courses = new ArrayList<Course>();

    public Course addCourse(String courseName, String description, int durationInWeeks)
            throws InvalidInputException {
        InputValidator.requireText(courseName, "Course name");
        InputValidator.requireText(description, "Description");
        InputValidator.requirePositive(durationInWeeks, "Duration");

        Course course = new Course(IdGenerator.getNextCourseId(), courseName.trim(), description.trim(),
                durationInWeeks);
        courses.add(course);
        return course;
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        throw new EntityNotFoundException("Course with ID " + id + " was not found.");
    }

    public void deactivateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(false);
    }

    public void activateCourse(int id) throws EntityNotFoundException {
        Course course = findCourseById(id);
        course.setActive(true);
    }

    public List<Course> listCourses() {
        return new ArrayList<Course>(courses);
    }

    public boolean isEmpty() {
        return courses.isEmpty();
    }
}

