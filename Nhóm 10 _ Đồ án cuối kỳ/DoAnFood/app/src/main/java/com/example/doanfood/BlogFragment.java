package com.example.doanfood;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlogFragment extends Fragment {
    View addBlogLayout, blog_layout, blog_container;
    TextView tv_Username,tvEx;

    EditText txt_blog;
    Button btn_update;
    ImageButton btn_send;
    Context mContext;
    public boolean checkifLogin = false;
    ListView lsvBlog;
    ArrayList<Blog> lvData = new ArrayList<>();
    CustomAdapterBlog adapter;
    String url_blog = "http://192.168.0.106/FoodReview/dulieublog.php";
    String url_addblog = "http://192.168.0.106/FoodReview/addblog.php";

    public BlogFragment() {

    }

    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
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
        View view =inflater.inflate(R.layout.fragment_blog, container, false);

        txt_blog = view.findViewById(R.id.txt_blog);
        addBlogLayout = view.findViewById(R.id.header);
        blog_layout = view.findViewById(R.id.blog_layout);
        tv_Username = view.findViewById(R.id.tv_Username);
        lsvBlog = view.findViewById(R.id.lsvBlog);
        btn_send = view.findViewById(R.id.btn_send);
        tvEx = view.findViewById(R.id.tvEx);
        blog_container = view.findViewById(R.id.blog_container);

        checkifLogin = CheckLogin.check;
        if(checkifLogin == true){
            blog_container.setBackgroundResource(R.color.white);
            addBlogLayout.getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
            addBlogLayout.requestLayout();
            retrieveUserDataFromServer();
        }else{
            blog_container.setBackgroundResource(R.drawable.color_backgroud_blog);
            addBlogLayout.getLayoutParams().height = 0;
            addBlogLayout.requestLayout();
        }
        getData(url_blog);
        addEvent();
        return view;

    }

    void addEvent(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
                txt_blog.clearFocus();
                txt_blog.setText("");
            }
        });


    }


    private void retrieveUserDataFromServer() {
        String url = "http://192.168.0.106/FoodReview/dulieuuser.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int userId = getActivity().getIntent().getIntExtra("id",0);
                            userId--;
                            JSONArray usersArray = response.getJSONArray("users");
                            JSONObject userObject = usersArray.getJSONObject(userId);

                            String username = userObject.getString("username");
                            tv_Username.setText(username);


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

    public void getData(String url){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonDataToArrayList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error Data!", Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    public void parseJsonDataToArrayList (String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.getJSONArray("blogs");
            for(int i=0;i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Blog b = new Blog();
                b.bId = jsonObject.getInt("bId");
                b.bName = jsonObject.getString("uName");
                b.bContent=jsonObject.getString("bContent");
                lvData.add(b);
            }
            adapter=new CustomAdapterBlog(getActivity(),
                    R.layout.blog_item,lvData);
            lsvBlog.setAdapter(adapter);
        } catch (JSONException e){
            e.printStackTrace();
        }

    }



    void addComment() {
        String userName = tv_Username.getText().toString();
        String blog= txt_blog.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_addblog,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "Thành công: ", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Thất bại: ", Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("key_uName", userName);
                hm.put("key_bContent", blog);
                return hm;
            }
        };
        queue.add(stringRequest);
    }


}


