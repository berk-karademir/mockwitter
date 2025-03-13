package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.authority=:authority")
    Optional<Role> findByAuthority(String authority);

    @Query("SELECT r.authority, r.id, r.description FROM Role r")
    List<Object[]> getAllRoles();
}
