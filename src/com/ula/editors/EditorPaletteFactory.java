


package com.ula.editors;





import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.jface.resource.ImageDescriptor;

import com.ula.figures.StateFigure;
import com.ula.models.State;




public class EditorPaletteFactory {

	private static final ImageDescriptor CIRCULO
	= ImageDescriptor.createFromImage(StateFigure.CIRCULO);

	
	
	private static final ImageDescriptor CONEXION
	= ImageDescriptor.createFromImage(StateFigure.CONNECTION_IMAGE);
	
	private static int i=0;

	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
		palette.add(createElementsDrawer());
		return palette;
	}

	private static PaletteEntry createToolsGroup(PaletteRoot palette) {
		PaletteToolbar toolbar = new PaletteToolbar("Tools");

		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		toolbar.add(tool);
		palette.setDefaultEntry(tool);

		// Add a marquee tool to the group
		toolbar.add(new MarqueeToolEntry());

		return toolbar;
	}

	/**
	 * Create a drawer containing tools to add the various genealogy model elements
	 */
	private static PaletteEntry createElementsDrawer() {
		
		
		PaletteDrawer componentsDrawer = new PaletteDrawer("Tools");

		SimpleFactory factory = new SimpleFactory(State.class) {
			public Object getNewObject() {
				State state = new State();
				
				state.getLabelState().setText("E"+i);
				i++;
				return state;
			}
		};
		CombinedTemplateCreationEntry component = new CombinedTemplateCreationEntry(
				"State",
				"Create State",
				factory, 
				CIRCULO, 
				CIRCULO);
		componentsDrawer.add(component);

				

		ToolEntry connection = new ConnectionCreationToolEntry(
				"Create Connection",
				"Create Connection", 
				null,
				CONEXION,
				CONEXION);
		componentsDrawer.add(connection);

		return componentsDrawer;
	}

}