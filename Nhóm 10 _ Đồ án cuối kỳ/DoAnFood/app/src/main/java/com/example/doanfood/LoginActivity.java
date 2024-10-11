package com.example.doanfood;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class LoginActivity extends AppCompatActivity {
    View login_layout;
    ArrayList<User> lstUsers= new ArrayList<User>();
    EditText txtuser, txtpass;
    Button loginbtn, btn_regis;
    ImageButton btn_back, btn_showPass, btn_hidePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtuser = (EditText) findViewById(R.id.editTextTenTaiKhoan);
        txtpass = (EditText) findViewById(R.id.editTextMatKhau);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_regis = (Button) findViewById(R.id.btn_regis);
        btn_showPass = (ImageButton) findViewById(R.id.btn_showPass);
        btn_hidePass = (ImageButton) findViewById(R.id.btn_hidePass);

        txtpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btn_showPass.setVisibility(View.INVISIBLE);
        btn_hidePass.setVisibility(View.VISIBLE);

        login_layout = findViewById(R.id.login_layout);

        loginbtn = (Button) findViewById(R.id.loginbtn);

        getData();
        addEvent();
    }

    public void getData(){
        String url = "http://192.168.0.106/FoodReview/dulieuuser.php";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"co loi" ,Toast.LENGTH_LONG).show();
            }});
        RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(request);
    }

    void addEvent(){
        btn_hidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtpass.setInputType(InputType.TYPE_CLASS_TEXT);
                btn_showPass.setVisibility(View.VISIBLE);
                btn_hidePass.setVisibility(View.INVISIBLE);
            }
        });

        btn_showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btn_showPass.setVisibility(View.INVISIBLE);
                btn_hidePass.setVisibility(View.VISIBLE);
            }
        });

        login_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view != txtuser || view != txtpass){
                    hideSoftKeyboard(view);
                }
            }
        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
                recreate();
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usertext, passtext;
                int checkLogin =0;
                int userId = 0;
                String userName = "";
                String userPass = "";
                usertext = txtuser.getText().toString();
                passtext = txtpass.getText().toString();
                for (User user: lstUsers){
                    userName = user.username;
                    userPass = user.password;
                    if(usertext.equals(userName) && passtext.equals(userPass)){
                        checkLogin = 1;
                        userId = user.id;
                    }
                }
                if(checkLogin == 1){
                    CheckLogin.check = true;
                    Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                    intent.putExtra("id",userId);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"dang nhap that bai",Toast.LENGTH_LONG).show();
                }


            }
        });
    }



    void parseJsonData(String jsonString){
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray fruitsArray =object.getJSONArray("users");
            for(int i = 0 ; i < fruitsArray.length();i++){
                JSONObject obj = fruitsArray.getJSONObject(i);
                User user = new User();
                user.id = obj.getInt("id");
                user.username = obj.getString("username");
                user.password = obj.getString("password");
                user.address = obj.getString("address");
                user.email = obj.getString("email");
                user.contact = obj.getInt("contact");
                String imgString = obj.getString("image");

                byte[] imgbyte = android.util.Base64.decode(imgString, android.util.Base64.DEFAULT);
                user.image = imgbyte;
                lstUsers.add(user);
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}