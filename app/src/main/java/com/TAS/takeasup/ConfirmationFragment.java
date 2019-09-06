package com.TAS.takeasup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TAS.takeasup.Database.Database;
import com.TAS.takeasup.Model.Order;

public class ConfirmationFragment extends Fragment {
    TextView textView1;
    EditText qtyText, cmmnts;
    Button addToCartButton;
    Database database;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirmation, container, false);
        textView1 = view.findViewById(R.id.textView1);
//        qtyText = view.findViewById(R.id.qtyText);
        cmmnts = view.findViewById(R.id.comments);
        addToCartButton = view.findViewById(R.id.cnfrmButton);
//        addToCartButton.setEnabled(false);
        database = new Database(getActivity());

        Bundle bundle = this.getArguments();
        final String nameInTextView = bundle.getString("DishName");
        final String dishPrice = bundle.getString("DishPrice");
        textView1.setText(nameInTextView);

//        qtyText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (qtyText.getText().toString().isEmpty()) {
//                    addToCartButton.setEnabled(false);
//                } else {
//                    addToCartButton.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=1;
                String qtyCnfrm = String.valueOf(i);
                boolean res = database.addToCart(new Order(nameInTextView, dishPrice,qtyCnfrm,i));
                if (res == true) {
                    Toast.makeText(getActivity(), "Added To Cart", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().addToBackStack("").replace(R.id.mainPage, new CartFragment()).commit();
                }else{
                    Toast.makeText(getActivity(), "Selected Dish is already in Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}