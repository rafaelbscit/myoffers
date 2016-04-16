package br.com.battista.myoffers.model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;

import br.com.battista.myoffers.database.contract.MyOffersContract;

@Table(name = MyOffersContract.OfferEntry.TABLE_NAME)
public class Offer extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_NAME, notNull = true)
    private String name;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_CATEGORY, notNull = true)
    private String category;

    private List<Vendor> vendors;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_AVERAGE_PRICE, notNull = false)
    private Double averagePrice;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_BRAND, notNull = false)
    private String brand;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_REVISE, notNull = true)
    private Boolean revise = Boolean.FALSE;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_DENOUNCE, notNull = true)
    private Boolean denounce = Boolean.FALSE;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_CODE_PRODUCT, notNull = true)
    private Long codeProduct;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }


    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
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

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void loadVendors() {
        vendors = getMany(Vendor.class, MyOffersContract.VendorEntry.COLUMN_NAME_VENDOR);
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public Boolean getRevise() {
        return revise;
    }

    public void setRevise(Boolean revise) {
        this.revise = revise;
    }

    public Boolean getDenounce() {
        return denounce;
    }

    public void setDenounce(Boolean denounce) {
        this.denounce = denounce;
    }


    public Offer name(String name) {
        this.name = name;
        return this;
    }

    public Offer category(String category) {
        this.category = category;
        return this;
    }

    public Offer averagePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
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
                .add("vendors", vendors)
                .add("averagePrice", averagePrice)
                .add("brand", brand)
                .add("revise", revise)
                .add("denounce", denounce)
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

    public Offer revise(Boolean revise) {
        this.revise = revise;
        return this;
    }

    public Offer denounce(Boolean denounce) {
        this.denounce = denounce;
        return this;
    }

    public Offer vendors(List<Vendor> vendors) {
        this.vendors = vendors;
        return this;
    }
}
