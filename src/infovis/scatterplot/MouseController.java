package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static java.lang.Double.max;
import static java.lang.Math.min;

public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;
	private double start_x, start_y, mx, my;
	private boolean drawing;
	private double range_min_x, range_min_y, range_max_x, range_max_y;
	private double scaling_x, scaling_y, local_min_x, local_min_y;
	private int dx, dy;

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
		int spacing = view.getSpacing();
		ArrayList<Integer> plotMatrix = view.getPlotMatrix();
		Rectangle2D wholeMatrix = new Rectangle2D.Double(plotMatrix.get(0),plotMatrix.get(1),
				plotMatrix.get(plotMatrix.size()-1),plotMatrix.get(plotMatrix.size()-2));
		start_x = arg0.getX();
		start_y = arg0.getY();
		mx = wholeMatrix.getX();
		my = wholeMatrix.getY();
		if (wholeMatrix.contains(start_x,start_y)) {
			Debug.println("I'm in.");
			start_x = start_x - mx;
			start_y = start_y - my;
			dx = (int)(start_x)/spacing;
			dy = (int)(start_y)/spacing;
			range_min_x = model.getRanges().get(dx).getMin();
			range_max_x = model.getRanges().get(dx).getMax();
			range_min_y = model.getRanges().get(dy).getMin();
			range_max_y = model.getRanges().get(dy).getMax();
			scaling_x = spacing / (range_max_x - range_min_x);
			scaling_y = spacing / (range_max_y - range_min_y);

			local_min_x = dx * spacing + mx;
			local_min_y = dy * spacing + my;
			drawing = true;

		}

	}

	public void mouseReleased(MouseEvent arg0) {
		drawing = false;
	}

	public void mouseDragged(MouseEvent arg0) {
		if (drawing) {
			Debug.println("I'm in drag.");
			double x = arg0.getX() - mx;
			double y = arg0.getY() - my;
			double spacing = view.getSpacing();

			double min_x = max(min(start_x, x), dx * spacing);
			double min_y = max(min(start_y, y), dy * spacing);
			double max_x = min(max(start_x, x), (dx + 1) * spacing);
			double max_y = min(max(start_y, y), (dy + 1) * spacing);
			//view.setMarkerRectangle(min_x, min_y, max_x, max_y);


			min_x = min_x - local_min_x + mx;
			max_x = max_x - local_min_x + my;
			min_y = min_y - local_min_y + mx;
			max_y = max_y - local_min_y + my;

			for (Data d : model.getList()) {
				double ex = (d.getValue(dx) - range_min_x) * scaling_x;
				double ey = (d.getValue(dy) - range_min_y) * scaling_y;

				if (min_x <= ex && ex <= max_x && min_y <= ey && ey <= max_y) {
					Debug.println("We're green.");
					d.setColor(Color.GREEN);
				} else {
					d.setColor(Color.RED);
					Debug.println("We're red.");
				}


			}
		}

		view.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void setModel(Model model) {
		this.model  = model;	
	}

	public void setView(View view) {
		this.view  = view;
	}

}
