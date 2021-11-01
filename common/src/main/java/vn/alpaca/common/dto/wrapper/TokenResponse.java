package vn.alpaca.common.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import vn.alpaca.common.dto.response.UserResponse;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"access_token", "refresh_token", "token_type", "user_info"})
public final class TokenResponse {

  @JsonProperty("access_token")
  private String token;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("token_type")
  private String type = "Bearer";

  @JsonProperty("user_info")
  private UserResponse userInfo;

  public TokenResponse(String token, String refreshToken, UserResponse userInfo) {
    this.token = token;
    this.refreshToken = refreshToken;
    this.userInfo = userInfo;
  }
}
