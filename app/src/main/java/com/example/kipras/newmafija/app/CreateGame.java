package com.example.kipras.newmafija.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kipras.newmafija.R;

import java.util.ArrayList;

public class CreateGame extends AppCompatActivity {

    Button addPlayer;
    ArrayList<String> players = new ArrayList<>();
    EditText player;
    ListView showPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game_page);

        player = (EditText)findViewById(R.id.input);

        showPlayers =(ListView)findViewById(R.id.Players);
        addPlayer = (Button)findViewById(R.id.addPlayer);
        addPlayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String getInput = player.getText().toString();

                if(players.contains(getInput)){
                    Toast.makeText(getBaseContext(), "Player already exists", Toast.LENGTH_LONG).show();
                }
                else if(getInput.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    players.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateGame.this, android.R.layout.simple_list_item_1, players);
                    showPlayers.setAdapter(adapter);
                    ((EditText) findViewById(R.id.input)).setText("");
                }
            }
        });
    }

    public void createGame(View view) {
        if (players.size() < 3){
            Toast.makeText(getBaseContext(), "You need at least 3 players", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, Game.class);
            intent.putExtra("ListOfPlayers", players);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
    }

}