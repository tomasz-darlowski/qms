/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.view.interfaces;

/**
 *
 * @author Tomek
 */
public interface IPersistable {

    public String createAndClose();

    public String createAndEdit();

    public String saveAndClose();

    public String delete();

    public void prepareData();

}
