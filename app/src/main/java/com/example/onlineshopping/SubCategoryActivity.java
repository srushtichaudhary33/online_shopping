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

        db = openOrCreateDatabase("AndroidOnlineShopping.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(20))";
        db.execSQL(tableQuery);

        String categoryQuery = "CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),IMAGE VARCHAR(100))";
        db.execSQL(categoryQuery);

        String subCategoryQuery = "CREATE TABLE IF NOT EXISTS SUBCATEGORY(SUBCATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORYID VARCHAR(10),NAME VARCHAR(50),IMAGE VARCHAR(100))";
        db.execSQL(subCategoryQuery);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        recyclerView = findViewById(R.id.sub_category_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String selectQuery = "SELECT * FROM SUBCATEGORY WHERE CATEGORYID='"+sp.getString(ConstantSp.CATEGORYID,"")+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                SubCategoryList list = new SubCategoryList();
                list.setId(cursor.getString(0));
                list.setCategoryId(cursor.getString(1));
                list.setName(cursor.getString(2));
                list.setImage(Integer.parseInt(cursor.getString(3)));
                arrayList.add(list);
            }
            SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this,arrayList);
            recyclerView.setAdapter(adapter);
        }

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
