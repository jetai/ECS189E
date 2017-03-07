import api.IInstructor;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jeffreytai on 3/1/17.
 */

public class TestInstructor {
    private IInstructor instructor;

    @Before
    public void setup() {
        Admin admin = new Admin();
        this.instructor = new Instructor();
        this.instructor.addHomework("Instructor", "Test", 2017,"Homework1", "Junit testing");
    }

    @Test
    public void testCreateHomework() {
        this.instructor.addHomework("Instructor", "Test", 2017, "Homework1", "Junit testing");
        assertTrue(this.instructor.homeworkExists("Test", 2017, "Homework1"));
    }

    @Test
    public void testCreateHomework2() {
        this.instructor.addHomework("Instructor", "Test", 2010, "Homework1", "Junit testing");
        assertFalse(this.instructor.homeworkExists("Test", 2017, "Homework1"));
    }

    @Test
    public void testDuplicateHomework() {
        this.instructor.addHomework("Instructor", "Test", 2017, "HW 1", "Debugging");
        boolean hw1_exists = this.instructor.homeworkExists("Instructor", 2017, "HW 1");
        this.instructor.addHomework("Instructor", "Test", 2017, "HW 1", "Debugging");
        boolean hw1_duplicate_exists = this.instructor.homeworkExists("Instructor", 2017, "HW 1");
        assertFalse(hw1_exists && hw1_duplicate_exists);
    }

    @Test
    public void testCorrectAssignedGrade() {
        this.instructor.addHomework("Instructor", "Test", 2017, "HW 1", "Debugging");
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW 1", "Jeffrey Tai", 95);
        Integer grade = this.instructor.getGrade("Test", 2017, "HW 1", "Jeffrey Tai");
        if (grade == null)
            assertNull(grade);
        else
            assertTrue(grade.equals(95));

    }

    @Test
    public void testGradeAssignedToSubmittedHomework() {
        this.instructor.addHomework("Instructor", "Test", 2017, null, null);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW 1", "Jeffrey Tai", 98);
        Integer grade = this.instructor.getGrade("Test", 2017, "Hw 1", "Jeffrey Tai");
        assertNull(grade);
    }

    @Test
    public void testGradeAssignedToCorrectHomework() {
        this.instructor.addHomework("Instructor", "Test", 2015, "HW 1", "Debugging");
        this.instructor.addHomework("Instructor2", "Test", 2017, "HW 1", "Debugging");
        this.instructor.assignGrade("Instructor2", "Test", 2017, "HW 1", "Jeffrey Tai", 100);
        Integer grade = this.instructor.getGrade("Test", 2015, "HW 1", "Jeffrey Tai");
        assertNull(grade);
    }
}
