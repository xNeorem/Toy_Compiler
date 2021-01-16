# Progetto compilatori completo - Fasano/Gullo

Data la natura del progetto, bisogna utilizzare i tool esterni JFlex e Cup per poter rispettivamente generare il lexer ed il parser. A tal proposito per rendere l'utilizzo di tali tool più confortevole ed agevolare gli utenti che collaborano al progetto abbiamo preferito utilizzare un package manager, in particolare Maven. Di fatto Maven ci permette di specificare le librerie da utilizzare ed in un IDE, nel momento dell'apertura del progetto, già si hanno tutte le librerie pronte per l'utilizzo ed in una versione sicuramente funzionante.

Abbiamo configurato il comando compile di Maven facendo in modo che, nel momento in cui venga eseguito, selezioni tutti i file con estensione flex per generare il lexer mentre per il parser viene selezionato solamente il file toy.cup. Abbiamo deciso anche di configurare il clear per eliminare tutti i file generati in precedenza.

Ci teniamo a precisare che JFlex fornisce direttamente il pacchetto utilizzabile da Maven mentre per Cup ci siamo dovuti affidare ad una implementazione di terze parti.

La struttura del progetto risultante da queste nostre scelte è la seguente (viene riportata la struttura solo della porzione interessata dal progetto):

- src
    - main
        - java -> it.esercitazione4 (Contiene tutti i file Java del progetto)
        - resources (Contiene tutti i programmi toy)
- srcjflexcup
    - toy.cup
    - toy.flex

## Analisi lessicale
Per la generazione del lexer è stato utilizzato un tool di creazione automatica di lexer, ovvero JFlex.

Data una specifica ber definita JFlex è in grado di generare un lexer in codice Java.

Il file `toy.flex` contiene la specifica fornita ad JFlex per la creazione del nostro lexer che è in grado di riconsocere tutte i token definiti nella specifica del linguaggio.

Sono stati creati dei stati per la gestione semplificata delle stringhe e dei commenti.

Infine è stato inserito un metodo che fornisce in caso di errore un messaggio più significativo per il programmatore, fornendo anche gli indici (riga, colonna) del errore lessicale.

## Analisi sintattica
Per la generazione del parser c'è stata la neccesità di 
## Analisi semantica
## Generazione codice Clang
## Compilazione ed esecuzione
Abbiamo avuto la necessità di comportarci in modo diverso a causa delle due principali famiglie di sistemi operativi: Linux e Windows.
### Linux
Per rendere più agevole l'esecuzione è stato inserito all'interno del file `Tester.java` il seguente blocco codice:
```java
if (!System.getProperty("os.name").toLowerCase().contains("win"))
            Runtime.getRuntime().exec("./Toy2C.sh " + filePath);
```
Quindi nel caso in cui ci si trovi in un sistema Linux si esegue all'interno della shell lo script `Toy2C.sh` inviando come argomento il percorso del file C da compilare.

Lo script `Toy2C.sh` non si limita semplicemente alla compilazione del file C ma si occupa anche di formattarlo in modo da renderlo leggibile (viene utilizzato il tool `clang-format`), compilarlo ed eseguirlo all'interno del terminale esterno (in modo da poterci interagire e visualizzare i risultati).
### Windows
Nel caso in cui ci si trovi in un sistema Windows si esegue all'interno della shell lo script `Toy2C.bat` inviando come argomento il percorso del file C da compilare.

Anche lo script `Toy2C.bat` offre le stesse funzionalità di `Toy2C.sh`, l'unica differenza tra i due script é che lo scirpt per windows deve essere eseguito manualmente da un terminare aperto dall'utente.