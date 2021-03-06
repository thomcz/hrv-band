package hrv.band.app.model.storage.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hrv.band.app.model.storage.sqlite.statements.HRVParameterContract;
import hrv.band.app.model.storage.sqlite.statements.RRIntervalContract;

/**
 * Copyright (c) 2017
 * Created by Julian Martin on 23.06.2016.
 */
public class SQLiteStorageController extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HRVParamDB.db";

    private static final String COLUMN_TYPE_REAL = " REAL, ";

    private static final String SQL_CREATE_HRVTABLE = "CREATE TABLE "
            + HRVParameterContract.HRVParameterEntry.TABLE_NAME
            + " (" + HRVParameterContract.HRVParameterEntry.COLUMN_NAME_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HRVParameterContract.HRVParameterEntry.COLUMN_NAME_TIME + " INTEGER, "
            + HRVParameterContract.HRVParameterEntry.COLUMN_NAME_RATING + COLUMN_TYPE_REAL
            + HRVParameterContract.HRVParameterEntry.COLUMN_NAME_CATEGORY + " STRING, "
            + HRVParameterContract.HRVParameterEntry.COLUMN_NAME_NOTE + " STRING, "
            + HRVParameterContract.HRVParameterEntry.COLUMN_NAME_RRDATAID + " INTEGER"
            + ")";

    private static final String SQL_CREATE_RRINTERVALTABLE = "CREATE TABLE "
            + RRIntervalContract.RRIntervalEntry.TABLE_NAME
            + "(" + RRIntervalContract.RRIntervalEntry.COLUMN_NAME_ENTRY_ID + " INTEGER, "
            + RRIntervalContract.RRIntervalEntry.COLUMN_NAME_ENTRY_VALUE + " REAL)";

    private static final String SQL_DELETE_RRINTERVALS =
            "DROP TABLE IF EXISTS " + RRIntervalContract.RRIntervalEntry.TABLE_NAME;

    private static final String SQL_DELETE_HRVPAARAMETERS =
            "DROP TABLE IF EXISTS " + HRVParameterContract.HRVParameterEntry.TABLE_NAME;


    public SQLiteStorageController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HRVTABLE);
        db.execSQL(SQL_CREATE_RRINTERVALTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        db.execSQL(SQL_DELETE_HRVPAARAMETERS);
        db.execSQL(SQL_DELETE_RRINTERVALS);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void clearDatabaseAndRecreate() {
        clearDatabase();
        onCreate(getWritableDatabase());
    }

    public void clearDatabase() {
        getWritableDatabase().execSQL(SQL_DELETE_HRVPAARAMETERS);
        getWritableDatabase().execSQL(SQL_DELETE_RRINTERVALS);
    }


}
