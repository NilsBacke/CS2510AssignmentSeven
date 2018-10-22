// represents an instructor in the school
public class Instructor extends ASchoolElement {
  IList<Course> courses;
  
  // creates a new instructor object
  Instructor(String name) {
    super(name);
  }
}
