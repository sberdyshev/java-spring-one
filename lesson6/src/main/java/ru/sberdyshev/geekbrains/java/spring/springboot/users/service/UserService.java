package ru.sberdyshev.geekbrains.java.spring.springboot.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.domain.Role;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.domain.User;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.exception.NotFoundUserException;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.exception.NotValidUserException;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.repository.RoleRepository;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.repository.UserRepository;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notfound.NotFoundCodes;
import ru.sberdyshev.geekbrains.java.spring.springboot.utils.exception.notvalid.NotValidCodes;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    //todo перенести в контроллер
//    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
        log.debug("------------------------------------------");
        userRepository.deleteAll();
        roleRepository.deleteAll();
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        userRole = roleRepository.save(userRole);
        adminRole = roleRepository.save(adminRole);
        Set<Role> adminSet = new HashSet<>();
        adminSet.add(adminRole);
        Set<Role> userSet = new HashSet();
        userSet.add(userRole);
        Set<Role> adminAndUserSet = new HashSet();
        adminAndUserSet.add(userRole);
        adminAndUserSet.add(adminRole);
        User user1 = new User("user1", passwordEncoder.encode("pass1"), userSet);
        User user2 = new User("user2", passwordEncoder.encode("pass2"), userSet);
        User user3 = new User("user3", passwordEncoder.encode("pass3"), adminSet);
        User admin = new User("admin", passwordEncoder.encode("pass4"), adminAndUserSet);
        log.debug("------------------------------------------Called constructor creating user with id \"{}\", name \"{}\", password \"{}\", roles \"{}\"",
                user1.getId(),
                user1.getName(),
                user1.getPassword(),
                user1.getRoles());
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(admin);
    }

    @Transactional
    public Optional<User> insert(User user) {
        String saveUserWithUserObjectOperation = "insert user with user object";
        validateUserExceptIdAndRole(user, saveUserWithUserObjectOperation);
        //Логировать пароль нельзя, но у это учебный проект - и так сойдет. Иначе бы маскировал.
        log.debug("Called \"{}\" with id \"{}\", name \"{}\", password \"{}\", role \"{}\"",
                saveUserWithUserObjectOperation,
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getRoles());
        if (user.getId() != null) {
            Optional<User> optionalUser = userRepository.findById(user.getId());
            if (optionalUser != null && optionalUser.isPresent()) {
                throw new NotValidUserException(NotValidCodes.NOT_VALID_OBJECT_ALREADY_EXISTS,
                        "Operation - " + saveUserWithUserObjectOperation + ". User with id \"" + user.getId() + "\" already exists");
            }
        }
        User resultUser = userRepository.save(user);
        return Optional.ofNullable(resultUser);
    }

    @Transactional
    public Optional<User> update(User user) {
        String updateByUserOperation = "update user by User object";
        validateUserStrict(user, updateByUserOperation);
        log.debug("Called \"{}\" with id \"{}\", name \"{}\", password \"{}\", roles \"{}\"",
                updateByUserOperation,
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getRoles());
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser != null && optionalUser.isPresent()) {
            User resultUser = userRepository.save(user);
            return Optional.ofNullable(resultUser);
        } else {
            log.error("Called \"{}\". User with id \"{}\" wasn't found, throwing NotFoundUserException exception",
                    updateByUserOperation,
                    user.getId());
            throw new NotFoundUserException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + updateByUserOperation + ". User with id \"" + user.getId() + "\" wasn't found");
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.debug("Called getAllUsers");
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Optional<Long> userId) {
        String findUserByIdOperation = "find user by id";
        log.debug("Called \"{}\" with id \"{}\"",
                findUserByIdOperation,
                userId.get());
        if (userId == null || !userId.isPresent()) {
            log.error("Called \"{}\". ID is null, throwing NotValidUserException exception",
                    findUserByIdOperation);
            throw new NotValidUserException(NotValidCodes.NOT_VALID_OBJECT_IS_NULL,
                    "Operation - " + findUserByIdOperation + ". ID is null");
        }
        Optional<User> result = userRepository.findById(userId.get());
        if (result != null && result.isPresent()) {
            //Логировать пароль нельзя, но у это учебный проект - и так сойдет. Иначе бы маскировал.
            log.debug("Called \"{}\", found user with id \"{}\", name \"{}\", password \"{}\", roles \"{}\"",
                    findUserByIdOperation,
                    result.get().getId(),
                    result.get().getName(),
                    result.get().getPassword(),
                    result.get().getRoles());
            return result;
        } else {
            log.error("Called \"{}\". Found no users with id \"{}\", throwing exception",
                    findUserByIdOperation,
                    userId);
            throw new NotFoundUserException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + findUserByIdOperation + ". User with id \"" + userId.get() + "\" wasn't found");
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findByName(Optional<String> userName) {
        String findUserByNameOperation = "find user by name";
        if (userName == null || !userName.isPresent()) {
            log.error("Called \"{}\". Name is null, throwing NotValidUserException exception",
                    findUserByNameOperation);
            throw new NotValidUserException(NotValidCodes.NOT_VALID_OBJECT_IS_NULL,
                    "Operation - " + findUserByNameOperation + ". Name is null");
        }
        log.debug("Called \"{}\" with name \"{}\"",
                findUserByNameOperation,
                userName.get());
        Optional<User> result = userRepository.findUserByName(userName.get());
        if (result != null && result.isPresent()) {
            //Логировать пароль нельзя, но у это учебный проект - и так сойдет. Иначе бы маскировал.
            log.debug("Called \"{}\", found user with id \"{}\", name \"{}\", password \"{}\", roles \"{}\"",
                    findUserByNameOperation,
                    result.get().getId(),
                    result.get().getName(),
                    result.get().getPassword(),
                    result.get().getRoles());
            return result;
        } else {
            log.error("Called \"{}\". Found no users with name \"{}\", throwing exception",
                    findUserByNameOperation,
                    userName);
            throw new NotFoundUserException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + findUserByNameOperation + ". User with name \"" + userName.get() + "\" wasn't found");
        }
    }

    @Transactional
    public Optional<User> delete(Optional<Long> userId) {
        String deleteByIdOperation = "delete user by ID";
        log.debug("Called \"{}\" with id \"{}\"",
                deleteByIdOperation,
                userId.get());
        if (userId == null || !userId.isPresent()) {
            log.error("Called \"{}\". ID is null, throwing NotValidUserException exception",
                    deleteByIdOperation);
            throw new NotValidUserException(NotValidCodes.NOT_VALID_OBJECT_IS_NULL,
                    "Operation - " + deleteByIdOperation + ". ID is null");
        }
        Optional<User> optionalUser = userRepository.findById(userId.get());
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return optionalUser;
        } else {
            log.error("Called \"{}\". User with ID \"{}\" wasn't found, throwing NotFoundUserException exception",
                    deleteByIdOperation,
                    userId);
            throw new NotFoundUserException(NotFoundCodes.NOT_FOUND_OBJECT_IS_MISSING,
                    "Operation - " + deleteByIdOperation + ". User with id \"" + userId.get() + "\" wasn't found");
        }
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsersByPage(
            Optional<Integer> pageNumber,
            Optional<Integer> pageSize) {
        log.debug("Called getAllUsersByPage with page number \"{}\", page size\"{}\"",
                pageNumber,
                pageSize);
        Pageable page = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
        if (pageNumber != null && pageNumber.isPresent() && pageSize != null && pageSize.isPresent()) {
            page = PageRequest.of(pageNumber.get() - 1, pageSize.get());
        }
        return userRepository.findAll(page);
    }

    private void validateUserStrict(User user, String operation) {
        log.debug("User validation - strict. Operation - \"{}\"",
                operation);
        validateUserIsNotNull(user, operation);
        StringBuffer allNullFields = getAllNullFields(user);
        if (allNullFields.length() != 0) {
            log.error("Called {} with null fields \"{}\", throwing NotValidUserException exception",
                    operation,
                    allNullFields);
            throw new NotValidUserException(
                    NotValidCodes.NOT_VALID_OBJECT_FIELDS_ARE_NULL,
                    "Operation - " + operation + ". User object is invalid. Field(s) \"" + allNullFields + "\" is(are) null.");
        }
    }

    private void validateUserExceptIdAndRole(User user, String operation) {
        log.debug("User validation - except id. Operation - \"{}\"",
                operation);
        validateUserIsNotNull(user, operation);
        StringBuffer nullDataFields = getNullDataFieldsExceptRole(user);
        if (nullDataFields.length() != 0) {
            log.error("Called {} with null fields \"{}\", throwing NotValidUserException exception",
                    operation,
                    nullDataFields);
            throw new NotValidUserException(
                    NotValidCodes.NOT_VALID_OBJECT_FIELDS_ARE_NULL,
                    "Operation - " + operation + ". User object is invalid. Field(s) \"" + nullDataFields + "\" is/are null.");
        }
    }

    private void validateUserIsNotNull(User user, String operation) {
        log.debug("User validation - checking if user is null. Operation - \"{}\"", operation);
        if (user == null) {
            log.error("Called {} with null object, throwing NotValidUserException exception", operation);
            throw new NotValidUserException(
                    NotValidCodes.NOT_VALID_OBJECT_IS_NULL,
                    "Operation - " + operation + ". User is null");
        }
    }

    @NotNull
    private StringBuffer getAllNullFields(User user) {
        log.debug("User validation - checking if all fields are null");
        StringBuffer allNullFields = new StringBuffer();
        StringBuffer nullDataFields = getNullDataFieldsExceptRole(user);
        if (user.getId() == null) {
            log.debug("User validation - id is null");
            allNullFields.append("id");
            if (nullDataFields.length() != 0) {
                allNullFields.append(", ");
            }
        }
        allNullFields.append(nullDataFields);
        return allNullFields;
    }

    @NotNull
    private StringBuffer getNullDataFieldsExceptRole(User user) {
        log.debug("User validation - checking if data fields are null");
        StringBuffer nullFields = new StringBuffer();
        if (user.getName() == null) {
            log.debug("User validation - name is null");
            nullFields.append("name");
        }
        if (user.getPassword() == null) {
            log.debug("User validation - password is null");
            if (nullFields.length() != 0) {
                nullFields.append(", ");
            }
            nullFields.append("password");
        }
        return nullFields;
    }
}
