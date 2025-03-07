package com.example.onlineshopping;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    int[] idArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
    int[] categoryIdArray = {1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 6, 6, 7};
    String[] nameArray = {"Lakme", "Nykaa", "Maybelline", "Sephora", "Oxidised", "Silver", "Gold",
            "Canvas", "Adidas", "Nike", "Rolex", "Titan", "Fastrack", "Raymond", "H & M", "Zara", "Allensolly"};
    int[] imageArray = {R.drawable.lakme, R.drawable.nykaa, R.drawable.maybelline, R.drawable.sephora,
            R.drawable.oxidised, R.drawable.silver, R.drawable.gold, R.drawable.canvas, R.drawable.adidas,
            R.drawable.nike, R.drawable.rolex, R.drawable.titan, R.drawable.fastrack, R.drawable.raymond,
            R.drawable.hm, R.drawable.zara, R.drawable.allensolly};

    ArrayList<SubCategoryList> arrayList;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        db = openOrCreateDatabase("AndroidOnlineShopping.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(20))";
        db.execSQL(tableQuery);

        String categoryQuery = "CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),IMAGE VARCHAR(100))";
        db.execSQL(categoryQuery);

        String subCategoryQuery = "CREATE TABLE IF NOT EXISTS SUBCATEGORY(SUBCATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORYID VARCHAR(10),NAME VARCHAR(50),IMAGE VARCHAR(100))";
        db.execSQL(subCategoryQuery);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
        int categoryId = Integer.parseInt(sp.getString(ConstantSp.CATEGORYID, "0"));

        recyclerView = findViewById(R.id.sub_category_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        String selectQuery = "SELECT * FROM SUBCATEGORY WHERE CATEGORYID='"+sp.getString(ConstantSp.CATEGORYID,"")+"'";
//
//
//        Cursor cursor = db.rawQuery(selectQuery,null);
//        arrayList = new ArrayList<>();
//        if(cursor.getCount()>0){
//            while (cursor.moveToNext()){
//                SubCategoryList list = new SubCategoryList();
//                list.setId(cursor.getString(0));
//                list.setCategoryId(cursor.getString(1));
//                list.setName(cursor.getString(2));
//                list.setImage(Integer.parseInt(cursor.getString(3)));
//                arrayList.add(list);
//            }
//            Log.d("SUBCATEGORY_DEBUG", String.valueOf(arrayList.size()));
//            SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this,arrayList);
//            recyclerView.setAdapter(adapter);
//        }


        // SUBCATEGORY Table માં data insert કરો જો તે પહેલેથી ના હોય
        for (int i = 0; i < idArray.length; i++) {
            String selectQuery = "SELECT * FROM SUBCATEGORY WHERE NAME = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{nameArray[i]});

            if (cursor.getCount() == 0) { // Only insert if not exists
                String insertQuery = "INSERT INTO SUBCATEGORY (CATEGORYID, NAME, IMAGE) VALUES (?, ?, ?)";
                db.execSQL(insertQuery, new Object[]{categoryIdArray[i], nameArray[i], imageArray[i]});
            }
            cursor.close();
        }


        arrayList = new ArrayList<>(); // Ensure arrayList is initialized

        String selectQuery = "SELECT * FROM SUBCATEGORY WHERE CATEGORYID=" + categoryId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("CURSOR_COUNT", String.valueOf(cursor.getCount()));
        Log.d("SUBCATEGORY_DEBUG", String.valueOf(categoryId));
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                SubCategoryList list = new SubCategoryList();
                list.setId(cursor.getString(0));
                list.setCategoryId(cursor.getString(1));
                list.setName(cursor.getString(2));
                list.setImage(cursor.getInt(3)); // Ensure correct integer retrieval
                arrayList.add(list);
            }
        }

        Log.d("SUBCATEGORY_DEBUG", "Size: " + arrayList.size());
        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, arrayList);
        recyclerView.setAdapter(adapter);


        /*arrayList = new ArrayList<>();
        if(Integer.parseInt(sp.getString(ConstantSp.CATEGORYID,"")) == categoryIdArray[i]) {
                SubCategoryList list = new SubCategoryList();
                list.setId(idArray[i]);
                list.setCategoryId(categoryIdArray[i]);
                list.setName(nameArray[i]);
                list.setImage(imageArray[i]);
                arrayList.add(list);
            }*/
    }
}
