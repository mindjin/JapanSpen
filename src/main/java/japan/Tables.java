package japan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Tables  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2418137248025555190L;

	protected JFrame jframe;
	
	protected JTextField nameSpend,costSpend, averageCost,rub,yen, kursYen, dailyExpansive, redDate;
	protected JButton addNewSpend,averageSpend,save,load, deleteValue;
	protected JTable jtable;
	JSlider expanseSlider;
	SimpleDateFormat sdf;
	protected DefaultTableModel dtm;
	protected Tables form = null;

	public Tables(JFrame jframe) {
		this.jframe = jframe;
		sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		addSlider();
		addTable();
		addButtons();
		addFields();
	}
	
	public void addSlider(){
		expanseSlider = new JSlider(0,10000,0);
		jframe.add(expanseSlider);
		expanseSlider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				long summ = getSumm();
				if(summ>0){
					dailyExpansive.setText(Integer.toString(expanseSlider.getValue()));
					if(expanseSlider.getValue()>0){
						long currentlyDays = (summ/expanseSlider.getValue())*1000*60*60*24;
						System.out.println(expanseSlider.getValue());
						long days = System.currentTimeMillis() + currentlyDays;
						System.out.println(sdf.format(days));
						redDate.setText(sdf.format(days));}
					else
						redDate.setText(sdf.format(System.currentTimeMillis()));
			}
				else{
					expanseSlider.setValue(0);
					dailyExpansive.setText("0");
				}
			}
		});
	}
	
	public long getSumm(){
		return  (long)(Double.parseDouble(rub.getText())/Double.parseDouble(kursYen.getText())+Double.parseDouble(yen.getText()));
	}
	
	private void addFields(){
		redDate = new JTextField(sdf.format(System.currentTimeMillis()));
		rub = new JTextField("0",12);
		yen = new JTextField("0",12);
		dailyExpansive = new JTextField("daily",8);
		kursYen = new JTextField();
		nameSpend = new JTextField("NameSpend",12);
		costSpend = new  JTextField("CostSpend",12);
		averageCost = new JTextField("average",7);
		kursYen.setEnabled(false);		
		kursYen.setText(Currency.yenToRub());
		averageCost.setEnabled(false);
		jframe.add(dailyExpansive);
		jframe.add(redDate);
		jframe.add(rub);
		jframe.add(yen);
		jframe.add(kursYen);		
		jframe.add(averageCost);
		jframe.add(nameSpend);
		jframe.add(costSpend);		
		
	}
	
	private void addTable(){		
		jtable = new JTable(new DefaultTableModel(new Object[][]{},new String[]{"Name","Cost"				
		}));
		JScrollPane jsp = new  JScrollPane(jtable);
		
		tableListener();
		
		jframe.add(jsp);
	}

	private void tableListener() {
		jtable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				int rows = jtable.getRowCount();
				int averageSum = 0;
				for(int i=0;i<rows;i++){
					if(jtable.getValueAt(i, 1)!=null){
					averageSum = averageSum+Integer.parseInt((String) jtable.getValueAt(i, 1));
					
					}
					
					
				}
				if(averageSum>0)
					averageCost.setText(String.valueOf(averageSum/rows));
					
					
				
				
			}
		});
	}
	
	
	private void addButtons(){
		
		
		addNewSpend = new JButton("+");
		deleteValue = new JButton("DeleteValue");
		addNewSpend.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
					
				if(nameSpend.getText()!=null&&costSpend.getText()!=null){
					dtm = (DefaultTableModel) jtable.getModel();
					try{
					Integer.parseInt(costSpend.getText());
					String[] value = {nameSpend.getText(), costSpend.getText()};
					dtm.addRow(value);
					}catch(NumberFormatException n){
						costSpend.setText("Only digital");
					}
				}
				
			}
		});
		save = new JButton("Save");
		load = new JButton("Load");
		
		
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveObject();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		load.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loadObject();
					recoverData();
				} catch (ClassNotFoundException|IOException e1) {
					e1.printStackTrace();
//					JOptionPane.showMessageDialog(null, "File is not found");
				
				
				}	
			}
		});
		
		
		
		jframe.add(load);
		jframe.add(save);
		deleteValue();
		jframe.add(addNewSpend);
		jframe.add(deleteValue);
	}
	
	private void deleteValue(){
		deleteValue.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				dtm = (DefaultTableModel) jtable.getModel();
				if(jtable.getSelectedRow()!=-1)
				dtm.removeRow(jtable.getSelectedRow());
			}
		});
	
	
	

		
		
	}
	
	
	
	private void recoverData(){
		jtable.setModel(form.jtable.getModel());
		tableListener();
		averageCost.setText(form.averageCost.getText());
		nameSpend.setText("");
		costSpend.setText("");
		
	}
	
	protected void loadObject() throws IOException, ClassNotFoundException {
		
		FileInputStream fis = new FileInputStream(new File("src/main/resources/config.txt"));
		ObjectInputStream ois = new ObjectInputStream(fis);
		form = (Tables)ois.readObject();
		fis.close();
		ois.close();
		
}

	protected void saveObject() throws IOException{
	
	
		FileOutputStream fos = new FileOutputStream(new File("src/main/resources/config.txt"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		jtable.removeEditor();
		oos.writeObject(this);
		
		fos.close();
		oos.close();		
	
}
	
	private long getDay(String value) throws ParseException {
		Date date = new SimpleDateFormat().parse(value);
		long smf = date.getTime()-System.currentTimeMillis();
		return smf/1000/60/60/24;
	}

}
