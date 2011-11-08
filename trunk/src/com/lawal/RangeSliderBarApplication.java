package com.lawal;

import com.lawal.RangeSliderBar.DoublePair;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class RangeSliderBarApplication extends Application {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5484943553531596125L;

	@Override
	public void init() {
		Window mainWindow = new Window("RangeSliderBar Application");
		
		VerticalLayout vlay = setup(); 
		
		mainWindow.addComponent(vlay);

		
		setMainWindow(mainWindow);
	}

	private VerticalLayout setup() {
		
		final RangeSliderBar bar = new RangeSliderBar();
		final TextField tf = new TextField();
		
		Button btn = new Button("Set value");
		
		btn.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					double dval = Double.parseDouble( (String) tf.getValue());
					bar.setSliderValue(   dval, dval+10);
				} catch (NumberFormatException e) {
					
					e.printStackTrace();
				}
			}
		});
		
		
		
		VerticalLayout vlay = new VerticalLayout();
		vlay.setWidth(500,Sizeable.UNITS_PIXELS);
		vlay.addStyleName(Reindeer.LAYOUT_BLUE);
		
		
		bar.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				
				DoublePair val = (DoublePair) event.getProperty().getValue(); 
				System.out.println("().new ValueChangeListener() {...}.valueChange()" + val._max+" "+val._min);
				tf.setValue(String.valueOf(val._max)+ " , "+val._min);
				
			}
		});
		bar.setNumberLabels(10);
		bar.setNumberTicks(10);
		bar.setRangeMin(-15);
		bar.setRangeMax(+15);
		bar.setStepSize(.5); 
		bar.setSuperImmediateMode(true);
		bar.setValue(10);
	

		bar.setImmediate(true);
		bar.setSizeFull();
		
		
		HorizontalLayout hlay = new HorizontalLayout();
		hlay.addComponent(tf);
		hlay.addComponent(btn);
		vlay.addComponent(hlay);
		vlay.addComponent(bar);
		return vlay;
	}

}
