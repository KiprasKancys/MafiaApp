package com.example.kipras.newmafija.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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

        ListView listView = (ListView)findViewById(R.id.listView);
        OptionsAdapter optionsAdapter = new OptionsAdapter(this, R.layout.option_layout, opts);
        listView.setAdapter(optionsAdapter);

    }

    public List<Options> prepOptimalOptions(int numberOfPlayers) {
        List<Options> options = new ArrayList<>();
        int numberOfThisRole = 0;
        for (ROLE role: ROLE.values()){
            switch (role)
            {
                case Mafija:
                    numberOfThisRole = numberOfPlayers / 5;
                    if (numberOfThisRole < 1) {
                        numberOfThisRole++;
                    }
                    break;
                case Policininkas:
                    numberOfThisRole = numberOfPlayers / 5;
                    if (numberOfThisRole < 1) {
                        numberOfThisRole++;
                    }
                    break;
                case Sesele:
                    numberOfThisRole = numberOfPlayers / 6;
                    if (numberOfThisRole < 1) {
                        numberOfThisRole++;
                    }
                    break;
                case Plastake:
                    numberOfThisRole = numberOfPlayers / 7;
                    break;
                case Miestietis:
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
        TextView number = (TextView) findViewById(R.id.number);
        String str = number.getText().toString();
        int num = Integer.valueOf(str);
        num++;
        number.setText(Integer.toString(num));
    }

    public void decrease(View v) {
        TextView number = (TextView)findViewById(R.id.number);
        String str = number.getText().toString();
        int num = Integer.parseInt(str);
        num--;
        number.setText(Integer.toString(num));
    }

    public void done(View view) {
        assignRoles();
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

    private ArrayList<ROLE> pickRoles(int numberOfMafia, int numberOfPolice, int numberOfMedics,
                                      int numberOfPlastake, int numberOfWillagers) {

        ArrayList<ROLE> roles = new ArrayList<>();

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

        for (int i=counter; i < numberOfMafia + numberOfPolice + numberOfMedics + numberOfPlastake;
             i++){
            roles.add(ROLE.Plastake);
            counter++;
        }

        for (int i=counter; i < numberOfMafia + numberOfPolice + numberOfMedics + numberOfPlastake
                + numberOfWillagers; i++){
            roles.add(ROLE.Miestietis);
            counter++;
        }

        Collections.shuffle(roles);

        return roles;
    }

    @Override
    public void onBackPressed() {
    }
}
