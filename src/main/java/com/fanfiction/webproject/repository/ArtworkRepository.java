package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Artwork;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends PagingAndSortingRepository<Artwork, Long> {

    List<Artwork> findByActiveTrue();

    List<Artwork> findByActiveTrueAndUser(UserEntity user);

    Artwork findByArtworkId(String artworkId);

    Page<Artwork> findByActiveTrueOrderByCreationDateDesc(Pageable pageable);

    Page<Artwork> findArtworksByActiveTrueAndUserIdOrderByCreationDateDesc(long userId, Pageable pageableRequest);

    Artwork findByCommentsContains(Comment comment);

    Artwork findByChaptersContains(Chapter chapter);

    @Query(value = "select a from Artwork a left join a.ratings r  where a.active = true group by a order by avg (r.value) desc")
    Page<Artwork> findTopOrderByAvg(Pageable pageable);


}
