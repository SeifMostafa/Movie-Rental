package Controllers;

import Models.UserModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ExtendRentingMovie", urlPatterns = {"/ExtendRentingMovie"})
public class ExtendRentingMovie extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession session =request.getSession(true);

        int userId = (int) session.getAttribute("userId");
        int movieId = Integer.parseInt(request.getParameter("id"));
        int extendedPeriod = Integer.parseInt(request.getParameter("rentPeriod"));
        float extendedPrice = Float.parseFloat(request.getParameter("totalPrice"))*extendedPeriod;
        
        UserModel user = new UserModel();
         if(user.hasBalance(extendedPrice, userId)){
            user.extendRentingMovie(userId, movieId, extendedPeriod, extendedPrice);
            user.reduceBalance(userId, extendedPrice);
            ShowMovie show = new ShowMovie();
            request.setAttribute("rented", null);
            show.processRequest(request, response);
         }else{
            ShowMovie show = new ShowMovie();
            request.setAttribute("rented", false);
            show.processRequest(request, response);
         }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ExtendRentingMovie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ExtendRentingMovie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
