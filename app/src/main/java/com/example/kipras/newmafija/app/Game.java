package com.example.kipras.newmafija.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kipras.newmafija.R;
import com.example.kipras.newmafija.model.Medic;
import com.example.kipras.newmafija.model.Player;
import com.example.kipras.newmafija.model.ROLE;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

/**
 * Game initialization
 */
public class Game extends AppCompatActivity {

    ArrayList<Player> players = new ArrayList<>();
    ArrayList<String> playerNames;
    int numberOfPlayers;
    List<Options> opts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_page);

        playerNames = (ArrayList<String>) getIntent().getSerializableExtra("ListOfPlayers");
        numberOfPlayers = playerNames.size();
        opts = prepOptimalOptions(numberOfPlayers);
        OptionsAdapter optionsAdapter = new OptionsAdapter(this, R.layout.option_layout, opts);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(optionsAdapter);

    }

    public List<Options> prepOptimalOptions(int numberOfPlayers) {
        List<Options> options = new ArrayList<>();
        int numberOfThisRole = 0;
        for (ROLE role: ROLE.values()){
            switch (role)
            {
                case Mafia:
                    numberOfThisRole = numberOfPlayers / 5;
                    if (numberOfThisRole < 1) {
                        numberOfThisRole++;
                    }
                    break;
                case Police:
                    numberOfThisRole = numberOfPlayers / 5;
                    if (numberOfThisRole < 1) {
                        numberOfThisRole++;
                    }
                    break;
                case Medic:
                    numberOfThisRole = numberOfPlayers / 6;
                    if (numberOfThisRole < 1) {
                        numberOfThisRole++;
                    }
                    break;
                case Butterfly:
                    numberOfThisRole = numberOfPlayers / 7;
                    break;
                case Villager:
                    int sumOfRolesInGame = 0;
                    for (Options op: options){
                        sumOfRolesInGame += Integer.parseInt(op.getNumber());
                    }
                    numberOfThisRole = numberOfPlayers - sumOfRolesInGame;
                    break;
            }

            Options opt = new Options(role.toString(), String.valueOf(numberOfThisRole));
            options.add(opt);
        }
        return options;
    }

    public void increase(View v) {
        TextView number = (TextView) ((View) v.getParent()).findViewById(R.id.number);
        String str = number.getText().toString();
        int num = Integer.valueOf(str);
        num++;
        if (num > numberOfPlayers){
            Toast.makeText(getBaseContext(), "Too many", Toast.LENGTH_LONG).show();
        } else {
            number.setText(Integer.toString(num));
            TextView role = (TextView) ((View) v.getParent()).findViewById(R.id.role);
            String s = role.getText().toString();
            for (Options op: opts){
                if(op.getRole().equals(s)){
                    op.setNumber(num);
                }
            }
        }
    }

    public void decrease(View v) {
        TextView number = (TextView) ((View) v.getParent()).findViewById(R.id.number);
        String str = number.getText().toString();
        int num = Integer.parseInt(str);
        if (num > 0){
            num--;
            number.setText(Integer.toString(num));
            TextView role = (TextView) ((View) v.getParent()).findViewById(R.id.role);
            String s = role.getText().toString();
            for (Options op: opts){
                if(op.getRole().equals(s)){
                    op.setNumber(num);
                }
            }
        }
    }

    public void done(View view) {
        checkNumberOfRoles();
    }

    private void checkNumberOfRoles(){

        int counter = 0;

        for (Options op: opts){
            counter += Integer.valueOf(op.getNumber());
        }

        if(counter == numberOfPlayers){
            assignRoles();
        } else {
            if(counter > numberOfPlayers){
                Toast.makeText(getBaseContext(), "Too many roles", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Not enough roles", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void assignRoles(){

        ArrayList<Integer> numbers = new ArrayList<>();
        for(Options op: opts){
            numbers.add(Integer.valueOf(op.getNumber()));
        }

        ArrayList<ROLE> roles = pickRoles(numbers.get(0), numbers.get(1), numbers.get(2),
                numbers.get(3), numbers.get(4));

        Player player;
        for(int i = 0; i < numberOfPlayers; i++) {
            if (roles.get(i).equals(ROLE.Medic)){
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

    private ArrayList<ROLE> pickRoles(int numberOfMafia, int numberOfPolice, int numberOfMedics,
                                      int numberOfPlastake, int numberOfWillagers) {

        ArrayList<ROLE> roles = new ArrayList<>();

        int counter = 0;

        for (int i=0; i < numberOfMafia; i++){
            roles.add(ROLE.Mafia);
            counter++;
        }

        for (int i=counter; i < numberOfMafia + numberOfPolice; i++){
            roles.add(ROLE.Police);
            counter++;
        }

        for (int i=counter; i < numberOfMafia + numberOfPolice + numberOfMedics; i++){
            roles.add(ROLE.Medic);
            counter++;
        }

        for (int i=counter; i < numberOfMafia + numberOfPolice + numberOfMedics + numberOfPlastake;
             i++){
            roles.add(ROLE.Butterfly);
            counter++;
        }

        for (int i=counter; i < numberOfMafia + numberOfPolice + numberOfMedics + numberOfPlastake
                + numberOfWillagers; i++){
            roles.add(ROLE.Villager);
            counter++;
        }

        Collections.shuffle(roles);

        return roles;
    }

    @Override
    public void onBackPressed() {
    }
}
