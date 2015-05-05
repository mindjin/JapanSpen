package awtproject;

import java.awt.event.*;

public class MyMouseMotionAdapter extends MouseMotionAdapter{
	AdapterDemo adapterDemo;
	public MyMouseMotionAdapter(AdapterDemo adapterDemo){
		this.adapterDemo = adapterDemo;
	}
	
	public void mouseDragged(MouseEvent me){
		adapterDemo.showStatus("Mouse dragged");
	}

}
