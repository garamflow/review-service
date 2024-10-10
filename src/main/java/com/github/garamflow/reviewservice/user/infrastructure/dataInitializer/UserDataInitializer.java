package com.github.garamflow.reviewservice.user.infrastructure.dataInitializer;

import com.github.garamflow.reviewservice.user.domain.repository.UserRepository;
import com.github.garamflow.reviewservice.user.domain.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {  // 사용자 데이터가 없을 경우에만 삽입
            userRepository.save(new User("testuser1"));
            userRepository.save(new User("testuser2"));
            userRepository.save(new User("testuser3"));
            System.out.println("테스트용 사용자 데이터가 생성되었습니다.");
        }
    }
}

