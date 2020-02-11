package filter;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceHibernate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    private UserService userService = UserServiceHibernate.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
//        String email = req.getParameter("emailIndex");
//        String password = req.getParameter("passwordIndex");
//        boolean isParamExists = email != null && password != null;
//        if (isParamExists) {
//            session.setAttribute("email", email);
//            session.setAttribute("password", password);
//        }
        if (session == null || session.getAttribute("email") == null) {
            req.setAttribute("message", "HELLO From Filter no session");
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            try {
                User user = userService.getUserByEmail((String) session.getAttribute("email"));

                if (user != null) {

                    if ((user.getRole().equals("admin") &&
                            user.getPassword().equals(session.getAttribute("password")))) {
                        chain.doFilter(request, response);
                    } else if (user.getRole().equals("user") &&
                            user.getPassword().equals(session.getAttribute("password"))) {
                        req.setAttribute("user", user);
                        resp.sendRedirect(req.getContextPath() + "/user");
                    } else {
                        req.setAttribute("message", "Wrong password: " + session.getAttribute("password"));
                        req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
                    }
                } else {
                    resp.setStatus(400);
                    req.setAttribute("message", "HELLO User == null");
                    resp.sendRedirect(req.getContextPath() + "/");
                }
            } catch (DBException e) {
                resp.setStatus(500);
                req.setAttribute("result", "DB ERROR");
                req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
