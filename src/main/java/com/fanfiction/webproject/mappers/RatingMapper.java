package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.dto.RatingDto;
import com.fanfiction.webproject.entity.Rating;
import com.fanfiction.webproject.ui.model.response.RatingRest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingDto entityToDto(Rating rating);

    RatingRest dtoToRest(RatingDto ratingDto);

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);
}
