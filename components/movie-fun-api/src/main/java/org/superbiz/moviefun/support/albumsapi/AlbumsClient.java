package org.superbiz.moviefun.support.albumsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class AlbumsClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String albumsUrl;
    private RestOperations restOperations;
    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

  /*  @PersistenceContext
    private EntityManager entityManager;*/

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
    }

    public AlbumInfo find(Long id) {
        return restOperations.exchange(albumsUrl + "/" + id, GET, null, albumListType).getBody().get(0);
    }

    @Transactional
    public void addAlbumInfo(AlbumInfo album) {
        logger.debug("Creating movie with title {}, and year {}", album.getTitle(), album.getYear());

        restOperations.postForEntity(albumsUrl, album, AlbumInfo.class);
    }



    @Transactional
    public void deleteAlbumInfoId(long id) {
        restOperations.delete(albumsUrl + "/" + id);
    }

    public List<AlbumInfo> getAlbumInfos() {
        return restOperations.exchange(albumsUrl, GET, null, albumListType).getBody();
    }

    public List<AlbumInfo> findAll(int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(albumsUrl)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);

        return restOperations.exchange(builder.toUriString(), GET, null, albumListType).getBody();

    }

    public int countAll() {
        return restOperations.getForObject(albumsUrl + "/count", Integer.class);
    }

    public int count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(albumsUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", searchTerm);
        return restOperations.getForObject(builder.toUriString(), Integer.class);

    }

    public List<AlbumInfo> findRange(String field, String key, int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(albumsUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(builder.toUriString(), GET, null, albumListType).getBody();
    }


    /*public void clean() {
        entityManager.createQuery("delete from AlbumInfo").executeUpdate();
    }*/
}
