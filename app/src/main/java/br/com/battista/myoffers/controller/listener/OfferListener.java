package br.com.battista.myoffers.controller.listener;

import java.util.List;

import br.com.battista.myoffers.constants.RestConstant;
import br.com.battista.myoffers.model.Offer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OfferListener {

    @GET(RestConstant.REST_API_V1 + "offer/")
    Call<List<Offer>> findOffers();

    @GET(RestConstant.REST_API_V1 + "offer/product/{codeProduct}")
    Call<Offer> findByCodeProduct(@Path("codeProduct") Long codeProduct);

    @POST(RestConstant.REST_API_V1 + "offer/")
    Call<Offer> createOffer(@Body Offer offer);
}
