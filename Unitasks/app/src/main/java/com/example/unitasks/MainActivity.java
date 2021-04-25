package com.example.unitasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

        public class MainActivity extends AppCompatActivity {
            private Toolbar mToolbar;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                mToolbar = findViewById(R.id.toolbar);
                setSupportActionBar(mToolbar);



            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                super.onCreateOptionsMenu(menu);
                getMenuInflater().inflate(R.menu.main_menu, menu);
                if(menu instanceof MenuBuilder){
                    MenuBuilder m = (MenuBuilder) menu;
                    //noinspection RestrictedApi
                    m.setOptionalIconsVisible(true);
                }
                return true;
            }

        }

