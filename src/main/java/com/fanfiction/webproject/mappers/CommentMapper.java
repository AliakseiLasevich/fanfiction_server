package com.fanfiction.webproject.mappers;

import com.fanfiction.webproject.entity.Comment;
import com.fanfiction.webproject.ui.model.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(source = "userEntity.nickName", target = "userNickName")
    @Mapping(source = "userEntity.userId", target = "userId")
    CommentResponse entityToResponse(Comment comment);

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
}
