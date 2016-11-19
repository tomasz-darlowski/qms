/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.enums;

/**
 *
 * @author Tomek
 */
public enum IssueType {

    BUG("bug"), TASK("task"), IMPOVEMENT("improvement");
    private String value;

    private IssueType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
