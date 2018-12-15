package pl.edu.pwr.w8.distributor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

public class Util {
  public static Properties getProps() {
    Properties res = new Properties();
    try {
      res.load(path2Stream("/my.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return res;
  }
  
  public static InputStreamReader path2Stream(String arg) throws UnsupportedEncodingException {
    InputStream temp = new Util().getClass().getResourceAsStream(arg);
    return new InputStreamReader(temp, "UTF-8");
  }
  
  public static void handleTrackCode(HttpServletRequest req, String arg, Person[] searchArr) {
    for (Person smb : searchArr) {
      String temp = smb.getName().toUpperCase()+"_TRACK";
      System.out.println("{"+System.getenv(temp).trim()+"} and {"+arg.trim()+"}");
      System.out.println("Lengths: "+System.getenv(temp).trim().length()+" and "+arg.trim().length());
      System.out.println("Check: "+System.getenv(temp).trim()==arg.trim());
      if (System.getenv(temp).trim()==arg.trim()) {
        System.out.println("GOT A MATCH. RETRIEVED IP: "+getIP(req));
        System.setProperty(temp, getMAC(getIP(req)));
        break;
      }
    }
  }

  public static String getIP(HttpServletRequest req) {
    return (req.getHeader("x-forwarded-for") == null) ? req.getRemoteAddr() : req.getHeader("x-forwarded-for");
  }

  public static String getMAC(String ip) {
    String str = "";
    String macAddress = "";
    try {
      Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
      InputStreamReader ir = new InputStreamReader(p.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);
      for (int i = 1; i < 100; i++) {
        str = input.readLine();
        if (str!=null) {
          if (str.indexOf("MAC Address") > 1) {
            macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
            break;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return macAddress;
  }
}