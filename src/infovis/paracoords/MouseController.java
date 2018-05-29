package infovis.paracoords;

import infovis.debug.Debug;
import infovis.scatterplot.Data;
import infovis.scatterplot.Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	private double start_x, start_y;
	private  boolean brushing;
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		start_x = e.getX();
		start_y = e.getY();
		brushing = true;

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		if (brushing) {
			int end_x = e.getX();
			int end_y = e.getY();
			ArrayList<Line2D> lines = view.getLines();
			ArrayList<Line2D> selected = new ArrayList<>();

			for (Line2D line : view.getLines()) {
				if (line.intersectsLine(start_x,start_y,end_x,end_y)) {
					selected.add(line);
				}
			}

			for (Data d : model.getList()) {
				for (Line2D l : selected) {
					if (d.getValue(0) == l.getX1() &&
							d.getValue(1) == l.getY1()) {
						d.setColor(Color.GREEN);
					} else {
						d.setColor(Color.GRAY);
					}
				}
			}
		}
		view.repaint();


	}

	public void mouseMoved(MouseEvent e) {

	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

}
