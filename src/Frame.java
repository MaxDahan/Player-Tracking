import javax.swing.JFrame;

public class Frame extends JFrame {
	
	public Frame(Main main, int GWIDTH, int GHEIGHT) {
		requestFocus();
		setSize(GWIDTH, GHEIGHT);
		setUndecorated(true);
		
		add(main);
		setVisible(true);
	}
}
