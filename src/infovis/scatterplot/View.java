package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0); 

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

			int rowHeight = plotWidth/dim;
			for (int i = 0; i < dim+1; i++)
				g2D.drawLine(offset,offset+i*rowHeight,offset+plotWidth, offset+i*rowHeight);

			int rowWidth = plotWidth/dim;
			for (int i = 0; i < dim+1; i++)
				g2D.drawLine(offset+i*rowWidth, offset, offset+i*rowWidth, offset+plotWidth);

			int i = 0;
	        for (String l : model.getLabels()) {
				g2D.drawString(l,rowHeight*i+offset,20);
				g2D.drawString(l,20,rowHeight*i+offset);
				i++;
			}
			for (Range range : model.getRanges()) {

	        }
			for (Data d : model.getList()) {
	        	for (double x : d.getValues()) {
					for (double y : d.getValues()) {
						Rectangle2D point = new Rectangle2D.Double(offset+(x-1)*2,offset+(y-1)*2,2,2);
						g2D.draw(point);
					}
				}

				Debug.print(d.toString());
				Debug.println("");
			}
	        
			
		}
		public void setModel(Model model) {
			this.model = model;
		}
}
