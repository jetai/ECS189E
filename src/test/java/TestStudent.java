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
 * Created by jeffreytai on 3/8/17.
 */
public class TestStudent {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    @Test
    public void testStudentRegistered() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 2);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        assertTrue(this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017));
    }

    @Test
    public void testStudentRegistered2() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 2);
        this.student.registerForClass("Jeffrey Tai", "ECS145", 2017);
        assertFalse(this.student.isRegisteredFor("Jeffrey Tai", "ECS189E", 2017));
    }

    @Test
    public void testNoCapacityAvailable() {
        this.admin.createClass("ECS189E", 2017, "Devanbu", 2);
        this.student.registerForClass("Jeffrey Tai", "ECS189E", 2017);
        this.student.registerForClass("Bob Ross", "ECS189E", 2017);
        this.student.registerForClass("Neil De Grasse Tyson", "ECS189E", 2017);
        assertTrue(this.admin.getClassCapacity("ECS189E", 2017) <= 2);

    }


}
