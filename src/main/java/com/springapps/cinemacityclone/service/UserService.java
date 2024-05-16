package com.springapps.cinemacityclone.service;

import com.springapps.cinemacityclone.dto.AuthRequestDTO;
import com.springapps.cinemacityclone.model.User;
import com.springapps.cinemacityclone.model.RoleType;
import com.springapps.cinemacityclone.model.Role;
import com.springapps.cinemacityclone.repository.RoleRepository;
import com.springapps.cinemacityclone.repository.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private Keycloak keycloak;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, Keycloak keycloak) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.keycloak = keycloak;
    }

    @Transactional
    public User register (AuthRequestDTO authRequestDTO){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(authRequestDTO.getUsername());
        userRepresentation.setCredentials(Collections.singletonList(createPasswordCredentials(authRequestDTO.getPassword())));
        userRepresentation.setEnabled(true);
        keycloak.realm("master").users().create(userRepresentation);

        // Fetch Keycloak ID
        String keycloakId = keycloak.realm("master").users().search(authRequestDTO.getUsername()).get(0).getId();

        // Create user in local DB
        User user = new User();
        user.setName(authRequestDTO.getUsername());
        Role role = roleRepository.findByRoleType(RoleType.ROLE_USER);
        user.getRoles().add(role);
        role.getUsers().add(user);
        return userRepository.save(user);
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        passwordCredentials.setTemporary(false);
        return passwordCredentials;
    }
}
