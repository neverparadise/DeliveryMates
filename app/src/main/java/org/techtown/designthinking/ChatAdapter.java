package org.techtown.designthinking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String name;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_chat_list, parent, false);
        return new MyViewHolder(v);
    }

    public ChatAdapter(List<ChatData> myDataset, Context context, String myName) {
        mDataset = myDataset;
        this.name = myName;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatData chat = mDataset.get(position);
        holder.tv_name.setText(chat.getName());
        holder.tv_msg.setText(chat.getMessage());


        if (chat.getName().equals(this.name)) {
            holder.tv_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.tv_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            //holder.tv_msg.setBackgroundColor(Color.parseColor("#aaff88"));
        }
        else{
            holder.tv_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.tv_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            //holder.tv_msg.setBackgroundColor(Color.parseColor("#FF929CA5"));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_msg;
        public View rootView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            rootView = itemView;
        }

    }

    public ChatData getChat(int position) {
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(ChatData chat) {
        mDataset.add(chat);
        notifyItemInserted(mDataset.size() - 1);
        // 0, 1, 2, ... =
    }
}
