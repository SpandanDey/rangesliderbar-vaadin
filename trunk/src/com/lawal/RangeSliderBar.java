/*
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * 
 * This is a direct port of the Google gwt RangeSliderBar into Vaadin obtained from
 * http://google-web-toolkit-incubator.googlecode.com/svn/trunk/demo/RangeSliderBar/index.html
 */
package com.lawal;

import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;

@SuppressWarnings("serial")
@com.vaadin.ui.ClientWidget(com.lawal.client.ui.VRangeSliderBar.class)
public class RangeSliderBar extends AbstractField {
	private double rangeMin = 0;
	private double rangeMax = 100;
	private double stepSize = 10;
	private int numTicks = 10;
	private int numLabels = 10;
	private boolean superImmediateMode = false;
	private double knobMinValue = 0;
	private double knobMaxValue = 100;

	/**
	 * Default Slider constructor. Sets all values to defaults and the slide
	 * handle at minimum value.
	 */
	public RangeSliderBar() {
		super();
		setKnobValues(knobMinValue, knobMaxValue, false);
	}

	/**
	 * Create a new slider with the caption given as parameter. All slider
	 * values set to defaults.
	 * 
	 * @param caption
	 */
	public RangeSliderBar(String caption) {
		this();
		setCaption(caption);
	}

	/**
	 * @param rangeMin
	 * @param rangeMax
	 * @param stepSize
	 */
	public RangeSliderBar(double min, double max, int stepsize) {
		this();
		setRangeMin(min);
		setRangeMax(max);
		setStepSize(stepsize);
	}

	/**
	 * Create a new slider with given range
	 * 
	 * @param rangeMin
	 * @param rangeMax
	 */
	public RangeSliderBar(int min, int max) {
		this();
		setRangeMin(min);
		setRangeMax(max);
		setStepSize(0);
	}

	/**
	 * Create a new slider with given caption and range
	 * 
	 * @param caption
	 * @param rangeMin
	 * @param rangeMax
	 */
	public RangeSliderBar(String caption, int min, int max) {
		this(min, max);
		setCaption(caption);
	}

	/**
	 * Gets the biggest possible value in Sliders range.
	 * 
	 * @return the biggest value slider can have
	 */
	public double getRangeMax() {
		return rangeMax;
	}

	/**
	 * Set the maximum value of the Slider. If the current value of the Slider
	 * is out of new bounds, the value is set to new minimum.
	 * 
	 * @param rangeMax
	 *            New maximum value of the Slider.
	 */
	public void setRangeMax(double rangeMax) {
		this.rangeMax = rangeMax;
		if ((new Double(getPairValue()._min.toString())).doubleValue() > rangeMax) {
			setKnobValues(knobMinValue, new Double(rangeMax), false);
		}
		// try {
		// } catch (final ClassCastException e) {
		// // FIXME: Handle exception
		// /*
		// * Where does ClassCastException come from? Can't see any casts
		// * above
		// */
		// setKnobValues( knobMinValue, new Double(rangeMax),false);
		// }
		requestRepaint();
	}

	/**
	 * Gets the minimum value in Sliders range.
	 * 
	 * @return the smalles value slider can have
	 */
	public double getRangeMin() {
		return rangeMin;
	}

	/**
	 * Set the minimum value of the Slider. If the current value of the Slider
	 * is out of new bounds, the value is set to new minimum.
	 * 
	 * @param rangeMin
	 *            New minimum value of the Slider.
	 */
	public void setRangeMin(double minRangeValue) {
		this.rangeMin = minRangeValue;
		if ((new Double(getPairValue()._max.toString())).doubleValue() < minRangeValue) {
			setKnobValues(new Double(minRangeValue), knobMaxValue, false);
		}
		// try {
		// } catch (final ClassCastException e) {
		// // FIXME: Handle exception
		// /*
		// * Where does ClassCastException come from? Can't see any casts
		// * above
		// */
		// }
		requestRepaint();
	}

	/**
	 * Get the current stepSize of the Slider.
	 * 
	 * @return stepSize
	 */
	public double getStepSize() {
		return stepSize;
	}

	/**
	 * Set a new stepSize for the Slider.
	 * 
	 * @param stepSize
	 */
	public void setStepSize(double stepSize) {
		if (stepSize < 0) {
			return;
		}
		this.stepSize = stepSize;
		requestRepaint();
	}

	/**
	 * Set the value of this Slider.
	 * 
	 * @param value
	 *            New value of Slider. Must be within Sliders range (rangeMin -
	 *            rangeMax),
	 *            otherwise throws an exception.
	 * @param repaintIsNotNeeded
	 *            If true, client-side is not requested to repaint itself.
	 * @throws ValueOutOfBoundsException
	 */
	private void setKnobValues(double min, double max, boolean repaintIsNotNeeded) {
		min = toMinDescrete(min);
		max = toMaxDescrete(max);
		super.setValue(new DoublePair(min, max), repaintIsNotNeeded);
	}

	private double toMaxDescrete(double newMax) {
		if (newMax < rangeMax) {
			return rangeMax;
		}
		return newMax;
	}

	private double toMinDescrete(double newMin) {
		if (newMin < rangeMin) {
			return rangeMin;
		}
		return newMin;
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		target.addAttribute("rangeMin", rangeMin);
		if (rangeMax > rangeMin) {
			target.addAttribute("rangeMax", rangeMax);
		} else {
			target.addAttribute("rangeMax", rangeMin);
		}
		target.addAttribute("stepsize", stepSize);
		target.addAttribute("numticks", numTicks);
		target.addAttribute("numlabels", numLabels);
		target.addAttribute("superimmediate", superImmediateMode);
		// if (stepSize > 0) {
		// target.addVariable(this, "value", ((Double)
		// getValue()).doubleValue());
		// } else {
		// target.addVariable(this, "value", ((Double) getValue()).intValue());
		// }
		target.addAttribute("knobmin", knobMinValue);
		target.addAttribute("knobmax", knobMaxValue);
	}

	/**
	 * Invoked when the value of a variable has changed. RangeSliderBar
	 * listeners are
	 * notified if the slider value has changed.
	 * 
	 * @param source
	 * @param variables
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);
		if (variables.containsKey("knobmin") && variables.containsKey("knobmax")) {
			final Double min = new Double(variables.get("knobmin").toString());
			final Double max = new Double(variables.get("knobmax").toString());
			if (min != null && max != null) {
				// try {
				setKnobValues(min, max, true);
				// } catch (final ValueOutOfBoundsException e) {
				// // Convert to nearest bound
				// double out = e.getValue().doubleValue();
				// if (out < rangeMin) {
				// out = rangeMin;
				// }
				// if (out > rangeMax) {
				// out = rangeMax;
				// }
				// super.setKnobValues(new Double(out), false);
				// }
			}
		}
	}

	public static class DoublePair {
		public DoublePair(double min, double max) {
			_min = min;
			_max = max;
		}

		public Double _min, _max;
	}

	@Override
	public Class<?> getType() {
		return DoublePair.class;
	}

	/**
	 * The number of tick marks to show.
	 */
	public void setNumberTicks(int numTicks) {
		this.numTicks = numTicks;
	}

	public int getNumberTicks() {
		return numTicks;
	}
	

	/**
	 * The number of labels to show.
	 */
	public void setNumberLabels(int numLabels) {
		this.numLabels = numLabels;
	}

	public int getNumberLabels() {
		return numLabels;
	}

	/**
	 * This method is used instead of {@link #setKnobValues(double)} without
	 * throwing
	 * exception. The val is set to rangeMax or rangeMin if it exceeds the
	 * rangeMin or rangeMax
	 * value
	 * accordingly
	 * 
	 * @param val
	 *            .
	 */
	public void setSliderValue(double min, double max) {
		if (max > rangeMax) {
			max = rangeMax;
		} else if (min < rangeMin) {
			min = rangeMin;
			return;
		} else
			setKnobValues(min, max, true);
	}

	/**
	 * If super immediate mode is true, values are immediately
	 * received from client when dragging with the mouse and
	 * when the Arrow keys are pressed down
	 * 
	 * @param superImmediateMode
	 */
	public void setSuperImmediateMode(boolean superImmediateMode) {
		if (superImmediateMode) this.setImmediate(true);
		this.superImmediateMode = superImmediateMode;
		requestRepaint();
	}

	public boolean isSuperImmediateMode() {
		return superImmediateMode;
	}

	public  DoublePair getPairValue() {
		return (DoublePair) super.getValue();
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		
		super.setValue(new DoublePair(knobMinValue, knobMaxValue));
	}
}
