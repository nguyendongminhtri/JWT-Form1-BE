package chinh.nguyen.demo3.service;

import chinh.nguyen.demo3.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(User user);
}
