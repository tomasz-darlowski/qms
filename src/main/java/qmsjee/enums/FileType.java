/*
 
 
 */
package qmsjee.enums;

/**
 *
 * @author Tomek
 */
public enum FileType {

    DOCUMENT, REPORT, IMAGE;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
