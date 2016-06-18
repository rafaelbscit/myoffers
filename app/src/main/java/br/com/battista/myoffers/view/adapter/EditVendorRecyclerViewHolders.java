package br.com.battista.myoffers.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import br.com.battista.myoffers.R;
import br.com.battista.myoffers.model.Vendor;

/**
 * Created by rabsouza on 11/04/16.
 */
public class EditVendorRecyclerViewHolders extends RecyclerView.ViewHolder {

    private final EditText txtItemPriceVendor;
    private final EditText txtItemNameVendor;
    private Vendor vendor;

    public EditVendorRecyclerViewHolders(View itemView) {
        super(itemView);

        txtItemPriceVendor = (EditText) itemView.findViewById(R.id.txtItemPriceVendor);
        txtItemNameVendor = (EditText) itemView.findViewById(R.id.txtItemNameVendor);
    }

    public EditText getTxtItemPriceVendor() {
        return txtItemPriceVendor;
    }

    public EditText getTxtItemNameVendor() {
        return txtItemNameVendor;
    }

    public Vendor getVendor() {
        if (vendor == null) {
            vendor = new Vendor();
        }
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}