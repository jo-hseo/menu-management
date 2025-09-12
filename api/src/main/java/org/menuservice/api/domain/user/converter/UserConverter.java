package org.menuservice.api.domain.user.converter;

import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.exception.ApiException;
import org.menuservice.api.domain.user.model.UserRegisterRequest;
import org.menuservice.api.domain.user.model.UserResponse;
import org.menuservice.db.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request) {

        return Optional.ofNullable(request)
                .map(it-> {
                    return UserEntity.builder()
                            .userId(request.getUserId())
                            .password(request.getPassword())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it-> {
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .userId(userEntity.getUserId())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }
}
