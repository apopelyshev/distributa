package pl.edu.pwr.w8.distributor;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

@WebServlet(name = "MainServlet", urlPatterns = { "/main" })

public class MainServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  AppService appService = new AppService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String code = req.getParameter("c");
    Person[] result = appService.getPeople();
    if (code != null) {
      System.out.println("GOT A PARAMETER! LET'S SEE...");
      Util.handleTrackCode(req, code, result);
    }
    forwardContent(req, resp, result);
  }

  private void forwardContent(HttpServletRequest req, HttpServletResponse resp, Person[] peopleArr)
      throws ServletException, IOException {
    String nextJSP = Util.getProps().getProperty("pathTo.pages") + "/main.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
    req.setAttribute("peopleArr", peopleArr);
    dispatcher.forward(req, resp);
  }
}