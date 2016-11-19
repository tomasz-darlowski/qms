/*
 
 
 */
package qmsjee.enums;

/**
 *
 * @author darlotom
 */
public enum EventStyle {

    HIGH, MEDIUM, LOW, OLDHIGH, OLDMEDIUM, OLDLOW, GAUAGE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
