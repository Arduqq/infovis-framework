package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	     private ArrayList<Integer> plotMatrix = new ArrayList<>();

	public void setMarkerRectangle(double min_x, double min_y, double max_x, double max_y) {
		this.markerRectangle = new Rectangle2D.Double(min_x+50, min_y+50, (max_x+50 - min_x+50), (max_y+50 - min_y+50));
	}

	public ArrayList<Integer> getPlotMatrix() {
		return plotMatrix;
	}

	public void setPlotMatrix(ArrayList<Integer> plotMatrix) {
		this.plotMatrix = plotMatrix;
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	private int spacing = 0;

	public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}

		 
		@Override
		public void paint(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2D.clearRect(0, 0, getWidth(), getHeight());
			int dim = model.getLabels().size();

			int offset = 30;
			int plotWidth = getWidth()/2;

			Rectangle2D matrixCell = new Rectangle2D.Double(offset,offset,plotWidth/dim,plotWidth/dim);


			// drawing the plot grid
			int x_spacing = 0;
			int y_spacing = 0;


			spacing = (plotWidth - offset) / dim;
			// draw a rectangle regarding the offset while adding space to the border
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					matrixCell.setRect(offset + x_spacing, offset + y_spacing, spacing, spacing);
					g2D.draw(matrixCell);
					plotMatrix.add(offset+x_spacing);
					plotMatrix.add(offset+y_spacing);
					plotMatrix.add(offset+x_spacing+plotWidth / dim);
					plotMatrix.add(offset+x_spacing+plotWidth / dim);

					x_spacing += spacing;
				}
				x_spacing = 0;
				y_spacing += spacing;
			}

			// adding labels

			int i = 0;
	        for (String l : model.getLabels()) {
				g2D.drawString(l,spacing*i+(plotWidth/dim),20);
				g2D.drawString(l,20,spacing*i+(plotWidth/dim));
				i++;
			}


			// filling plots
			// for every bounding box draw the point pairs and adapt the value to the box sizes
			for (int k = 0; k < dim; k++) {
	        	for (int l = 0; l < dim; l++) {
	        		double dx, dy;
					double x_min = model.getRanges().get(k).getMin();
					double x_max = model.getRanges().get(k).getMax();
					double y_min = model.getRanges().get(l).getMin();
					double y_max = model.getRanges().get(l).getMax();
					double scaling_x = spacing / (x_max - x_min);
					double scaling_y = spacing / (y_max - y_min);

					g2D.translate((k*spacing),(l*spacing));

	        		for (Data d : model.getList()) {
						dx = (d.getValue(k) - x_min) * scaling_x;
						dy = (d.getValue(l) - y_min) * scaling_y;
						Rectangle2D point = new Rectangle2D.Double(offset + dx-1,offset+dy-1,2,2);
						g2D.setColor(d.getColor());
						g2D.draw(point);
					}
					g2D.translate((-k*spacing),(-l*spacing));
				}
			}
			g2D.setColor(Color.DARK_GRAY);
			g2D.draw(markerRectangle);

		}
		public void setModel(Model model) {
			this.model = model;
		}
}
