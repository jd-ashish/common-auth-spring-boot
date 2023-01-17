package com.projects.app;

import com.projects.app.models.Role;
import com.projects.app.commons.constants.RolesEnum;
import com.projects.app.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class AuthenticationApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;


    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {



        // creating default roles like admin and user
        try {
            List<Role> roles = new ArrayList<>();
            for (RolesEnum n : RolesEnum.values()) {
                Map.Entry<String, Integer> e = n.getBoth();
                Role role = new Role();
                role.setName(e.getKey());
                if (roleRepo.findById(e.getValue()).isEmpty()) {
                    roles.add(role);
                }
            }
            if (roles.size() > 0) {
                roleRepo.saveAll(roles);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
