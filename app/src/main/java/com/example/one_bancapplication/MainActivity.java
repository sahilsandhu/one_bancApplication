package com.example.one_bancapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.TransitionAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView translistRecyclerView;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translistRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_transactions);
        username = (TextView) findViewById(R.id.username);
        translistRecyclerView.setHasFixedSize(true);
        translistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        translistRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

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
             List<Transaction__1> arrayList = new ArrayList<Transaction__1>();
             arrayList =  transactions.getTransactions();
             Collections.sort(arrayList, new SortbyDate());

             //Setting Thw username
             Transaction__1 temp = arrayList.get(0);
             Customer cust = temp.getCustomer();
             String vpay = cust.getvPay();
             String[] sep = vpay.split("@");
             String name = sep[0];
             username.setText(name);

                TransactionAdapter transactionAdapter =  new TransactionAdapter(MainActivity.this, arrayList);

                //This is the code to provide a sectioned list
                List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();
                //Sections
                int position = 0;


                Transaction__1 transaction__1 = arrayList.get(0);
                String var = transaction__1.getStartDate();
                String[] tem;
                tem = var.split("T");
                var = tem[0];
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,var));
                for(int i=1;i<arrayList.size();i++)
                {
                    transaction__1 = arrayList.get(i);
                    String st = transaction__1.getStartDate();
                    String[] temp1  = st.split("T");
                    String a = temp1[0];
                    if(!var.equals(a))
                    {

                        String st1 = transaction__1.getStartDate();
                        String[] temp2  = st.split("T");
                        var = temp1[0];
                        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(i,var));
                    }
                }



                //Add your adapter to the sectionAdapter
                SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
                SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                        SimpleSectionedRecyclerViewAdapter(MainActivity.this,R.layout.section,R.id.section_text,transactionAdapter);
                mSectionedAdapter.setSections(sections.toArray(dummy));

                translistRecyclerView.setAdapter(mSectionedAdapter);
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
