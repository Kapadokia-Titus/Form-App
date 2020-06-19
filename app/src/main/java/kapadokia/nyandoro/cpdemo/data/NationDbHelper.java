package kapadokia.nyandoro.cpdemo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * this class is responsible for creation of the database, upgrading or downgrading
 *
 */
public class NationDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "nations.db";
	private static final int DATABASE_VERSION = 1;

	private final String SQL_CREATE_COUNTRY_TABLE
			= "CREATE TABLE " + NationContract.NationEntry.TABLE_NAME
			+ " (" + NationContract.NationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NationContract.NationEntry.COLUMN_COUNTRY + " TEXT NOT NULL, "
			+ NationContract.NationEntry.COLUMN_CONTINENT + " TEXT"
			+ ");";

	public NationDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_COUNTRY_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Ideally we wouldn't want to delete all of our entries!
		db.execSQL("DROP TABLE IF EXISTS " + NationContract.NationEntry.TABLE_NAME);
		onCreate(db);	// Call to create a new db with upgraded schema and version
	}
}

/*
		TABLE NAME: countries	Database Name: nations.db

		 _id	country		continent
 		  1		 India		 Asia
 		  2		 Japan		 Asia
 		  3		 Nigeria	 Africa
* */
