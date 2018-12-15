package pl.edu.pwr.w8.distributor;

import java.util.Optional;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

public class Main {
  public static final Optional<String> PORT = Optional.ofNullable(System.getenv("PORT"));

  public static void main(String[] args) throws Exception {
    String appBase = Util.getProps().getProperty("pathTo.app");
    int port = Integer.valueOf(PORT.orElse("8080"));
    Tomcat tomcat = new Tomcat();
    
    Connector nioConnector = new Connector(Util.getProps().getProperty("protocol"));
    nioConnector.setPort(port);
    
    tomcat.setPort(port);
    tomcat.setConnector(nioConnector);
    tomcat.getHost().setAppBase(appBase);
    tomcat.getService().addConnector(tomcat.getConnector());
    StandardContext ctx = (StandardContext) tomcat.addWebapp("", appBase);
    ctx.setDefaultWebXml(Util.getProps().getProperty("pathTo.xml"));
    tomcat.start();
    tomcat.getServer().await();
  }
}