package org.springframework.webflow.samples.booking;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;

/**
 * Credit card data.
 * @author mszarlinski
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
        return MoreObjects.toStringHelper(this)
            .add("number", number)
            .add("name", name)
            .add("expiryMonth", expiryMonth)
            .add("expiryYear", expiryYear)
            .toString();
    }
}
