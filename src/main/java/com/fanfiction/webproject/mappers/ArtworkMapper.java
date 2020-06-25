package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.entity.Artwork;
import com.fanfiction.webproject.ui.model.request.ArtworkRequestModel;
import com.fanfiction.webproject.ui.model.response.ArtworkRest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArtworkMapper {


    Artwork dtoToEntity(ArtworkDto artworkDto);

    ArtworkDto entityToDto(Artwork artwork);

    ArtworkRest dtoToRest(ArtworkDto artworkDto);

    ArtworkDto requestModelToDto(ArtworkRequestModel artworkRequestModel);

    ArtworkMapper INSTANCE = Mappers.getMapper(ArtworkMapper.class);
}
