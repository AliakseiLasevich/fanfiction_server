package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Artwork;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends PagingAndSortingRepository<Artwork, Long> {
    Artwork findByArtworkId(String artworkId);

    Page<Artwork> findAllByOrderByCreationDateDesc(Pageable pageable);

    Page<Artwork> findByUserIdOrderByCreationDate(long userId, Pageable pageableRequest);

    Artwork findByCommentsContains(Comment comment);

    Artwork findByChaptersContains(Chapter chapter);

    @Query(value = "SELECT *, (SELECT AVG(value) FROM Rating WHERE  artwork_id = artwork.id) AS avg_rating " +
            "from Artwork ORDER BY avg_rating LIMIT 5", nativeQuery = true)
    List<Artwork> findTop5OrderByAvg();


}
