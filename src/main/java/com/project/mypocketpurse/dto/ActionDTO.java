package com.project.mypocketpurse.dto;

import com.project.mypocketpurse.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
public class ActionDTO
        extends CommonDTO {
    private Long id;
    private Double amount;
    private ActionType actionType;
    private String dateTime;
    private Label label;
    private Account account;
    private Category category;

    public ActionDTO(final Action action) {
        this.id = action.getId();
        this.amount = action.getAmount();
        this.actionType = action.getActionType();
        this.dateTime = action.getDateTime().format(DateTimeFormatter.ISO_DATE);
        this.label = action.getLabel();
        this.account = action.getAccount();
        this.category = action.getCategory();
    }
}
