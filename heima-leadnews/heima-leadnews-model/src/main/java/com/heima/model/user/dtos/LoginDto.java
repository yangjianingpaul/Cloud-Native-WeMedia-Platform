package com.heima.model.user.dtos;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDto {

    /**
     * Phone number
     */
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    /**
     * password
     */
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
