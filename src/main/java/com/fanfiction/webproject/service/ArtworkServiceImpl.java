package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.dto.ArtworkPreviewPageDto;
import com.fanfiction.webproject.entity.*;
import com.fanfiction.webproject.exceptions.ArtworkServiceException;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.repository.ArtworkRepository;
import com.fanfiction.webproject.service.interfaces.*;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.utils.Utils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtworkServiceImpl implements ArtworkService {


    private ArtworkRepository artworkRepository;
    private ChapterService chapterService;
    private UserService userService;
    private TagService tagService;
    private GenreService genreService;
    private final Utils utils;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository artworkRepository, UserService userService, Utils utils, TagService tagService, GenreService genreService, ChapterService chapterService) {
        this.artworkRepository = artworkRepository;
        this.chapterService = chapterService;
        this.userService = userService;
        this.tagService = tagService;
        this.genreService = genreService;
        this.utils = utils;
    }

    @Override
    public List<ArtworkDto> findAll() {
        List<Artwork> artworkEntities = Lists.newArrayList(artworkRepository.findByActiveTrue());
        if (artworkEntities.size() == 0) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORDS_IN_BASE.getErrorMessage());
        }
        return mapEntitiesToDtos(artworkEntities);
    }

    private List<ArtworkDto> mapEntitiesToDtos(List<Artwork> artworkEntities) {
        return artworkEntities.stream()
                .map(ArtworkMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArtworkDto findById(String artworkId) {
        Artwork artwork = artworkRepository.findByArtworkId(artworkId);
        checkArtworkActive(artwork);
        checkArtworkExist(artwork);
        return ArtworkMapper.INSTANCE.entityToDto(artwork);
    }

    @Override
    public List<ArtworkDto> findByUserId(String userId) {
        UserEntity userEntity = getUserEntity(userId);
        List<Artwork> artworkEntities = artworkRepository.findByActiveTrueAndUser(userEntity);
        return mapEntitiesToDtos(artworkEntities);
    }

    private UserEntity getUserEntity(String userId) {
        return userService.getUserEntityByUserId(userId);
    }

    @Override
    public ArtworkPreviewPageDto getArtworksPreviewPageByUserId(String userId, int page, int limit) {
        UserEntity userEntity = getUserEntity(userId);
        Pageable pageableRequest = getPageableRequest(page, limit);
        Page<Artwork> artworkPage = artworkRepository.findArtworksByActiveTrueAndUserIdOrderByCreationDateDesc(userEntity.getId(), pageableRequest);
        List<ArtworkDto> currentPage = getArtworkDtosPage(artworkPage);
        return new ArtworkPreviewPageDto(currentPage, artworkPage.getTotalPages());
    }

    private Pageable getPageableRequest(int page, int limit) {
        if (page > 0) {
            page = page - 1;
        }
        return PageRequest.of(page, limit);
    }


    @Override
    public ArtworkPreviewPageDto getArtworksPreviewPage(int page, int limit) {
        Pageable pageableRequest = getPageableRequest(page, limit);
        Page<Artwork> artworkPage = artworkRepository.findByActiveTrueOrderByCreationDateDesc(pageableRequest);
        List<ArtworkDto> currentPage = getArtworkDtosPage(artworkPage);
        return new ArtworkPreviewPageDto(currentPage, artworkPage.getTotalPages());
    }

    private List<ArtworkDto> getArtworkDtosPage(Page<Artwork> artworkPage) {
        List<Artwork> artworkEntities = artworkPage.getContent();
        return mapEntitiesToDtos(artworkEntities);
    }

    @Transactional
    @Override
    public ArtworkDto createArtwork(ArtworkDto artworkDto) {
        Artwork artwork = ArtworkMapper.INSTANCE.dtoToEntity(artworkDto);
        artwork.setCreationDate(LocalDateTime.now());
        artwork.setUser(userService.getUserEntityByUserId(artworkDto.getUserId()));
        artwork.setArtworkId(utils.generateRandomString(30));
        artwork.setGenre(genreService.findOrSave(artworkDto.getGenre().getName()));
        List<Tag> tags = artworkDto.getTags().stream().map(tag -> tagService.findOrSave(tag.getName())).collect(Collectors.toList());
        artwork.setTags(tags);
        Artwork storedArtwork = artworkRepository.save(artwork);
        return ArtworkMapper.INSTANCE.entityToDto(storedArtwork);
    }

    @Override
    public ArtworkDto update(ArtworkDto artworkDto, String artworkId) {
        Artwork artwork = artworkRepository.findByArtworkId(artworkId);
        checkArtworkExist(artwork);
        artwork.setName(artworkDto.getName());
        artwork.setSummary(artworkDto.getSummary());


        List<Chapter> edited = chapterService.updateChapters(artworkId, artworkDto.getChapters());

        artwork.setChapters(edited);
        artwork.setGenre(genreService.findOrSave(artworkDto.getGenre().getName()));
        List<Tag> tags = artworkDto.getTags().stream().map(tag -> tagService.findOrSave(tag.getName())).collect(Collectors.toList());
        artwork.setTags(tags);
        Artwork updatedArtwork = artworkRepository.save(artwork);
        return ArtworkMapper.INSTANCE.entityToDto(updatedArtwork);
    }

    @Override
    public void deleteArtwork(String artworkId) {
        Artwork artwork = artworkRepository.findByArtworkId(artworkId);
        checkArtworkExist(artwork);
        artwork.setActive(false);
        artworkRepository.save(artwork);
    }

    private void checkArtworkExist(Artwork artwork) {
        if (artwork == null) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
    }

    @Override
    public Artwork findArtworkEntityByArtworkId(String artworkId) {
        return artworkRepository.findByArtworkId(artworkId);
    }

    @Override
    public ArtworkDto findByComment(Comment comment) {
        Artwork artwork = artworkRepository.findByCommentsContains(comment);
        checkArtworkActive(artwork);
        return ArtworkMapper.INSTANCE.entityToDto(artwork);
    }

    private void checkArtworkActive(Artwork artwork) {
        if (!artwork.getActive()) {
            throw new ArtworkServiceException(ErrorMessages.RECORD_WAS_DELETED.getErrorMessage());
        }
    }

    @Override
    public ArtworkDto findByChapter(Chapter chapter) {
        Artwork artwork = artworkRepository.findByChaptersContains(chapter);
        checkArtworkActive(artwork);
        return ArtworkMapper.INSTANCE.entityToDto(artwork);
    }

    @Override
    public List<ArtworkDto> findTopOrderByAvg(int limit) {
        List<Artwork> artworks = artworkRepository.findTopOrderByAvg(limit);
        return artworks.stream().map(ArtworkMapper.INSTANCE::entityToDto).collect(Collectors.toList());
    }
}
