package com.bilgeadam.boost.course01.mymovies.client.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bilgeadam.boost.course01.mymovies.client.model.Database;

/**
 * @author $Görkem Sönmez
 */
public class GenresMovies {
	
	public GenresMovies() {
		super();
		
	}
	
	public void moviesByGenre(String genre) {
		genre = genre.toUpperCase();
		genre = genre.replaceAll("I", "İ");
		
		String query = "SELECT m.name,m.year from movies m, genres g, movie_genres mg where" + "					 mg.movie_id=m.id AND mg.genre_id=g.id AND" + "						g.genre =? ORDER BY RANDOM() limit 10;";
		try (Connection conn = Database.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			
			stmt.setString(1, genre);
			
			ResultSet rs = stmt.executeQuery();
			boolean isThereGenre = false;
			while (rs.next()) {
				isThereGenre = true;
				System.out.print("Film İsmi===> " + rs.getString(1) + " Yılı ===> " + rs.getInt(2) + "\n");
				
			}
			if (isThereGenre == false) {
				System.out.println("Böyle bir tür yoktur.");
				
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
