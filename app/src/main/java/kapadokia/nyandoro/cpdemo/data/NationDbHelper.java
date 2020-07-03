package kapadokia.nyandoro.cpdemo.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NationDbHelper extends SQLiteOpenHelper{

    // this helper class is responsible for
    // 1. creation of the database
    // 2. upgrading or downgrading of the database

    public static final String DATABASE_NAME = "nation.db";
    public static final int DATABASE_VERSION = 1;

    // defining the database schema
    final String  SQL_CREATE_COUNTRY_TABLE
            = "CREATE TABLE " + NationContract.NationEntry.TABLE_NAME
          +"("+NationContract.NationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NationContract.NationEntry.COLUMN_COUNTRY+ " TEXT NOT NULL, "
            +NationContract.NationEntry.COLUMN_CONTINENT +" TEXT);";

              public NationDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         // passing the sql schema to execute database creation
        db.execSQL(SQL_CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //this method is normally called when we want to make some changes to the database.
        db.execSQL("DROP TABLE IF EXISTS "+ NationContract.NationEntry.TABLE_NAME);
        onCreate(db);
    }
}