package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.entity.Artwork;
import com.fanfiction.webproject.entity.Chapter;
import com.fanfiction.webproject.entity.Genre;
import com.fanfiction.webproject.entity.Tag;
import com.fanfiction.webproject.ui.model.request.ArtworkRequestModel;
import com.fanfiction.webproject.ui.model.request.ChapterRequestModel;
import com.fanfiction.webproject.ui.model.response.ArtworkPreviewRest;
import com.fanfiction.webproject.ui.model.response.ArtworkRest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArtworkPreviewMapper {


    ArtworkPreviewRest dtoToArtworkPreviewRest(ArtworkDto artworkDto);

    ArtworkPreviewMapper INSTANCE = Mappers.getMapper(ArtworkPreviewMapper.class);
}
