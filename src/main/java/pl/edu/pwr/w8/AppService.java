package pl.edu.pwr.w8;

import java.util.List;

public class AppService {
  private TopicList listInstance = new TopicList();
  private final PersonArr people = new PersonArr(
      new Person("Vitya", "male"),
      new Person("Nadya", "female"),
      new Person("Anya", "female"),
      new Person("Artem", "male")
  );
  
  protected void updateOwners() {
    for (Person smb : people.getArr())
      listInstance.assignToOwner(smb);
  }
  protected List<Topic> getList() { return listInstance.getTopics(); }
  protected PersonArr getPeople() { return people; }
}