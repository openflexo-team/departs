package org.openflexo.module.traceanalysis.view.routeview;

import java.awt.Color;
import java.awt.Font;

import org.openflexo.fge.BackgroundImageBackgroundStyle;
import org.openflexo.fge.ColorGradientBackgroundStyle.ColorGradientDirection;
import org.openflexo.fge.ConnectorGraphicalRepresentation;
import org.openflexo.fge.Drawing.DrawingTreeNode;
import org.openflexo.fge.DrawingGraphicalRepresentation;
import org.openflexo.fge.FGEConstants;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.ForegroundStyle.DashStyle;
import org.openflexo.fge.GraphicalRepresentation.HorizontalTextAlignment;
import org.openflexo.fge.ShapeGraphicalRepresentation;
import org.openflexo.fge.connectors.ConnectorSpecification.ConnectorType;
import org.openflexo.fge.connectors.ConnectorSymbol.EndSymbolType;
import org.openflexo.fge.control.AbstractDianaEditor;
import org.openflexo.fge.control.MouseControl.MouseButton;
import org.openflexo.fge.control.MouseControlContext;
import org.openflexo.fge.control.actions.MouseClickControlActionImpl;
import org.openflexo.fge.control.actions.MouseClickControlImpl;
import org.openflexo.fge.shapes.Rectangle;
import org.openflexo.fge.shapes.ShapeSpecification;
import org.openflexo.fge.shapes.ShapeSpecification.ShapeType;
import org.openflexo.module.traceanalysis.view.routeview.OBPConfigurationInRoute.RelativeConfigurationArtefact;
import org.openflexo.module.traceanalysis.view.routeview.OBPRoute.AbstractTransitionArtefact;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.technologyadapter.trace.model.OBPTraceConfiguration;

/**
 * This class is used to create the graphical representations required in the module view
 * 
 * @author Vincent
 *
 */
public class DrawingFactory {

	public static final Resource SUCCESS = ResourceLocator.locateResource("Icons/cdl_success.png");
	public static final Resource REJECT = ResourceLocator.locateResource("Icons/cdl_reject.png");
	public static final Resource CUT = ResourceLocator.locateResource("Icons/cdl_cut.png");
	public static final Resource NORMAL = ResourceLocator.locateResource("Icons/cdl_normal.png");

	private final FGEModelFactory factory;

	public DrawingFactory(FGEModelFactory factory) {
		this.factory = factory;
	}

	public DrawingGraphicalRepresentation createGraphRepresentation() {
		DrawingGraphicalRepresentation graphRepresentation = factory.makeDrawingGraphicalRepresentation();
		graphRepresentation.setHeight(500);
		return graphRepresentation;
	}

	public ShapeGraphicalRepresentation createRejectObserverStateRepresentation(double width, double height) {
		return createObserverStateRepresentation(width, height, REJECT);
	}

	public ShapeGraphicalRepresentation createSucessObserverStateRepresentation(double width, double height) {
		return createObserverStateRepresentation(width, height, SUCCESS);
	}

	public ShapeGraphicalRepresentation createCutObserverStateRepresentation(double width, double height) {
		return createObserverStateRepresentation(width, height, CUT);
	}

	public ShapeGraphicalRepresentation createNormalObserverStateRepresentation(double width, double height) {
		return createObserverStateRepresentation(width, height, NORMAL);
	}

	public ShapeGraphicalRepresentation createDefaultStateRepresentation(double width, double height) {
		ShapeSpecification defaultStateSpecification = factory.makeShape(ShapeType.RECTANGLE);
		((Rectangle) defaultStateSpecification).setIsRounded(true);
		((Rectangle) defaultStateSpecification).setArcSize(10);
		ShapeGraphicalRepresentation defaultStateRepresentation = factory.makeShapeGraphicalRepresentation(defaultStateSpecification);
		defaultStateRepresentation.setWidth(width);
		defaultStateRepresentation.setHeight(height);
		defaultStateRepresentation.setAbsoluteTextX(width / 2);
		defaultStateRepresentation.setAbsoluteTextY(height / 2);
		defaultStateRepresentation.setTextStyle(factory.makeTextStyle(Color.BLACK, FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		defaultStateRepresentation.setShadowStyle(factory.makeNoneShadowStyle());
		defaultStateRepresentation.setBackground(factory.makeColoredBackground(Color.white));
		defaultStateRepresentation.setForeground(factory.makeForegroundStyle(Color.LIGHT_GRAY));
		defaultStateRepresentation.setLayer(1);
		return defaultStateRepresentation;
	}

	public ConnectorGraphicalRepresentation createConcreteTransitionRepresentation() {
		ConnectorGraphicalRepresentation concreteTransitionRepresentation = factory
				.makeConnectorGraphicalRepresentation(ConnectorType.LINE);
		concreteTransitionRepresentation.setForeground(factory.makeForegroundStyle(Color.GRAY, 0.5f));
		return concreteTransitionRepresentation;
	}

	public ConnectorGraphicalRepresentation createAbstractTransitionRepresentation() {
		ConnectorGraphicalRepresentation abstractTransitionRepresentation = factory
				.makeConnectorGraphicalRepresentation(ConnectorType.LINE);
		abstractTransitionRepresentation.setForeground(factory.makeForegroundStyle(Color.DARK_GRAY, 0.5f, DashStyle.DOTS_DASHES));
		abstractTransitionRepresentation
				.setTextStyle(factory.makeTextStyle(Color.green, FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.ITALIC)));
		abstractTransitionRepresentation.addToMouseClickControls(new MouseClickControlImpl<AbstractDianaEditor<?, ?, ?>>(
				"toogleVisibleStateForConfiguration", MouseButton.LEFT, 2, new MouseClickControlActionImpl<AbstractDianaEditor<?, ?, ?>>() {
					@Override
					public boolean handleClick(DrawingTreeNode<?, ?> dtn, AbstractDianaEditor<?, ?, ?> controller,
							MouseControlContext context) {
						System.out.println("toogleVisibleStateForConfiguration for " + dtn.getDrawable());
						if (dtn.getDrawable() instanceof AbstractTransitionArtefact) {
							AbstractTransitionArtefact transition = (AbstractTransitionArtefact) dtn.getDrawable();
							transition.unfold();

						}
						return true;
					}
				}, false, false, false, false, null));
		return abstractTransitionRepresentation;
	}

	public ConnectorGraphicalRepresentation createMessageRepresentation() {
		ConnectorGraphicalRepresentation messageRepresentation = factory.makeConnectorGraphicalRepresentation(ConnectorType.LINE);
		messageRepresentation.setForeground(factory.makeForegroundStyle(Color.GRAY, 1.5f));
		messageRepresentation.getConnectorSpecification().setEndSymbol(EndSymbolType.ARROW);
		messageRepresentation.getConnectorSpecification().setEndSymbolSize(10.0);
		return messageRepresentation;
	}

	public ShapeGraphicalRepresentation createInitialStateRepresentation(double width, double height) {
		ShapeSpecification initialStateSpecification = factory.makeShape(ShapeType.CIRCLE);
		ShapeGraphicalRepresentation initialStateRepresentation = factory.makeShapeGraphicalRepresentation(initialStateSpecification);
		initialStateRepresentation.setWidth(width);
		initialStateRepresentation.setHeight(height);
		initialStateRepresentation.setAbsoluteTextX(width / 2);
		initialStateRepresentation.setAbsoluteTextY(height / 2);
		initialStateRepresentation.setTextStyle(factory.makeTextStyle(Color.BLACK, FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		initialStateRepresentation.setShadowStyle(factory.makeNoneShadowStyle());
		initialStateRepresentation.setBackground(factory.makeColoredBackground(Color.gray));
		initialStateRepresentation.setForeground(factory.makeForegroundStyle(Color.BLACK));
		initialStateRepresentation.setLayer(1);
		return initialStateRepresentation;
	}

	public ShapeGraphicalRepresentation createConfigurationRepresentation() {
		ShapeGraphicalRepresentation configurationRepresentation = factory.makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		configurationRepresentation.setWidth(20);
		configurationRepresentation.setHeight(20);
		configurationRepresentation.setTextStyle(factory.makeTextStyle(Color.BLACK, FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.BOLD)));
		configurationRepresentation.setShadowStyle(factory.makeNoneShadowStyle());
		configurationRepresentation.setBackground(factory.makeColorGradientBackground(FGEConstants.DEFAULT_BACKGROUND_COLOR, Color.white,
				ColorGradientDirection.NORTH_WEST_SOUTH_EAST));
		configurationRepresentation.setForeground(factory.makeForegroundStyle(Color.ORANGE));
		configurationRepresentation.addToMouseClickControls(new MouseClickControlImpl<AbstractDianaEditor<?, ?, ?>>(
				"toogleVisibleStateForConfiguration", MouseButton.LEFT, 2, new MouseClickControlActionImpl<AbstractDianaEditor<?, ?, ?>>() {
					@Override
					public boolean handleClick(DrawingTreeNode<?, ?> dtn, AbstractDianaEditor<?, ?, ?> controller,
							MouseControlContext context) {
						System.out.println("toogleVisibleStateForConfiguration for " + dtn.getDrawable());
						if (dtn.getDrawable() instanceof OBPConfigurationInRoute) {
							OBPConfigurationInRoute configInRoute = (OBPConfigurationInRoute) dtn.getDrawable();
							configInRoute.getRoute().addToForcedHidedConfiguration(configInRoute);

						}
						return true;
					}
				}, false, false, false, false, null));
		return configurationRepresentation;
	}

	public ShapeGraphicalRepresentation createIntermediateConfigurationRepresentation() {
		ShapeGraphicalRepresentation intermediateConfigurationRepresentation = factory.makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		intermediateConfigurationRepresentation.setWidth(10);
		intermediateConfigurationRepresentation.setHeight(10);
		intermediateConfigurationRepresentation.setAbsoluteTextX(35);
		intermediateConfigurationRepresentation.setAbsoluteTextY(8);
		intermediateConfigurationRepresentation
				.setTextStyle(factory.makeTextStyle(Color.LIGHT_GRAY, FGEConstants.DEFAULT_SMALL_TEXT_FONT.deriveFont(Font.ITALIC)));
		intermediateConfigurationRepresentation.setShadowStyle(factory.makeNoneShadowStyle());
		intermediateConfigurationRepresentation.setBackground(
				factory.makeColorGradientBackground(Color.LIGHT_GRAY, Color.white, ColorGradientDirection.NORTH_WEST_SOUTH_EAST));
		intermediateConfigurationRepresentation.setForeground(factory.makeForegroundStyle(Color.ORANGE));
		intermediateConfigurationRepresentation.addToMouseClickControls(
				new MouseClickControlImpl<AbstractDianaEditor<?, ?, ?>>("toogleVisibleStateForIntermediateConfiguration", MouseButton.LEFT,
						1, new MouseClickControlActionImpl<AbstractDianaEditor<?, ?, ?>>() {
							@Override
							public boolean handleClick(DrawingTreeNode<?, ?> dtn, AbstractDianaEditor<?, ?, ?> controller,
									MouseControlContext context) {
								System.out.println("toogleVisibleStateForIntermediateConfiguration for " + dtn.getDrawable());
								if (dtn.getDrawable() instanceof RelativeConfigurationArtefact) {
									RelativeConfigurationArtefact artefact = (RelativeConfigurationArtefact) dtn.getDrawable();
									OBPRoute route = artefact.getConfigurationInRoute().getRoute();
									OBPTraceConfiguration config = artefact.getConfiguration();
									route.addToForcedVisibleConfiguration(route.getOBPConfigurationInRoute(config));
								}
								return true;
							}
						}, false, false, false, false, null));
		return intermediateConfigurationRepresentation;
	}

	public ShapeGraphicalRepresentation createMessageAnchorRepresentation() {
		ShapeGraphicalRepresentation messageAnchorRepresentation = factory.makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
		messageAnchorRepresentation.setWidth(0);
		messageAnchorRepresentation.setHeight(0);
		messageAnchorRepresentation.setShadowStyle(factory.makeNoneShadowStyle());
		messageAnchorRepresentation.setBackground(factory.makeEmptyBackground());
		messageAnchorRepresentation.setForeground(factory.makeNoneForegroundStyle());
		messageAnchorRepresentation.setIsFocusable(false);
		return messageAnchorRepresentation;
	}

	public ShapeGraphicalRepresentation createLifeLineRepresentation() {
		ShapeGraphicalRepresentation lifeLineRepresentation = factory.makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
		lifeLineRepresentation.setWidth(0);
		lifeLineRepresentation.setHeight(10);
		lifeLineRepresentation.setAbsoluteTextX(0);
		lifeLineRepresentation.setAbsoluteTextY(-20);
		lifeLineRepresentation.setTextStyle(factory.makeTextStyle(Color.DARK_GRAY, FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.BOLD)));
		lifeLineRepresentation.setShadowStyle(factory.makeNoneShadowStyle());
		lifeLineRepresentation.setBackground(factory.makeEmptyBackground());
		lifeLineRepresentation.setForeground(factory.makeForegroundStyle(Color.LIGHT_GRAY, 3f));
		lifeLineRepresentation.getForeground().setUseTransparency(true);
		lifeLineRepresentation.getForeground().setTransparencyLevel(0.5f);
		lifeLineRepresentation.setIsFocusable(false);
		lifeLineRepresentation.setLayer(0);
		return lifeLineRepresentation;
	}

	public ShapeGraphicalRepresentation createChronogramRepresentation() {
		ShapeGraphicalRepresentation chronogramGR = factory.makeShapeGraphicalRepresentation(ShapeType.RECTANGLE);
		// chronogramGR.setText(var.getName());
		// chronogramGR.setX(50);
		// chronogramGR.setY(50);
		chronogramGR.setWidth(900);
		chronogramGR.setHeight(25);
		chronogramGR.setAbsoluteTextX(-15);
		chronogramGR.setAbsoluteTextY(30);
		chronogramGR.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT);
		chronogramGR.setTextStyle(factory.makeTextStyle(Color.blue, FGEConstants.DEFAULT_TEXT_FONT));
		chronogramGR.setShadowStyle(factory.makeNoneShadowStyle());
		chronogramGR.setBackground(factory.makeColoredBackground(Color.WHITE));
		chronogramGR.setForeground(factory.makeForegroundStyle(Color.DARK_GRAY));
		// chronogramGR.setBorder(factory.makeShapeBorder(20, 20, 0, 0));
		return chronogramGR;
	}

	private ShapeGraphicalRepresentation createObserverStateRepresentation(double width, double height, Resource resource) {
		ShapeSpecification rejectStateSpecification = factory.makeShape(ShapeType.CIRCLE);
		ShapeGraphicalRepresentation rejectStateRepresentation = factory.makeShapeGraphicalRepresentation(rejectStateSpecification);
		rejectStateRepresentation.setWidth(width);
		rejectStateRepresentation.setHeight(height);
		rejectStateRepresentation.setAbsoluteTextX(-5);
		rejectStateRepresentation.setAbsoluteTextY(-10);
		rejectStateRepresentation.setTextStyle(factory.makeTextStyle(Color.BLACK, FGEConstants.DEFAULT_TEXT_FONT.deriveFont(Font.PLAIN)));
		rejectStateRepresentation.setShadowStyle(factory.makeNoneShadowStyle());
		rejectStateRepresentation.setBackground(factory.makeImageBackground(resource));
		rejectStateRepresentation.setForeground(factory.makeForegroundStyle(Color.BLACK));
		((BackgroundImageBackgroundStyle) rejectStateRepresentation.getBackground()).setFitToShape(true);
		rejectStateRepresentation.setLayer(1);
		return rejectStateRepresentation;
	}

}
