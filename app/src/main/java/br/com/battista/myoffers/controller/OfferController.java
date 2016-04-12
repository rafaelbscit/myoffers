package br.com.battista.myoffers.controller;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.sqlbrite.BriteDatabase;
import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.controller.service.OfferService;
import br.com.battista.myoffers.database.repository.OfferRepository;
import br.com.battista.myoffers.model.Offer;

public class OfferController {

    public static final String TAG_CLASSNAME = OfferController.class.getSimpleName();
    private OfferService service;
    private OfferRepository repository;

    public OfferController() {
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

    public Boolean loadFromServerAndSaveOffer(Long codeProduct) {
        Log.i(TAG_CLASSNAME,
                String.format("Find offer by code:%s from server and stored in the app database.",
                        codeProduct));
        Offer offer = service.findByCodeProduct(codeProduct);
        if (offer == null || offer.getId() == null) {
            Log.i(TAG_CLASSNAME,
                    String.format("Not found offer with code:%s in server!", codeProduct));
            return Boolean.FALSE;
        }
        BriteDatabase.Transaction transaction = ActiveAndroid.beginTransaction();
        try {
            Log.i(TAG_CLASSNAME, String.format("Save offer with id:%s in database!", offer.getId()));
            repository.save(offer);
        } finally {
            ActiveAndroid.endTransaction(transaction);
        }
        return Boolean.TRUE;
    }

    public List<Offer> loadFromBatabase() {
        List<Offer> offers = repository.findAll();
        Log.i(TAG_CLASSNAME, String.format("Retrieves %s offers from database.", offers.size()));

        return offers;
    }

    public Offer loadFromDatabaseById(Long id) {
        Offer offer = repository.findById(id);
        if (offer != null) {
            Log.i(TAG_CLASSNAME, String.format("Retrieve offer by id: %s from database.",
                    offer.getId()));
        } else {
            Log.i(TAG_CLASSNAME, String.format("Not found offer with id: %s.", id));
        }
        return offer;
    }

    public Offer loadFromDatabaseByCodeProduct(Long codeProduct) {
        Offer offer = repository.findByCodeProduct(codeProduct);
        if (offer != null) {
            Log.i(TAG_CLASSNAME, String.format("Retrieve offer by code: %s from database.",
                    offer.getId()));
        } else {
            Log.i(TAG_CLASSNAME, String.format("Not found offer with code: %s.", codeProduct));
        }
        return offer;
    }
}
