import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JButton;


public class JButtonWithMLstnr extends JButton {

	public JButtonWithMLstnr(String text, MouseListener pMouseListener) {
		super(text);
		this.setPreferredSize(new Dimension(250, 30));
		this.setMinimumSize(new Dimension(250, 30));
		this.setMaximumSize(new Dimension(250, 30));
		this.addMouseListener(pMouseListener);
	}

}
