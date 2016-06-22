package com.example.kipras.newmafija.model;

public class Medic extends Player {

    private boolean medicHeal;

    public Medic(String name1, ROLE role1) {
        super(name1, role1);
        this.medicHeal = false;
    }

    public boolean isMedicHeal() { return medicHeal; }

    public void medicHeal(boolean value) { this.medicHeal = value; }
}
