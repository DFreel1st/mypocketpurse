package com.project.mypocketpurse.dto;

import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.model.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO
        extends CommonDTO {
    private Long id;
    private String name;
    private Double amount;
    private String description;
    private CurrencyType currencyType;

    public AccountDTO(final Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.amount = account.getAmount();
        this.description = account.getDescription();
        this.currencyType = account.getCurrencyType();
    }
}