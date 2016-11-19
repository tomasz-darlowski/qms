/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */

package qmsjee.utils;

import java.util.Date;

/**
 *
 * @author darlotom
 */
public class ValidationUtils {
    public static boolean checkDateIsBetween(Date from, Date to, Date checked){
        if (checked != null && (from != null || to != null)) {
                if (from != null && checked.before(from) && to == null) {
                    return false;
                } else if (to != null && checked.after(to) && from == null) {
                    return false;
                } else if (checked.before(from) && checked.after(to)) {
                    return false;
                }
            }
        return true;
    }
}
