/*


 */
package qmsjee.enums;

/**
 *
 * @author darlotom
 */
public enum Navigation {

    GAUGES, SCHEDULER, COMPONENTS, CREATE, EDIT, VIEW, CUSTOMER, SUPPLIER;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
