package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.dto.ArtworkPreviewPageDto;
import com.fanfiction.webproject.entity.*;
import com.fanfiction.webproject.exceptions.ArtworkServiceException;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.repository.ArtworkRepository;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.service.interfaces.GenreService;
import com.fanfiction.webproject.service.interfaces.TagService;
import com.fanfiction.webproject.service.interfaces.UserService;
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
    private UserService userService;
    private TagService tagService;
    private GenreService genreService;
    private final Utils utils;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository artworkRepository, UserService userService, Utils utils, TagService tagService, GenreService genreService) {
        this.artworkRepository = artworkRepository;
        this.userService = userService;
        this.tagService = tagService;
        this.genreService = genreService;
        this.utils = utils;
    }

    @Override
    public List<ArtworkDto> findAll() {
        List<Artwork> artworkEntities = Lists.newArrayList(artworkRepository.findAll());
        if (artworkEntities.size() == 0) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORDS_IN_BASE.getErrorMessage());
        }
        return artworkEntities.stream()
                .map(ArtworkMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public ArtworkDto findById(String artworkId) {
        Artwork artwork = artworkRepository.findByArtworkId(artworkId);
        if (artwork == null) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        return ArtworkMapper.INSTANCE.entityToDto(artwork);
    }

    @Override
    public List<ArtworkDto> findByUserId(String userId) {
        UserEntity userEntity = userService.getUserEntityByUserId(userId);
        if (userEntity == null) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        List<Artwork> artworkEntities = userEntity.getArtworkEntities();
        return artworkEntities.stream()
                .map(ArtworkMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArtworkPreviewPageDto getArtworksPreviewPageByUserId(String userId, int page, int limit) {
        UserEntity userEntity = userService.getUserEntityByUserId(userId);
        if (userEntity == null) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        if (page > 0) {
            page = page - 1;
        }

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Artwork> artworkPage = artworkRepository.findByUserIdOrderByCreationDate(userEntity.getId(), pageableRequest);
        List<Artwork> artworkEntities = artworkPage.getContent();
        List<ArtworkDto> currentPage = artworkEntities.stream()
                .map(ArtworkMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());

        return new ArtworkPreviewPageDto(currentPage, artworkPage.getTotalPages());
    }

    @Transactional
    @Override
    public ArtworkDto createArtwork(ArtworkDto artworkDto) {
        Artwork artwork = ArtworkMapper.INSTANCE.dtoToEntity(artworkDto);
        artwork.setUser(userService.getUserEntityByUserId(artworkDto.getUserId()));
        artwork.setArtworkId(utils.generateRandomString(30));
        artwork.setGenre(genreService.findOrSave(artworkDto.getGenre().getName()));
        List<Tag> tags = artworkDto.getTags().stream().map(tag -> tagService.findOrSave(tag.getName())).collect(Collectors.toList());
        artwork.setTags(tags);
        artwork.setCreationDate(LocalDateTime.now());
        Artwork storedArtwork = artworkRepository.save(artwork);
        return ArtworkMapper.INSTANCE.entityToDto(storedArtwork);
    }

    @Override
    public ArtworkPreviewPageDto getArtworksPreviewPage(int page, int limit) {
        if (page > 0) {
            page = page - 1;
        }
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Artwork> artworkPage = artworkRepository.findAllByOrderByCreationDateDesc(pageableRequest);
        List<Artwork> artworkEntities = artworkPage.getContent();
        List<ArtworkDto> currentPage = artworkEntities.stream()
                .map(ArtworkMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
        return new ArtworkPreviewPageDto(currentPage, artworkPage.getTotalPages());
    }

    @Override
    public Artwork findArtworkEntityByArtworkId(String artworkId) {
        return artworkRepository.findByArtworkId(artworkId);
    }

    @Override
    public ArtworkDto findByComment(Comment comment) {
        return ArtworkMapper.INSTANCE.entityToDto(artworkRepository.findByCommentsContains(comment));
    }

    @Override
    public ArtworkDto findByChapter(Chapter chapter) {
        return ArtworkMapper.INSTANCE.entityToDto(artworkRepository.findByChaptersContains(chapter));
    }
}
