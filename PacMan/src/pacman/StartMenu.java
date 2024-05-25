package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;

public class StartMenu {

	public static String name;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(StartMenu.class.getResource("/map/menu.jpg")));
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setTitle("Курсовая работа");
		frame.setBounds(630, 270, 660, 500);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("Имя: ");
		textArea_1.setForeground(new Color(255, 255, 255));
		textArea_1.setFont(new Font("Arial Black", Font.PLAIN, 18));
		textArea_1.setBackground(Color.BLACK);
		textArea_1.setBounds(49, 199, 180, 37);
		frame.getContentPane().add(textArea_1);
		
		JTextArea input = new JTextArea();
		input.setBackground(new Color(255, 255, 255));
		input.setFont(new Font("Arial Black", Font.PLAIN, 18));
		input.setBounds(239, 199, 213, 37);
		frame.getContentPane().add(input);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(0, 0, 0));
		textArea.setForeground(new Color(255, 255, 255));
		textArea.setFont(new Font("Arial Black", Font.PLAIN, 26));
		textArea.setText("Добро пожаловать!");
		textArea.setBounds(190, 115, 301, 47);
		frame.getContentPane().add(textArea);
		
		JButton btnNewButton = new JButton("Начать игру");
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setIcon(new ImageIcon(StartMenu.class.getResource("/map/backgroundbutton_1.jpg")));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                name = input.getText();
                if(!name.isEmpty()) {
                	Game.main(new String[]{});
                	frame.dispose();   
                }else {
                	JOptionPane.showMessageDialog(frame, "Введите имя", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
		btnNewButton.setBounds(74, 313, 170, 47);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Таблица рекордов");
		btnNewButton_1.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnNewButton_1.setIcon(new ImageIcon(StartMenu.class.getResource("/map/backgroundvutton_2.jpg")));
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RecMenu.main(new String[]{});
				frame.dispose(); 
			}
		});
		btnNewButton_1.setBounds(397, 313, 170, 47);
		frame.getContentPane().add(btnNewButton_1);
		frame.setVisible(true);
	}
}
