package com.BekoInc.mockwitter.util;

import com.BekoInc.mockwitter.entity.user.Role;
import com.BekoInc.mockwitter.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;


    @Autowired
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        createRoleIfNotFound(UserRoleType.CASUAL, "Basic, standard user with casual access.");
        createRoleIfNotFound(UserRoleType.COMPANY, "User with company-level access. Only for corporate.");
        createRoleIfNotFound(UserRoleType.ADMIN, "Restricted role! User with admin privileges, needs admin authorization!");

        System.out.println(" ✅ Application is running successfully! ✅");
    }

    private void createRoleIfNotFound(UserRoleType userRole, String description) {
        if (!roleRepository.findByAuthority(userRole.name()).isPresent()) {
            Role role = new Role();
            role.setAuthority(userRole.name());
            role.setDescription(description);
            roleRepository.save(role);
            System.out.println("Role created: " + userRole.name() + " - with description: " + description);
        } else {
            System.out.println("Role already exists: " + userRole.name());
        }

    }


}
