package hp.test.mytv.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hp.test.mytv.model.on_air.OnAirItem;
import hp.test.mytv.model.sql_lite.Favorite;
import hp.test.mytv.model.sql_lite.OnAir;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TvShow";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Favorite.CREATE_TABLE);
        db.execSQL(OnAir.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Favorite.DROP_TABLE);
        db.execSQL(OnAir.DROP_TABLE);
        onCreate(db);
    }

    public void addOnAirs(List<OnAirItem> onAirItems){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        for (OnAirItem onAirItem:onAirItems){
            ContentValues values = new ContentValues();
            values.put(OnAir.COLUMN_ID,onAirItem.getId());
            values.put(OnAir.COLUMN_FAVORITE,0);
            values.put(OnAir.COLUMN_NAME,onAirItem.getName());
            values.put(OnAir.COLUMN_ORIGINAL_NAME,onAirItem.getOriginalName());
            values.put(OnAir.COLUMN_OVERVIEW,onAirItem.getOverview());
            values.put(OnAir.COLUMN_POSTER,onAirItem.getPosterPath());
            long id = sqLiteDatabase.insert(OnAir.TABLE_NAME,null,values);
        }
        sqLiteDatabase.close();
    }

    public void clearOnAir(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(OnAir.TABLE_NAME,null,null);
    }

    public List<OnAir> getOnAirs(){
        return getOnAirs(false);
    }

    public List<OnAir> getOnAirs(boolean favorite){
        List<OnAir> onAirs = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(OnAir.TABLE_NAME,null,OnAir.COLUMN_FAVORITE+">=?",new String[]{favorite?"1":"0"},null,null,OnAir.COLUMN_NAME);
        if (cursor.moveToFirst()){
            do {
                OnAir onAir = new OnAir(
                        cursor.getInt(cursor.getColumnIndex(OnAir.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(OnAir.COLUMN_ORIGINAL_NAME)),
                        cursor.getString(cursor.getColumnIndex(OnAir.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(OnAir.COLUMN_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(OnAir.COLUMN_POSTER)),
                        cursor.getInt(cursor.getColumnIndex(OnAir.COLUMN_FAVORITE))>0
                );
                onAirs.add(onAir);
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return  onAirs;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + OnAir.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void updateFavorite(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL("UPDATE " + OnAir.TABLE_NAME + " SET "+ OnAir.COLUMN_FAVORITE + " = 1 WHERE "+ OnAir.COLUMN_ID +" IN (" +
                "SELECT "+ Favorite.COLUMN_TMDB_ID + " FROM "+ Favorite.TABLE_NAME +
               ")");
        sqLiteDatabase.close();

    }

    public void addFavorite(int tmdbId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Favorite.COLUMN_TMDB_ID,tmdbId);
        sqLiteDatabase.insert(Favorite.TABLE_NAME,null,values);

        ContentValues contentValues = new ContentValues();
        contentValues.put(OnAir.COLUMN_FAVORITE,1);

        sqLiteDatabase.update(OnAir.TABLE_NAME,contentValues,OnAir.COLUMN_ID+"=?",new String[]{String.valueOf(tmdbId)});
        sqLiteDatabase.close();
    }

    public void removeFavorite(int tmdbId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Favorite.TABLE_NAME, Favorite.COLUMN_TMDB_ID + " = ?",
                new String[]{String.valueOf(tmdbId)});
        ContentValues contentValues = new ContentValues();
        contentValues.put(OnAir.COLUMN_FAVORITE,0);
        sqLiteDatabase.update(OnAir.TABLE_NAME,contentValues,OnAir.COLUMN_ID+"=?",new String[]{String.valueOf(tmdbId)});
        sqLiteDatabase.close();
    }

}
