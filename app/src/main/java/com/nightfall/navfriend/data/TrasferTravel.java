package com.nightfall.navfriend.data;

/**
 * Created by Dev on 28/04/2015.
 */
public class TrasferTravel {

    private User user;
    private Coordinates coordinates;
    private String descrizione;

    public TrasferTravel(User user, Coordinates coordinates, String descrizione) {
        this.user = user;
        this.coordinates = coordinates;
        this.descrizione = descrizione;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coordinates getcoordinates() {
        return coordinates;
    }

    public void setcoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
