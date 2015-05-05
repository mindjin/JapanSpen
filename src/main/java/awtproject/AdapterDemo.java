package awtproject;

import java.applet.Applet;

public class AdapterDemo extends Applet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1036617242183714133L;

	public void init(){
		addMouseListener(new MyMouseAdapter(this));
		addMouseMotionListener(new MyMouseMotionAdapter(this));
	}
	
	

}
