package kapadokia.nyandoro.cpdemo.data;

// contains constants that define names for URIs, table and columns

import android.provider.BaseColumns;

public final class NationContract{

    //define an inner class for our database table;
    public static final class NationEntry implements BaseColumns{

        // define table name
        public static final String TABLE_NAME= "countries";

        // the name of the columns to be created inside the table
        public static final String _ID =BaseColumns._ID;
        public static final String COLUMN_COUNTRY ="country";
        public static final String COLUMN_CONTINENT ="continent";
    }
}