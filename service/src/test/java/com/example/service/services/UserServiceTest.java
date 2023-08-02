package com.example.service.services;

import com.example.database.entity.Role;
import com.example.database.entity.User;
import com.example.database.repository.UserRepository;
import com.example.service.dto.PersonalInfo;
import com.example.service.dto.UserCreateDto;
import com.example.service.dto.UserReadDto;
import com.example.service.mapper.UserMapper;
import com.example.service.util.ConstContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.service.util.ConstContainer.EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreate() {
        UserCreateDto userCreateDto = new UserCreateDto(
                "John",
                "Doe",
                "Smith",
                "john.doe@example.com",
                Role.SALE_USER
        );

        User user = new User();

        UserReadDto userReadDto = new UserReadDto(
                new PersonalInfo("John", "Doe", "Smith"),
                "john.doe@example.com",
                "SALE_USER"
        );

        when(userMapper.userCreateDtoToUserMapper(userCreateDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToUserReadDto(user)).thenReturn(userReadDto);
        Optional<UserReadDto> result = userService.create(userCreateDto);

        assertEquals(Optional.of(userReadDto), result);

        verify(userMapper).userCreateDtoToUserMapper(userCreateDto);
        verify(userRepository).save(user);
        verify(userMapper).userToUserReadDto(user);
    }

    @Test
    public void testGetUsersByPage() {
        int page = 0;
        User user1 = User.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .role(Role.SALE_USER)
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("Jane")
                .surname("Smith")
                .email("jane@example.com")
                .role(Role.ADMIN)
                .build();

        List<User> users = Arrays.asList(user1, user2);

        UserReadDto userReadDto1 = new UserReadDto(
                new PersonalInfo("John", "Doe", null),
                "john@example.com",
                "SALE_USER"
        );

        UserReadDto userReadDto2 = new UserReadDto(
                new PersonalInfo("Jane", "Smith", null),
                "jane@example.com",
                "ADMIN"
        );

        List<UserReadDto> expectedUserReadDtos = Arrays.asList(userReadDto1, userReadDto2);

        Page<User> pageMock = new PageImpl<>(users);
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(pageMock);

        when(userMapper.userToUserReadDto(user1)).thenReturn(userReadDto1);
        when(userMapper.userToUserReadDto(user2)).thenReturn(userReadDto2);

        Page<UserReadDto> result = userService.getUsersByPage(page);

        assertEquals(expectedUserReadDtos, result.getContent());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(userRepository).findAll(PageRequest.of(page, ConstContainer.PAGE_SIZE, Sort.by(EMAIL)));
        verify(userMapper, times(2)).userToUserReadDto(any(User.class));
    }


    @Test
    public void testGetUsersInAlphabeticalOrder() {
        User user1 = new User(1L, "John", "Doe", "Petrovich",
                "john@example.com", Role.SALE_USER);
        User user2 = new User(2L, "Alice", "Smith", "Sergeevich",
                "alice@example.com", Role.CUSTOMER_USER);
        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll(any(Sort.class))).thenReturn(userList);

        UserReadDto userReadDto1 = new UserReadDto(
                new PersonalInfo("John", "Doe", "Petrovich"),
                "john@example.com",
                Role.SALE_USER.toString()
        );
        UserReadDto userReadDto2 = new UserReadDto(
                new PersonalInfo("Alice", "Smith", "Sergeevich"),
                "alice@example.com",
                Role.CUSTOMER_USER.toString()
        );

        when(userMapper.userToUserReadDto(user1)).thenReturn(userReadDto1);
        when(userMapper.userToUserReadDto(user2)).thenReturn(userReadDto2);

        List<UserReadDto> result = userService.getUsers();

        assertEquals(2, result.size());
        assertEquals(userReadDto1, result.get(0));
        assertEquals(userReadDto2, result.get(1));

        verify(userRepository).findAll(Sort.by(Sort.Direction.ASC, EMAIL));
        verify(userMapper, times(2)).userToUserReadDto(any(User.class));
    }
}