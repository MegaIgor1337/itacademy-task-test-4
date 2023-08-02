package com.example.controller.rest;

import com.example.service.dto.UserCreateDto;
import com.example.service.dto.UserReadDto;
import com.example.service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.service.util.ConstContainer.*;

@RequestMapping(USERS_LINK)
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserReadDto> addUser(@Valid @RequestBody UserCreateDto userCreateDto,
                                               BindingResult bindingResult) {
        var userReadDto = userService.create(userCreateDto);
        return userReadDto.map(readDto -> ResponseEntity.status(HttpStatus.CREATED)
                .body(readDto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(BY_PAGE)
    public ResponseEntity<Page<UserReadDto>> getUsersByPage(@RequestParam(value = PAGE,
            defaultValue = ZERO) Integer page) {
        Page<UserReadDto> users = userService.getUsersByPage(page);
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<UserReadDto>> getUsers() {
        var users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

}
