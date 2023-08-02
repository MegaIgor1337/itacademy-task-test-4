package com.example.database.repository;

import com.example.database.TestConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = TestConfig.class)
@Transactional
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    public void findByEmailTest() {
        String email = "alice@example.com";
        var result = userRepository.findAllByEmail(email);
        assertTrue(result.isPresent());
        assertThat(result.get().getName()).isEqualTo("Alice");
    }

    @Test
    public void findByEmailNotTest() {
        String email = "johafsdgrfasdawdn.doe@example.com";
        var result = userRepository.findAllByEmail(email);
        assertThat(result).isEmpty();
    }

    @Test
    public void findAllByPageableTest() {
        var pageable = PageRequest.of(1, 2, Sort.by("email"));
        var result = userRepository.findAll(pageable);
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).getEmail())
                .isEqualTo("emily@example.com");
    }

    @Test
    public void findAllSortTest() {
        Sort sort = Sort.by(Sort.Direction.ASC, "email");
        var result = userRepository.findAll(sort);
        assertThat(result).hasSize(10);
        assertThat(result.get(5).getName()).isEqualTo("John");
    }
}