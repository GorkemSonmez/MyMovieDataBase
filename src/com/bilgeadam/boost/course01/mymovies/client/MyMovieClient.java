package com.bilgeadam.boost.course01.mymovies.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

import com.bilgeadam.boost.course01.mymovies.client.communication.ServerCommunication;
import com.bilgeadam.boost.course01.mymovies.client.controller.ActorsMovies;
import com.bilgeadam.boost.course01.mymovies.client.controller.GenresMovies;
import com.bilgeadam.boost.course01.mymovies.client.controller.RatingsMovie;
import com.bilgeadam.boost.course01.mymovies.client.controller.TagMovie;
import com.bilgeadam.boost.course01.mymovies.client.controller.YearsMovies;
import com.bilgeadam.boost.course01.mymovies.client.model.Data;
import com.bilgeadam.boost.course01.mymovies.client.model.Database;
import com.bilgeadam.boost.course01.mymovies.client.view.Menu;
import com.bilgeadam.boost.course01.mymovies.database.DatabaseSetup;

/**
 * @author $Görkem Sönmez
 */
public class MyMovieClient {
	private String id;
	private ServerCommunication communication;
	private Socket socket;
	private Scanner sc;
	
	public MyMovieClient() {
		this.id = UUID.randomUUID().toString();
		
	}
	
	public static void main(String[] args) {
		
		MyMovieClient movieClient = new MyMovieClient();
		movieClient.checkDatabase();
		movieClient.connect2Server();
		try {
			movieClient.startUI();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void checkDatabase() {
		if (!Database.getInstance().isInitialized()) {
			System.err.println("Database is not initialized. Creating it...");
			new DatabaseSetup().execute();
		} else {
			System.out.println("Database initialized");
			if (Database.getInstance().isLoaded()) {
				System.out.println("Data is loaded");
			} else {
				System.out.println("Data is not loaded");
				Data.parse();
				Data.load();
			}
		}
	}
	
	private void startUI() throws IOException {
		sc = new Scanner(System.in);
		Menu menu = new Menu.Builder().title("Babür Hoca'nın Filmleri").body("IMDB verilerinden oluşturulmuştur").build();
		menu.addMenu(1, "Artistin filmleri");
		menu.addMenu(2, "Yıla Göre Film Listesi");
		menu.addMenu(3, "Türe Göre Film Listesi");
		menu.addMenu(4, "Film Puanlaması");
		menu.addMenu(5, "Taglere Göre Film Listesi");
		menu.addMenu(80, "CSV'leri yükle");
		menu.addMenu(99, "Programdan çık");
		int selection = -1;
		while (selection != 99) {
			selection = menu.show().readInteger();
			this.processSelection(selection);
		}
		sc.close();
	}
	
	private void processSelection(int selection) throws IOException {
		switch (selection) {
			case 1: {
				ActorsMovies actorMovies = new ActorsMovies();
				System.out.println("Lütfen bir artist adı giriniz: ");
				actorMovies.showReply(sc.nextLine(), socket, communication);
				break;
			}
			case 2: {
				YearsMovies ym = new YearsMovies();
				System.out.println("Lütfen bir yıl giriniz: ");
				ym.getMoviesByYear(sc.nextInt()).toString();
				sc.nextLine();
				System.out.println(ym.toString());
				break;
			}
			case 3: {
				GenresMovies gm = new GenresMovies();
				System.out.println("Lütfen bir tür giriniz: ");
				gm.moviesByGenre(sc.nextLine());
				sc.nextLine();
				break;
			}
			case 4: {
				RatingsMovie gm = new RatingsMovie();
				System.out.println("Lütfen bir film giriniz: ");
				gm.moviesByRatings(sc.nextLine());
				sc.nextLine();
				break;
			}
			case 5: {
				TagMovie gm = new TagMovie();
				System.out.println("Lütfen bir etiket giriniz: ");
				gm.moviesByTag(sc.nextLine());
				sc.nextLine();
				break;
			}
			case 80: {
				if (Database.getInstance().isLoaded()) {
					System.out.println("Veriler Zaten yüklü");
				} else {
					Data.parse();
					Data.load();
					System.out.println("Tablolar yüklendi");
				}
				
			}
			default:
				System.out.println("Lütfen Geçerli Bir Seçenek Girin");
		}
		new Menu.Builder().body("Devam etmek için bir tuşa basınız...").build().show().readString();
	}
	
	private void connect2Server() {
		try {
			this.socket = new Socket("localhost", 4711);
			this.communication = new ServerCommunication(this.socket);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
