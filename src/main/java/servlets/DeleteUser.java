package servlets;

import exception.DBException;
import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/delete")
public class DeleteUser extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("id");

        try {
            Long id =
                    Long.parseLong(
                            req.getParameter("id"));
            if (userService.deleteUser(id)) {
                List<User> users = UserServiceImpl.getInstance().getAllUsers();
                req.setAttribute("usersFromDB", users);
                req.getRequestDispatcher("/WEB-INF/view/allUsers.jsp").forward(req, resp);
            } else {
                resp.setStatus(200);
                req.setAttribute("result", "User don't exists");
                req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            req.setAttribute("result", "incorrect id (please use only numbers)");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);

        } catch (DBException e) {
            resp.setStatus(500);
            req.setAttribute("result", "DB ERROR");
            req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
        }
    }
}
