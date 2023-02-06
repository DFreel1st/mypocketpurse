package com.project.mypocketpurse.dto;

import com.project.mypocketpurse.model.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDTO
        extends CommonDTO {
    private Long id;
    private RoleDTO role;
    @NotEmpty(message = "Can't be empty")
    private String login;

    @NotEmpty(message = "Can't be empty")
    @Size(min = 3, message = "Password must contain more than 3 symbol")
    private String password;
    @NotEmpty(message = "Can't be empty")
    private String firstName;
    private String middleName;
    @NotEmpty(message = "Can't be empty")
    private String lastName;
    @NotEmpty(message = "Can't be empty")
    private String phone;
    @NotEmpty(message = "Can't be empty")
    @Email
    private String email;
    @NotEmpty(message = "Can't be empty")
    private String birthday;

    public UserDTO(final User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.birthday = user.getBirthday().format(DateTimeFormatter.ISO_DATE);
        this.role = new RoleDTO(user.getRoleId());
    }
}
