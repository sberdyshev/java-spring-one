package ru.sberdyshev.geekbrains.java.spring.springboot.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.domain.Role;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.repository.RoleRepository;

import java.util.List;

@Slf4j
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        log.debug("Called getAllRoles");
        return roleRepository.findAll();
    }
}
