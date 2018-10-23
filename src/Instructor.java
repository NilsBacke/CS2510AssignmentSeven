// represents an instructor in the school
public class Instructor extends ASchoolElement {
  IList<Course> courses;
  
  // creates a new instructor object
  Instructor(String name) {
    super(name);
  }
  
  // returns true if this student is in more than one of this instructor's courses
  boolean dejavu(Student c) {
    return courses.filterByStudent(new ClassmatesPred(), c).length() > 1;
  }
}
