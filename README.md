# Progetto compilatori linguaggio Toy completo
_Il progetto è stato eseguito dagli studenti Fasano Salvatore e Gullo Gerardo nell'ambito del corso di Compilatori, Università degli Studi di Salerno, AA 202/2021._

## Maven
Data la natura del progetto, bisogna utilizzare i tool esterni JFlex e JavaCup per poter rispettivamente generare il lexer ed il parser. A tal proposito per rendere l'utilizzo di tali tool più confortevole ed agevolare gli utenti che collaborano al progetto abbiamo preferito utilizzare un package manager, in particolare Maven. Di fatto Maven ci permette di specificare le librerie da utilizzare ed in un IDE, nel momento dell'apertura del progetto, già si hanno tutte le librerie pronte per l'utilizzo ed in una versione sicuramente funzionante.

Abbiamo configurato il comando compile di Maven facendo in modo che, nel momento in cui venga eseguito, selezioni tutti i file con estensione flex per generare il lexer mentre per il parser viene selezionato solamente il file toy.cup. Abbiamo deciso anche di configurare il clear per eliminare tutti i file generati in precedenza.

Ci teniamo a precisare che JFlex fornisce direttamente il pacchetto utilizzabile da Maven mentre per JavaCup ci siamo dovuti affidare ad una implementazione di terze parti.

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

Data una specifica ben definita JFlex è in grado di generare un lexer in codice Java.

Il file `toy.flex` contiene la specifica fornita a JFlex per la creazione automatica di un lexer che sia in grado di riconoscere tutti i token definiti nella specifica del linguaggio.

Il file è organizzato come segue:
- Codice utente
    - Viene importato `java_cup.runtime.Symbol`
- Opzioni e dichiarazioni
    - Istanziazione di uno StringBuffer che verrà utilizzato per memorizzare le `STRING_CONST`
    - Dichiarazione di una funzione error che verrà utilizzata in presenza di situazioni di errore per generare una eccezione con un messaggio di errore inerente alla problematica presentatasi, inoltre grazie all'uso di `%line` e `%column` inseriremo anche informazioni relative alla riga ed alla colonna in cui l'errore si è verificato
    - Dichiarazione dei due stati lessicali `STRING` e `COMMENT` per poter riconoscere stringhe e commenti
- Regole lessicali
    - Il riconoscimento inzia dallo stato lessicale di default `YYINITIAL`, non ci sono particolarità nel riconoscimento di token semplici
    - Nel caso in cui vengano ritrovate delle virgolette allora si entra nello stato lessicale `STRING` in modo da riconoscere l'intera stringa (compresi caratteri speciali) utilizzando lo StringBuffer dichiarato in precedenza, se si incontra la fine del file mentre si sta procesdsando la stringa allora si esegue la funzione error per lanciare una eccezione
    - Nel caso in cui venga riconosciuto `/*` allora si va nello stato lessicale `COMMENT` semplicemente per evitare di mantenere informazioni relative ad un commento, come nel caso precedente se il file termina mentre si sta processando un commento allora viene utilizzata la funzione errore per lanciare una eccezione
 
## Analisi sintattica
Per la fase di analisi sintattica c'è stata, prima di tutto, la neccesità di definire come strutturare l'albero sintattico. Inizialmente abbiamo pensato di procedere con utilizzando una libreria di terze parti che si occupasse dell'albero e di tutte le informazioni necessarie, andando a differenziare i nodi dell'albero semplicemente per nome. Questa strategia alla lunga non si è rivelata opportuna, per cui abbiamo poi deciso di utilizzare un approccio object-oriented: abbiamo definito una classe `Node` che viene poi estesa da tutti i possibili nodi che si possono trovare all'interno del nostro albero sintatico; ovviamente la strttura di ogni nodo è stata definita in base alla grammatica.

All'interno del file `toy.cup` abbiamo quindi definito tutti i possibili terminali e non terminali con i relativi tipi ed abbiamo avuto la necessità di definire le diverse preceper rendere la grammatica non ambigua.

Gran parte del lavoro è stato effettuato per la produzione `Expr`, è infatti questa produzione che, se non correttamente gestita, rende la grammatica ambigua a causa delle divere operazioni che si possono fare con due `Expr`, abbiamo quindi dato le precedenze nel seguente ordine:
1. Operatore unario `UMINUS`
2. Operazioni moltiplicazione `TIMES` e divisione `DIV`
3. Operazioni di addizione `PLUS` e sottrazione `MINUS`
4. Negazione `NOT`
5. Operatori relazionali `GT, GE, NE, LT, LE, EQ`
6. Operatori `AND, OR`

Come sopra riportato, definire le precedenze ci ha permesso di risolvere una gran parte dei problemi, tuttavia abbiamo dovuto apportare piccole modifiche alla grammatica per risolvere dei conflitti che si verificano. 

Innanzitutto abbiamo aggiunto un nuovo terminale `RETURN`, poi abbiamo modificato le produzioni `Proc, WhileStat` in modo che utilizzino il nuovo terminale `RETURN` ed abbiamo eliminato la produzione `Stat -> /* empty */`.

A questo punto JavaCup riesce ad utilizzare il file `toy.cup` per generare automaticamente un parser `LALR(1)`.

## Analisi semantica
In questa fase sono state implementate tutte le regole d'inferenza e il `Type System` del linguaggio Toy.

E' stata effettuata una verifica per ogni tipo di nodo, evidenziando al programmatore attraverso delle opportune `Exception` nel caso di un errore semantico.

Di seguito é riportata la tabella che evidenzia i vari tipi cui possono essere impiegati gli operatori.

parlare dello stack.

OP | ARG1 | ARG2 | RETURN 
--- | --- | --- |--- 
`ADD` | `INT` | `INT` | `INT` |
`DIFF` | `INT` | `INT` | `INT` |
`MUL` | `INT` | `INT` | `INT` |
`DIV` | `INT` | `INT` | `INT` |
`ADD` | `INT` | `FLOAT` | `FLOAT` |
`DIFF` | `INT` | `FLOAT` | `FLOAT` |
`MUL` | `INT` | `FLOAT` | `FLOAT` |
`DIV` | `INT` | `FLOAT` | `FLOAT` |
`ADD` | `FLOAT` | `FLOAT` | `FLOAT` |
`DIFF` | `FLOAT` | `FLOAT` | `FLOAT` |
`MUL` | `FLOAT` | `FLOAT` | `FLOAT` |
`DIV` | `FLOAT` | `FLOAT` | `FLOAT` |
`AND` | `BOOL` | `BOOL` | `BOOL` |
`OR` | `BOOL` | `BOOL` | `BOOL` |
`LT` | `BOOL` | `BOOL` | `BOOL` |
`LE` | `BOOL` | `BOOL` | `BOOL` |
`NE` | `BOOL` | `BOOL` | `BOOL` |
`EQ` | `BOOL` | `BOOL` | `BOOL` |
`GT` | `BOOL` | `BOOL` | `BOOL` |
`GE` | `BOOL` | `BOOL` | `BOOL` |
`LT` | `INTEGER` | `INTEGER` | `BOOL` |
`LE` | `INTEGER` | `INTEGER` | `BOOL` |
`NE` | `INTEGER` | `INTEGER` | `BOOL` |
`EQ` | `INTEGER` | `INTEGER` | `BOOL` |
`GT` | `INTEGER` | `INTEGER` | `BOOL` |
`GE` | `INTEGER` | `INTEGER` | `BOOL` |
`LT` | `INTEGER` | `FLOAT` | `BOOL` |
`LE` | `INTEGER` | `FLOAT` | `BOOL` |
`NE` | `INTEGER` | `FLOAT` | `BOOL` |
`EQ` | `INTEGER` | `FLOAT` | `BOOL` |
`GT` | `INTEGER` | `FLOAT` | `BOOL` |
`GE` | `INTEGER` | `FLOAT` | `BOOL` |
`LT` | `STRING` | `STRING` | `BOOL` |
`LE` | `STRING` | `STRING` | `BOOL` |
`NE` | `STRING` | `STRING` | `BOOL` |
`EQ` | `STRING` | `STRING` | `BOOL` |
`GT` | `STRING` | `STRING` | `BOOL` |
`GE` | `STRING` | `STRING` | `BOOL` |


## Generazione codice Clang

Una scelta implementativa signficativa è stata per la gestione dei multipli valori di ritorno di una funzione.

Dato che in C non è possibile effettuare questo tipo di operazione, quando viene verificato che una funzione ritorna più di un valore, in C viene generata una funzione che ritorna un array di puntatori a void.

Ad esempio dal seguente codice toy:

    proc test(bool y; string nome)int,int,int:
        int a:=1,b:=2,c:=3;
            if y then write(nome);
            fi;
        -> a, b, c
    corp;
Verrà generato il seguente codice C:

```C
void ** test(bool y, char * nome) {
  int a = 1, b = 2, c = 3;
  void ** t_0 = (void ** ) malloc(sizeof(void * ) * 3);
  if (t_0 == NULL) {
    printf("Memory not allocated for return values.");
    exit(0);
  }
  if (y) {
    printf("%s", nome);
  }
  t_0[0] = &a;
  t_0[1] = &b;
  t_0[2] = &c;
  return t_0;
}
```

I puntatori a `void` possono puntate qualsisi tipo di dato, questa loro proprietà ci permette attraverso dei opportuni cast di gestire i vari tipi dei valori di ritorno.

Per non avere nessun tipo di overhead nel codice tradotto questa struttura da noi creata viene **deallocata**.

Abbiamo avuto la necessità di mappare gli operatori di Toy con quelli di C in modo da facilitarci nella traduzione.

La tabella sottostante mostra la corrispondeza degli operatori tra i due linguaggi.

OP | STRING
--- | --- 
`ADD` | `+`
`DIFF` | `-`
`MUL` | `*`
`DIV` | `/`
`AND` | `&&`
`OR` | &#124;&#124;
`LT` | `<`
`LE` | `<=`
`NE` | `!=`
`EQ` | `==`
`GT` | `>` 
`GE` | `>=`

La stessa tecnica è stata impiegata anche per facilitarci nella traduzione nel comando `write` in una `printf` di C.
Essendo che la funzione `printf` utilizza dei caratteri speciali per la stampa. 
Abbiamo mappato per ogni tipo di operatore il suo carattere speciale. 

IOCONST | STRING
--- | --- 
`STRING` | `%d`
`FLOAT` | `%f`
`STRING` | `%s`
`BOOL` | `%d`

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

Anche lo script `Toy2C.bat` offre le stesse funzionalità di `Toy2C.sh`, l'unica differenza tra i due script é che in un sistema Windows bisogna eseguire manualmente lo script all'interno di un terminale aperto dall'utente.
## Generazione codice Javascript
Attraverso l'uso dello stumento EMScripten è stato possibile utilizzare il codice C generato da un programma Toy per ottenerne una versione con linguaggio di programmazione JavaScript. 
La geenraizone può essere eseguita in modo che il programma JavaScript venga eseguito sfruttando la tecnologia NodeJS o all'interno di una pagina HTML con template di default.
Il primo comando che presentiamo ci permetterà di generare solamente il file.js a partire dal file.c, tale file potrà essere eseguito mediante NodeJS.
```shell script
emcc pathFile.c
node pathFile.js
```
Il prossimo comando invece permette di generare una serie di files per eseguire il programma in una pagina HTML, bisogna precisare che non è garantito il corretto funzionamento se la pagina HTML viene aperta direttamente, è invece consigliato di aprirla all'interno di un web server.
```shell script
emcc pathFile.c --emrun -o pathFile.html
# il comando emrun permette anche una serie di opzioni che consentono di selezionare un preciso browser, di determinate la durata della vita del webserver ed altre possibilità
emrun pathFile.html
```