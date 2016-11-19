/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.enums;

/**
 *
 * @author darlotom
 */
public enum IssueStatus {

    OPEN("Open"), REOPENED("Reopened"), RESOLVED("Resolved"), REJECTED("Rejected"), INPROGRESS("In progress"), CLOSED("Closed");

    private String value;
    
    private IssueStatus(String value) {
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
