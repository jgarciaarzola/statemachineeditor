package com.ula.editors;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.SaveAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.LabelRetargetAction;

public class EditorActionBarContributor extends ActionBarContributor{

	public EditorActionBarContributor() {/*Empty*/}
	@Override
	protected void buildActions() {

		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
		addRetargetAction(new LabelRetargetAction(ActionFactory.SELECT_ALL.getId(), "Select All"));
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());
		
		
			
	}
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(new ZoomComboContributionItem(getPage()));
		

	}
	@Override
	protected void declareGlobalActionKeys() {
	}
	
	public void contributeToMenu(IMenuManager menubar) {
	    super.contributeToMenu(menubar);
	    MenuManager viewMenu = new MenuManager("&View");
	    viewMenu.add(getAction(GEFActionConstants.ZOOM_IN));
	    viewMenu.add(getAction(GEFActionConstants.ZOOM_OUT));
	    menubar.insertAfter(IWorkbenchActionConstants.M_EDIT, viewMenu);
	}
	

}
