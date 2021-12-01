package com.bilgeadam.boost.course01.mymovies.client.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author $Görkem Sönmez
 */
public class ServerCommunication {
	private PrintWriter out;
	private BufferedReader in;
	
	public ServerCommunication(Socket socket) {
		super();
		
		try {
			this.out = new PrintWriter(socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public String askForActorsMovies(String actorName) throws IOException {
		String line = "FILMS:" + actorName;
		out.println(line);
		out.flush();
		return in.readLine();
	}
	
}
