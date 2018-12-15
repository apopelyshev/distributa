package pl.edu.pwr.w8.distributor;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import net.rubyeye.xmemcached.MemcachedClient;

@WebServlet(name = "MainServlet", urlPatterns = { "/main" })

public class MainServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private MemcachedClient cacheInstance;
  private AppService appService = new AppService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String code = req.getParameter("c");
    Person[] result = appService.getPeople();
    if (code != null) {
      if (cacheInstance==null) {
        try { cacheInstance = Util.buildCacheClient(); }
        catch (IOException ioe) { ioe.printStackTrace(); }
      }
      for (Person smb : result) {
        String temp = smb.getName().toUpperCase() + "_TRACK";
        boolean check = Util.getCached(temp, cacheInstance)==null
                     || Util.getCached(temp, cacheInstance).isEmpty();
        if (System.getenv(temp).equals(code) && check) {
          Util.setCached(temp, Util.getIP(req), cacheInstance);
          break;
        }
      }
    }
    forwardContent(req, resp, result);
  }

  private void forwardContent(HttpServletRequest req, HttpServletResponse resp, Person[] peopleArr)
      throws ServletException, IOException {
    String nextJSP = Util.getProps().getProperty("pathTo.pages") + "main.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
    req.setAttribute("peopleArr", peopleArr);
    dispatcher.forward(req, resp);
  }
}