package com.example.recyclerview;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Transaction> transactionList;

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView transDate, transTime, pan, aplAr, amountAr;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            transDate = itemView.findViewById(R.id.transDate);
            transTime = itemView.findViewById(R.id.transTime);
            pan = itemView.findViewById(R.id.pan);
            aplAr = itemView.findViewById(R.id.aplAr);
            amountAr = itemView.findViewById(R.id.amountAr);
        }
    }

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.transDate.setText(transaction.getTransDate());
        holder.transTime.setText(transaction.getTransTime());
        holder.pan.setText(transaction.getPan());
        holder.aplAr.setText(transaction.getAplAr());
        holder.amountAr.setText(transaction.getAmountAr());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }
}
