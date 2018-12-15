package pl.edu.pwr.w8.distributor;

import java.util.Optional;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class Main {
  public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));

  public static void main(String[] args) throws Exception {
    String contextPath = "";
    String appBase = ".";
    int port = Integer.valueOf(PORT.orElse("8080"));
    Tomcat tomcat = new Tomcat();
    
    Connector nioConnector = new Connector(Util.getProps().getProperty("protocol"));
    nioConnector.setPort(port);
    
    tomcat.setPort(port);
    tomcat.setConnector(nioConnector);
    tomcat.getHost().setAppBase(appBase);
    tomcat.getService().addConnector(tomcat.getConnector());
    Context webContext = tomcat.addWebapp(contextPath, appBase);
    webContext.setAltDDName(Util.getProps().getProperty("pathTo.xml"));
    tomcat.start();
    tomcat.getServer().await();
  }
}