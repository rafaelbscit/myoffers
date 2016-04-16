package br.com.battista.myoffers.controller.service;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.battista.myoffers.constants.HttpStatus;
import br.com.battista.myoffers.constants.RestConstant;
import br.com.battista.myoffers.controller.listener.VendorListener;
import br.com.battista.myoffers.model.Vendor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class VendorService {

    public static final String TAG_CLASSNAME = VendorService.class.getSimpleName();

    private Retrofit builder;
    private static VendorService service;

    private VendorService() {
        builder = new Retrofit.Builder()
                .baseUrl(RestConstant.REST_API_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static VendorService getInstance() {
        if (service == null) {
            service = new VendorService();
        }
        return service;
    }

    public List<Vendor> findVendors() {
        Log.i(TAG_CLASSNAME, String.format("Retrieve all vendors in rest server:[%s]!",
                RestConstant.REST_API_ENDPOINT));
        List<Vendor> vendors = new ArrayList<Vendor>();
        VendorListener listener = builder.create(VendorListener.class);
        try {
            Response<List<Vendor>> response = listener.findVendors().execute();
            if (response != null && response.code() == HttpStatus.OK.value()) {
                vendors = response.body();
                Log.i(TAG_CLASSNAME, String.format("Founds %s vendors!", vendors.size()));
            } else {
                HttpStatus httpStatus = HttpStatus.valueOf(response.code());
                Log.i(TAG_CLASSNAME, String.format("Not found vendors, return to code: %s and reason: %s!",
                        httpStatus.value(), httpStatus.getReasonPhrase()));
            }
        } catch (IOException e) {
            Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
        }
        return vendors;
    }

    public List<Vendor> findByCodeProduct(Long codeProduct) {
        Log.i(TAG_CLASSNAME, String.format("Retrieve vendors with code:%s in rest server:[%s]!",
                codeProduct, RestConstant.REST_API_ENDPOINT));
        List<Vendor> vendors = null;
        VendorListener listener = builder.create(VendorListener.class);
        try {
            Response<List<Vendor>> response = listener.findByCodeProduct(codeProduct).execute();
            if (response != null && response.code() == HttpStatus.OK.value()) {
                vendors = response.body();
                Log.i(TAG_CLASSNAME, String.format("Found %s vendors!", vendors.size()));
            } else {
                HttpStatus httpStatus = HttpStatus.valueOf(response.code());
                Log.i(TAG_CLASSNAME, String.format("Not found vendors, return to code: %s and reason: %s!",
                        httpStatus.value(), httpStatus.getReasonPhrase()));
            }
        } catch (IOException e) {
            Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
        }
        return vendors;
    }

    public Vendor createVendor(Vendor vendor) {
        Log.i(TAG_CLASSNAME, String.format("Create vendor with code:%s in rest server:[%s]!",
                vendor.getCodeProduct(), RestConstant.REST_API_ENDPOINT));
        VendorListener listener = builder.create(VendorListener.class);
        try {
            Response<Vendor> response = listener.createVendor(vendor).execute();
            if (response != null && response.code() == HttpStatus.OK.value()) {
                Log.i(TAG_CLASSNAME, "Succeeded in creating a new vendor!");
                return response.body();
            } else {
                HttpStatus httpStatus = HttpStatus.valueOf(response.code());
                Log.i(TAG_CLASSNAME, String.format("Error in creating a new vendor, return to code: %s and reason: %s!",
                        httpStatus.value(), httpStatus.getReasonPhrase()));
            }
        } catch (IOException e) {
            Log.e(TAG_CLASSNAME, e.getLocalizedMessage(), e);
        }
        return vendor;
    }
}
