package it.esercitazione4.exceptions;

public class AlreadyDeclaredException extends Exception {

    public AlreadyDeclaredException(){
        super("La variabile è già stata dichiarata in questo scope");
    }

    public AlreadyDeclaredException(String name){
        super(name + " è già stato dichiarato nello scope");
    }
}
