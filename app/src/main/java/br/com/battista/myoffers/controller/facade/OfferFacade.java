package br.com.battista.myoffers.controller.facade;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.sqlbrite.BriteDatabase;
import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.controller.service.OfferService;
import br.com.battista.myoffers.database.repository.OfferRepository;
import br.com.battista.myoffers.model.Offer;

public class OfferFacade {

    public static final String TAG_CLASSNAME = OfferFacade.class.getSimpleName();
    private OfferService service;
    private OfferRepository repository;

    public OfferFacade() {
        service = OfferService.getInstance();
        repository = new OfferRepository();
    }

    public void loadFromServerAndSaveOffers() {
        Log.i(TAG_CLASSNAME, "Find all offers from server and stored in the app database.");
        List<Offer> offers = service.findOffers();
        BriteDatabase.Transaction transaction = ActiveAndroid.beginTransaction();
        try {
            Log.i(TAG_CLASSNAME, String.format("Save %s offers in database!", offers.size()));
            for (int count = 0; count < offers.size(); count++) {
                Offer offer = offers.get(count);
                repository.save(offer);
                if (count % 10 == 0) {
                    ActiveAndroid.setTransactionSuccessful(transaction);
                }
            }
        } finally {
            ActiveAndroid.endTransaction(transaction);
        }

    }

    public List<Offer> loadFromBatabase() {
        List<Offer> offers = repository.findAll();
        Log.i(TAG_CLASSNAME, String.format("Retrieves %s offers from database.", offers.size()));

        return offers;
    }
}
