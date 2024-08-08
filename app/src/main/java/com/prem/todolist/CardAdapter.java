package com.prem.todolist;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.prem.todolist.databinding.ItemCardBinding;

import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
     Context context;
    private List<Card> cardList;
    static CardAdapter cardAdapter;

    public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    public static CardAdapter getCardAdapter() {
        return cardAdapter;
    }
    public void giveAdapter(CardAdapter cardAdapter) {
        this.cardAdapter = cardAdapter;
    }

    public void updateCardList(int id) {
        int i;
        for (i=0; i<cardList.size(); i++) {
            if(cardList.get(i).getId() == id){
                cardList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.binding.title.setText(card.getTitle());
        holder.binding.card.setOnClickListener(view -> {
            Intent intent = new Intent(context.getApplicationContext(), CardText.class);
            intent.putExtra("cardId",card.getId());
            intent.putExtra("cardTitle",card.getTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCardBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCardBinding.bind(itemView);
        }
    }
}

