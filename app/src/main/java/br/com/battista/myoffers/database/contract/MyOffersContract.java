package br.com.battista.myoffers.database.contract;

import android.provider.BaseColumns;

public class MyOffersContract {

    public MyOffersContract() {
    }

    private static abstract class BaseEntry implements BaseColumns {

        public static final String COLUMN_NAME_CREATED_AT = "createdAt";
        public static final String COLUMN_NAME_UPDATED_AT = "updatedAt";
        public static final String COLUMN_NAME_VERSION = "version";
    }

    public static abstract class OfferEntry extends BaseEntry {

        public static final String TABLE_NAME = "Offer";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_AVERAGE_PRICE = "averagePrice";
        public static final String COLUMN_NAME_BRAND = "brand";
        public static final String COLUMN_NAME_REVISE = "revise";
        public static final String COLUMN_NAME_DENOUNCE = "denounce";
        public static final String COLUMN_NAME_CODE_PRODUCT = "codeProduct";
    }

    public static abstract class VendorEntry extends BaseEntry {

        public static final String TABLE_NAME = "Vendor";
        public static final String COLUMN_NAME_VENDOR = "vendor";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_CODE_PRODUCT = "codeProduct";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_CITY = "city";
    }
}
