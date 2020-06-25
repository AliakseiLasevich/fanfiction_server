package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.ArtworkEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends PagingAndSortingRepository<ArtworkEntity, Long> {
    ArtworkEntity findByArtworkId(String artworkId);
}
