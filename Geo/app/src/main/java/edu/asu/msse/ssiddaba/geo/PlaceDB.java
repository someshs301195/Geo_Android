package edu.asu.msse.ssiddaba.geo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import edu.asu.msse.ssiddaba.geo.R;

/**
 * Copyright (c) 2021 Somesh Siddabasappa,
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Purpose: SER 423 Course Assignment
 * <p>
 * Right to use: I provide the instructor and the University with the right to
 * build and evaluate the software package for the purpose of determining your
 * grade and program assessment.
 *
 * @author Somesh Siddabasappa ssiddaba@asu.edu
 * Software Engineering, CIDSE, MSSE, Arizona State University Polytechnic
 * @version March 28, 2021
 */

public class PlaceDB extends SQLiteOpenHelper {
    private static final boolean debugon = true;
    private static final int DATABASE_VERSION = 3;
    private static String dbName = "placedb";
    private String dbPath;
    private SQLiteDatabase placeDB;
    private final Context context;

    public PlaceDB(Context context) {
        super(context, dbName, null, DATABASE_VERSION);
        this.context = context;

        dbPath = context.getFilesDir().getPath() + "/";
        android.util.Log.d(this.getClass().getSimpleName(), "db path is: " +
                context.getDatabasePath("placedb"));
        android.util.Log.d(this.getClass().getSimpleName(), "dbpath: " + dbPath);
    }

    public void createDB() throws IOException {
        this.getReadableDatabase();
        try {
            copyDB();
        } catch (IOException e) {
            android.util.Log.w(this.getClass().getSimpleName(),
                    "createDB Error copying database " + e.getMessage());
        }
    }

    /**
     * Does the database exist and has it been initialized? This method determines whether
     * the database needs to be copied to the data/data/pkgName/files directory by
     * checking whether the file exists. If it does it checks to see whether the db is
     * uninitialized or whether it has the course table.
     *
     * @return false if the database file needs to be copied from the assets directory, true
     * otherwise.
     */
    private boolean checkDB() {    //does the database exist and is it initialized?
        SQLiteDatabase checkDB = null;
        boolean placeTabExists = false;
        try {
            String path = dbPath + dbName + ".db";
            File aFile = new File(path);
            if (aFile.exists()) {
                checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
                if (checkDB != null) {
                    Cursor tabChk = checkDB.rawQuery("SELECT name FROM sqlite_master where type='table' and name='place';", null);
                    if (tabChk == null) {
                        debug("PlaceDB --> checkDB", "check for place table result set is null");
                    } else {
                        tabChk.moveToNext();
                        placeTabExists = !tabChk.isAfterLast();
                    }
                    if (placeTabExists) {
                        Cursor c = checkDB.rawQuery("SELECT * FROM place", null);
                        c.moveToFirst();
                        while (!c.isAfterLast()) {
                            c.moveToNext();
                        }
                        placeTabExists = true;
                    }
                }
            }
        } catch (SQLiteException e) {
            android.util.Log.w("PlaceDB->checkDB", e.getMessage());
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return placeTabExists;
    }

    public void copyDB() throws IOException {
        try {
            if (!checkDB()) {
                // only copy the database if it doesn't already exist in my database directory
                debug("PlaceDB --> copyDB", "checkDB returned false, starting copy");
                InputStream ip = context.getResources().openRawResource(R.raw.placedb);
                // make sure the database path exists. if not, create it.
                File aFile = new File(dbPath);
                if (!aFile.exists()) {
                    aFile.mkdirs();
                }
                String op = dbPath + dbName + ".db";
                OutputStream output = new FileOutputStream(op);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = ip.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                output.flush();
                output.close();
                ip.close();
            }
        } catch (IOException e) {
            android.util.Log.w("PlaceDB --> copyDB", "IOException: " + e.getMessage());
        }
    }

    public SQLiteDatabase openDB() throws SQLException {
        String myPath = dbPath + dbName + ".db";
        if (checkDB()) {
            placeDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } else {
            try {
                this.copyDB();
                placeDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            } catch (Exception ex) {
                android.util.Log.w(this.getClass().getSimpleName(), "unable to copy and open db: " + ex.getMessage());
            }
        }
        return placeDB;
    }

    @Override
    public synchronized void close() {
        if (placeDB != null)
            placeDB.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void debug(String hdr, String msg) {
        if (debugon) {
            android.util.Log.d(hdr, msg);
        }
    }
}

