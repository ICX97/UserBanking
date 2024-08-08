package com.icx97.UserBanking.service;

import com.icx97.UserBanking.dto.UserDTO;
import com.icx97.UserBanking.exception.CustomException;
import com.icx97.UserBanking.mapper.UserMapper;
import com.icx97.UserBanking.model.User;
import com.icx97.UserBanking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserDTO createUser(UserDTO userDTO) {
        logger.info("Creating User: {}", userDTO.getEmail());
        User user = userMapper.userDTOToUser(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all Users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        logger.info("Fetching User with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User with id: " + id + " does not exist"));
        return userMapper.userToUserDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        logger.info("Updating User with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User with id: " + id + " does not exist"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        User updatedUser = userRepository.save(user);
        return userMapper.userToUserDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        logger.info("Deleting User with id: {}", id);
        userRepository.deleteById(id);
    }
}