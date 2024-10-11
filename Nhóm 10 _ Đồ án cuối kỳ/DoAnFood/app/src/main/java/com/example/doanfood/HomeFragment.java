package com.example.doanfood;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment {
    ImageView restaurant_vector_home;
    ImageButton btn_all, btn_restaurant, btn_cafeshop, btn_search;
    ListView lsvHome;
    View home_layout;
    EditText txt_search;
    CustomAdapterShop adapter;
    ArrayList<Shop> lvData=new ArrayList<>();
    TextView category_title;
    Context mContext;

    String url_shop = "http://192.168.0.106/FoodReview/dulieushop.php";
    String selectedItem = "all";
    public HomeFragment() {

    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        restaurant_vector_home = view.findViewById(R.id.restaurant_vector_home);
        restaurant_vector_home.bringToFront();
        btn_all = (ImageButton) view.findViewById(R.id.category_all);
        btn_restaurant = (ImageButton) view.findViewById(R.id.category_restaurant);
        btn_cafeshop = (ImageButton) view.findViewById(R.id.category_cafeshop);
        lsvHome =(ListView) view.findViewById(R.id.lsvHome);
        home_layout = view.findViewById(R.id.home_layout);
        txt_search = (EditText) view.findViewById(R.id.txt_search);
        btn_search = (ImageButton) view.findViewById(R.id.btn_search);
        category_title = (TextView) view.findViewById(R.id.category_title);
        getData(url_shop);
        addEvent();
        return view;
    }

    void filterCategoryList(String category){
        selectedItem = category;
        ArrayList<Shop> filteredCategory=new ArrayList<>();
        for (Shop shop: lvData){
            if(shop.getCategory().toLowerCase().contains(category)){
                filteredCategory.add(shop);
            }
        }

        adapter=new CustomAdapterShop(mContext,
                R.layout.home_shop_item,filteredCategory);
        lsvHome.setAdapter(adapter);
    }


    void addEvent(){
        lsvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),ShopDetailActivity.class);
                intent.putExtra("shopid",lvData.get(i).getId());
                startActivity(intent);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = txt_search.getText().toString();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("search", search);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("data", lvData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        home_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != txt_search){
                    hideSoftKeyboard(v);
                }
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = "all";
                adapter=new CustomAdapterShop(mContext,
                        R.layout.home_shop_item,lvData);
                lsvHome.setAdapter(adapter);
                category_title.setText("Tất cả quán");
                btn_all.setBackgroundResource(R.drawable.category_shape_chosen);
                btn_restaurant.setBackgroundResource(R.drawable.category_shape);
                btn_cafeshop.setBackgroundResource(R.drawable.category_shape);
            }
        });

        btn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCategoryList("quán ăn");
                category_title.setText("");
                category_title.setText("Quán ăn");
                btn_all.setBackgroundResource(R.drawable.category_shape);
                btn_restaurant.setBackgroundResource(R.drawable.category_shape_chosen);
                btn_cafeshop.setBackgroundResource(R.drawable.category_shape);
            }
        });

        btn_cafeshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCategoryList("quán cafe");
                category_title.setText("Quán cafe");
                btn_all.setBackgroundResource(R.drawable.category_shape);
                btn_restaurant.setBackgroundResource(R.drawable.category_shape);
                btn_cafeshop.setBackgroundResource(R.drawable.category_shape_chosen);
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
                Toast.makeText(mContext,"Error Data!", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
            adapter=new CustomAdapterShop(mContext,
                    R.layout.home_shop_item,lvData);
            lsvHome.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }

    }


    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}