package infovis.paracoords;

import infovis.scatterplot.Data;
import infovis.scatterplot.Model;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;
	private double scaling;

	public double getScaling() {
		return scaling;
	}

	public void setScaling(double scaling) {
		this.scaling = scaling;
	}

	public double getNextScaling() {
		return nextScaling;
	}

	public void setNextScaling(double nextScaling) {
		this.nextScaling = nextScaling;
	}

	private double nextScaling;

	public ArrayList<Line2D> getLines() {
		return lines;
	}

	public void setLines(ArrayList<Line2D> lines) {
		this.lines = lines;
	}

	private ArrayList<Line2D> lines = new ArrayList<>();


	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		model.setDim(model.getLabels().size());
		int dim = model.getDim();
		g2D.setColor(Color.black);
		int stepSize = getWidth()/dim;
		int lineLength = 4*getHeight()/dim;
		for(int i = 0; i < dim; i++) {
			g2D.drawLine(stepSize*(i+1),0,stepSize*(i+1),lineLength);
		}


		for(int i = 0; i < dim-1; i++) {
			double rangedCoord, nextCoord;
			double min = model.getRanges().get(i).getMin();
			double max = model.getRanges().get(i).getMax();
			double nextMin = model.getRanges().get(i+1).getMin();
			double nextMax = model.getRanges().get(i+1).getMax();
			scaling = lineLength / (max-min);
			nextScaling = lineLength / (nextMax-nextMin);
			for (Data d : model.getList()) {
				rangedCoord = (d.getValue(i)-min) * scaling;
				nextCoord = (d.getValue(i+1)-nextMin) * nextScaling;
				Line2D line = new Line2D.Double(stepSize*i, rangedCoord, stepSize*(i+1), nextCoord);
				lines.add(line);
				g2D.setColor(d.getColor());
				g2D.draw(line);

			}
		}

		//System.out.println(model.getList());
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}
