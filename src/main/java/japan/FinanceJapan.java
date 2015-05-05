package japan;

import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class FinanceJapan{

	
	
	JFrame jfrm;
	
	JSlider expanseSlider, savingSlider;
	
	JRadioButton expanseNow, expanseNextTime;
	
	JTextField dailyExpansive, redDate,
	averageSpend,saving,dataSaving,
	averageSpendSaving, nameIncome,costIncome, daysSaving, daysIncome;
	
	JButton consider, addSpend, addIncome, openSpend, openSpendNextTime, considerSaving;
	
	JCheckBox checkSpend, checkSpendAndNextTime, checkAll;
	
	Tables tables;
	
	FinanceJapan(){
		jfrm = new JFrame("Test");
		
		jfrm.setSize(1000, 500);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jfrm.setLayout(new FlowLayout());
		jfrm.setVisible(true);
		tables = new Tables(jfrm);
//		buttonsForm = new ButtonsForm(jfrm);
//		fieldsForm = new FieldsForm(jfrm);
		
		
		
		
		
		
	}
	
	
	
	

	public static void main (String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try{
					new FinanceJapan();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		});
		
	}
}
