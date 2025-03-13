package com.BekoInc.mockwitter.controller;


import com.BekoInc.mockwitter.service.RoleService;
import com.BekoInc.mockwitter.util.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Map<String, Object>> getAllRolesAsList() {
        return roleService.getAllRoles(); // Return the list of maps (id + authority)
    }


}
