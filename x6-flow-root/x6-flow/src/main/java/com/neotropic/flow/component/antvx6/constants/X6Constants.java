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
    * The width of the graph in pixels.
    */
   public static final int GRAPH_WIDTH = 600;

   /**
    * The height of the graph in pixels.
    */
   public static final int GRAPH_HEIGHT = 600;

   /**
    * The background color of the graph.
    */
   public static final String GRAPH_BACKGROUND_COLOR = "#edf6f9";

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
    * The string identifier for cell node types.
    */
   public static final String CELL_NODE = "node";
}
