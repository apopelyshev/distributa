package pl.edu.pwr.w8;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;

public class Util {
  static Properties getProps() {
    Properties res = new Properties();
    try {
      res.load(path2Stream("/my.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return res;
  }

  static InputStreamReader path2Stream(String arg) throws UnsupportedEncodingException {
    InputStream temp = new Util().getClass().getResourceAsStream(arg);
    return new InputStreamReader(temp, "UTF-8");
  }
  
  static void recordMemberStatus(Person memberFromServlet, PersonArr arrOfAllMembers) {
    int searchInd = arrOfAllMembers.getIndAtMatch(memberFromServlet);
    if (searchInd>=0) arrOfAllMembers.getArr()[searchInd].setActive(true);
  }

  static String getIP(HttpServletRequest req) {
    return (req.getHeader("x-forwarded-for") == null)
        ? req.getRemoteAddr() + "NR"
        : req.getHeader("x-forwarded-for");
  }

  static String getMAC(HttpServletRequest req) {
    System.out.println("Trying to get the MAC");
    String ip = getIP(req);
    System.out.println("Got an IP: "+ip);
    if (ip.endsWith("NR"))
      return ip;
    System.out.println("Trying to build MAC...");
    StringBuilder sb = new StringBuilder();
    try {
      InetAddress address = InetAddress.getByName(ip);
      NetworkInterface ni = NetworkInterface.getByInetAddress(address);
      if (ni != null) {
        System.out.println("Got a NetworkInterface");
        byte[] mac = ni.getHardwareAddress();
        if (mac != null) {
          System.out.println("Got a hardware address");
          for (int i = 0; i < mac.length; i++)
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
      }
    } catch (UnknownHostException | SocketException e) {
      e.printStackTrace();
    }
    System.out.println("Finally the MAC-string is built. Of length: "+sb.capacity());
    return sb.toString();
  }
}