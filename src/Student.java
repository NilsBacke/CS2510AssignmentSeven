// represents a Student object
public class Student extends ASchoolElement {
  int id;
  IList<Course> courses;

  // creates a new Student object
  Student(String name, int id) {
    super(name);
    this.id = id;
  }

  // adds this student to the given course and
  // adds the given course to this student
  void enroll(Course c) {
    c.addStudent(this);
    this.courses = courses.addToFront(c);
  }

  // returns true if this student shares a class with the given student
  boolean classmates(Student s) {
    return courses.filterByStudent(new ClassmatesPred(), s).length() > 0;
  }

}

// represents a function object for filtering a list of courses
class ClassmatesPred implements IPred<Course, Student> {

  // returns true if the given student is enrolled in the given course
  @Override
  public boolean apply(Course c, Student s) {
    return c.containsStudent(s);
  }

}
