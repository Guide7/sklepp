package com.example.projectyes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.Slider;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnItemAddedListener {

    NavigationBarView navigationBarView;
    ViewPager2 viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(this));
        navigationBarView = findViewById(R.id.bottom_navigation);

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_1:
                        viewPager.setCurrentItem(0, true);
                        return true;
                    case R.id.item_2:
                        viewPager.setCurrentItem(1, true);
                        return true;
                    case R.id.item_3:
                        viewPager.setCurrentItem(2, true);
                        return true;
                    default:
                        return false;
                }
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        navigationBarView.setSelectedItemId(R.id.item_1);
                        break;
                    case 1:
                        navigationBarView.setSelectedItemId(R.id.item_2);
                        break;
                    case 2:
                        navigationBarView.setSelectedItemId(R.id.item_3);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    @Override
    public void onItemAdded() {
        Page2Fragment page2Fragment = (Page2Fragment) getSupportFragmentManager().findFragmentByTag("Page2Fragment");
        page2Fragment.refreshListView();
    }
}

