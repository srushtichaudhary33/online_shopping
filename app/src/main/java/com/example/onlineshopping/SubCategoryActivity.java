package com.example.onlineshopping;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    int[] idArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
    int[] categoryIdArray = {1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 6, 6, 7};
    String[] nameArray = {"lakme", "nykaa", "maybelline", "sephora", "oxidised", "silver", "gold",
            "canvas", "adidas", "nike", "rolex", "titan", "fastrack", "raymond", "H & M", "zara", "allensolly"};
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

        // Initialize SharedPreferences
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);
        String categoryId = sp.getString(ConstantSp.CATEGORYID, "");

        // Validate CATEGORYID
        if (categoryId == null || categoryId.isEmpty()) {
            // Handle missing CATEGORYID case
            return;
        }

        // Open database
        db = openOrCreateDatabase("AndroidOnlineShopping.db", MODE_PRIVATE, null);

        // Create tables if they don't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS SUBCATEGORY(SUBCATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CATEGORYID INTEGER, NAME TEXT, IMAGE INTEGER)");

        // Setup RecyclerView
        recyclerView = findViewById(R.id.sub_category_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Insert subcategories if not exists
        for (int i = 0; i < idArray.length; i++) {
            Cursor cursor = db.rawQuery("SELECT * FROM SUBCATEGORY WHERE CATEGORYID = ? AND NAME = ?",
                    new String[]{String.valueOf(categoryIdArray[i]), nameArray[i]});

            if (!cursor.moveToFirst()) {
                db.execSQL("INSERT INTO SUBCATEGORY (CATEGORYID, NAME, IMAGE) VALUES (?, ?, ?)",
                        new Object[]{categoryIdArray[i], nameArray[i], imageArray[i]});
            }
            cursor.close();
        }

        // Fetch subcategories from database
        Cursor cursor = db.rawQuery("SELECT * FROM SUBCATEGORY WHERE CATEGORYID = ?", new String[]{categoryId});

        if (cursor.getCount() > 0) {
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                SubCategoryList list = new SubCategoryList();
                list.setId(cursor.getString(0));
                list.setCategoryId(cursor.getString(1));
                list.setName(cursor.getString(2));
                list.setImage(cursor.getInt(3));
                arrayList.add(list);
            }
            cursor.close();

            // Set adapter
            SubCategoryAdapter adapter = new SubCategoryAdapter(this, arrayList);
            recyclerView.setAdapter(adapter);
        }
    }
}
