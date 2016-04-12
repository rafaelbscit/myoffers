package br.com.battista.myoffers.view.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.util.Log;

import java.util.List;

import br.com.battista.myoffers.R;
import br.com.battista.myoffers.model.Offer;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewHolders> {

    public static final String TAG_CLASSNAME = ProductRecyclerViewAdapter.class.getSimpleName();

    private List<Offer> offers;
    private Context context;

    public ProductRecyclerViewAdapter(Context context, List<Offer> offers) {
        this.offers = offers;
        this.context = context;
    }

    @Override
    public ProductRecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View productLayoutView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_product, null);
        return new ProductRecyclerViewHolders(productLayoutView);
    }

    @Override
    public void onBindViewHolder(ProductRecyclerViewHolders holder, int position) {
        if (position < offers.size()) {
            Offer offer = offers.get(position);
            Log.d(TAG_CLASSNAME,
                    String.format("Filler position %s with id %s.", position, offer.getId()));
            StringBuilder descProduct = new StringBuilder();
            descProduct.append(offer.getCategory());
            descProduct.append(": ");
            descProduct.append(offer.getName());
            holder.getLblAddProduct().setText(descProduct.toString());
            holder.setIdProduct(offer.getId());
        } else {
            Log.w(TAG_CLASSNAME, String.format("Invalid position %s in offers.", position));
        }

    }

    @Override
    public int getItemCount() {
        return this.offers.size();
    }

}
