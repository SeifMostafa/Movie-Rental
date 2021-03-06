package Controllers;

import Models.MovieModel;
import Models.UserModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "RentMovie", urlPatterns = {"/RentMovie"})
public class RentMovie extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        HttpSession session =request.getSession(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        
        int userId = (int) session.getAttribute("userId");
        int movieId = Integer.parseInt(request.getParameter("id"));
        int rentPeriod = Integer.parseInt(request.getParameter("rentPeriod"));
        float totalPrice = Float.parseFloat(request.getParameter("totalPrice"))*rentPeriod;
        
        java.util.Date utilDate = new java.util.Date();
        Date startDate = new Date(utilDate.getTime());
        
        Calendar c = Calendar.getInstance(); 
        c.setTime(utilDate); 
        c.add(Calendar.DATE, rentPeriod);
        utilDate = c.getTime();
        Date endDate = new Date(utilDate.getTime());
        
        
        
        UserModel user = new UserModel();
        if(user.hasBalance(totalPrice, userId)){
            user.rentMovie(userId, movieId, startDate, endDate, totalPrice);
            user.reduceBalance(userId, totalPrice);
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
        } catch (ParseException ex) {
            Logger.getLogger(RentMovie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(RentMovie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
