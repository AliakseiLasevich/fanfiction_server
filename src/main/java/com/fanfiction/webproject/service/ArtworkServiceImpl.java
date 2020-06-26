package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.entity.Artwork;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.exceptions.ArtworkServiceException;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.repository.ArtworkRepository;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.fanfiction.webproject.utils.Utils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtworkServiceImpl implements ArtworkService {


    private ArtworkRepository artworkRepository;
    private UserService userService;
    private final Utils utils;

    @Autowired
    public ArtworkServiceImpl(ArtworkRepository artworkRepository, UserService userService, Utils utils) {
        this.artworkRepository = artworkRepository;
        this.userService = userService;
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

    @Transactional
    @Override
    public ArtworkDto createArtwork(ArtworkDto artworkDto) {
        Artwork artwork = ArtworkMapper.INSTANCE.dtoToEntity(artworkDto);
        artwork.setUser(userService.getUserEntityByUserId(artworkDto.getUserId()));
        artwork.setArtworkId(utils.generateRandomString(30));
        Artwork storedArtwork = artworkRepository.save(artwork);
        return ArtworkMapper.INSTANCE.entityToDto(storedArtwork);

    }
}
