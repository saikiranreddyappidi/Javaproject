package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SumInSwing implements ActionListener {
    JLabel l1,l2,l3;
    JTextField t1,t2;
    JButton b1;
    SumInSwing(){
        JFrame frame=new JFrame("Sum of two numbers");
        frame.setLayout(new FlowLayout());
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        l1=new JLabel("Enter First Number");
        t1=new JTextField(10);
        l2=new JLabel("Enter Second Number");
        t2=new JTextField(10);
        b1=new JButton("Add");
        l3=new JLabel("Result");
        frame.add(l1);
        frame.add(t1);
        frame.add(l2);
        frame.add(t2);
        frame.add(b1);
        frame.add(l3);
        b1.addActionListener(this);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new SumInSwing();
    }
    public void actionPerformed(ActionEvent e){
        int a=Integer.parseInt(t1.getText());
        int b=Integer.parseInt(t2.getText());
        int c=a+b;
        l3.setText("Sum is "+c);
    }
}
