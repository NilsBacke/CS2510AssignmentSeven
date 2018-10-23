// represents a Course in the school
public class Course extends ASchoolElement {
  Instructor prof;
  IList<Student> students;

  // creates a new Course object
  Course(String name, Instructor prof, IList<Student> students) {
    super(name);
    this.prof = prof;
    this.students = students;
  }

  // EFFECT: adds the given student to the list of students in this course
  void addStudent(Student s) {
    this.students.addToFront(s);
  }

  // returns true if the given student can be found in this course's students list
  boolean containsStudent(Student s) {
    return this.students.contains(s);
  }
}
