package com.project.mypocketpurse.dto;

import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.model.Category;
import com.project.mypocketpurse.model.Label;
import com.project.mypocketpurse.model.User;
import lombok.Data;

@Data
public class ActionSearchDTO {
    private Account account;
    private Category category;
    private Label label;
//    private User user;
//    private String login;
}
