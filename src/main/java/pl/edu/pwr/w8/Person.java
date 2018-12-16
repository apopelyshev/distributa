package pl.edu.pwr.w8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Person {
  private String name;
  private String gender;
  private List<Integer> hasTopics;
  private boolean isViewing = false;
  
  public Person(String name, String gender) {
    this.name = name;
    this.gender = gender;
    this.hasTopics = getIndList(getCheckVariable().orElse(""));
  }
  
  public boolean checkReceived() {
    return getCheckVariable().isPresent();
  }
  public Optional<String> getCheckVariable() {
    return Optional.ofNullable(System.getenv(name.toUpperCase()+"_HAS_TOPICS"));
  }
  private List<Integer> getIndList(String arg) {
    List<Integer> res = new ArrayList<Integer>();
    if (!arg.isEmpty()) {
      String[] temp = arg.split(";");
      for (String el : temp)
        res.add(Integer.valueOf(el));
    }
    return res;
  }
  
  public boolean checkActive() { return isViewing; }
  public boolean equals(Person personToMatch) { return name==personToMatch.name; }
  public List<Integer> getOwnTopics() { return hasTopics; }
  public String getName() { return name; }
  public String getGender() { return gender; }
  public String getImagePath() { return Util.getProps().getProperty("pathTo.images")+name+".png"; }
  public void setActive(boolean arg) { isViewing = arg; }
  public String toString() {
    return "Person{"+"name="+name+", gender="+gender+", topics="+hasTopics.toString()+"}";
  }
}