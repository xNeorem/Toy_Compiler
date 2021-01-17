# Progetto compilatori linguaggio Toy completo
_Il progetto è stato eseguito dagli studenti Fasano Salvatore e Gullo Gerardo nell'ambito del corso di Compilatori, Università degli Studi di Salerno, AA 2020/2021._

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
In questa fase è stato creato un `SemanticVisitor.class` in grado di effettuare il Type Check, ovvero assegnare un tipo a tutti i simboli del linguaggio, in modo da poter poi effettuare eventuali controlli di tipo all'interno delle espressioni.

Sono state inoltre implementate tutte le regole di inferenza richieste all'interno della specifica del linguaggio.

Eventuali errori di tipo semantico vengono evidenziati al programmatore mediante l'utilizzo di opportune Exception che sono state create:
- `UndeclaredException`: lanciata quando si incontra l'utilizzo di una variabile o di una funzione che non è stata dichiarata
- `AlreadyDeclaredException`: lanciata quando un simbolo è già stato definito all'interno dello scope e si prova a riutilizzarlo in una nuova definizione
- `TypeMismatchException`: lanciata ogni qual volta si riscontrino problemi di tipo all'interno delle operazioni
- `ReturnParamsException`: lanciata quando i valori di ritorno di una funzione non rispettano la firma della funzione stessa
- `CallProcException`: lanciata quando una funzione non viene invocata correttamente

È stata creata una classe statica per effettuare una corretta gestione dello scoping, tale classe è stata chiamta `TableStack.java` ed è stata costruita in modo da semplificare al massimo il lavoro all'interno del SemanticVisitor:
- Contiene una Stack di SymbolTable
- Espone la funzione `EntrySymbolTable lookup(String symbol)` che ricerca un determinato simbolo all'interno dello scope corrente ed in quello dei genitori
- Espone la funzione `SymbolTable getHead()` che restituisce la SymbolTable in testa allo Stack
- Ulteriori funzionalità per la normale gestione di una Stack, come: add, pop e size

Presentiamo la struttura di una entry della nostra SymbolTable:

ID | SYMBOL | TYPE | TYPEIN | TYPEOUT | KIND
--- | --- | --- |--- | --- |---
`String` | `String` | `String` |`ArrayList<String>` | `ArrayList<String>` | `var`&#124;&#124;`proc`

In base al costruttore utilizzato, la tabella riconosce automaticamente il tipo di costrutto (variabile o procedura).

Per le procedure viene conservato l'id, il simbolo, la lista dei tipi dei parametri in input ed la lista dei tipi dei valori di ritorno.

Nel caso delle variabili viene conservato l'id, il simbolo e il tipo a lei associata.

Riportiamo di seguito il Type System, ovvero la tabella che specifica come assegnare un tipo ad una espressione data una operazione ed i tipi degli argomenti.

Dal punto di vista pratico questa tabella è stata implementata come una `HashTable<KeyOpTypes, String> opTypes2` dove l'oggetto KeyOp non è altro che una tripla costituita dall'operazione, tipo del primo argomento e tipo del secondo argomento; ciò significa che semplicemente utilizzando il metodo `get(KeyOpTypes tripla)` fornito di default per il tipo HashMap otteniamo il tipo della espressione ricercata.

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

Una scelta che abbiamo deciso di portare avanti in questa fase è stata quella di effettuare tutti i controlli necessari per supportare la funziona di unpacking di Toy:
```
mul, add, diff := mulAddDiff()
```
ed inoltre permettere che non tutti i valori di ritorno della funzione vengano effettivamente assegnati ad una variabile, in tal modo se si è interessati solo ai primi due valori basterà utilizzare solo due variabili.
## Generazione codice Clang
Nella conversione del codice Toy in codice C abbiamo effettuato varie scelte progettuali in risposta a problemi specifici:
1. come viene effettuata la conversione da main di Toy a main di C
2. rappresentazione dei tipi booleani
3. rappresentazione delle stringhe
4. conversione delle funzioni che hanno valori di ritorno multipli

Per evitare qualsiasi sorta di problema abbiamo deciso di creare, all'interno del codice C, una funzione main che rispetti quelle che sono le specifiche del C e che invochi la funzione main del programma scritto in linguaggio Toy, riportiamo un esempio pratico.
```
proc main()void:
    write("Test");
    ->
corp;
```
```C
void main_func() {
    printf("Test");
}

int main(int argc, char *argv[]){
    main_func();
    return 0;
}
```
In questo modo non c'è bisogno di preoccuparsi di quale sia il tipo di ritorno della funzione main specificata in Toy.

Una ulteriore possibilità implementata è la capacità di inserire parametri alla funzione main di Toy e tradurre tutto ciò facendo in modo che tali variabili siano attesi in C dalla riga di comando, ovvero presi dall'array argv (con tutte le conversioni del caso).

I booleani invece sono stati gestiti semplicemente inserendo all'interno del codice C la libreria `stdbool.h`.

In modo simile è stata effettuata la gestione delle stringhe, come sappiamo C non supporta un tipo stringa ma implementa questo concetto con un array di char, per cui le stringhe sono state dichiarate come `char*` e le operazioni tra stringhe sono state garantite tramite l'utilizzo della libreria `string.h`.

La scelta che ha maggiormente impattato sulla fase di conversione del codice è stata quella di gestire tutte le funzioni con valori di ritorno multipli con dei puntatori.

Dato che in C non è possibile che una funzione abbia valori di ritorno multipli, quando ci si trova in una situazione del genere, in C viene generata una funzione che ritorna un array di puntatori a void.

Ad esempio dal seguente codice Toy:
```
proc test(bool y; string nome)int,int,int:
    int a:=1,b:=2,c:=3;
        if y then write(nome);
        fi;
    -> a, b, c
corp;
```
Verrà generato il seguente codice C:

```C
void **test(bool y, char *nome) {
  int a = 1, b = 2, c = 3;
  void **t_0 = (void **) malloc(sizeof(void*) * 3);
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

I puntatori a void possono puntare qualsiasi tipo di dato, questa loro proprietà ci permette attraverso degli opportuni cast di gestire i vari tipi dei valori di ritorno.

Per rendere questa nostra scelta funzionale anche dal punto di vista della gestione della memoria, ci siamo assicurati dove necessario, di andare a deallocare correttamente la struttura dati.

Abbiamo permesso la prossibilità, inserendo una chiamata a funzione in una write, anche di stampare tutti i valori che la funzione ritorna.

Tra l'altro dato che Toy permette una sorta di meccanismo di unpacking del tipo:
```
mul, add, diff := mulAddDiff()
```
abbiamo supportato questo comportamento mediante la creazione di variabili temporanee.

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
Abbiamo mappato per ogni tipo di variabile il carattere speciale per rappresentarla all'interno di una stringa. 

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