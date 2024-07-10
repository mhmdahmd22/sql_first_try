package com.example.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Trans_History.db";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Trans_History";
    private final Context context;
    private String databasePath;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
        copyDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void copyDatabase() {
        if (!checkDatabase()) {
            this.getReadableDatabase();
            try {
                copyDatabaseFromAssets();
                Log.d("DatabaseHelper", "Database copied successfully.");
            } catch (IOException e) {
                Log.e("DatabaseHelper", "Error copying database", e);
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.d("DatabaseHelper", "Database does not exist yet.");
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDatabaseFromAssets() throws IOException {
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = databasePath;
        OutputStream myOutput = new FileOutputStream(outFileName);


        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        List<String> transDates = queryColumn("TransDate", db);


        List<String> transTimes = queryColumn("TransTime", db);


        List<String> pans = queryColumn("PAN", db);


        List<String> aplArs = queryColumn("AplAr", db);


        List<String> amountArs = queryColumn("AmountAr", db);

        // Combine the results into a list of Transaction objects
        int rowCount = transDates.size();
        for (int i = 0; i < rowCount; i++) {
            Transaction transaction = new Transaction(
                    transDates.get(i),
                    transTimes.get(i),
                    pans.get(i),
                    aplArs.get(i),
                    amountArs.get(i)
            );
            transactions.add(transaction);
        }

        return transactions;
    }

    @SuppressLint("Range")
    private List<String> queryColumn(String columnName, SQLiteDatabase db) {
        List<String> columnValues = new ArrayList<>();
        String selectQuery = "SELECT " + columnName + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                columnValues.add(cursor.getString(cursor.getColumnIndex(columnName)));
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.e("DatabaseHelper", "No data found in column: " + columnName);
        }

        return columnValues;
    }
}