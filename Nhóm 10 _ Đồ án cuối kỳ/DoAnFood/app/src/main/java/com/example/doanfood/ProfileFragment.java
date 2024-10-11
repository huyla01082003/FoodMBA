package com.example.doanfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {
    View isLogin_layout, view;


    TextView tvEmail, tvSDT, tvAddress;
    Button btn_update, notLogin, btn_logout;

    TextView TvUsername;
    Context mContext;
    public boolean checkifLogin = false;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvSDT = view.findViewById(R.id.tvSDT);
        tvAddress = view.findViewById(R.id.tvAddress);
        btn_update = view.findViewById(R.id.btn_update);
        TvUsername = view.findViewById(R.id.tvusername);
        isLogin_layout = view.findViewById(R.id.isLogin_layout);
        notLogin = view.findViewById(R.id.notLogin_layout);
        btn_logout = view.findViewById(R.id.btn_Logout);
        checkifLogin = CheckLogin.check;


        if(checkifLogin == true){
            isLogin_layout.setVisibility(View.VISIBLE);
            notLogin.setVisibility(View.INVISIBLE);
            retrieveUserDataFromServer();
        }else{
            isLogin_layout.setVisibility(View.INVISIBLE);
            notLogin.setVisibility(View.VISIBLE);
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userID;
                userID = getActivity().getIntent().getIntExtra("id", 0);
                Intent intent = new Intent(getActivity(),UpdateProfileActivity.class);
                intent.putExtra("userId",userID);
                startActivity(intent);
            }
        });

        notLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút "Chỉnh sửa"

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckLogin.check = false;
                getActivity().recreate();
            }
        });
        return view;

    }


    private void retrieveUserDataFromServer() {
        String url = "http://192.168.0.106/FoodReview/dulieuuser.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Xử lý dữ liệu JSON và hiển thị trực tiếp trên giao diện
                        try {
                            int userId = getActivity().getIntent().getIntExtra("id",0);
                            userId--;
                            JSONArray usersArray = response.getJSONArray("users");
                            JSONObject userObject = usersArray.getJSONObject(userId);

                            String username = userObject.getString("username");
                            String email = userObject.getString("email");
                            String contact = userObject.getString("contact");
                            String address = userObject.getString("address");
                            // Cập nhật trực tiếp lên giao diện
                            TvUsername.setText(username);
                            tvEmail.setText("Email: " +email);
                            tvSDT.setText("SĐT: " +contact);
                            tvAddress.setText("Địa chỉ: " +address);


                            // Xử lý các trường dữ liệu khác nếu cần

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Lỗi xử lý dữ liệu JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi khi lấy dữ liệu
                        Toast.makeText(getActivity(), "Có lỗi xảy ra khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }


    // Hàm để lưu thông tin người dùng đã chỉnh sửa lên server
}


