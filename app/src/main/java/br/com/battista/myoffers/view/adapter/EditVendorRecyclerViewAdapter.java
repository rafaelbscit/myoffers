package br.com.battista.myoffers.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.util.Log;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.battista.myoffers.R;
import br.com.battista.myoffers.model.Vendor;


public class EditVendorRecyclerViewAdapter extends RecyclerView.Adapter<EditVendorRecyclerViewHolders> {

    public static final String TAG_CLASSNAME = EditVendorRecyclerViewAdapter.class.getSimpleName();
    private Locale locale = new Locale("pt", "BR");

    private List<Vendor> vendors;
    private Context context;
    private NumberFormat numberInstance;

    public EditVendorRecyclerViewAdapter(Context context, List<Vendor> vendors) {
        this.vendors = vendors;
        this.context = context;

        numberInstance = NumberFormat.getNumberInstance(locale);
        numberInstance.setMinimumFractionDigits(2);
    }

    @Override
    public EditVendorRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View productLayoutView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_edit_vendor, parent, false);
        return new EditVendorRecyclerViewHolders(productLayoutView);
    }

    @Override
    public void onBindViewHolder(EditVendorRecyclerViewHolders holder, int position) {
        if (position < vendors.size()) {
            Vendor vendor = vendors.get(position);
            Log.d(TAG_CLASSNAME,
                    String.format("Filler position %s with id %s.", position, vendor.getId()));

            if (vendor.getVendor() != null) {
                holder.getTxtItemNameVendor().setText(vendor.getVendor());
            } else {
                holder.getTxtItemNameVendor().setText(null);
            }

            if (vendor.getPrice() != null) {
                holder.getTxtItemPriceVendor().setText(numberInstance.format(vendor.getPrice()));
            } else {
                holder.getTxtItemPriceVendor().setText(null);
            }

            holder.setVendor(vendor);
        } else {
            Log.w(TAG_CLASSNAME, String.format("Invalid position %s in vendors.", position));
        }

    }

    @Override
    public int getItemCount() {
        return this.vendors.size();
    }

}
