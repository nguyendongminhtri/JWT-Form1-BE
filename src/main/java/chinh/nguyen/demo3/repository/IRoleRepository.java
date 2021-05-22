package chinh.nguyen.demo3.repository;

import chinh.nguyen.demo3.model.Role;
import chinh.nguyen.demo3.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
