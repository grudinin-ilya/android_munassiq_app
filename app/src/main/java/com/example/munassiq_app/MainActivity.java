package com.example.munassiq_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText unit, result;

    ImageButton imgBtnDo, imgBtnShow, imgBtnSave;

    Spinner spinner;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference fDatabaseRoot = database.getReference();
    RadioGroup radios;
    RadioButton radioButtonAbsent, radioButtonNotNead;
    ViewPager viewPager;
    SliderAdapter sliderAdapter;

    List<String> name;
    List<String> address;

    TextView tvTitle;
    TextView tvDescription;
    ImageView ivIcon;

    public void showDialog(int title, int des, int icon) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);

        tvTitle = dialog.findViewById(R.id.title);
        tvDescription = dialog.findViewById(R.id.description);
        ivIcon = dialog.findViewById(R.id.icon);

        tvTitle.setText(title);
        tvDescription.setText(des);
        ivIcon.setImageResource(icon);

        dialog.show();
    }

    public void hideKeybord() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        unit = findViewById(R.id.unit);
        imgBtnDo = findViewById(R.id.imgBtnDo);
        imgBtnShow = findViewById(R.id.imgBtnShow);
        imgBtnSave = findViewById(R.id.imgBtnSave);
        spinner = findViewById(R.id.spinner);
        radios = findViewById(R.id.radios);
        radioButtonAbsent = findViewById(R.id.radioButtonAbsent);
        radioButtonNotNead = findViewById(R.id.radioButtonNotNead);
        viewPager = findViewById(R.id.viewPager);

//        sliderAdapter = new SliderAdapter(this);
//        viewPager.setAdapter(sliderAdapter);


        imgBtnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unit.length() != 2) {
                    showDialog(R.string.resultErrorTitle, R.string.noUnit, R.drawable.error);
                } else {
                    fDatabaseRoot.child("Units").child(unit.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final List<String> studNames = new ArrayList<String>();

                            for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                                String names = areaSnapshot.getValue(String.class);
                                studNames.add(names);
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, studNames);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(arrayAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    hideKeybord();
                }
            }
        });

        imgBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (unit.length() != 2) {
                    showDialog(R.string.resultErrorTitle, R.string.noUnit, R.drawable.error);
                } else {

                    fDatabaseRoot.child("Lists").child(unit.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            name = new ArrayList<String>();
                            address = new ArrayList<String>();
                            for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                                Module module = areaSnapshot.getValue(Module.class);
                                name.add(module.getName());
                                address.add(module.getAddress());
                            }

                            Intent intent = new Intent(MainActivity.this, DataActivity.class);

                            intent.putExtra("name", name.toString());
                            intent.putExtra("address", address.toString());

                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemPosition() < 0) {
                    showDialog(R.string.resultErrorTitle, R.string.resultErrorSpinner, R.drawable.error);
                    radios.clearCheck();
                    result.setText("");
                } else if ((!radioButtonAbsent.isChecked() && !radioButtonNotNead.isChecked()) && result.length() == 0) {
                    showDialog(R.string.resultErrorTitle, R.string.resultErrorDesc, R.drawable.error);
                } else if ((radioButtonAbsent.isChecked() || radioButtonNotNead.isChecked()) && result.length() != 0) {
                    showDialog(R.string.resultErrorTitle, R.string.resultNotRight, R.drawable.error);
                    radios.clearCheck();
                    result.setText("");
                } else if (radioButtonAbsent.isChecked() && spinner.getSelectedItem().toString().length() > 0) {
                    database.getReference("Results").child(unit.getText().toString()).child(spinner.getSelectedItem().toString()).setValue(radioButtonAbsent.getText());
                    result.setText("");
                    radios.clearCheck();
                    showDialog(R.string.doneTitle, R.string.doneDesc, R.drawable.thumb_up);
                } else if (radioButtonNotNead.isChecked() && spinner.getSelectedItem().toString().length() > 0) {
                    database.getReference("Results").child(unit.getText().toString()).child(spinner.getSelectedItem().toString()).setValue(radioButtonNotNead.getText());
                    result.setText("");
                    radios.clearCheck();
                    showDialog(R.string.doneTitle, R.string.doneDesc, R.drawable.thumb_up);
                } else if (result.length() != 0 && spinner.getSelectedItem().toString().length() > 0) {
                    database.getReference("Results").child(unit.getText().toString()).child(spinner.getSelectedItem().toString()).setValue(result.getText().toString());
                    hideKeybord();
                    result.setText("");
                    radios.clearCheck();
                    showDialog(R.string.doneTitle, R.string.doneDesc, R.drawable.thumb_up);
                }
            }
        });
    }
}
