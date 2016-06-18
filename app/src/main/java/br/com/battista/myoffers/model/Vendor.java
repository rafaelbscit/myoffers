package br.com.battista.myoffers.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import br.com.battista.myoffers.database.contract.MyOffersContract;

@Table(name = MyOffersContract.VendorEntry.TABLE_NAME)
public class Vendor extends BaseEntity {

    @Column(name = MyOffersContract.VendorEntry.COLUMN_NAME_VENDOR, notNull = true)
    private String vendor;

    @Column(name = MyOffersContract.VendorEntry.COLUMN_NAME_PRICE, notNull = true)
    private Double price;

    @Column(name = MyOffersContract.VendorEntry.COLUMN_NAME_CODE_PRODUCT, notNull = true)
    private Long codeProduct;

    @Column(name = MyOffersContract.VendorEntry.COLUMN_NAME_STATE, notNull = false)
    private String state;

    @Column(name = MyOffersContract.VendorEntry.COLUMN_NAME_CITY, notNull = false)
    private String city;

    @Override
    public Object getPk() {
        return getId();
    }

    public String getVendor() {
        return vendor;
    }

    public Double getPrice() {
        return price;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCodeProduct() {
        return codeProduct;
    }

    public void setCodeProduct(Long codeProduct) {
        this.codeProduct = codeProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;
        Vendor vendor1 = (Vendor) o;
        return Objects.equal(getVendor(), vendor1.getVendor()) &&
                Objects.equal(getCodeProduct(), vendor1.getCodeProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVendor(), getCodeProduct());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("vendor", vendor)
                .add("state", state)
                .add("city", city)
                .add("price", price)
                .add("codeProduct", codeProduct)
                .toString();
    }

    public Vendor vendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public Vendor price(Double price) {
        this.price = price;
        return this;
    }

    public Vendor codeProduct(Long codeProduct) {
        this.codeProduct = codeProduct;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Vendor state(String state) {
        this.state = state;
        return this;
    }

    public Vendor city(String city) {
        this.city = city;
        return this;
    }
}
