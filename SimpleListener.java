package metabolique;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.peer.MenuItemPeer;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;

public class SimpleListener implements MenuListener, ActionListener, MenuKeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void menuSelected(MenuEvent e) {
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void menuKeyTyped(MenuKeyEvent e) {
		Object source = e.getSource();
		
		if(source == "item"){
			System.out.println("Vous avez cliqué ici.");
		} else if(source == "item2"){
			System.out.println("Vous avez cliqué là.");	
		}
		
	}

	@Override
	public void menuKeyPressed(MenuKeyEvent e) {
		Object source = e.getSource();
		
		if(source == "item"){
			System.out.println("Vous avez cliqué ici.");
		} else if(source == "item2"){
			System.out.println("Vous avez cliqué là.");	
		}
		
	}

	@Override
	public void menuKeyReleased(MenuKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		System.out.println("OKK");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
