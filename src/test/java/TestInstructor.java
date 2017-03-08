import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jeffreytai on 3/1/17.
 */

public class TestInstructor {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test
    public void testCreateHomework() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework1", "Github Querier");
        assertTrue(this.instructor.homeworkExists("ECS189E", 2017, "Homework1"));
    }

    @Test
    public void testCreateHomework2() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework1", "Github Querier");
        assertFalse(this.instructor.homeworkExists("ECS189E", 2015, "Homework1"));
    }

    @Test
    public void testDuplicateHomework() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        boolean hw1_exists = this.instructor.homeworkExists("ECS189E", 2017, "Homework 1");
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        boolean hw1_duplicate_exists = this.instructor.homeworkExists("ECS189E", 2017, "Homework 1");
        assertFalse(hw1_exists && hw1_duplicate_exists);
    }

    @Test
    public void testGradeByAssignedInstructor() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        this.instructor.assignGrade("Matloff", "ECS189E", 2017, "Homework 1", "Jeffrey Tai", 50);
        Integer grade = this.instructor.getGrade("ECS189E", 2017, "Homework 1", "Jeffrey Tai");
        if (grade == null)
            assertNull(grade);
    }

    @Test
    public void testAssignGradeToStudentSubmittedHomework() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        this.instructor.assignGrade("Devanbu", "ECS189E", 2017, "Homework 1", "Jeffrey Tai", 100);
        Integer grade = this.instructor.getGrade("ECS189E", 2017, "Homework 1", "Jeffrey Tai");
        assertTrue(grade.equals(100));
    }

    @Test
    public void testAssignGradeToStudentSubmittedHomework2() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        this.instructor.assignGrade("Devanbu", "ECS189E", 2017, "Homework 1", "Bob Ross", 70);
        Integer grade = this.instructor.getGrade("ECS189E", 2017, "Homework 1", "Jeffrey Tai");
        assertNull(grade);
    }

    @Test
    public void testAssignGradeToHomeworkThatExists() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        this.instructor.assignGrade("Matloff", "ECS145", 2017, "Problem 1", "Jeffrey Tai", 88);
        Integer grade = this.instructor.getGrade("ECS189E", 2017, "Homework 1", "Jeffrey Tai");
        if (grade == null)
            assertNull(grade);
    }

    @Test
    public void testAssignGradeToNullHomework() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        this.student.submitHomework("Jeffrey Tai", null, null, "ECS189E", 2017);
        this.instructor.assignGrade("Devanbu", "ECS189E", 2017, "Homework 1", "Jeffrey Tai", 95);
        Integer grade = this.instructor.getGrade("Test", 2017, "Homework 1", "Jeffrey Tai");
        assertNull(grade);
    }

    @Test
    public void testGradeAssignedToCorrectYearHomework() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        this.instructor.assignGrade("Devanbu", "ECS189E", 2017, "Homework 1", "Jeffrey Tai", 100);
        Integer grade = this.instructor.getGrade("Test", 2015, "Homework 1", "Jeffrey Tai");
        assertNull(grade);
    }

    @Test
    public void testGradeAssignedToCorrectYearHomework2() {
        this.instructor.addHomework("Devanbu", "ECS189E", 2017, "Homework 1", "Github Querier");
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        this.instructor.assignGrade("Devanbu", "ECS189E", 2015, "Homework 1", "Jeffrey Tai", 100);
        Integer grade = this.instructor.getGrade("Test", 2017, "Homework 1", "Jeffrey Tai");
        assertNull(grade);
    }
}
