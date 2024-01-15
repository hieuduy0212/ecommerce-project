package com.hieuduy.core.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {
    @Size(min = 3, max = 10, message = "Invalid first name (3-10 characters)!")
    private String firstName;

    @Size(min = 3, max = 10, message = "Invalid last name (3-10 characters)!")
    private String lastName;
    private String username;

    @Size(min = 5, max = 15, message = "Invalid password (5-15 characters)!")
    private String password;
    
    private String repeatPassword;

}
