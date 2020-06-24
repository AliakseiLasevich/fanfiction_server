package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.dto.UserDto;
import com.fanfiction.webproject.entity.UserEntity;
import com.fanfiction.webproject.ui.model.request.UserDetailsRequestModel;
import com.fanfiction.webproject.ui.model.response.UserRest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto requestModelToDto(UserDetailsRequestModel requestModel);

    UserRest dtoToRest(UserDto userDto);

    UserEntity dtoToEntity(UserDto userDto);

    UserDto entitytoDto(UserEntity userEntity);

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
