package com.example.doanfood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDetailActivity extends AppCompatActivity  {
    CustomAdapterComment adapter;

    ArrayList<Comments> lvData = new ArrayList<>();
    ListView lsvComment;
    Button contactButton, btn_back;
    TextView tvName, tvOpen, tvClose, tvAddress;
    ImageView imgShop, imgMap;
    ImageButton btn_send;
    EditText txt_comment;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    private GoogleMap mMap;
    String number;
    String url_comment = "http://192.168.0.106/FoodReview/dulieucomment.php";
    String url_addcomment = "http://192.168.0.106/FoodReview/addcomment.php";
    View shop_detail_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        btn_back = findViewById(R.id.btn_back);
        contactButton = findViewById(R.id.contactButton);
        tvName = findViewById(R.id.tvName);
        tvOpen = findViewById(R.id.tvOpen);
        tvClose = findViewById(R.id.tvClose);
        tvAddress = findViewById(R.id.addressTextView);
        imgShop = findViewById(R.id.imgshop);
        imgMap = findViewById(R.id.imgMap);
        lsvComment = findViewById(R.id.lsvComment);
        btn_send = findViewById(R.id.btn_send);
        txt_comment = findViewById(R.id.txt_comment);
        shop_detail_layout = findViewById(R.id.shop_detail_layout);
        retrieveUserDataFromServer();
        addEvent();
        getData(url_comment);
    }


    private void addEvent() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
                txt_comment.clearFocus();
                txt_comment.setText("");
                getData(url_comment);
            }
        });

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoogleMap(tvAddress.getText().toString());
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                recreate();
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = number;
                openPhoneDialer(phoneNumber);
            }
        });

        shop_detail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(v);

            }
        });
    }

    private void retrieveUserDataFromServer() {
        String url = "http://192.168.0.106/FoodReview/dulieushop.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Xử lý dữ liệu JSON và hiển thị trực tiếp trên giao diện
                        try {
                            int shopID = getIntent().getIntExtra("shopid",0);
                            shopID--;
                            JSONArray shopArray = response.getJSONArray("shops");
                            JSONObject shopOject = shopArray.getJSONObject(shopID);

                            String name = shopOject.getString("Name");
                            tvName.setText(name);
                            String address=shopOject.getString("Address");
                            tvAddress.setText(address);
                            number=shopOject.getString("Number");
                            String open = shopOject.getString("Open");
                            tvOpen.setText(open);
                            String close=shopOject.getString("Close");
                            tvClose.setText(close);
                            number=shopOject.getString("Number");
                            String imgString = shopOject.getString("Image");
                            byte[] imageshopbyte = android.util.Base64.decode(imgString, android.util.Base64.DEFAULT);
                            Bitmap imgshop = BitmapFactory.decodeByteArray(imageshopbyte, 0, imageshopbyte.length);
                            imgShop.setImageBitmap(imgshop);
                            String imgString2 = shopOject.getString("ImageMap");
                            byte[] imagemapbyte = android.util.Base64.decode(imgString2, android.util.Base64.DEFAULT);
                            Bitmap imgmap = BitmapFactory.decodeByteArray(imagemapbyte, 0, imagemapbyte.length);
                            imgMap.setImageBitmap(imgmap);
                            // Xử lý các trường dữ liệu khác nếu cần

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShopDetailActivity.this, "Lỗi xử lý dữ liệu JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi khi lấy dữ liệu
                        Toast.makeText(ShopDetailActivity.this, "Có lỗi xảy ra khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ShopDetailActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void openPhoneDialer(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse( "tel:" + Uri.encode(phoneNumber)));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openGoogleMap(String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            showToast("Google Maps is not installed");
        }
    }

    private void showToast(String message) {
        // Hiển thị Toast thông báo
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),"Error Data!", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(ShopDetailActivity.this);
        queue.add(request);
    }

    public void parseJsonDataToArrayList (String response) {
        try {
            int shopID = getIntent().getIntExtra("shopid",0);
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.getJSONArray("comments");
            for(int i=0;i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Comments c = new Comments();
                c.cId = jsonObject.getInt("cId");
                c.sId = jsonObject.getInt("sId");
                c.cContent=jsonObject.getString("cContent");
                if(c.sId == shopID){
                    lvData.add(c);
                }
            }
            adapter=new CustomAdapterComment(getApplicationContext(),
                    R.layout.comment_item,lvData);
            lsvComment.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    void addComment() {
        int shopID = getIntent().getIntExtra("shopid",0);
        String comment= txt_comment.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(ShopDetailActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_addcomment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ShopDetailActivity.this, "Thành công: ", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopDetailActivity.this, "Thất bại: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("key_sId", String.valueOf(shopID));
                hm.put("key_cContent", comment);
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
