
package com.example.onlineshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    SharedPreferences sp;

    SQLiteDatabase db;

//    int[] productIdArray = {1,2,3,4};
//    int[] productSubCatIdArray = {3,3,5,5};
//    String[] productNameArray = { "Asoit01", "Asoit02", "Sou01", "Sou02"};
//    String[] productPriceArray = { "250", "350", "200", "300"};
//    int[] productImageArray = { R.drawable.asoit01, R.drawable.asoit02, R.drawable.sou01, R.drawable.sou02};
//    String[] escArray = {
//            " A digital platform for easy access to e-books and online resources." ,
//            " A versatile platform for managing, accessing, and borrowing library resources efficiently."
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("AndroidOnlineShopping.db", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), EMAIL VARCHAR(50), CONTACT BIGINT(10), PASSWORD VARCHAR(20))";
        db.execSQL(tableQuery);

        String categoryQuery = "CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(50), IMAGE VARCHAR(100))";
        db.execSQL(categoryQuery);

        String subCategoryQuery = "CREATE TABLE IF NOT EXISTS SUBCATEGORY(SUBCATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORYID VARCHAR(10), NAME VARCHAR(50), IMAGE VARCHAR(100))";
        db.execSQL(subCategoryQuery);

        String productQuery = "CREATE TABLE IF NOT EXISTS PRODUCT(PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT, SUBCATEGORYID VARCHAR(10), NAME VARCHAR(50), PRICE VARCHAR(20), IMAGE VARCHAR(100), DESCRIPTION TEXT)";
        db.execSQL(productQuery);

        String wishlistQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(WISHLISTID INTEGER PRIMARY KEY AUTOINCREMENT, USERID INTEGER(10), PRODUCTID INTEGER(10))";
        db.execSQL(wishlistQuery);

        String cartQuery = "CREATE TABLE IF NOT EXISTS CART(CARTID INTEGER PRIMARY KEY AUTOINCREMENT,ORDERID INTEGER(10),USERID INTEGER(10),PRODUCTID INTEGER(10),QTY INTEGER(3),PRICE VARCHAR(10),TOTALPRICE VARCHAR(10))";
        db.execSQL(cartQuery);



        imageView = findViewById(R.id.splash_image);

        Glide.with(SplashActivity.this).asGif().load("https://pixabay.com/gifs/loading-bar-complete-wait-3692/").placeholder(R.mipmap.ic_launcher).into(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getString(ConstantSp.USERID,"").equalsIgnoreCase("")) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        },4000);
    }
}