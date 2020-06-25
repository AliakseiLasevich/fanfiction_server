package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.entity.ArtworkEntity;
import com.fanfiction.webproject.ui.model.request.ArtworkRequestModel;
import com.fanfiction.webproject.ui.model.response.ArtworkRest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArtworkMapper {

    ArtworkEntity dtoToEntity(ArtworkDto artworkDto);

    ArtworkDto entityToDto(ArtworkEntity artworkEntity);

    ArtworkRest dtoToRest(ArtworkDto artworkDto);

    ArtworkDto requestModelToDto(ArtworkRequestModel artworkRequestModel);

    ArtworkMapper INSTANCE = Mappers.getMapper(ArtworkMapper.class);
}
