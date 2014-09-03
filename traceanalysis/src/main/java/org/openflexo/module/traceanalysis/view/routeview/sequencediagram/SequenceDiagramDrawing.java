package org.openflexo.module.traceanalysis.view.routeview.sequencediagram;

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
import org.openflexo.model.annotations.Getter.GetterImpl;
import org.openflexo.module.traceanalysis.view.routeview.BehaviourObjectInRoute;
import org.openflexo.module.traceanalysis.view.routeview.MessageInRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPConfigurationInRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPStateInRoute;
import org.openflexo.module.traceanalysis.view.routeview.OBPConfigurationInRoute.RelativeConfigurationArtefact;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute.AbstractTransitionArtefact;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.trace.model.OBPTraceBehaviourObjectInstance;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessage;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageReceive;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSend;
import org.openflexo.technologyadapter.trace.model.OBPTraceMessageSynchro;
import org.openflexo.technologyadapter.trace.model.OBPTraceState;
import org.openflexo.technologyadapter.trace.model.OBPTraceTransition;
import org.openflexo.toolbox.ImageIconResource;

public class SequenceDiagramDrawing extends DrawingImpl<OBPRoute> {

	public static final double ROW_HEIGHT = 60.0;
	public static final double LIFE_LINE_WIDTH = 150.0;
	public static final double LIFE_LINE_HEIGHT = 60.0;
	public static final double RECTANGLE_STATE_WIDTH = 50.0;
	public static final double RECTANGLE_STATE_HEIGHT = 20.0;
	public static final double CIRCLE_STATE_WIDTH = 20.0;
	public static final double CIRCLE_STATE_HEIGHT = 20.0;
	public static final double LEFT_OFFSET = 70.0;
	public static final double UP_OFFSET = 50.0;
	public static final double PREVIOUS_CONFIGURATION_OFFSET = 35.0;
	public static final double NEXT_CONFIGURATION_OFFSET = 75.0;
	public static final double INTERMEDIATE_CONFIGURATION_OFFSET = 85.0;
	
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
	
	public enum RouteLayout {
			HORIZONTAL, VERTICAL
	}

	private RouteLayout layout;
	
	public SequenceDiagramDrawing(OBPRoute graph, FGEModelFactory factory, RouteLayout layout) {
		super(graph, factory, PersistenceMode.SharedGraphicalRepresentations);
		this.layout=layout;
	}

	public RouteLayout getLayout() {
		return layout;
	}

	public void setLayout(RouteLayout layout) {
		this.layout = layout;
		invalidateGraphicalObjectsHierarchy(getModel());
		updateGraphicalObjectsHierarchy(getModel());
		//invalidateGraphicalObjectsHierarchy(getModel());
	}
	
	@Override
	public void init() {

		graphRepresentation = getFactory().makeDrawingGraphicalRepresentation();
		graphRepresentation.setHeight(500);
		
		configurationRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		configurationRepresentation.setWidth(20);
		configurationRepresentation.setHeight(20);
		/*configurationRepresentation.setAbsoluteTextX(40);
		configurationRepresentation.setAbsoluteTextY(13);*/
		configurationRepresentation.setTextStyle(getFactory().makeTextStyle(Color.BLACK,
				FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.BOLD)));
		configurationRepresentation.setShadowStyle(getFactory().makeNoneShadowStyle());
		configurationRepresentation.setBackground(getFactory().makeColorGradientBackground(FGEConstants.DEFAULT_BACKGROUND_COLOR,
				Color.white, ColorGradientDirection.SOUTH_EAST_NORTH_WEST));
		configurationRepresentation.setForeground(getFactory().makeForegroundStyle(Color.ORANGE));
		configurationRepresentation.addToMouseClickControls(new MouseClickControlImpl<AbstractDianaEditor<?, ?, ?>>(
				"toogleVisibleStateForConfiguration", MouseButton.LEFT, 2, new MouseClickControlActionImpl<AbstractDianaEditor<?, ?, ?>>() {
					@Override
					public boolean handleClick(DrawingTreeNode<?, ?> dtn, AbstractDianaEditor<?, ?, ?> controller,
							MouseControlContext context) {
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
		defaultStateRepresentation.setLayer(1);
		
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
		initialStateRepresentation.setLayer(1);
		
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
		successStateRepresentation.setLayer(1);
		
		
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
		rejectStateRepresentation.setLayer(1);
		
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
		cutStateRepresentation.setLayer(1);
		
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
		normalStateRepresentation.setLayer(1);
		
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
		lifeLineRepresentation.setLayer(0);

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
		
		// VERTICAL BINDINGS
		final DrawingGRBinding<OBPRoute> graphBinding = bindDrawing(OBPRoute.class, "route", new DrawingGRProvider<OBPRoute>() {
			@Override
			public DrawingGraphicalRepresentation provideGR(OBPRoute drawable, FGEModelFactory factory) {
				return graphRepresentation;
			}
		});
		final ShapeGRBinding<OBPConfigurationInRoute> configurationVerticalBinding = bindShape(OBPConfigurationInRoute.class, "configuration",
				new ShapeGRProvider<OBPConfigurationInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPConfigurationInRoute drawable, FGEModelFactory factory) {
						return configurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> nextConfigurationVerticalBinding = bindShape(RelativeConfigurationArtefact.class,
				"nextConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> previousConfigurationVerticalBinding = bindShape(RelativeConfigurationArtefact.class,
				"previousConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> intermediateConfigurationVerticalBinding = bindShape(
				RelativeConfigurationArtefact.class, "intermediateConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> defaultStateVerticalBinding = bindShape(
				OBPStateInRoute.class, "defaultState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return defaultStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> initialStateVerticalBinding = bindShape(
				OBPStateInRoute.class, "initialState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return initialStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> successStateVerticalBinding = bindShape(
				OBPStateInRoute.class, "successState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return successStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> rejectStateVerticalBinding = bindShape(
				OBPStateInRoute.class, "rejectState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return rejectStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> cutStateVerticalBinding = bindShape(
				OBPStateInRoute.class, "cutState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return cutStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> normalStateVerticalBinding = bindShape(
				OBPStateInRoute.class, "normalState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return normalStateRepresentation;
					}
				});
		final ConnectorGRBinding<OBPTraceTransition> concreteTransitionVerticalBinding = bindConnector(OBPTraceTransition.class, "concreteTransition",
				configurationVerticalBinding, configurationVerticalBinding, graphBinding, new ConnectorGRProvider<OBPTraceTransition>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(OBPTraceTransition drawable, FGEModelFactory factory) {
						return concreteTransitionRepresentation;
					}
				});
		final ConnectorGRBinding<AbstractTransitionArtefact> abstractTransitionVerticalBinding = bindConnector(AbstractTransitionArtefact.class,
				"abstractTransition", configurationVerticalBinding, configurationVerticalBinding, graphBinding,
				new ConnectorGRProvider<AbstractTransitionArtefact>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(AbstractTransitionArtefact drawable, FGEModelFactory factory) {
						return abstractTransitionRepresentation;
					}
				});

		final ShapeGRBinding<BehaviourObjectInRoute> lifeLineVerticalBinding = bindShape(BehaviourObjectInRoute.class, "lifeLine",
				new ShapeGRProvider<BehaviourObjectInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(BehaviourObjectInRoute drawable, FGEModelFactory factory) {
						return lifeLineRepresentation;
					}
				});

		final ShapeGRBinding<MessageInRoute> messageStartAnchorVerticalBinding = bindShape(MessageInRoute.class, "messageStartAnchor",
				new ShapeGRProvider<MessageInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageAnchorRepresentation;
					}
				});

		final ShapeGRBinding<MessageInRoute> messageEndAnchorVerticalBinding = bindShape(MessageInRoute.class, "messageEndAnchor",
				new ShapeGRProvider<MessageInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageAnchorRepresentation;
					}
				});
		final ConnectorGRBinding<MessageInRoute> messageVerticalBinding = bindConnector(MessageInRoute.class, "message", messageStartAnchorVerticalBinding,
				messageEndAnchorVerticalBinding, graphBinding, new ConnectorGRProvider<MessageInRoute>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageRepresentation;
					}
				});
		
		// HORIZONTAL BINDINGS
		final ShapeGRBinding<OBPConfigurationInRoute> configurationHorizontalBinding = bindShape(OBPConfigurationInRoute.class, "configuration",
				new ShapeGRProvider<OBPConfigurationInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPConfigurationInRoute drawable, FGEModelFactory factory) {
						return configurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> nextConfigurationHorizontalBinding = bindShape(RelativeConfigurationArtefact.class,
				"nextConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> previousConfigurationHorizontalBinding = bindShape(RelativeConfigurationArtefact.class,
				"previousConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<RelativeConfigurationArtefact> intermediateConfigurationHorizontalBinding = bindShape(
				RelativeConfigurationArtefact.class, "intermediateConfiguration", new ShapeGRProvider<RelativeConfigurationArtefact>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(RelativeConfigurationArtefact drawable, FGEModelFactory factory) {
						return intermediateConfigurationRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> defaultStateHorizontalBinding = bindShape(
				OBPStateInRoute.class, "defaultState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return defaultStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> initialStateHorizontalBinding = bindShape(
				OBPStateInRoute.class, "initialState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return initialStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> successStateHorizontalBinding = bindShape(
				OBPStateInRoute.class, "successState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return successStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> rejectStateHorizontalBinding = bindShape(
				OBPStateInRoute.class, "rejectState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return rejectStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> cutStateHorizontalBinding = bindShape(
				OBPStateInRoute.class, "cutState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return cutStateRepresentation;
					}
				});
		final ShapeGRBinding<OBPStateInRoute> normalStateHorizontalBinding = bindShape(
				OBPStateInRoute.class, "normalState", new ShapeGRProvider<OBPStateInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(OBPStateInRoute drawable, FGEModelFactory factory) {
						return normalStateRepresentation;
					}
				});
		final ConnectorGRBinding<OBPTraceTransition> concreteTransitionHorizontalBinding = bindConnector(OBPTraceTransition.class, "concreteTransition",
				configurationHorizontalBinding, configurationHorizontalBinding, graphBinding, new ConnectorGRProvider<OBPTraceTransition>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(OBPTraceTransition drawable, FGEModelFactory factory) {
						return concreteTransitionRepresentation;
					}
				});
		final ConnectorGRBinding<AbstractTransitionArtefact> abstractTransitionHorizontalBinding = bindConnector(AbstractTransitionArtefact.class,
				"abstractTransition", configurationHorizontalBinding, configurationHorizontalBinding, graphBinding,
				new ConnectorGRProvider<AbstractTransitionArtefact>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(AbstractTransitionArtefact drawable, FGEModelFactory factory) {
						return abstractTransitionRepresentation;
					}
				});

		final ShapeGRBinding<BehaviourObjectInRoute> lifeLineHorizontalBinding = bindShape(BehaviourObjectInRoute.class, "lifeLine",
				new ShapeGRProvider<BehaviourObjectInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(BehaviourObjectInRoute drawable, FGEModelFactory factory) {
						return lifeLineRepresentation;
					}
				});

		final ShapeGRBinding<MessageInRoute> messageStartAnchorHorizontalBinding = bindShape(MessageInRoute.class, "messageStartAnchor",
				new ShapeGRProvider<MessageInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageAnchorRepresentation;
					}
				});

		final ShapeGRBinding<MessageInRoute> messageEndAnchorHorizontalBinding = bindShape(MessageInRoute.class, "messageEndAnchor",
				new ShapeGRProvider<MessageInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageAnchorRepresentation;
					}
				});
		final ConnectorGRBinding<MessageInRoute> messageHorizontalBinding = bindConnector(MessageInRoute.class, "message", messageStartAnchorHorizontalBinding,
				messageEndAnchorHorizontalBinding, graphBinding, new ConnectorGRProvider<MessageInRoute>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(MessageInRoute drawable, FGEModelFactory factory) {
						return messageRepresentation;
					}
				});

		graphBinding.addToWalkers(new GRStructureVisitor<OBPRoute>() {

			@Override
			public void visit(OBPRoute route) {
				route.synchronizeWithMask();
				if(layout==RouteLayout.VERTICAL){
					for (BehaviourObjectInRoute compInRoute : route.getVisibleBehaviourObjects()) {
						drawShape(lifeLineVerticalBinding, compInRoute);
					}
					for (int i = 0; i < route.getSize(); i++) {
						OBPConfigurationInRoute config = route.getConfiguration(i);
						drawShape(configurationVerticalBinding, config);
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
									drawShape(intermediateConfigurationVerticalBinding, pConfig.getNextConfigurationArtefact());
									drawConnector(concreteTransitionVerticalBinding, nextTransition, pConfig, pConfig.getNextConfigurationArtefact(),
											route);
									drawConnector(concreteTransitionVerticalBinding, previousTransition, pConfig.getNextConfigurationArtefact(),
											config, route);
								} else {
									// Otherwise, we represent the next and previous configuration of next visible
									AbstractTransitionArtefact abstractTransition = route.getAbstractTransition(pConfig, config);
									drawShape(nextConfigurationVerticalBinding, pConfig.getNextConfigurationArtefact());
									drawShape(previousConfigurationVerticalBinding, config.getPreviousConfigurationArtefact());
									drawConnector(abstractTransitionVerticalBinding, abstractTransition, pConfig.getNextConfigurationArtefact(),
											config.getPreviousConfigurationArtefact(), route);
									drawConnector(concreteTransitionVerticalBinding, nextTransition, pConfig, pConfig.getNextConfigurationArtefact(),
											route);
									drawConnector(concreteTransitionVerticalBinding, previousTransition, config.getPreviousConfigurationArtefact(),
											config, route);
								}
							} else {
								OBPTraceTransition transition = route.getTrace()
										.getTransition(pConfig.getConfiguration(), config.getConfiguration());
								drawConnector(concreteTransitionVerticalBinding, transition, pConfig, config, route);

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
											drawShape(messageStartAnchorVerticalBinding, route.getMessageInRoute(sendMessage));
											drawShape(messageEndAnchorVerticalBinding, route.getMessageInRoute(sendMessage.getOBPTraceMessageReceive()));
											drawConnector(messageVerticalBinding, route.getMessageInRoute(sendMessage), messageStartAnchorVerticalBinding,
												route.getMessageInRoute(sendMessage), messageEndAnchorVerticalBinding, route.getMessageInRoute(sendMessage.getOBPTraceMessageReceive()), graphBinding,route);
										}
										
									} 
									// A receive cannot happen without a send(the inverse can happen), so only process send messages
									else if(m instanceof OBPTraceMessageReceive){
										// Do nothing
									}
									else if(m instanceof OBPTraceMessageSynchro){	
										drawShape(messageStartAnchorVerticalBinding, route.getMessageInRoute(m));
										drawShape(messageEndAnchorVerticalBinding, route.getMessageInRoute(m));
										drawConnector(messageVerticalBinding, route.getMessageInRoute(m), messageStartAnchorVerticalBinding,
											route.getMessageInRoute(m), messageEndAnchorVerticalBinding, route.getMessageInRoute(m), graphBinding,route);
									}
									
								}

							}
						}
						// Show states for visible BehaviourObjects
						for (BehaviourObjectInRoute objectInRoute : route.getVisibleBehaviourObjects()) {
							OBPStateInRoute state = route.getOBPStateInRoute(objectInRoute, config);
							if(state.isSuccess()){
								drawShape(successStateVerticalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(state.isReject()){
								drawShape(rejectStateVerticalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(state.isCut()){
								drawShape(cutStateVerticalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(state.isNormal()){
								drawShape(normalStateVerticalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(config.getVisibleIndex()==0){
								drawShape(initialStateVerticalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else{
								drawShape(defaultStateVerticalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}
						}
					}
				}
				else if(layout==RouteLayout.HORIZONTAL){
					for (BehaviourObjectInRoute compInRoute : route.getVisibleBehaviourObjects()) {
						drawShape(lifeLineHorizontalBinding, compInRoute);
					}
					for (int i = 0; i < route.getSize(); i++) {
						OBPConfigurationInRoute config = route.getConfiguration(i);
						drawShape(configurationHorizontalBinding, config);
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
									drawShape(intermediateConfigurationHorizontalBinding, pConfig.getNextConfigurationArtefact());
									drawConnector(concreteTransitionHorizontalBinding, nextTransition, pConfig, pConfig.getNextConfigurationArtefact(),
											route);
									drawConnector(concreteTransitionHorizontalBinding, previousTransition, pConfig.getNextConfigurationArtefact(),
											config, route);
								} else {
									// Otherwise, we represent the next and previous configuration of next visible
									AbstractTransitionArtefact abstractTransition = route.getAbstractTransition(pConfig, config);
									drawShape(nextConfigurationHorizontalBinding, pConfig.getNextConfigurationArtefact());
									drawShape(previousConfigurationHorizontalBinding, config.getPreviousConfigurationArtefact());
									drawConnector(abstractTransitionHorizontalBinding, abstractTransition, pConfig.getNextConfigurationArtefact(),
											config.getPreviousConfigurationArtefact(), route);
									drawConnector(concreteTransitionHorizontalBinding, nextTransition, pConfig, pConfig.getNextConfigurationArtefact(),
											route);
									drawConnector(concreteTransitionHorizontalBinding, previousTransition, config.getPreviousConfigurationArtefact(),
											config, route);
								}
							} else {
								OBPTraceTransition transition = route.getTrace()
										.getTransition(pConfig.getConfiguration(), config.getConfiguration());
								drawConnector(concreteTransitionHorizontalBinding, transition, pConfig, config, route);

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
											drawShape(messageStartAnchorHorizontalBinding, route.getMessageInRoute(sendMessage));
											drawShape(messageEndAnchorHorizontalBinding, route.getMessageInRoute(sendMessage.getOBPTraceMessageReceive()));
											drawConnector(messageHorizontalBinding, route.getMessageInRoute(sendMessage), messageStartAnchorHorizontalBinding,
												route.getMessageInRoute(sendMessage), messageEndAnchorHorizontalBinding, route.getMessageInRoute(sendMessage.getOBPTraceMessageReceive()), graphBinding,route);
										}
										
									} 
									// A receive cannot happen without a send(the inverse can happen), so only process send messages
									else if(m instanceof OBPTraceMessageReceive){
										// Do nothing
									}
									else if(m instanceof OBPTraceMessageSynchro){
										drawShape(messageStartAnchorHorizontalBinding, route.getMessageInRoute(m));
										drawShape(messageEndAnchorHorizontalBinding, route.getMessageInRoute(m));
										drawConnector(messageHorizontalBinding, route.getMessageInRoute(m), messageStartAnchorHorizontalBinding,
											route.getMessageInRoute(m), messageEndAnchorHorizontalBinding, route.getMessageInRoute(m), graphBinding,route);
									}
									
								}

							}
						}
						// Show states for visible BehaviourObjects
						for (BehaviourObjectInRoute objectInRoute : route.getVisibleBehaviourObjects()) {
							OBPStateInRoute state = route.getOBPStateInRoute(objectInRoute, config);
							if(state.isSuccess()){
								drawShape(successStateHorizontalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(state.isReject()){
								drawShape(rejectStateHorizontalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(state.isCut()){
								drawShape(cutStateHorizontalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(state.isNormal()){
								drawShape(normalStateHorizontalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else if(config.getVisibleIndex()==0){
								drawShape(initialStateHorizontalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}else{
								drawShape(defaultStateHorizontalBinding, route.getOBPStateInRoute(objectInRoute, config));
							}
						}
					}
				}
			}
		});

		// VERTICAL BINDINGS
		setConfigurationBindingDynamicPropertyValues(configurationVerticalBinding, RouteLayout.VERTICAL);
		setStateBindingDynamicPropertyValues(initialStateVerticalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.VERTICAL);
		setStateBindingDynamicPropertyValues(defaultStateVerticalBinding,RECTANGLE_STATE_WIDTH,RECTANGLE_STATE_HEIGHT,  true, RouteLayout.VERTICAL);
		setStateBindingDynamicPropertyValues(successStateVerticalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.VERTICAL);
		setStateBindingDynamicPropertyValues(rejectStateVerticalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.VERTICAL);
		setStateBindingDynamicPropertyValues(cutStateVerticalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.VERTICAL);
		setStateBindingDynamicPropertyValues(normalStateVerticalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.VERTICAL);
		setInterConfigurationBindingDynamicPropertyValues(previousConfigurationVerticalBinding,PREVIOUS_CONFIGURATION_OFFSET, RouteLayout.VERTICAL);
		setInterConfigurationBindingDynamicPropertyValues(nextConfigurationVerticalBinding,NEXT_CONFIGURATION_OFFSET, RouteLayout.VERTICAL);
		setInterConfigurationBindingDynamicPropertyValues(intermediateConfigurationVerticalBinding,INTERMEDIATE_CONFIGURATION_OFFSET, RouteLayout.VERTICAL);
		setLifeLineBindingDynamicPropertyValues(lifeLineVerticalBinding, RouteLayout.VERTICAL);
		setMessageBindingDynamicPropertyValues(messageStartAnchorVerticalBinding, messageEndAnchorVerticalBinding, messageVerticalBinding, RouteLayout.VERTICAL);

		// HORIZONTAL BINDINGS
		setConfigurationBindingDynamicPropertyValues(configurationHorizontalBinding, RouteLayout.HORIZONTAL);
		setStateBindingDynamicPropertyValues(initialStateHorizontalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.HORIZONTAL);
		setStateBindingDynamicPropertyValues(defaultStateHorizontalBinding,RECTANGLE_STATE_WIDTH,RECTANGLE_STATE_HEIGHT,  true, RouteLayout.HORIZONTAL);
		setStateBindingDynamicPropertyValues(successStateHorizontalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.HORIZONTAL);
		setStateBindingDynamicPropertyValues(rejectStateHorizontalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.HORIZONTAL);
		setStateBindingDynamicPropertyValues(cutStateHorizontalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.HORIZONTAL);
		setStateBindingDynamicPropertyValues(normalStateHorizontalBinding,CIRCLE_STATE_WIDTH,CIRCLE_STATE_HEIGHT,  false, RouteLayout.HORIZONTAL);
		setInterConfigurationBindingDynamicPropertyValues(previousConfigurationHorizontalBinding,PREVIOUS_CONFIGURATION_OFFSET, RouteLayout.HORIZONTAL);
		setInterConfigurationBindingDynamicPropertyValues(nextConfigurationHorizontalBinding,NEXT_CONFIGURATION_OFFSET, RouteLayout.HORIZONTAL);
		setInterConfigurationBindingDynamicPropertyValues(intermediateConfigurationHorizontalBinding,INTERMEDIATE_CONFIGURATION_OFFSET, RouteLayout.HORIZONTAL);
		setLifeLineBindingDynamicPropertyValues(lifeLineHorizontalBinding, RouteLayout.HORIZONTAL);
		setMessageBindingDynamicPropertyValues(messageStartAnchorHorizontalBinding, messageEndAnchorHorizontalBinding, messageHorizontalBinding, RouteLayout.HORIZONTAL);

	}
	
	private void setConfigurationBindingDynamicPropertyValues(ShapeGRBinding<OBPConfigurationInRoute> configurationBinding, RouteLayout layout){
		configurationBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.configuration.name"),false);
		if(layout==RouteLayout.VERTICAL){
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(""+LEFT_OFFSET), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.visibleIndex*"+ ROW_HEIGHT + "+" +UP_OFFSET ), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("-10"), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("10"), false);
		}else if(layout==RouteLayout.HORIZONTAL){
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(""+UP_OFFSET), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.visibleIndex*"+ LIFE_LINE_WIDTH + "+" +LEFT_OFFSET ), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("10"), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("-10"), false);
		}
	}
	
	private void setInterConfigurationBindingDynamicPropertyValues(ShapeGRBinding<RelativeConfigurationArtefact> configurationBinding,double offset, RouteLayout layout){
		configurationBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.configuration.name"),false);
		if(layout==RouteLayout.VERTICAL){
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("5+"+LEFT_OFFSET), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"+ ROW_HEIGHT + "+" + offset), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("-5"), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("5"), false);
		}else if(layout==RouteLayout.HORIZONTAL){
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("5+"+UP_OFFSET), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"+ LIFE_LINE_WIDTH + "+20" +"+"+ offset), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("5"), false);
			configurationBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("-5"), false);
		}
	}
	
	private void setLifeLineBindingDynamicPropertyValues(ShapeGRBinding<BehaviourObjectInRoute> lifelineBinding, RouteLayout layout){
		lifelineBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.behaviourObject.name"),false);
		if(layout==RouteLayout.VERTICAL){
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.index*" + LIFE_LINE_WIDTH + "+" +LIFE_LINE_WIDTH), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(""+UP_OFFSET), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.HEIGHT, new DataBinding<Double>("(drawable.route.size-1)*"+ ROW_HEIGHT + "+" +UP_OFFSET), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("0"), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("-20"), false);
		}else if(layout==RouteLayout.HORIZONTAL){
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.index*" + ROW_HEIGHT + "+" + ROW_HEIGHT + "+"+ UP_OFFSET), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(""+LEFT_OFFSET), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.WIDTH, new DataBinding<Double>("(drawable.route.size-1)*"+ LIFE_LINE_WIDTH + "+" +LEFT_OFFSET), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.HEIGHT, new DataBinding<Double>("0.0"), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("-20"), false);
			lifelineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("0"), false);
		}
	}
	
	private void setStateBindingDynamicPropertyValues(ShapeGRBinding<OBPStateInRoute> stateBinding, double stateWidth, double stateHeight,boolean insideText,RouteLayout layout){
		stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.TEXT, new DataBinding<String>("drawable.state.value"), false);
		if(layout==RouteLayout.VERTICAL){
			stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+LIFE_LINE_WIDTH + "+"+LIFE_LINE_WIDTH + "-" + stateWidth/2), false);
			stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"+ ROW_HEIGHT + "+" +UP_OFFSET), false);
			if(insideText){
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>(""+stateWidth/2), false);
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>(""+stateHeight/2), false);
			}else{
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>("0"), false);
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("-"+stateHeight/2), false);
			}
		}else if(layout==RouteLayout.HORIZONTAL){
			stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.behaviourObjectInRoute.index*"+ROW_HEIGHT + "+" +ROW_HEIGHT + "+"+ UP_OFFSET + "-" + stateHeight/2), false);
			stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.configurationInRoute.visibleIndex*"+ LIFE_LINE_WIDTH + "+" + LEFT_OFFSET), false);
			if(insideText){
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>(""+stateWidth/2), false);
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>(""+stateHeight/2), false);
			}else{
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_X, new DataBinding<Double>(""+stateWidth/2), false);
				stateBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.ABSOLUTE_TEXT_Y, new DataBinding<Double>("-"+stateHeight/2), false);
			}
		}
	}
	
	private void setMessageBindingDynamicPropertyValues(ShapeGRBinding<MessageInRoute> startMessageBinding,ShapeGRBinding<MessageInRoute> endMessageBinding,ConnectorGRBinding<MessageInRoute> messageBinding, RouteLayout layout){
		messageBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.message.informationLabel"),false);
		if(layout==RouteLayout.VERTICAL){
			startMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
					"drawable.startBehaviourObject.index*" + LIFE_LINE_WIDTH + "+" + LIFE_LINE_WIDTH ), false);
			startMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
					"(drawable.startConfiguration.visibleIndex+1)*" + ROW_HEIGHT +"+" + RECTANGLE_STATE_HEIGHT), false);
			endMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
					"drawable.endBehaviourObject.index*" + LIFE_LINE_WIDTH + "+" + LIFE_LINE_WIDTH ), false);
			endMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
					"(drawable.endConfiguration.visibleIndex+1)*" + ROW_HEIGHT +"-" + RECTANGLE_STATE_HEIGHT), false);
		}else if(layout==RouteLayout.HORIZONTAL){
			startMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
					"drawable.startBehaviourObject.index*"+ ROW_HEIGHT +"+" +ROW_HEIGHT + "+"+ UP_OFFSET), false);
			startMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
					"(drawable.startConfiguration.visibleIndex+1)*" + LIFE_LINE_WIDTH ), false);
			endMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
					"drawable.endBehaviourObject.index*" + ROW_HEIGHT +"+" +ROW_HEIGHT + "+"+ UP_OFFSET), false);
			endMessageBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
					"(drawable.endConfiguration.visibleIndex+1)*" + LIFE_LINE_WIDTH +"-" + RECTANGLE_STATE_WIDTH*2), false);
		}
	
	}

}
