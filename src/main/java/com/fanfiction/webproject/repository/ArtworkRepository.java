package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends PagingAndSortingRepository<Artwork, Long> {
    Artwork findByArtworkId(String artworkId);
    Page<Artwork> findAllByOrderByCreationDateDesc(Pageable pageable);

    Page<Artwork> findByUserIdOrderByCreationDate(long userId, Pageable pageableRequest);
}
