package com.bilgeadam.boost.course01.mymovies.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.bilgeadam.boost.course01.mymovies.server.common.CommonData;
import com.bilgeadam.boost.course01.mymovies.utils.Props;

/**
 * @author $Görkem Sönmez
 */
public class MyMovieServer {
	
	public static void main(String[] args) {
		
		CommonData.getInstance().loadPropertiesFile(args);
		
		(new MyMovieServer()).start();
	}
	
	private void start() {
		
		this.startServer();
		
	}
	
	private void startServer() {
		try (ServerSocket server = new ServerSocket(Props.getInstance().getServerPort())) {
			server.setReuseAddress(true);
			
			while (true) {
				System.out.println("Server Hazır");
				Socket client = server.accept();
				
				ClientHandler clienthandler = new ClientHandler(client);
				new Thread(clienthandler).start();
			}
		} catch (IOException ex) {
			
			ex.printStackTrace();
		}
	}
}
