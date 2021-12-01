package com.bilgeadam.boost.course01.mymovies.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.bilgeadam.boost.course01.mymovies.server.data.Movie;
import com.bilgeadam.boost.course01.mymovies.server.data.Name;

/**
 * @author $Görkem Sönmez
 */
public class ClientHandler implements Runnable {
	private final Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
		this.out = null;
		this.in = null;
	}
	
	public void run() {
		try {
			
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String line;
			while ((line = in.readLine()) != null) {
				this.processRequest(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void processRequest(String line) {
		
		if (line.startsWith("FILMS:")) {
			String actorName = line.substring(6);
			System.out.println(actorName);
			String[] movieIds = Name.MovieIds(actorName.trim());
			System.out.println(movieIds);
			if (movieIds != null) {
				String[] movieTitles = Movie.getMovieTitles(movieIds);
				line = "";
				for (String title : movieTitles) {
					line += title + "|";
				}
			} else {
				line = "Kayıt bulunamadı";
			}
		}
		this.out.println(line);
		
		this.out.flush();
	}
}
