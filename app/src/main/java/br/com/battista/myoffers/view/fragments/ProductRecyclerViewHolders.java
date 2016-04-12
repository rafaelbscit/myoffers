package br.com.battista.myoffers.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.battista.myoffers.ProductActivity;
import br.com.battista.myoffers.R;
import br.com.battista.myoffers.constants.ViewConstant;

/**
 * Created by rabsouza on 11/04/16.
 */
public class ProductRecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView lblAddProduct;
    private final ImageView btnAddProduct;
    private Long idProduct = 0l;

    public ProductRecyclerViewHolders(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        lblAddProduct = (TextView) itemView.findViewById(R.id.lblAddProduct);
        btnAddProduct = (ImageView) itemView.findViewById(R.id.btnAddProduct);
    }

    public TextView getLblAddProduct() {
        return lblAddProduct;
    }

    public ImageView getBtnAddProduct() {
        return btnAddProduct;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Bundle args = new Bundle();
        args.putLong(ViewConstant.PARAM_ID_PRODUCT, getIdProduct());

        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtras(args);
        context.startActivity(intent, args);
    }
}