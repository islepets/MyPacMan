package pacman;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Font;
import javax.swing.ImageIcon;

public class RecMenu {
	private static JTable table;
	private static DefaultTableModel model;
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(StartMenu.class.getResource("/map/backgroundvutton_2.jpg")));
		frame.getContentPane().setBackground(new Color(0, 0, 0));
		frame.setTitle("Курсовая работа");
		frame.setBounds(630, 270, 660, 500);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		JButton btnNewButton_2 = new JButton("Стартовое меню");
		btnNewButton_2.setIcon(new ImageIcon(RecMenu.class.getResource("/map/menu.jpg")));
		btnNewButton_2.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnNewButton_2.setBackground(new Color(255, 255, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartMenu.main(new String[]{});
				frame.dispose(); 
			}
		});
		btnNewButton_2.setBounds(30, 390, 170, 47);
		frame.getContentPane().add(btnNewButton_2);
		
		
		model = new DefaultTableModel(new String[]{"Имя", "Результат", "Дата"}, 0);
		table = new JTable(model);
	    table.setForeground(new Color(255, 255, 255));
	    table.getColumnModel().getColumn(0).setPreferredWidth(187);
	    table.getColumnModel().getColumn(0).setMinWidth(187);
	    table.getColumnModel().getColumn(1).setPreferredWidth(187);
	    table.getColumnModel().getColumn(1).setMinWidth(187);
	    table.getColumnModel().getColumn(2).setPreferredWidth(186);
	    table.getColumnModel().getColumn(2).setMinWidth(187);
	    table.setFont(new Font("Arial Black", Font.PLAIN, 18));
	    table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    table.setBounds(30, 28, 560, 295);
	    table.setBackground(new Color(0,0,0));
	    table.setGridColor(new Color(255,255,255));
	    table.setRowHeight(58);
	    
	    
	    try {
	        FileReader fileReader = new FileReader("table.txt");
	        BufferedReader buffReader = new BufferedReader(fileReader);
	        
	        String line;
	        while ((line = buffReader.readLine()) != null) {
	            String[] tbl = line.split(" ");
	            model.addRow(tbl);
	        }
	        fileReader.close();
	        buffReader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    	
	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
	    sorter = new TableRowSorter<>(model);
	    table.setRowSorter(sorter);
	    sorter.setSortsOnUpdates(true);
	    sorter.setComparator(1, (o1, o2) -> {
	        String s1 = (String) o1;
	        String s2 = (String) o2;
	        return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
	    });
	    sorter.setSortable(1, false);
	    sorter.toggleSortOrder(1); 
	    sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.DESCENDING)));
	    
	    
	  
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setEnabled(false);
	    scrollPane.setBounds(30, 28, 583, 295);
	    frame.getContentPane().add(scrollPane);
	}
}
