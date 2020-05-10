package ru.sberdyshev.geekbrains.java.spring.springboot.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(String userName);
}
