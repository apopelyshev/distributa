package pl.edu.pwr.w8.distributor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TopicList {
  private final String pathToFile = Util.getProps().getProperty("pathTo.dbTextFile");
  private final List<Topic> topics = new ArrayList<Topic>();
  
  public TopicList() {
    try {
      BufferedReader bfReader = new BufferedReader(Util.path2Stream(pathToFile));
      String line;
      while ((line = bfReader.readLine())!=null)
        topics.add(new Topic(line));
      bfReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public boolean assignToOwner(Person smb) {
    boolean res = false;
    for (Topic el : topics) {
      if (smb.getOwnTopics().contains(el.getInd())) {
        el.setOwner(smb);
        res = true;
      }
    }
    return res;
  }
  public List<Topic> getTopics() { return topics; }
}