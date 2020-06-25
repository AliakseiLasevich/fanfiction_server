package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Artwork;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends PagingAndSortingRepository<Artwork, Long> {
    Artwork findByArtworkId(String artworkId);
}
