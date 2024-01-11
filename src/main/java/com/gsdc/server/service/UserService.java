package com.gsdc.server.service;

import com.gsdc.server.entity.User;
import com.gsdc.server.exceptions.AccountAlreadyExistsException;
import com.gsdc.server.exceptions.CouldNotFindAccountException;
import com.gsdc.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IService<User> {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Page<User> findAllPageable(int size, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } throw new CouldNotFindAccountException();
    }

    @Override
    public User findByUUID(String uuid) {
        Optional<User> user = userRepository.findByKey(uuid);

        if (user.isPresent()) {
            return user.get();
        } throw new CouldNotFindAccountException();
    }


    // TODO Account update
    @Override
    public User update(User obj, Long id) {
        return null;
    }

    @Override
    public User update(User obj, String uuid) {
        return null;
    }

    @Override
    public User create(User obj) {
        Optional<User> user = userRepository.findByEmailAndUsername(obj.getEmail(), obj.getUsername());

        if (user.isEmpty()) {
            return userRepository.save(obj);
        }

        throw new AccountAlreadyExistsException(obj.getEmail(), obj.getUsername());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(String uuid) {
        userRepository.deleteByKey(uuid);
    }


    public Boolean deleteUserById(Long id) {
        delete(id);
        return true;
    }

    public User findUserByUsernameAndThrowException(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new CouldNotFindAccountException();
    }

    public User findUserByEmailAndThrowException(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        }

        throw new CouldNotFindAccountException();
    }
}
