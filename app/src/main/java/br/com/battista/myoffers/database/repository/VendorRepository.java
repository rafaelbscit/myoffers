package br.com.battista.myoffers.database.repository;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.database.contract.MyOffersContract;
import br.com.battista.myoffers.model.Vendor;


public class VendorRepository implements BaseRepository<Vendor> {

    public static final String TAG_CLASSNAME = OfferController.class.getSimpleName();

    @Override
    public void save(Vendor entity) {
        if (entity != null) {
            Log.i(TAG_CLASSNAME, String.format("Save to vendor with id: %s.", entity.getId()));
            entity.save();
        } else {
            Log.i(TAG_CLASSNAME, "Entity can not be null!");
        }
    }

    @Override
    public Vendor findById(Long id) {
        Log.i(TAG_CLASSNAME, String.format("Find vendor by id: %s.", id));
        return new Select()
                .from(Vendor.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public List<Vendor> findByCodeProduct(Long codeProduct) {
        Log.i(TAG_CLASSNAME, String.format("Find vendor by code: %s.", codeProduct));
        return new Select()
                .from(Vendor.class)
                .where("codeProduct = ?", codeProduct)
                .execute();
    }

    public List<Vendor> findByCodeProductAndStateAndCity(Long codeProduct, String state, String city) {
        Log.i(TAG_CLASSNAME, String.format("Find vendor by code: %s, state: %s and city: %s.",
                codeProduct, state, city));
        return new Select()
                .from(Vendor.class)
                .where("codeProduct = ?", codeProduct)
                .where("state = ?", state)
                .where("city = ?", city)
                .orderBy(MyOffersContract.OfferEntry.COLUMN_NAME_UPDATED_AT + " DESC")
                .execute();
    }

    @Override
    public void update(Vendor entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Vendor> findAll() {
        Log.i(TAG_CLASSNAME, "Find all vendors.");
        return new Select()
                .from(Vendor.class)
                .orderBy(MyOffersContract.OfferEntry.COLUMN_NAME_UPDATED_AT + " DESC")
                .execute();
    }

    @Override
    public void deleteAll() {
        new Delete()
                .from(Vendor.class)
                .execute();
    }
}
