package br.com.battista.myoffers.controller.service;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.battista.myoffers.constants.HttpStatus;
import br.com.battista.myoffers.constants.RestConstant;
import br.com.battista.myoffers.controller.listener.OfferListener;
import br.com.battista.myoffers.model.Offer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OfferService {

    public static final String TAG_CLASSNAME = OfferService.class.getSimpleName();

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
        Log.i(TAG_CLASSNAME, String.format("Retrieve all offers in rest server:[%s]!",
                RestConstant.REST_API_ENDPOINT));
        List<Offer> offers = new ArrayList<Offer>();
        OfferListener listener = builder.create(OfferListener.class);
        try {
            Response<List<Offer>> response = listener.findOffers().execute();
            if (response != null && response.code() == HttpStatus.OK.value()) {
                offers = response.body();
                Log.i(TAG_CLASSNAME, String.format("Founds %s offers!", offers.size()));
            } else {
                HttpStatus httpStatus = HttpStatus.valueOf(response.code());
                Log.i(TAG_CLASSNAME, String.format("Not found offers, return to code: %s and reason: %s!",
                        httpStatus.value(), httpStatus.getReasonPhrase()));
            }
        } catch (IOException e) {
            Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
        }
        return offers;
    }
}
