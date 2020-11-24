package com.example.bookinghotel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookinghotel.R;
import com.example.bookinghotel.entity.Comment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{
    private Context context;
    private ArrayList<Comment> data;
    public CommentAdapter(Context context, ArrayList<Comment> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_conment_item, parent, false);

        CommentHolder commentHolder = new CommentHolder(view);
        return commentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = data.get(position);
        holder.tvName.setText(comment.getName());
        holder.tvComment.setText(comment.getComment());
        holder.ratingBar.setRating(comment.getRating());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvComment;
        RatingBar ratingBar;
        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCommentName);
            tvComment = itemView.findViewById(R.id.tvCommentContent);
            ratingBar =itemView.findViewById(R.id.rbRating);
        }
    }
}
