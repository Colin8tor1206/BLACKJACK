//stuff to do: Aces still broken, start over still broken
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
public class Main extends EverythingElse {
    static int money = 500;
    static int betM;
    public static void main(String[] args) {
        //This creates the buttons
        Frame f=new Frame("ActionListener Example");
        f.setLayout(null);
        Button hit = new Button("Hit");  
        hit.setBounds(65,50,290,30);  
        Button stand = new Button("Stand");  
        stand.setBounds(355,50,290,30);  
        Button bet = new Button("Bet");  
        bet.setBounds(210,190,290,30);  
        JLabel status = new JLabel("Slide the slider to input your bet");
        status.setBounds(260, 160, 300, 30);
        JSlider slider = new JSlider(0, getMoney());
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBounds(55,100,600,60);  
        JLabel moneys = new JLabel("$" + String.valueOf(money));
        moneys.setBounds(20, 260, 75, 30); 
        Hashtable position = new Hashtable();
        for(int i = 0; i < (money/25) + 1; i++){
            int number = i * 25;
            String sNumber = String.valueOf(number);
            position.put(number, new JLabel(sNumber));   
        }
        slider.setLabelTable(position);
        //waits until button "hit" is pressed
        hit.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    hit();
                    if(pBust()){
                        if(money > 0){
                            startOver();
                            //bet
                            f.add(bet);
                            f.add(status);
                            f.setVisible(true);
                            for(int i = 0; i < (money/25) + 1; i++){
                                int number = i * 25;
                                String sNumber = String.valueOf(number);
                                position.put(number, new JLabel(sNumber));   
                            }

                            bet.setVisible(true); 
                            status.setVisible(true); 
                            slider.setVisible(true); 
                            f.add(bet); 
                            f.add(status);
                            f.add(slider);
                            money -= betM;
                            moneys.setText("$" + String.valueOf(money));
                            bet.setVisible(false); 
                            status.setVisible(false); 
                            slider.setVisible(false); 
                            f.remove(bet); 
                            f.remove(status);
                            f.remove(slider);

                        } else {
                            System.out.println("You have no money!");
                        }
                    }
                }  
            });  
        //waits until button "stand" is pressed
        stand.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    stand(); 
                    if(startOver){
                        f.add(bet);
                        f.add(status);
                        f.setVisible(true);
                        bet.setVisible(true); 
                        status.setVisible(true); 
                        slider.setVisible(true); 
                        f.add(bet); 
                        f.add(status);
                        f.add(slider);
                        moneys.setText("$" + String.valueOf(money));
                        bet.setVisible(false); 
                        status.setVisible(false); 
                        slider.setVisible(false); 
                        f.remove(bet); 
                        f.remove(status);
                        f.remove(slider);
                        for(int i = 0; i < (money/25) + 1; i++){
                            int number = i * 25;
                            String sNumber = String.valueOf(number);
                            position.put(number, new JLabel(sNumber));   
                        }

                    }
                    if(blackjack){
                        money += betM;
                        startOver();
                    }
                }

            });  
        //bet button
        bet.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                    money -= betM;
                    moneys.setText("$" + String.valueOf(money));
                    bet.setVisible(false); 
                    status.setVisible(false); 
                    slider.setVisible(false); 
                    f.remove(bet); 
                    f.remove(status);
                    f.remove(slider);
                    deal();
                    f.add(hit);
                    f.add(stand);
                }  
            });  
        slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    status.setText("Value of the slider is: " + ((JSlider)e.getSource()).getValue());
                    betM = ((JSlider)e.getSource()).getValue();
                }
            });

        f.add(slider);
        f.setSize(750,300);    
        f.add(bet);
        f.add(status);
        f.add(moneys);
        f.setVisible(true);  
    }
}

