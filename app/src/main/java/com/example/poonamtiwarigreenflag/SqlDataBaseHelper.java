package com.example.poonamtiwarigreenflag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;


public class SqlDataBaseHelper extends SQLiteOpenHelper {


    static String name =  "database";
    static int version = 1;
    public static String GREEN_FLAG_PROJECT = "GREEN_FLAG_PROJECT";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    private final Context context;


    String user_table = "CREATE TABLE if not exists GREEN_FLAG_PROJECT ('id' INTEGER PRIMARY KEY AUTOINCREMENT ," +
           EMAIL+" TEXT , " +
            PASSWORD+ " TEXT )";


    public SqlDataBaseHelper(Context context) {
        super(context, name, null , version);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(user_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GREEN_FLAG_PROJECT);
        onCreate(db);
    }


    public void insertUserDetail(ContentValues contentValues){

        try (Cursor cursor = getReadableDatabase().
                rawQuery("SELECT * FROM GREEN_FLAG_PROJECT WHERE email = ?", new String[]{contentValues.get("email").toString()})) {
            if (cursor.moveToFirst()) {
                Toast.makeText(context, "Username already exist", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        getWritableDatabase().insert(GREEN_FLAG_PROJECT, "", contentValues);
        Toast.makeText(context, "Registered as user", Toast.LENGTH_SHORT).show();

    }
    public void addNewUser(String email, String password) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put( EMAIL,email);
        cv.put( PASSWORD,password);
        db.insert(GREEN_FLAG_PROJECT, null, cv);
        Toast.makeText(context, "Registered as user", Toast.LENGTH_SHORT).show();
    }
    public User checkUser(){
        //ContentValues contentValues=new ContentValues();
        User user=new User();
        int count=0;
        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery ( "SELECT * FROM GREEN_FLAG_PROJECT WHERE email = ?", new String[]{EMAIL});
        Cursor cursor = db.rawQuery("SELECT * FROM " + GREEN_FLAG_PROJECT, null);
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                user=new User(cursor.getString(1));

            } while (cursor.moveToNext());

//        try (Cursor cursor = getReadableDatabase().
//                rawQuery("SELECT * FROM GREEN_FLAG_PROJECT WHERE email = ?", new String[]{EMAIL})) {
//            if (cursor.moveToFirst()) {
//                Toast.makeText(context, "Username already exist", Toast.LENGTH_SHORT).show();
//                count++;
//            }
//            if(count>0) {
//                return EMAIL;
//            }else{
//                return "Does not exist";
//            }
        }
        return user;
    }
}
