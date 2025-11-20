package org.example.portfoliospring1.repository;

import org.example.portfoliospring1.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByNickname(String nickname);
    User findByEmail(String email);

    List<User> findAllByNickname(String nickname);
    List<User> findAllByEmail(String email);

    User findByEmailAndNickname(String email, String nickname);
    User findByEmailOrNickname(String email, String nickname);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByProviderIdAndDeleted(String providerId, Boolean deleted);
}
