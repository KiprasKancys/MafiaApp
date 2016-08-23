package com.example.kipras.newmafija.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kipras.newmafija.model.Player;
import com.example.kipras.newmafija.R;

import java.util.ArrayList;

/**
 * Created by Kipras on 2016.06.14. wups
 */
public class Day extends AppCompatActivity {

    ArrayList<Player> players;
    int cursor;
    ArrayList<String> listOfPlayerNames = new ArrayList<String>();
    ListView playersList;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_page);

        players = (ArrayList<Player>) getIntent().getSerializableExtra("ListOfPlayers");
        cursor = getIntent().getExtras().getInt("Cursor");

        name = (TextView)findViewById(R.id.name);
        name.setText(players.get(cursor).getName());
        playersList =(ListView)findViewById(R.id.players);

        getShowPlayers(players.get(cursor).getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Day.this, android.R.layout.simple_list_item_single_choice, listOfPlayerNames);
        playersList.setAdapter(adapter);

        playersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String targetName = ((TextView) view).getText().toString();

                Player targetedPlayer = getPlayerByName(targetName);

                targetedPlayer.increaseVotes();

            }
        });

    }

    public void next(View view) {
        boolean notFound = true;
        while (notFound) {
            cursor++;
            if (players.size() == cursor) {
                notFound = false;
                Intent intent = new Intent(this, ReportDay.class);
                intent.putExtra("ListOfPlayers", players);
                startActivity(intent);
            } else {
                if (players.get(cursor).isAlive()) {
                    notFound = false;
                    Intent intent = new Intent(this, Day.class);
                    intent.putExtra("ListOfPlayers", players);
                    intent.putExtra("Cursor", cursor);
                    startActivity(intent);
                }
            }
        }
    }

    private void getShowPlayers(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isAlive() && !players.get(i).getName().equals(name)){
                listOfPlayerNames.add(players.get(i).getName());
            }
        }
    }

    private Player getPlayerByName(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)){
                return(players.get(i));
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
    }
}
