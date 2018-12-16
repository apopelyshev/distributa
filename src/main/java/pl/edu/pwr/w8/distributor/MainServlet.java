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
  private MemcachedClient memcachedInstance;
  private AppService appService = new AppService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Person member;
    String code = req.getParameter("c");
    PersonArr allMembers = appService.getPeople();
    
    try {
      if (memcachedInstance==null)
        memcachedInstance = Util.buildCacheClient();
      
      if (code != null)
        member = Util.getMemberByCode(req, memcachedInstance, code, allMembers);
      member = Util.getMemberByMAC(req, memcachedInstance, allMembers);
      if (member!=null) Util.recordMemberStatus(member, allMembers);
    }
    catch (IOException ioe) { ioe.printStackTrace(); }
    
    forwardContent(req, resp, allMembers.getArr());
  }

  private void forwardContent(HttpServletRequest req, HttpServletResponse resp, Person[] members2jsp) throws ServletException, IOException {
    String nextJSP = Util.getProps().getProperty("pathTo.pages") + "main.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
    req.setAttribute("members", members2jsp);
    dispatcher.forward(req, resp);
  }
}