package com.TAS.takeasup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Common.Session;
import com.TAS.takeasup.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up extends Fragment
{
    private EditText e3,e4,e5,e6,e7;
    private Button b2;
    private DatabaseReference dbr;
    private FirebaseAuth auth;
    private Session session;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sign__up, container, false);

        e3 = v.findViewById(R.id.editText3);
        e4 = v.findViewById(R.id.editText4);
        e5 = v.findViewById(R.id.editText5);
        e6 = v.findViewById(R.id.editText6);
        e7 = v.findViewById(R.id.editText7);
        b2 = v.findViewById(R.id.button2);

        dbr= FirebaseDatabase.getInstance().getReference("Users");
        auth= FirebaseAuth.getInstance();
        pref = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = pref.edit();
        session = new Session(getActivity());

        if(session.loggedIn()){
            getFragmentManager().beginTransaction().replace(R.id.mainPage,new HomePageFragment()).commit();
        }

        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInternet(getActivity())) {

                    final String username = e3.getText().toString();
                    final String userphone = e4.getText().toString();
                    final String useremail = e5.getText().toString();
                    String usernewpass = e6.getText().toString();
                    String cnfrm = e7.getText().toString();

                    e3.setText("");
                    e4.setText("");
                    e5.setText("");
                    e6.setText("");
                    e7.setText("");

                    if (username.isEmpty() && userphone.isEmpty() && useremail.isEmpty() && usernewpass.isEmpty() && cnfrm.isEmpty()) {
                        e3.setError("Enter your Name");
                        e4.setError("Enter your Number");
                        e5.setError("Enter Valid Email ID");
                        e6.setError("Can't be Empty");
                        e7.setError("Can't be Empty");
                    } else if (username.isEmpty()) {
                        e3.setError("Enter your Name");
                    } else if (userphone.isEmpty()) {
                        e4.setError("Enter your Number");
                    } else if (useremail.isEmpty()) {
                        e5.setError("Enter Valid Email ID");
                    } else if (usernewpass.isEmpty()) {
                        e6.setError("Can't be Empty");
                    } else if (cnfrm.isEmpty()) {
                        e7.setError("Can't be Empty");
                    } else if (!usernewpass.equals(cnfrm)) {
                        Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_SHORT).show();
                    } else {
                        auth.createUserWithEmailAndPassword(useremail, usernewpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                session.setLoggedIn(true);

                                FirebaseUser user = auth.getCurrentUser();
                                String uid = user.getUid();

                                editor.putString("uid", uid);
                                editor.commit();

                                Users info = new Users();
                                info.setUser_name(username);
                                info.setPhone(userphone);
                                info.setEmail(useremail);
                                info.setUid(uid);

                                dbr.child(uid).setValue(info);

                                getFragmentManager().beginTransaction().replace(R.id.mainPage, new HomePageFragment()).commit();
                                Toast.makeText(getActivity(), "Thank You", Toast.LENGTH_SHORT).show();

                                e3.setText("");
                                e4.setText("");
                                e5.setText("");
                                e6.setText("");
                                e7.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    Toast.makeText(getActivity(), "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        return v;
    }

}
