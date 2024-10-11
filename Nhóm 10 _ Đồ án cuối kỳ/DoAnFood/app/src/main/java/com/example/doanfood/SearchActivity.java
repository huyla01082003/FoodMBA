package com.example.doanfood;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    TextView tv_search_title;
    View search_layout;
    ImageButton btn_search, btn_back;
    EditText txt_search;
    ListView lsvSearch;

    CustomAdapterShop adapter;

    ArrayList<Shop> lvData = new ArrayList<>();
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tv_search_title = (TextView) findViewById(R.id.tv_search_title);
        txt_search= (EditText) findViewById(R.id.txt_search);
        lsvSearch= (ListView) findViewById(R.id.lsvSearch);
        btn_search = (ImageButton) findViewById(R.id.btn_search);
        btn_back= (ImageButton) findViewById(R.id.btn_back);
        search_layout= findViewById(R.id.search_layout);
        getData();
        searchBefore();
        addEvent();

    }

    void addEvent(){
        lsvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this,ShopDetailActivity.class);
                intent.putExtra("shopid",lvData.get(i).getId());
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != txt_search){
                    hideSoftKeyboard(v);
                }
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search;
                search = txt_search.getText().toString();
                searchFunction(search);
                tv_search_title.setText(search + " ("+num+" kết quả)" );
                hideSoftKeyboard(v);
            }
        });
    }

    void getData(){
        lvData = getIntent().getExtras().getParcelableArrayList("data") ;
    }

    void searchBefore(){
        Intent intent = getIntent();
        String search_before = intent.getStringExtra("search");
        txt_search.setText(search_before);
        searchFunction(search_before);
        tv_search_title.setText(search_before + " ("+num+" kết quả)" );
    }

    void searchFunction(String search){
        num = 0;
        ArrayList<Shop> searchList=new ArrayList<>();
        for (Shop shop: lvData){
            if(shop.getName().toLowerCase().contains(search)){
                searchList.add(shop);
                num++;
            }
        }

        adapter=new CustomAdapterShop(getApplicationContext(),
                R.layout.home_shop_item,searchList);
        lsvSearch.setAdapter(adapter);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}