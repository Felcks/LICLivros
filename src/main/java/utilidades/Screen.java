package utilidades;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Screen {
	
	public static int width;
	public static int height;
	
	public static void start(){
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dim = toolkit.getScreenSize();
		width = dim.width;
		height = dim.height;
	}
	
	
}