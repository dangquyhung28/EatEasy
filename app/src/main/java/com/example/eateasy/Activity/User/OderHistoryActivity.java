package com.example.eateasy.Activity.User;

import android.os.Bundle;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.ListView;
=======
>>>>>>> 42eebc0b4ebc2c14d8c409776930628beb301707

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.R;

public class OderHistoryActivity extends AppCompatActivity {
<<<<<<< HEAD
    ImageView backBtn_history;
    ListView order_list;
=======

>>>>>>> 42eebc0b4ebc2c14d8c409776930628beb301707
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oder_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
<<<<<<< HEAD
        initWidget();

    }
    private void initWidget() {
        backBtn_history = findViewById(R.id.backBtn_history);
        order_list = findViewById(R.id.order_list);
=======
>>>>>>> 42eebc0b4ebc2c14d8c409776930628beb301707
    }
}