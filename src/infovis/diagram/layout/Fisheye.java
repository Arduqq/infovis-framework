package infovis.diagram.layout;

import infovis.debug.Debug;
import infovis.diagram.Model;
import infovis.diagram.View;
import infovis.diagram.elements.Edge;
import infovis.diagram.elements.Vertex;

import java.awt.geom.Point2D;
import java.util.Iterator;

/*
 * 
 */

public class Fisheye implements Layout{


	private int mousePositionX = 0;
	private int mousePositionY = 0;

	private double frameSizeX[];
	private double frameSizeY[];
	private double frameCoordX[];
	private double frameCoordY[];

	public void setMouseCoords(int x, int y, View view) {
		double vertAmount = frameCoordX.length;
		for (int i = 0; i < vertAmount; ++i){
			Vertex vert = view.getModel().getVertices().get(i);
			vert.setFrame(frameCoordX[i], frameCoordY[i], frameSizeX[i], frameSizeY[i]);
		}

		mousePositionX = x;
		mousePositionY = y;

	}

	public Model transform(Model model, View view) {
		Point2D centerFisheye = new Point2D.Double(mousePositionX,mousePositionY);
		Model modelFisheye = view.getModel();
		int vertAmount = modelFisheye.getVertices().size();

		frameSizeX = new double[vertAmount];
		frameSizeY = new double[vertAmount];
		frameCoordX = new double[vertAmount];
		frameCoordY = new double[vertAmount];

		// calculate new position of every vertice
		for (int i = 0; i < vertAmount; i++) {
			Vertex vertex = modelFisheye.getVertices().get(i);
			frameCoordX[i] = vertex.getX();
			frameCoordY[i] = vertex.getY();
			frameSizeX[i] = vertex.getWidth();
			frameSizeY[i] = vertex.getHeight();

			// get centers, endpoint and its normal
			double vertexCenterX = vertex.getCenterX();
			double vertexCenterY = vertex.getCenterY();
			double vertexEndX = vertex.getX();
			double vertexEndY = vertex.getY();
			double vertexEndNormX, vertexEndNormY;

			// normalization
			if (vertexCenterX < centerFisheye.getX()) {
				vertexEndNormX = vertex.getX();
			} else {
				vertexEndNormX = vertex.getMaxX();
			}
			if (vertexCenterY < centerFisheye.getY()) {
				vertexEndNormY = vertex.getY();
			} else {
				vertexEndNormY = vertex.getMaxY();
			}

			// calculation of the vertex
			vertexCenterX = calculateNewPosition(centerFisheye.getX(),vertexCenterX,view.getWidth());
			vertexCenterY = calculateNewPosition(centerFisheye.getY(),vertexCenterY,view.getHeight());
			vertexEndX = calculateNewPosition(centerFisheye.getY(),vertexEndX,view.getWidth());
			vertexEndY = calculateNewPosition(centerFisheye.getY(),vertexEndY,view.getHeight());
			vertexEndNormX = calculateNewPosition(centerFisheye.getY(),vertexEndNormX,view.getWidth());
			vertexEndNormY = calculateNewPosition(centerFisheye.getY(),vertexEndNormY,view.getHeight());


			double sx = Math.abs(vertexEndNormX - vertexCenterX);
			double sy = Math.abs(vertexEndNormY - vertexCenterY);
			double s = 2 * Math.min(sx, sy);

			vertex.setFrame(vertexEndX, vertexEndY, s, s * (vertex.getHeight()/vertex.getWidth()));

		}

		view.setModel(modelFisheye);
		view.repaint();

		return modelFisheye;
	}

	private double calculateNewPosition(double fisheyeCoord, double length, double limit) {
		double distanceMax;
		double distanceNorm = length - fisheyeCoord;


		if(length < fisheyeCoord) {
			distanceMax = -fisheyeCoord;
		} else {
			distanceMax = limit - fisheyeCoord;
		}

		return (fisheyeCoord + g(distanceNorm / distanceMax) * distanceMax);

	}

	private double g(double x) {
		double d = 2;
		return (((d + 1) * x) / (d * x + 1));
	}
	
}
