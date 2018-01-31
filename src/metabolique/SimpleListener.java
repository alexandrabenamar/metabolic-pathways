package metabolique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SimpleListener implements MouseListener {
	
	@SuppressWarnings("unused")
	private ToolbarBrowser toolbarBrowser;

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Override
	public void mouseClicked(MouseEvent e) {
		@SuppressWarnings("unused")
		Object source = e.getSource();
//
//		if (source == toolbarBrowser.itemCapture) {
//			System.out.println("Capture");
//		}

//		else if (source == toolbarBrowser.getItemGenerique()) {
//			System.out.println("Générique");
//		}
//		
//		else if (source == toolbarBrowser.itemSpecifique) {
//			System.out.println("Spécifique");
//		}
//		
//		else if (source == toolbarBrowser.itemHelp) {
//			System.out.println("Help");
//		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

}
