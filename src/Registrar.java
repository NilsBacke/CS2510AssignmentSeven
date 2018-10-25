import tester.Tester;

// represents a generic list of type T
interface IList<T> {
  // returns a new list with the additional element T
  IList<T> addToFront(T t);

  // returns true if this list contains the given T
  boolean contains(T t);

  // returns a new list filtered by a given predicate and student
  IList<T> filter(IPred<T> pred);

  // returns the length of this list
  int length();
}

// represents an empty list of type T
class MtList<T> implements IList<T> {
  // creates a new empty list object
  MtList() {
  }

  /* TEMPLATE:
   * Methods:
   * ... this.addToFront(T) ...     -- IList<T>
   * ... this.contains(T) ...       -- boolean
   * ... this.filter(IPred<T>) ...  -- IList<T>
   * ... this.length() ...          -- int
   */

  // creates a new list with only one element, the given T
  public IList<T> addToFront(T t) {
    return new ConsList<T>(t, this);
  }

  // return false because there are no elements in an empty list
  public boolean contains(T t) {
    return false;
  }

  // no elements to filter
  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  // length 0 of an empty list
  public int length() {
    return 0;
  }
}

// represents one element and the rest of a generic list of type T
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  // creates a new ConsList object with a given first and rest
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  /* TEMPLATE:
   * Fields:
   * ... this.first ... -- T
   * ... this.rest ...  -- IList<T>
   * Methods:
   * ... this.addToFront(T) ...     -- IList<T>
   * ... this.contains(T) ...       -- boolean
   * ... this.filter(IPred<T>) ...  -- IList<T>
   * ... this.length() ...          -- int
   * Methods of Fields:
   * ... this.rest.addToFront(T) ...     -- IList<T>
   * ... this.rest.contains(T) ...       -- boolean
   * ... this.rest.filter(IPred<T>) ...  -- IList<T>
   * ... this.rest.length() ...          -- int
   */

  // appends the given element to the front of the list
  public IList<T> addToFront(T t) {
    return new ConsList<T>(t, this);
  }

  // returns true if the given T is in this list
  public boolean contains(T t) {
    return first.equals(t) || rest.contains(t);
  }

  // filter this list by the given predicate and student
  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(first)) {
      return new ConsList<T>(first, rest.filter(pred));
    }
    else {
      return rest.filter(pred);
    }
  }

  // add one to the current length and recurse across the rest of the list
  public int length() {
    return 1 + rest.length();
  }
}

// represents a predicate interface with an apply method that returns a boolean
interface IPred<T> {
  boolean apply(T t);
}

// represents a Course in the school
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  // creates a new Course object with an empty list of students
  // EFFECT: adds this course to the prof's list of courses
  Course(String name, Instructor prof) {
    this.name = name;
    prof.addCourse(this);
    this.prof = prof;
    this.students = new MtList<Student>();
  }

  /* TEMPLATE:
   * Fields:
   * ... this.name ...      -- String
   * ... this.prof ...      -- Instructor
   * ... this.students ...  -- IList<Student>
   * Methods:
   * ... this.addStudent(Student) ...       -- void
   * ... this.containsStudent(Student) ...  -- boolean
   * Methods of Fields:
   * ... this.prof.addCourse(Course) ...           -- void
   * ... this.prof.dejavu(Student) ...             -- boolean
   * ... this.students.addToFront(Student) ...     -- IList<Student>
   * ... this.students.contains(Student) ...       -- boolean
   * ... this.students.filter(IPred<Student>) ...  -- IList<Student>
   * ... this.students.length() ...                -- int
   */

  // EFFECT: adds the given student to the list of students in this course
  void addStudent(Student s) {
    students = students.addToFront(s);
  }

  // returns true if the given student can be found in this course's students list
  boolean containsStudent(Student s) {
    return students.contains(s);
  }
}

// represents an instructor in the school
class Instructor {
  String name;
  IList<Course> courses;

  // creates a new instructor object
  Instructor(String name) {
    this.name = name;
    courses = new MtList<Course>();
  }

  /* TEMPLATE:
   * Fields:
   * ... this.name ...      -- String
   * ... this.courses ...   -- IList<Courses>
   * Methods:
   * ... this.addCourse(Course) ... -- void
   * ... this.dejavu(Student) ...   -- boolean
   * Methods of Fields:
   * ... this.courses.addToFront(Course) ...     -- IList<Course>
   * ... this.courses.contains(Course) ...       -- boolean
   * ... this.courses.filter(IPred<Course>) ...  -- IList<Course>
   * ... this.courses.length() ...               -- int
   */

  // EFFECT: add a course to the intructor's courses
  void addCourse(Course c) {
    courses = courses.addToFront(c);
  }

  // returns true if this student is in more than one of this instructor's courses
  boolean dejavu(Student s) {
    return courses.filter(new CoursesWithStudent(s)).length() > 1;
  }
}

// represents a Student object
class Student {
  String name;
  int id;
  IList<Course> courses;

  // creates a new Student object
  Student(String name, int id) {
    this.name = name;
    this.id = id;
    courses = new MtList<Course>();
  }

  /* TEMPLATE:
   * Fields:
   * ... this.name ...      -- String
   * ... this.id ...        -- int
   * ... this.courses ...   -- IList<Courses>
   * Methods:
   * ... this.enroll(Course) ...        -- void
   * ... this.classmates(Student) ...   -- boolean
   * Methods of Fields:
   * ... this.courses.addToFront(Course) ...     -- IList<Course>
   * ... this.courses.contains(Course) ...       -- boolean
   * ... this.courses.filter(IPred<Course>) ...  -- IList<Course>
   * ... this.courses.length() ...               -- int
   */

  // EFFECT: adds this student to the given course and
  // adds the given course to this student
  void enroll(Course c) {
    c.addStudent(this);
    courses = courses.addToFront(c);
  }

  // returns true if this student shares a class with the given student
  boolean classmates(Student s) {
    return courses.filter(new CoursesWithStudent(s)).length() > 0;
  }
}

// represents a function object for filtering a list of courses
class CoursesWithStudent implements IPred<Course> {
  Student student;

  CoursesWithStudent(Student student) {
    this.student = student;
  }

  /* TEMPLATE:
   * Fields:
   * ... this.student ...   -- Student
   * Methods:
   * ... this.apply(Course) ...   -- boolean
   */

  // returns true if student is enrolled in the given course
  public boolean apply(Course c) {
    return c.containsStudent(student);
  }
}

// example Integer predicate for testing filter
class TwoOrGreater implements IPred<Integer> {

  /* TEMPLATE:
   * Methods:
   * ... this.apply(Course) ...   -- boolean
   */

  public boolean apply(Integer i) {
    return i >= 2;
  }
}

// class for data examples and unit tests
class ExamplesRegistrar {
  // example students
  Student john;
  Student paul;
  Student george;
  Student ringo;
  Student pete;

  // example instructors
  Instructor david;
  Instructor ben;

  // example courses
  Course logic;
  Course hci;
  Course fundies;
  Course ood;

  // EFFECT: sets up initial test conditions by re-initializing the example data
  void initTestConditions() {
    initStudents();
    initInstructors();

    logic = new Course("logic", david);
    hci = new Course("hci", david);
    fundies = new Course("fundies", ben);
    ood = new Course("ood", ben);
  }

  // EFFECT: re-initialize only example students
  void initStudents() {
    john = new Student("john", 1);
    paul = new Student("paul", 2);
    george = new Student("george", 3);
    ringo = new Student("ringo", 4);
    pete = new Student("pete", 5);
  }

  // EFFECT: re-initialize only example instructors
  void initInstructors() {
    david = new Instructor("david");
    ben = new Instructor("ben");
  }

  // test List methods
  boolean testListMethods(Tester t) {
    IList<Integer> mtex = new MtList<Integer>();
    IList<Integer> cex1 = new ConsList<Integer>(1, mtex);
    IList<Integer> cex2 = new ConsList<Integer>(2, cex1);
    IList<Integer> cex3 = new ConsList<Integer>(3, cex2);

    boolean addToFrontTest = t.checkExpect(mtex.addToFront(1), cex1)
        && t.checkExpect(cex1.addToFront(2), cex2)
        && t.checkExpect(cex2.addToFront(3), cex3);

    boolean containsTest = t.checkExpect(mtex.contains(1), false)
        && t.checkExpect(cex1.contains(2), false)
        && t.checkExpect(cex2.contains(3), false)
        && t.checkExpect(cex1.contains(1), true)
        && t.checkExpect(cex2.contains(1), true)
        && t.checkExpect(cex3.contains(2), true);

    boolean lengthTest = t.checkExpect(mtex.length(), 0)
        && t.checkExpect(cex1.length(), 1)
        && t.checkExpect(cex2.length(), 2)
        && t.checkExpect(cex3.length(), 3);

    boolean filterTest = t.checkExpect(mtex.filter(new TwoOrGreater()), mtex)
        && t.checkExpect(cex1.filter(new TwoOrGreater()), mtex)
        && t.checkExpect(cex2.filter(new TwoOrGreater()),
            new ConsList<Integer>(2, mtex))
        && t.checkExpect(cex3.filter(new TwoOrGreater()),
            new ConsList<Integer>(3,
              new ConsList<Integer>(2, mtex)));

    return addToFrontTest && containsTest && lengthTest && filterTest;
  }

  // test the methods (and constructor) of the Course class
  boolean testCourseMethods(Tester t) {
    // test the effect on profs when creating a course
    initInstructors();
    boolean preCourseProfs = t.checkExpect(david.courses, new MtList<Course>())
        && t.checkExpect(ben.courses, new MtList<Course>());

    initTestConditions();
    boolean postCourseProfs = t.checkExpect(david.courses,
        new ConsList<Course>(hci, new ConsList<Course>(logic, new MtList<Course>())))
        && t.checkExpect(ben.courses,
          new ConsList<Course>(ood, new ConsList<Course>(fundies, new MtList<Course>())));

    // test addStudent
    boolean noStudents = t.checkExpect(hci.students, new MtList<Student>());

    hci.addStudent(ringo);
    boolean oneStudent = t.checkExpect(hci.students,
        new ConsList<Student>(ringo, new MtList<Student>()));

    hci.addStudent(paul);
    boolean twoStudents = t.checkExpect(hci.students,
        new ConsList<Student>(paul,
          new ConsList<Student>(ringo, new MtList<Student>())));

    // test containsStudent
    boolean containsStudentTest = t.checkExpect(hci.containsStudent(ringo), true)
        && t.checkExpect(hci.containsStudent(paul), true)
        && t.checkExpect(hci.containsStudent(george), false)
        && t.checkExpect(logic.containsStudent(george), false);

    return preCourseProfs && postCourseProfs
        && noStudents && oneStudent && twoStudents
        && containsStudentTest;
  }

  // test the mothods of the Student class
  boolean testStudentMethods(Tester t) {
    initTestConditions();

    // test enroll
    boolean noneEnrolledTest = t.checkExpect(hci.students.contains(paul), false)
        && t.checkExpect(hci.students.contains(john), false)
        && t.checkExpect(paul.courses.contains(hci), false)
        && t.checkExpect(john.courses.contains(hci), false);

    paul.enroll(hci);
    boolean oneEnrolledTest = t.checkExpect(hci.students.contains(paul), true)
        && t.checkExpect(hci.students.contains(john), false)
        && t.checkExpect(paul.courses.contains(hci), true)
        && t.checkExpect(john.courses.contains(hci), false);

    john.enroll(hci);
    boolean twoEnrolledTest = t.checkExpect(hci.students.contains(paul), true)
        && t.checkExpect(hci.students.contains(john), true)
        && t.checkExpect(paul.courses.contains(hci), true)
        && t.checkExpect(john.courses.contains(hci), true);

    // test classmates
    boolean classmatesTest = t.checkExpect(paul.classmates(john), true)
        && t.checkExpect(john.classmates(paul), true)
        && t.checkExpect(john.classmates(john), true)
        && t.checkExpect(john.classmates(ringo), false)
        && t.checkExpect(paul.classmates(ringo), false)
        && t.checkExpect(ringo.classmates(paul), false)
        && t.checkExpect(ringo.classmates(john), false)
        && t.checkExpect(ringo.classmates(ringo), false);

    return noneEnrolledTest && oneEnrolledTest && twoEnrolledTest;
  }

  // test the methods of the Instructor class
  boolean testInstructorMethods(Tester t) {
    // test addCourse
    initTestConditions();
    boolean noCoursesAdded = t.checkExpect(david.courses,
        new ConsList<Course>(hci,
          new ConsList<Course>(logic, new MtList<Course>())));

    david.addCourse(fundies);
    boolean oneCourseAdded = t.checkExpect(david.courses,
        new ConsList<Course>(fundies,
          new ConsList<Course>(hci,
            new ConsList<Course>(logic, new MtList<Course>()))));

    david.addCourse(ood);
    boolean twoCoursesAdded = t.checkExpect(david.courses,
        new ConsList<Course>(ood,
          new ConsList<Course>(fundies,
            new ConsList<Course>(hci,
              new ConsList<Course>(logic, new MtList<Course>())))));

    // test dejavu
    initTestConditions();

    paul.enroll(hci);
    ringo.enroll(hci);
    ringo.enroll(logic);
    pete.enroll(logic);

    boolean dejavuTest = t.checkExpect(david.dejavu(paul), false)
        && t.checkExpect(david.dejavu(ringo), true)
        && t.checkExpect(david.dejavu(pete), false)
        && t.checkExpect(david.dejavu(john), false);

    return noCoursesAdded && oneCourseAdded && twoCoursesAdded && dejavuTest;
  }
}
