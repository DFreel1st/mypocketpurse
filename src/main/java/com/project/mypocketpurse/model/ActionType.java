package com.project.mypocketpurse.model;

public enum ActionType {
    INCOME("Revenue"),
    EXPENSE("Expense");
//    TRANSFER("перевод");

    private final String actionTypeText;

    ActionType(String actionTypeText) {
        this.actionTypeText = actionTypeText;
    }

    public String getActionTypeText() {
        return this.actionTypeText;
    }
}