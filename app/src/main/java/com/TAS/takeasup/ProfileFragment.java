package com.TAS.takeasup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Common.Session;
import com.TAS.takeasup.Database.Database;
import com.TAS.takeasup.Database.FavouritesDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView nameText, phoneText, emailText;
    Button logOutButton;
    ImageView profileImage, editName, editPhone;
    DatabaseReference ref;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Database database;
    public String userId;
    FavouritesDatabase favouritesDatabase;
    Uri filepath;
    private SwipeRefreshLayout refreshLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameText = view.findViewById(R.id.profileUserName);
        phoneText = view.findViewById(R.id.profilePhoneNo);
        emailText = view.findViewById(R.id.profileEmailId);
        profileImage = view.findViewById(R.id.profilePic);
        editName = view.findViewById(R.id.editUserName);
        editName.setOnClickListener(this);
        editPhone = view.findViewById(R.id.editPhoneNo);
        editPhone.setOnClickListener(this);
        logOutButton = view.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(this);
        refreshLayout = view.findViewById(R.id.swipeLayoutProfile);

        database = new Database(getActivity());
        favouritesDatabase = new FavouritesDatabase(getActivity());

        pref = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("uid", "");

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });

        load();
        return view;
    }

    public void load() {
        if (Common.isConnectedToInternet(getActivity())) {

            refreshLayout.setRefreshing(false);

            ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String uid = data.child("uid").getValue(String.class);

                        if (userId.equals(uid)) {

                            String profileName = data.child("user_name").getValue(String.class);
                            String profilePhone = data.child("phone").getValue(String.class);
                            String profileEmail = data.child("email").getValue(String.class);
                            String profilePic = data.child("pic").getValue(String.class);

                            nameText.setText(profileName);
                            phoneText.setText(profilePhone);
                            emailText.setText(profileEmail);
                            try {
                                Picasso.get().load(profilePic).fit().centerCrop().into(profileImage);
                            } catch (Exception e) {
                                Picasso.get().load(R.drawable.ic_add_a_photo).into(profileImage);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == editName) {
            editUserName();
        } else if (view == editPhone) {
            editPhone();
        } else if (view == profileImage) {

        } else if (view == logOutButton) {
            logOut();
        }
    }

    public void editUserName() {
        showAlertDialog("user_name");
    }

    private void editPhone() {
        showAlertDialog("phone");
    }

    public void logOut() {
        database.cleanCart();
        favouritesDatabase.cleanFavourites();
        Session session = new Session(getActivity());
        session.setLoggedIn(false);
        Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showAlertDialog(final String key) {
        AlertDialog.Builder editProfile = new AlertDialog.Builder(getActivity());
        editProfile.setIcon(R.drawable.ic_edit);
        editProfile.setTitle("Edit " + key);
        editProfile.setMessage("Enter new " + key);
        final EditText change = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        change.setLayoutParams(lp);
        editProfile.setView(change);
        editProfile.setCancelable(false);
        editProfile.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newText = change.getText().toString();
                if (!TextUtils.isEmpty(newText)) {
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, newText);
                    ref.child(userId).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), key + " updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please Enter Correct " + key, Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        editProfile.show();
    }

    public void selectPic() {
        Intent intent = new Intent();
        intent.setType("/image*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            profileImage.setImageURI(filepath);
        } else {
            Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


//    public String getFileExt(Uri filepath){
//        ContentResolver cr = getActivity().getContentResolver();
//        String ext = cr.getType(filepath);
//        MimeTypeMap map = MimeTypeMap.getSingleton();
//        map.getExtensionFromMimeType(ext);
//        return ext;
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == 101 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
//            filepath = data.getData();
//            profileImage.setImageURI(filepath);
//        }else{
//            Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }