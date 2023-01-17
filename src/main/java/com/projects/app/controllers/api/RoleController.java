package com.projects.app.controllers.api;

import com.projects.app.models.Role;
import com.projects.app.dto.RoleDto;
import com.projects.app.repositories.RoleRepo;
import com.projects.app.services.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private RoleMapper roleMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto){
        if(roleDto.getName().toUpperCase().startsWith("ROLE_")){
            return new ResponseEntity<>("Roles name do not start with {ROLE_}! "
                    , HttpStatus.CREATED);
        }
        roleDto.setName("ROLE_"+roleDto.getName().toUpperCase());

        roleRepo.save(roleMapper.toEntity(roleDto));

        return new ResponseEntity<>("Successfully roles created! ", HttpStatus.CREATED);
    }

    @PostMapping("/roles-list")
    public ResponseEntity<?> getRoles() {

        return new ResponseEntity<>(
                this.roleRepo.findAll()
                , HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateRole(@RequestBody Role role){
        if(role.getName().toUpperCase().startsWith("ROLE_")){
            return new ResponseEntity<>("Roles name do not start with {ROLE_}! "
                    , HttpStatus.CREATED);
        }
        Role roles = roleRepo.findById(role.getId()).get();
        roles.setName("ROLE_"+role.getName().toUpperCase());
        roleRepo.save(roles);

        return new ResponseEntity<>("Successfully roles updated! ",
                HttpStatus.OK);
    }
}
