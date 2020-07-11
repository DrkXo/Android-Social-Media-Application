package com.example.ebsma.basic.views.main;

import android.content.Context;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ebsma.basic.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.commonmark.node.Node;

import de.hdodenhof.circleimageview.CircleImageView;
import io.noties.markwon.Markwon;

import static java.sql.Types.NULL;

public class PostsViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

    public View mView;
    public LinearLayout likeClick;
    public LinearLayout commentClick;
    public ImageButton likePostButton;
    public ImageButton commentPostButton;
    public CircleImageView postProfileImage;
    TextView displayNoOfLikes;
    TextView displayNoOfComments;
    public TextView txtFullName;
    int countLikes;
    public MapView mpView;
    public GoogleMap googleMap;
    int countComments;
    String currentUserID;
    DatabaseReference LikesRef;
    DatabaseReference PostsRef;


    public PostsViewHolder(View itemView) {
        super(itemView);
        mView = itemView;


        mpView = (MapView) mView.findViewById(R.id.p_mapview);
        mpView.onCreate(null);
        mpView.getMapAsync(this);
        mpView.onResume();
        likePostButton = mView.findViewById(R.id.like_button);
        commentPostButton = mView.findViewById(R.id.comment_button);
        displayNoOfLikes = mView.findViewById(R.id.display_no_of_likes);
        displayNoOfComments = mView.findViewById(R.id.display_no_of_comments);
        postProfileImage = mView.findViewById(R.id.post_profile_image);
        likeClick = mView.findViewById(R.id.click_like_layout);
        commentClick = mView.findViewById(R.id.click_comment_layout);
        LikesRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    public void setLikeButtonStatus(final String PostKey){
        LikesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(PostKey).hasChild(currentUserID)){
                    countLikes = (int) dataSnapshot.child(PostKey).getChildrenCount();
                    likePostButton.setImageResource(R.drawable.like);
                    if (countLikes > 0) {
                        if (countLikes > 1)
                        {
                            displayNoOfLikes.setText(Integer.toString(countLikes) + " Likes");
                        }
                        else {
                            displayNoOfLikes.setText(Integer.toString(countLikes) + " Like");
                        }
                    }
                    else{
                        displayNoOfLikes.setText("Like");
                    }
                }
                else {
                    countLikes = (int) dataSnapshot.child(PostKey).getChildrenCount();
                    likePostButton.setImageResource(R.drawable.dislike);
                    if (countLikes > 0) {
                        if (countLikes > 1)
                        {
                            displayNoOfLikes.setText(Integer.toString(countLikes) + " Likes");
                        }
                        else {
                            displayNoOfLikes.setText(Integer.toString(countLikes) + " Like");
                        }
                    }
                    else{
                        displayNoOfLikes.setText("Like");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void displaysCommentsNumber(final String PostKey) {
        PostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(PostKey).hasChild("Comments")){
                    countComments = (int) dataSnapshot.child(PostKey).child("Comments").getChildrenCount();
                    if (countComments == 0) {
                        displayNoOfComments.setText(Integer.toString(countComments) + " Comment");
                    }
                    else if (countComments > 1)
                    {
                        displayNoOfComments.setText(Integer.toString(countComments) + " Comments");
                    }
                    else{
                        displayNoOfComments.setText(Integer.toString(countComments) + " Comment");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void setProfileimage(String profileimage) {
        CircleImageView image = mView.findViewById(R.id.post_profile_image);
        Picasso.get().load(profileimage).into(image);
    }

    public void setDefaultProfileimage() {
        CircleImageView image = mView.findViewById(R.id.post_profile_image);
        image.setImageResource(R.drawable.profile);
    }

    public void setDescription(String description, Context mContext) {
        TextView postDescription = mView.findViewById(R.id.post_description);
//        postDescription.setText(description);

        // obtain an instance of Markwon
        final Markwon markwon = Markwon.create(mContext);


        final Node node = markwon.parse(description);
        final Spanned markdown = markwon.render(node);
// set markdown
        markwon.setParsedMarkdown(postDescription, markdown);

    }

    public void setPostimage(String postimage) {
        ImageView postImage = mView.findViewById(R.id.post_image);
        Picasso.get().load(postimage).into(postImage);
    }

    public void setDate(String date) {
        TextView postDate = mView.findViewById(R.id.post_date);
        postDate.setText("   " + date);
    }

    public void setTime(String time) {
        TextView postTime = mView.findViewById(R.id.post_time);
        String timeWithoutSecond = time.substring(0, 5);
        postTime.setText("   " + timeWithoutSecond);
    }

    public void setFullName(String fullName) {
        txtFullName = mView.findViewById(R.id.post_user_name);
        txtFullName.setText(fullName);
    }

    public void setCat(String Category) {
        TextView haha = mView.findViewById(R.id.dis_cat);
        haha.setText("#" + Category);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        // googleMap.setLatLngBoundsForCameraTarget(null);

        googleMap.setMinZoomPreference(6.0f);
        googleMap.setMaxZoomPreference(17.0f);
    }


    public void displaymap(final String PostKey) {

        PostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Current Key", PostKey);
                Log.i("Database:", String.valueOf(PostsRef));

                String lat = String.valueOf(dataSnapshot.child(PostKey).child("latitude").getValue());
                Log.i("lat=", lat);
                String lng = String.valueOf(dataSnapshot.child(PostKey).child("longitude").getValue());
                Log.i("lng=", lng);
                double latitude = Double.parseDouble(lat);
                Log.i("new lat=", String.valueOf(latitude));
                double longitude = Double.parseDouble(lng);
                Log.i("new lng=", String.valueOf(longitude));
                LatLng ltlg = new LatLng(latitude, longitude);
                Log.i("LatLng=", String.valueOf(ltlg));

                if (latitude == NULL) {
                    mpView.setVisibility(View.GONE);
                }

                googleMap.addMarker(new MarkerOptions()
                        .position(ltlg).title("This is where it was !")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.locationpin1)));

                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(ltlg));

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ltlg, 15));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void set_Event_Month(int Event_Month) {
        TextView e_month = mView.findViewById(R.id.evnt_mnth);
        e_month.setText(String.valueOf(Event_Month) + "-");
        if (Event_Month == NULL) {
            e_month.setVisibility(View.GONE);
        }
    }

    public void set_Event_Year(int Event_Year) {
        TextView e_year = mView.findViewById(R.id.evnt_yr);
        e_year.setText(String.valueOf(Event_Year) + ".");
        if (Event_Year == NULL) {
            e_year.setVisibility(View.GONE);
        }
    }

    public void set_Event_Date(int Event_Date) {
        TextView e_date = mView.findViewById(R.id.evnt_date);
        e_date.setText(String.valueOf(Event_Date) + "-");

        if (Event_Date == NULL) {
            e_date.setVisibility(View.GONE);
            LinearLayout L1 = mView.findViewById(R.id.evnt_date_layout);
            L1.setVisibility(View.INVISIBLE);
        }
    }
}
