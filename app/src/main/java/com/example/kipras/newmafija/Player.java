package com.example.kipras.newmafija;

import java.io.Serializable;

/**
 * Created by Kipras on 2016.06.13.
 */
public class Player implements Serializable {

    private String name;
    private ROLE role;
    private int votes;
    private boolean alive;
    private boolean healed;
    private boolean silenced;
    private boolean attempted;
    private boolean guessed;
    private boolean medicSelfHeal;

    public Player(String name1, ROLE role1) {
        this.name = name1;
        this.role = role1;
        this.setVotes(0);
        this.setLife(true);
        this.heal(false);
        this.silence(false);
        this.attempt(false);
        this.guess(false);
        this.medicSelfHeal(false);
    }

    public String getName() { return name; }

    public ROLE getRole() { return role; }

    public int getVotes() { return votes; }

    public boolean isAlive() { return alive; }

    public boolean isHealed() { return healed; }

    public boolean isSilenced() { return silenced; }

    public boolean isAttempted() { return attempted; }

    public boolean isGuessed() { return guessed; }

    public boolean isMedicSelfHeal() { return medicSelfHeal; }

    public void setVotes(int votes) { this.votes = votes; }

    public void increaseVotes() {
        this.votes++;
    }

    public void setLife(boolean value) {
        this.alive = value;
    }

    public void heal(boolean value) {
        this.healed = value;
    }

    public void silence(boolean value) {
        this.silenced = value;
    }

    public void attempt(boolean value) { this.attempted = value; }

    public void guess(boolean value) { this.guessed = value; }

    public void medicSelfHeal(boolean value) { this.medicSelfHeal = value; }

}
