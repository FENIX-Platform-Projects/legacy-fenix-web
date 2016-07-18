package org.fao.fenix.web.modules.birt.client.view.chart.viewer.format;


import org.fao.fenix.web.modules.birt.client.utils.ColorPicker;
import org.fao.fenix.web.modules.birt.client.utils.ColorPickerCaller;
import org.fao.fenix.web.modules.lang.client.BabelFish;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.IconButton;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;

public class TabTitle extends TabItem {

	ContentPanel mainCont;
	TextField<String> textTitle;
	ListBox sizeTitle;
	CheckBox visibleTitle;
	IconButton colorTitle;
	
	StylePanel stylePanel;
		
	public IconButton getColorTitle() {
		return colorTitle;
	}

	public TextField<String> getTextTitle() {
		return textTitle;
	}

	public void setTextTitle(TextField<String> textTitle) {
		this.textTitle = textTitle;
	}

	public ListBox getSizeTitle() {
		return sizeTitle;
	}

	public void setSizeTitle(ListBox sizeTitle) {
		this.sizeTitle = sizeTitle;
	}

	public CheckBox getVisibleTitle() {
		return visibleTitle;
	}

	public void setVisibleTitle(CheckBox visibleTitle) {
		this.visibleTitle = visibleTitle;
	}

	private void populateSizeList(ListBox l){
		for (int i=5; i <= 40; i++){
			l.addItem(String.valueOf(i));
		}
	}
	
	public TabTitle(){
		
		setText(BabelFish.print().chartTitle());
		
		mainCont = new ContentPanel();
		mainCont.setHeaderVisible(false);
		mainCont.setBodyBorder(false);
		
		VerticalPanel v = new VerticalPanel();
		v.setSpacing(10);
//		mainCont.setSpacing(10);
		
		stylePanel = new StylePanel();
		
		FieldSet fieldSet = new FieldSet();  
		fieldSet.setHeading("Title");
		
		FormLayout layout = new FormLayout();  
		layout.setLabelWidth(75);  
		layout.setLabelPad(4);
		fieldSet.setLayout(layout);
		
		HorizontalPanel hrFirst = new HorizontalPanel();
		hrFirst.setSpacing(5);
		hrFirst.add(new HTML("Text&nbsp;&nbsp;&nbsp;"));
		textTitle = new TextField<String>();
		textTitle.setAllowBlank(true);
		hrFirst.add(textTitle);
		fieldSet.add(hrFirst);
		fieldSet.setTitle("Title");
				
		HorizontalPanel hrSecond = new HorizontalPanel();
		hrSecond.setSpacing(5);
		String t = BabelFish.print().size() + "&nbsp;&nbsp;&nbsp;"; 
		hrSecond.add(new HTML(t));
		sizeTitle = new ListBox();
		populateSizeList(sizeTitle);
		sizeTitle.setWidth("45px");
		hrSecond.add(sizeTitle);
		fieldSet.add(hrSecond);
			
		HorizontalPanel hrThird = new HorizontalPanel();
		hrThird.setSpacing(5);
		hrThird.add(new HTML(BabelFish.print().visible()));
		visibleTitle = new CheckBox();
		hrThird.add(visibleTitle);
		fieldSet.add(hrThird);
		
		HorizontalPanel hrFourth = new HorizontalPanel();
		hrFourth.setSpacing(5);
		hrFourth.add(new HTML("Color"));
		colorTitle = new IconButton();
		colorTitle.setSize(60, 10);
		colorTitle.setStyleAttribute("border", "1px solid black");
		colorTitle.addSelectionListener(new SelectionListener<IconButtonEvent>(){
			
			public void componentSelected(IconButtonEvent ce){
				new ColorPicker(ce.getTarget(), ce.getTarget().getAbsoluteLeft(), ce.getTarget().getAbsoluteTop()).build(ColorPickerCaller.GENERIC);
			}
			
		});
		hrFourth.add(colorTitle);
		fieldSet.add(hrFourth);
		
		// adding the style panel
		fieldSet.add(stylePanel.buildVerticalPanel(false, false, false, "Arial"));
		fieldSet.setTitle(BabelFish.print().chartTitle());
		
		
		
		v.add(fieldSet);
		mainCont.add(v);
		
		add(mainCont);
		
	}

	public StylePanel getStylePanel() {
		return stylePanel;
	}
	
	
}