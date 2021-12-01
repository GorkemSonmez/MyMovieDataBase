package com.bilgeadam.boost.course01.mymovies.client.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bilgeadam.boost.course01.mymovies.client.model.Database;

/**
 * @author $Görkem Sönmez
 */
public class TagMovie {
	
	public TagMovie() {
		super();
		
	}
	
	public void moviesByTag(String tag) {
		tag = tag.toUpperCase();
		tag = tag.replaceAll("I", "İ");
		tag = "%" + tag + "%";
		
		String query = "select m.name from movies as m inner join(select t.tag,t.id,mt.movie_id from tags as t inner join movie_tags as mt on t.id=mt.tag_id where t.tag like ? ) as x on x.movie_id=m.id;";
		try (Connection conn = Database.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(query);) {
			
			stmt.setString(1, tag);
			
			ResultSet rs = stmt.executeQuery();
			boolean isThereTag = false;
			while (rs.next()) {
				isThereTag = true;
				System.out.print("Film İsmi===> " + rs.getString(1) + "\n");
				
			}
			if (isThereTag == false) {
				System.out.println("Böyle bir etiket yoktur.");
				
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
