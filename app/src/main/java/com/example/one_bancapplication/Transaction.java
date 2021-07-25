
package com.example.one_bancapplication;

import java.util.List;


import com.example.one_bancapplication.Transaction__1;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Transaction {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("receipeintId")
    @Expose
    private Integer receipeintId;
    @SerializedName("transactions")
    @Expose
    private List<Transaction__1> transactions = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReceipeintId() {
        return receipeintId;
    }

    public void setReceipeintId(Integer receipeintId) {
        this.receipeintId = receipeintId;
    }

    public List<Transaction__1> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction__1> transactions) {
        this.transactions = transactions;
    }

}
