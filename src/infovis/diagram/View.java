package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;



public class View extends JPanel{
	private Model model = null;
	private Color color = Color.BLUE;
	private double scale = 1;
	private double translateX= 0;
	private double translateY=0;
	private Rectangle2D marker = new Rectangle2D.Double(0,0,getWidth()/4, getHeight()/4);
	private Rectangle2D overviewRect = new Rectangle2D.Double();   

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	public void paint(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.clearRect(0, 0, getWidth(), getHeight());


		scale = getScale();
		overviewRect.setRect(0,0,getWidth()/4, getHeight()/4);
		g2D.setColor(Color.WHITE);
		g2D.fill(overviewRect);
		g2D.setColor(Color.BLACK);
		g2D.draw(overviewRect);

		marker = getMarker();
		updateMarker(translateX,translateY);
		g2D.setColor(Color.BLACK);
		g2D.draw(marker);

		g2D.scale(scale, scale);
		g2D.translate(-translateX*4,-translateY*4);
		paintDiagram(g2D);


		g2D.translate(+translateX*4,+translateY*4);
		g2D.scale(0.25/scale,0.25/scale);
		paintDiagram(g2D);
		
	}
	private void paintDiagram(Graphics2D g2D){
		for (Element element: model.getElements()){
			element.paint(g2D);
		}
	}

	
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale(){
		return scale;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double tansslateY) {
		this.translateY = tansslateY;
	}
	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}	
	public void updateMarker(double x, double y){

		marker.setRect(x, y, 1/scale*getWidth()/4, 1/scale*getHeight()/4);
	}
	public Rectangle2D getMarker(){
		return marker;
	}
	public Rectangle2D getOverviewRect(){
		return overviewRect;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}
	public boolean overviewRectContains(int x, int y){
		return overviewRect.contains(x, y);
	}
}
 