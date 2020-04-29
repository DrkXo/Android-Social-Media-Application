package com.example.ebsma.basic.contracts;

import com.example.ebsma.basic.models.comment.Comments;
import com.example.ebsma.basic.views.comment.CommentsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public interface CommentContract {
    interface View {
        void setCommentsListAdapter(FirebaseRecyclerAdapter<Comments, CommentsViewHolder> firebaseRecyclerAdapter);
        void setCommentInputText(String commentText);
        String getCommentInputText();
        void onPostCommentFailed(String errorMessage);
    }

    interface Presenter {
        void showCommentsList();
        void postComment();
    }
}
