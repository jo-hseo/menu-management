package org.menuservice.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.menuservice.api.common.error.ErrorCode;
import org.menuservice.api.common.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;

    private String resultMessage;

    private String resultDescription;

    public static Result OK() {
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription("성공")
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, Throwable cause) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(cause.getLocalizedMessage())
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(description)
                .build();
    }
}
