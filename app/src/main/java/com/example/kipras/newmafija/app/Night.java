package com.example.kipras.newmafija.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kipras.newmafija.model.Player;
import com.example.kipras.newmafija.R;
import com.example.kipras.newmafija.model.ROLE;
import com.example.kipras.newmafija.model.Medic;

import java.util.ArrayList;

/**
 * Night activity
 */
public class Night extends AppCompatActivity {

    ArrayList<Player> players;
    int cursor;

    Button show;
    Button activity;
    ArrayList<String> listOfPlayerNames = new ArrayList<>();
    TextView name;
    TextView role;
    ListView playersList;

    ROLE currentRole;

    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_page);

        players = (ArrayList<Player>) getIntent().getSerializableExtra("ListOfPlayers");
        cursor = getIntent().getExtras().getInt("Cursor");


        name = (TextView)findViewById(R.id.name);
        name.setText(players.get(cursor).getName());

        role = (TextView)findViewById(R.id.role);
        playersList =(ListView)findViewById(R.id.players);
        show = (Button)findViewById(R.id.show);
        activity = (Button)findViewById(R.id.activity);


        show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!clicked){

                    clicked = true;

                    currentRole = players.get(cursor).getRole();

                    role.append(currentRole.toString());

                    getShowPlayers(currentRole, players.get(cursor).getName());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Night.this, android.R.layout.simple_list_item_single_choice, listOfPlayerNames);
                    playersList.setAdapter(adapter);

                    activity.setVisibility(View.VISIBLE);

                    playersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String targetName = ((TextView) view).getText().toString();

                            activities(currentRole, getPlayerByName(targetName));

                        }
                    });
                }

            }
        });
    }

    public void next(View view) {
        boolean notFound = true;
        while (notFound) {
            cursor++;
            if (players.size() == cursor) {
                notFound = false;
                Intent intent = new Intent(this, ReportNight.class);
                intent.putExtra("ListOfPlayers", players);
                startActivity(intent);
            } else {
                if (players.get(cursor).isAlive()) {
                    notFound = false;
                    Intent intent = new Intent(this, Night.class);
                    intent.putExtra("ListOfPlayers", players);
                    intent.putExtra("Cursor", cursor);
                    startActivity(intent);
                }
            }
        }
    }

    private void activities(ROLE role, Player player) {
        switch (role)
        {
            case Mafia:
                activity.setText(R.string.kill);
                mafiaActivity(player);
                break;
            case Police:
                activity.setText(R.string.investigate);
                policeActivity(player);
                break;
            case Medic:
                activity.setText(R.string.heal);
                medicActivity(role, player);
                break;
            case Butterfly:
                activity.setText(R.string.dingdong);
                ButterflyActivity(player);
                break;
            case Villager:
                activity.setText(R.string.next);
                break;
        }
    }

    private void mafiaActivity(Player player) {
        player.attempt(true);
    }

    private void policeActivity(Player player) {
        player.guess(true);
    }

    private void medicActivity(ROLE role, Player player) {
        if (!(role.equals(ROLE.Medic) && player.isMedicSelfHeal())){
            player.heal(true);
            player.medicSelfHeal(true);
        }
    }

    private void ButterflyActivity(Player player) {
        player.silence(true);
    }

    private Player getPlayerByName(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)){
                return(players.get(i));
            }
        }
        return null;
    }

    private void getShowPlayers(ROLE role, String name){
        if (role.equals(ROLE.Medic)){
            Medic medic = (Medic) players.get(cursor);
            if (!medic.isMedicHeal()){
                for(int i = 0; i < players.size(); i++){
                    if(players.get(i).isAlive()){
                        listOfPlayerNames.add(players.get(i).getName());
                    }
                }
            }
        } else {
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).isAlive() && !players.get(i).getName().equals(name)){
                    listOfPlayerNames.add(players.get(i).getName());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
    }

}
