package filter;

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

@WebFilter
public class UserFilter implements Filter {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            try {
                User user = userService.getUserByEmail((String) session.getAttribute("email"));
                if (user != null) {
                    if ((user.getRole().equals("admin") || (user.getRole().equals("user"))) &&
                            user.getPassword().equals(session.getAttribute("password"))) {
                        chain.doFilter(request, response);
                    } else {
                        req.setAttribute("message", "Wrong Pass");
                        resp.sendRedirect(req.getContextPath() + "/");
                    }
                } else {
                    req.setAttribute("message", "User don't exists");
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
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
