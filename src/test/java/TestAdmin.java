import api.IAdmin;
import api.core.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jeffreytai on 2/28/17.
 */

public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testCreateClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testCreateClass2() {
        this.admin.createClass("Test", 2015, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    @Test
    public void testCorrectInstructorYear() {
        this.admin.createClass("Test", 2017, "Instructor", 30);
        assertNull(this.admin.getClassInstructor("Instructor", 2016));
    }

    @Test
    public void testCorrectInstructorName() {
        this.admin.createClass("Test", 2017, "Instructor", 30);
        assertTrue(this.admin.getClassInstructor("Test", 2017).equals("Instructor"));
    }

    @Test
    public void testUniqueClassInstructor() {
        this.admin.createClass("Test", 2017, "Instructor", 30);
        this.admin.createClass("Test", 2017, "Instructor2", 30);
        assertFalse(this.admin.getClassInstructor("Test", 2017).equals("Instructor2"));
    }

    @Test
    public void testNullInstructor() {
        this.admin.createClass("Test", 2017, null, 30);
        assertNull(this.admin.getClassInstructor("Test", 2017));
    }

    @Test
    public void testCapacityChange() {
        this.admin.createClass("Test", 2017, "Instructor", 30);
        this.admin.changeCapacity("Test", 2017, 50);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 50);
    }

    @Test
    public void testOneProfessorPerCoursePerYear() {
        this.admin.createClass("Class1", 2017, "Instructor", 30);
        this.admin.createClass("Class2", 2017, "Instructor", 50);
        String temp1 = this.admin.getClassInstructor("Class1", 2017);
        String temp2 = this.admin.getClassInstructor("Class2", 2017);
        assertFalse(temp1.equals(temp2));
    }

    @Test
    public void testNoNegativeCapacityValue() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.getClassCapacity("Test", 2017) < 0);
    }
}
