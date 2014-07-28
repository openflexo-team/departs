package org.openflexo.traceanalysis;

import java.awt.Color;
import java.awt.Font;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.fge.ColorGradientBackgroundStyle.ColorGradientDirection;
import org.openflexo.fge.ConnectorGraphicalRepresentation;
import org.openflexo.fge.DrawingGraphicalRepresentation;
import org.openflexo.fge.FGEConstants;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.ForegroundStyle.DashStyle;
import org.openflexo.fge.GRBinding.ConnectorGRBinding;
import org.openflexo.fge.GRBinding.DrawingGRBinding;
import org.openflexo.fge.GRBinding.GeometricGRBinding;
import org.openflexo.fge.GRBinding.ShapeGRBinding;
import org.openflexo.fge.GRProvider.ConnectorGRProvider;
import org.openflexo.fge.GRProvider.DrawingGRProvider;
import org.openflexo.fge.GRProvider.GeometricGRProvider;
import org.openflexo.fge.GRProvider.ShapeGRProvider;
import org.openflexo.fge.GRStructureVisitor;
import org.openflexo.fge.GeometricGraphicalRepresentation;
import org.openflexo.fge.GraphicalRepresentation;
import org.openflexo.fge.ShapeGraphicalRepresentation;
import org.openflexo.fge.connectors.ConnectorSpecification.ConnectorType;
import org.openflexo.fge.connectors.ConnectorSymbol.EndSymbolType;
import org.openflexo.fge.control.AbstractDianaEditor;
import org.openflexo.fge.control.MouseControl.MouseButton;
import org.openflexo.fge.control.MouseControlContext;
import org.openflexo.fge.control.actions.MouseClickControlActionImpl;
import org.openflexo.fge.control.actions.MouseClickControlImpl;
import org.openflexo.fge.geom.FGEGeometricObject.Filling;
import org.openflexo.fge.geom.FGERectangle;
import org.openflexo.fge.impl.DrawingImpl;
import org.openflexo.fge.shapes.ShapeSpecification.ShapeType;
import org.openflexo.traceanalysis.OBPConfigurationInRoute.RelativeConfigurationArtefact;
import org.openflexo.traceanalysis.OBPRoute.AbstractTransitionArtefact;

public class RouteDrawing extends DrawingImpl<OBPRoute> {

	public static final double ROW_HEIGHT = 60.0;
	public static final double LIFE_LINE_WIDTH = 150.0;

	private DrawingGraphicalRepresentation graphRepresentation;
	private ShapeGraphicalRepresentation configurationRepresentation;
	private ConnectorGraphicalRepresentation concreteTransitionRepresentation;
	private ConnectorGraphicalRepresentation abstractTransitionRepresentation;
	private ShapeGraphicalRepresentation intermediateConfigurationRepresentation;

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
		// lifeLineRepresentation.setLayer(9);

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
		final ConnectorGRBinding<OBPTransition> concreteTransitionBinding = bindConnector(OBPTransition.class, "concreteTransition",
				configurationBinding, configurationBinding, graphBinding, new ConnectorGRProvider<OBPTransition>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(OBPTransition drawable, FGEModelFactory factory) {
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

		final ShapeGRBinding<ComponentInRoute> lifeLineBinding = bindShape(ComponentInRoute.class, "lifeLine",
				new ShapeGRProvider<ComponentInRoute>() {
					@Override
					public ShapeGraphicalRepresentation provideGR(ComponentInRoute drawable, FGEModelFactory factory) {
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

		final Object myRectangle = new Object();
		FGERectangle rect = new FGERectangle(Filling.FILLED);
		rect.setRect(200, 200, 200, 200);

		final GeometricGraphicalRepresentation geomGR = getFactory().makeGeometricGraphicalRepresentation(rect);
		geomGR.setBackground(getFactory().makeColoredBackground(/*TextureType.TEXTURE1, Color.red, Color.white*/Color.YELLOW));
		geomGR.setForeground(getFactory().makeForegroundStyle(Color.blue));

		final GeometricGRBinding<Object> geomBinding = bindGeometric(Object.class, "geometricObject", new GeometricGRProvider<Object>() {
			@Override
			public GeometricGraphicalRepresentation provideGR(Object drawable, FGEModelFactory factory) {
				return geomGR;
			}
		});

		graphBinding.addToWalkers(new GRStructureVisitor<OBPRoute>() {

			@Override
			public void visit(OBPRoute route) {
				for (int i = 0; i < route.getSize(); i++) {
					OBPConfigurationInRoute config = route.getConfiguration(i);
					drawShape(configurationBinding, config);
					if (i > 0) {
						OBPConfigurationInRoute pConfig = route.getConfiguration(i - 1);
						OBPTransition nextTransition = route.getTrace().getTransition(pConfig.getConfiguration(),
								pConfig.getNextConfigurationArtefact().getConfiguration());
						OBPTransition previousTransition = route.getTrace().getTransition(
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
							OBPTransition transition = route.getTrace()
									.getTransition(pConfig.getConfiguration(), config.getConfiguration());
							drawConnector(concreteTransitionBinding, transition, pConfig, config, route);

							for (Message m : transition.getMessages()) {
								drawShape(messageStartAnchorBinding, route.getMessageInRoute(m));
								drawShape(messageEndAnchorBinding, route.getMessageInRoute(m));
								drawConnector(messageBinding, route.getMessageInRoute(m), messageStartAnchorBinding,
										route.getMessageInRoute(m), messageEndAnchorBinding, route.getMessageInRoute(m), graphBinding,
										route);
							}

						}
					}
				}

				for (ComponentInRoute compInRoute : route.getVisibleComponents()) {
					drawShape(lifeLineBinding, compInRoute);
				}

				//drawGeometricObject(geomBinding, myRectangle);

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

		lifeLineBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.component.componentName"),
				false);
		lifeLineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.index*" + LIFE_LINE_WIDTH
				+ "+120.0"), false);
		lifeLineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("50.0"), false);
		lifeLineBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.HEIGHT, new DataBinding<Double>("(drawable.route.size-1)*"
				+ ROW_HEIGHT + "+20.0"), false);

		messageStartAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
				"drawable.startComponent.index*" + LIFE_LINE_WIDTH + "+120.0"), false);
		messageStartAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
				"(drawable.startConfiguration.visibleIndex+1)*" + ROW_HEIGHT + "+10.0"), false);

		messageEndAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>(
				"drawable.endComponent.index*" + LIFE_LINE_WIDTH + "+120.0"), false);
		messageEndAnchorBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>(
				"(drawable.endConfiguration.visibleIndex+1)*" + ROW_HEIGHT + "-10.0"), false);
		messageBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.message.messageLabel"),
				false);

	}
}
