package com.example.projectyes;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.Slider;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Page1Fragment extends Fragment {

    EditText email;
    Slider slider;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    TextView price;
    Button button;
    private OnItemAddedListener listener;

    public void sendEmailSingleRecipient(String recipient, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemAddedListener) {
            listener = (OnItemAddedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnItemAddedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order, container, false);
        AtomicInteger final_price = new AtomicInteger(2000);

        SQLhandler sqLhandler = new SQLhandler(getContext());
        SQLiteDatabase db = sqLhandler.getWritableDatabase();

        email = rootView.findViewById(R.id.email);
        slider = rootView.findViewById(R.id.slider);
        slider.setValueFrom(1);
        slider.setValueTo(10);
        slider.setValue(1);
        checkBox1 = rootView.findViewById(R.id.checkbox1);
        checkBox2 = rootView.findViewById(R.id.checkbox2);
        checkBox3 = rootView.findViewById(R.id.checkbox3);
        price = rootView.findViewById(R.id.textviewprice);
        button = rootView.findViewById(R.id.elevatedButton);
        price.setText(final_price.toString());

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                final_price.set((int) (2000 * value));
                price.setText(String.valueOf(final_price.get()));
            }
        });

        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                final_price.addAndGet(200);
            } else {
                final_price.addAndGet(-200);
            }
            price.setText(String.valueOf(final_price.get()));
        });

        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                final_price.addAndGet(300);
            } else {
                final_price.addAndGet(-300);
            }
            price.setText(String.valueOf(final_price.get()));
        });

        checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                final_price.addAndGet(400);
            } else {
                final_price.addAndGet(-400);
            }
            price.setText(String.valueOf(final_price.get()));
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float sliderValue = slider.getValue();
                    Log.d("SliderValue", "Slider value: " + sliderValue);

                    String recipientEmail = email.getText().toString().trim();
                    String emailSubject = "Your Subject Here";
                    String emailBody = "Your Email Body Here";
                    sendEmailSingleRecipient(recipientEmail, emailSubject, emailBody);

                    int amount = (int) sliderValue;
                    int finalPrice = final_price.get();
                    boolean checkbox1Checked = checkBox1.isChecked();
                    boolean checkbox2Checked = checkBox2.isChecked();
                    boolean checkbox3Checked = checkBox3.isChecked();

                    if(!recipientEmail.isEmpty()){
                        sqLhandler.insertData(recipientEmail, amount, finalPrice, checkbox1Checked, checkbox2Checked, checkbox3Checked);
                    }

                    if (listener != null) {
                        listener.onItemAdded();
                    }

                    email.setText("");
                    slider.setValue(1);
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);

                    for(DataItem name : sqLhandler.readData())
                        Log.d("xd", name.getEmail());

                } catch (Exception e) {
                    Log.e("DatabaseError", "Error inserting data into database: " + e.getMessage());
                }
            }
        });

        return rootView;
    }
}