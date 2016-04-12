package br.com.battista.myoffers.database.repository;

import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.controller.facade.OfferFacade;
import br.com.battista.myoffers.database.contract.MyOffersContract;
import br.com.battista.myoffers.model.Offer;

public class OfferRepository implements BaseRepository<Offer> {

    public static final String TAG_CLASSNAME = OfferFacade.class.getSimpleName();

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
                .orderBy(MyOffersContract.OfferEntry.COLUMN_NAME_UPDATE_AT + " ASC")
                .execute();
    }
}