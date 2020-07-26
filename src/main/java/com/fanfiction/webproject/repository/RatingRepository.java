package com.fanfiction.webproject.repository;

import com.fanfiction.webproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query(value = "SELECT AVG(r.value) from Rating r WHERE r.artwork.artworkId =:artworkId")
    Double getAverageRatingByArtworkId(@Param("artworkId") String artworkId);

    Rating findByUserEntityUserIdAndArtworkArtworkId(String userId, String artworkId);
}
