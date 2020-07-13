package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Artwork;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends PagingAndSortingRepository<Artwork, Long> {
    Artwork findByArtworkId(String artworkId);

    Page<Artwork> findAllByOrderByCreationDateDesc(Pageable pageable);

    Page<Artwork> findByUserIdOrderByCreationDate(long userId, Pageable pageableRequest);

    Artwork findByCommentsContains(Comment comment);

    Artwork findByChaptersContains(Chapter chapter);
}
