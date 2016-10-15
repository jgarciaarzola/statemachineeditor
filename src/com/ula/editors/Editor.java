package com.ula.editors;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;

import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;




import com.ula.models.Canvas;
import com.ula.models.ArcShapeBendPoint;
import com.ula.models.ArcShape;
import com.ula.models.Element;
import com.ula.models.LabelBase;
import com.ula.models.State;
import com.ula.parts.EditorEditPartFactory;
import com.ula.xml.Arc;
import com.ula.xml.BendPoints;
import com.ula.xml.EventSM;
import com.ula.xml.Initial;
import com.ula.xml.Source;
import com.ula.xml.StateMachine;
import com.ula.xml.Target;




public class Editor extends GraphicalEditorWithFlyoutPalette {

	private final Canvas canvas = new Canvas();
	

	public Editor() {

		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return EditorPaletteFactory.createPalette();
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		doSave(((IFileEditorInput) getEditorInput()).getFile(), arg0);

	}


	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		final GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new EditorEditPartFactory());
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		// Two different approaches for dynamically modifying the selection
		// to exclude nested figures when that figure's ancestor is selected

		// Option #1
		//viewer.addSelectionChangedListener(new SelectionModificationChangeListener(viewer));

		// Option #2
		//viewer.setSelectionManager(new ModifiedSelectionManager(viewer));

		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));
		List zoomContributions = Arrays.asList(new String[] { 

			     ZoomManager.FIT_ALL, 

			     ZoomManager.FIT_HEIGHT, 

			     ZoomManager.FIT_WIDTH });

			rootEditPart.getZoomManager().setZoomLevelContributions(zoomContributions);
			//Revisar el metodo setZoomLevel			
			IAction zoomIn = new ZoomInAction(rootEditPart.getZoomManager());

			IAction zoomOut = new ZoomOutAction(rootEditPart.getZoomManager());

			getActionRegistry().registerAction(zoomIn);

			getActionRegistry().registerAction(zoomOut);

			getSite().getKeyBindingService().registerAction(zoomIn);			
	

			getSite().getKeyBindingService().registerAction(zoomOut);
			
			viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);


	}

	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getGraphicalViewer().setContents(canvas);
		getGraphicalViewer().addDropTargetListener(
				new TemplateTransferDropTargetListener(getGraphicalViewer()));
	}


	



	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}
	
	protected void setInput(IEditorInput input) {
		canvas.getElements().clear();

		super.setInput(input);
		try {

			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			
			BufferedReader fin = new BufferedReader( //
			        new InputStreamReader(file.getContents(), file.getCharset()));

//			InputStream is = file.getContents();
			setPartName(file.getName());
			String nameCanvas = file.getName();
			int i=0;
			while(nameCanvas.charAt(i)!='.'){
				i++;
			}
			
			canvas.setName(nameCanvas.substring(0, i));
			
			
			JAXBContext jc;
			jc = JAXBContext.newInstance( //
					com.ula.xml.ObjectFactory.class.getPackage().getName());
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			//
			StateMachine xmlStateMachine = (StateMachine) unmarshaller
					.unmarshal(fin);
			toStateMachineDiagram(xmlStateMachine);

//			is.close();
			fin.close();

			setPartName(getEditorInput().getName());
		} catch (Exception e) {

			handleLoadException(e);
		}

	}
	
	
	
	private void toStateMachineDiagram(StateMachine xmlStateMachine) {
		List<State> listState = new ArrayList<State>();

		ArrayList<com.ula.models.Event> events = new ArrayList<com.ula.models.Event>();
		for (EventSM e : xmlStateMachine.getEventSM()) {
			com.ula.models.Event eventAux = new com.ula.models.Event();
			eventAux.setName(e.getName());
			events.add(eventAux);
		}
		canvas.setEvents(events);
		canvas.setName(xmlStateMachine.getName());
		canvas.setPathPackage(xmlStateMachine.getPackage());
		canvas.setTargetPath(xmlStateMachine.getPathJava());
		if(xmlStateMachine.getAlternativeTemplateDirectory()!=null){
			IPath alternativeTemplatePath = new Path(xmlStateMachine.getAlternativeTemplateDirectory());
			canvas.setAlternativeTemplateDirectory(alternativeTemplatePath);
		}

		for (com.ula.xml.State xmlState : xmlStateMachine.getState()) {

			State state = GEFTOXMLUTILS.createState(xmlState);
			canvas.addElement(state);
			canvas.addElement(state.getLabelState());
			listState.add(state);

		}

		for (com.ula.xml.State xmlState : xmlStateMachine.getState()) {

			for (Arc xmlArc : xmlState.getArc()) {
				State sourceState = null;
				State targetState = null;
				for (State s : listState) {
					if (s.getLabelState()
							.getText()
							.equals(xmlArc.getSource().getState().getLabelState()
									.getName())) {
						sourceState = s;
					}
					if (s.getLabelState()
							.getText()
							.equals(xmlArc.getTarget().getState().getLabelState()
									.getName())) {
						targetState = s;
					}
				}

				ArcShape connection = new ArcShape(sourceState,
						targetState);
				connection.setCanvas(canvas);

				int countEvents = 0;
				if (canvas.getEvents().size() > 0) {
					for (com.ula.models.Event e : canvas.getEvents()) {
						if (e.getName().equals(xmlArc.getEventArc().getName())) {
							connection.setEvent(e);
							connection.setComboOption(countEvents + 1);
							connection.getLabelEvent().setLocation(
									xmlArc.getEventArc().getX(),
									xmlArc.getEventArc().getY());
							connection.getLabelEvent().setSize(
									connection.getLabelEvent().getText()
											.length() * 10, 20);
							

							break;
						}
						countEvents++;
					}
				

				}
				if(xmlArc.getBendPoints().size()>0){
					int i=0;
					for(BendPoints xmlBendPoint: xmlArc.getBendPoints()){
						ArcShapeBendPoint bendPoint = new ArcShapeBendPoint();
						Dimension d1 = new Dimension(xmlBendPoint.getD1W(), xmlBendPoint.getD1H());
						Dimension d2 = new Dimension(xmlBendPoint.getD2W(), xmlBendPoint.getD2H());
						bendPoint.setRelativeDimensions(d1, d2);
						connection.insertBendpoint(i, bendPoint);
						i++;
					}
				}

			}

		}

	}

	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}
	
	private void handleLoadException(Exception e) {
		System.err.println("** Load failed. Using default model. **");
//		e.printStackTrace();
		 
	}
	
	private void createOutputStream(OutputStream os) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(canvas);
		oos.close();
	}
	@Override
	public Object getAdapter(Class type) {
		if (type == ZoomManager.class){

		     return getGraphicalViewer().getProperty(ZoomManager.class.toString());
		}
		return super.getAdapter(type);
	}
	
	
	
	public void doSaveAs() {
		
		// Show a SaveAs dialog
		Shell shell = getSite().getWorkbenchWindow().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();
		

		IPath path = dialog.getResult();
		if (path != null) {
			// try to save the editor's contents under a different file name
			final IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(path);
			try {
				new ProgressMonitorDialog(shell).run(false, // don't fork
						false, // not cancelable
						new WorkspaceModifyOperation() { // run this operation
							public void execute(final IProgressMonitor monitor) {

//								 doSave(((IFileEditorInput)
//								 getEditorInput()).getFile(), monitor);

								try {
									ByteArrayOutputStream out = new ByteArrayOutputStream();
									createOutputStream(out);
									file.create(
											new ByteArrayInputStream(out
													.toByteArray()), // contents
											true, // keep saving, even if IFile
											// is out of sync with the
											// Workspace
											monitor); // progress monitor
								} catch (CoreException ce) {
									ce.printStackTrace();
								} catch (IOException ioe) {
									ioe.printStackTrace();
								}
							}
						});
				// set input to the new file
				setInput(new FileEditorInput(file));
				getCommandStack().markSaveLocation();
			} catch (InterruptedException ie) {
				// should not happen, since the monitor dialog is not cancelable
				ie.printStackTrace();
			} catch (InvocationTargetException ite) {
				ite.printStackTrace();
			}
		}
	}
	
	
	private void doSave(IFile file, IProgressMonitor monitor) {
		

		try {
			
//			byte[] filedata = code.getBytes(Charset.forName(file.getCharset()));
//		    ByteArrayInputStream bais = new ByteArrayInputStream(filedata);

		    

			
			
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			StateMachine xmlStateMachine= toXmlStateMachine();

			JAXBContext jc = JAXBContext.newInstance(com.ula.xml.ObjectFactory.class
					.getPackage().getName());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.marshal(xmlStateMachine, baos);
			
			
		

			baos.close();
			if (file.exists()) {
			      file.setContents(new ByteArrayInputStream(baos.toByteArray()), 0, null);
			    } else {
			      file.create(new ByteArrayInputStream(baos.toByteArray()), true, null);
			    }
			    file.setDerived(false);	
			getCommandStack().markSaveLocation();

//			file.setContents(new ByteArrayInputStream(baos.toByteArray()),
//					true, false, monitor);
//			getCommandStack().markSaveLocation();

		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	private StateMachine toXmlStateMachine() {
		StateMachine xmlStateMachine = new StateMachine();
		Arc xmlSourceArc = null;
		int i=0;

		for (Element e : canvas.getElements()) {
			if (e instanceof State) {
				State state = (State) e;
				com.ula.xml.State xmlState = GEFTOXMLUTILS.createXmlState(state
						.getLabelState().getText(), state.getType(), state.getX(),
						state.getY(), state.getW(), state.getH(),state.getLabelState().getX(),state.getLabelState().getY());
				if(state.getType() ==2){
					Initial xmlInitialState = GEFTOXMLUTILS
							.createXmlInitialState(state.getLabelState().getText(),
									state.getType(), state.getX(),
									state.getY(), state.getW(), state.getH());
					xmlStateMachine.setInitial(xmlInitialState);
					i++;
				}
				
				if (state.getSourceConnections().size() != 0) {

					for(ArcShape c: state.getSourceConnections()){
						xmlSourceArc = GEFTOXMLUTILS.saveConnection(c);
						xmlState.getArc().add(xmlSourceArc);
					}
					
					
				}
				xmlStateMachine.getState().add(xmlState);
			}
		}
		for (com.ula.models.Event e : canvas.getEvents()) {
			EventSM xmlEvent = new EventSM();
			xmlEvent.setName(e.getName());
			xmlStateMachine.getEventSM().add(xmlEvent);
		}		
		if(i>1){
			MessageDialog.openError(null, "Warning", "You should specify a single initial state");
		}
		xmlStateMachine.setName(canvas.getName());
		xmlStateMachine.setPackage(canvas.getPathPackage());
		xmlStateMachine.setPathJava(canvas.getTargetPath());
		if(canvas.getAlternativeTemplateDirectory() != null){
			xmlStateMachine.setAlternativeTemplateDirectory(canvas.getAlternativeTemplateDirectory().toString());
		}
		else{
			xmlStateMachine.setAlternativeTemplateDirectory(null);
		}
		if(canvas.getAlternativeNameTemplate()!=null){
			xmlStateMachine.setAlternativeTemplateName(canvas.getAlternativeNameTemplate());
		}
		else{
			xmlStateMachine.setAlternativeTemplateName(null);
		}

		return xmlStateMachine;
	}	
	
	
	
}
