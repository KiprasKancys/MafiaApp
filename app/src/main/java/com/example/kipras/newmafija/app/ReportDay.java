package com.example.kipras.newmafija.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kipras.newmafija.R;
import com.example.kipras.newmafija.model.ROLE;
import com.example.kipras.newmafija.model.Player;

import java.util.ArrayList;

/**
 * Report after day
 */
public class ReportDay extends AppCompatActivity {

    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);

        players = (ArrayList<Player>) getIntent().getSerializableExtra("ListOfPlayers");

        TextView killed = (TextView)findViewById(R.id.info);
        killed.setText(whoWasKilled());

        String result = isThereWinner();
        if(!result.equals("")){
            TextView winner = (TextView)findViewById(R.id.info2);
            winner.setText(result);
        } else {
            cleanUp();
            Button next = (Button)findViewById(R.id.button);
            next.setText("Next");
        }

    }

    private String isThereWinner(){

        int mafia = 0;
        int other = 0;

        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isAlive()){
                if(players.get(i).getRole().equals(ROLE.Mafija)){
                    mafia++;
                } else {
                    other++;
                }
            }
        }

        if (mafia == 0){
            findViewById(R.id.button2).setVisibility(View.INVISIBLE);
            findViewById(R.id.button).setVisibility(View.VISIBLE);
            return("Villagers wins");
        }

        if(other == 0 || mafia == other){
            findViewById(R.id.button2).setVisibility(View.INVISIBLE);
            findViewById(R.id.button).setVisibility(View.VISIBLE);
            return("Mafia wins");

        }

        return "";

    }

    private String whoWasKilled(){

        int max = 0;

        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getVotes() > max){
                max = players.get(i).getVotes();
            }
        }

        int candidate = 0;
        int count = 0;

        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getVotes() == max){
                candidate = i;
                count++;
            }
        }

        if(count == 1){
            players.get(candidate).setLife(false);
            return "Died " + players.get(candidate).getName() + ". The role was " +
                    players.get(candidate).getRole().toString();
        } else {
            return "Nobody died";
        }

    }

    private void cleanUp(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).setVotes(0);
        }
    }

    public void next(View view) {
        boolean notFound = true;
        int cursor = -1;
        while (notFound) {
            cursor++;
            if (players.get(cursor).isAlive()) {
                notFound = false;
                Intent intent = new Intent(this, Night.class);
                intent.putExtra("ListOfPlayers", players);
                intent.putExtra("Cursor", cursor);
                startActivity(intent);
            }
        }
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
    }
}
