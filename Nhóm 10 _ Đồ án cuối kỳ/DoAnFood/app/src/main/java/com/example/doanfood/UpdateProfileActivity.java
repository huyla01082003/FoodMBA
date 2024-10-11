package com.example.doanfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;


public class UpdateProfileActivity extends AppCompatActivity {
    boolean chooseImage = false;
    ImageView imgProfile;
    ImageButton btn_back, btn_imgprofile;

    EditText txt_email, txt_contact, txt_address;
    Button btn_update;
    View update_profile_layout;
    String ip = "http://192.168.0.106/FoodReview/update.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        imgProfile = findViewById(R.id.imgprofile);
        btn_imgprofile = findViewById(R.id.btn_imgprofile);
        txt_email = findViewById(R.id.txt_email);
        txt_contact = findViewById(R.id.txt_contact);
        txt_address = findViewById(R.id.txt_address);
        btn_update = findViewById(R.id.btn_update);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        update_profile_layout = findViewById(R.id.update_profile_layout);
        addEvent();
        addImage();
    }

    void addEvent(){

        update_profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view != txt_email || view != txt_contact || view != txt_address){
                    hideSoftKeyboard(view);
                }
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra("userId",0);
                Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                intent.putExtra("id",id);
                UpdateFunction();
                finish();
                startActivity(intent);
            }

        });

    }

    private void addImage(){
        btn_imgprofile = findViewById(R.id.btn_imgprofile);
        btn_imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                takeImage.launch(intent);
                chooseImage = true;
            }
        });


    }
    ActivityResultLauncher<Intent> takeImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Uri uri = result.getData().getData();
                    imgProfile.setImageURI(uri);
                }
            }
    );

    void UpdateFunction() {
        int id = getIntent().getIntExtra("userId", 0);
        String idString = String.valueOf(id);
        String email=txt_email.getText ().toString();
        String contact=txt_contact.getText().toString();
        String address=txt_address.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(UpdateProfileActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ip,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateProfileActivity.this, "Thành công: ", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateProfileActivity.this, "Thất bại: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                HashMap<String, String> hm = new HashMap<>();
                hm.put("key_id", idString);
                hm.put("key_email", email);
                hm.put("key_contact", contact);
                hm.put("key_address", address);
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







