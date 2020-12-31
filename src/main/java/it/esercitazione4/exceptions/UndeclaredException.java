package it.esercitazione4.exceptions;

public class UndeclaredException extends Exception {

    public UndeclaredException(){
        super("La variabile non è stata dichiarata");
    }

    public UndeclaredException(String name){
        super("La dichiarazione di "+name +" non è presente");
    }
}
