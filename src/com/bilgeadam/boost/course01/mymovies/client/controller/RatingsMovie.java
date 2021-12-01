package com.bilgeadam.boost.course01.mymovies.client.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bilgeadam.boost.course01.mymovies.client.model.Database;

/**
 * @author $Görkem Sönmez
 */
public class RatingsMovie {
	
	public RatingsMovie() {
		super();
		
	}
	
	public void moviesByRatings(String movie) {
		movie = movie.toUpperCase();
		movie = movie.replace("İ", "I");
		
		maxRating(movie);
		minRating(movie);
		
	}
	
	private void maxRating(String movie) {
		String query = "select movies.name,movie_ratings.rating from movies inner join movie_ratings on movies.id=movie_ratings.movie_id where upper(movies.name)=? group by movies.name,movie_ratings.rating order by movie_ratings.rating desc ;";
		
		try (Connection conn = Database.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			
			stmt.setString(1, movie);
			
			ResultSet rs = stmt.executeQuery();
			boolean isThereMovie = false;
			while (rs.next()) {
				isThereMovie = true;
				System.out.print("Film İsmi===> " + rs.getString(1) + " Maximum Oyu ===> " + rs.getDouble(2));
				break;
			}
			if (isThereMovie == false) {
				System.out.println("Böyle bir Film yoktur.");
				
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	private void minRating(String movie) {
		String query = "select movies.name,movie_ratings.rating from movies inner join movie_ratings on movies.id=movie_ratings.movie_id where upper(movies.name)=? group by movies.name,movie_ratings.rating order by movie_ratings.rating asc ;";
		
		try (Connection conn = Database.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			
			stmt.setString(1, movie);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				System.out.print(" Minimum Oyu ===> " + rs.getDouble(2) + "\n");
				break;
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
