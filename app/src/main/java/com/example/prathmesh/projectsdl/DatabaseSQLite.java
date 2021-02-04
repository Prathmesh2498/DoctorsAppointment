package com.example.prathmesh.projectsdl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLite {

    //Create variables for column names

    public static final String COL_DETAILS = "Appointment_Details";
    public static final String COL_DATE = "Appointment_Date";
    public static final String COL_COUNT = "Final_Count";
    public static final String COL_SRNO = "SR_NO";


    public static final String DATABASE_NAME = "Appointments" ;
    public static final String DATABASE_TABLE_NAME = "Appointment_Info";
    public static final int DATABASE_VERSION = 1;

    /*Database Variables */

    private DBHelper sqlHelper;

    //Get context of class using this DB use context
    private final Context sqlContext;

   //To connect to SQLite DB
    private SQLiteDatabase sqLiteDatabase;

    //Constructor
    public DatabaseSQLite(Context context){
        sqlContext = context;
    }






    //Class which has all functions
    private class DBHelper extends SQLiteOpenHelper{
        //Constructor
        public DBHelper(Context context){

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        //On create runs only when database file doesn't exist.
        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create Query
            String sqlCreateQueryAT = "CREATE TABLE IF NOT EXISTS "+DATABASE_TABLE_NAME + " (" +COL_SRNO +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                     + COL_DETAILS + " TEXT NOT NULL, " + COL_DATE + " TEXT NOT NULL, "
                                     + COL_COUNT + " TEXT NOT NULL);" ;


            //String sqlCreateQueryV = "CREATE TABLE IF NOT EXISTS Version "+" (" + COL_DBVersion + " INTEGER NOT NULL);";

            //Execute Query
            db.execSQL(sqlCreateQueryAT);

            //db.execSQL(sqlCreateQueryV);


        }

        //Called when stored version is lower than requested in constructor
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_NAME);
            onCreate(db);
        }
    }

    //CREATE METHODS TO USE IN CODE

    //METHOD OPEN

    public DatabaseSQLite open() throws SQLException{

        //DATABASE_VERSION = getGetDatabaseVersion();

        sqlHelper = new DBHelper(sqlContext);
        sqLiteDatabase = sqlHelper.getWritableDatabase();
        return this;
    }

    //METHOD CLOSE

    public void close(){
        sqlHelper.close();
    }

    //METHOD CREATE
    public long addValues(String Details, int Date,  int count){

        //ContentValues class is used to set values to DB
        ContentValues cv = new ContentValues();
        cv.put(COL_DETAILS , Details);
        cv.put(COL_DATE ,Date);
        cv.put(COL_COUNT ,count);

        //insert return no of rows affected in long
        return sqLiteDatabase.insert(DATABASE_TABLE_NAME, null , cv);
    }

    //METHOD RETRIEVE
    public String[] retrieveData(){

        //Init required col in array
        String [] col = new String[] {COL_DETAILS , COL_DATE , COL_COUNT};

        //Cursor is used to traverse DB
        Cursor sqlCursor = sqLiteDatabase.query(DATABASE_TABLE_NAME, col, null , null, null, null, null);

        //Get col indices
        int DetailID = sqlCursor.getColumnIndex(COL_DETAILS);
        int DateID = sqlCursor.getColumnIndex(COL_DATE);
        int CountID = sqlCursor.getColumnIndex(COL_COUNT);

        String [] sendDataBack = new String[3];
        for (int i=0;i<3;i++){
            sendDataBack[i]="a"+i;

        }


        for (sqlCursor.moveToFirst();!sqlCursor.isAfterLast();sqlCursor.moveToNext()){
            sendDataBack[0] = sqlCursor.getString(DetailID);
            sendDataBack[1]=sqlCursor.getString(DateID);
            sendDataBack[2]=sqlCursor.getString(CountID);



        }

        return sendDataBack;

    }

    //METHOD DELETE

    public void deleteData(int id){

        sqLiteDatabase.delete(DATABASE_TABLE_NAME, COL_COUNT +" = ? ", new String[]{Integer.toString(id)});
    }

    //METHOD RETURN COUNT

    public int getCount(){
        //Init required col in array
        String [] col = new String[] {COL_DETAILS , COL_DATE , COL_COUNT};

        //Cursor is used to traverse DB
        Cursor sqlCursor = sqLiteDatabase.query(DATABASE_TABLE_NAME, col, null , null, null, null, null);

        int count = sqlCursor.getCount();

        return count;
    }

    //METHOD UPDATE
    public long update(int count){
        ContentValues ucv = new ContentValues();
        ucv.put(COL_COUNT, Integer.toString(count));
        return sqLiteDatabase.update(DATABASE_TABLE_NAME, ucv, COL_COUNT+" =? ", new String[]{Integer.toString(count)} );
    }


}
