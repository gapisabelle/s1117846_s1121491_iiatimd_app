package com.example.movinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.icu.util.ULocale;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {
    //    private Card[] localDataSet;
    private List<ChatMsg> localDataSet;
    private Context context;
    private int userId;

    //    public CardStackAdapter() {
//        localDataSet = new ArrayList<Card>(Arrays.asList(new Card[0]));
//    }
    public ChatMessageAdapter(Context context) {
        localDataSet = new ArrayList<ChatMsg>(Arrays.asList(new ChatMsg[0]));
        this.context = context;
    }

    public ChatMsg[] getLocalDataSet() {
        return localDataSet.toArray(new ChatMsg[0]);
    }

    public ChatMsg getCard(int index) {
        return localDataSet.get(index);
    }
    public void setUserId(int userId) { this.userId = userId; }

    public void setLocalDataSet(List<ChatMsg> dataSet) {
        System.out.println("[Movinder localDataSet] CHANGE");
//        localDataSet = Arrays.asList(dataSet);
        localDataSet.clear();
        localDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    public void addToLocalDataSet(ChatMsg ...dataSet) {
        int startPos = localDataSet.size();
        localDataSet.addAll(Arrays.asList(dataSet));
//        notifyDataSetChanged();
//        notifyItemRangeChanged(startPos, localDataSet.size() - startPos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_message_info, parent, false);
        return new ChatMessageAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMsg msg = localDataSet.get(position);
        holder.chatMessage.setText(msg.getMessage());
        String time = "18:09"; // TODO
        Date date = msg.getDate();
        holder.chatTime.setText(time);


        if (msg.getUser_id() != userId) {
            // TODO: Change to float right and other backgroundTint
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.parent.findViewById(R.id.chatMessageWrapper).setBackgroundTintList(ContextCompat.getColorStateList(this.context, R.color.chatMessageDark));
            }

            holder.parent.setGravity(Gravity.START);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.msgParent.getLayoutParams();

            params.setMargins(0, 0, 204, 0);
//            ltrb
            holder.msgParent.setLayoutParams(params);

//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.gravity = Gravity.END;
//
//            holder.parent.setLayoutParams(params);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.parent.findViewById(R.id.chatMessageWrapper).setBackgroundTintList(ContextCompat.getColorStateList(this.context, R.color.primary));
            }

            holder.parent.setGravity(Gravity.END);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.msgParent.getLayoutParams();

            params.setMargins(204, 0, 0, 0);
//            ltrb
            holder.msgParent.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView chatMessage;
        public TextView chatTime;
        public LinearLayout parent;
        public ConstraintLayout msgParent;

        public ViewHolder(View view) {
            super(view);

            chatMessage = view.findViewById(R.id.message_text);
            chatTime = view.findViewById(R.id.message_time);
            parent = view.findViewById(R.id.chatMessageLayout);
            msgParent = view.findViewById(R.id.chatMessageWrapper);
        }
    }

    public ChatMessageAdapter(ChatMsg[] dataSet) {
        localDataSet = Arrays.asList(dataSet);
    }


}
