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

	public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}

		 
		@Override
		public void paint(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			model.setDim(model.getLabels().size());
			int dim = model.getDim();

			int offset = 50;
			int plotWidth = getWidth()/2;

			Rectangle2D matrixCell = new Rectangle2D.Double(offset,offset,plotWidth/dim,plotWidth/dim);
			g2D.translate(offset,offset);


			// drawing the plot grid
			int x_spacing = 0;
			int y_spacing = 0;
			// draw a rectangle regarding the offset while adding space to the border
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					matrixCell.setRect(offset + x_spacing, offset + y_spacing, plotWidth / dim, plotWidth / dim);
					x_spacing += plotWidth / dim +5;
					g2D.draw(matrixCell);
					plotMatrix.add(offset+x_spacing);
					plotMatrix.add(offset+y_spacing);
					plotMatrix.add(offset+x_spacing+plotWidth / dim);
					plotMatrix.add(offset+x_spacing+plotWidth / dim);
				}
				x_spacing = 0;
				y_spacing += plotWidth / dim +5;
			}

			// adding labels
			int spacing = plotWidth/dim;
			int i = 0;
	        for (String l : model.getLabels()) {
				g2D.drawString(l,spacing*i+offset,20);
				g2D.drawString(l,20,spacing*i+offset);
				i++;
			}
			for (Range r : model.getRanges()) {

			}

			// filling plots
			// for every bounding box draw the point pairs and adapt the value to the box sizes
			for (Data d : model.getList()) {
				for (int c = 0; c <= plotMatrix.size()+3; c+=4) {
					for (double x : d.getValues()) {
						for (double y : d.getValues()) {
							int x_min = plotMatrix.get(c);
							int y_min = plotMatrix.get(c + 1);
							int x_max = plotMatrix.get(c + 2);
							int y_max = plotMatrix.get(c + 3);
							Rectangle2D point = new Rectangle2D.Double((x_min+x) % x_max,(y_min+y) % x_min,2,2);
							g2D.draw(point);
							Debug.print(x_min + " " + y_min + " " + x_max + " " + y_max + "\n");
							c += 4;
						}

					}
				}
			}
			
		}
		public void setModel(Model model) {
			this.model = model;
		}
}
