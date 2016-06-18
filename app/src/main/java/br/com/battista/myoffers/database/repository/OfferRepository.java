package br.com.battista.myoffers.database.repository;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.controller.OfferController;
import br.com.battista.myoffers.database.contract.MyOffersContract;
import br.com.battista.myoffers.model.Offer;

public class OfferRepository implements BaseRepository<Offer> {

    public static final String TAG_CLASSNAME = OfferController.class.getSimpleName();

    @Override
    public void save(Offer entity) {
        if (entity != null) {
            Log.i(TAG_CLASSNAME, String.format("Save to offer with id: %s.", entity.getId()));
            entity.save();
        } else {
            Log.i(TAG_CLASSNAME, "Entity can not be null!");
        }
    }

    @Override
    public Offer findById(Long id) {
        Log.i(TAG_CLASSNAME, String.format("Find offer by id: %s.", id));
        return new Select()
                .from(Offer.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public Offer findByCodeProduct(Long codeProduct) {
        Log.i(TAG_CLASSNAME, String.format("Find offer by code: %s.", codeProduct));
        return new Select()
                .from(Offer.class)
                .where("codeProduct = ?", codeProduct)
                .executeSingle();
    }

    @Override
    public void update(Offer entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Offer> findAll() {
        Log.i(TAG_CLASSNAME, "Find all offers.");
        return new Select()
                .from(Offer.class)
                .where("denounce = ?", false)
                .orderBy(MyOffersContract.OfferEntry.COLUMN_NAME_UPDATED_AT + " DESC")
                .execute();
    }

    @Override
    public void deleteAll() {
        new Delete()
                .from(Offer.class)
                .execute();
    }
}
