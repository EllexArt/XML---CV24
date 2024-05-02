package fr.univrouen.cv24.exceptions;

public class CVNotFoundException extends Exception{

    private long id;

    public CVNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
