package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.dto.LikeDto;
import com.fanfiction.webproject.entity.Like;
import com.fanfiction.webproject.ui.model.response.LikeRest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(source = "chapter.chapterNumber", target = "chapterNumber")
    LikeDto entityToDto(Like like);

    LikeRest dtoToRest(LikeDto likeDto);

    LikeMapper INSTANCE = Mappers.getMapper(LikeMapper.class);
}
