package com.fanfiction.webproject.service;

import com.fanfiction.webproject.dto.ArtworkDto;
import com.fanfiction.webproject.entity.ArtworkEntity;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.exceptions.ArtworkServiceException;
import com.fanfiction.webproject.mappers.ArtworkMapper;
import com.fanfiction.webproject.repository.ArtworkRepository;
import com.fanfiction.webproject.service.interfaces.ArtworkService;
import com.fanfiction.webproject.service.interfaces.UserService;
import com.fanfiction.webproject.ui.model.response.ErrorMessages;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtworkServiceImpl implements ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<ArtworkDto> findAll() {
        List<ArtworkEntity> artworkEntities = Lists.newArrayList(artworkRepository.findAll());
        if (artworkEntities.size() == 0) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORDS_IN_BASE.getErrorMessage());
        }
        return artworkEntities.stream()
                .map(ArtworkMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }


    @Override
    public ArtworkDto findById(String artworkId) {
        ArtworkEntity artworkEntity = artworkRepository.findByArtworkId(artworkId);
        if (artworkEntity == null) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        return null;
    }

    @Override
    public List<ArtworkDto> findByUserId(String userId) {
        UserEntity userEntity = userService.getUserEntityByUserId(userId);
        if (userEntity == null) {
            throw new ArtworkServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        List<ArtworkEntity> artworkEntities = userEntity.getArtworkEntities();
        return artworkEntities.stream()
                .map(ArtworkMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
    }
}
