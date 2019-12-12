package org.superbiz.moviefun.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.superbiz.moviefun.support.albumsapi.AlbumFixtures;
import org.superbiz.moviefun.support.albumsapi.AlbumsClient;
import org.superbiz.moviefun.support.albumsapi.AlbumInfo;
import org.superbiz.moviefun.support.moviesapi.MovieFixtures;
import org.superbiz.moviefun.support.moviesapi.MovieInfo;
import org.superbiz.moviefun.support.moviesapi.MoviesClient;

import java.util.Map;

@Controller
public class HomeController {

    private final MoviesClient moviesClient;
    private final AlbumsClient albumsClient;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public HomeController(MoviesClient moviesClient, AlbumsClient albumsClient, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.moviesClient = moviesClient;
       this.albumsClient = albumsClient;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

   /* public HomeController(MoviesRepository moviesRepository, MovieFixtures movieFixtures) {
        this.moviesRepository = moviesRepository;
           this.movieFixtures = movieFixtures;
    }*/


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (MovieInfo movieInfo : movieFixtures.load()) {
            moviesClient.addMovieInfo(movieInfo);
        }

      for (AlbumInfo album : albumFixtures.load()) {
          albumsClient.addAlbumInfo(album);
        }

        model.put("movies", moviesClient.getMovies());
        model.put("albums", albumsClient.getAlbumInfos());

        return "setup";
    }
}
