package fr.univrouen.cv24.exceptions;

public class CVAlreadyInDatabaseException extends Exception {
    public CVAlreadyInDatabaseException(String msg) {
        super(msg);
    }
}
