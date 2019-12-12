package org.superbiz.moviefun.support.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import static org.springframework.http.HttpMethod.GET;

public class MoviesClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String moviesUrl;
    private RestOperations restOperations;
    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };

  /*  @PersistenceContext
    private EntityManager entityManager;*/

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
    }

    public MovieInfo find(Long id) {
        return restOperations.exchange(moviesUrl + "/" + id, GET, null, movieListType).getBody().get(0);
    }

    @Transactional
    public void addMovieInfo(MovieInfo movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.getTitle(), movie.getYear());

        restOperations.postForEntity(moviesUrl, movie, MovieInfo.class);
    }



    @Transactional
    public void deleteMovieInfoId(long id) {
        restOperations.delete(moviesUrl + "/" + id);
    }

    public List<MovieInfo> getMovieInfos() {
        return restOperations.exchange(moviesUrl, GET, null, movieListType).getBody();
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();

    }

    public int countAll() {
        return restOperations.getForObject(moviesUrl + "/count", Integer.class);
    }

    public int count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", searchTerm);
        return restOperations.getForObject(builder.toUriString(), Integer.class);

    }

    public List<MovieInfo> findRange(String field, String key, int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }

    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl, GET, null, movieListType).getBody();
    }

    /*public void clean() {
        entityManager.createQuery("delete from MovieInfo").executeUpdate();
    }*/
}
