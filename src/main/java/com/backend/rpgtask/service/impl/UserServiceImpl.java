package com.backend.rpgtask.service.impl;

import com.backend.rpgtask.io.entity.RoleEntity;
import com.backend.rpgtask.io.entity.RoleName;
import com.backend.rpgtask.io.entity.UserEntity;
import com.backend.rpgtask.io.repositories.UserRepository;
import com.backend.rpgtask.service.UserService;
import com.backend.rpgtask.shared.UserPrincipal;
import com.backend.rpgtask.shared.Utils;
import com.backend.rpgtask.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        return UserPrincipal.build(userEntity);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new RuntimeException("Email already used");

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

        userEntity.setUserId(utils.generateUserId(10));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setEnabled(false);
        userEntity.setConfirmationToken(UUID.randomUUID().toString());

        RoleEntity userRole = new RoleEntity();
        userRole.setId(userDto.getId());
        userRole.setName(RoleName.ROLE_USER);

        Set<RoleEntity> roles = new HashSet<>();
        roles.add(userRole);
        userEntity.setRoles(roles);

        UserEntity storedUserEntity = userRepository.save(userEntity);

        return modelMapper.map(storedUserEntity, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new UsernameNotFoundException("User with ID: " + userId + " not found");

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new RuntimeException(userId);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, userDto);

        return userDto;
    }

    @Override
    public UserDto getUserByConfirmationToken(String confirmationToken) {
        UserEntity userEntity = userRepository.findByConfirmationToken(confirmationToken);

        if (userEntity == null) throw new RuntimeException(confirmationToken);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new RuntimeException(userId);

        userRepository.delete(userEntity);
    }
}
