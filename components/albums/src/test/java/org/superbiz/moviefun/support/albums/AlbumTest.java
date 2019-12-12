package org.superbiz.moviefun.support.albums;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AlbumTest {

    @Test
    public void testIsEquivalent() {
        AlbumInfo persisted = new AlbumInfo("Radiohead", "OK Computer", 1997, 8);
        persisted.setId(10L);

        AlbumInfo sameFromCsv = new AlbumInfo("Radiohead", "OK Computer", 1997, 9);
        assertThat(persisted.isEquivalent(sameFromCsv), is(true));

        AlbumInfo otherFromCsv = new AlbumInfo("Radiohead", "Kid A", 2000, 9);
        assertThat(persisted.isEquivalent(otherFromCsv), is(false));
    }
}
