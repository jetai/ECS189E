import api.IAdmin;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jeffreytai on 3/8/17.
 */
public class TestStudent {
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.student = new Student();
    }

    @Test
    public void testStudentRegistered() {
        /**
         * Test case if user registers a student for a class
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 2);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        assertTrue(this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017));
    }

    @Test
    public void testStudentRegistered2() {
        /**
         * Test case if user registers a student for the wrong class
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 2);
        this.student.registerForClass("Jeffrey Tai", "ECS145", 2017);
        assertFalse(this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017));
    }

    @Test
    public void testNoCapacityAvailable() {
        /**
         * Test case if user tries to register a student when the class is at max capacity
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 2);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.registerForClass("Bob Ross", "ECS189E", 2017);
        this.student.registerForClass("Neil De Grasse Tyson", "ECS189E", 2017);
        assertTrue(this.admin.getClassCapacity("ECS189E", 2017) <= 2);
    }

    @Test
    public void testDropClass() {
        /**
         * Test case if user drops a student from a class, then he is dropped
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.dropClass("Jeffrey Tai", "ECS189E", 2017);
        boolean registered = this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017);
        assertTrue(!registered);
    }

    @Test
    public void testDropClass2() {
        /**
         * Test case for to ensure a student remains registered when another is dropped
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.dropClass("Bob Ross", "ECS189E", 2017);
        assertTrue(this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017));
    }

    @Test
    public void testDropClass3() {
        /**
         * Test case if user tries to drop a class he is not registered for
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.dropClass("Jeffrey Tai", "ECS170", 2017);
        assertTrue(this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017));
    }

    @Test
    public void testSubmitHomework() {
        /**
         * Test case if user submits the correct homework
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        assertTrue(this.student.hasSubmitted("Jeffrey Tai", "Homework 1", "ECS189E", 2017));
    }

    @Test
    public void testSubmitHomework2() {
        /**
         * Test case if user submits the wrong homework
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.submitHomework("Jeffrey Tai", "Homework 2", "Solution", "ECS189E", 2017);
        assertFalse(this.student.hasSubmitted("Jeffrey Tai", "Homework 1", "ECS189E", 2017));
    }

    @Test
    public void testSubmitHomework3() {
        /**
         * Test case if user tries to submit a homework for a class that he is not registered for
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        if (!this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017)) {
            assertFalse(this.student.hasSubmitted("Jeffrey Tai", "Homework 1", "ECS189E", 2017));
        }
    }

    @Test
    public void testSubmitHomework4() {
        /**
         * Test case if user tries to submit a homework for a class that he is registered for
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        if (this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017)) {
            assertTrue(this.student.hasSubmitted("Jeffrey Tai", "Homework 1", "ECS189E", 2017));
        }
    }

    @Test
    public void testSubmitHomework5() {
        /**
         * Test case if user tries to submit a homework for registered in a previous year
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2014);
        assertFalse(this.student.hasSubmitted("Jeffrey Tai", "Homework 1", "ECS189E", 2017));
    }

    @Test
    public void testSubmitHomework6() {
        /**
         * Test case if user tries to submit a homework for registered class in the current year
         */
        this.admin.createClass("ECS189E", 2017, "Devanbu", 80);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.submitHomework("Jeffrey Tai", "Homework 1", "Solution", "ECS189E", 2017);
        assertTrue(this.student.hasSubmitted("Jeffrey Tai", "Homework 1", "ECS189E", 2017));
    }




}
