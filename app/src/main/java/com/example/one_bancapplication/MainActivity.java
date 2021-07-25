package com.example.one_bancapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView translist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translist = (RecyclerView) findViewById(R.id.recyclerView_transactions);
        translist.setLayoutManager(new LinearLayoutManager(this));
        RequestCall();
        //JsonParserMethod();
    }

    private void RequestCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://dev.onebanc.ai/assignment.asmx/GetTransactionHistory?userId=1&recipientId=2";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response){

             GsonBuilder gsonBuilder = new GsonBuilder();
             Gson gson = gsonBuilder.create();
             Transaction transactions = gson.fromJson(String.valueOf(response),Transaction.class);
             Log.d("Data", String.valueOf(transactions));
             List<Transaction__1> arrayList = new ArrayList<>();
             arrayList =  transactions.getTransactions();

             Collections.sort(arrayList, new SortbyDate());

                translist.setAdapter(new transactionAdapter(MainActivity.this,arrayList));
                //arrayList.get(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
                Log.e("Error : ", error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }
    class SortbyDate implements Comparator<Transaction__1>
    {
        public int compare(Transaction__1 a, Transaction__1 b)
        {
           return a.getStartDate().compareTo(b.getStartDate());
        }
    }
}
