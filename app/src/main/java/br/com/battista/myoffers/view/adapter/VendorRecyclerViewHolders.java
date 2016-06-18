package br.com.battista.myoffers.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.battista.myoffers.R;

/**
 * Created by rabsouza on 11/04/16.
 */
public class VendorRecyclerViewHolders extends RecyclerView.ViewHolder {

    private final TextView lblItemNameVendor;
    private final TextView lblItemPriceVendor;

    public VendorRecyclerViewHolders(View itemView) {
        super(itemView);

        lblItemNameVendor = (TextView) itemView.findViewById(R.id.lblItemNameVendor);
        lblItemPriceVendor = (TextView) itemView.findViewById(R.id.lblItemPriceVendor);
    }

    public TextView getLblItemNameVendor() {
        return lblItemNameVendor;
    }

    public TextView getLblItemPriceVendor() {
        return lblItemPriceVendor;
    }

}