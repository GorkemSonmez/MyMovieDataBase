package com.bilgeadam.boost.course01.mymovies.server.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.bilgeadam.boost.course01.mymovies.server.data.Movie;
import com.bilgeadam.boost.course01.mymovies.server.data.Name;
import com.bilgeadam.boost.course01.mymovies.utils.Props;

/**
 * @author $Görkem Sönmez
 */
public class CommonData {
	public static final int UNSPECIFIED = -1;
	public static final String UNKNOWN = "";
	private static CommonData instance;
	private Logger logger;
	
	private TreeMap<Integer, ArrayList<String>> moviesByYear;
	
	private LinkedList<String> clients;
	
	private CommonData() {
		super();
	}
	
	public static CommonData getInstance() {
		if (instance == null) {
			instance = new CommonData();
		}
		return instance;
	}
	
	public void loadPropertiesFile(String[] args) {
		if (args.length == 0) {
			
			Props.getInstance().setPropsFilePath(Thread.currentThread().getContextClassLoader().getResource("").getPath(), "server.properties");
			this.getLogger().warning("No properties-File defined. Using Default");
		} else {
			
			Props.getInstance().setPropsFilePath(args[0], "server.properties");
			this.getLogger().info("Using given properties-File on <<<" + args[0] + ">>>");
		}
	}
	
	public void logInfo(String msg) {
		this.getLogger().info(msg);
	}
	
	public void logWarning(String msg) {
		this.getLogger().warning(msg);
	}
	
	public void logError(String msg) {
		this.getLogger().severe(msg);
	}
	
	private Logger getLogger() {
		if (this.logger == null) {
			this.logger = Logger.getLogger("My Movie Server Logger");
			this.logger.setLevel(Props.getInstance().getLogLevel());
		}
		return this.logger;
	}
	
	public Movie readMovies(String movieId) {
		CommonData.getInstance().getLogger().info("Enter readMovies()");
		Movie movie = Movie.read(movieId);
		return movie;
	}
	
	public Name readNames(String actorName) {
		CommonData.getInstance().getLogger().info("Enter readNames()");
		Name name = Name.read(actorName);
		return name;
	}
	
	private TreeMap<Integer, ArrayList<String>> getMoviesByYear() {
		if (this.moviesByYear == null) {
			this.moviesByYear = new TreeMap<Integer, ArrayList<String>>();
		}
		return this.moviesByYear;
	}
	
	public void addMovieToYear(Movie movie) {
		ArrayList<String> yearsMovies = this.getMoviesByYear().get(movie.getStartYear());
		if (yearsMovies == null) {
			yearsMovies = new ArrayList<>();
			this.getMoviesByYear().put(movie.getStartYear(), yearsMovies);
		}
		yearsMovies.add(movie.getID());
	}
	
	public void registerClient(String clientId) {
		this.getClients().add(clientId);
	}
	
	private LinkedList<String> getClients() {
		if (this.clients == null) {
			this.clients = new LinkedList<String>();
		}
		return this.clients;
	}
	
	public int numClients() {
		return this.getClients().size();
	}
}
