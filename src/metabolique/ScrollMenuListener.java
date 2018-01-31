package metabolique;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;

public class ScrollMenuListener implements ListSelectionListener {

	ScrollMenu scrollMenu;

	/**
	 * creation de la carte generique
	 */
	protected GenericGraph graphic;
	/**
	 * creation du graphique
	 */
	protected Graph graph; 
	/**
	 * creation du viewer pour le graphique
	 */
	protected Viewer viewer;

	protected FenetreGraphique fenetre;

	ScrollMenuListener(FenetreGraphique fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void valueChanged(ListSelectionEvent e) {
//		if (e.getSource() == fenetre.scrollMenu.pathMenu && !e.getValueIsAdjusting())
//		{
//			String selectedValue = (String) scrollMenu.getPathList().getSelectedValue().toString().substring(4);
//
//			fenetre.graphic = new GenericGraph(selectedValue);
//			fenetre.graphic.setDefaultStylesheet();
//			fenetre.graph = graphic.getGraphic();
//			fenetre.viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
//			fenetre.viewer.disableAutoLayout();
//
//			ViewPanel view = viewer.addDefaultView(false);
//
//			// Add the view to self and make it visible
//			fenetre.setVisible(true);
//			view.setSize(new Dimension(200, 200));
//			view.setBackground(Color.LIGHT_GRAY);
//			fenetre.panelGraphe.setLayout(new BorderLayout());
//			fenetre.panelGraphe.add(view, BorderLayout.CENTER);
//
//			// Refresh the window
//			fenetre.repaint();
//			fenetre.revalidate();
//
//		}
//
//		if (e.getSource() == fenetre.scrollMenu.speciesMenu && !e.getValueIsAdjusting())
//		{
//			graphic.setDefaultStylesheet();
//			String selectedValue = (String) scrollMenu.getSpeciesList().getSelectedValue().toString();
//			graphic.updateStylesheet(selectedValue);
//		}
//	}

}
