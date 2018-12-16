package pl.edu.pwr.w8;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainServlet", urlPatterns = { "/main" })

public class MainServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private CacheService cacheService = new CacheService();
  private AppService appService = new AppService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Person member;
    PersonArr allMembers = appService.getPeople();
    String code = req.getParameter("c");
    
    if (code != null)
      member = cacheService.getMemberByCode(req, code, allMembers);
    member = cacheService.getMemberByMAC(req, allMembers);
    if (member!=null) Util.recordMemberStatus(member, allMembers);
    
    if (System.getenv("RESET")!=null)
      cacheService.handleAllMemberTrackings(allMembers, "reset");
    System.out.println(cacheService.handleAllMemberTrackings(allMembers, "print"));
    forwardContent(req, resp, allMembers.getArr());
  }

  private void forwardContent(HttpServletRequest req, HttpServletResponse resp, Person[] members2jsp) throws ServletException, IOException {
    String nextJSP = Util.getProps().getProperty("pathTo.pages") + "main.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
    req.setAttribute("members", members2jsp);
    dispatcher.forward(req, resp);
  }
}