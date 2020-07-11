package com.example.ebsma.basic.views.comment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ebsma.basic.R;

public class CommentsViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public CommentsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setUsername(String username) {
        TextView myUserName = mView.findViewById(R.id.comment_username);
        myUserName.setText("@" + username + "  ");
    }

    public void setTime(String time) {
        TextView myTime = mView.findViewById(R.id.comment_time);
        String timeWithoutSecond = time.substring(0, 5);
        myTime.setText(timeWithoutSecond);
    }

    public void setDate(String date) {
        TextView myDate = mView.findViewById(R.id.comment_date);
        myDate.setText(" " + date + " ");
    }

    public void setComment(String comment) {
        TextView myComment = mView.findViewById(R.id.comment_content);
        myComment.setText(comment);
    }
}
