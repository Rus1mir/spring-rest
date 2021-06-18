package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    @Query(value = "SELECT r FROM Role r WHERE r.name =:name")
    Optional<Role> findRoleByName(@Param("name") String name);
}



















