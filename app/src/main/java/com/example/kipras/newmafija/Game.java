package com.example.kipras.newmafija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Collections;

/**
 * Created by Kipras on 2016.06.13.
 */
public class Game extends AppCompatActivity {

    ArrayList<Player> players = new ArrayList<Player>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assignRoles();
    }

    private void assignRoles(){

        ArrayList<String> playerNames = (ArrayList<String>) getIntent().getSerializableExtra("ListOfPlayers");

        int numberOfPlayers = playerNames.size();

        ArrayList<ROLE> roles = pickRoles(playerNames.size(), 1, 1, 1);

        Player player;
        for(int i = 0; i < numberOfPlayers; i++) {
            if (roles.get(i).equals(ROLE.Sesele)){
                player = new Medic(playerNames.get(i), roles.get(i));
            } else {
                player = new Player(playerNames.get(i), roles.get(i));
            }
            players.add(player);
        }

        Intent intent = new Intent(this, Night.class);
        intent.putExtra("ListOfPlayers", players);
        intent.putExtra("Cursor", 0);
        startActivity(intent);
    }

    private ArrayList<ROLE> pickRoles(int numberOfPlayers, int numberOfMafia, int numberOfPolice, int numberOfMedics) {

        ArrayList<ROLE> roles = new ArrayList<ROLE>();

        int counter = 0;

        for (int i=0; i < numberOfMafia; i++){
            roles.add(ROLE.Mafija);
            counter++;
        }

        for (int i=counter; i < numberOfMafia + numberOfPolice; i++){
            roles.add(ROLE.Policininkas);
            counter++;
        }

        for (int i=counter; i < numberOfMafia + numberOfPolice + numberOfMedics; i++){
            roles.add(ROLE.Sesele);
            counter++;
        }

        for (int i=counter; i < numberOfPlayers; i++){
            roles.add(ROLE.Miestietis);
        }

        Collections.shuffle(roles);

        return roles;
    }

    @Override
    public void onBackPressed() {
    }
}
