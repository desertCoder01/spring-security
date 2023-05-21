package com.example.demo.security;


import com.example.demo.exception.InvalidRequestException;
import com.example.demo.model.entity.PermissionInfo;
import com.example.demo.model.entity.RoleInfo;
import com.example.demo.model.entity.UserInfo;
import com.example.demo.model.repository.PermissionRepository;
import com.example.demo.model.repository.RoleRepository;
import com.example.demo.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Received Authentication request for email :"+email);
        UserInfo userInfo = userRepository.findByEmail(email);
        if(ObjectUtils.isEmpty(userInfo)){
            throw new UsernameNotFoundException("User not found for :"+email);
        }
        if(!userInfo.isActive()){
            throw new InvalidRequestException("Account not activated for :"+email);
        }
        Optional<RoleInfo> optionalRole = roleRepository.findById(userInfo.getRole());
        List<PermissionInfo> allPermissions = permissionRepository.findAllById(userInfo.getPermissions());
        return SessionUserDetails.builder()
                .username(email)
                .role(optionalRole.get().getName())
                .permissions(allPermissions==null ? null : allPermissions.stream()
                        .map(permit -> permit.getName()).collect(Collectors.toList()))
                .password(userInfo.getPassword())
                .userId(userInfo.getId())
                .build();
    }
}
