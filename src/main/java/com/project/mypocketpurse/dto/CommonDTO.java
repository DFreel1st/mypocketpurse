package com.project.mypocketpurse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class CommonDTO {
    private String createdBy;
    private LocalDateTime createdWhen;
}