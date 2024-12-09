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

import com.neotropic.flow.component.antvx6.objects.Geometry;
import com.neotropic.flow.component.antvx6.objects.X6Edge;
import com.neotropic.flow.component.antvx6.objects.X6Node;
import com.neotropic.flow.component.antvx6.objects.X6NodeBackground;
import com.neotropic.flow.component.antvx6.constants.X6Constants;
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
    private static final String PROPERTY_KUWAIBA_GRAPH = "kuwaiba_graph";
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
        this.edges = new ArrayList();
        this.lstListeners = new ArrayList();
    }        
    
    /**
    * Retrieves the ID of the node background.
     * @return node background id.
    */
    public String getNodeBackgroundId(){
        return this.getElement().getAttribute(PROPERTY_GRAPH_NODE_BACKGROUND_ID);
    }
    
     /**
    * Set the Kuwaiba graph.
    */
    public void setKuwaibaGraph(int kuwaibaGraph){
        this.getElement().setProperty(PROPERTY_KUWAIBA_GRAPH ,kuwaibaGraph);
    }
    
    /**
    * Set the ID of the node background.
    */
    public void setNodeBackgroundId(String idBackground){
        this.getElement().setProperty(PROPERTY_GRAPH_NODE_BACKGROUND_ID, idBackground);
    }
    
    /**
    * Retrieves the current zoom level of the graph.
     * @return graph zoom.
    */
    public String getGraphZoom(){
        return this.getElement().getAttribute(PROPERTY_GRAPH_ZOOM);
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
    * @param toggle true if the node's label color has been toggled, 
    *                false if it has not been toggled.
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
    * @param zoom the zoom level as a double value (e.g., 1.0 for 100%).
    */
    public void setGraphZomm(double zoom){
        this.getElement().setProperty(PROPERTY_GRAPH_ZOOM, zoom);
    }
    
    /**
    * Clears all nodes and edges from the graph.
    * 
    * This method removes all nodes and edges from the local lists,
    * effectively resetting the graph's state. It also calls the
    * JavaScript method `cleanGraph()` to ensure that the graph
    * is cleared in the frontend representation.
    */
    public void cleanGraph(){
        this.nodes.clear();
        this.edges.clear();
        getElement().executeJs("this.cleanGraph();");
    }
    
    /**
    * Refreshes the graph.
    * 
    * This method calls the JavaScript method `refreshGraph()`,
    * which is responsible for re-rendering the graph.It creates
    * a backup of the current graph, clears it, and then 
    * re-populates it using the backup data.
    */
    public void refreshGraph(){
        this.getElement().executeJs("this.refreshGraph();");
    }
    
    /**
    * Draws the node background for a graph.
    * 
    * This method constructs a background object for a node using the provided
    * `X6NodeBackground` instance. It calls the JavaScript function `createBackground`
    * to create the visual representation of the background on the frontend.
    * This method also updates the internal reference to the node background.
    * 
    * @param background the X6NodeBackground object
    */
    public void drawNodeBackground(X6NodeBackground background) {
        getElement().executeJs(
            "this.createBackground({ " +
            "id: $0, " +
            "geometry: { " +
            "  coordinates: { x: $1, y: $2 }, " +
            "  dimensions: { width: $3, height: $4 } " +
            "}, " +
            "shape: $5, " +
            "imgUrl: $6, " +
            "movable: $7, " +
            "parentId: $8, " +
            "labelText: $9, " +
            "labelStyles: { " +
            "  labelTextColor: $10, " +
            "  labelFontSize: $11, " +
            "  labelFontFamily: $12, " +
            "  labelPosition: $13, " +
            "  labelVisibility: $14 " +
            "}, " +
            "nodeStyles: { " +
            "  borderRadius: $15, " +
            "  fillColor: $16, " +
            "  strokeColor: $17, " +
            "  strokeWidth: $18, " +
            "  dash: $19, " +
            "  zIndex: $20 " +
            "}})",
            background.getId(),
            background.getGeometry().getCoordinates().getX(),
            background.getGeometry().getCoordinates().getY(),
            background.getGeometry().getDimensions().getWidth(),
            background.getGeometry().getDimensions().getHeight(),
            background.getShape(),
            background.getImgUrl(),
            background.isMovable(),
            background.getParentId(),
            background.getLabelText(),
            background.getLabelStyles().getLabelTextColor(),
            background.getLabelStyles().getLabelFontSize(),
            background.getLabelStyles().getLabelFontFamily(),
            background.getLabelStyles().getLabelPosition(),
            background.getLabelStyles().getLabelVisibility(),
            background.getNodeStyles().getBorderRadius(),
            background.getNodeStyles().getFillColor(),
            background.getNodeStyles().getStrokeColor(),
            background.getNodeStyles().getStrokeWidth(),
            background.getNodeStyles().getDash(),
            background.getNodeStyles().getzIndex()
        );
        this.nodeBackground = background;
    }


    
    /**
    * Removes the node background of the the graph.
    * 
    * This method calls the JavaScript function `removeBackground()`, which 
    * handles the removal of the node background from the frontend representation.
    */
    public void removeNodeBackground(){
        getElement().executeJs("this.removeBackground();");
        this.nodeBackground.setId("");
        this.nodeBackground.setGeometry(new Geometry(0, 0 , 0, 0));
        this.nodeBackground.setImgUrl("");
        this.nodeBackground.setShape(X6Constants.SHAPE_IMAGE);
    }
    
    /**
    * Draws a visual representation of a node in the graph.
    *
    * This method constructs and sends the necessary data to the frontend
    * to create a node. It calls the JavaScript function `drawNode`.
    * 
    * @param node the X6Node object to be draw.
    */
    public void drawNode(X6Node node) {
        getElement().executeJs(
            "this.drawNode({ " +
            "id: $0, " +
            "geometry: { " +
            "  coordinates: { x: $1, y: $2 }, " +
            "  dimensions: { width: $3, height: $4 } " +
            "}, " +
            "shape: $5, " +
            "imgUrl: $6, " +
            "movable: $7, " +
            "parentId: $8, " +
            "labelText: $9, " +
            "labelStyles: { " +
            "  labelTextColor: $10, " +
            "  labelFontSize: $11, " +
            "  labelFontFamily: $12, " +
            "  labelPosition: $13, " +
            "  labelVisibility: $14 " +
            "}, " +
            "nodeStyles: { " +
            "  borderRadius: $15, " +
            "  fillColor: $16, " +
            "  strokeColor: $17, " +
            "  strokeWidth: $18, " +
            "  dash: $19, " +
            "  zIndex: $20 " +
            "}, " +
            "port: $21 " +
            "})",
            node.getId(),
            node.getGeometry().getCoordinates().getX(),
            node.getGeometry().getCoordinates().getY(),
            node.getGeometry().getDimensions().getWidth(),
            node.getGeometry().getDimensions().getHeight(),
            node.getShape(),
            node.getImgUrl(),
            node.isMovable(),
            node.getParentId(),
            node.getLabelText(),
            node.getLabelStyles().getLabelTextColor(),
            node.getLabelStyles().getLabelFontSize(),
            node.getLabelStyles().getLabelFontFamily(),
            node.getLabelStyles().getLabelPosition(),
            node.getLabelStyles().getLabelVisibility(),
            node.getNodeStyles().getBorderRadius(),
            node.getNodeStyles().getFillColor(),
            node.getNodeStyles().getStrokeColor(),
            node.getNodeStyles().getStrokeWidth(),
            node.getNodeStyles().getDash(),
            node.getNodeStyles().getzIndex(),
            node.isPort()
        );
        this.nodes.add(node);
    }


    /**
    * Draws a visual representation of a text for a node in the graph.
    *
    * This method constructs and sends the necessary data to the frontend
    * to create a text node. It calls the JavaScript function `drawText`.
    * 
    */
    public void drawText(X6NodeText nodeText) {
        getElement().executeJs(
            "this.drawText({ " +
            "id: $0, " +
            "geometry: { " +
            "  coordinates: { x: $1, y: $2 }, " +
            "  dimensions: { width: $3, height: $4 } " +
            "}, " +
            "shape: $5, " +
            "imgUrl: $6, " +
            "movable: $7, " +
            "parentId: $8, " +
            "labelText: $9, " +
            "labelStyles: { " +
            "  labelTextColor: $10, " +
            "  labelFontSize: $11, " +
            "  labelFontFamily: $12, " +
            "  labelPosition: $13, " +
            "  labelVisibility: $14 " +
            "}, " +
            "nodeStyles: { " +
            "  borderRadius: $15, " +
            "  fillColor: $16, " +
            "  strokeColor: $17, " +
            "  strokeWidth: $18, " +
            "  dash: $19, " +
            "  zIndex: $20 " +
            "}, " +
            "labelPositionRelative: $21 " +
            "})",
            nodeText.getId(),
            nodeText.getGeometry().getCoordinates().getX(),
            nodeText.getGeometry().getCoordinates().getY(),
            nodeText.getGeometry().getDimensions().getWidth(),
            nodeText.getGeometry().getDimensions().getHeight(),
            nodeText.getShape(),
            nodeText.getImgUrl(),
            nodeText.isMovable(),
            nodeText.getParentId(),
            nodeText.getLabelText(),
            nodeText.getLabelStyles().getLabelTextColor(),
            nodeText.getLabelStyles().getLabelFontSize(),
            nodeText.getLabelStyles().getLabelFontFamily(),
            nodeText.getLabelStyles().getLabelPosition(),
            nodeText.getLabelStyles().getLabelVisibility(),
            nodeText.getNodeStyles().getBorderRadius(),
            nodeText.getNodeStyles().getFillColor(),
            nodeText.getNodeStyles().getStrokeColor(),
            nodeText.getNodeStyles().getStrokeWidth(),
            nodeText.getNodeStyles().getDash(),
            nodeText.getNodeStyles().getzIndex(),
            nodeText.getLabelPositionRelative()
            );
        this.textNodes.add(nodeText);
    }


    
    /**
    * Adjusts the width of a node based on its children.
    * This method calculates the necessary width for the specified node by considering
    * the reserved space, spacing between child nodes, and any additional height required.
    *
    * @param id - The unique identifier of the node whose width is to be adjusted.
    * @param reserveSpace - The amount of space to reserve for the initial and final child.
    * @param childSpacing - The spacing to apply between each child node.
    * @param heightIncrease - The additional height to add to the node's dimensions.
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
    
    /**
    * Centers the children of a specified parent node horizontally within that parent node.
    * This method calculates the starting position and applies the specified spacing 
    * between child nodes to ensure they are evenly centered.
    *
    * @param id - The unique identifier of the parent node whose children will be centered.
    * @param startX - The starting X position from which to center the child nodes.
    * @param childSpacing - The spacing to apply between each child node.
    */
    public void centerChildrenHorizontally(String id, int startX, int childSpacing){
        this.getElement().callJsFunction("centerChildrenHorizontally", id, startX, childSpacing);
    }
    
    public void centerChildrenVertically(String id, int childSpacing){
        this.getElement().callJsFunction("centerChildrenVertically", id, childSpacing);
    }
    
    /**
    * Establishes a parent-child relationship between two nodes in the graph.
    *
    * This method constructs and sends the necessary data to the frontend
    * to link a child node to its parent node. It calls the JavaScript function `setFather`.
    *
    * @param idParent the id of the parent node
    * @param idChild the id of the child node
    */
    public void setParent(String idParent, String idChild){
        this.getElement().callJsFunction("setParent", idParent, idChild);
    }
    
    public void establishHierarchyThroughEdges(){
        this.getElement().callJsFunction("establishHierarchyThroughEdges");
    }
    
    /**
    * Draws a visual representation of an edge in the graph.
    *
    * This method constructs and sends the necessary data to the frontend
    * to create an edge of the object view. It calls the JavaScript function
    * `drawEdge`.
    * 
    * @param edge the X6Edge object to be draw.
    */
    public void drawEdge(X6Edge edge) {
        getElement().executeJs(
            "this.drawEdge({ " +
            "id: $0, idSource: $1, idTarget: $2, label: $3 " +
            "})",
            edge.getId(),
            edge.getIdSource(),
            edge.getIdTarget(),
            edge.getLabel()
        );
        this.edges.add(edge);
    }
    
    /**
    * Selects a specified node in the graph.
    *
    * This method constructs and sends the necessary data to the frontend
    * to visually select a node. It calls the JavaScript function
    * `selectNode`.
    * 
    * @param id id of the X6Node to be select.
    */
    public void selectNode(String id) {
        getElement().callJsFunction("selectNode", id);
    }

    
    /**
    * Updates the label state of all nodes in the graph.
    *
    * This method calls the JavaScript function `updateNodesLabelState()`,
    * which updates the visibility or state of labels for all nodes in the
    * graph.
    */
    public void updateNodesLabelState(){
        getElement().executeJs("this.updateNodesLabelState();");
    }
    
    /**
    * Updates the label color of all nodes in the graph.
    *
    * This method calls the JavaScript function `updateNodesLabelColor()`,
    * which modifies the color of labels for all nodes in the graph. 
    */
    public void updateNodesLabelColor(){
        getElement().executeJs("this.updateNodesLabelColor();");
    }
    
    /**
    * Sets the absolute positions of parent nodes based on their widths.
    * 
    * This method positions parent nodes, which may have multiple children, 
    * starting from a specified horizontal offset. 
    *
    * @param parentsId - A JSON string containing the unique identifiers of the parent nodes to be positioned.
    */
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
  

    /**
    * Exports the current graph as a JPEG image.
    *
    * This method calls the JavaScript function `exportGraphToJPEG`,
    * The function handles the conversion of the graph into a JPEG format and download the image.
    *
    * @param filename the desired name for the exported JPEG file.
    */
    public void exportGraphAsJPEG(String filename){
        this.getElement().callJsFunction("exportGraphToJPEG", filename);
    }
    
    /**
    * Registers a listener for the EdgeCreatedEvent.
    *
    * @param listener the listener to be notified when an edge is created
    * @return a Registration object that can be used to unregister the listener
    */
    public Registration addEdgeCreatedListener(ComponentEventListener<EdgeCreatededEvent> listener) {
        return addListener(EdgeCreatededEvent.class, listener);
    }
    
    /**
    * Registers a listener for the GraphCreatedEvent.
    *
    * @param listener the listener to be notified when a graph is created
    * @return a Registration object that can be used to unregister the listener
    */
    public Registration addGraphCreatedListener(ComponentEventListener<GraphCreatedEvent> listener) {
        Registration registration = addListener(GraphCreatedEvent.class, listener);
        this.lstListeners.add(registration);
        return registration;
    }
    
    /**
     * Registers a listener for the GraphCleanedEvent.
     *
     * @param listener the listener to be notified when a graph is cleaned
     * @return a Registration object that can be used to unregister the listener
     */
    public Registration addGraphCleanedListener(ComponentEventListener<GraphCleanedEvent> listener) {
        Registration registration = addListener(GraphCleanedEvent.class, listener);
        lstListeners.add(registration);
        return registration;
    }
    
    /**
     * Registers a listener for the GraphRefresheddEvent.
     *
     * @param listener the listener to be notified when a graph is refreshed
     * @return a Registration object that can be used to unregister the listener
     */
    public Registration addGraphRefreshedListener(ComponentEventListener<GraphRefresheddEvent> listener) {
        Registration registration = addListener(GraphRefresheddEvent.class, listener);
        lstListeners.add(registration);
        return registration;
    }
    
    /**
     * Registers a listener for the NodeMovedEvent.
     *
     * @param listener the listener to be notified when a node is moved
     * @return a Registration object that can be used to unregister the listener
     */
    public Registration addNodeMovedListener(ComponentEventListener<NodeMovedEvent> listener) {
        return addListener(NodeMovedEvent.class, listener);
    }
    
    /**
     * Registers a listener for the NodeBackgroundResizedEvent.
     *
     * @param listener the listener to be notified when a node's background is resized
     * @return a Registration object that can be used to unregister the listener
     */
    public Registration addNodeBackgroundResizedListener(ComponentEventListener<NodeBackgroundResizedEvent> listener) {
        return addListener(NodeBackgroundResizedEvent.class, listener);
    }

    /**
     * Registers a listener for the CellSelectedEvent.
     *
     * @param listener the listener to be notified when a cell (node or edge) is selected
     * @return a Registration object that can be used to unregister the listener
     */
    public Registration addCellSelectedListener(ComponentEventListener<CellSelectedEvent> listener) {
        return addListener(CellSelectedEvent.class, listener);
    }
    
    /**
     * Removes listeners.
     */
    public void removeListeners() {
        lstListeners.forEach(item -> item.remove());
        lstListeners = new ArrayList();
    }

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

    public List<X6NodeText> getTextNodes() {
        return textNodes;
    }

    public void setTextNodes(List<X6NodeText> textNodes) {
        this.textNodes = textNodes;
    }
    
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
    
    /**
    * Event fired when a cell(node or edge) has been selected.
    */
    @DomEvent("cell-selected")
    public static class CellSelectedEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final String cellType;

        public CellSelectedEvent(AntvX6 source, boolean fromClient,
                                 @EventData("event.detail.cell.id") String id,
                                 @EventData("event.detail.cell.cellType") String cellType) {
            super(source, fromClient);
            this.id = id;
            this.cellType = cellType;
        }

        public String getId() {
            return id;
        }

        public String getCellType() {
            return cellType;
        }
    }

    /**
    * Event fired when a edge has been created.
    */
    @DomEvent("edge-created")
    public static class EdgeCreatededEvent extends ComponentEvent<AntvX6> {
        private final String id;
        private final String idSource;
        private final String idTarget;

        public EdgeCreatededEvent(AntvX6 source, boolean fromClient,
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
  
}
