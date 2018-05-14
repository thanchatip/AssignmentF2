import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Menu extends JFrame{
    private int statePlay = 0;
    
    public Menu(){
        super("Space War"); 
        while(statePlay == 0){
            
            setSize(400,600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setLayout(new BorderLayout());
            
            JPanel x = new JPanel();
        
            JButton startBt = new JButton("Start Game");
            startBt.setPreferredSize(new Dimension(300, 50));
            startBt.setFont(startBt.getFont().deriveFont(24.0f));
            
            startBt.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae1){
                    statePlay = 1;
                }
            });
            x.add(startBt);
          
            getContentPane().add(x, BorderLayout.PAGE_END);
        
	

            setVisible(true);
        }
        
    } 
}  
