package infovis.paracoords;

import infovis.scatterplot.Data;
import infovis.scatterplot.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		model.setDim(model.getLabels().size());
		int dim = model.getDim();
		g2D.setColor(Color.black);
		for(int i = 0; i < dim; i++) {
			g2D.drawLine((getWidth()/dim)*(i+1),0,(getWidth()/dim)*(i+1),getHeight());
		}
		ArrayList list = new ArrayList();
		for (Data d : model.getList()) {
			Map<Double, Integer> hm = new HashMap<Double, Integer>();
			for(int i = 0; i < dim; i++) {
				//System.out.println(d.getValue(i));
				hm.put(d.getValue(i),getHeight()-(getHeight()/10)*hm.size());

			}
			list.add(hm);
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
