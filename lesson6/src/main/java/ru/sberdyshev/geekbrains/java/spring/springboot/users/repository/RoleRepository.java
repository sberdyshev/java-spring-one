package ru.sberdyshev.geekbrains.java.spring.springboot.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.domain.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}