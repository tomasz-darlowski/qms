/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qmsjee.view.controlers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Tomek
 */
@Named
@ApplicationScoped
public class CommonNavigationRule {

    public String goBackToView() {
        return "view";
    }
}
