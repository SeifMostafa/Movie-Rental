package Controllers;

import Models.MovieModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateMovie", urlPatterns = {"/UpdateMovie"})
public class UpdateMovie extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        HashMap<String,String>values=new HashMap<String,String>();
        values.put("name",request.getParameter("movieName"));
        values.put("category",request.getParameter("category"));
        values.put("description",request.getParameter("description"));
        values.put("imgUrl", request.getParameter("imgUrl"));
        values.put("duration",request.getParameter("duration"));
        values.put("price",request.getParameter("price"));
        values.put("year",request.getParameter("year"));
        values.put("quality",request.getParameter("quality"));
        
        MovieModel movie= new MovieModel();
        movie.updateMovie(values);
        response.setContentType("text/html");
        request.setAttribute("id", request.getParameter("id"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("ShowMovie");
        dispatcher.forward(request, response);

    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
