package kapadokia.nyandoro.cpdemo;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import kapadokia.nyandoro.cpdemo.data.NationContract;
import kapadokia.nyandoro.cpdemo.data.NationDbHelper;

import static kapadokia.nyandoro.cpdemo.data.NationContract.NationEntry.CONTENT_URI;

/*
*
* 	MODULE 3: Designing the Structured Data Storage
* 	Author: Sriyank Siddhartha
*
*		"AFTER" Project
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText etCountry, etContinent, etWhereToUpdate, etNewContinent, etWhereToDelete, etQueryRowById;
	private Button btnInsert, btnUpdate, btnDelete, btnQueryRowById, btnDisplayAll;

	private static final String TAG = MainActivity.class.getSimpleName();

	private SQLiteDatabase database;
	private NationDbHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etCountry 		=  findViewById(R.id.etCountry);
		etContinent 	=  findViewById(R.id.etContinent);
		etWhereToUpdate =  findViewById(R.id.etWhereToUpdate);
		etNewContinent 	=  findViewById(R.id.etUpdateContinent);
		etQueryRowById 	=  findViewById(R.id.etQueryByRowId);
		etWhereToDelete =  findViewById(R.id.etWhereToDelete);

		btnInsert 		=  findViewById(R.id.btnInsert);
		btnUpdate 		=  findViewById(R.id.btnUpdate);
		btnDelete 		=  findViewById(R.id.btnDelete);
		btnQueryRowById =  findViewById(R.id.btnQueryByID);
		btnDisplayAll 	=  findViewById(R.id.btnDisplayAll);

		btnInsert.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnQueryRowById.setOnClickListener(this);
		btnDisplayAll.setOnClickListener(this);

		databaseHelper = new NationDbHelper(this);

		// getWritableDatabase() method is responsible for creation of the database.
		// if the database doesn't exist it will create a new database
		database = databaseHelper.getWritableDatabase();		// READ/WRITE
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.btnInsert:
				insert();
				break;

			case R.id.btnUpdate:
				update();
				break;

			case R.id.btnDelete:
				delete();
				break;

			case R.id.btnQueryByID:
				queryRowById();
				break;

			case R.id.btnDisplayAll:
				queryAndDisplayAll();
				break;
		}
	}

	private void insert() {

		String countryName 	= etCountry.getText().toString();
		String continentName= etContinent.getText().toString();

		ContentValues contentValues = new ContentValues();
		contentValues.put(NationContract.NationEntry.COLUMN_COUNTRY, countryName);
		contentValues.put(NationContract.NationEntry.COLUMN_CONTINENT, continentName);


		// use the content provider class to insert the data into the database
		Uri uri = CONTENT_URI;
		Uri uriRowInserted = getContentResolver().insert(uri, contentValues);
		Log.i(TAG, "Items inserted at: "+uriRowInserted);
	}

	private void update() {

		String whereCountry = etWhereToUpdate.getText().toString();
		String newContinent = etNewContinent.getText().toString();

		String selection = NationContract.NationEntry.COLUMN_COUNTRY + " = ?";
		String[] selectionArgs = { whereCountry };			// WHERE country = ? = Japan

		ContentValues contentValues = new ContentValues();
		contentValues.put(NationContract.NationEntry.COLUMN_CONTINENT, newContinent);

		int rowsUpdated = database.update(NationContract.NationEntry.TABLE_NAME, contentValues, selection, selectionArgs);
		Log.i(TAG, "Number of rows updated: " + rowsUpdated);
	}

	private void delete() {

		String countryName = etWhereToDelete.getText().toString();

		String selection = NationContract.NationEntry.COLUMN_COUNTRY + " = ? ";
		String[] selectionArgs = { countryName };		// WHERE country = "Japan"

		int rowsDeleted = database.delete(NationContract.NationEntry.TABLE_NAME, selection, selectionArgs);
		Log.i(TAG, "Number of rows deleted: " + rowsDeleted);
	}

	private void queryRowById() {

		String rowId = etQueryRowById.getText().toString();

		String[] projection = {
				NationContract.NationEntry._ID,
				NationContract.NationEntry.COLUMN_COUNTRY,
				NationContract.NationEntry.COLUMN_CONTINENT
		};

		// Filter results. Make these null if you want to query all rows
		String selection = NationContract.NationEntry._ID + " = ? ";	// _id = ?
		String[] selectionArgs = { rowId };				// Replace '?' by rowId in runtime		// _id = 5

		String sortOrder = null;	// Ascending or Descending ...

		Uri uri = Uri.withAppendedPath(CONTENT_URI, rowId);
		Cursor cursor = getContentResolver().query(uri, projection,selection,selectionArgs,sortOrder);
		if (cursor != null && cursor.moveToNext()) {

			String str = "";

			String[] columns = cursor.getColumnNames();
			for (String column : columns) {
				str += "\t" + cursor.getString(cursor.getColumnIndex(column));
			}
			str += "\n";

			cursor.close();
			Log.i(TAG, str);
		}
	}

	private void queryAndDisplayAll() {

		String[] projection = {
				NationContract.NationEntry._ID,
				NationContract.NationEntry.COLUMN_COUNTRY,
				NationContract.NationEntry.COLUMN_CONTINENT
		};

		// Filter results. Make these null if you want to query all rows
		String selection = null;
		String[] selectionArgs = null;

		String sortOrder = null;	// Ascending or Descending ...


		Uri uri = CONTENT_URI;
		Log.i(TAG, " " + uri);
		Cursor cursor = getContentResolver().query(uri,projection, selection, selectionArgs, sortOrder);
		if (cursor != null) {

			String str = "";
			while (cursor.moveToNext()) {	// Cursor iterates through all rows

				String[] columns = cursor.getColumnNames();
				for (String column : columns) {
					str += "\t" + cursor.getString(cursor.getColumnIndex(column));
				}
				str += "\n";
			}

			cursor.close();
			Log.i(TAG, str);
		}
	}

	@Override
	protected void onDestroy() {
		database.close();	// Close Database Connection
		super.onDestroy();
	}
}
