package com.project.mypocketpurse.dto;

import com.project.mypocketpurse.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDTO
        extends CommonDTO {
    private Long id;
    private String name;
    private String description;

    public CategoryDTO(final Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
    }
}
