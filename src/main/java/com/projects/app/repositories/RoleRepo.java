package com.projects.app.repositories;

import com.projects.app.dto.RoleDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.app.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo  extends JpaRepository<Role, Integer>{
    @Query(value = "SELECT * FROM role WHERE id = ?1", nativeQuery = true)
    List<RoleDto> getRolesById(Integer id);

}
