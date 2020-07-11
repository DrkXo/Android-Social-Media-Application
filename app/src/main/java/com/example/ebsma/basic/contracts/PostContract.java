package com.example.ebsma.basic.contracts;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.Spinner;

public interface PostContract {
    interface View {
        void onPostFailed(String errorMessage);
        void onPostSuccess();
        void showLoadingBar(String title, String message);
        void dismissLoadingBar();
    }

    interface Presenter {
        void validatePostInfo(EditText PostDescription, Bitmap BitMapPostImage, Spinner spinner, double longitude, double latitude, int mYear, int mDay, int mMonth);

        Bitmap resizeBitMap(Bitmap image, int maxWidth, int maxHeight);
    }
}
