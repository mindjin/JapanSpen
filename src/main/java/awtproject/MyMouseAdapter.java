package awtproject;

import java.awt.event.*;

public class MyMouseAdapter extends MouseAdapter{

	AdapterDemo adapterDemo;
	public MyMouseAdapter (AdapterDemo adapterDemo){
		this.adapterDemo = adapterDemo;
	}
	public void mouseClicked(MouseEvent me){
		adapterDemo.showStatus("Mouse clicked");
	}
}
