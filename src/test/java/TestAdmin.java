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
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        assertTrue(this.admin.classExists("ECS189E", 2017));
    }

    @Test
    public void testCreateClass2() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        assertFalse(this.admin.classExists("ECS189E", 2016));
    }

    @Test
    public void testCreateClass3() {
        this.admin.createClass("ECS189E", 2015, "Devanbu", 80);
        assertFalse(this.admin.classExists("ECS189E", 2015));
    }

    @Test
    public void testCorrectInstructorYear() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        assertNull(this.admin.getClassInstructor("ECS189E", 2016));
    }

    @Test
    public void testCorrectInstructorName() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        assertTrue(this.admin.getClassInstructor("ECS189E", 2017).equals("Devanbu"));
    }

    @Test
    public void testUniqueClassInstructor() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.admin.createClass("ECS189E", 2017, "Matloff", 80);
        assertTrue(this.admin.getClassInstructor("ECS189E", 2017).equals("Devanbu"));
    }

    @Test
    public void testNullInstructor() {
        this.admin.createClass("ECS189E", 2017, null, 80);
        assertNull(this.admin.getClassInstructor("ECS189E", 2017));
    }

    @Test
    public void testCorrectCapacityChange() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.admin.changeCapacity("ECS189E", 2017, 110);
        assertTrue(this.admin.getClassCapacity("Test", 2017) == 110);
    }

    @Test
    public void testSameCapacityChange() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        int capacity_before = this.admin.getClassCapacity("Test", 2017);
        this.admin.changeCapacity("ECS189E", 2017, 80);
        int capacity_after = this.admin.getClassCapacity("Test", 2017);
        assertFalse(capacity_before == capacity_after);
    }

    @Test
    public void testValidCapacityChange() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.admin.changeCapacity("ECS189E", 2017, 60);
        assertTrue(this.admin.getClassCapacity("Test", 2017) >= 80);
    }

    @Test
    public void testTwoCoursesPerYearForEachProfessor() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.admin.createClass("ECS152A", 2017, "Devanbu", 120);
        this.admin.createClass("ECS145", 2017, "Devanbu", 75);
        String course1 = this.admin.getClassInstructor("ECS189E", 2017);
        String course2 = this.admin.getClassInstructor("ECS152A", 2017);
        String course3 = this.admin.getClassInstructor("ECS145", 2017);

        if (course1.equals(course2))
            assertFalse(course2.equals(course3));
    }

    @Test
    public void testCreateClassWithNegativeCapacity() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", -1);
        assertFalse(this.admin.getClassCapacity("Test", 2017) < 0);
    }

    @Test
    public void testCannotChangeToNegativeCapacity() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.admin.changeCapacity("ECS189E", 2017, -5);
        assertTrue(this.admin.getClassCapacity("ECS189E", 2017) > 0);
    }
}
