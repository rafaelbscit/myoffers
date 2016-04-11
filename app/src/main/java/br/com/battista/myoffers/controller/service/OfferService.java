package br.com.battista.myoffers.controller.service;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.battista.myoffers.constants.RestConstant;
import br.com.battista.myoffers.controller.listener.OfferListener;
import br.com.battista.myoffers.model.Offer;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OfferService {

    private Retrofit builder;
    private static OfferService service;

    private OfferService() {
        builder = new Retrofit.Builder()
                .baseUrl(RestConstant.REST_API_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }


    public static OfferService getInstance() {
        if (service == null) {
            service = new OfferService();
        }
        return service;
    }

    public List<Offer> findOffers() {

        List<Offer> offers = new ArrayList<Offer>();
        OfferListener listener = builder.create(OfferListener.class);
        try {
            offers = listener.findOffers().execute().body();
            Log.i("Offer", offers.toString());
        } catch (IOException e) {
            Log.e("Error", e.getLocalizedMessage(), e);
        }
        return offers;
    }
}
