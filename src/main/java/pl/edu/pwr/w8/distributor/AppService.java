package pl.edu.pwr.w8.distributor;

import java.util.List;

public class AppService {
  private TopicList listInstance = new TopicList();
  private final Person[] people = new Person[] {
      new Person("Vitya", "male"),
      new Person("Nadya", "female"),
      new Person("Anya", "female"),
      new Person("Artem", "male"),
  };
  
  public void updateOwners() {
    for (Person smb : people)
      listInstance.assignToOwner(smb);
  }
  public List<Topic> getList() { return listInstance.getTopics(); }
  public Person[] getPeople() { return people; }
}