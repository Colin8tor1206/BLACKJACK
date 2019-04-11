import java.awt.GridLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*; 
import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.*;
import java.awt.*;   
import java.awt.event.*;  
import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.concurrent.*;
public class RealBlackjack{
    static ArrayList<Integer> player = new ArrayList<Integer>();
    static ArrayList<Integer> computer = new ArrayList<Integer>();
    static ArrayList<Integer> pAce = new ArrayList<Integer>();
    static ArrayList<Integer> cAce = new ArrayList<Integer>();
    static int bet;
    static int money = 500;
    static boolean startOver = false;
    static boolean hasBet = false;
    static String dealtCards = "";
    static int pTotal = 0; //value of players cards
    static int cTotal = 0; //value of computers cards
    static boolean aceOver = false;
    static boolean blackjack = false;
    static int betM;
    static Frame f = new Frame("User Interface");
    static Button hit = new Button("Hit");
    static Button stand = new Button("Stand"); 
    static Button betB = new Button("Bet"); 
    static JLabel status = new JLabel("Slide the slider to input your bet");
    static Button restart = new Button("Start Over");
    static JSlider slider = new JSlider(0, money);
    static JLabel moneys = new JLabel();
    static Hashtable position = new Hashtable();
    static int tickSpace = 50;
    static int record;
    static Scanner scan;
    static BufferedWriter bw = null;
    static ArrayList<String> images = new ArrayList<String>();
    static ArrayList<String> dealerImages = new ArrayList<String>();
    static JFrame frame = new JFrame("Hand");
    static JFrame dealer = new JFrame("Dealer");

    public static void main(String[] args) throws Exception{
        f.setLayout(null);  
        scan = new Scanner(new File("Record.txt"));
        record = scan.nextInt();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        dealer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dealer.setLayout(new FlowLayout());
        frame.setLocation(5, 25);
        dealer.setLocation(5, 330);
        f.setLocation(5, 595);
        cDeal();
        try {
            bw = new BufferedWriter(new FileWriter(new File("Record.txt")));
            bw.write(String.valueOf(record));
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        hit.setBounds(65,50,290,30); 
        stand.setBounds(355,50,290,30);
        betB.setBounds(210,190,290,30); 
        restart.setBounds(510, 250, 200, 30);
        status.setBounds(260, 160, 300, 30);
        while(money/tickSpace > 20){
            tickSpace+=25;
        }
        slider.setMajorTickSpacing(tickSpace);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setBounds(55,100,600,60);

        moneys.setBounds(20, 175, 250, 200); 
        moneys.setText("$" + String.valueOf(money) + "       Record: $" + record);
        for(int i = 0; i < (money/tickSpace) + 1; i++){
            int number = i * tickSpace;
            String sNumber = String.valueOf(number);
            position.put(number, new JLabel(sNumber));   
        }
        slider.setLabelTable(position);
        slider.setValue(money/2);
        betM = money/2;
        //waits until button "hit" is pressed
        hit.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    hit();
                }  
            });

        //waits until button "stand" is pressed
        stand.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e) {  
                    stand();
                    hit.setVisible(false);
                    stand.setVisible(false);
                    restart.setVisible(true);
                }

            });
            
            
        restart.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    startOver();
                    restart.setVisible(false);
                }  
            });

        //bet button

        betB.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                    money -= betM;
                    moneys.setText("$" + String.valueOf(money) + "       Record: $" + record);
                    betB.setVisible(false); 
                    status.setVisible(false); 
                    slider.setVisible(false); 
                    deal();
                    f.add(hit);
                    f.add(stand);
                    hit.setVisible(true);
                    stand.setVisible(true);
                    for(int i = 0; i < images.size(); i++){
                        frame.add(new JLabel(new ImageIcon(images.get(i))));
                        frame.pack();
                        frame.revalidate();
                        frame.repaint();
                    }
                    frame.setVisible(true);
                }  
            });  
        slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    status.setText("Value of the slider is: $" + ((JSlider)e.getSource()).getValue());
                    betM = ((JSlider)e.getSource()).getValue();
                }
            });

        f.add(slider);
        f.setSize(750,300);    
        f.add(betB);
        f.add(status);
        f.add(moneys);
        f.add(restart);
        restart.setVisible(false);
    
        f.setVisible(true);

    }

    public static void startOver(){
        for(int i = 0; i < player.size(); i++){
            player.remove(i);
            i--;
        }
        for(int i = 0; i < computer.size(); i++){
            computer.remove(i);
            i--;
        }
        for(int i = 0; i < pAce.size(); i++){
            pAce.remove(i);
            i--;
        }
        for(int i = 0; i < cAce.size(); i++){
            cAce.remove(i);
            i--;
        }
        moneys.setText("$" + String.valueOf(money) + "       Record: $" + record);
        position.clear();
        slider.setMaximum(money);
        while(money/tickSpace > 10){
            tickSpace+=25;
        }
        slider.setMajorTickSpacing(tickSpace);
        for(int i = 0; i < (money/tickSpace) + 1; i++){
            int number = i * tickSpace;
            String sNumber = String.valueOf(number);
            position.put(number, new JLabel(sNumber));   
        }
        for(int i = 0; i < images.size(); i++){
            images.remove(i);
            i--;
        }
        for(int i = 0; i < dealerImages.size(); i++){
            dealerImages.remove(i);
            i--;
        }

        if(money > record){
            record = money;
            try {
                bw = new BufferedWriter(new FileWriter(new File("Record.txt")));
                bw.write(String.valueOf(record));
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        moneys.setText("$" + String.valueOf(money) + "       Record: $" + record);
        slider.setLabelTable(position);
        slider.setValue(money/2);
        aceOver = false;
        cTotal = 0;
        pTotal = 0;
        hasBet = false;
        dealtCards = "";
        startOver = true;
        blackjack = false;

        betB.setVisible(true); 
        status.setVisible(true); 
        slider.setVisible(true);
        f.setVisible(true);
        hit.setVisible(false);
        stand.setVisible(false);
        slider.setValue(money/2);
        betM = money/2;

        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
        frame.pack();
        dealer.getContentPane().removeAll();
        dealer.revalidate();
        dealer.repaint();
        dealer.pack();
        cDeal();
    }

    public static void deal(){
        startOver = false;
        player.add((int) (Math.random() * 13) + 1);
        player.add((int) (Math.random() * 13) + 1);
        if(player.get(0) == 1){
            dealtCards += "Ace, ";
            pTotal += 11;
            pAce.add(11);
            images.add("AS.jpg");
        } else if(player.get(0) == 11){
            dealtCards += "Jack, ";
            pTotal += 10;
            images.add("JS.jpg");
        } else if(player.get(0) == 12){
            dealtCards += "Queen, ";
            pTotal += 10;
            images.add("QS.jpg");
        } else if(player.get(0) == 13){
            dealtCards += "King, ";
            pTotal += 10;
            images.add("KS.jpg");
        } else {
            dealtCards += player.get(0) + ", ";
            pTotal += player.get(0);
            images.add(player.get(0) + "S.jpg");
        }
        if(player.get(1) == 1){
            dealtCards += "Ace";
            pTotal += 11;
            pAce.add(11);
            images.add("AS.jpg");
        } else if(player.get(1) == 11){
            dealtCards += "Jack";
            pTotal += 10;
            images.add("JS.jpg");
        } else if(player.get(1) == 12){
            dealtCards += "Queen";
            pTotal += 10;
            images.add("QS.jpg");
        } else if(player.get(1) == 13){
            dealtCards += "King";
            pTotal += 10;
            images.add("KS.jpg");
        } else {
            dealtCards += player.get(1);
            pTotal += player.get(1);
            images.add(player.get(1) + "S.jpg");
        }
        pAceCheck(pTotal);
        if(pTotal == 21){
            blackjack = true;
        }
        System.out.println();
        System.out.println();
        //System.out.println("Your cards are: " + dealtCards + " (" + pTotal + ")");
        frame.setTitle("Hand: " + String.valueOf(pTotal));
    }

    public static void cDeal(){
        computer.add((int) (Math.random() * 13) + 1);
        computer.add((int) (Math.random() * 13) + 1);
        if(computer.get(0) == 1){
            cTotal += 11;
            pAce.add(11);
            dealerImages.add("AS.jpg");
        } else if(computer.get(0) == 11){
            cTotal += 10;
            dealerImages.add("JS.jpg");
        } else if(computer.get(0) == 12){
            cTotal += 10;
            dealerImages.add("QS.jpg");
        } else if(computer.get(0) == 13){
            cTotal += 10;
            dealerImages.add("KS.jpg");
        } else {
            cTotal += computer.get(0);
            dealerImages.add(computer.get(0) + "S.jpg");
        }
        if(computer.get(1) == 1){
            cTotal += 11;
            cAce.add(11);
            dealerImages.add("AS.jpg");
        } else if(computer.get(1) == 11){
            cTotal += 10;
            dealerImages.add("JS.jpg");
        } else if(computer.get(1) == 12){
            cTotal += 10;
            dealerImages.add("QS.jpg");
        } else if(computer.get(1) == 13){
            cTotal += 10;
            dealerImages.add("KS.jpg");
        } else {
            cTotal += computer.get(1);
            dealerImages.add(computer.get(1) + "S.jpg");
        }
        cAceCheck(cTotal);
        dealer.add(new JLabel(new ImageIcon(dealerImages.get(0))));
        dealer.add(new JLabel(new ImageIcon("back.jpg")));
        dealer.pack();
        dealer.setVisible(true);   
    }

    public static void hit(){
        player.add((int) (Math.random() * 13) + 1);
        if(player.get(player.size() - 1) == 1){
            dealtCards += ", Ace";
            pTotal += 11; 
            pAce.add(11);
            images.add("AS.jpg");
        } else if(player.get(player.size() - 1) == 11){
            dealtCards += ", Jack";
            pTotal += 10;
            images.add("JS.jpg");
        } else if(player.get(player.size() - 1) == 12){
            dealtCards += ", Queen";
            pTotal += 10;
            images.add("QS.jpg");
        } else if(player.get(player.size() - 1) == 13){
            dealtCards += ", King";
            pTotal += 10;
            images.add("KS.jpg");
        } else {
            dealtCards += ", " + player.get(player.size() - 1);
            pTotal += player.get(player.size() - 1);
            images.add(player.get(player.size() - 1) + "S.jpg");
        }
        pAceCheck(pTotal);
        frame.add(new JLabel(new ImageIcon(images.get(images.size() - 1))));
        frame.pack();
        frame.setTitle("Hand: " + String.valueOf(pTotal));
        //System.out.println("Your cards are: " + dealtCards + " (" + pTotal + ")");
        if(blackJack()){
            restart.setVisible(false);
            money += bet * 2;
            startOver();
        }

        if(pBust()){
            restart.setVisible(false);
            startOver();
        }

    }

    public static void cHit(){
        computer.add((int) (Math.random() * 13) + 1);
        if(computer.get(computer.size() - 1) == 1){
            cTotal += 11; 
            cAce.add(11);
            dealerImages.add("AS.jpg");
        } else if(computer.get(computer.size() - 1) == 11){
            cTotal += 10;
            dealerImages.add("JS.jpg");
        } else if(computer.get(computer.size() - 1) == 12){
            cTotal += 10;
            dealerImages.add("QS.jpg");
        } else if(computer.get(computer.size() - 1) == 13){
            cTotal += 10;
            dealerImages.add("KS.jpg");
        } else {
            cTotal += player.get(player.size() - 1);
            dealerImages.add(computer.get(computer.size() - 1) + "S.jpg");
        }
        cAceCheck(cTotal);
    }

    public static void stand(){
        while(cTotal < 17){
            cHit();
        }
        System.out.println("Total: " + pTotal);
        if((pTotal > cTotal || cTotal > 21) && pTotal <= 21){
            System.out.println("You win! Dealers Cards: " + cTotal);
            money += betM * 2;
        }
        if(cTotal > pTotal && cTotal <= 21){
            System.out.println("You lose! Dealers Cards: " + cTotal);
        }
        if(cTotal == pTotal && pTotal <=21){
            System.out.println("You tied!");
            money += betM;
        }
        dealer.getContentPane().removeAll();
        for(int i = 0; i < dealerImages.size(); i++){
            dealer.add(new JLabel(new ImageIcon(dealerImages.get(i))));
            dealer.pack();
            dealer.revalidate();
            dealer.repaint();
        }
        dealer.revalidate();
        dealer.repaint();
        dealer.setVisible(true);
    }

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
