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
	private double start_x, start_y, tstart_x, tstart_y, mx, my;
	private boolean drawing;
	// start_x will be the original starting point; whereas, tstart will be modified in the proces (please don't punch me)
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
		tstart_x = start_x;
		tstart_y = start_y;
		mx = wholeMatrix.getX();
		my = wholeMatrix.getY();
		if (wholeMatrix.contains(tstart_x,tstart_y)) {
			Debug.println("I'm in.");
			tstart_x = tstart_x - mx;
			tstart_y = tstart_y - my;
			dx = (int)(tstart_x)/spacing;
			dy = (int)(tstart_y)/spacing;
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
			double end_x = arg0.getX();
			double end_y = arg0.getY();
			double mark_x = Math.min(start_x,end_x);
			double mark_y = Math.min(start_y,end_y);
			double width = Math.max(start_x - end_x, end_x - start_x);
			double height = Math.max(start_y - end_y, end_y - start_y);
			double spacing = view.getSpacing();

			double min_x = max(min(tstart_x, x), dx * spacing);
			double min_y = max(min(tstart_y, y), dy * spacing);
			double max_x = min(max(tstart_x, x), (dx + 1) * spacing);
			double max_y = min(max(tstart_y, y), (dy + 1) * spacing);
			view.setMarkerRectangle(new Rectangle2D.Double(mark_x,mark_y, width, height));


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
