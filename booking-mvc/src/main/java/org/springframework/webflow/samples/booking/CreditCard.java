package org.springframework.webflow.samples.booking;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author mszarlinski on 2016-06-07.
 */
@Embeddable
public class CreditCard implements Serializable {

    private String number;

    private String name;

    private int expiryMonth;

    private int expiryYear;

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreditCard{");
        sb.append("number='").append(number).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", expiryMonth=").append(expiryMonth);
        sb.append(", expiryYear=").append(expiryYear);
        sb.append('}');
        return sb.toString();
    }
}