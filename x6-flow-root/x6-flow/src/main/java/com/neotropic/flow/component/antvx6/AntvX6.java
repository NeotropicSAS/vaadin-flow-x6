/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.neotropic.flow.component.antvx6;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.neotropic.flow.component.antvx6.objects.Geometry;
import com.neotropic.flow.component.antvx6.objects.X6Edge;
import com.neotropic.flow.component.antvx6.objects.X6Node;
import com.neotropic.flow.component.antvx6.objects.X6NodeBackground;
import com.neotropic.flow.component.antvx6.constants.X6Constants;
import com.neotropic.flow.component.antvx6.objects.Vertex;
import com.neotropic.flow.component.antvx6.objects.X6EdgeBasic;
import com.neotropic.flow.component.antvx6.objects.X6NodeText;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
@Tag("x-6")
@JsModule("./src/x-6.ts")
@NpmPackage(value = "@antv/x6", version = "2.18.1")
@NpmPackage(value = "@antv/x6-plugin-snapline", version = "2.1.7")
@NpmPackage(value = "@antv/x6-plugin-transform", version = "2.1.8")
@NpmPackage(value = "@antv/x6-plugin-export", version = "2.1.6")
@NpmPackage(value = "@antv/x6-plugin-selection", version = "2.2.2")
@NpmPackage(value = "@antv/x6-plugin-scroller", version = "2.0.10")
@NpmPackage(value = "@antv/x6-plugin-minimap", version = "2.0.7")
public class AntvX6 extends Div {
    /*
    * Basic graph configuration properties
    */
    private static final String PROPERTY_GRAPH_TYPE = "graph_type";
    private static final String PROPERTY_MINIMAP_DIV = "minimap_div";
    private static final String PROPERTY_CONTEXT_MENU_DIV = "context_menu_div";
    private static final String PROPERTY_GRAPH_WIDTH = "graph_width";
    private static final String PROPERTY_GRAPH_HEIGHT = "graph_height";
    private static final String PROPERTY_GRAPH_BACKGROUND_COLOR = "graph_background_color";
    private static final String PROPERTY_GRAPH_GRID = "graph_grid";
    private static final String PROPERTY_GRAPH_PANNING = "graph_panning";
    private static final String PROPERTY_GRAPH_MOUSE_WHEEL = "graph_mouse_wheel";
    private static final String PROPERTY_NODES_LABEL_STATE = "nodes_label_state";
    private static final String PROPERTY_NODES_LABEL_COLOR_TOGGLE = "nodes_label_color_toggle";
    private static final String PROPERTY_NODES_LABEL_BGCOLOR = "nodes_label_bgColor";
    private static final String PROPERTY_PADDING_EXPORT_GRAPH_JPEG = "padding_export_graph_JPEG";
    private static final String PROPERTY_GRAPH_ZOOM = "graph_zoom";
    private static final String PROPERTY_GRAPH_NODE_BACKGROUND_ID = "graph_node_background_id";

    /*
    * Background of the x6 canvas.
    */  
    private X6NodeBackground nodeBackground;
    /*
    * List of nodes present in the graph.
    */
    private List<X6Node> nodes;
    /*
    * List of text nodes present in the graph.
    */
    private List<X6NodeText> textNodes;
    /*
    * List of basic edges present in the graph.
    */
    private List<X6EdgeBasic> basicEdges;
    /*
    * List of edges present in the graph.
    */
    private List<X6Edge> edges;
    /*
    * List of listeners.
    */
    public List<Registration> lstListeners;
   
    public AntvX6() {
        this.nodeBackground = new X6NodeBackground();
        this.nodes = new ArrayList();
        this.textNodes = new ArrayList();
        this.basicEdges = new ArrayList<>();
        this.edges = new ArrayList();
        this.lstListeners = new ArrayList();
    }      
    
    /*
    * Methods to set property X6 web component
    */
    
    /**
    * Sets the type of the graph.
    * 
    * @param graphType the type of the graph to set. Use 0 for a basic graph and 1 for a graph with interactions.
    */
    public void setGraphType(int graphType){
        getElement().setProperty(PROPERTY_GRAPH_TYPE, graphType);
    }
    
    /**
    * Enables or disables the minimap state.
    * 
    * @param state the state to set for the minimap. Pass true to enable the minimap div, or false to disable it.
    */
    public void setMinimapState(boolean state){
        getElement().setProperty(PROPERTY_MINIMAP_DIV , state);
    }

    /**
     * Enables or disables the context menu state.
     * 
     * @param state the state to set for the context menu.Pass true to enable the context menu div, or false to disable it.
     */
    public void setContextMenuState(boolean state){
        getElement().setProperty(PROPERTY_CONTEXT_MENU_DIV, state);
    }

    /**
    * Set the ID of the node background.
     * @param idBackground id of the background node
    */
    public void setNodeBackgroundId(String idBackground){
        this.getElement().setProperty(PROPERTY_GRAPH_NODE_BACKGROUND_ID, idBackground);
    }
    
    /**
    * Sets the width of the graph.
    *
    * @param width the width to set for the graph in pixels.
    */
    public void setGrapthWidth(int width){
        this.getElement().setProperty(PROPERTY_GRAPH_WIDTH, width);
    }
    
    /**
    * Sets the height of the graph.
    *
    * @param height the height to set for the graph in pixels.
    */
     public void setGraptHeight(int height){
        this.getElement().setProperty(PROPERTY_GRAPH_HEIGHT, height);
    }
     
    /**
    * Sets the background color of the graph.
    *
    * @param color the background color.
    */
    public void setBackgroundColor(String color){
        this.getElement().setProperty(PROPERTY_GRAPH_BACKGROUND_COLOR, color);
    }
    
    /**
    * Enables or disables the grid on the graph.
    *
    * @param grid true to enable the grid, false to disable it.
    */
    public void setGrid(boolean grid){
        this.getElement().setProperty(PROPERTY_GRAPH_GRID, grid);
    }
    
    /**
    * Enables or disables panning functionality for the graph.
    *
    * @param panning true to enable panning, false to disable it.
    */
    public void setPanning(boolean panning){
        this.getElement().setProperty(PROPERTY_GRAPH_PANNING, panning);
    }
    
    /**
    * Enables or disables mouse wheel zooming for the graph.
    *
    * @param mouseWheel true to enable mouse wheel zooming, false to disable it.
    */
    public void setMouseWheel(boolean mouseWheel){
        this.getElement().setProperty(PROPERTY_GRAPH_MOUSE_WHEEL, mouseWheel);
    }
    
    /**
    * Sets the label visibility state for nodes.
    *
    * @param state true to show labels, false to hide them.
    */
    public void setNodesLabelState(boolean state){
        this.getElement().setProperty(PROPERTY_NODES_LABEL_STATE, state);
    }
    
    /**
    * Toggles the color setting for node labels.
    *
    * @param toggle true if the node's label color has been toggled, false if it has not been toggled.
    */
    public void setNodesLabelColorToggle(boolean toggle){
        this.getElement().setProperty(PROPERTY_NODES_LABEL_COLOR_TOGGLE, toggle);
    }
    
    /**
    * Sets the background color for node labels.
    *
    * @param color the background color.
    */
    public void setNodesLabelBgColor(String color){
        this.getElement().setProperty(PROPERTY_NODES_LABEL_BGCOLOR, color);
    }
    
     /**
    * Sets the padding for exporting the graph as a JPEG image.
    *
    * @param padding the padding value in pixels.
    */
    public void setPaddingExportGraphJPEG(int padding){
        this.getElement().setProperty(PROPERTY_PADDING_EXPORT_GRAPH_JPEG, padding);
    }
    
    /**
    * Sets the zoom level for the graph.
    *
    * @param zoom the zoom level as a double value.
    */
    public void setGraphZomm(double zoom){
        this.getElement().setProperty(PROPERTY_GRAPH_ZOOM, zoom);
    }
    
    /*
    * End of methods to set property X6 web component
    */
    
    /*
    * Canvas manipulation methods
    */
    
    /**
    * Clears all nodes and edges from the graph.
    * 
    * This method removes all nodes and edges from the local lists,
    * effectively resetting the graph's state.
    */
    public void cleanGraph(){
        this.nodes.clear();
        this.edges.clear();
        getElement().callJsFunction("cleanGraph");
    }
    
    public void removeCell(String id){
        removeX6Cell(id);
        getElement().callJsFunction("removeCell", id);
    }
    
    public boolean removeX6Cell(String id) {
        return removeX6Node(id) || removeX6NodeText(id) || removeX6Edge(id) || removeX6EdgeBasic(id);
    }

    public boolean removeX6Node(String id) {
        return nodes.removeIf(node -> node.getId().equals(id));
    }

    public boolean removeX6NodeText(String id) {
        return textNodes.removeIf(text -> text.getId().equals(id));
    }

    public boolean removeX6Edge(String id) {
        return edges.removeIf(edge -> edge.getId().equals(id));
    }

    public boolean removeX6EdgeBasic(String id) {
        return basicEdges.removeIf(edge -> edge.getId().equals(id));
    }


    /**
    * Refreshes the graph.
    * 
    * Creates a backup of the current graph, clears it, and then 
    * re-populates it using the backup data.
    */
    public void refreshGraph(){
        this.getElement().callJsFunction("refreshGraph");
    }
    
    /*
    * End of canvas manipulation methods
    */
    
    /*
    * Methods to add X6 plugins
    */
    
    /**
    * Adds a scroller plugin
    *
    * @param enabled              Whether the scroller is enabled.
    * @param pannable             Whether the scroller is pannable.
    * @param pageVisible          Whether the page is visible.
    * @param pageBreak            Whether the page breaks are enabled.
    * @param width                The width of the scroller.
    * @param height               The height of the scroller.
    * @param pageWidth            The width of the page.
    * @param pageHeight           The height of the page.
    * @param padding              The padding around the scroller.
    * @param autoResize           Whether the scroller should auto-resize.
    * @param scrollerPositionLeft The left position of the scroller.
    * @param scrollerPositionTop  The top position of the scroller.
    */
    public void addScrollerPlugin(boolean enabled, boolean pannable, boolean pageVisible, boolean pageBreak,
        double width, double height, double pageWidth, double pageHeight, double padding, boolean autoResize,
        double scrollerPositionLeft, int scrollerPositionTop)
    {
        getElement().callJsFunction(
            "addScrollerPlugin",enabled, pannable, pageVisible, pageBreak, 
                                width, height, pageWidth, pageHeight, padding, autoResize, 
                                scrollerPositionLeft, scrollerPositionTop
        );
    }
    
    /**
    * Adds an export plugin to the element, enabling the ability to export the graph as an image.
    */
    public void addExportPlugin(){
        getElement().callJsFunction("addExportPlugin");
    }

    /**
     * Adds a snapline plugin to nodes, enabling or disabling snapline functionality for better alignment.
     *
     * @param enabled Whether the snapline functionality is enabled or disabled.
     */
    public void addSnaplinePlugin(boolean enabled){
        getElement().callJsFunction("addSnaplinePlugin", enabled);
    }
    
    /**
    * Adds a transform plugin, enabling transformation features like rotating and resizing.
    *
    * @param rotating                  Whether rotating is enabled.
    * @param resizingEnabled           Whether resizing is enabled.
    * @param resizingOrthogonal        Whether to display the intermediate adjustment points
    * @param resizingMinWidth          The minimum width allowed when resizing.
    * @param resizingMinHeight         The minimum height allowed when resizing.
    * @param resizingPreserveAspectRatio Whether the aspect ratio is preserved during resizing.
    */
    public void addTransformPlugin(boolean rotating, boolean resizingEnabled, boolean resizingOrthogonal, 
        int resizingMinWidth, int resizingMinHeight, boolean resizingPreserveAspectRatio)
    {
        getElement().callJsFunction(
            "addTransformPlugin", rotating, resizingEnabled, resizingOrthogonal, 
            resizingMinWidth, resizingMinHeight, resizingPreserveAspectRatio
        );
    }
    
    /**
    * Adds a selection plugin to the element, enabling various selection features like multiple selection, rubberband, and movement.
    *
    * @param enabled                Whether selection is enabled.
    * @param multiple               Whether multiple selection is allowed.
    * @param rubberband             Whether to enable the box selection node function.
    * @param movable                Whether selected nodes can be moved.
    * @param showNodeSelectionBox   Whether the node selection box is shown.
    */
    public void addSelectionPlugin(boolean enabled, boolean multiple,boolean rubberband ,boolean movable, boolean showNodeSelectionBox) {
        getElement().callJsFunction(
            "addSelectionPlugin", enabled, multiple, rubberband ,movable, showNodeSelectionBox
        );
    }
    
    /**
     * Adds a minimap plugin to the element, providing a smaller, overview version of the graph.
     *
     * @param width  The width of the minimap.
     * @param height The height of the minimap.
     */
    public void addMinimapPlugin(int width, int height){
        getElement().callJsFunction("addMinimapPlugin", width, height);
    }

    /*
    * End of methods to add X6 plugins
    */
    
    /*
    * Methods to manipulate nodes
    */
    
    /**
    * Draws the node background for a graph.
    * 
    * This method constructs a background object in the canvas
    * 
    * @param background the X6NodeBackground object
    */
    public void drawNodeBackground(X6NodeBackground background) {
        JsonObject backgroundData = new JsonObject();

        backgroundData.addProperty("id", background.getId());

        JsonObject geometry = new JsonObject();
        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("x", background.getGeometry().getCoordinates().getX());
        coordinates.addProperty("y", background.getGeometry().getCoordinates().getY());
        geometry.add("coordinates", coordinates);

        JsonObject dimensions = new JsonObject();
        dimensions.addProperty("width", background.getGeometry().getDimensions().getWidth());
        dimensions.addProperty("height", background.getGeometry().getDimensions().getHeight());
        geometry.add("dimensions", dimensions);

        backgroundData.add("geometry", geometry);

        backgroundData.addProperty("shape", background.getShape());
        backgroundData.addProperty("imgUrl", background.getImgUrl());
        backgroundData.addProperty("movable", background.isMovable());
        backgroundData.addProperty("parentId", background.getParentId());
        backgroundData.addProperty("labelText", background.getLabelText());

        JsonObject labelStyles = new JsonObject();
        labelStyles.addProperty("labelTextColor", background.getLabelStyles().getLabelTextColor());
        labelStyles.addProperty("labelFontSize", background.getLabelStyles().getLabelFontSize());
        labelStyles.addProperty("labelFontFamily", background.getLabelStyles().getLabelFontFamily());
        labelStyles.addProperty("labelPosition", background.getLabelStyles().getLabelPosition());
        labelStyles.addProperty("labelVisibility", background.getLabelStyles().getLabelVisibility());
        backgroundData.add("labelStyles", labelStyles);

        JsonObject nodeStyles = new JsonObject();
        nodeStyles.addProperty("borderRadius", background.getNodeStyles().getBorderRadius());
        nodeStyles.addProperty("fillColor", background.getNodeStyles().getFillColor());
        nodeStyles.addProperty("strokeColor", background.getNodeStyles().getStrokeColor());
        nodeStyles.addProperty("strokeWidth", background.getNodeStyles().getStrokeWidth());
        nodeStyles.addProperty("dash", background.getNodeStyles().getDash());
        nodeStyles.addProperty("zIndex", background.getNodeStyles().getzIndex());
        backgroundData.add("nodeStyles", nodeStyles);

        getElement().callJsFunction(
            "drawBackground", backgroundData.toString()
        );

        this.nodeBackground = background;
    }
    
    /**
    * Removes the node background of the the graph.
    */
    public void removeNodeBackground(){
        getElement().callJsFunction("removeBackground");
        this.nodeBackground.setId("");
        this.nodeBackground.setGeometry(new Geometry(0, 0 , 0, 0));
        this.nodeBackground.setImgUrl("");
        this.nodeBackground.setShape(X6Constants.SHAPE_IMAGE);
    }
    
    /**
    * Draws a visual representation of a node in the graph.
    * 
    * @param node the X6Node object to be draw.
    */
    public void drawNode(X6Node node) {
        JsonObject nodeData = new JsonObject();

        nodeData.addProperty("id", node.getId());

        JsonObject geometry = new JsonObject();
        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("x", node.getGeometry().getCoordinates().getX());
        coordinates.addProperty("y", node.getGeometry().getCoordinates().getY());
        geometry.add("coordinates", coordinates);

        JsonObject dimensions = new JsonObject();
        dimensions.addProperty("width", node.getGeometry().getDimensions().getWidth());
        dimensions.addProperty("height", node.getGeometry().getDimensions().getHeight());
        geometry.add("dimensions", dimensions);

        nodeData.add("geometry", geometry);
        
        JsonArray toolsArray = new JsonArray();
        for (String tool : node.getTools()) 
            toolsArray.add(tool);  
        nodeData.add("tools", toolsArray);

        nodeData.addProperty("shape", node.getShape());
        nodeData.addProperty("imgUrl", node.getImgUrl());
        nodeData.addProperty("movable", node.isMovable());
        nodeData.addProperty("parentId", node.getParentId());
        nodeData.addProperty("labelText", node.getLabelText());

        JsonObject labelStyles = new JsonObject();
        labelStyles.addProperty("labelTextColor", node.getLabelStyles().getLabelTextColor());
        labelStyles.addProperty("labelFontSize", node.getLabelStyles().getLabelFontSize());
        labelStyles.addProperty("labelFontFamily", node.getLabelStyles().getLabelFontFamily());
        labelStyles.addProperty("labelPosition", node.getLabelStyles().getLabelPosition());
        labelStyles.addProperty("labelVisibility", node.getLabelStyles().getLabelVisibility());
        nodeData.add("labelStyles", labelStyles);

        JsonObject nodeStyles = new JsonObject();
        nodeStyles.addProperty("borderRadius", node.getNodeStyles().getBorderRadius());
        nodeStyles.addProperty("fillColor", node.getNodeStyles().getFillColor());
        nodeStyles.addProperty("strokeColor", node.getNodeStyles().getStrokeColor());
        nodeStyles.addProperty("strokeWidth", node.getNodeStyles().getStrokeWidth());
        nodeStyles.addProperty("dash", node.getNodeStyles().getDash());
        nodeStyles.addProperty("zIndex", node.getNodeStyles().getzIndex());
        nodeData.add("nodeStyles", nodeStyles);

        nodeData.addProperty("port", node.isPort());
        
        getElement().callJsFunction(
            "drawNode", nodeData.toString()
        );

        this.nodes.add(node);
    }

    /**
    * Draws a visual representation of a text for a node in the graph.
    * 
    * @param nodeText the X6NodeText object to be draw
    */
    public void drawText(X6NodeText nodeText) {
        JsonObject textData = new JsonObject();

        textData.addProperty("id", nodeText.getId());

        JsonObject geometry = new JsonObject();
        JsonObject coordinates = new JsonObject();
        coordinates.addProperty("x", nodeText.getGeometry().getCoordinates().getX());
        coordinates.addProperty("y", nodeText.getGeometry().getCoordinates().getY());
        geometry.add("coordinates", coordinates);

        JsonObject dimensions = new JsonObject();
        dimensions.addProperty("width", nodeText.getGeometry().getDimensions().getWidth());
        dimensions.addProperty("height", nodeText.getGeometry().getDimensions().getHeight());
        geometry.add("dimensions", dimensions);

        textData.add("geometry", geometry);

        textData.addProperty("shape", nodeText.getShape());
        textData.addProperty("imgUrl", nodeText.getImgUrl());
        textData.addProperty("movable", nodeText.isMovable());
        textData.addProperty("parentId", nodeText.getParentId());
        textData.addProperty("labelText", nodeText.getLabelText());

        JsonObject labelStyles = new JsonObject();
        labelStyles.addProperty("labelTextColor", nodeText.getLabelStyles().getLabelTextColor());
        labelStyles.addProperty("labelFontSize", nodeText.getLabelStyles().getLabelFontSize());
        labelStyles.addProperty("labelFontFamily", nodeText.getLabelStyles().getLabelFontFamily());
        labelStyles.addProperty("labelPosition", nodeText.getLabelStyles().getLabelPosition());
        labelStyles.addProperty("labelVisibility", nodeText.getLabelStyles().getLabelVisibility());
        textData.add("labelStyles", labelStyles);

        JsonObject nodeStyles = new JsonObject();
        nodeStyles.addProperty("borderRadius", nodeText.getNodeStyles().getBorderRadius());
        nodeStyles.addProperty("fillColor", nodeText.getNodeStyles().getFillColor());
        nodeStyles.addProperty("strokeColor", nodeText.getNodeStyles().getStrokeColor());
        nodeStyles.addProperty("strokeWidth", nodeText.getNodeStyles().getStrokeWidth());
        nodeStyles.addProperty("dash", nodeText.getNodeStyles().getDash());
        nodeStyles.addProperty("zIndex", nodeText.getNodeStyles().getzIndex());
        textData.add("nodeStyles", nodeStyles);

        textData.addProperty("labelPositionRelative", nodeText.getLabelPositionRelative());

        getElement().callJsFunction("drawText", textData.toString());

        this.textNodes.add(nodeText);
    }
    
    /**
    * Sets the style for a specific node.
    * 
    * @param id the ID of the node to which the style should be applied.
    * @param style the name of the style property to modify.
    * @param value the value to set for the specified style property.
    */
    public void setNodeStyle(String id, String style, String value){
        this.getElement().callJsFunction("setNodeStyle", id, style, value);
    }
    
    /**
    * Establishes a parent-child relationship between two nodes in the graph.
    *
    * @param idParent the id of the parent node
    * @param idChild the id of the child node
    */
    public void setParent(String idParent, String idChild){
        this.getElement().callJsFunction("setParent", idParent, idChild);
    }
    
    /**
    * Selects a specified node in the graph. (Make sure to add the Selection plugin first)
    *
    * @param id id of the X6Node to be select.
    */
    public void selectNode(String id) {
        getElement().callJsFunction("selectNode", id);
    }
    
    /**
    * Unselect a specified cell in the graph. (Make sure to add the Selection plugin first)
    *
    * @param id id of the X6Cell to be unselect.
    */
    public void unselectCell(String id){
        getElement().callJsFunction("unselectCell", id);
    }
    
    /*
    * End of methods to manipulate nodes
    */
    
    /*
    * Methods to manipulate edges
    */
    
    /**
    * Draws a visual representation of an edge in the graph with a single label.
    * 
    * @param edge the X6Edge object to be draw.
    */
    public void drawEdge(X6Edge edge) {
        JsonObject edgeData = new JsonObject();

        edgeData.addProperty("id", edge.getId());
        edgeData.addProperty("idSource", edge.getIdSource());
        edgeData.addProperty("idTarget", edge.getIdTarget());
        edgeData.addProperty("label", edge.getLabel());

        JsonObject edgeStyles = new JsonObject();
        edgeStyles.addProperty("labelTextColor", edge.getEdgeStyles().getLabelTextColor());
        edgeStyles.addProperty("labelFontSize", edge.getEdgeStyles().getLabelFontSize());
        edgeStyles.addProperty("labelFontFamily", edge.getEdgeStyles().getLabelFontFamily());
        edgeStyles.addProperty("strokeColor", edge.getEdgeStyles().getStrokeColor());
        edgeStyles.addProperty("strokeWidth", edge.getEdgeStyles().getStrokeWidth());
        edgeStyles.addProperty("dash", edge.getEdgeStyles().getDash());
        edgeStyles.addProperty("borderRadius", edge.getEdgeStyles().getBorderRadious());
        edgeStyles.addProperty("zIndex", edge.getEdgeStyles().getzIndex());
        edgeData.add("edgeStyles", edgeStyles);

        JsonArray verticesArray = new JsonArray();
        for (Vertex vertex : edge.getVertices()) {
            JsonObject vertexObj = new JsonObject();
            vertexObj.addProperty("x", vertex.getX());
            vertexObj.addProperty("y", vertex.getY());
            verticesArray.add(vertexObj);
        }
        edgeData.add("vertices", verticesArray);

        getElement().callJsFunction(
            "drawEdge", edgeData.toString() 
        );

        this.edges.add(edge);
    }
    
    /**
    * Draws a visual representation of an edge in the graph with no labels.
    * 
    * @param edge the X6EdgeBasic object to be draw.
    */
    public void drawEBasicEdge(X6EdgeBasic edge) {
        JsonObject edgeData = new JsonObject();

        edgeData.addProperty("id", edge.getId());
        edgeData.addProperty("idSource", edge.getIdSource());
        edgeData.addProperty("idTarget", edge.getIdTarget());

        JsonObject edgeStyles = new JsonObject();
        edgeStyles.addProperty("strokeColor", edge.getEdgeStyles().getStrokeColor());
        edgeStyles.addProperty("strokeWidth", edge.getEdgeStyles().getStrokeWidth());
        edgeStyles.addProperty("dash", edge.getEdgeStyles().getDash());
        edgeStyles.addProperty("borderRadius", edge.getEdgeStyles().getBorderRadious());
        edgeStyles.addProperty("zIndex", edge.getEdgeStyles().getzIndex());
        edgeData.add("edgeStyles", edgeStyles);

        JsonArray verticesArray = new JsonArray();
        for (Vertex vertex : edge.getVertices()) {
            JsonObject vertexObj = new JsonObject();
            vertexObj.addProperty("x", vertex.getX());
            vertexObj.addProperty("y", vertex.getY());
            verticesArray.add(vertexObj);
        }
        edgeData.add("vertices", verticesArray);

        getElement().callJsFunction(
            "drawBasicEdge", edgeData.toString() 
        );

        this.basicEdges.add(edge);
    }
    
    /**
    * Sets the style for an edge in the graph.
    *
    * @param id The unique identifier of the edge whose style needs to be modified.
    * @param style The style property to be changed.
    * @param value The value to be applied to the style property.
    *
    * This method calls a JavaScript function (`setEdgeStyle`) to apply the style changes to the specified edge.
    */
    public void setEdgeStyle(String id, String style, String value){
        this.getElement().callJsFunction("setEdgeStyle", id, style, value);
    }
    
    /*
    * End of methods to manipulate edges
    */

    /*
    * These methods will be deleted very soon and implemented in Java, because they cover business rules that are not necessary or of interest. 
    */

    public void adjustNodeWidth(String id, int reserveSpace, int childSpacing, int heightIncrease){
        this.getElement().callJsFunction("adjustNodeWidth", id, reserveSpace, childSpacing, heightIncrease);
    }
    
    public void executeTree(String containerId, int spacing){
        this.getElement().callJsFunction("executeTree", containerId, spacing);
    }
    
    public void orderChildrenByName(String idContainer){
        this.getElement().callJsFunction("orderChildrenByName", idContainer);
    }
    
    public void adjustNodeHeight(String id, int childSpacing){
        this.getElement().callJsFunction("adjustNodeHeight", id, childSpacing);
    }
    
    public void centerChildrenHorizontally(String id, int startX, int childSpacing){
        this.getElement().callJsFunction("centerChildrenHorizontally", id, startX, childSpacing);
    }
    
    public void centerChildrenVertically(String id, int childSpacing){
        this.getElement().callJsFunction("centerChildrenVertically", id, childSpacing);
    }
    
    public void establishHierarchyThroughEdges(){
        this.getElement().callJsFunction("establishHierarchyThroughEdges");
    }
    
    public void setPositionAbsoluteParent(List<String> parentsId) {
        StringBuilder jsonArray = new StringBuilder("[");
        for (int i = 0; i < parentsId.size(); i++) {
            jsonArray.append("\"").append(parentsId.get(i)).append("\"");
            if (i < parentsId.size() - 1) {
                jsonArray.append(","); 
            }
        }
        jsonArray.append("]");
        this.getElement().callJsFunction("setPositionAbsoluteParent", jsonArray.toString());
    }
    
    public void updateNodesLabelState(){
        getElement().callJsFunction("updateNodesLabelState");
    }
    
    public void updateNodesLabelColor(){
        getElement().callJsFunction("updateNodesLabelColor");
    }
    
    /*
    * End of  methods that will be deleted very soon and implemented in Java. 
    */
    
    /*
    * Events of X6
    * These methods must be initialized if you need the listeners for these events to work.
    */
    
    public void initEventCellSelected() {
        getElement().callJsFunction("eventCellSelected");
    }

    public void initEventNodesConnected() {
        getElement().callJsFunction("eventNodesConnected");
    }

    public void initEventGetNodeNewPosition() {
        getElement().callJsFunction("eventGetNodeNewPosition");
    }

    public void initEventGetNodeBackgroundNewDimensions() {
        getElement().callJsFunction("eventGetNodeBackgroundNewDimensions");
    }

    public void initEventResizeNodeBackgroundDblClick() {
        getElement().callJsFunction("eventResizeNodeBackgroundDblClick");
    }
    
    public void initEventNodeChanged(){
        getElement().callJsFunction("eventNodeChanged");
    }
    
    public void initEventConfigureZIndexControls() {
    getElement().callJsFunction("configureZIndexControls");
}

    public void initEventContextMenu() {
        getElement().callJsFunction("eventContextMenu");
    }

    public void initEventResizeNode() {
        getElement().callJsFunction("eventResizeNode");
    }

    public void initEventCellUnselect() {
        getElement().callJsFunction("eventCellUnselect");
    }

    public void initEventAddNodeButtonRemove() {
        getElement().callJsFunction("eventAddNodeButtonRemoveTool");
    }

    public void initEventRemoveNodeButtonRemoveTool() {
        getElement().callJsFunction("eventRemoveNodeButtonRemoveTool");
    }

    public void initEventAddEdgeVerticesTool() {
        getElement().callJsFunction("eventAddEdgeVerticesTool");
    }
    
    public void initEventAddEdgeButtonRemoveTool(){
        getElement().callJsFunction("eventAddEdgeButtonRemoveTool");
    }

    public void initEventRemoveEdgeTools() {
        getElement().callJsFunction("eventRemoveEdgeTools");
    }
    
    public void activateContextMenu(){
        getElement().callJsFunction("activateContextMenu");
    }
    
    public void initEventEdgeDblclick(){
        getElement().callJsFunction("eventDblClickEdge");
    }
    
    public void initEventEdgeChanged(){
        getElement().callJsFunction("eventEdgeChanged");
    }
    
    public void initEventCellRemoved(){
        getElement().callJsFunction("eventCellRemoved");
    }

    /*
    * End of X6 events
    */
    
    
    /*
    * Other methods 
    */
    
    /*
    * Creates a ghost node in the middle of the canvas, it is used to organize the canvas
    */
    public void addGhost(){
        getElement().callJsFunction("createGhost");
    }
    
    /**
    * Exports the current graph as a JPEG image.
    *
    * The function handles the conversion of the graph into a JPEG format and download the image.
    *
    * @param filename the desired name for the exported JPEG file.
    */
    public void exportGraphAsJPEG(String filename){
        this.getElement().callJsFunction("exportGraphToJPEG", filename);
    }
    
    /*
    * End of other methods
    */
    
    
    /*
    * Listeners
    */
    
    public Registration addNodesConnectedListener(ComponentEventListener<EdgeCreatedEvent> listener) {
        return addListener(EdgeCreatedEvent.class, listener);
    }
    
    public Registration addGraphCreatedListener(ComponentEventListener<GraphCreatedEvent> listener) {
        Registration registration = addListener(GraphCreatedEvent.class, listener);
        this.lstListeners.add(registration);
        return registration;
    }
    
    public Registration addGraphCleanedListener(ComponentEventListener<GraphCleanedEvent> listener) {
        Registration registration = addListener(GraphCleanedEvent.class, listener);
        lstListeners.add(registration);
        return registration;
    }
    
    public Registration addGraphRefreshedListener(ComponentEventListener<GraphRefresheddEvent> listener) {
        Registration registration = addListener(GraphRefresheddEvent.class, listener);
        lstListeners.add(registration);
        return registration;
    }
    
    public Registration addBringToFrontCellListener(ComponentEventListener<BringToFrontEvent> listener){
        return addListener(BringToFrontEvent.class, listener);
    }
    
    public Registration addSendToBackCellListener(ComponentEventListener<SendToBackEvent> listener){
        return addListener(SendToBackEvent.class, listener);
    }
    
    public Registration addNodeChangedListener(ComponentEventListener<NodeChangedEvent> listener){
        return addListener(NodeChangedEvent.class, listener);
    }
   
    public Registration addNodeMovedListener(ComponentEventListener<NodeMovedEvent> listener) {
        return addListener(NodeMovedEvent.class, listener);
    }
    
    public Registration addNodeBackgroundResizedListener(ComponentEventListener<NodeBackgroundResizedEvent> listener) {
        return addListener(NodeBackgroundResizedEvent.class, listener);
    }

    public Registration addCellSelectedListener(ComponentEventListener<CellSelectedEvent> listener) {
        return addListener(CellSelectedEvent.class, listener);
    }
    
    public Registration addCellUnselectedListener(ComponentEventListener<CellUnselectedEvent> listener) {
        return addListener(CellUnselectedEvent.class, listener);
    }
    
    public Registration addEdgeDblClickListener(ComponentEventListener<EdgeDblClickEvent> listener) {
        return addListener(EdgeDblClickEvent.class, listener);
    }
    
    public Registration addEdgeChangedListener(ComponentEventListener<EdgeChangedEvent> listener) {
        return addListener(EdgeChangedEvent.class, listener);
    }
    
    public Registration addCellRemovedListener(ComponentEventListener<CellRemovedEvent> listener) {
        return addListener(CellRemovedEvent.class, listener);
    }

    /*
    * End of Listeners
    */
    
    /**
     * Removes listeners.
     */
    public void removeListeners() {
        lstListeners.forEach(item -> item.remove());
        lstListeners = new ArrayList();
    }
    
    /*
    * Getter and Setter of AntvX6 
    */

    public X6NodeBackground getNodeBackground() {
        return nodeBackground;
    }

    public void setNodeBackground(X6NodeBackground nodeBackground) {
        this.nodeBackground = nodeBackground;
    }
    
    public List<X6Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<X6Node> nodes) {
        this.nodes = nodes;
    }
    
    public List<X6Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<X6Edge> edges) {
        this.edges = edges;
    }

    public List<X6EdgeBasic> getBasicEdges() {
        return basicEdges;
    }

    public void setBasicEdges(List<X6EdgeBasic> basicEdges) {
        this.basicEdges = basicEdges;
    }
    
    public List<X6NodeText> getTextNodes() {
        return textNodes;
    }

    public void setTextNodes(List<X6NodeText> textNodes) {
        this.textNodes = textNodes;
    }
    
    public X6Node getNodeById(String id) {
        for (X6Node node : nodes) {
            if (node.getId().equals(id))
                return node; 
        }
        return null;
    }
    
    public X6Edge getEdgeById(String id){
        for(X6Edge edge: edges){
            if(edge.getId().equals(id))
                return edge;
        }
        return null;
    }
    
    /*
    * End of Getter and Setter of AntvX6 
    */
    
    /*
    * Events of X6
    */
    
    /**
    * Event fired when a graph has been created.
    */
    @DomEvent("graph-created")
    public static class GraphCreatedEvent extends ComponentEvent<AntvX6> {
        private final String status;
        
        public GraphCreatedEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.status") String status) {
            super(source, fromClient);
            this.status = status;
        }
        
        public String getStatus(){
            return status;
        }
    }
    
    /**
    * Event fired when a graph has been cleaned.
    */
    @DomEvent("graph-cleaned")
    public static class GraphCleanedEvent extends ComponentEvent<AntvX6> {
        private final String status;
        
        public GraphCleanedEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.state") String status) {
            super(source, fromClient);
            this.status = status;
        }
        
        public String getStatus(){
            return status;
        }
    }
    
    /**
    * Event fired when a graph has been refreshed.
    */
    @DomEvent("graph-refreshed")
    public static class GraphRefresheddEvent extends ComponentEvent<AntvX6> {
        private final String status;
        
        public GraphRefresheddEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.state") String status) {
            super(source, fromClient);
            this.status = status;
        }
        
        public String getStatus(){
            return status;
        }
    }
    
    /**
    * Event fired when a node has been moved.
    */
    @DomEvent("node-moved")
    public static class NodeMovedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final double x;
        private final double y;

        public NodeMovedEvent(AntvX6 source, boolean fromClient,
                              @EventData("event.detail.node.id") String id,
                              @EventData("event.detail.node.x") double x,
                              @EventData("event.detail.node.y") double y) {
            super(source, fromClient);
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public String getId() {
            return id;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
    
    /**
    * Event fired when a node background has been resized.
    */
    @DomEvent("background-resized")
    public static class NodeBackgroundResizedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final double width;
        private final double height;

        public NodeBackgroundResizedEvent(AntvX6 source, boolean fromClient,
                              @EventData("event.detail.node.id") String id,
                              @EventData("event.detail.node.width") double width,
                              @EventData("event.detail.node.height") double height) {
            super(source, fromClient);
            this.id = id;
            this.width = width;
            this.height = height;
        }
        
        public String getId(){
            return id;
        }
        
        public double getWidth(){
            return width;
        }
        
        public double getHeight(){
            return height;
        }
    }
    
    @DomEvent("bring-to-front")
    public static class BringToFrontEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final int zIndex;

        public BringToFrontEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.cell.id") String id,
                                @EventData("event.detail.cell.zIndex") int zIndex){
            super(source, fromClient);
            this.id = id;
            this.zIndex = zIndex;
        }
        
        public String getId() {
            return id;
        }
        
        public int getZIndex(){
            return zIndex;
        }
    }
    
    @DomEvent("send-to-back")
    public static class SendToBackEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final int zIndex;

        public SendToBackEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.cell.id") String id,
                                @EventData("event.detail.cell.zIndex") int zIndex){
            super(source, fromClient);
            this.id = id;
            this.zIndex = zIndex;
        }
        
        public String getId() {
            return id;
        }
        
        public int getZIndex(){
            return zIndex;
        }
    }
    
    @DomEvent("node-changed")
    public static class NodeChangedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final double x;
        private final double y;
        private final double width;
        private final double height;
        private final String newLabel;

        public NodeChangedEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.node.id") String id,
                                @EventData("event.detail.node.x") Double x,
                                @EventData("event.detail.node.y") Double y,
                                @EventData("event.detail.node.width") Double width,
                                @EventData("event.detail.node.height") Double height,
                                @EventData("event.detail.node.newLabel") String newLabel){
            super(source, fromClient);
            this.id = id;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.newLabel = newLabel;
        }
        
        public String getId() {
            return id;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
        
        public String getNewLabel(){
            return newLabel;
        }
    }
    
    @DomEvent("edge-changed")
    public static class EdgeChangedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final String idSource;
        private final String idTarget;
        private final String verticesJson; 

        public EdgeChangedEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.edge.id") String id,
                                @EventData("event.detail.edge.idSource") String idSource,
                                @EventData("event.detail.edge.idTarget") String idTarget,
                                @EventData("event.detail.edge.vertices") String verticesJson) {
            super(source, fromClient);
            this.id = id;
            this.idSource = idSource;
            this.idTarget = idTarget;
            this.verticesJson = verticesJson; 
        }

        public String getId() {
            return id;
        }

        public String getIdSource() {
            return idSource;
        }

        public String getIdTarget() {
            return idTarget;
        }

        public String getVerticesJson() {
            return verticesJson; 
        }
    }

    @DomEvent("edge-dblclick")
    public static class EdgeDblClickEvent extends ComponentEvent<AntvX6> {
        private final String edgeId;
        private final String idSource;
        private final String idTarget;

        public EdgeDblClickEvent(AntvX6 source, boolean fromClient,
                                 @EventData("event.detail.edge.id") String edgeId,
                                 @EventData("event.detail.edge.idSource") String idSource,
                                 @EventData("event.detail.edge.idTarget") String idTarget) {
            super(source, fromClient);
            this.edgeId = edgeId;
            this.idSource = idSource;
            this.idTarget = idTarget;
        }

        public String getEdgeId() {
            return edgeId;
        }

        public String getIdSource() {
            return idSource;
        }

        public String getIdTarget() {
            return idTarget;
        }

    }

    /**
    * Event fired when a cell(node or edge) has been selected.
    */
    @DomEvent("cell-selected")
    public static class CellSelectedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final String cellType;
        private final int numberCells;
        
        public CellSelectedEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.cell.id") String id,
                                @EventData("event.detail.cell.cellType") String cellType,
                                @EventData("event.detail.cell.numberCells") int numberCells){
            super(source, fromClient);
            this.id = id;
            this.cellType = cellType;
            this.numberCells = numberCells;
        }

        public String getId() {
            return id;
        }

        public String getCellType() {
            return cellType;
        }

        public int getNumberCells() {
            return numberCells;
        }    
    }
    
    /**
    * Event fired when a cell(node or edge) has been unselected.
    */
    @DomEvent("cell-unselected")
    public static class CellUnselectedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final String state;

        public CellUnselectedEvent(AntvX6 source, boolean fromClient,
                                 @EventData("event.detail.cell.id") String id,
                                 @EventData("event.detail.cell.state") String state) {
            super(source, fromClient);
            this.id = id;
            this.state = state;
        }

        public String getId() {
            return id;
        }

        public String getState() {
            return state;
        }
    }
    
    @DomEvent("cell-removed")
    public static class CellRemovedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final String typeCell;

        public CellRemovedEvent(AntvX6 source, boolean fromClient,
                                @EventData("event.detail.cell.id") String id,
                                @EventData("event.detail.cell.typeCell") String typeCell) {
            super(source, fromClient);
            this.id = id;
            this.typeCell = typeCell;
        }

        public String getId() {
            return id;
        }

        public String getTypeCell() {
            return typeCell;
        }
    }

  
    /**
    * Event fired when a edge has been created.
    */
    @DomEvent("edge-created")
    public static class EdgeCreatedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final String idSource;
        private final String idTarget;

        public EdgeCreatedEvent(AntvX6 source, boolean fromClient,
                                  @EventData("event.detail.edge.id") String id,
                                  @EventData("event.detail.edge.idSource") String idSource,
                                  @EventData("event.detail.edge.idTarget") String idTarget) {
            super(source, fromClient);
            this.id = id;
            this.idSource = idSource;
            this.idTarget = idTarget;
        }

        public String getId() {
            return id;
        }

        public String getIdSource() {
            return idSource;
        }

        public String getIdTarget() {
            return idTarget;
        }
    }
    
    /*
    * End of events of X6
    */
  
}
