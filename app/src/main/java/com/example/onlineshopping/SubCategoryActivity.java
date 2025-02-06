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

        // Open database
        db = openOrCreateDatabase("AndroidOnlineShopping.db", MODE_PRIVATE, null);

        // Create tables if they don't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, EMAIL TEXT, CONTACT INTEGER, PASSWORD TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, IMAGE TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS SUBCATEGORY(SUBCATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CATEGORYID INTEGER, NAME TEXT, IMAGE TEXT)");

        // Initialize SharedPreferences
        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.sub_category_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Insert subcategories if not exists
        for (int i = 0; i < idArray.length; i++) {
            String checkQuery = "SELECT * FROM SUBCATEGORY WHERE CATEGORYID='" + sp.getString(ConstantSp.CATEGORYID, "") + "'";
            Cursor cursor = db.rawQuery(checkQuery, null);

            if (cursor.getCount() == 0) {
                String insertQuery = "INSERT INTO SUBCATEGORY (CATEGORYID, NAME, IMAGE) VALUES " +
                        "('" + categoryIdArray[i] + "', '" + nameArray[i] + "', '" + imageArray[i] + "')";
                db.execSQL(insertQuery);
            }
            cursor.close();
        }

        // Fetch subcategories from database
        String selectQuery = "SELECT * FROM SUBCATEGORY WHERE CATEGORYID='" + sp.getString(ConstantSp.CATEGORYID, "") + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                SubCategoryList list = new SubCategoryList();
                list.setId(cursor.getString(0));
                list.setCategoryId(cursor.getString(1));
                list.setName(cursor.getString(2));
                list.setImage(Integer.parseInt(cursor.getString(3)));
                arrayList.add(list);
            }
            cursor.close();

            // Set adapter
            SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this, arrayList);
            recyclerView.setAdapter(adapter);
        }
    }
}








//package com.example.onlineshopping;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//import com.example.onlineshopping.databinding.ActivitySubCategoryBinding;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SubCategoryActivity extends AppCompatActivity {
//
//    private ActivitySubCategoryBinding binding;
//    private FirebaseFirestore firestore;
//    private SharedPreferences sharedPreferences;
//    private ArrayList<SubCategoryList> subCategoryList;
//    private SubCategoryAdapter adapter;
//
//    private final int[] categoryIdArray = {1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 6, 6, 7};
//    private final String[] nameArray = {
//            "lakme", "nykaa", "maybelline", "sephora", "oxidised", "silver", "gold",
//            "canvas", "adidas", "nike", "rolex", "titan", "fastrack", "raymond", "H & M", "zara", "allensolly"
//    };
//    private final int[] imageArray = {
//            R.drawable.lakme, R.drawable.nykaa, R.drawable.maybelline, R.drawable.sephora,
//            R.drawable.oxidised, R.drawable.silver, R.drawable.gold, R.drawable.canvas, R.drawable.adidas,
//            R.drawable.nike, R.drawable.rolex, R.drawable.titan, R.drawable.fastrack, R.drawable.raymond,
//            R.drawable.hm, R.drawable.zara, R.drawable.allensolly
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivitySubCategoryBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Initialize Firebase Firestore
//        firestore = FirebaseFirestore.getInstance();
//        sharedPreferences = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);
//
//        // Set up RecyclerView
//        binding.subCategoryRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        binding.subCategoryRecyclerview.setItemAnimator(new DefaultItemAnimator());
//
//        subCategoryList = new ArrayList<>();
//        adapter = new SubCategoryAdapter(this, subCategoryList);
//        binding.subCategoryRecyclerview.setAdapter(adapter);
//
//        // Check and insert default subcategories
//        insertDefaultSubCategories();
//
//        // Fetch data from Firestore
//        fetchSubCategories();
//    }
//
//    private void insertDefaultSubCategories() {
//        CollectionReference subCategoryRef = firestore.collection("subcategories");
//
//        for (int i = 0; i < nameArray.length; i++) {
//            int categoryId = categoryIdArray[i];
//            String name = nameArray[i];
//            int image = imageArray[i];
//
//            subCategoryRef.whereEqualTo("categoryId", categoryId).whereEqualTo("name", name)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            QuerySnapshot snapshot = task.getResult();
//                            if (snapshot.isEmpty()) {
//                                // Add subcategory if it doesn't exist
//                                Map<String, Object> subCategory = new HashMap<>();
//                                subCategory.put("categoryId", categoryId);
//                                subCategory.put("name", name);
//                                subCategory.put("image", image);
//
//                                subCategoryRef.add(subCategory);
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void fetchSubCategories() {
//        String categoryId = sharedPreferences.getString(ConstantSp.CATEGORYID, "");
//
//        firestore.collection("subcategories")
//                .whereEqualTo("categoryId", Integer.parseInt(categoryId))
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        subCategoryList.clear();
//                        for (DocumentSnapshot document : task.getResult()) {
//                            SubCategoryList list = new SubCategoryList();
//                            list.setId(document.getId());
//                            list.setCategoryId(String.valueOf(document.getLong("categoryId")));
//                            list.setName(document.getString("name"));
//                            list.setImage(document.getLong("image").intValue());
//                            subCategoryList.add(list);
//                        }
//                        adapter.notifyDataSetChanged();
//                    } else {
//                        Log.e("Firestore", "Error fetching data", task.getException());
//                    }
//                });
//    }
//}
//
//
//////package com.example.onlineshopping;
//////
//////import android.content.SharedPreferences;
//////import android.database.Cursor;
//////import android.database.sqlite.SQLiteDatabase;
//////import android.os.Bundle;
//////
//////import androidx.activity.EdgeToEdge;
//////import androidx.appcompat.app.AppCompatActivity;
//////import androidx.core.graphics.Insets;
//////import androidx.core.view.ViewCompat;
//////import androidx.core.view.WindowInsetsCompat;
//////import androidx.recyclerview.widget.DefaultItemAnimator;
//////import androidx.recyclerview.widget.RecyclerView;
//////import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//////
//////import java.util.ArrayList;
//////
//////public class SubCategoryActivity extends AppCompatActivity {
//////
//////    RecyclerView recyclerView;
//////
//////    int[] idArray = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
//////    int[] categoryIdArray = {1,1,1,1,2,2,2,3,3,3,4,4,4,5,6,6,7};
//////    String[] nameArray = {"lakme", "nykaa", "maybelline", "sephora", "oxidised", "silver", "gold", "canvas", "adidas", "nike", "rolex", "titan", "fastrack", "raymond", "H & M", "zara", "allensolly"};
//////    int[] imageArray = {R.drawable.lakme, R.drawable.nykaa, R.drawable.maybelline, R.drawable.sephora, R.drawable.oxidised, R.drawable.silver, R.drawable.gold, R.drawable.canvas, R.drawable.adidas, R.drawable.nike, R.drawable.rolex, R.drawable.titan, R.drawable.fastrack, R.drawable.raymond, R.drawable.hm, R.drawable.zara, R.drawable.allensolly};
//////
//////    ArrayList<SubCategoryList> arrayList;
//////
//////    SharedPreferences sp;
//////    SQLiteDatabase db;
//////
//////    @Override
//////    protected void onCreate(Bundle savedInstanceState) {
//////        super.onCreate(savedInstanceState);
//////        setContentView(R.layout.activity_sub_category);
//////
//////        db = openOrCreateDatabase("AndroidOnlineShopping.db", MODE_PRIVATE, null);
//////        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), EMAIL VARCHAR(50), CONTACT BIGINT(10), PASSWORD VARCHAR(20))";
//////        db.execSQL(tableQuery);
//////
//////        String categoryQuery = "CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), IMAGE VARCHAR(100))";
//////        db.execSQL(categoryQuery);
//////
//////        String SubCategoryQuery = "CREATE TABLE IF NOT EXISTS SUBCATEGORY(SUBCATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORYID VARCHAR(10) ,NAME VARCHAR(50), IMAGE VARCHAR(100))";
//////        db.execSQL(SubCategoryQuery);
//////
//////        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
//////
//////        recyclerView = findViewById(R.id.sub_category_recyclerview);
//////        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//////        recyclerView.setItemAnimator(new DefaultItemAnimator());
//////
//////        for (int i=0;i<idArray.length;i++) {
//////            String selectQuery = "SELECT * FROM SUBCATEGORY WHERE CATEGORYID='"+sp.getString(ConstantSp.CATEGORYID,"")+"'";
//////            Cursor cursor = db.rawQuery(selectQuery,null);
//////            if (cursor.getCount()>0){
//////
//////            }
//////            else {
//////                String insertQuery = "INSERT INTO SUBCATEGORY VALUES (NULL, '"+categoryIdArray[i]+"','"+nameArray[i]+"','"+imageArray[i]+"')";
//////                db.execSQL(insertQuery);
//////            }
//////        }
//////
//////        String selectQuery = "SELECT * FROM SUBCATEGORY WHERE CATEGORY='"+sp.getString(ConstantSp.CATEGORYID,"")+"'";
//////        Cursor cursor = db.rawQuery(selectQuery,null);
//////        if (cursor.getCount()>0){
//////            arrayList = new ArrayList<>();
//////            while (cursor.moveToNext()){
//////                SubCategoryList list = new SubCategoryList();
//////                list.setId(cursor.getString(0));
//////                list.setCategoryId(cursor.getString(1));
//////                list.setName(cursor.getString(2));
//////                list.setImage(Integer.parseInt(cursor.getString(3)));
//////                arrayList.add(list);
//////
//////            }
//////            SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this,arrayList);
//////            recyclerView.setAdapter(adapter);
//////        }
//////
//////        /*  arrayList = new ArrayList<>();
//////        if (Integer.parseInt( sp.getString(ConstantSp.CATEGORYID, "")) == categoryIdArray[i]) {
//////                SubCategoryList list = new SubCategoryList();
//////                list.setId(idArray[i]);
//////                list.setCategoryId(categoryIdArray[i]);
//////                list.setName(nameArray[i]);
//////                list.setImage(imageArray[i]);
//////                arrayList.add(list);
//////            } */
//////        }
//////    }