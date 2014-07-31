package org.openflexo.traceanalysis.view.routeview;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.sound.midi.Receiver;
import javax.swing.ImageIcon;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.fge.BackgroundImageBackgroundStyle;
import org.openflexo.fge.BackgroundStyle;
import org.openflexo.fge.ColorGradientBackgroundStyle.ColorGradientDirection;
import org.openflexo.fge.ConnectorGraphicalRepresentation;
import org.openflexo.fge.DrawingGraphicalRepresentation;
import org.openflexo.fge.FGEConstants;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.ForegroundStyle.DashStyle;
import org.openflexo.fge.GRBinding.ConnectorGRBinding;
import org.openflexo.fge.GRBinding.DrawingGRBinding;
import org.openflexo.fge.GRBinding.ShapeGRBinding;
import org.openflexo.fge.GRProvider.ConnectorGRProvider;
import org.openflexo.fge.GRProvider.DrawingGRProvider;
import org.openflexo.fge.GRProvider.ShapeGRProvider;
import org.openflexo.fge.GRStructureVisitor;
import org.openflexo.fge.GraphicalRepresentation;
import org.openflexo.fge.ShapeGraphicalRepresentation;
import org.openflexo.fge.connectors.ConnectorSpecification.ConnectorType;
import org.openflexo.fge.connectors.ConnectorSymbol.EndSymbolType;
import org.openflexo.fge.control.AbstractDianaEditor;
import org.openflexo.fge.control.MouseControl.MouseButton;
import org.openflexo.fge.control.MouseControlContext;
import org.openflexo.fge.control.actions.MouseClickControlActionImpl;
import org.openflexo.fge.control.actions.MouseClickControlImpl;
import org.openflexo.fge.impl.DrawingImpl;
import org.openflexo.fge.shapes.Rectangle;
import org.openflexo.fge.shapes.ShapeSpecification;
import org.openflexo.fge.shapes.ShapeSpecification.ShapeType;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObject;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageReceive;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSend;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;
import org.openflexo.toolbox.ImageIconResource;
import org.openflexo.traceanalysis.view.routeview.OBPConfigurationInRoute.RelativeConfigurationArtefact;
import org.openflexo.traceanalysis.view.routeview.OBPRoute.AbstractTransitionArtefact;

public class RouteDrawing extends DrawingImpl<OBPRoute> {

	public static final double ROW_HEIGHT = 60.0;
	public static final double LIFE_LINE_WIDTH = 150.0;
	public static final double RECTANGLE_STATE_WIDTH = 70.0;
	public static final double RECTANGLE_STATE_HEIGHT = 20.0;
	public static final double CIRCLE_STATE_WIDTH = 20.0;
	public static final double CIRCLE_STATE_HEIGHT = 20.0;
	
	public static final Resource SUCCESS = ResourceLocator.locateResource("Icons/cdl_success.png");
	public static final Resource REJECT = ResourceLocator.locateResource("Icons/cdl_reject.png");
	public static final Resource CUT = ResourceLocator.locateResource("Icons/cdl_cut.png");
	public static final Resource NORMAL = ResourceLocator.locateResource("Icons/cdl_normal.png");
	
	private DrawingGraphicalRepresentation graphRepresentation;
	private ShapeGraphicalRepresentation configurationRepresentation;
	private ConnectorGraphicalRepresentation concreteTransitionRepresentation;
	private ConnectorGraphicalRepresentation abstractTransitionRepresentation;
	private ShapeGraphicalRepresentation intermediateConfigurationRepresentation;
	
	private ShapeGraphicalRepresentation initialStateRepresentation;
	private ShapeGraphicalRepresentation defaultStateRepresentation;
	private ShapeGraphicalRepresentation successStateRepresentation;
	private ShapeGraphicalRepresentation rejectStateRepresentation;
	private ShapeGraphicalRepresentation cutStateRepresentation;
	private ShapeGraphicalRepresentation normalStateRepresentation;
	private ShapeGraphicalRepresentation finalStateRepresentation;

	private ShapeGraphicalRepresentation lifeLineRepresentation;
	private ShapeGraphicalRepresentation messageAnchorRepresentation;
	private ConnectorGraphicalRepresentation messageRepresentation;
	// private ShapeGraphicalRepresentation previousConfigurationRepresentation;

	public RouteDrawing(OBPRoute graph, FGEModelFactory factory) {
		super(graph, factory, PersistenceMode.SharedGraphicalRepresentations);
	}

	@Override
	public void init() {

		graphRepresentation = getFactory().makeDrawingGraphicalRepresentation();

		configurationRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		configurationRepresentation.setWidth(20);
		configurationRepresentation.setHeight(20);
		configurationRepresentation.setAbsoluteTextX(40);
		configurationRepresentation.setAbsoluteTextY(13);
		configurationRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.BOLD)));
		configurationRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		configurationRepresentation.setBackground(getFactory().makeColorGradientBackground(FGEConstants.DEFAULT_BACKGROUND_COLOR,
				Color.white, ColorGradientDirection.SOUTH_EAST_NORTH_WEST));
		configurationRepresentation.setForeground(getFactory().makeForegroundStyle(Color.ORANGE));
		// configurationRepresentation.setHasFocusedBackground(true);
		// configurationRepresentation.setFocusedBackground(getFactory().makeColorGradientBackground(Color.orange, Color.white,
		// ColorGradientDirection.SOUTH_EAST_NORTH_WEST));
		// configurationRepresentation.setDrawControlPointsWhenFocused(false);
		// configurationRepresentation.setHasSelectedBackground(true);
		// configurationRepresentation.setSelectedBackground(getFactory().makeColorGradientBackground(Color.orange, Color.white,
		// ColorGradientDirection.SOUTH_EAST_NORTH_WEST));
		configurationRepresentation.addToMouseClickControls(new MouseClickControlImpl<AbstractDianaEditor<?, ?, ?>>(
				"toogleVisibleStateForConfiguration", MouseButton.LEFT, 1, new MouseClickControlActionImpl<AbstractDianaEditor<?, ?, ?>>() {
					@Override
					public boolean handleClick(DrawingTreeNode<?, ?> dtn, AbstractDianaEditor<?, ?, ?> controller,
							MouseControlContext context) {
						/*FGEView view = controller.getDrawingView().viewForNode(dtn);
						Point newPoint = getPointInView(dtn, controller, context);
						controller.showContextualMenu(dtn, view, newPoint);*/
						System.out.println("toogleVisibleStateForConfiguration for " + dtn.getDrawable());
						if (dtn.getDrawable() instanceof OBPConfigurationInRoute) {
							OBPConfigurationInRoute configInRoute = (OBPConfigurationInRoute) dtn.getDrawable();
							configInRoute.getRoute().hide(configInRoute.getConfiguration());
						}
						return true;
					}
				}, false, false, false, false, null));

		intermediateConfigurationRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		intermediateConfigurationRepresentation.setWidth(10);
		intermediateConfigurationRepresentation.setHeight(10);
		intermediateConfigurationRepresentation.setAbsoluteTextX(35);
		intermediateConfigurationRepresentation.setAbsoluteTextY(8);
		intermediateConfigurationRepresentation.setTextStyle(getFactory().makeTextStyle(Color.LIGHT_GRAY,
				FGEConstants.DEFAULT_SMALL_TEXT_FONT.deriveFont(Font.ITALIC)));
		intermediateConfigurationRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		intermediateConfigurationRepresentation.setBackground(getFactory().makeColorGradientBackground(Color.LIGHT_GRAY, Color.white,
				ColorGradientDirection.SOUTH_EAST_NORTH_WEST));
		intermediateConfigurationRepresentation.setForeground(getFactory().makeForegroundStyle(Color.ORANGE));
		intermediateConfigurationRepresentation.addToMouseClickControls(new MouseClickControlImpl<AbstractDianaEditor<?, ?, ?>>(
				"toogleVisibleStateForIntermediateConfiguration", MouseButton.LEFT, 1,
				new MouseClickControlActionImpl<AbstractDianaEditor<?, ?, ?>>() {
					@Override
					public boolean handleClick(DrawingTreeNode<?, ?> dtn, AbstractDianaEditor<?, ?, ?> controller,
							MouseControlContext context) {
						/*FGEView view = controller.getDrawingView().viewForNode(dtn);
						Point newPoint = getPointInView(dtn, controller, context);
						controller.showContextualMenu(dtn, view, newPoint);*/
						System.out.println("toogleVisibleStateForIntermediateConfiguration for " + dtn.getDrawable());
						if (dtn.getDrawable() instanceof RelativeConfigurationArtefact) {
							RelativeConfigurationArtefact artefact = (RelativeConfigurationArtefact) dtn.getDrawable();
							artefact.getConfigurationInRoute().getRoute().show(artefact.getConfiguration());
							// invalidateGraphicalObjectsHierarchy();
						}
						return true;
					}
				}, false, false, false, false, null));

		
		ShapeSpecification defaultStateSpecification = getFactory().makeShape(ShapeType.RECTANGLE);
		((Rectangle)defaultStateSpecification).setIsRounded(true);
		((Rectangle)defaultStateSpecification).setArcSize(10);
		defaultStateRepresentation = getFactory().makeShapeGraphicalRepresentation(defaultStateSpecification);
		defaultStateRepresentation.setWidth(RECTANGLE_STATE_WIDTH);
		defaultStateRepresentation.setHeight(RECTANGLE_STATE_HEIGHT);
		defaultStateRepresentation.setAbsoluteTextX(RECTANGLE_STATE_WIDTH/2);
		defaultStateRepresentation.setAbsoluteTextY(RECTANGLE_STATE_HEIGHT/2);
		defaultStateRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		defaultStateRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		defaultStateRepresentation.setBackground(getFactory().makeColoredBackground(Color.white));
		defaultStateRepresentation.setForeground(getFactory().makeForegroundStyle(Color.LIGHT_GRAY));
		
		ShapeSpecification initialStateSpecification = getFactory().makeShape(ShapeType.CIRCLE);
		initialStateRepresentation = getFactory().makeShapeGraphicalRepresentation(initialStateSpecification);
		initialStateRepresentation.setWidth(CIRCLE_STATE_WIDTH);
		initialStateRepresentation.setHeight(CIRCLE_STATE_HEIGHT);
		initialStateRepresentation.setAbsoluteTextX(0);
		initialStateRepresentation.setAbsoluteTextY(-10);
		initialStateRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		initialStateRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		initialStateRepresentation.setBackground(getFactory().makeColoredBackground(Color.BLACK));
		initialStateRepresentation.setForeground(getFactory().makeForegroundStyle(Color.BLACK));
		
		ShapeSpecification successStateSpecification = getFactory().makeShape(ShapeType.CIRCLE);
		successStateRepresentation = getFactory().makeShapeGraphicalRepresentation(successStateSpecification);
		successStateRepresentation.setWidth(CIRCLE_STATE_WIDTH);
		successStateRepresentation.setHeight(CIRCLE_STATE_HEIGHT);
		successStateRepresentation.setAbsoluteTextX(-5);
		successStateRepresentation.setAbsoluteTextY(-10);
		successStateRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		successStateRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		successStateRepresentation.setBackground(getFactory().makeImageBackground(SUCCESS));
		successStateRepresentation.setForeground(getFactory().makeForegroundStyle(Color.BLACK));
		((BackgroundImageBackgroundStyle) successStateRepresentation.getBackground()).setFitToShape(true);
		
		
		ShapeSpecification rejectStateSpecification = getFactory().makeShape(ShapeType.CIRCLE);
		rejectStateRepresentation = getFactory().makeShapeGraphicalRepresentation(rejectStateSpecification);
		rejectStateRepresentation.setWidth(CIRCLE_STATE_WIDTH);
		rejectStateRepresentation.setHeight(CIRCLE_STATE_HEIGHT);
		rejectStateRepresentation.setAbsoluteTextX(-5);
		rejectStateRepresentation.setAbsoluteTextY(-10);
		rejectStateRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		rejectStateRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		rejectStateRepresentation.setBackground(getFactory().makeImageBackground(REJECT));
		rejectStateRepresentation.setForeground(getFactory().makeForegroundStyle(Color.BLACK));
		((BackgroundImageBackgroundStyle) rejectStateRepresentation.getBackground()).setFitToShape(true);
		
		ShapeSpecification cutStateSpecification = getFactory().makeShape(ShapeType.CIRCLE);
		cutStateRepresentation = getFactory().makeShapeGraphicalRepresentation(cutStateSpecification);
		cutStateRepresentation.setWidth(CIRCLE_STATE_WIDTH);
		cutStateRepresentation.setHeight(CIRCLE_STATE_HEIGHT);
		cutStateRepresentation.setAbsoluteTextX(5);
		cutStateRepresentation.setAbsoluteTextY(-10);
		cutStateRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		cutStateRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		cutStateRepresentation.setBackground(getFactory().makeImageBackground(CUT));
		cutStateRepresentation.setForeground(getFactory().makeForegroundStyle(Color.BLACK));
		((BackgroundImageBackgroundStyle) cutStateRepresentation.getBackground()).setFitToShape(true);
		
		ShapeSpecification normalStateSpecification = getFactory().makeShape(ShapeType.CIRCLE);
		normalStateRepresentation = getFactory().makeShapeGraphicalRepresentation(normalStateSpecification);
		normalStateRepresentation.setWidth(CIRCLE_STATE_WIDTH);
		normalStateRepresentation.setHeight(CIRCLE_STATE_HEIGHT);
		normalStateRepresentation.setAbsoluteTextX(-5);
		normalStateRepresentation.setAbsoluteTextY(-10);
		normalStateRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		normalStateRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		normalStateRepresentation.setBackground(getFactory().makeImageBackground(NORMAL));
		normalStateRepresentation.setForeground(getFactory().makeForegroundStyle(Color.BLACK));
		((BackgroundImageBackgroundStyle) normalStateRepresentation.getBackground()).setFitToShape(true);
		
		concreteTransitionRepresentation = getFactory().makeConnectorGraphicalRepresentation(ConnectorType.LINE);
		concreteTransitionRepresentation.setForeground(getFactory().makeForegroundStyle(Color.GRAY, 0.5f));

		abstractTransitionRepresentation = getFactory().makeConnectorGraphicalRepresentation(ConnectorType.LINE);
		abstractTransitionRepresentation.setForeground(getFactory().makeForegroundStyle(Color.DARK_GRAY, 0.5f, DashStyle.DOTS_DASHES));

		lifeLineRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
		lifeLineRepresentation.setWidth(0);
		lifeLineRepresentation.setHeight(10);
		lifeLineRepresentation.setAbsoluteTextX(0);
		lifeLineRepresentation.setAbsoluteTextY(-20);
		lifeLineRepresentation.setTextStyle(getFactory().makeTextStyle(Color.DARK_GRAY,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.BOLD)));
		lifeLineRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		lifeLineRepresentation.setBackground(getFactory().makeEmptyBackground());
		lifeLineRepresentation.setForeground(getFactory().makeForegroundStyle(Color.LIGHT_GRAY, 3f));
		lifeLineRepresentation.getForeground().setUseTransparency(true);
		lifeLineRepresentation.getForeground().setTransparencyLevel(0.5f);
		lifeLineRepresentation.setIsFocusable(false);

		messageAnchorRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
		messageAnchorRepresentation.setWidth(0);
		messageAnchorRepresentation.setHeight(0);
		messageAnchorRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		messageAnchorRepresentation.setBackground(getFactory().makeEmptyBackground());
		messageAnchorRepresentation.setForeground(getFactory().makeNoneForegroundStyle());
		messageAnchorRepresentation.setIsFocusable(false);
		// messageAnchorRepresentation.setLayer(10);

		messageRepresentation = getFactory().makeConnectorGraphicalRepresentation(ConnectorType.LINE);
		messageRepresentation.setForeground(getFactory().makeForegroundStyle(Color.GRAY, 1.5f));
		messageRepresentation.getConnectorSpecification().setEndSymbol(EndSymbolType.ARROW);
		messageRepresentation.getConnectorSpecification().setEndSymbolSize(10.0);
		// messageRepresentation.setLayer(11);
		
		final DrawingGRBinding<OBPRoute> graphBinding = bindDrawing(OBPRoute.class, "route", new DrawingGRProvider<OBPRoute>() {
			@Override
			public DrawingGraphicalRepresentation provideGR(OBPRoute drawable, FGEModelFactory factory) {
				return graphRepresentation;
			}
		});
		final ShapeGRBinding<OBPConfigurationInRoute> configurationBinding = bindShape(OBPConfigurationInRoute.class, "configuration",
				new ShapeGRProvider<OBPConfigurationInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPConfigurationInRoute drawable, FGEModelFactory factory) {
						return configurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> nextConfigurationBinding = bindShape(RelativeConfigurationArtefact.class,
				"nextConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> previousConfigurationBinding = bindShape(RelativeConfigurationArtefact.class,
				"previousConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> intermediateConfigurationBinding = bindShape(
				RelativeConfigurationArtefact.class, "intermediateConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> defaultStateBinding = bindShape(
				OBPStateInRoute.class, "defaultState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return defaultStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> initialStateBinding = bindShape(
				OBPStateInRoute.class, "initialState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return initialStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> successStateBinding = bindShape(
				OBPStateInRoute.class, "successState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return successStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> rejectStateBinding = bindShape(
				OBPStateInRoute.class, "rejectState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return rejectStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> cutStateBinding = bindShape(
				OBPStateInRoute.class, "cutState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return cutStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> normalStateBinding = bindShape(
				OBPStateInRoute.class, "normalState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return normalStateRepresentation;
					}
				});
		final ConnectorGRBinding<OBPTraceTransition> concreteTransitionBinding = bindConnector(OBPTraceTransition.class, "concreteTransition",
				configurationBinding, configurationBinding, graphBinding, new ConnectorGRProvider<OBPTraceTransition>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(OBPTraceTransition drawable, FGEModelFactory factory) {
						return concreteTransitionRepresentation;
					}
				});
		final ConnectorGRBinding<AbstractTransitionArtefact> abstractTransitionBinding = bindConnector(AbstractTransitionArtefact.class,
				"abstractTransition", configurationBinding, configurationBinding, graphBinding,
				new ConnectorGRProvider<AbstractTransitionArtefact>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(AbstractTransitionArtefact drawable, FGEModelFactory factory) {
						return abstractTransitionRepresentation;
					}
				});

		final ShapeGRBinding<BehaviourObjectInRoute> lifeLineBinding = bindShape(BehaviourObjectInRoute.class, "lifeLine",
				new ShapeGRProvider<BehaviourObjectInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(BehaviourObjectInRoute drawable, FGEModelFactory factory) {
						return lifeLineRepresentation;
					}
				});

		final ShapeGRBinding<MessageInRoute> messageStartAnchorBinding = bindShape(MessageInRoute.class, "messageStartAnchor",
				new ShapeGRProvider<MessageInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageAnchorRepresentation;
					}
				});

		final ShapeGRBinding<MessageInRoute> messageEndAnchorBinding = bindShape(MessageInRoute.class, "messageEndAnchor",
				new ShapeGRProvider<MessageInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageAnchorRepresentation;
					}
				});
		final ConnectorGRBinding<MessageInRoute> messageBinding = bindConnector(MessageInRoute.class, "message", messageStartAnchorBinding,
				messageEndAnchorBinding, graphBinding, new ConnectorGRProvider<MessageInRoute>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageRepresentation;
					}
				});

		graphBinding.addToWalkers(new GRStructureVisitor<OBPRoute>() {

			@Override
			public void visit(OBPRoute route) {
				for (BehaviourObjectInRoute compInRoute : route.getVisibleBehaviourObjects()) {
					drawShape(lifeLineBinding, compInRoute);
				}
				for (int i = 0; i < route.getSize(); i++) {
					OBPConfigurationInRoute config = route.getConfiguration(i);
					drawShape(configurationBinding, config);
					if (i > 0) {
						OBPConfigurationInRoute pConfig = route.getConfiguration(i - 1);
						OBPTraceTransition nextTransition = route.getTrace().getTransition(pConfig.getConfiguration(),
								pConfig.getNextConfigurationArtefact().getConfiguration());
						OBPTraceTransition previousTransition = route.getTrace().getTransition(
								config.getPreviousConfigurationArtefact().getConfiguration(), config.getConfiguration());
						if (pConfig.nextTransitionIsAbstract()) {
							if (pConfig.getNextConfigurationArtefact().getConfiguration() == config.getPreviousConfigurationArtefact()
									.getConfiguration()) {
								// In this case, we have a unique configuration, we won't represent separately the previous and the next
								drawShape(intermediateConfigurationBinding, pConfig.getNextConfigurationArtefact());
								drawConnector(concreteTransitionBinding, nextTransition, pConfig, pConfig.getNextConfigurationArtefact(),
										route);
								drawConnector(concreteTransitionBinding, previousTransition, pConfig.getNextConfigurationArtefact(),
										config, route);
							} else {
								// Otherwise, we represent the next and previous configuration of next visible
								AbstractTransitionArtefact abstractTransition = route.getAbstractTransition(pConfig, config);
								drawShape(nextConfigurationBinding, pConfig.getNextConfigurationArtefact());
								drawShape(previousConfigurationBinding, config.getPreviousConfigurationArtefact());
								drawConnector(abstractTransitionBinding, abstractTransition, pConfig.getNextConfigurationArtefact(),
										config.getPreviousConfigurationArtefact(), route);
								drawConnector(concreteTransitionBinding, nextTransition, pConfig, pConfig.getNextConfigurationArtefact(),
										route);
								drawConnector(concreteTransitionBinding, previousTransition, config.getPreviousConfigurationArtefact(),
										config, route);
							}
						} else {
							OBPTraceTransition transition = route.getTrace()
									.getTransition(pConfig.getConfiguration(), config.getConfiguration());
							drawConnector(concreteTransitionBinding, transition, pConfig, config, route);

							for (OBPTraceMessage m : transition.getOBPTraceMessages()) {
								
								if(m instanceof OBPTraceMessageSend){
									// Show an asynchronous message only if all configuration between send/receive are visible
									boolean showMessage = true;
									OBPTraceMessageSend sendMessage = (OBPTraceMessageSend)m;
									
									for(OBPConfigurationInRoute configuration : route.getAsyncMessageConfigurations(sendMessage, sendMessage.getOBPTraceMessageReceive())){
										if(!route.isVisible(configuration.getConfiguration())){
											showMessage = false;
										}
									}
									
									if(showMessage){
										drawShape(messageStartAnchorBinding, route.getMessageInRoute(sendMessage));
										drawShape(messageEndAnchorBinding, route.getMessageInRoute(sendMessage.getOBPTraceMessageReceive()));
										drawConnector(messageBinding, route.getMessageInRoute(sendMessage), messageStartAnchorBinding,
											route.getMessageInRoute(sendMessage), messageEndAnchorBinding, route.getMessageInRoute(sendMessage.getOBPTraceMessageReceive()), graphBinding,route);
									}
									
								} 
								// A receive cannot happen without a send(the inverse can happen), so only process send messages
								else if(m instanceof OBPTraceMessageReceive){
									// Do nothing
								}
								else{	
									drawShape(messageStartAnchorBinding, route.getMessageInRoute(m));
									drawShape(messageEndAnchorBinding, route.getMessageInRoute(m));
									drawConnector(messageBinding, route.getMessageInRoute(m), messageStartAnchorBinding,
										route.getMessageInRoute(m), messageEndAnchorBinding, route.getMessageInRoute(m), graphBinding,route);
								}
								
							}

						}
						// Show states for visible BehaviourObjects
						for (BehaviourObjectInRoute objectInRoute : route.getVisibleBehaviourObjects()) {
							OBPStateInRoute state = route.getOBPStateInRoute(objectInRoute, pConfig);
							if(state.isSuccess()){
								drawShape(successStateBinding, route.getOBPStateInRoute(objectInRoute, pConfig));
							}else if(state.isReject()){
								drawShape(rejectStateBinding, route.getOBPStateInRoute(objectInRoute, pConfig));
							}else if(state.isCut()){
								drawShape(cutStateBinding, route.getOBPStateInRoute(objectInRoute, pConfig));
							}else if(state.isNormal()){
								drawShape(normalStateBinding, route.getOBPStateInRoute(objectInRoute, pConfig));
							}else if(pConfig.getVisibleIndex()==0){
								drawShape(initialStateBinding, route.getOBPStateInRoute(objectInRoute, pConfig));
							}else{
								drawShape(defaultStateBinding, route.getOBPStateInRoute(objectInRoute, pConfig));
							}
						}
					}
				}
			}
		});

		configurationBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.configuration.name"),
				false);//
		// Configuration
		// '+drawable.identifier"),
		// true);
		// nodeBinding.setDynamicPropertyValue(GraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("drawable.labelX"));
		// nodeBinding.setDynamicPropertyValue(GraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("drawable.labelY"));
		configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("10.0"), false);
		configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.visibleIndex*"
				+ ROW_HEIGHT + "+50.0"), false);
		
		initialStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.state.value"), false);
		initialStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+LIFE_LINE_WIDTH+"+"+CIRCLE_STATE_WIDTH+"+90.0"), false);
		initialStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"
				+ ROW_HEIGHT + "+50.0"), false);
		
		defaultStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.state.value"), false);
		defaultStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+LIFE_LINE_WIDTH+"+"+RECTANGLE_STATE_WIDTH+"+15.0"), false);
		defaultStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"
				+ ROW_HEIGHT + "+50.0"), false);
		
		successStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.state.value"), false);
		successStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+LIFE_LINE_WIDTH+"+"+CIRCLE_STATE_WIDTH+"+90.0"), false);
		successStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"
				+ ROW_HEIGHT + "+50.0"), false);
		
		rejectStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.state.value"), false);
		rejectStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+LIFE_LINE_WIDTH+"+"+CIRCLE_STATE_WIDTH+"+90.0"), false);
		rejectStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"
				+ ROW_HEIGHT + "+50.0"), false);
		
		cutStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.state.value"), false);
		cutStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+LIFE_LINE_WIDTH+"+"+CIRCLE_STATE_WIDTH+"+90.0"), false);
		cutStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"
				+ ROW_HEIGHT + "+50.0"), false);
		
		normalStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.state.value"), false);
		normalStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+LIFE_LINE_WIDTH+"+"+CIRCLE_STATE_WIDTH+"+90.0"), false);
		normalStateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"
				+ ROW_HEIGHT + "+50.0"), false);
		
		//stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.BACKGROUND, new DataBinding<BackgroundStyle>("drawable.stateBackgroundStyle"), false);
		
		nextConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.configuration.name"), false);
		nextConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("15.0"), false);
		nextConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
				"drawable.configurationInRoute.visibleIndex*" + ROW_HEIGHT + "+75.0"), false);

		previousConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.configuration.name"), false);
		previousConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("15.0"), false);
		previousConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
				"drawable.configurationInRoute.visibleIndex*" + ROW_HEIGHT + "+35.0"), false);

		intermediateConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>(
				"drawable.configuration.name"), false);
		intermediateConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("15.0"), false);
		intermediateConfigurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
				"drawable.configurationInRoute.visibleIndex*" + ROW_HEIGHT + "+85.0"), false);

		lifeLineBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.behaviourObject.name"),
				false);
		lifeLineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.index*" + LIFE_LINE_WIDTH
				+ "+120.0"), false);
		lifeLineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("50.0"), false);
		lifeLineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.HEIGHT, new DataBinding<Double>("(drawable.route.size-1)*"
				+ ROW_HEIGHT + "+20.0"), false);

		messageStartAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
				"drawable.startBehaviourObject.index*" + LIFE_LINE_WIDTH + "+120.0"), false);
		messageStartAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
				"(drawable.startConfiguration.visibleIndex+1)*" + ROW_HEIGHT + "+10.0"), false);

		messageEndAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
				"drawable.endBehaviourObject.index*" + LIFE_LINE_WIDTH + "+120.0"), false);
		messageEndAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
				"(drawable.endConfiguration.visibleIndex+1)*" + ROW_HEIGHT + "-10.0"), false);
		messageBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.message.informationLabel"),
				false);

	}
}
