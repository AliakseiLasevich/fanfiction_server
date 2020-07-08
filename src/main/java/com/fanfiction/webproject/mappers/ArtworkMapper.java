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
public interface ArtworkMapper {


    Artwork dtoToEntity(ArtworkDto artworkDto);

    @Mapping(source = "user.userId", target = "authorId")
    @Mapping(source = "user.firstName", target = "authorName")
    ArtworkDto entityToDto(Artwork artwork);

    ArtworkRest dtoToArtworkRest(ArtworkDto artworkDto);

    ArtworkDto requestModelToDto(ArtworkRequestModel artworkRequestModel);

    @Mapping(source = "index", target = "chapterNumber")
    @Mapping(source = "imgUrl", target = "imageUrl")
    Chapter chapterRequestToChapterEntity(ChapterRequestModel chapterRequestModel);

    @Mapping(source = "tag", target = "name")
    Tag tagStringToEntity(String tag);

    @Mapping(source = "tag", target = "name")
    Genre genreStringToEntity(String tag);

//    @Mapping(source = "", target = "")
    ArtworkPreviewRest dtoToArtworkCardRest(ArtworkDto artworkDto);

    ArtworkMapper INSTANCE = Mappers.getMapper(ArtworkMapper.class);
}
