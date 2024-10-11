package com.example.doanfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisActivity extends AppCompatActivity {
    ImageButton btn_back, btn_showPass, btn_hidePass;;

    EditText editTextTenTaiKhoan;
    EditText editTextMatKhau;
    Button regisButton;
    View regis_layout;
    String ip = "http://192.168.0.106/FoodReview/register.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        editTextTenTaiKhoan = findViewById(R.id.editTextTenTaiKhoan);
        editTextMatKhau = findViewById(R.id.editTextMatKhau);
        regisButton = findViewById(R.id.regisbtn);
        btn_showPass = (ImageButton) findViewById(R.id.btn_showPass);
        btn_hidePass = (ImageButton) findViewById(R.id.btn_hidePass);
        editTextMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btn_showPass.setVisibility(View.INVISIBLE);
        btn_hidePass.setVisibility(View.VISIBLE);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        regis_layout = findViewById(R.id.regis_layout);

        addEvent();
    }

    void addEvent(){
        regis_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view != editTextTenTaiKhoan || view != editTextMatKhau){
                    hideSoftKeyboard(view);
                }
            }
        });

        btn_hidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextMatKhau.setInputType(InputType.TYPE_CLASS_TEXT);
                btn_showPass.setVisibility(View.VISIBLE);
                btn_hidePass.setVisibility(View.INVISIBLE);
            }
        });

        btn_showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextMatKhau.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btn_showPass.setVisibility(View.INVISIBLE);
                btn_hidePass.setVisibility(View.VISIBLE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisFunction();
                Intent intent = new Intent(RegisActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
            }

        });

    }

    void RegisFunction() {
        String user=editTextTenTaiKhoan.getText ().toString();
        String password=editTextMatKhau.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(RegisActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ip,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisActivity.this, "Thành công: ", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisActivity.this, "Thất bại: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                HashMap<String, String> hm = new HashMap<>();
                hm.put("key_username", user);
                hm.put("key_password", password);
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







