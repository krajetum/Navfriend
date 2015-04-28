package com.nightfall.navfriend.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dev on 28/04/2015.
 */
public class Travel implements Serializable {

    private int ID;
    private String owner;
    private String descrizione;
    private Coordinates destinazione;
    private List<User> guest;

    public Travel(String owner, String descrizione, Coordinates destinazione){
        this.owner = owner;
        this.descrizione = descrizione;
        this.destinazione = destinazione;
    }

    public Travel(String owner, Coordinates destinazione){
        this.owner = owner;
        this.destinazione = destinazione;
    }

    public boolean addUser(User user){
        if(guest.contains(user)) {
            return false;
        }else{
            guest.add(user);
            return true;
        }
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return this.ID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Coordinates getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(Coordinates destinazione) {
        this.destinazione = destinazione;
    }

    public List<User> getGuest() {
        return guest;
    }

    public void setGuest(List<User> guest) {
        this.guest = guest;
    }
}
