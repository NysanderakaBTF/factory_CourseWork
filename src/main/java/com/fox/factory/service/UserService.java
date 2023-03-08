package com.fox.factory.service;

import com.fox.factory.entities.User;
import com.fox.factory.entities.dto.UserDetailDto;
import com.fox.factory.entities.dto.UserInCommentDto;
import com.fox.factory.entities.dto.UserInTicketDto;
import com.fox.factory.repositories.UserRepository;
import com.fox.factory.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User save(UserDetailDto userDTO) {
        return save(userMapper.toEntityDetail(userDTO));
    }

    public User getByUsername(String username) {
        return findByUsername(username).orElseThrow();
    }

    public UserDetailDto getById(Long id) {
        return userMapper.toDetailDto(userRepository.findById(id).orElse(null));
    }

    public UserDetailDto updateUserInfo(Long id, UserDetailDto dto) {
        var user = userRepository.findById(id).orElse(null);
        if (user != null)
            return userMapper.toDetailDto(
                    userRepository.save(
                            userMapper.partialUpdateDtoDetail(dto, user)
                    )
            );
        return null;
    }

    public UserInCommentDto getInfoForComment(Long id) {
        return userMapper.toInCommentDto(userRepository.findById(id).orElse(null));
    }

    public UserInTicketDto getInfoForTicket(Long id) {
        return userMapper.toInTicketDto(userRepository.findById(id).orElse(null));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
