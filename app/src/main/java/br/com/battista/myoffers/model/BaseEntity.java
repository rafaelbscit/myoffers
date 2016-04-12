package br.com.battista.myoffers.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

import br.com.battista.myoffers.constants.EntityConstant;
import br.com.battista.myoffers.database.contract.MyOffersContract;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntity extends Model implements Serializable {

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_CREATED_AT, notNull = true)
    private Date createdAt;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_UPDATED_AT, notNull = true, index = true)
    private Date updatedAt;

    @Column(name = MyOffersContract.OfferEntry.COLUMN_NAME_VERSION, notNull = true, index = true)
    private Long version;

    public abstract Object getPk();

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BaseEntity version(final Long version) {
        this.version = version;
        return this;
    }

    public void initEntity() {
        createdAt = new Date();
        updatedAt = createdAt;
        version = EntityConstant.DEFAULT_VERSION;
    }

}

