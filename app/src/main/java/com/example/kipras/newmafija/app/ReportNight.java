package com.example.kipras.newmafija.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.kipras.newmafija.R;
import com.example.kipras.newmafija.model.ROLE;
import com.example.kipras.newmafija.model.Player;

import java.util.ArrayList;

/**
 * Report after night
 */
public class ReportNight extends AppCompatActivity {

    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);

        players = (ArrayList<Player>) getIntent().getSerializableExtra("ListOfPlayers");

        TextView killed = (TextView)findViewById(R.id.info);
        killed.setText(whoWasKilled());

        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getRole().equals(ROLE.Police) && players.get(i).isAlive()){
                TextView police = (TextView)findViewById(R.id.info2);
                police.setText(policeWereRight());
            }
        }

        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getRole().equals(ROLE.Butterfly) && players.get(i).isAlive()){
                TextView butterfly = (TextView)findViewById(R.id.info3);
                butterfly.setText(whoWasSilenced());
            }
        }

        String result = isThereWinner();
        if(!result.equals("")){
            TextView winner = (TextView)findViewById(R.id.info4);
            winner.setText(result);
        } else {
            cleanUp();
        }
    }

    private String isThereWinner(){

        int mafia = 0;
        int other = 0;

        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isAlive()){
                if(players.get(i).getRole().equals(ROLE.Mafia)){
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
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isAttempted() && !players.get(i).isHealed()){
                players.get(i).setLife(false);
                return "Died " + players.get(i).getName() + ". The role was " +
                        players.get(i).getRole().toString() ;
            }
        }
        return "Nobody died";
    }

    private String policeWereRight(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isGuessed() && players.get(i).getRole().equals(ROLE.Mafia)){
                return("Police guessed right");
            }
        }
        return "Police guessed wrong";
    }

    private String whoWasSilenced(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isSilenced()){
                return(players.get(i).getName() + " is silenced");
            }
        }
        return "";
    }

    private void cleanUp(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).isHealed()){
                players.get(i).heal(false);
            }
            if(players.get(i).isAttempted()){
                players.get(i).attempt(false);
            }
            if(players.get(i).isGuessed()){
                players.get(i).guess(false);
            }
        }
    }

    public void next(View view) {
        boolean notFound = true;
        int cursor = -1;
        while (notFound) {
            cursor++;
            if (players.get(cursor).isAlive()) {
                notFound = false;
                Intent intent = new Intent(this, Day.class);
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
