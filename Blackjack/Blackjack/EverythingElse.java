import java.util.*;
import java.util.Scanner;
import java.awt.*; 
import javax.swing.*;
public class EverythingElse {
    static ArrayList<Integer> player = new ArrayList<Integer>();
    static ArrayList<Integer> computer = new ArrayList<Integer>();
    static ArrayList<Integer> pAce = new ArrayList<Integer>();
    static ArrayList<Integer> cAce = new ArrayList<Integer>();
    static int bet;
    static int money = 500;
    static boolean startOver = false;
    static boolean hasBet = false;
    static Scanner scan = new Scanner(System.in);
    static String dealtCards = "";
    static int pTotal = 0; //value of players cards
    static int cTotal = 0; //value of computers cards
    static boolean aceOver = false;
    static boolean blackjack = false;

    public static void startOver(){
        for(int i = 0; i < player.size(); i++){
            player.remove(i);
        }
        for(int i = 0; i < computer.size(); i++){
            computer.remove(i);
        }
        for(int i = 0; i < pAce.size(); i++){
            pAce.remove(i);
        }
        for(int i = 0; i < cAce.size(); i++){
            cAce.remove(i);
        }
        aceOver = false;
        cTotal = 0;
        pTotal = 0;
        hasBet = false;
        dealtCards = "";
        startOver = true;
        blackjack = false;
    }

    public static boolean isStartOver(){
        return startOver;
    }
    //Gets the bet

    public static int getMoney(){
        return money;
    }


    //Deals cards to both player and computer
    public static void deal(){
        startOver = false;
        player.add((int) (Math.random() * 13) + 1);
        player.add((int) (Math.random() * 13) + 1);
        computer.add((int) (Math.random() * 13) + 1);
        computer.add((int) (Math.random() * 13) + 1);
        if(player.get(0) == 1){
            dealtCards += "Ace, ";
            pTotal += 11;
            pAce.add(11);
        } else if(player.get(0) == 11){
            dealtCards += "Jack, ";
            pTotal += 10;
        } else if(player.get(0) == 12){
            dealtCards += "Queen, ";
            pTotal += 10;
        } else if(player.get(0) == 13){
            dealtCards += "King, ";
            pTotal += 10;
        } else {
            dealtCards += player.get(0) + ", ";
            pTotal += player.get(0);
        }
        if(player.get(1) == 1){
            dealtCards += "Ace";
            pTotal += 11;
            pAce.add(11);

        } else if(player.get(1) == 11){
            dealtCards += "Jack";
            pTotal += 10;
        } else if(player.get(1) == 12){
            dealtCards += "Queen";
            pTotal += 10;
        } else if(player.get(1) == 13){
            dealtCards += "King";
            pTotal += 10;
        } else {
            dealtCards += player.get(1);
            pTotal += player.get(1);
        }

        //dealer
        if(computer.get(0) == 1){
            cTotal += 11;
            pAce.add(11);
        } else if(computer.get(0) == 11){
            cTotal += 10;
        } else if(computer.get(0) == 12){
            cTotal += 10;
        } else if(computer.get(0) == 13){
            cTotal += 10;
        } else {
            cTotal += computer.get(0);
        }
        if(computer.get(1) == 1){
            cTotal += 11;
            cAce.add(11);

        } else if(computer.get(1) == 11){
            cTotal += 10;
        } else if(computer.get(1) == 12){
            cTotal += 10;
        } else if(computer.get(1) == 13){
            cTotal += 10;
        } else {
            cTotal += computer.get(1);
        }
        pAceCheck(pTotal);
        cAceCheck(cTotal);
        if(pTotal == 21){
            blackjack = true;
        }
        System.out.println();
        System.out.println();
        System.out.println("Your cards are: " + dealtCards + " (" + pTotal + ")");

    }
    //draws a card for the player
    public static void hit(){
        player.add((int) (Math.random() * 13) + 1);
        if(player.get(player.size() - 1) == 1){
            dealtCards += ", Ace";
            pTotal += 11; 
            pAce.add(11);
        } else if(player.get(player.size() - 1) == 11){
            dealtCards += ", Jack";
            pTotal += 10;
        } else if(player.get(player.size() - 1) == 12){
            dealtCards += ", Queen";
            pTotal += 10;
        } else if(player.get(player.size() - 1) == 13){
            dealtCards += ", King";
            pTotal += 10;
        } else {
            dealtCards += ", " + player.get(player.size() - 1);
            pTotal += player.get(player.size() - 1);
        }
        pAceCheck(pTotal);
        System.out.println("Your cards are: " + dealtCards + " (" + pTotal + ")");
    }
    //computer hit
    public static void cHit(){
        computer.add((int) (Math.random() * 13) + 1);
        if(computer.get(computer.size() - 1) == 1){
            cTotal += 11; 
            cAce.add(11);
        } else if(computer.get(computer.size() - 1) == 11){
            cTotal += 10;
        } else if(computer.get(computer.size() - 1) == 12){
            cTotal += 10;
        } else if(computer.get(computer.size() - 1) == 13){
            cTotal += 10;
        } else {
            cTotal += player.get(player.size() - 1);
        }
        cAceCheck(cTotal);
    }

    //player is done
    public static void stand(){
        while(cTotal < 17){
            cHit();
        }
        System.out.println("Total: " + pTotal);
        if((pTotal > cTotal || cTotal > 21) && pTotal <= 21){
            System.out.println("You win! Dealers Cards: " + cTotal);
        }
        if(cTotal > pTotal && cTotal <= 21){
            System.out.println("You lose! Dealers Cards: " + cTotal);
        }
        startOver();
    }

    //checks if ace brings it over 21 for player
    public static void pAceCheck(int total){
        if(total > 21){
            for(int i = 0; i < pAce.size(); i++){
                if(pAce.get(i) == 11){
                    pTotal -= 10;
                    pAce.set(i, 1);
                }
            }
        }
    }
    //checks if ace brings it over 21 for computer
    public static void cAceCheck(int total){
        for(int i = 0; i < cAce.size(); i++){
            if(cAce.get(i) == 11){
                cTotal -= 10;
                cAce.set(i, 1);
            }
        }
    }

    public static boolean pBust(){
        if(pTotal > 21){
            System.out.println("BUST! (" + pTotal + ")");
            return true;
        }
        return false;
    }

    public static boolean blackJack(){
        if(blackjack)
            return true;
        return false;
    }
}

