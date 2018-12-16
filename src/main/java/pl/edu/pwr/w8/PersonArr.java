package pl.edu.pwr.w8;

import java.util.stream.IntStream;

public class PersonArr {
  private final Person[] array;
  
  public PersonArr(Person... args) {
    array = (args.length>0) ? args : new Person[0];
  }
  
  // Here I'm basically showing off my functional programming skills
  // while searching for a person in array
  protected int getIndAtMatch(Person personToSearch) {
    if (array.length==0) return -1;
    return IntStream.range(0, array.length)
        .filter(i -> array[i].equals(personToSearch))
        .sum();
  }
  protected Person[] getArr() { return array; }
  public String toString() {
    String res = "";
    for (Person el : array)
      res+=el;
    return res;
  }
}