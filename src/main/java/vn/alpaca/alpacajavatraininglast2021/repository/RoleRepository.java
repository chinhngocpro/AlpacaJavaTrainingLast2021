package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;

public interface RoleRepository extends
        JpaRepository<Role, Integer> {

    @Query("SELECT role FROM Role role LEFT JOIN role.authorities")
    Page<Role> findAllFetch(Pageable pageable);
}
