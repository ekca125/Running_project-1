package com.trainer.courserunner.managedata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MapDAO {
    private static SQLiteDatabase mapDB = null;

    public static void initMapDB(Context context) {
        String[] expansionFiles = ObbLoader.getAPKExpansionFiles(context, 1, 0);
        String dbLocation = expansionFiles[0];
        if (mapDB == null) {
            MapDAO.mapDB = SQLiteDatabase.openDatabase(dbLocation, null, SQLiteDatabase.OPEN_READONLY);
        } else {
            Log.v("DBLOG", "DB Load Success");
        }
    }

    public static List<MapDTO> getScopeAddress(double startX, double startY,
                                               double endX, double endY) {
        String sql = "SELECT longitude, latitude FROM addresstable " +
                "WHERE longitude > ? AND latitude > ? AND longitude < ? AND latitude< ?";
        String[] whereArgs = new String[]{
                String.valueOf(startX),
                String.valueOf(startY),
                String.valueOf(endX),
                String.valueOf(endY)
        };
        Cursor cursor = mapDB.rawQuery(sql, whereArgs);
        ArrayList<MapDTO> Address = new ArrayList<>();
        while (cursor.moveToNext()) {
            Address.add(new MapDTO(cursor.getDouble(cursor.getColumnIndex("longitude")),
                            cursor.getDouble(cursor.getColumnIndex("latitude"))
                    )
            );
        }
        cursor.close();
        return Address;
    }
}
