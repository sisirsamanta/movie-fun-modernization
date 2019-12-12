package org.superbiz.moviefun.support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.support.albumsapi.AlbumFixtures;
import org.superbiz.moviefun.support.albumsapi.AlbumInfo;
import org.superbiz.moviefun.support.albumsapi.AlbumsClient;
import org.superbiz.moviefun.support.moviesapi.MovieFixtures;
import org.superbiz.moviefun.support.moviesapi.MovieInfo;
import org.superbiz.moviefun.support.moviesapi.MoviesClient;

import java.util.Map;

//@Controller
public class SetupController {

    private final MoviesClient moviesClient;
    private final AlbumsClient albumsClient;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public SetupController(MoviesClient moviesClient, AlbumsClient albumsClient, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.moviesClient = moviesClient;
        this.albumsClient = albumsClient;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (MovieInfo movie : movieFixtures.load()) {
            moviesClient.addMovieInfo(movie);
        }

        for (AlbumInfo album : albumFixtures.load()) {
            albumsClient.addAlbumInfo(album);
        }

        model.put("movies", moviesClient.getMovies());
        model.put("albums", albumsClient.getAlbumInfos());

        return "setup";
    }
}