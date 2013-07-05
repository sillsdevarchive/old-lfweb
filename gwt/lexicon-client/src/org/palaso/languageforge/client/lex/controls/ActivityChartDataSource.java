package org.palaso.languageforge.client.lex.controls;

public class ActivityChartDataSource {
	private double valueX;
	private double valueY;

	public ActivityChartDataSource(double valueX, double valueY) {
		this.setValueX(valueX);
		this.setValueY(valueY);
	}

	public double getValueY() {
		return valueY;
	}

	public void setValueY(double value) {
		this.valueY = value;
	}

	public double getValueX() {
		return valueX;
	}

	public void setValueX(double value) {
		this.valueX = value;
	}
}
