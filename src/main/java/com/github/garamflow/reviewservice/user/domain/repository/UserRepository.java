package com.github.garamflow.reviewservice.user.domain.repository;

import com.github.garamflow.reviewservice.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
