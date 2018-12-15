package pl.edu.pwr.w8.distributor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.InterruptedException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

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

  public static MemcachedClient buildCacheClient() throws IOException {
    List<InetSocketAddress> servers = AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS").replace(",", " "));
    AuthInfo authInfo = AuthInfo.plain(
        System.getenv("MEMCACHIER_USERNAME"),
        System.getenv("MEMCACHIER_PASSWORD")
    );
    
    MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);
    for(InetSocketAddress server : servers) {
      builder.addAuthInfo(server, authInfo);
    }
    builder.setCommandFactory(new BinaryCommandFactory());
    builder.setConnectTimeout(1000);
    builder.setEnableHealSession(true);
    builder.setHealSessionInterval(2000);
    return builder.build();
  }
  
  public static boolean setCached(String key, String value, MemcachedClient mc) {
    try {
      mc.set(key, 0, value);
      return true;
    } catch (TimeoutException | InterruptedException | MemcachedException e) {
      e.printStackTrace();
    }
    return false;
  }
  
  public static String getCached(String key, MemcachedClient mc) {
    String value = "";
    try {
      value = mc.get(key);
    } catch (TimeoutException | InterruptedException | MemcachedException e) {
      e.printStackTrace();
    }
    System.out.println("VALUE? - "+value);
    return value;
  }

  public static String getIP(HttpServletRequest req) {
    return (req.getHeader("x-forwarded-for") == null)
        ? req.getRemoteAddr() + " (NOT REAL)"
        : req.getHeader("x-forwarded-for");
  }

  public static String getMAC(String ip) {
    StringBuilder sb = new StringBuilder();
    try {
      InetAddress address = InetAddress.getByName(ip);
      NetworkInterface ni = NetworkInterface.getByInetAddress(address);
      if (ni != null) {
        byte[] mac = ni.getHardwareAddress();
        if (mac != null) {
          for (int i = 0; i < mac.length; i++)
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
      }
    } catch (UnknownHostException | SocketException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }
}