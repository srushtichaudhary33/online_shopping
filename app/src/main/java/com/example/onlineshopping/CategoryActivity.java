package com.example.onlineshopping;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    int[] idArray = {1,2,3,4,5,6,7};
    String[] nameArray = {"beauty", "jewellery", "shoes", "watch" , "western", "croptop","menstshirt"};
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
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), EMAIL VARCHAR(50), CONTACT BIGINT(10), PASSWORD VARCHAR(20))";
        db.execSQL(tableQuery);

        String categoryQuery = "CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), IMAGE VARCHAR(100))";
        db.execSQL(categoryQuery);

        recyclerView = findViewById(R.id.category_recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        for (int i=0;i<idArray.length;i++){
            String selectQuery = "SELECT * FROM CATEGORY WHERE NAME ='"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(selectQuery,null);
            if (cursor.getCount()>0){

            }
            else {
                String insertQuery = "INSERT INTO CATEGORY VALUES (NULL, '"+nameArray[i]+"','"+imageArray[i]+"')";
                db.execSQL(insertQuery);
            }
        }

        String selectQuery = "SELECT * FROM CATEGORY";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount()>0) {
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                CategoryList list = new CategoryList();
                list.setId(cursor.getString(0));
                list.setName(cursor.getString(1));
                list.setImage(Integer.parseInt(cursor.getString(2)));
                arrayList.add(list);
            }
            CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, arrayList);
            recyclerView.setAdapter(adapter);
        }

//        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,idArray,nameArray,imageArray);
//        recyclerView.setAdapter(adapter);

    }
}