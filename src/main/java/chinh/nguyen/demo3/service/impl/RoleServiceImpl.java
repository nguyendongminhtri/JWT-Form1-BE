package chinh.nguyen.demo3.service.impl;

import chinh.nguyen.demo3.model.Role;
import chinh.nguyen.demo3.model.RoleName;
import chinh.nguyen.demo3.repository.IRoleRepository;
import chinh.nguyen.demo3.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
