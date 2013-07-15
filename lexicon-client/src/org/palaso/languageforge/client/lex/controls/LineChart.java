package org.palaso.languageforge.client.lex.controls;

import java.util.ArrayList;
import java.util.List;

import org.palaso.languageforge.client.lex.controls.gwtcanvas.Color;
import org.palaso.languageforge.client.lex.controls.gwtcanvas.GWTCanvas;

import com.google.gwt.user.client.Window.Navigator;
import com.googlecode.gchart.client.GChart;
import com.googlecode.gchart.client.GChartCanvasFactory;
import com.googlecode.gchart.client.GChartCanvasLite;

public class LineChart extends GChart  {

	List<ActivityChartDataSource> datasource = new ArrayList<ActivityChartDataSource>();

	public class GWTCanvasBasedCanvasFactory implements GChartCanvasFactory {

		public GChartCanvasLite create() {
			GChartCanvasLite result = new GWTCanvasBasedCanvasLite();
			return result;
		}

	}

	public class GWTCanvasBasedCanvasLite extends GWTCanvas implements GChartCanvasLite {
		// GChartCanvasLite requires CSS/RGBA color strings, but
		// GWTCanvas uses its own Color class instead, so we wrap:
		public void setStrokeStyle(String cssColor) {
			// Sharp angles of default MITER can overwrite adjacent pie slices
			setLineJoin(GWTCanvas.ROUND);
			setStrokeStyle(new Color(cssColor));
		}

		public void setFillStyle(String cssColor) {
			setFillStyle(new Color(cssColor));
		}
		// Note: all other GChartCanvasLite methods (lineTo, moveTo,
		// arc, etc.) are directly inherited from GWTCanvas, so no
		// wrapper methods are needed.
	}

	private boolean canvasDrawAllowed() {
		String agentString = Navigator.getUserAgent().toUpperCase();
		if (agentString.indexOf("MSIE 8") > 0 || agentString.indexOf("MSIE 7") > 0 || agentString.indexOf("MSIE 6") > 0) {
			return false;
		}
		return true;
	}

	public LineChart() {
		super(600, 400);
		// if it is IE and version lower then 9, we should not use HTML5 canvas.
		if (canvasDrawAllowed()) {
			setCanvasFactory(new GWTCanvasBasedCanvasFactory());
		}
		getXAxis().setAxisLabel("<small><b><i>Time</i></b></small>");
		getXAxis().setHasGridlines(false);
		getXAxis().setTickLabelFormat("##");
		getYAxis().setAxisLabel("<small><b><i>Activity</i></b></small>");

		getXAxis().setTickCount(30);
		getYAxis().setTickCount(10);
		getYAxis().setAxisMin(0);
		getYAxis().setAxisMax(150);

	}

	public void setDataSource(Curve curve, List<ActivityChartDataSource> datasource) {
		this.datasource = datasource;
		for (ActivityChartDataSource activity : datasource) {
			curve.addPoint((int) activity.getValueX(), activity.getValueY());
		}
	}

}
