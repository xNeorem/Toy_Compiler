package it.esercitazione4.exceptions;

public class TypeMismatchException extends Exception {

    public TypeMismatchException(){
        super("Non è possibile effettuare tale operazione per problemi di tipo");
    }

    /*public TypeMismatchException(String name){
        super("La dichiarazione di "+name +" non è presente");
    }*/
}
