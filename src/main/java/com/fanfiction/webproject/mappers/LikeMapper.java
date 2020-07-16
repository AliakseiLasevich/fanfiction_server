package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.dto.LikeDto;
import com.fanfiction.webproject.dto.RatingDto;
import com.fanfiction.webproject.entity.Like;
import com.fanfiction.webproject.entity.Rating;
import com.fanfiction.webproject.ui.model.response.LikeRest;
import com.fanfiction.webproject.ui.model.response.RatingRest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    LikeDto entityToDto(Like like);

    LikeRest dtoToRest(LikeDto likeDto);

    LikeMapper INSTANCE = Mappers.getMapper(LikeMapper.class);
}
