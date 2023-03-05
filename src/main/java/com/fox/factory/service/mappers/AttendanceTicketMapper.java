package com.fox.factory.service.mappers;

import com.fox.factory.entities.AttendanceTicket;
import com.fox.factory.entities.dto.AttendanceTicketDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface AttendanceTicketMapper {
    AttendanceTicket toEntity(AttendanceTicketDto attendanceTicketDto);

    AttendanceTicketDto toDto(AttendanceTicket attendanceTicket);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AttendanceTicket partialUpdate(AttendanceTicketDto attendanceTicketDto, @MappingTarget AttendanceTicket attendanceTicket);
}