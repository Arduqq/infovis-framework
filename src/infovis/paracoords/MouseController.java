package infovis.paracoords;

import infovis.debug.Debug;
import infovis.scatterplot.Data;
import infovis.scatterplot.Model;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MouseController implements MouseListener, MouseMotionListener {
	private View view = null;
	private Model model = null;
	Shape currentShape = null;
	private double start_x, start_y;
	private  boolean brushing;

	private ArrayList<Line2D> lines;
	private ArrayList<Line2D> selected = new ArrayList<>();
	
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
		lines = view.getLines();
		selected.clear();
		view.setMarkerRectangle(new Rectangle2D.Double(0.0,0.0,0.0,0.0));
		for(Data d : model.getList()) {
			d.setColor(Color.GRAY);
		}

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		if (brushing) {
			double end_x = e.getX();
			double end_y = e.getY();


			double x = Math.min(start_x,end_x);
			double y = Math.min(start_y,end_y);

			double width = Math.max(start_x - end_x, end_x - start_x);
			double height = Math.max(start_y - end_y, end_y - start_y);

			view.setMarkerRectangle(new Rectangle2D.Double(x, y, width, height));


			for (Line2D line : lines) {
				if (view.getMarkerRectangle().intersectsLine(line)) {
					if (!selected.contains(line))
						selected.add(line);
				}
			}

			int i = 0;
			for (Line2D l : selected) {
				for (Data d : model.getList()) {

					Line2D currLine = lines.get(i);
					Debug.println(""+i);
					//Debug.println(currLine.getX1()+" "+l.getX1());
					if (currLine.getX1() == l.getX1() && currLine.getY1() == l.getY1()
							&& currLine.getX2() == l.getX2() && currLine.getY2() == l.getY2()) {

						d.setColor(Color.GREEN);
						i=0;
						break;
					}

					i++;
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
