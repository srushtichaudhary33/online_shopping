package com.example.onlineshopping;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int[] idArray = {1,2,3,4,5,6,7};
    String[] nameArray = {"Beauty", "Jewellery", "Shoes", "Watch" , "Western", "Croptop","Menstshirt"};
    int[] imageArray = {R.drawable.beauty, R.drawable.jewellery, R.drawable.shoes, R.drawable.watch, R.drawable.western, R.drawable.croptop, R.drawable.menstshirt};

    ArrayList<CategoryList> arrayList;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = openOrCreateDatabase("AndroidOnlineShopping.db", MODE_PRIVATE, null);

        // CATEGORY table માં IMAGE column ને INTEGER type બનાવ્યું
        String categoryQuery = "CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), IMAGE INTEGER)";
        db.execSQL(categoryQuery);

        recyclerView = findViewById(R.id.category_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //CATEGORY Table માં data insert કરો જો તે પહેલેથી ના હોય
        for (int i = 0; i < idArray.length; i++) {
            String selectQuery = "SELECT * FROM CATEGORY WHERE NAME = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{nameArray[i]});

            if (cursor.getCount() == 0) { // Only insert if not exists
                String insertQuery = "INSERT INTO CATEGORY (NAME, IMAGE) VALUES (?, ?)";
                db.execSQL(insertQuery, new Object[]{nameArray[i], imageArray[i]});
            }
            cursor.close();
        }

        // CATEGORY table માંથી data fetch કરો
        arrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM CATEGORY";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                CategoryList list = new CategoryList();
                list.setId(cursor.getString(0));
                list.setName(cursor.getString(1));
                list.setImage(cursor.getInt(2)); // No need to parseInt now
                arrayList.add(list);
            }
        }
        cursor.close();

        // Adapter Set કરો
        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
