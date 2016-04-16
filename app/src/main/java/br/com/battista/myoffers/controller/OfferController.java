package br.com.battista.myoffers.controller;


import android.database.sqlite.SQLiteException;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.sqlbrite.BriteDatabase;
import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.controller.service.OfferService;
import br.com.battista.myoffers.controller.service.VendorService;
import br.com.battista.myoffers.database.repository.OfferRepository;
import br.com.battista.myoffers.database.repository.VendorRepository;
import br.com.battista.myoffers.model.Offer;
import br.com.battista.myoffers.model.Vendor;

public class OfferController {

    public static final String TAG_CLASSNAME = OfferController.class.getSimpleName();
    private final OfferService offerService;
    private final VendorService vendorService;
    private final OfferRepository offerRepository;
    private final VendorRepository vendorRepository;

    public OfferController() {
        offerService = OfferService.getInstance();
        offerRepository = new OfferRepository();
        vendorRepository = new VendorRepository();
        vendorService = VendorService.getInstance();
    }

    public void loadFromServerAndSaveOffers() {
        Log.i(TAG_CLASSNAME, "Find all offers from server and stored in the app database.");
        List<Offer> offers = offerService.findOffers();
        BriteDatabase.Transaction transaction = ActiveAndroid.beginTransaction();
        try {
            Log.i(TAG_CLASSNAME, String.format("Save %s offers in database!", offers.size()));
            for (int count = 0; count < offers.size(); count++) {
                Offer offer = offers.get(count);
                offerRepository.save(offer);

                for (Vendor vendor : offer.getVendors()) {
                    vendorRepository.save(vendor);
                }
                if (count % 10 == 0 && count > 1) {
                    ActiveAndroid.setTransactionSuccessful(transaction);
                }
            }
            ActiveAndroid.setTransactionSuccessful(transaction);
        } finally {
            try {
                ActiveAndroid.endTransaction(transaction);
            } catch (SQLiteException e) {
                Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
                System.exit(1);
            }
        }

    }

    public Boolean loadFromServerAndSaveOffer(Long codeProduct) {
        Log.i(TAG_CLASSNAME,
                String.format("Find offer by code:%s from server and stored in the app database.",
                        codeProduct));
        Offer offer = offerService.findByCodeProduct(codeProduct);
        if (offer == null || offer.getId() == null) {
            Log.i(TAG_CLASSNAME,
                    String.format("Not found offer with code:%s in server!", codeProduct));
            return Boolean.FALSE;
        }
        saveOfferInDatabase(offer);
        return Boolean.TRUE;
    }

    public List<Offer> loadFromBatabase() {
        List<Offer> offers = offerRepository.findAll();
        Log.i(TAG_CLASSNAME, String.format("Retrieves %s offers from database.", offers.size()));

        if (offers != null) {
            Log.i(TAG_CLASSNAME, "Load all vendors to offers!");
            for (Offer offer : offers) {
                offer.setVendors(vendorRepository.findByCodeProduct(offer.getCodeProduct()));
            }
        }

        return offers;
    }

    public Offer loadFromDatabaseById(Long id) {
        Offer offer = offerRepository.findById(id);
        if (offer != null) {
            Log.i(TAG_CLASSNAME, String.format("Retrieve offer by id: %s from database.",
                    offer.getId()));

            Log.i(TAG_CLASSNAME, "Load all vendors to offer!");
            offer.setVendors(vendorRepository.findByCodeProduct(offer.getCodeProduct()));
        } else {
            Log.i(TAG_CLASSNAME, String.format("Not found offer with id: %s.", id));
        }
        return offer;
    }

    public Offer loadFromDatabaseByCodeProduct(Long codeProduct) {
        Offer offer = offerRepository.findByCodeProduct(codeProduct);
        if (offer != null) {
            Log.i(TAG_CLASSNAME, String.format("Retrieve offer by code: %s from database.",
                    offer.getId()));

            Log.i(TAG_CLASSNAME, "Load all vendors to offer!");
            offer.setVendors(vendorRepository.findByCodeProduct(offer.getCodeProduct()));
        } else {
            Log.i(TAG_CLASSNAME, String.format("Not found offer with code: %s.", codeProduct));
        }
        return offer;
    }

    public Offer createNewOfferInServerAndDatabase(Offer offer) {
        Log.i(TAG_CLASSNAME, String.format("Create new offer [%s] in server.",
                offer));
        if (offer.getVendors() != null) {
            for (Vendor vendor : offer.getVendors()) {
                vendorService.createVendor(vendor);
            }
        }
        offer = offerService.createOffer(offer);
        if (offer != null && offer.getId() != null) {
            Log.i(TAG_CLASSNAME, String.format("Save new offer with id: %s in database.",
                    offer.getId()));
            loadFromServerAndSaveOffers();
        } else {
            Log.i(TAG_CLASSNAME, String.format("Error in creating a new offer with code: %s.", offer.getCodeProduct()));
        }
        return offer;
    }

    private void saveOfferInDatabase(Offer offer) {
        BriteDatabase.Transaction transaction = ActiveAndroid.beginTransaction();
        try {
            Log.i(TAG_CLASSNAME, String.format("Save offer with id:%s in database!", offer.getId()));
            offerRepository.save(offer);
            for (Vendor vendor : offer.getVendors()) {
                vendorRepository.save(vendor);
            }
            ActiveAndroid.setTransactionSuccessful(transaction);
        } finally {
            try {
                ActiveAndroid.endTransaction(transaction);
            } catch (SQLiteException e) {
                Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
                System.exit(1);
            }
        }
    }
}
