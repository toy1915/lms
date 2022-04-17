package toy.lms.jwt.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class TokenRequestDto {
    @ApiParam(value = "access 토큰", required = true)
    @ApiModelProperty(example = "access 토큰")
    @NotNull(message = "access 토큰 값이 필요합니다.")
    private String accessToken;

    @ApiParam(value = "refresh 토큰", required = true)
    @ApiModelProperty(example = "refresh 토큰")
    @NotNull(message = "refresh 토큰 값이 필요합니다.")
    private String refreshToken;
}
