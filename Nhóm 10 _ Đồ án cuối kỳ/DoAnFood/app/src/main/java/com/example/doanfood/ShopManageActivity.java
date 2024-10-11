package com.example.doanfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopManageActivity extends AppCompatActivity {

    ImageButton btn_addShop;
    ListView lsvShop;

    CustomAdapterShop adapter;
    String url_shop = "http://192.168.0.106/FoodReview/dulieushop.php";
    ArrayList<Shop> lvData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_management);
        lsvShop = findViewById(R.id.lsvShop);
        btn_addShop = findViewById(R.id.btn_addShop);

        getData(url_shop);
        addEvent();

    }

    void addEvent(){
        btn_addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopManageActivity.this, AddShopActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getData(String url){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonDataToArrayList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShopManageActivity.this,"Error Data!", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(ShopManageActivity.this);
        queue.add(request);
    }


    public void parseJsonDataToArrayList (String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.getJSONArray("shops");
            for(int i=0;i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Shop s = new Shop();
                s.id = jsonObject.getInt("Id");
                s.name = jsonObject.getString("Name");
                s.address=jsonObject.getString("Address");
                s.number=jsonObject.getString("Number");
                s.category=jsonObject.getString("Category");
                String imgString = jsonObject.getString("Image");
                byte[] imgbyte = android.util.Base64.decode(imgString, android.util.Base64.DEFAULT);
                s.image= imgbyte;
                String imgString2 = jsonObject.getString("Image");
                byte[] imgbyte2 = android.util.Base64.decode(imgString2, android.util.Base64.DEFAULT);
                s.imageMap= imgbyte2;
                lvData.add(s);
            }
            adapter=new CustomAdapterShop(ShopManageActivity.this,
                    R.layout.home_shop_item,lvData);
            lsvShop.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }

    }


}