package com.example.service.mapper;

import com.example.database.entity.User;
import com.example.service.dto.UserCreateDto;
import com.example.service.dto.UserReadDto;
import com.example.service.util.ConstContainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = ConstContainer.SPRING)
public interface UserMapper {
    @Mapping(source = ConstContainer.NAME, target = ConstContainer.PERSONAL_INFO + ConstContainer.NAME)
    @Mapping(source = ConstContainer.SURNAME, target = ConstContainer.PERSONAL_INFO + ConstContainer.SURNAME)
    @Mapping(source = ConstContainer.PATRONYMIC, target = ConstContainer.PERSONAL_INFO + ConstContainer.PATRONYMIC)
    UserReadDto userToUserReadDto(User user);

    User userCreateDtoToUserMapper(UserCreateDto userCreateDto);
}
