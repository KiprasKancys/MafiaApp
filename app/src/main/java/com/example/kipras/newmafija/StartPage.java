package com.example.kipras.newmafija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);
    }

    public void createGame(View view) {
        Intent intent = new Intent(this, CreateGame.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

}

