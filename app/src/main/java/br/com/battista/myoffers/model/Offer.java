package br.com.battista.myoffers.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

import br.com.battista.myoffers.database.contract.MyOffersContract;

@Table(name = MyOffersContract.OfferEntry.TABLE_NAME)
public class Offer extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_NAME, notNull = true)
    private String name;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_CATEGORY)
    private String category;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_VENDOR)
    private String vendor;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_PRICE)
    private Double price;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_BRAND)
    private String brand;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_CODE_PRODUCT, notNull = true, index = true)
    private Long codeProduct;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Offer name(String name) {
        this.name = name;
        return this;
    }

    public Offer category(String category) {
        this.category = category;
        return this;
    }

    public Offer vendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public Offer price(Double price) {
        this.price = price;
        return this;
    }

    public Offer brand(String brand) {
        this.brand = brand;
        return this;
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
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return Objects.equal(getCodeProduct(), offer.getCodeProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodeProduct());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", name)
                .add("category", category)
                .add("vendor", vendor)
                .add("price", price)
                .add("brand", brand)
                .add("codeProduct", codeProduct)
                .toString();
    }

    @Override
    public Object getPk() {
        return getId();
    }

    public Offer codeProduct(Long codeProduct) {
        this.codeProduct = codeProduct;
        return this;
    }
}
