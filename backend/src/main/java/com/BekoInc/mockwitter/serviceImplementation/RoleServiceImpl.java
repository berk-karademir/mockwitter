package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.repository.RoleRepository;
import com.BekoInc.mockwitter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Map<String, Object>> getAllRoles() {
        List<Map<String, Object>> roleList = new ArrayList<>();
        List<Object[]> roles = roleRepository.getAllRoles();  // Veritabanından authority, id ve description'ı al

        for (Object[] role : roles) {
            Map<String, Object> roleMap = new LinkedHashMap<>();  // LinkedHashMap kullanarak sıralamayı koruyoruz
            roleMap.put("id", role[1]);
            roleMap.put("authority", role[0]);
            roleMap.put("description", role[2]);  // Description'ı da ekle
            roleList.add(roleMap);
        }
        return roleList;
    }
}
