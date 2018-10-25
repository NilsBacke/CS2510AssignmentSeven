//// represents a generic list of type T
//interface IList<T> {
//  // returns a new list with the additional element T
//  IList<T> addToFront(T t);
//
//  // returns true if this list contains the given T
//  boolean contains(T t);
//
//  // returns a new list filtered by a given predicate and student
//  <U> IList<T> filterByStudent(IPred<T, U> pred, U student);
//
//  // returns the length of this list
//  int length();
//}
//
//// represents an empty list of type T
//class MtList<T> implements IList<T> {
//  // creates a new empty list object
//  MtList() {
//  }
//
//  // creates a new list with only one element, the given T
//  @Override
//  public IList<T> addToFront(T t) {
//    return new ConsList<T>(t, this);
//  }
//
//  // return false because there are no elements in an empty list
//  @Override
//  public boolean contains(T t) {
//    return false;
//  }
//
//  // no elements to filter
//  @Override
//  public <U> IList<T> filterByStudent(IPred<T, U> pred, U student) {
//    return this;
//  }
//
//  // length 0 of an empty list
//  @Override
//  public int length() {
//    return 0;
//  }
//
//}
//
//// represents one element and the rest of a generic list of type T
//class ConsList<T> implements IList<T> {
//  T first;
//  IList<T> rest;
//
//  // creates a new ConsList object with a given first and rest
//  ConsList(T first, IList<T> rest) {
//    this.first = first;
//    this.rest = rest;
//  }
//
//  // appends the given element to the front of the list
//  @Override
//  public IList<T> addToFront(T t) {
//    return new ConsList<T>(t, this);
//  }
//
//  // returns true if the given T is in this list
//  @Override
//  public boolean contains(T t) {
//    return this.first.equals(t) || this.rest.contains(t);
//  }
//
//  // filter this list by the given predicate and student
//  @Override
//  public <U> IList<T> filterByStudent(IPred<T, U> pred, U student) {
//    if (pred.apply(this.first, student)) {
//      return new ConsList<T>(this.first, this.rest.filterByStudent(pred, student));
//    }
//    else {
//      return this.rest.filterByStudent(pred, student);
//    }
//  }
//
//  // add one to the current length and recurse across the rest of the list
//  @Override
//  public int length() {
//    return 1 + this.rest.length();
//  }
//}
//
//// represents a predicate interface with an apply method that returns a boolean
//interface IPred<T, U> {
//  boolean apply(T t, U s);
//}
