package com.example.kipras.newmafija;

/**
 * Created by Kipras on 2016.06.15.
 */
public class Medic extends Player {

    private boolean medicHeal;

    public Medic(String name1, ROLE role1) {
        super(name1, role1);
        this.medicHeal = false;
    }

    public boolean isMedicHeal() { return medicHeal; }

    public void medicHeal(boolean value) { this.medicHeal = value; }
}
