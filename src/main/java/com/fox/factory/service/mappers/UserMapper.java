package com.fox.factory.service.mappers;

import com.fox.factory.entities.User;
import com.fox.factory.entities.dto.UserDetailDto;
import com.fox.factory.entities.dto.UserInOrderDto;
import com.fox.factory.entities.dto.UserInCommentDto;
import com.fox.factory.entities.dto.UserInTicketDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CommentMapper.class, OrderMapper.class, AttendanceTicketMapper.class})
public interface UserMapper {
    User toEntityInTicket(UserInTicketDto userInTicketDto);

    UserInTicketDto toInTicketDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdateForTicket(UserInTicketDto userInTicketDto, @MappingTarget User user);


    User toEntityInComment(UserInCommentDto userInCommentDto);

    UserInCommentDto toInCommentDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdateForComment(UserInCommentDto userInCommentDto, @MappingTarget User user);


    User toEntityDetail(UserDetailDto userDetailDto);

    UserDetailDto toDetailDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdateDtoDetail(UserDetailDto userDetailDto, @MappingTarget User user);



    User toEntity1(UserInOrderDto userInOrderDto);

    UserInOrderDto toDto1(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate1(UserInOrderDto userInOrderDto, @MappingTarget User user);
}