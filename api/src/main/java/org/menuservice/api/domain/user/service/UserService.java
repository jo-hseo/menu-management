package org.menuservice.api.domain.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.error.UserErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.menuservice.db.user.UserEntity;
import org.menuservice.db.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity register(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it-> {
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }

    public UserEntity login(String userId, String password) {
        var entity = getUser(userId, password);
        return entity;
    }

    public UserEntity getUser(String userId, String password) {
        return userRepository.findByUserIdAndPassword(userId, password)
                .orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
