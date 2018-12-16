package pl.edu.pwr.w8;

import java.util.Optional;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class Base {
  public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));

  public static void main(String[] args) throws Exception {
    int port = Integer.valueOf(PORT.orElse("8080"));
    Tomcat tomcat = new Tomcat();
    
    Connector nioConnector = new Connector(Util.getProps().getProperty("protocol"));
    nioConnector.setPort(port);
    
    tomcat.setPort(port);
    tomcat.setConnector(nioConnector);
    tomcat.getHost().setAppBase(".");
    tomcat.getService().addConnector(tomcat.getConnector());
    tomcat.addWebapp("", ".");
    tomcat.start();
    tomcat.getServer().await();
  }
}