package com.fleetrentaljdbcdaosample.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {

    private String lastName;
    private String firstName;
    private String emailAddress;
    private String dayTimePhone;
    private String eveningTimePhone;

    /**
     *
     */
    public Customer() {

        // TODO Auto-generated constructor stub
    }

    /**
     * @param lastName
     * @param firstName
     * @param emailAddress
     */
    public Customer(String lastName, String firstName, String emailAddress, String dayTimePhone, String eveningTimePhone) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.dayTimePhone = dayTimePhone;
        this.eveningTimePhone = eveningTimePhone;
    }

    /**
     * @param emailAddress The emailAddress to set.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return Returns the dayTimePhone.
     */
    public String getDayTimePhone() {
        return dayTimePhone;
    }

    /**
     * @return Returns the eveningTimePhone.
     */
    public String getEveningTimePhone() {
        return eveningTimePhone;
    }

    /**
     * @param firstName The firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return Returns the emailAddress.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @return Returns the firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }
    
        public boolean validate() {
    if (lastName == null) {
        return false;
    }
    if (firstName == null) {
        return false;
    }
    if (emailAddress == null) {
        return false;
    }
    if (dayTimePhone == null) {
        return false;
    }
    if (eveningTimePhone == null) {
        return false;
    }

    return true;
}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.lastName);
        hash = 89 * hash + Objects.hashCode(this.firstName);
        hash = 89 * hash + Objects.hashCode(this.emailAddress);
        hash = 89 * hash + Objects.hashCode(this.dayTimePhone);
        hash = 89 * hash + Objects.hashCode(this.eveningTimePhone);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.emailAddress, other.emailAddress)) {
            return false;
        }
        if (!Objects.equals(this.dayTimePhone, other.dayTimePhone)) {
            return false;
        }
        return true;
    }


    public String toString() {
        StringBuffer strBfr = new StringBuffer();
        strBfr.append("\nlastname :");
        strBfr.append(lastName);
        strBfr.append("\nfirstname :");
        strBfr.append(firstName);
        strBfr.append("\nemail address :");
        strBfr.append(emailAddress);
        strBfr.append("\nday time phone :");
        strBfr.append(dayTimePhone);
        strBfr.append("\nevening Phone :");
        strBfr.append(eveningTimePhone);

        return strBfr.toString();
    }

} // end class Customer
