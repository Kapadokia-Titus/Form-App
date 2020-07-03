package kapadokia.nyandoro.cpdemo.data;

// contains constants that define names for URIs, table and columns

import android.net.Uri;
import android.provider.BaseColumns;

public final class NationContract{

    //defining the authority string
    public static final String CONTENT_AUTHORITY ="kapadokia.nyandoro.cpdemo.data.NationPovider";
    // declare content Uri
    // BASE_CONTENT_URI will be content://kapadokia.nyandoro.cpdemo.data.NationPovider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    // path to get client app to our table
    public static final String PATH_COUNTRIES= "countries";
    //define an inner class for our database table;
    public static final class NationEntry implements BaseColumns{

        // declare the final content Uri
        // CONTENT_URI will be content://kapadokia.nyandoro.cpdemo.data.NationPovider/countries
         public static final Uri CONTENT_URI =  Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COUNTRIES);


        // define table name
        public static final String TABLE_NAME= "countries";

        // the name of the columns to be created inside the table
        public static final String _ID =BaseColumns._ID;
        public static final String COLUMN_COUNTRY ="country";
        public static final String COLUMN_CONTINENT ="continent";
    }
}