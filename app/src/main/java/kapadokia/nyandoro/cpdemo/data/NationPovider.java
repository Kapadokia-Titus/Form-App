package kapadokia.nyandoro.cpdemo.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.SQLData;

import static android.content.ContentValues.TAG;
import static kapadokia.nyandoro.cpdemo.data.NationContract.CONTENT_AUTHORITY;
import static kapadokia.nyandoro.cpdemo.data.NationContract.NationEntry.TABLE_NAME;
import static kapadokia.nyandoro.cpdemo.data.NationContract.PATH_COUNTRIES;

public class NationPovider extends ContentProvider {

    // declaring the reference of the NationDbHelper class
    private NationDbHelper databaseHelper;

    //constants for the operation
    private static final int COUNTRIES =1; // for whole table
    private static final int COUNTRIES_COUNTRY_NAME =2; // for a specific row in a table identified as COUNTRY_NAME
    private static final int COUNTRIES_ID =3; // for a specific row in a table identified as _ID

    // defining our matcher class
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // defining a static block inside which all patterns of the Uri will be declared.

    static {
        // nb: the last parameter is a static value which can be any positive integer
            uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES, COUNTRIES); //content://kapadokia.nyandoro.cpdemo.data.NationPovider/countries
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES+"/#", COUNTRIES_ID); //content://kapadokia.nyandoro.cpdemo.data.NationPovider/countries/#
        // matcher for *
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_COUNTRIES+"/*", COUNTRIES_COUNTRY_NAME); //content://kapadokia.nyandoro.cpdemo.data.NationPovider/countries/*
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new NationDbHelper(getContext());

        // return true means the completion of initialization of the content provider
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        //when reading the database
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        // define the cursor and use the switch case to match the incoming uri
        Cursor cursor;

        switch (uriMatcher.match(uri)){
            case COUNTRIES:
                cursor = database.query(NationContract.NationEntry.TABLE_NAME,		// The table name
				projection,                 // The columns to return
				selection,                  // Selection: WHERE clause OR the condition
				selectionArgs,              // Selection Arguments for the WHERE clause
				null,                       // don't group the rows
				null,                       // don't filter by row groups
				sortOrder);					// The sort order


                break;

            case COUNTRIES_ID:
                // cursor helps us print out all values present in the database.
                cursor = database.query(NationContract.NationEntry.TABLE_NAME, projection, selection,selectionArgs, null,null, sortOrder);					// The sort order

                break;
            default:
                throw new IllegalArgumentException(TAG +"Insert Unknown Uri: "+uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {



        switch (uriMatcher.match(uri)){

            case COUNTRIES:
                return insertRecord(uri, values, TABLE_NAME);
            default:
                throw new IllegalArgumentException(TAG + "Insert unknown URI " +uri);
        }

    }

    private Uri insertRecord(Uri uri, ContentValues values, String tableName) {
        //Create an instance of SQLite database
        // it will open the connection between the SQLite database in a write mode
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        long rowId =database.insert(tableName, null, values);

        // if something went wrong,  we will return null
        if (rowId == -1){
            Log.e(TAG, "insert Error for Uri: "+ uri );
            return null;
        }
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
