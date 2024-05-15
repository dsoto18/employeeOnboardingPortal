package com.cooksys.groupfinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserLoginDto {

    String email;
    String password;
}
