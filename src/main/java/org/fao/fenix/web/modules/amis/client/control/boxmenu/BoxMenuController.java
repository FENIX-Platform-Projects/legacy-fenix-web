package org.fao.fenix.web.modules.amis.client.control.boxmenu;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Window;

public class BoxMenuController {


	public static SelectionListener<IconButtonEvent> showInfo(final String text) {
		return new SelectionListener<IconButtonEvent>() {
			public void componentSelected(IconButtonEvent ce) {
				Html html = new Html();
				if ( text != null )
					html.setHtml(text);
				else
					html.setHtml("No additional Info");

				ContentPanel panel = new ContentPanel();
				panel.setHeaderVisible(false);
				panel.setHeight(250);
				panel.setWidth(450);
				panel.setScrollMode(Scroll.AUTO);

				panel.add(html);


				Window window = new Window();
				window.setHeight(280);
				window.setWidth(465);


				window.add(panel);
				window.setHeaderVisible(true);
				window.setHeading("Info");

				window.show();
//				window.setScrollMode(Scroll.AUTO);
			}
		};
	}

}