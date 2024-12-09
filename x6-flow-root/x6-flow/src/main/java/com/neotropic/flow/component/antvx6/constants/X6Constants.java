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
package com.neotropic.flow.component.antvx6.constants;

/**
 * Provides constants for configuring the Antv X6 graph instance and its objects.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>
 */
public class X6Constants {
    /**
    * The kuwaiba constant for any view with no events.
    */
    public static final int KUWAIBA_DEFAULT_VIEW = 0;

    /**
    * The kuwaiba constant for object view.
    */
    public static final int KUWAIBA_OBJECT_VIEW = 1;

     /**
    * The kuwaiba constant for fiber splitter view.
    */
    public static final int KUWAIBA_FIBER_SPLITTER_VIEW = 2;

    /**
    * The kuwaiba constant for splice box view.
    */
    public static final int KUWAIBA_SPLICE_BOX_VIEW = 3;

    /**
    * The kuwaiba constant for physical path view.
    */
    public static final int KUWAIBA_PHYSICAL_PATH_VIEW = 4;

    /**
    * The kuwaiba constant for physical tree view.
    */
    public static final int KUWAIBA_PHYSICAL_TREE_VIEW = 5;

    /**
    * The kuwaiba constant for physical tree view.
    */
    public static final int KUWAIBA_TOPOLOGY_DESIGNER = 6;

    /**
    * The width of the graph in pixels.
    */
    public static final int GRAPH_WIDTH = 600;

    /**
     * The height of the graph in pixels.
     */
    public static final int GRAPH_HEIGHT = 600;

    /**
     * The width of the graph in pixels for fiber splitter view.
     */
    public static final int GRAPH_WIDTH_FIBER_SPLITTER = 1500;

    /**
     * The height of the graph in pixels for fiber splitter view.
     */
    public static final int GRAPH_HEIGHT_FIBER_SPLITTER = 700;

    /**
     * The width of the graph in pixels for splice box view.
     */
    public static final int GRAPH_WIDTH_SPLICE_BOX = 1500;

    /**
     * The height of the graph in pixels for splice box view.
     */
    public static final int GRAPH_HEIGHT_SPLICE_BOX = 700;

    /**
     * The width of the graph in pixels for physical path view.
     */
    public static final int GRAPH_WIDTH_PHYSICAL_PATH = 1500;

    /**
     * The height of the graph in pixels for physical path view.
     */
    public static final int GRAPH_HEIGHT_PHYSICAL_PATH = 500;

    /**
    * The width of the graph in pixels for physical tree view.
    */
    public static final int GRAPH_WIDTH_PHYSICAL_TREE = 1500;

    /**
    * The height of the graph in pixels for physical tree view.
    */
    public static final int GRAPH_HEIGHT_PHYSICAL_TREE = 500;

    /**
    * The background color of the graph.
    */
    public static final String GRAPH_BACKGROUND_COLOR = "#f8f9fa";

    /**
    * Indicates whether the graph should display a grid.
    */
    public static final boolean GRAPH_GRID = true;

    /**
    * Indicates whether panning is enabled for the graph.
    */
    public static final boolean GRAPH_PANNING = false;

    /**
    * Indicates whether mouse wheel zooming is enabled for the graph.
    */
    public static final boolean GRAPH_MOUSE_WHEEL = true;

    /**
    * The zoom factor applied to the graph.
    */
    public static final double GRAPH_ZOOM = -0.1;

    /**
    * The string identifier for image shape types.
    */
    public static final String SHAPE_IMAGE = "image";

    /**
    * The string identifier for rect shape types.
    */
    public static final String SHAPE_RECT = "rect";

    /**
    * The string identifier for circle shape types.
    */
    public static final String SHAPE_CIRCLE = "circle";

    /**
    * The string identifier for ellipse shape types.
    */
    public static final String SHAPE_ELLIPSE = "ellipse";

    /**
    * The string identifier for text-block shape types.
    */
    public static final String SHAPE_TEXT_BLOCK = "text-block";

    /**
    * The string identifier for cell node types.
    */
    public static final String CELL_NODE = "node";

    /**
    * Represents the position of the label at the bottom of the node.
    */
    public static final String LABEL_NODE_POSITION_BOTTOM = "bottom";

    /**
    * Represents the default (center) position of the label on the node.
    */
    public static final String LABEL_NODE_POSITION_DEFAULT = "default";

    /*
    * Represents whether the node should be hidden
    */
    public static final String LABEL_NODE_HIDDEN = "hidden";

    /*
    * Represents whether the node should be visible
    */
    public static final String LABEL_NODE_VISIBLE = "visible";
   
    public static final String STYLE_STROKECOLOR = "strokeColor";
    public static final String STYLE_FILLCOLOR = "fillColor";
    public static final String STYLE_DASHED = "dashed";
    public static final String STYLE_ROUNDED = "rounded";
    public static final String STYLE_STROKEWIDTH = "strokeWidth";
    public static final String STYLE_FONTSIZE = "fontSize";
    public static final String STYLE_FONTCOLOR = "fontColor";
    public static final String STYLE_FONTFAMILY = "fontFamily";
}
