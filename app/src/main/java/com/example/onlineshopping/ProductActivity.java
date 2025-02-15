package com.example.onlineshopping;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    int[] idArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49};
    int[] subCatIdArray = {1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 5, 5, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 15, 16, 16, 16, 17, 17, 17};
    String[] nameArray = {"Lipstick", "Kajal", "Foundation", "Combopack", "Glitterati Nail Polish", "lipstick", "Kajal", "Foundantion", "Combopack", "Compact Powder", "Teddt Tint", "Eye Shedo", "Combo Pack", "Ring", "Bangle", "Silver", "Jewellery", "Payal", "Earring", "Rani Haar", "Greeen", "Black", "Denim", "Pink", "Rose Pink", "Color Full", "White", "Jordan", "Black", "Ladies Watch", "Two Tone", "Memento Blue", "Purple", "Rose Gold", "Brown", "Green", "Rose Gold", "Indo Western", "Suit", "Wedding Dress", "Black", "White", "Multi Color", "Green", "Pink", "White", "Dark Green", "Dark Grey", "Checks Shirt"};
    String[] priceArray = {"365", "479", "463", "2085", "180", "400", "560", "1999", "3050", "243", "870", "5609", "4321", "980", "567", "765", "98713", "67312", "7632", "96510", "3456", "5671", "2314", "6590", "7421", "4590", "7000", "3467", "50971", "98120", "89712", "2000", "5612", "6000", "1000", "1050", "3412", "2050", "10000", "50000", "3210", "7609", "3500", "5000", "4210", "2110", "8713", "7000", "1209"};
    int[] imageArray = {R.drawable.lipstick, R.drawable.kajal, R.drawable.foundation, R.drawable.combopack, R.drawable.glitteratinailpolish, R.drawable.naykaalipstick, R.drawable.naykaakajal, R.drawable.naykaafoundation, R.drawable.naykaacombopack, R.drawable.compactpowder, R.drawable.teddttint, R.drawable.eyeshedo, R.drawable.combopack, R.drawable.oxidisedring, R.drawable.oxidisedbangle, R.drawable.oxidizedsilver, R.drawable.silverjewellery, R.drawable.silverpayal, R.drawable.goldearring, R.drawable.goldranihaar, R.drawable.canvasgreen, R.drawable.canvasblack, R.drawable.canvasdenim, R.drawable.adidaspink, R.drawable.adidasrosepink, R.drawable.nikecolorful, R.drawable.nikewhite, R.drawable.nikejordan, R.drawable.rolexblack, R.drawable.rolexladieswatch, R.drawable.rolextwotone, R.drawable.titanmementoblue, R.drawable.titanpurple, R.drawable.titanrosegold, R.drawable.fastrackbrown, R.drawable.fastrackgreen, R.drawable.fastrackrosegold, R.drawable.raymondindowestern, R.drawable.raymondsuit, R.drawable.raymondweddingdress, R.drawable.hmblack, R.drawable.hmwhite, R.drawable.hmmulticolor, R.drawable.zaragreen, R.drawable.zarapink, R.drawable.zarawhite, R.drawable.allensollydarkgreen, R.drawable.allensollydarkgrey, R.drawable.allensollychecksshirt };
    String[] descArray = {"Liquid matte formulations are the most longlasting lipsticks", "This dermatologically tested kajal is smudge proof, water proof and adds a dramatic, glamorous look to your eyes. Longlasting up to 22 hours.", "Lakme foundations are made to suit every skin type and skin tone of Indian women. They are made from skinsafe ingredients which are also longlasting.", "Lakmē continuously innovates to offer a wide range of high performance and world class cosmetics and skincare products that have earned recognition.", "Step into the world of Chrome Couture with 10 luxurious metallic shades that radiate opulence and sophistication in every stroke.", "Experience longlasting formulas that provide allday wear, intense pigmentation for vibrant looks, and seamless application for a flawless finish.", "Nykaa Black Magic Kajal is a waterproof, smudgeproof, and vegan kajal that can be used to accentuate the eyes. It is designed to be longlasting and can last up to 12 hours.", "Nykaa offers a variety of foundations, including matte, antipollution, and other types. Nykaa foundations are designed to create an even complexion, cover blemishes, and minimize pores.", "Nykaa combopacks are sets of beauty products that include multiple items. Nykaa sells a variety of products, including makeup, skincare, hair care, and fragrances.", "Maybelline New Yorks Fit Me Matte  Poreless Compact Powder gives you a flawless and naturalmatte finish with upto 16 hours of oil control.", "Achieve Fluffy, Blurred Matte Finish With Teddy Soft Texture. Buy Now For AllDay Comfort. Get A Soft Matte Look With Our Plush Superstay Tint. Weightless, 12 Hour Stay Shop Now. Fluffy. Plush. Airy.", "eyeshadow makeup from Sephora now. Find a variety of glitter, metallic, natural, nude, natural, cream, matte eyeshadows and more to make your eyes pop.", "visiting the Pick Your Free Samples section of the Beauty Offers page or by clicking the link to select your samples in Basket.", "Oxidised jewellery is silver jewellery that has been purposefully oxidised by a jeweller to create a strong, black patina.", "highquality metal, these bangles are treated with an oxidation process to give them a vintage and antique look.", "Silver oxidised jewellery is made by treating silver with a chemical process that darkens the metal, giving it an antique, vintage look.", "silver necklaces for women at Jaypore. Elevate your style with handcrafted pieces perfect for any occasion.", "Our silver payal collection online ranges from simple payals to unique pieces encrusted with precious stones, and more.", "A timeless classic, gold hoop earrings offer a touch of modern edge.", "rani haar set is handcrafted in 22k gold, rubies, emeralds and freshwater pearls.", "Green is the color of life, and the green shade present on Sneaker Shoes symbolises positivity, growth, nature and life itself.", "The shoes are praised for their stylish appearance, particularly for casual outfits like jeans and pencil fit pants. The material of the shoe is soft", "Just like suede and leather shoes, canvas shoes and trainers can soak up water very quickly.", "A pair of adidas pink shoes will have you looking pretty in pink while you kill your fitness goals.", "These updated shoes take minimalist design cues from the classic silhouette and push even further with subtle accents and barelythere 3Stripes.", "American athletic footwear and apparel corporation headquartered near Beaverton, Oregon, United States. ", "exude a sense of crisp cleanliness that is visually appealing. The stark white color creates a fresh, polished look that captures attention and reflects a sense of care and attention to detail in ones appearance.", "Air Jordan is a line of basketball shoes produced by Nike, Inc. Related apparel and accessories are marketed under Jordan Brand.", "A symbol of timeless elegance and versatility, black dials are available across a wide range of Rolex models.", "Rolex is an integrated and independent Swiss watch manufacture.", "They tend to retain a resale premium for a while, but it erodes with time–albeit more slowly with Rolex than with other brands.", "one that is gigantic in size or power.", "The main advantages of a titanium watch are its strength and lightweight. The titanium watch case wont crack. Can be exposed to saltwater without corroding. Titanium watches are comfortable to wear because they are light on the wrist.", "Rose gold contains more copper, resulting in a pinkish hue, while 18k yellow gold is alloyed with varying amounts of copper, silver, or zinc to achieve the desired color.", " Fastrack Exuberant Quartz Multifunction Brown Dial Stainless Steel Strap Watch for Guys.", " Fastrack Rider Smart Watch offers advanced fitness tracking features, including step count, calories burned, distance covered, and more. Monitor your health goals with precision and style.", "Fastrack Style Up Quartz Analog Mother Of Pearl Dial Rose Gold Stainless Steel Strap Watch For Girls.", "A perfect blend of style and royalty is this Indowestern set from Ethnix. Tailored from a blend of 70 polyester and 30 viscose, they are soft against the skin. This comfortable set can be best teamed with mojaris to complete your look.", "Raymond Suits, a global leader in worsted suiting fabric with a manufacturing capacity of 43 million meters, is one of Indias top textile brands, known for its premium blends marketed as Raymond Fine Fabrics. A wellstitched suit can make an outfit truly stand out.", "Wedding dress designers must possess certain skills to succeed in their craft. These include having an aesthetic eye, sewing and patternmaking skills, creativity, and communication skills. ", "Browse for staple dark pieces with our womens black crop tops. Add new black pieces to your outfits and choose from all styles and fabrics.", "Look through versatile neutral styles with our wearable womens white crop tops. Search for signature styles to add interest to outfits or browse basics.", "Crop tops are a modern wardrobe essential, adding a stylish spin to any look. Perfect for wearing with jeans, skirts, or highwaisted trousers.", "Green dresses for women arrive bold and bright as well as subtle and discreet. The colour wheel runs from fresh and zesty green to olive.", "A crop top or a belly shirt, tummy top, or half shirt is a kind of shirt. Crop tops do not cover all of the middle section of the wearers body. A person wearing a crop top will have part of their belly and back showing.", "Zara white dresses are yearround styles that can be worn solo or layered with our wider collection of womenswear.", "Allen Solly green polo tshirt for men. Made from a premium blend of cotton and polyester, this regularfit solid tee offers breathability and softness that elevates your casual look effortlessly.", "Allen Solly solid grey polo tshirt for men. with a regular fit and made using a blend of cotton and polyester.", "A checkered shirt is a shirt with a pattern of squares or rectangles that alternate in color, similar to a checkerboard."};

    ArrayList<ProductList> arrayList;
    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("AndroidInternshipJune.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(20))";
        db.execSQL(tableQuery);

        String categoryQuery = "CREATE TABLE IF NOT EXISTS CATEGORY(CATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),IMAGE VARCHAR(100))";
        db.execSQL(categoryQuery);

        String subCategoryQuery = "CREATE TABLE IF NOT EXISTS SUBCATEGORY(SUBCATEGORYID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORYID VARCHAR(10),NAME VARCHAR(50),IMAGE VARCHAR(100))";
        db.execSQL(subCategoryQuery);

        String productQuery = "CREATE TABLE IF NOT EXISTS PRODUCT(PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT,SUBCATEGORYID VARCHAR(10),NAME VARCHAR(50),PRICE VARCHAR(20),IMAGE VARCHAR(100),DESCRIPTION TEXT)";
        db.execSQL(productQuery);

        String wishlistQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(WISHLISTID INTEGER PRIMARY KEY AUTOINCREMENT,USERID INTEGER(10),PRODUCTID INTEGER(10))";
        db.execSQL(wishlistQuery);

        String cartQuery = "CREATE TABLE IF NOT EXISTS CART(CARTID INTEGER PRIMARY KEY AUTOINCREMENT,ORDERID INTEGER(10),USERID INTEGER(10),PRODUCTID INTEGER(10),QTY INTEGER(3),PRICE VARCHAR(10),TOTALPRICE VARCHAR(10))";
        db.execSQL(cartQuery);

        recyclerView = findViewById(R.id.product_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String selectQuery = "SELECT * FROM PRODUCT WHERE SUBCATEGORYID='"+sp.getString(ConstantSp.SUBCATEGORYID,"")+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                ProductList list = new ProductList();
                list.setId(cursor.getString(0));
                list.setSubCatId(cursor.getString(1));
                list.setName(cursor.getString(2));
                list.setPrice(cursor.getString(3));
                list.setImage(Integer.parseInt(cursor.getString(4)));
                list.setDesc(cursor.getString(5));
                String wishlistSelectQuery = "SELECT * FROM WISHLIST WHERE PRODUCTID='"+cursor.getString(0)+"' AND USERID='"+sp.getString(ConstantSp.USERID,"")+"'";
                Cursor wishCursor = db.rawQuery(wishlistSelectQuery,null);
                if(wishCursor.getCount()>0){
                    list.setWishlist(true);
                }
                else {
                    list.setWishlist(false);
                }

                String cartSelectQuery = "SELECT * FROM CART WHERE PRODUCTID='"+cursor.getString(0)+"' AND USERID='"+sp.getString(ConstantSp.USERID,"")+"' AND ORDERID='0'";
                Cursor cartCursor = db.rawQuery(cartSelectQuery,null);
                if(cartCursor.getCount()>0){
                    while (cartCursor.moveToNext()){
                        list.setCartId(cartCursor.getString(0));
                        list.setQty(Integer.parseInt(cartCursor.getString(4)));
                    }
                }
                else{
                    list.setCartId("0");
                    list.setQty(0);
                }

                arrayList.add(list);
            }
            ProductAdapter adapter = new ProductAdapter(ProductActivity.this,arrayList,db);
            recyclerView.setAdapter(adapter);
        }

        /*arrayList = new ArrayList<>();
        for(int i=0;i<idArray.length;i++){
            if(Integer.parseInt(sp.getString(ConstantSp.SUBCATEGORYID,"")) == subCatIdArray[i]) {
                ProductList list = new ProductList();
                list.setId(idArray[i]);
                list.setSubCatId(subCatIdArray[i]);
                list.setName(nameArray[i]);
                list.setPrice(priceArray[i]);
                list.setImage(imageArray[i]);
                list.setDesc(descArray[i]);
                arrayList.add(list);
            }
        }
        ProductAdapter adapter = new ProductAdapter(ProductActivity.this,arrayList);
        recyclerView.setAdapter(adapter);*/
    }
}