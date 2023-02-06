package com.project.mypocketpurse.dto;

import com.project.mypocketpurse.model.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Setter
@Getter
public class LabelDTO
        extends CommonDTO {
    private Long id;
    private String name;
    private String description;

    private String fd;
    private String td;

    public LabelDTO(final Label label) {
        this.id = label.getId();
        this.name = label.getName();
        this.description = label.getDescription();
//        this.fd = label.getFd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        this.td = label.getTd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
