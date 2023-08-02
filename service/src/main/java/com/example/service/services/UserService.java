package com.example.service.services;

import com.example.database.repository.UserRepository;
import com.example.service.dto.UserCreateDto;
import com.example.service.dto.UserReadDto;
import com.example.service.mapper.UserMapper;
import com.example.service.util.ConstContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.service.util.ConstContainer.EMAIL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public Optional<UserReadDto> create(UserCreateDto userCreateDto) {
        var user = userMapper.userCreateDtoToUserMapper(userCreateDto);
         return Optional.of(userRepository.save(user))
                .map(userMapper::userToUserReadDto);
    }

    public Page<UserReadDto> getUsersByPage(Integer page) {
        var pageable = PageRequest.of(page, ConstContainer.PAGE_SIZE,
                Sort.by(EMAIL));
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserReadDto);
    }

    public List<UserReadDto> getUsers() {
        Sort sort = Sort.by(Sort.Direction.ASC, EMAIL);
        return userRepository.findAll(sort).stream()
                .map(userMapper::userToUserReadDto).toList();
    }
}
