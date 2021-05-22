package chinh.nguyen.demo3.service;

import chinh.nguyen.demo3.model.Role;
import chinh.nguyen.demo3.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
