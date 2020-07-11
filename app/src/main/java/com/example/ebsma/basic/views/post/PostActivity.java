package com.example.ebsma.basic.views.post;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.ebsma.basic.R;
import com.example.ebsma.basic.contracts.PostContract;
import com.example.ebsma.basic.presenters.post.PostPresenter;
import com.example.ebsma.basic.views.main.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import io.noties.markwon.Markwon;
import io.noties.markwon.editor.MarkwonEditor;
import io.noties.markwon.editor.MarkwonEditorTextWatcher;

public class PostActivity extends AppCompatActivity implements PostContract.View,
        View.OnClickListener, AdapterView.OnItemSelectedListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    Toolbar mToolbar;
    ImageButton SelectPostImage;
    Button UpdatePostButton;
    EditText PostDescription;
    public List<Address> user = null;
    Spinner spinner;
    static final int Gallery_Pick = 1;
    Uri imageUri;
    Button DatePicker, md_show;
    Bitmap BitMapPostImage;
    Geocoder geocoder;
    String bestProvider;
    double latitude;
    double longitude;
    Location location;
    MarkerOptions markerOptions;
    ProgressDialog loadingBar;
    TextView txtDate;
    PostPresenter presenter;
    Context context = this;
    private int mYear, mMonth, mDay;
    private GoogleMap mMap;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        presenter = new PostPresenter(this);
        initViews();

        SelectPostImage.setOnClickListener(this);
        UpdatePostButton.setOnClickListener(this);

        txtDate = (TextView) findViewById(R.id.date_show);

        spinner = (Spinner) findViewById(R.id.cat_spinner);  //->Spinner Creation variable initialized in line number 36
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cat, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Markwon markwon = Markwon.create(this);

// create editor
        final MarkwonEditor editor = MarkwonEditor.create(markwon);

//// set edit listener
//        PostDescription.addTextChangedListener(MarkwonEditorTextWatcher.withProcess(editor));
//        editor.process(PostDescription.getText());
        PostDescription.addTextChangedListener(MarkwonEditorTextWatcher.withPreRender(
                editor,
                Executors.newCachedThreadPool(),
                PostDescription));

        editor.preRender(PostDescription.getText(), new MarkwonEditor.PreRenderResultListener() {
            @Override
            public void onPreRenderResult(@NonNull MarkwonEditor.PreRenderResult result) {
                // it's wise to check if rendered result is for the same input,
                // for example by matching raw input
                if (PostDescription.getText().toString().equals(result.resultEditable().toString())) {

                    // if you are in background thread do not forget
                    // to execute dispatch in main thread
                    result.dispatchTo(PostDescription.getText());
                }
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        DatePicker = (Button) findViewById(R.id.date_picker);
        DatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(PostActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //Display Selected date in textbox
                                txtDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                                mDay = dayOfMonth;
                                mMonth = (monthOfYear + 1);
                                mYear = year;

                                Log.i("year=", String.valueOf(mYear));
                                Log.i("month=", String.valueOf(mMonth));
                                Log.i("day=", String.valueOf(mDay));

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        md_show = (Button) findViewById(R.id.md_tips);
        md_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dl = new Dialog(context);
                dl.setContentView(R.layout.activity_tips);
                dl.setTitle("Tips");
                Button dialogButton = (Button) dl.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl.dismiss();
                    }
                });
                dl.show();
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }


    private void initViews() {
        mToolbar = findViewById(R.id.update_post_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Update Post");

        loadingBar = new ProgressDialog(this);

        SelectPostImage = findViewById(R.id.select_post_image);
        UpdatePostButton = findViewById(R.id.update_post_button);
        PostDescription = findViewById(R.id.post_description);
    }


    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(2, 1)
                    .start(this);


//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                BitMapPostImage = presenter.resizeBitMap(bitmap, 550, 450);
//                SelectPostImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                SelectPostImage.setImageBitmap(BitMapPostImage);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    BitMapPostImage = presenter.resizeBitMap(bitmap, 550, 450);
                    SelectPostImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    SelectPostImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            sendUserToMainActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendUserToMainActivity() {
        Intent mainIntetn = new Intent(PostActivity.this, MainActivity.class);
        startActivity(mainIntetn);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_post_image:
                openGallery();
                break;
            case R.id.update_post_button:
                presenter.validatePostInfo(this.PostDescription, this.BitMapPostImage, this.spinner, this.longitude, this.latitude, this.mYear, this.mDay, this.mMonth);
                break;
        }
    }

    @Override
    public void onPostFailed(String errorMessage) {
        Toast.makeText(PostActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostSuccess() {
        sendUserToMainActivity();
        Toast.makeText(PostActivity.this, "Post Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingBar(String title, String message) {
        loadingBar.setTitle(title);
        loadingBar.setMessage(message);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    @Override
    public void dismissLoadingBar() {
        loadingBar.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String Category = (String) adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Getting Your Location", Toast.LENGTH_SHORT).show();
        /*
         Return false so that we don't consume the event and the default behavior still occurs
         (the camera animates to the user's current position).
        */
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
    }
}
