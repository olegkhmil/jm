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

@WebFilter("/")
public class LoginFilter implements Filter {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        try {
            if (session == null || session.getAttribute("email") == null) {
                chain.doFilter(request, response);
            } else {
                User user = userService.getUserByEmail((String) session.getAttribute("email"));
                if (user != null &&
                        user.getRole().equals("admin") &&
                        user.getPassword().equals(session.getAttribute("password"))) {
                    resp.sendRedirect(req.getContextPath() + "/admin/all");
                } else if(user != null &&
                        user.getRole().equals("user") &&
                        user.getPassword().equals(session.getAttribute("password"))) {
                    resp.sendRedirect(req.getContextPath() + "/user");
                } else {
                    session.removeAttribute("email");
                    session.removeAttribute("password");
                    req.setAttribute("message", "User don't exists or wrong password");
                    req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
                }
            }
        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
