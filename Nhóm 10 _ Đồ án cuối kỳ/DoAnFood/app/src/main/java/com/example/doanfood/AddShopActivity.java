package com.example.doanfood;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddShopActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    View add_shop_layout;
    ArrayList<User> lstUsers= new ArrayList<User>();
    EditText txt_shop, txt_contact, txt_address, txt_open, txt_close;
    Spinner spinner;
    Button btn_add;
    ImageButton btn_back;

    private static final String[] category = {"Quán ăn", "Quán cafe"};
    String category_string = "";
    String ip = "http://192.168.0.106/FoodReview/addshop.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        txt_shop = (EditText) findViewById(R.id.txt_shop);
        txt_address = (EditText) findViewById(R.id.txt_address);
        txt_contact =  findViewById(R.id.txt_contact);
        txt_open = findViewById(R.id.txt_open);
        txt_open = findViewById(R.id.txt_close);
        btn_add = findViewById(R.id.btn_add);
        spinner = findViewById(R.id.spinner_category);
        add_shop_layout = findViewById(R.id.add_shop_layout);
        btn_back = findViewById(R.id.btn_back);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddShopActivity.this,
                android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addEvent();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                category_string = "Quán ăn";
                break;
            case 1:
                category_string = "Quán cafe";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    void addEvent(){

        add_shop_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view != txt_address || view != txt_close || view != txt_contact || view != txt_contact || view != txt_shop || view != txt_open){
                    hideSoftKeyboard(view);
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFunction();
                Intent intent = new Intent(AddShopActivity.this, ShopManageActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    void addFunction() {
        String shop=txt_shop.getText ().toString();
        String address=txt_address.getText().toString();
        String contact=txt_contact.getText().toString();
        String open=txt_open.getText().toString();
        String close=txt_close.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(AddShopActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ip,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddShopActivity.this, "Thành công: ", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddShopActivity.this, "Thất bại: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("key_sName", shop);
                hm.put("key_sAddress", address);
                hm.put("key_sNumber", contact);
                hm.put("key_sOpen", open);
                hm.put("key_sClose", close);
                hm.put("key_sCategory", category_string);
                return hm;
            }
        };
        queue.add(stringRequest);
    }


    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}