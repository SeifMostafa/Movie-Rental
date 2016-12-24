package Models;

import DBConnection.DBC;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieModel {
    
    public MovieModel(){
        
    }
    
    public HashMap<String,String> showMovie(int id, int userId){
        
        HashMap<String,String> movie = new HashMap<>();
        String name="", description="", img_url="", duration="", renting_price_per_day="", category="", year="", quality="";
        double rate_sum = 0.0, rate = 0.0, rate_count = 0.0;
        boolean isRent = true, currentRent= false;
        Date endDate; 
        Connection con = DBC.getActiveConnection();
        String query="Select * from movie where id=?";
        try {
            PreparedStatement p = (PreparedStatement) con.prepareStatement(query);
            p.setInt(1, id);
            ResultSet row = p.executeQuery();
            
            if(row.next()){
                name = row.getString("name");
                description = row.getString("description");
                img_url = row.getString("img_url");
                duration = row.getString("duration");
                renting_price_per_day = row.getString("renting_price_per_day");
                category = row.getString("category");
                rate_sum = row.getDouble("rate_sum");
                rate = row.getDouble("rate");
                rate_count = row.getDouble("rate_count");
                year = row.getString("year");
                quality = row.getString("quality");
            }
            
            query="Select * from movie_user_rent where idUser=? and idMovie=?";
            p = (PreparedStatement) con.prepareStatement(query);
            p.setInt(1, userId);
            p.setInt(2, id);
            
            row = p.executeQuery();
            
            if(row.next()){
                endDate = row.getDate("endDate");
                java.util.Date utilDate = new java.util.Date();
                Date currentDate = new Date(utilDate.getTime());
                if (currentDate.getTime() > endDate.getTime()){
                    long diff = currentDate.getTime() - endDate.getTime();
                    long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    if (diffDays < 7 ){
                        isRent = false;
                    }
                }else{
                    currentRent = true;
                }
            }
            
            movie.put("name", name);
            movie.put("description", description);
            movie.put("img_url", img_url);
            movie.put("duration", duration);
            movie.put("renting_price_per_day", renting_price_per_day);
            movie.put("category", category);
            movie.put("rate_sum", String.valueOf(rate_sum));
            movie.put("rate", String.valueOf(rate));
            movie.put("rate_count", String.valueOf(rate_count));
            movie.put("id", String.valueOf(id));
            movie.put("year", year);
            movie.put("quality", quality);
            movie.put("isRent", String.valueOf(isRent));
            movie.put("currentRent", String.valueOf(currentRent));
            DBC.closeConnection();

            return movie;
        } catch (SQLException ex) {
            Logger.getLogger(MovieModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DBC.closeConnection();
        
        return null;
    }
}
