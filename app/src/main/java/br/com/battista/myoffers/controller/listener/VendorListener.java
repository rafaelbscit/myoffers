package br.com.battista.myoffers.controller.listener;

import java.util.List;

import br.com.battista.myoffers.constants.RestConstant;
import br.com.battista.myoffers.model.Vendor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VendorListener {

    @GET(RestConstant.REST_API_V1 + "vendor/")
    Call<List<Vendor>> findVendors();

    @GET(RestConstant.REST_API_V1 + "vendor/product/{codeProduct}")
    Call<List<Vendor>> findByCodeProduct(@Path("codeProduct") Long codeProduct);

    @POST(RestConstant.REST_API_V1 + "vendor/")
    Call<Vendor> createVendor(@Body Vendor vendor);
}
