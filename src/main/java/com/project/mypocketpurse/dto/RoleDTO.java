package com.project.mypocketpurse.dto;

import com.project.mypocketpurse.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RoleDTO
        extends CommonDTO {
    private Long id;
    private String name;
    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
