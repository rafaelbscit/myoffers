package br.com.battista.myoffers.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.R;
import br.com.battista.myoffers.model.Vendor;
import br.com.battista.myoffers.util.NumberFormatUtil;


public class VendorRecyclerViewAdapter extends RecyclerView.Adapter<VendorRecyclerViewHolders> {

    public static final String TAG_CLASSNAME = VendorRecyclerViewAdapter.class.getSimpleName();

    private List<Vendor> vendors;

    public VendorRecyclerViewAdapter(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    @Override
    public VendorRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View productLayoutView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_vendor, parent, false);
        return new VendorRecyclerViewHolders(productLayoutView);
    }

    @Override
    public void onBindViewHolder(VendorRecyclerViewHolders holder, int position) {
        if (position < vendors.size()) {
            Vendor vendor = vendors.get(position);
            Log.d(TAG_CLASSNAME,
                    String.format("Filler position %s with id %s.", position, vendor.getId()));
            holder.getLblItemNameVendor().setText(vendor.getVendor());
            holder.getLblItemPriceVendor().setText(NumberFormatUtil.format(vendor.getPrice()));
        } else {
            Log.w(TAG_CLASSNAME, String.format("Invalid position %s in vendors.", position));
        }

    }

    @Override
    public int getItemCount() {
        return this.vendors.size();
    }

}
