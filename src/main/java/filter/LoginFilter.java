package filter;

import daoFactory.UserDaoFactory;
import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/login")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
//        if(req.getParameter("emailIndex") != null && req.getParameter("passwordIndex") != null){
//            chain.doFilter(request, response);
//        }
        if (session == null || session.getAttribute("user") == null) {
            chain.doFilter(req, resp);
        } else {
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRole().equals("admin")) {
                resp.sendRedirect(req.getContextPath() + "/admin/all");
            } else if (user != null && user.getRole().equals("user")) {
                session.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/user");
            } else {
                session.removeAttribute("user");
                req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
            }
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
