
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BingoTeste {
    static Scanner scanner = new Scanner(System.in);
    static int[][] cards;
    static String[] players;

    public static void main(String[] args) {


        //cabeçalho
        System.out.println("__________________________________________________________________");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println(" ");
        System.out.println("              Bem-vindo ao ADA BINGO!");
        System.out.println(" ");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println("__________________________________________________________________");
        System.out.println(" ");
        System.out.println("           Opções de comando ");
        System.out.println("__________________________________________________________________");

        System.out.println("1. Iniciar jogo");
        System.out.println("2. Sair");

        int option = scanner.nextInt();
        if (option == 1) {
             players = addPlayers(); // aqui quando inserir os jogadores ele ja vai definir o numero de jogadores, nao preciso perguntar
            cards = menuCards(players);
            System.out.println("Lista de jogadores:"); //printa os nomes na tela
            for (int i = 0; i < players.length; i++) {
                System.out.printf("%2d - %15s -  ", i + 1, players[i]);
                System.out.println(Arrays.toString(cards[i]));
            }
            menuSorteio();

        } else {
            System.out.println("Chega por hoje??!");
        }
    }
    private static void menuSorteio() {
        System.out.println("Como voce gostaria de sortear? ");
        System.out.println("A- para sorteio automático");
        System.out.println("M- para sorteio manual");

        String chooseOption = scanner.next();
        if (chooseOption.equals("M")) {
            sorteioManual();
        } else {
            sorteioAuto();
        }
    }
    private static int[][] menuCards(String[] players) {

        System.out.println("Como voce gostaria de jogar? ");
        System.out.println("A- para escolha  automática dos numeros");
        System.out.println("M- para escolha  manual dos numeros");

        String chooseOption = scanner.next();
        if (chooseOption.equals("M")) {
            cards = addCards(); //seja qual for a escolha armazena em cards
        } else {
            cards = autoCards(players);
        }
        return cards;
    }
    //inserir nomes jogadores(players)
    public static String[] addPlayers() {

        System.out.println("Escreva os nomes dos jogadores separados por hífen:");
        String playersInput = scanner.next(); // recebe o txt
        String[] playersNames = playersInput.split("-"); //divide os jogadores em partes

        return playersNames; //devolve os nomes pro main
    }
    //gerar cartelas manual (addCards)
    private static int[][] addCards() {

        System.out.println("Escreva os  cinco números de cada cartela que voce acredita que lhe farão vencedor, separe-os por vírgulas:");
        //Scanner scanner = new Scanner(System.in);
        String cardsInput = scanner.next();
        String[] choosenCards = cardsInput.split("-"); //divide as cartelas em partes
        String[] rightCards;
        int[][] drawnNumbers = new int[choosenCards.length][5];// array bid
        for (int i = 0; i < choosenCards.length; i++) {
            rightCards = choosenCards[i].split(","); // arranca as virgulas
            for (int j = 0; j < 5; j++) { // sao 5 numeros
                int number = Integer.parseInt(rightCards[j]); //transforma string em int
                drawnNumbers[i][j] = number;
            }
        }
        return drawnNumbers;
    }
    //gerar cartelas automatico (autoCards)
    private static int[][] autoCards(String[] players) {
        Random rand = new Random(); //metodo
        int upperbound = 60;
        // gerando inteiros aleatórios de 1 a 60
        int[][] drawnNumbers = new int[players.length][5];// array bid
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < 5; j++) { // sao 5 numeros
                int number = rand.nextInt(upperbound) + 1; //random
                for (int k = 0; k < 5; k++) {
                    if (drawnNumbers[i][k] == number) {
                        number = rand.nextInt(upperbound) + 1; //random
                        k = -1;
                    }
                }
                drawnNumbers[i][j] = number;
            }
        }
        return drawnNumbers;
    }
        //Sortear os numeros  do Bingo manual
    private static void sorteioManual() {
        int[] sorteadosRodada = new int[5];
        int[] todosSorteados = new int[60];
        boolean continuar = true;
        int vencedor = -1;
        while (continuar) {
            System.out.println("Digite os 5 números do sorteio separados por vírgulas sem espaços. Exemplo:1,2,3,4,5");
            String usuarioSorteio = scanner.next();

            //Atenção

            String[] numerosSorteio = usuarioSorteio.split(","); //divide as cartelas em partes

            for (int i = 0; i < 5; i++) {
                int number = Integer.parseInt(numerosSorteio[i]); //transforma string em int
                sorteadosRodada[i] = number;
                for (int j = 0; j < 60; j++) {
                    if (todosSorteados[j] == 0) {
                        todosSorteados[j] = number;
                        break;
                    }
                }
            }
            System.out.println("Sorteados da rodada" + Arrays.toString(sorteadosRodada));
            System.out.println("Todos os numeros sorteados" + Arrays.toString(todosSorteados));

//            System.out.println(" Caso voce queira  continuar o sorteio digite : C ");
//            System.out.println(" Caso voce queira parar digite : X  ");
//            String chooseOption = scanner.next();
//            if (chooseOption.equals("X")) {
//                continuar = false;
//            }
            int [] pontuacao= conferencia(cards, todosSorteados);//int pontuaçao
            vencedor =  eBingo(pontuacao);
            if (vencedor >-1){
                continuar =false;
            }else{
                System.out.println(" Caso voce queira  continuar o sorteio digite : C ");
                System.out.println(" Caso voce queira parar digite : X  ");
                String chooseOption = scanner.next();
                if (chooseOption.equals("X")) {
                    continuar = false;
                }
            }
        }
        System.out.printf("%2d - %15s - BINGO!!  ", vencedor + 1, players[vencedor]);
        System.out.println(Arrays.toString(cards[vencedor]));
        System.out.println(" Jogo Encerrado");
    }
    private static void sorteioAuto() {
        Random rand = new Random(); //metodo
        int upperbound = 60;
        int[] sorteadosRodada = new int[5];
        int[] todosSorteados = new int[60];
        boolean continuar = true;
        int vencedor = -1;
        while (continuar) {
            for (int i = 0; i < 5; i++) {
                int number = rand.nextInt(upperbound) + 1; //random
                for (int j = 0; j < 60; j++) {
                    if (todosSorteados[j] == number) {
                        number = rand.nextInt(upperbound) + 1; //random
                        j = -1;
                    }
                }
                sorteadosRodada[i] = number;
                for (int j = 0; j < 60; j++) {
                    if (todosSorteados[j] == 0) {
                        todosSorteados[j] = number;
                        break;
                    }
                }
            }
            System.out.println("sorteados da rodada" + Arrays.toString(sorteadosRodada));
            System.out.println(" todos os numeros sorteados" + Arrays.toString(todosSorteados));

            int [] pontuacao= conferencia(cards, todosSorteados);//int pontuaçao
            vencedor =  eBingo(pontuacao);
            if (vencedor >-1){
                continuar =false;
            }else{
                System.out.println(" Caso voce queira  continuar o sorteio digite : C ");
                System.out.println(" Caso voce queira parar digite : X  ");
                String chooseOption = scanner.next();
                if (chooseOption.equals("X")) {
                    continuar = false;
                }
            }
        }
        System.out.printf("%2d - %15s - BINGO!!  ", vencedor + 1, players[vencedor]);
        System.out.println(Arrays.toString(cards[vencedor]));

        System.out.println(" Jogo Encerrado");
    }
    private static int [] conferencia(int[][] cards, int[] todosSorteados) {
        int[][] guardaConferencia = new int[cards.length][5];
        int []pontuacao = new int [cards.length];
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < todosSorteados.length; k++) {
                    if(cards[i][j] == todosSorteados [k]){
                     guardaConferencia  [i][j]= 1 ;
                    }
                }
            }
        }
        for (int i = 0; i < cards.length; i++) {
            int soma=0 ;
            for (int j = 0; j < 5; j++) {
                soma += guardaConferencia[i] [j];
            }
            pontuacao [i] = soma;
        }
        //System.out.println(cards.length);
        System.out.println(Arrays.deepToString(cards));
        System.out.println(Arrays.deepToString(guardaConferencia));
        System.out.println(Arrays.toString(todosSorteados));
        return pontuacao;
    }
    private static int eBingo(int[] pontuacao){
        for (int i = 0; i < pontuacao.length; i++) {
            if( pontuacao [i] == 5 ){
                return i;
            }
        }
        return -1;
    }
}








