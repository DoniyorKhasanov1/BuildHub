package org.task.buildhub;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.task.buildhub.entity.User;
import org.task.buildhub.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User user = User.builder()
                    .username("John Doe")
                    .password(passwordEncoder.encode("passdjohn"))
                    .fullName("John Doe")
                    .phone("+998901234567")
                    .role("USER")
                    .enabled(true)
                    .build();

            userRepository.save(user);
            System.out.println("Test user created: John Doe / passdjohn");
        }
    }
}