package com.example.one_bancapplication;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.time.Month;
import java.util.List;

public class transactionAdapter extends RecyclerView.Adapter<transactionAdapter.transactionViewHolder> {

    private List<Transaction__1> data;
    private  Context context;
    public transactionAdapter(Context mContext,List<Transaction__1> transaction)
    {
         this.context = mContext;
         this.data = transaction;
    }

    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.left_transaction,parent,false);
        return new transactionViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder holder, int position) {

        Transaction__1 object = data.get(position);

        String startDate = object.getStartDate();
        Double amount = object.getAmount();
        String direction = object.getDirection().toString();
        String type = object.getType().toString();
        String status = object.getStatus().toString();
        String id = object.getId().toString();

        holder.amount.setText(Double.toString(amount));
        // Setting Date //
        String[]ar = startDate.split("T");
        String a = ar[0];
        String b = ar[1];
        String[] arr1 = a.split("-");
        String[] arr2 = b.split(":");
        Month month = Month.of(Integer.parseInt(arr1[1]));
        String tz;
        if(Integer.parseInt(arr2[0])>12)
            tz = "AM";
        else
            tz = "PM";
        String Date = arr1[2]+" "+month+" "+arr1[0]+","+arr2[0]+":"+arr2[1]+" "+tz;
        holder.date.setText(Date);
        if(direction.equals("1"))
        {
            holder.firstLinearLayout.setGravity(Gravity.RIGHT);
            //holder.firstLinearLayout.
            if(status.equals("1"))
            {
                holder.cancelButton.setVisibility(View.VISIBLE);
                holder.direction.setText("You Requested");
            }
            else if(status.equals("2"))
            {
                holder.transactionId.setText(id);
                holder.direction.setText("You Paid");
            }

        }
        else if(direction.equals("2"))
        {
        if(type.equals("1"))
        {
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.transactionId.setText(id);
            holder.direction.setText("You Received");
        }
        else if(type.equals("2"))
        {
            holder.linearLayout2.setVisibility(View.VISIBLE);
            holder.direction.setText("Request Received");
        }
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder{
        TextView amount;
        TextView direction;
        LinearLayout linearLayout;

        TextView transactionId;
        LinearLayout linearLayout2;
        Button payButton;
        Button declineButton;
        Button cancelButton;
        ImageButton nextButton;
        TextView date;
        LinearLayout firstLinearLayout;
        public transactionViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = (TextView) itemView.findViewById(R.id.amount);
            direction = (TextView) itemView.findViewById(R.id.direction);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            //transactionIdH = (TextView) itemView.findViewById(R.id.transactionIdH);
            transactionId = (TextView) itemView.findViewById(R.id.transactionId);
            linearLayout2 = (LinearLayout) itemView.findViewById(R.id.linearLayout2);
            payButton = (Button) itemView.findViewById(R.id.payButton);
            declineButton = (Button) itemView.findViewById(R.id.declineButton);
            cancelButton = (Button) itemView.findViewById(R.id.cancel_button);
            nextButton = (ImageButton)itemView.findViewById(R.id.next_button);
            date  = (TextView) itemView.findViewById(R.id.date);
            firstLinearLayout = (LinearLayout) itemView.findViewById(R.id.firstLinearLayout);
        }
    }
 }
