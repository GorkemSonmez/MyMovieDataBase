package com.bilgeadam.boost.course01.mymovies.client.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import com.bilgeadam.boost.course01.mymovies.client.communication.ServerCommunication;

/**
 * @author $Görkem Sönmez
 */
public class ActorsMovies {
	
	public ActorsMovies() {
		
	}
	
	public void showReply(String input, Socket socket, ServerCommunication communication) throws IOException {
		
		String reply = communication.askForActorsMovies(input);
		StringTokenizer tokenizer = new StringTokenizer(reply, "|");
		int cnt = 1;
		while (tokenizer.hasMoreElements()) {
			String token = (String) tokenizer.nextElement();
			System.out.printf("%03d - %s\n", cnt++, token);
		}
	}
}
