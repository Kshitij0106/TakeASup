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
import android.widget.TextView;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Common.Session;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Page extends Fragment {
    private EditText e1, e2;
    private Button b1;
    private TextView t1,t2;
    private Session session;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login__page, container, false);

        pref = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = pref.edit();

        e1 = v.findViewById(R.id.editText);
        e2 = v.findViewById(R.id.editText2);
        b1 = v.findViewById(R.id.button);
        t1 = v.findViewById(R.id.textView);
        t2 = v.findViewById(R.id.skiptext);
        auth = FirebaseAuth.getInstance();
        session = new Session(getActivity());

        if(session.loggedIn()){
            getFragmentManager().beginTransaction().replace(R.id.mainPage,new HomePageFragment()).commit();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInternet(getActivity())) {

                    String userId = e1.getText().toString();
                    String pass = e2.getText().toString();

                    e1.setText("");
                    if (userId.isEmpty() && pass.isEmpty()) {
                        e1.setError("Enter Email Id");
                        e2.setError("Enter Password");
                    } else if (userId.isEmpty()) {
                        e1.setError("Enter Email Id");
                    } else if (pass.isEmpty()) {
                        e2.setError("Enter Password");
                    } else {
                        auth.signInWithEmailAndPassword(userId, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                session.setLoggedIn(true);

                                FirebaseUser users = auth.getCurrentUser();
                                String uid = users.getUid();

                                editor.putString("uid", uid);
                                editor.commit();

                                getFragmentManager().beginTransaction().replace(R.id.mainPage, new HomePageFragment()).commit();
                                Toast.makeText(getActivity(), "Welcome ", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e2.setText("");
                                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        e1.setText("");
                        e2.setText("");
                    }
                }else{
                    Toast.makeText(getActivity(), "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainPage,new Sign_Up()).addToBackStack("").commit();
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.mainPage,new HomePageFragment()).commit();
            }
        });
        return v;
    }
}
