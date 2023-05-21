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
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("Received Authentication request for id :"+userId);
        Optional<UserInfo> optionalUserInfo = userRepository.findById(userId);
        if(ObjectUtils.isEmpty(optionalUserInfo)){
            throw new UsernameNotFoundException("User not found for :"+userId);
        }
        UserInfo userInfo = optionalUserInfo.get();
        if(!userInfo.isActive()){
            throw new InvalidRequestException("Account not activated for :"+userId);
        }
        Optional<RoleInfo> optionalRole = roleRepository.findById(userInfo.getRole());
        List<PermissionInfo> allPermissions = permissionRepository.findAllById(userInfo.getPermissions());
        return SessionUserDetails.builder()
                .username(userInfo.getEmail())
                .role(optionalRole.get().getName())
                .permissions(allPermissions==null ? null : allPermissions.stream()
                        .map(permit -> permit.getName()).collect(Collectors.toList()))
                .password(userInfo.getPassword())
                .userId(userInfo.getId())
                .build();
    }
}
