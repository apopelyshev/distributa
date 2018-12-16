package pl.edu.pwr.w8;

public class Topic {
  private int ind;
  private Person belongsTo = null;
  private String content = "";
  
  public Topic() {}
  public Topic(String arg) {
    if (arg!=null) {
      String[] parts = arg.split(";");
      ind = Integer.valueOf(parts[0]);
      content = parts[1];
    }
  }
  public Topic(String arg, Person owner) {
    this(arg);
    if (owner!=null)
      belongsTo = owner;
  }
  
  public boolean setOwner(Person arg) {
    if (arg==null)
      return false;
    belongsTo = arg;
    return true;
  }
  public Person getOwner() { return belongsTo; }
  public String getContent() { return content; }
  public int getInd() { return ind; }
}
