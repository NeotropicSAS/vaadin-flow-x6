/**
 * @license
 * Copyright 2010-2022 Neotropic SAS <contact@neotropic.co>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {LitElement, customElement, query, html, css, property } from 'lit-element';
import { Edge, Graph, Node } from '@antv/x6';
import{ Snapline }from'@antv/x6-plugin-snapline';
import { Transform } from '@antv/x6-plugin-transform';
import { Export } from '@antv/x6-plugin-export'; 
import { Selection } from '@antv/x6-plugin-selection'
import { Scroller } from '@antv/x6-plugin-scroller' 
import { MiniMap } from '@antv/x6-plugin-minimap';

/**
* Represents a 2D coordinate with x and y values.
*/
interface Coordinate{
  x: number;
  y: number;
}

/**
* Represents the dimensions of a node.
*/
interface Dimension{
  width: number;
  height: number;
}

/**
* Represents geometric information including coordinates and dimensions.
*/
interface Geometry {
  coordinates: Coordinate;
  dimensions: Dimension;
}

/**
 * represents the label styles of a edge in the X6 model
 */
interface X6EdgeLabelStyles {
  fillColor: string;
  fontColor: string;
  fontSize: number;
  fontFamily: string;
  strokeColor: string;
  strokeWidth: number;
  borderRadius: number;
}

/**
 * represents the styles of an edge in the X6 model
 */
interface X6EdgeStyles {
  strokeColor: string;
  strokeWidth: number;
  dash: number;
  borderRadius: number;
  zIndex: number;
}

/**
 * represents the label styles of a node in the X6 model
 */
interface X6NodeLabelStyles {
  fontColor: string;
  fontSize: number;
  fontFamily: string;
  labelPosition: string;
  visibility: string;
}

/**
 * represents the styles of a node in the X6 model
 */
interface X6NodeStyles{
  fillColor: string;
  strokeColor: string;
  strokeWidth: number;
  dash: string;
  borderRadius: number;
  zIndex : number;
}

/**
* Represents a cell in the graph.
*/
interface X6Cell{
  id: string;
  geometry: Geometry;
  tools : string[];
}

/**
* Represents a node in the graph, extending the base cell properties.
*/
interface X6AbstractNode extends X6Cell{
  shape: string;
  imgUrl: string;
  movable: boolean;
  parentId: string;
  label: string;
  styles : X6NodeStyles;
  labelStyles : X6NodeLabelStyles;
}

/**
* Represents a background node in the graph.
*/
interface X6NodeBackground extends X6AbstractNode{

}

/**
* Represents a node in the graph.
*/
interface X6Node extends X6AbstractNode{
  port: boolean;
}

/**
* Represents a text node in the graph.
*/
interface X6NodeText extends X6AbstractNode{
  // position respect to its parent node (top, bottom)
  labelPositionRelative: string;
}

/**
 * represents a vertex within an edge in the x6 model
 */
interface Vertex {
  x: number;
  y: number;
}

/**
* Represents an edge in a graph.
*/
interface X6Edge extends X6Cell{
  idSource: string;
  idTarget: string;
  vertices: string | Vertex[];
  labels: string | X6EdgeLabel[];
  styles: X6EdgeStyles;
}

/**
* Represents a label of an edge.
*/
interface X6EdgeLabel{
  label: string;
  distance: number;
  styles: X6EdgeLabelStyles;
}

/**
 * AntV X6 element.
 * @author Julian David Camacho Erazo <julian.camacho@kuwaiba.org>
 */
@customElement('x-6')
export class X6 extends LitElement {
  /**
   * X6 Styles
   * 
   * This static property defines the styles used for the X6 components.
   * 
   * If you need to utilize any additional plugins provided by X6, 
   * add their styles here. You can find the required styles in the 
   * node_modules folder of the project.
   */
  static styles = css`
    #contextMenu {
      display: none;
      position: absolute;
      background-color: white;
      border: 1px solid #ccc;
      padding: 0; 
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
      z-index: 9999;
      border-radius: 4px;
    }

    #contextMenu ul {
      list-style: none;
      padding: 0;
      margin: 0;
    }

    #contextMenu li {
      padding: 8px 16px;
      cursor: pointer;
      background-color: #f5f5f5;
      border-radius: 4px;
      border-bottom: 1px solid #ddd;  
      margin: 0;  
      transition: background-color 0.3s;
    }

    #contextMenu li:last-child {
      border-bottom: none; 
    }

    #contextMenu li:hover {
      background-color: #3f3d56;
      color: white;
    }

    #contextMenu li:active {
      background-color: #2980b9;
    }

    .x6-graph {
      position: relative;
      overflow: hidden;
      outline: none;
      touch-action: none;
    }
    .x6-graph-background,
    .x6-graph-grid,
    .x6-graph-svg {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
    }
    .x6-graph-background-stage,
    .x6-graph-grid-stage,
    .x6-graph-svg-stage {
      user-select: none;
    }
    .x6-graph.x6-graph-pannable {
      cursor: grab;
      cursor: -moz-grab;
      cursor: -webkit-grab;
    }
    .x6-graph.x6-graph-panning {
      cursor: grabbing;
      cursor: -moz-grabbing;
      cursor: -webkit-grabbing;
      user-select: none;
    }
    .x6-node {
      cursor: move;
    }
    .x6-node.x6-node-immovable {
      cursor: default;
    }
    .x6-node * {
      -webkit-user-drag: none;
    }
    .x6-node .scalable * {
      vector-effect: non-scaling-stroke;
    }
    .x6-node [magnet='true'] {
      cursor: crosshair;
      transition: opacity 0.3s;
    }
    .x6-node [magnet='true']:hover {
      opacity: 0.7;
    }
    .x6-node foreignObject {
      display: block;
      overflow: visible;
      background-color: transparent;
    }
    .x6-node foreignObject > body {
      position: static;
      width: 100%;
      height: 100%;
      margin: 0;
      padding: 0;
      overflow: visible;
      background-color: transparent;
    }
    .x6-edge .source-marker,
    .x6-edge .target-marker {
      vector-effect: non-scaling-stroke;
    }
    .x6-edge .connection {
      stroke-linejoin: round;
      fill: none;
    }
    .x6-edge .connection-wrap {
      cursor: move;
      opacity: 0;
      fill: none;
      stroke: #000;
      stroke-width: 15;
      stroke-linecap: round;
      stroke-linejoin: round;
    }
    .x6-edge .connection-wrap:hover {
      opacity: 0.4;
      stroke-opacity: 0.4;
    }
    .x6-edge .vertices {
      cursor: move;
      opacity: 0;
    }
    .x6-edge .vertices .vertex {
      fill: #1abc9c;
    }
    .x6-edge .vertices .vertex:hover {
      fill: #34495e;
      stroke: none;
    }
    .x6-edge .vertices .vertex-remove {
      cursor: pointer;
      fill: #fff;
    }
    .x6-edge .vertices .vertex-remove-area {
      cursor: pointer;
      opacity: 0.1;
    }
    .x6-edge .vertices .vertex-group:hover .vertex-remove-area {
      opacity: 1;
    }
    .x6-edge .arrowheads {
      cursor: move;
      opacity: 0;
    }
    .x6-edge .arrowheads .arrowhead {
      fill: #1abc9c;
    }
    .x6-edge .arrowheads .arrowhead:hover {
      fill: #f39c12;
      stroke: none;
    }
    .x6-edge .tools {
      cursor: pointer;
      opacity: 0;
    }
    .x6-edge .tools .tool-options {
      display: none;
    }
    .x6-edge .tools .tool-remove circle {
      fill: #f00;
    }
    .x6-edge .tools .tool-remove path {
      fill: #fff;
    }
    .x6-edge:hover .vertices,
    .x6-edge:hover .arrowheads,
    .x6-edge:hover .tools {
      opacity: 1;
    }
    .x6-highlight-opacity {
      opacity: 0.3;
    }
    .x6-cell-tool-editor {
      position: relative;
      display: inline-block;
      min-height: 1em;
      margin: 0;
      padding: 0;
      line-height: 1;
      white-space: normal;
      text-align: center;
      vertical-align: top;
      overflow-wrap: normal;
      outline: none;
      transform-origin: 0 0;
      -webkit-user-drag: none;
    }
    .x6-edge-tool-editor {
      border: 1px solid #275fc5;
      border-radius: 2px;
    }

    .x6-widget-snapline {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
      pointer-events: none;
    }
    .x6-widget-snapline-vertical,
    .x6-widget-snapline-horizontal {
      stroke: #2ecc71;
      stroke-width: 1px;
    }
    .x6-widget-transform {
      position: absolute;
      box-sizing: content-box !important;
      margin: -5px 0 0 -5px;
      padding: 4px;
      border: 1px dashed #000;
      border-radius: 5px;
      user-select: none;
      pointer-events: none;
    }
    .x6-widget-transform > div {
      position: absolute;
      box-sizing: border-box;
      background-color: #fff;
      border: 1px solid #000;
      transition: background-color 0.2s;
      pointer-events: auto;
      -webkit-user-drag: none;
      user-drag: none;
      /* stylelint-disable-line */
    }
    .x6-widget-transform > div:hover {
      background-color: #d3d3d3;
    }
    .x6-widget-transform-cursor-n {
      cursor: n-resize;
    }
    .x6-widget-transform-cursor-s {
      cursor: s-resize;
    }
    .x6-widget-transform-cursor-e {
      cursor: e-resize;
    }
    .x6-widget-transform-cursor-w {
      cursor: w-resize;
    }
    .x6-widget-transform-cursor-ne {
      cursor: ne-resize;
    }
    .x6-widget-transform-cursor-nw {
      cursor: nw-resize;
    }
    .x6-widget-transform-cursor-se {
      cursor: se-resize;
    }
    .x6-widget-transform-cursor-sw {
      cursor: sw-resize;
    }
    .x6-widget-transform-resize {
      width: 10px;
      height: 10px;
      border-radius: 6px;
    }
    .x6-widget-transform-resize[data-position='top-left'] {
      top: -5px;
      left: -5px;
    }
    .x6-widget-transform-resize[data-position='top-right'] {
      top: -5px;
      right: -5px;
    }
    .x6-widget-transform-resize[data-position='bottom-left'] {
      bottom: -5px;
      left: -5px;
    }
    .x6-widget-transform-resize[data-position='bottom-right'] {
      right: -5px;
      bottom: -5px;
    }
    .x6-widget-transform-resize[data-position='top'] {
      top: -5px;
      left: 50%;
      margin-left: -5px;
    }
    .x6-widget-transform-resize[data-position='bottom'] {
      bottom: -5px;
      left: 50%;
      margin-left: -5px;
    }
    .x6-widget-transform-resize[data-position='left'] {
      top: 50%;
      left: -5px;
      margin-top: -5px;
    }
    .x6-widget-transform-resize[data-position='right'] {
      top: 50%;
      right: -5px;
      margin-top: -5px;
    }
    .x6-widget-transform.prevent-aspect-ratio .x6-widget-transform-resize[data-position='top'],
    .x6-widget-transform.prevent-aspect-ratio .x6-widget-transform-resize[data-position='bottom'],
    .x6-widget-transform.prevent-aspect-ratio .x6-widget-transform-resize[data-position='left'],
    .x6-widget-transform.prevent-aspect-ratio .x6-widget-transform-resize[data-position='right'] {
      display: none;
    }
    .x6-widget-transform.no-orth-resize .x6-widget-transform-resize[data-position='bottom'],
    .x6-widget-transform.no-orth-resize .x6-widget-transform-resize[data-position='left'],
    .x6-widget-transform.no-orth-resize .x6-widget-transform-resize[data-position='right'],
    .x6-widget-transform.no-orth-resize .x6-widget-transform-resize[data-position='top'] {
      display: none;
    }
    .x6-widget-transform.no-resize .x6-widget-transform-resize {
      display: none;
    }
    .x6-widget-transform-rotate {
      top: -20px;
      left: -20px;
      width: 12px;
      height: 12px;
      border-radius: 6px;
      cursor: crosshair;
    }
    .x6-widget-transform.no-rotate .x6-widget-transform-rotate {
      display: none;
    }
    .x6-widget-transform-active {
      border-color: transparent;
      pointer-events: all;
    }
    .x6-widget-transform-active > div {
      display: none;
    }
    .x6-widget-transform-active > .x6-widget-transform-active-handle {
      display: block;
      background-color: #808080;
    }

    .x6-widget-selection {
      position: absolute;
      top: 0;
      left: 0;
      display: none;
      width: 0;
      height: 0;
      touch-action: none;
    }
    .x6-widget-selection-rubberband {
      display: block;
      overflow: visible;
      opacity: 0.3;
    }
    .x6-widget-selection-selected {
      display: block;
    }
    .x6-widget-selection-box {
      cursor: move;
    }
    .x6-widget-selection-inner[data-selection-length='0'],
    .x6-widget-selection-inner[data-selection-length='1'] {
      display: none;
    }
    .x6-widget-selection-content {
      position: absolute;
      top: 100%;
      right: -20px;
      left: -20px;
      margin-top: 30px;
      padding: 6px;
      line-height: 14px;
      text-align: center;
      border-radius: 6px;
    }
    .x6-widget-selection-content:empty {
      display: none;
    }
    .x6-widget-selection-rubberband {
      background-color: #3498db;
      border: 2px solid #2980b9;
    }
    .x6-widget-selection-box {
      box-sizing: content-box !important;
      margin-top: -4px;
      margin-left: -4px;
      padding-right: 4px;
      padding-bottom: 4px;
      border: 2px dashed #feb663;
      box-shadow: 2px 2px 5px #d3d3d3;
    }
    .x6-widget-selection-inner {
      box-sizing: content-box !important;
      margin-top: -8px;
      margin-left: -8px;
      padding-right: 12px;
      padding-bottom: 12px;
      border: 2px solid #feb663;
      box-shadow: 2px 2px 5px #d3d3d3;
    }
    .x6-widget-selection-content {
      color: #fff;
      font-size: 10px;
      background-color: #6a6b8a;
    }

    .x6-graph-scroller {
      position: relative;
      box-sizing: border-box;
      overflow: scroll;
      outline: none;
    }
    .x6-graph-scroller-content {
      position: relative;
    }
    .x6-graph-scroller-background {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
    }
    .x6-graph-scroller .x6-graph {
      position: absolute;
      display: inline-block;
      margin: 0;
      box-shadow: none;
    }
    .x6-graph-scroller .x6-graph > svg {
      display: block;
    }
    .x6-graph-scroller.x6-graph-scroller-paged .x6-graph {
      box-shadow: 0 0 4px 0 #eee;
    }
    .x6-graph-scroller.x6-graph-scroller-pannable[data-panning='false'] {
      cursor: grab;
    }
    .x6-graph-scroller.x6-graph-scroller-pannable[data-panning='true'] {
      cursor: grabbing;
      user-select: none;
    }
    .x6-graph-pagebreak {
      position: absolute;
      top: 0;
      right: 0;
      bottom: 0;
      left: 0;
    }
    .x6-graph-pagebreak-vertical {
      position: absolute;
      top: 0;
      bottom: 0;
      box-sizing: border-box;
      width: 1px;
      border-left: 1px dashed #bdbdbd;
    }
    .x6-graph-pagebreak-horizontal {
      position: absolute;
      right: 0;
      left: 0;
      box-sizing: border-box;
      height: 1px;
      border-top: 1px dashed #bdbdbd;
    }

    .x6-widget-minimap {
      position: relative;
      display: table-cell;
      box-sizing: border-box;
      overflow: hidden;
      text-align: center;
      vertical-align: middle;
      background-color: #fff;
      user-select: none;
    }
    .x6-widget-minimap .x6-graph {
      display: inline-block;
      box-shadow: 0 0 4px 0 #eee;
      cursor: pointer;
    }
    .x6-widget-minimap .x6-graph > svg {
      pointer-events: none;
      shape-rendering: optimizespeed;
    }
    .x6-widget-minimap .x6-graph .x6-node * {
      /* stylelint-disable-next-line */
      vector-effect: initial;
    }
    .x6-widget-minimap-viewport {
      position: absolute;
      box-sizing: content-box !important;
      margin: -2px 0 0 -2px;
      border: 2px solid #31d0c6;
      cursor: move;
    }
    .x6-widget-minimap-viewport-zoom {
      position: absolute;
      right: 0;
      bottom: 0;
      box-sizing: border-box;
      width: 12px;
      height: 12px;
      margin: 0 -6px -6px 0;
      background-color: #fff;
      border: 2px solid #31d0c6;
      border-radius: 50%;
      cursor: nwse-resize;
    }
  `;

  /*
  *  Graph type:
  *  basic graph = 0 
  *  interactions graph = 1 (with this graph you can enable or disable node movement)
  */
  @property()
  graph_type = 0;

  /*
  *  A boolean value that defines whether or not the div containing the minimap is needed.
  */
  @property()
  minimap_div = false;

  /*
  * A boolean value that defines whether or not the div containing the context menu is needed. 
  */
  @property()
  context_menu_div = false;

  /**
  * The width of the graph.
  */
  @property()
  graph_width: number = 0;

  /**
   * The height of the graph.
   */
  @property()
  graph_height: number = 0;

  /**
  * The background color of the graph.
  */
  @property()
  graph_background_color: string = '#ffffff';

  /**
  * The ID of the node background image for the graph.
  */
  @property()
  graph_node_background_id: string = '';

  /**
  * Whether to show the grid on the graph.
  */
  @property()
  graph_grid: boolean = false;

  /**
  * Whether panning is enabled on the graph.
  */
  @property()
  graph_panning: boolean = false;

  /**
  * Whether mouse wheel zooming is enabled on the graph.
  */
  @property()
  graph_mouse_wheel: boolean = false;

  /**
  * Whether to display node labels.
  */
  @property()
  nodes_label_state: boolean = true;

  /**
  * Toggles the color of node labels.
  */
  @property()
  nodes_label_color_toggle: boolean = false;

  /**
  * The background color of node labels.
  */
  @property()
  nodes_label_bgColor: string = '#15ed32';
  
  /**
  * The padding for exporting the graph as a JPEG image.
  */
  @property()
  padding_export_graph_JPEG: number = 20;

  /**
  * The zoom level for the graph.
  */
  @property()
  graph_zoom: number = -0.1;

  /**
  * The graph instance.
  */
  private graph: Graph | null = null;

  /*
  * A path that defines the location of a node style attribute in the X6 model.
  */
  private stylesPathNode: Record <string, string> = {
    fillColor: "body/fill",
    strokeColor: "body/stroke",
    strokeWidth: "body/strokeWidth",
    dashed: "body/strokeDasharray",
    rounded: "body/r",
    zIndex: "zIndex",
    visibility: "label/visibility",
    fontSize: "label/fontSize",
    fontColor: "label/fill",
    fontFamily: "label/fontFamily"
  }

  /*
  * A path that defines the location of a edge style attribute in the X6 model.
  */
  private stylesPathEdge: Record <string, string> = {
    strokeColor: "line/stroke",
    strokeWidth: "line/strokeWidth",
    dashed: "line/strokeDasharray",
    rounded: "connector/args/radius",
    zIndex: "zIndex"
  }

   /*
  * A path that defines the location of a edge label style attribute in the X6 model.
  */
   private stylesPathEdgeLabel: Record <string, string> = {
    fillColor: "no path",
    fontColor: "no path",
    fontSize: "no path",
    fontFamily: "no path",
    strokeColor: "no path",
    strokeWidth: "no path",
    rounded: "no path"
  }

  /*
  * The main canvas element for rendering the graph. 
  */
  @query('#canvas')
  target!: HTMLDivElement;

  /* 
  * The context menu element 
  */
  @query('#contextMenu') 
  contextMenu!: HTMLElement;

  /**
 * Button to bring the selected element to the front.
 */
  @query('#bringToFront') 
  bringToFrontBtn!: HTMLElement;

  /**
 * Button to send the selected element to the back.
 */
  @query('#sendToBack') 
  sendToBackBtn!: HTMLElement;

  /**
  * Button to move the selected element one unit up.
  */
  @query('#moveUp') 
  moveUpBtn!: HTMLElement;

  /**
  * Button to move the selected element one unit down.
  */
  @query('#moveDown') 
  moveDownBtn!: HTMLElement;

  /*
  * The minimap element for providing an overview of the graph.
  */
  @query('#minimap')
  minimap!: HTMLDivElement;

  protected firstUpdated() {
    if(this.target){
      switch(this.graph_type){
        case 1:
          this.initGraphWithInteractions();
        break;
        default:
          this.initGraph();
        break;
      }
      this.eventInitGraph();
    }
  }

  /**
  * Dispatches a custom event indicating that the graph has been created.
  */
  private eventInitGraph(){
    this.dispatchEvent(new CustomEvent('graph-created', {
      detail: {
        status: 'success'
      }
    }));
  }

  /* Methods to init a X6 graph */

  /**
  * Initializes the graph with basic settings.
  */
  public initGraph(){
    this.graph = new Graph({
      container: this.target,
      width: this.graph_width, 
      height: this.graph_height,
      grid: this.graph_grid,
      panning: this.graph_panning,
      mousewheel: this.graph_mouse_wheel,
      background: {
        color: this.graph_background_color,
      },
      connecting: {
        snap: true, 
        allowBlank: false, 
        allowNode: true, 
        allowEdge: false,
        allowPort: true,
        allowMulti: 'withPort', 
        highlight: true,
      },
    });
  }

  /**
  * Initializes the graph with interactions.
  */
  public initGraphWithInteractions(){
    this.graph = new Graph({
      container: this.target,
      width: this.graph_width, 
      height: this.graph_height,
      grid: this.graph_grid,
      panning: this.graph_panning,
      mousewheel: this.graph_mouse_wheel,
      background: {
        color: this.graph_background_color,
      },
      interacting:{
        nodeMovable(view) {
          const node = view.cell
          const { enableMove } = node.getData()
          return enableMove
        },
      },
      connecting: {
        snap: true,  
        allowNode: true, 
        allowMulti: 'withPort', 
      },
   });
  }

  /* End of methods to init a X6 graph */

  /*
  * Methods for add X6 plugins
  */

  /**
  * Adds the scroller plugin to the graph with the specified configuration.
  */
  public addScrollerPlugin(
    enabled: boolean,
    pannable: boolean,
    pageVisible: boolean,
    pageBreak: boolean,
    width: number,
    height: number,
    pageWidth: number,
    pageHeight: number,
    padding: number,
    autoResize: boolean,
    scrollerPositionLeft: number,
    scrollerPositionTop: number
  ) {
    if (this.graph) {
      const scrollerPlugin = new Scroller({
        enabled: enabled,
        pannable: pannable,
        pageVisible: pageVisible,
        pageBreak: pageBreak,
        width: width,
        height: height,
        pageWidth: pageWidth,
        pageHeight: pageHeight,
        padding: padding,
        autoResize: autoResize
      });
  
      this.graph.use(scrollerPlugin);
      this.graph.setScrollbarPosition(scrollerPositionLeft, scrollerPositionTop);
    }
  }

  /**
  * Adds the export plugin to the graph.
  */
  public addExportPlugin(){
    if(this.graph)
      this.graph.use(new Export());
  } 

  /**
  * Adds the snapline plugin to the graph with the specified configuration.
  * @param enabled - A boolean value to enable or disable the snapline feature.
  */
  public addSnaplinePlugin(enabled : boolean){
    if(this.graph)
      this.graph.use(new Snapline({enabled: enabled}));
  }

  /**
  * Adds the transform plugin to the graph.
  */
  public addTransformPlugin(
    rotating: boolean,
    resizingEnabled: boolean,
    resizingOrthogonal: boolean,
    resizingMinWidth: number,
    resizingMinHeight: number,
    resizingPreserveAspectRatio: boolean
  ) {
    if (this.graph) {
      const transformPlugin = new Transform({
        rotating: rotating,
        resizing: {
          enabled: resizingEnabled,
          orthogonal: resizingOrthogonal,
          minWidth: resizingMinWidth,
          minHeight: resizingMinHeight,
          preserveAspectRatio: resizingPreserveAspectRatio,
        },
      });
  
      this.graph.use(transformPlugin);
    }
  }

  /**
  * Adds the selection plugin to the graph.
  */
  public addSelectionPlugin(
    enabled: boolean,
    multiple: boolean,
    rubberband: boolean, 
    movable: boolean,
    showNodeSelectionBox: boolean
  ) {
    if (this.graph) {
      this.graph.use(new Selection({
        enabled: enabled,
        multiple: multiple,
        rubberband: rubberband,
        movable: movable,
        showNodeSelectionBox: showNodeSelectionBox,
      }));
    }
  }

  /**
  * Adds the mminimap plugin to the graph.
  */
  public addMinimapPlugin(width: number, height: number){
    if(this.graph){
      if(this.minimap){
        const minimap = new MiniMap({
          container: this.minimap,
          width: width, 
          height: height, 
        });
  
        this.graph.use(minimap);
      }
    }
  }

  /* 
  * End of methods to add X6 plugins
  */

  /* 
  * Methods for Graph View Management 
  */

  /**
  * Clears the graph by removing all cells.
  */
  public cleanGraph(){
    if(this.graph){
      this.graph.clearCells();
      this.dispatchEvent(new CustomEvent('graph-cleaned', {
        detail: {
          state: 'success'
        }
      }));
    }    
  }

  /**
  * Centers the graph view around a specified node.
  * 
  * @param {string} idNode - The ID of the node to center the graph around.
  */
  public centerGraph(idNode: string){
    if(this.graph){
      const nodeCell = this.graph.getCellById(idNode);
      if(nodeCell)
        this.graph.centerCell(nodeCell);
    }
  }

  /**
  * Refreshes the graph by clearing it and restoring its previous state.
  */
  public refreshGraph(){
    if(this.graph){
      const backup = this.graph.toJSON();
      this.graph.clearCells();
      this.graph.fromJSON(backup);
      this.dispatchEvent(new CustomEvent('graph-refreshed', {
        detail: {
          state: 'success'
        }
      }));
    }
  }

  /* 
  * End of methods for Graph View Management 
  */
  
  /* 
  * Methods for node selection functionalities
  */

  public unselectCell(id: string){
    if(this.graph){
      const cell = this.graph.getCellById(id);
      if(cell){
        const selectionPlugin = this.graph.getPlugin('selection') as Selection;
        if(selectionPlugin)
          selectionPlugin.unselect(cell);
      }
    }
  }

  /**
  * Selects a node in the graph and centers the view on it.
  * 
  * @param {string} id - The id of the node to select.
  */
  public selectNode(id: string){
    if(this.graph){
      if(id){
        const nodeCell = this.graph.getCellById(id);
        if(nodeCell && id !== this.graph_node_background_id){
          this.graph.select(nodeCell);
          this.graph.centerCell(nodeCell);
        }
      }
    }
  }

  /**
  * Sets up an event listener for when a cell is selected in the graph.
  * 
  * If the selected cell is not the background node.
  */
  public eventCellSelected(){
    if(this.graph){
      this.graph.on('cell:selected',({cell}) => {   
        const selectedCells = this.graph?.getSelectedCells();
        if(selectedCells){
          let cellType = '';
          if(cell.isNode())
            cellType = 'node';
          else
            cellType = 'edge';

          this.dispatchEvent(new CustomEvent('cell-selected', {
            detail: {
              cell: {
                id: cell.id,
                cellType: cellType,
                numberCells: selectedCells.length
              }
            }
          }));
        }
      })
    }
  }

  /**
  * Registers an event listener for when a cell is unselected in the graph.
  */
  public eventCellUnselect(){
    if(this.graph){
      this.graph.on('cell:unselected', ({cell}) => {
        this.dispatchEvent(new CustomEvent('cell-unselected', {
          detail: {
            cell: {
              id: cell.id,
              state: 'successful',
            }
          }
        }));
      });
    }
  }

  /* End of methods for node selection functionalities*/

  /* 
  * Methods to draw objects in X6 graph
  */

  /**
  * Creates a background node in the graph using the specified parameters.
  * This method adds a node that serves as the background.
  * 
  * @param {string} nodeData - OBJ in json format
  */
  public drawBackground(nodeData : string){
    if(this.graph){
      // If another background existed, remove it.
      if(this.graph_node_background_id){
        const oldBackground = this.graph.getCellById(this.graph_node_background_id);
        if(oldBackground)
          this.graph.removeCell(oldBackground);
      }
      
      const X6NodeBackground = JSON.parse(nodeData) as X6NodeBackground;
      this.graph_node_background_id = X6NodeBackground.id;
      const labelPosition = this.getNodeLabelConfiguration(X6NodeBackground);

      this.graph.addNode({
        id:  X6NodeBackground.id,
        shape: X6NodeBackground.shape,
        x: X6NodeBackground.geometry.coordinates.x,
        y: X6NodeBackground.geometry.coordinates.y,
        width: X6NodeBackground.geometry.dimensions.width,
        height: X6NodeBackground.geometry.dimensions.height,
        data: { enableMove: X6NodeBackground.movable },
        imageUrl:X6NodeBackground.imgUrl,
        attrs: {
          body: {
            fill: X6NodeBackground.styles.fillColor,
            stroke: X6NodeBackground.styles.strokeColor,
            strokeWidth: X6NodeBackground.styles.strokeWidth,
            strokeDasharray: X6NodeBackground.styles.dash,
            rx: X6NodeBackground.styles.borderRadius,
            ry: X6NodeBackground.styles.borderRadius
          },
          label: {
            ...labelPosition,
          },
        },
        zIndex:X6NodeBackground.styles.zIndex
      });
    }
  }

  /**
  * Draws a node in the graph using the specified properties.
  * 
  * @param {string} nodeData - OBJ in json format
  */
  public drawNode(nodeData : string){
    if(this.graph){
      const node = JSON.parse(nodeData) as X6Node;
      const labelPosition = this.getNodeLabelConfiguration(node);
      const port = this.getNodePortConfiguration(node.port);
      this.graph.addNode({
        id: node.id,
        shape: node.shape,
        x: node.geometry.coordinates.x,
        y: node.geometry.coordinates.y,
        width: node.geometry.dimensions.width ,
        height: node.geometry.dimensions.height,
        data: { enableMove: node.movable },
        imageUrl: node.imgUrl,
        attrs: {
          body: {
            fill: node.styles.fillColor,
            stroke: node.styles.strokeColor,
            strokeWidth: node.styles.strokeWidth,
            strokeDasharray: node.styles.dash,
            rx: node.styles.borderRadius,
            ry: node.styles.borderRadius
          },
          label: {
            ...labelPosition,
          },
        },
        ports: {
          ...port
        },
        zIndex: node.styles.zIndex,
      })

      this.setNodeTools(node);
      this.setParent(node.parentId, node.id);
    }
  }

  /**
  * Draws a text node in the graph using the specified properties.
  * 
  * @param {string} nodeData - OBJ in json format.
  */
  public drawText(nodeData : string){
    if(this.graph){
      const nodeText = JSON.parse(nodeData) as X6NodeText;
      const labelDefaultPosition = this.getNodeLabelConfiguration(nodeText);

      this.graph.addNode({
        id: nodeText.id,
        width: nodeText.geometry.dimensions.width,
        height: nodeText.geometry.dimensions.height,
        x: nodeText.geometry.coordinates.x,
        y: nodeText.geometry.coordinates.y,
        shape: nodeText.shape,
        data: { enableMove: nodeText.movable },
        attrs: {
          body:{
            fill: nodeText.styles.fillColor,
            stroke: nodeText.styles.strokeColor,
            strokeWidth: nodeText.styles.strokeWidth,
            strokeDasharray: nodeText.styles.dash,
            rx: nodeText.styles.borderRadius,
            ry: nodeText.styles.borderRadius,
          },
          label: {
            ...labelDefaultPosition,
          }
        }
      });

      this.setParent(nodeText.parentId, nodeText.id);
    }
  }

  /**
  * Draws an edge in the graph's using the specified properties.
  * 
  * This method adds an edge connecting two nodes, defined by their 
  * source and target IDs.
  * 
  * @param {string} edgeData - OBJ in json format.
  */
  public drawEdge(edgeData: string) {
    if (this.graph) {
      const edge = JSON.parse(edgeData) as X6Edge;
      const labelsConfiguration = this.getEdgeLabelsConfiguration(edge);
      //Connector sets the borderRadius of an edge
      const edgeConnector = this.getEdgeConnector(edge);

      const newEdge = this.graph.addEdge({
        id: edge.id,
        source: edge.idSource,
        target: edge.idTarget,
        zIndex: edge.styles.zIndex,
        connector: { ...edgeConnector },
        attrs: {
            line: {
              sourceMarker: null,
              targetMarker: null,
              stroke: edge.styles.strokeColor,
              strokeWidth: edge.styles.strokeWidth,
              strokeDasharray: edge.styles.dash
            }
        }
      });

      if(labelsConfiguration.length > 0)
        this.setLabelsToEdge(newEdge, labelsConfiguration);

      if (Array.isArray(edge.vertices) && edge.vertices.length > 0) 
          newEdge.setVertices(this.getVerticesFormat(edge.vertices));
        
    }
  }

  /* 
  * End of methods to draw objects in X6 graph
  */

  /*
  * Methods about objects configuration
  */

  /**
  * Creates a label position configuration for a node based on its label position settings.
  *
  * @param node - The node object containing label information.
  * @returns An object representing the label position configuration.
  */
  private getNodeLabelConfiguration(node: X6AbstractNode) {
    let labelPosition;
    
    if (node.labelStyles.labelPosition === 'bottom') {
      labelPosition = {
        text: node.label,
        fontSize: node.labelStyles.fontSize,
        fontFamily: node.labelStyles.fontFamily,
        fill: node.labelStyles.fontColor,
        refX: 0.5,
        refY: '100%',
        refY2: 4,
        textAnchor: 'middle',
        textVerticalAnchor: 'top',
        visibility: node.labelStyles.visibility
      };
    } else {
      labelPosition = {
        text: node.label,
        fontSize: node.labelStyles.fontSize,
        fontFamily: node.labelStyles.fontFamily,
        fill: node.labelStyles.fontColor,
        refX: 0.5,
        refY: 0.5,
        textAnchor: 'middle',
        textVerticalAnchor: 'middle',
        visibility: node.labelStyles.visibility
      };
    }
    
    return labelPosition;
  }

  /**
  * Creates a port configuration for a node based on a boolean flag.
  *
  * @param port - A boolean indicating whether to create a port configuration.
  * @returns An object representing the port configuration or an empty object.
  */
  public getNodePortConfiguration(port: boolean){
    let nodePort = {};

    if(port){
      nodePort = {
        groups: {
          group1: {
            position: {
              name: 'absolute', 
              args: { x: '100%', y: '90%' },
            },
            attrs: {
              circle: {
                r: 6,
                magnet: true,
                stroke: '#31d0c6',
                fill: '#fff',
                strokeWidth: 2,
              },
              },
            },
          },
          items: [
            {
              id: 'port1',
              group: 'group1',
            }
          ]
      }
    }

    return nodePort;
  }

  private getEdgeLabelsConfiguration(edge: X6Edge){
    const labelConfigs = [] as any[];
    const x6Labels = edge.labels as X6EdgeLabel[];
    
    if(x6Labels.length !== 0){
      x6Labels.forEach((currentLabel) => {
        if(currentLabel.label && currentLabel.label != "" && currentLabel.distance >= 0 && currentLabel.distance <= 1){
          let currentLabelConfig = {};
          currentLabelConfig = {
            attrs: {
              text: {
                text: currentLabel.label,
                fontSize: currentLabel.styles.fontSize,
                fontFamily: currentLabel.styles.fontFamily,
                fill: currentLabel.styles.fontColor,
                textAnchor: 'middle',
                textVerticalAnchor: 'middle',
              },
              rect: {
                fill: currentLabel.styles.fillColor,
                ref: 'text',
                refX: -4,
                refY: -2,
                refWidth: '100%',
                refHeight: '100%',
                refWidth2: 8,
                refHeight2: 5,
                stroke: currentLabel.styles.strokeColor,
                strokeWidth: currentLabel.styles.strokeWidth,
                rx: currentLabel.styles.borderRadius,
                ry: currentLabel.styles.borderRadius,
              }
            },
            position: {
              distance: currentLabel.distance,
              offset: 0,
            },
          };

          labelConfigs.push(currentLabelConfig);
        }
      });
    }
    return labelConfigs;
  }

  private setLabelsToEdge(edge: Edge , labels: any[]){
    labels.forEach(currentLabel => {
      edge.appendLabel(currentLabel);
    });
  }

  /**
  * This method retrieves the connector configuration for an edge in the X6 graph.
  * 
  * @param edge - The edge object whose connector configuration is to be retrieved.
  * @returns The connector configuration object for the edge.
  */
  private getEdgeConnector(edge : X6Edge){
    let edgeConnector = {}
    let valueRadius = 0;
    let nameConnector = 'normal';

    if(edge.styles.borderRadius && edge.styles.borderRadius > 0){
      valueRadius = edge.styles.borderRadius;
      nameConnector = 'rounded';
      edgeConnector = {
        name: nameConnector,
        args: { radius: valueRadius}
      }
    }else{
      edgeConnector = {
        name: nameConnector,
      }
    }
  
    return edgeConnector;
  }

  /**
  * This method formats a list of vertex objects, returning a new array of vertices with 
  * their x and y coordinates.
  * 
  * @param vertices - An array of vertex objects containing x and y coordinates.
  * @returns A new array of formatted vertex objects with x and y coordinates.
  */
  private getVerticesFormat(vertices: {x: number, y: number}[]) {
    return vertices.map(vertex => ({
      x: vertex.x,
      y: vertex.y
    }));
  }

  /*
  * End of methods to objects configuration 
  */

  /*
  * Methods to set object styles
  */

  public setNodeStyle(id: string, style: string, value:string){
    if(this.graph){
      const cell = this.graph.getCellById(id);
      if(cell && cell.isNode()){
        const node = cell as Node;
        if(style in this.stylesPathNode){
          const pathStyle = this.stylesPathNode[style];
          if(style == "rounded"){
            node.setAttrByPath(pathStyle + "x", value);
            node.setAttrByPath(pathStyle + "y", value);
          }else if(style == "zIndex")
            node.setProp(pathStyle, value);
          else 
            node.setAttrByPath(pathStyle, value);
        }
      }
    }
  }

  public setEdgeStyle(id: string, style: string, value: string) {
    if (this.graph) {
      const cell = this.graph.getCellById(id);
      if (cell && cell.isEdge()) {
        const edge = cell as Edge;
        if (style in this.stylesPathEdge) {
          const pathStyle = this.stylesPathEdge[style];
          if (style == "rounded") {
            edge.removeProp('connector');
            if (value && Number(value) > 0) {
              edge.setProp({
                connector: {
                  name: 'rounded',
                  args: { radius: Number(value) }
                }
              });
            } else {
              edge.setProp({
                connector: {
                  name: 'normal'
                }
              });
            }
          }else if (style == "zIndex")
            edge.setProp(pathStyle, value);
          else
            edge.setAttrByPath(pathStyle, value);
        }
      }
    }
  }
  
  public setEdgeLabelStyle(id: string, style: string, value: string, labelPos: number){
    if(this.graph){
      const cell = this.graph.getCellById(id);
      if(cell && cell.isEdge()){
        const edge = cell as Edge;
        if(style in this.stylesPathEdgeLabel){
          //label of the edg
          const label = edge.getLabelAt(labelPos);
          if(style == "fillColor"){
            if(label && label.attrs && label.attrs.rect)
              label.attrs.rect.fill = value;
          }else if(style == "fontColor"){
            if(label && label.attrs && label.attrs.text)
              label.attrs.text.fill = value;      
          }else if(style == "fontSize"){
            if(label && label.attrs && label.attrs.text)
              label.attrs.text.fontSize = value;   
          }else if(style == "fontFamily"){
            if(label && label.attrs && label.attrs.text)
              label.attrs.text.fontFamily = value;
          }else if(style == "strokeColor"){
            if(label && label.attrs && label.attrs.rect)
              label.attrs.rect.stroke = value;
          }else if(style == "strokeWidth"){
            if(label && label.attrs && label.attrs.rect)
              label.attrs.rect.strokeWidth  = value;
          }else if(style == "rounded"){
            if(label && label.attrs && label.attrs.rect){
              label.attrs.rect.rx = value;
              label.attrs.rect.ry = value;
            }
          }
                
        }
      }
    }
  }

  /*
  * End of methods to set object styles 
  */

  /*
  * Methods to configure x6-tools
  */

  /**
  * This method sets tools for a given node based on its tools array.
  * 
  * @param node - The X6Node object which contains the tools to be set.
  */
  public setNodeTools(node: X6Node) {
    if (Array.isArray(node.tools)) {  
      for (const tool of node.tools) {
        if(tool == 'node-editor')
          this.setNodeEditorTool(node.id);
      }
    }
  }

  /**
  * This method adds the 'node-editor' tool to a node if it doesn't already have it.
  * 
  * @param id - The id of the node to which the 'node-editor' tool will be added.
  */
  public setNodeEditorTool(id: string){
    if(this.graph){
      const node = this.graph.getCellById(id);
      if(node){
        if(!node.hasTool('node-editor')){
          node.addTools({
            name: 'node-editor',
            args: {
              getText: 'label/text',
              setText: 'label/text',
            }
          });
        }
      }
    }
  }

  /**
  * This method listens for the 'mouseenter' event on edges.
  * When the mouse enters an edge, it adds the 'vertices' and 'segments' tools to that edge.
  */
  public eventAddEdgeVerticesTool(){
    if(this.graph){
      this.graph.on('edge:mouseenter',({ edge })=>{   
        edge.addTools(['vertices','segments']) 
      })
    }
  }

  public eventAddEdgeButtonRemoveTool(){
    if(this.graph){
      this.graph.on('edge:mouseenter',({ edge })=>{   
        edge.addTools({
          name: 'button-remove',
          args: { distance: 40 },
        })
  
        edge.addTools({
          name: 'button-remove',
          args: { distance: -40 },
        })
      })
    }
  }

  /**
  * This method listens for the 'mouseleave' event on edges.
  * When the mouse leaves an edge, it removes all tools from that edge.
  */
  public eventRemoveEdgeTools(){
    if(this.graph){
      this.graph.on('edge:mouseleave',({ edge })=>{   
        edge.removeTools()
      })
    }
  }

  /**
  * This method listens for the 'mouseenter' event on nodes.
  * When the mouse enters a node, it adds a remove button tool.
  */
  public eventAddNodeButtonRemoveTool(){
    if(this.graph){
      this.graph.on('node:mouseenter', ({ node }) => {
        node.addTools([
        {
          name: 'button-remove',
          args: {
            x: 0,
            y: 0,
          }
        }])
      });
    }
  }

  /**
  * This method listens for the 'mouseleave' event on nodes.
  * When the mouse leaves a node, it removes 'button-remove' tool from the node.
  */
  public eventRemoveNodeButtonRemoveTool(){
    if(this.graph){
      this.graph.on('node:mouseleave', ({ node }) => {
        node.removeTool('button-remove');
      });
    }
  }

  /*
  * End of methods to configure x6-tools
  */

  /*
  * Events
  */

  /**
  * Sets up an event listener for when the background node is resized.
  * 
  * When the background node is resized, it dispatches a custom event 
  * with the new dimensions (width and height) of the background node.
  */
  public eventGetNodeBackgroundNewDimensions(){
    if(this.graph){
      this.graph.on('node:resized', ({ node }) => {
        if(node.id === this.graph_node_background_id){
          this.dispatchEvent(new CustomEvent('background-resized', {
            detail: {
              node: {
                id: node.id,
                width: node.getSize().width ,
                height: node.getSize().height,
              }
            }
          }));
        }
      });
    }
  }

  public eventCellRemoved(){
    if(this.graph){
      this.graph.on('cell:removed', ({ cell }) => {
        let typeCell = 'edge';
        if(cell.isNode())
          typeCell = 'node'
        this.dispatchEvent(new CustomEvent('cell-removed', {
          detail: {
            cell: {
              id: cell.id,
              typeCell: typeCell
            }
          }
        }));
      });
    }
  }

  public eventDblClickEdge() {
    if (this.graph) {
      this.graph.on('edge:dblclick', ({ edge }) => {
        this.dispatchEvent(new CustomEvent('edge-dblclick', {
          detail: {
            edge: {
              id: edge.id,
              idSource: edge.getSourceCell()?.id,
              idTarget: edge.getTargetCell()?.id,
            }
          }
        }));
      });
    }
  }
  

  public eventEdgeChanged() {
    if (this.graph) {
      this.graph.on('edge:changed', ({ edge }) => {
        const vertices = edge.getVertices().map(vertex => ({
          x: vertex.x,
          y: vertex.y
        }));
  
        const verticesJson = JSON.stringify(vertices);
  
        this.dispatchEvent(new CustomEvent('edge-changed', {
          detail: {
            edge: {
              id: edge.id,
              idSource: edge.getSourceCell()?.id,
              idTarget: edge.getTargetCell()?.id,
              vertices: verticesJson
            }
          }
        }));
      });
    }
  }
  
  /**
  * Sets up an event listener for when a node is moved in the graph.
  * 
  * When a node is moved, it dispatches a custom event with the new 
  * position (x and y coordinates) of the moved node.
  */
  public eventGetNodeNewPosition(){
    if(this.graph){
      this.graph.on('node:moved', ({ node }) => {
        this.dispatchEvent(new CustomEvent('node-moved', {
          detail: {
            node: {
              id: node.id,
              x: node.position().x,
              y: node.position().y,
            }
          }
        }));
      });
    }
  }

  /**
  * Sets up an event listener for when an edge is connected in the graph.
  * 
  * When an edge is connected, it sets the attributes for the edge.
  * dispatches a custom event with details about the newly created edge.
  */
  public eventNodesConnected(){
    if(this.graph){
      this.graph.on('edge:connected', ({ edge }) => {
        if(edge.getSourceCell() != null && edge.getTargetCell != null){
          this.dispatchEvent(new CustomEvent('edge-created', {
            detail: {
              edge: {
                id: edge.id,
                idSource: edge.getSourceCell()?.id,
                idTarget: edge.getTargetCell()?.id,
              }
            }
          }));

          this.graph?.removeEdge(edge);
        }
      });
    }
  }


  public eventNodeChanged(){
    if(this.graph){
      this.graph.on('node:changed', ({ node }) => {
        this.dispatchEvent(new CustomEvent('node-changed', {
          detail: {
            node: {
              id: node.id,
              x: node.getBBox().x,
              y: node.getBBox().y,
              width: node.getBBox().width,
              height: node.getBBox().height,
              newLabel:node.getAttrByPath('label/text')
            }
          }
        }));
      });
    }  
  }

  public eventResizeNode(){
    if(this.graph){
      this.graph.on('node:dblclick', ({node}) => {
        if(node.shape != "image"){
          const transformPlugin = this.graph?.getPlugin('transform') as Transform;
          transformPlugin.createWidget(node);
        }
      });
    }
  }

  /**
  * Sets up an event listener for double-click events on the background node.
  * 
  * When the background node is double-clicked, it triggers the creation of a 
  * resizing widget for that background node using the transform plugin.
  */
  public eventResizeNodeBackgroundDblClick(){
    if(this.graph){
      this.graph.on('node:dblclick', ({node}) => {
        if(node.id === this.graph_node_background_id){
          const transformPlugin = this.graph?.getPlugin('transform') as Transform;
          transformPlugin.createWidget(node);
        }
      });
    }
  }

  public eventContextMenu() {
    if (this.graph) {
      this.graph.on('cell:contextmenu', (e) => {
  
        const x = e.e.clientX;  
        const y = e.e.clientY; 

        this.contextMenu.style.left = `${x}px`;
        this.contextMenu.style.top = `${y}px`;

        this.contextMenu.style.display = 'block';
        this.contextMenu.setAttribute('data-cell-id', e.cell.id);
      });
    }
  }

  /*
  * End of events
  */


  /*
  * Other methods
  */

  /**
  * Removes the background node from the graph.
  * 
  * This method checks if a background node ID is set and removes the 
  * corresponding node from the graph if it exists.
  */
  public removeBackground(){
    if(this.graph){
      if(this.graph_node_background_id != null && this.graph_node_background_id !== ''){
        const node = this.graph.getCellById(this.graph_node_background_id);
        this.graph_node_background_id = '';
        if (node)
          this.graph.removeCell(node);
      }
    }
  }

  public removeCell(id: string){
    if(this.graph){
      const cell = this.graph.getCellById(id);
      this.graph.removeCell(cell);
    } 
  }

  /**
  * Establishes a parent-child relationship between two nodes in the graph.
  *
  * This method links a child node to its parent node by retrieving both nodes from the graph
  * using their unique identifiers. If both nodes exist, the child is added to the parent's
  * list of children.
  *
  * @param idParent - The unique identifier of the parent node.
  * @param idChild - The unique identifier of the child node.
  */
  public setParent(idParent: string, idChild: string){
    if(this.graph){
      if(idParent && idChild){
        const father = this.graph.getCellById(idParent);
        const child = this.graph.getCellById(idChild);
  
        if(father && child)
          father.addChild(child);
      }
    }
  }

  /**
  * Creates a ghost( It's a non-visible and non-manipulable node) to manage the center of the canvas.
  * This node acts as a reference point for positioning the Scroller's bars of the graph.
  */
  public createGhost(){
    if(this.graph){
      const center = this.graph.addNode({
        x: this.graph_width/2,
        y: this.graph_height/2,
        width: 32,
        height: 32,
        shape: 'rect',
        visible: false,
      });

      this.centerGraph(center.id);
    }
  }  

  public activateContextMenu(){
    this.addEventListener('click', (e: Event) => {
      if (!this.contextMenu.contains(e.target as HTMLElement)) {
        this.contextMenu.style.display = 'none';
      }
    });
  }
  
  /**
  * Exports the current graph as a JPEG image.
  * 
  * This method generates a JPEG file of the graph with specified dimensions, 
  * background color, quality, and padding. The exported file will be named 
  * according to the provided filename.
  * 
  * @param {string} fileName - The name of the file to which the graph will be exported (without extension).
  */
  public exportGraphToJPEG(fileName: string){
    if(this.graph){
      this.graph.exportJPEG(fileName + '.jpeg', {
        width: 1920, 
        height: 1080,
        backgroundColor: this.graph_background_color,
        quality: 1, 
        padding: this.padding_export_graph_JPEG,

      });
    }
  }

  public configureZIndexControls(){
    this.bringToFrontBtn.addEventListener('click', () => this.bringToFront());
    this.sendToBackBtn.addEventListener('click', () => this.sendToBack());
    this.moveUpBtn .addEventListener('click', () => this.moveUpOneLevel());
    this.moveDownBtn.addEventListener('click', () => this.moveDownOneLevel());
  }

  public bringToFront(){
    const cellId = this.contextMenu.getAttribute('data-cell-id')
    if(this.graph && cellId){
      const cell = this.graph.getCellById(cellId);
      let nextLevel = this.graph.getCellCount();
      cell.setProp('zIndex', nextLevel);

      this.dispatchEvent(new CustomEvent('bring-to-front', {
        detail: {
          cell: {
            id: cell.id,
            zIndex: cell.getProp('zIndex')
          }
        }
      }));
    }
    this.contextMenu.style.display = 'none';  
  }

  public moveUpOneLevel() {
    const cellId = this.contextMenu.getAttribute('data-cell-id')
    if(this.graph && cellId){
      const cell = this.graph.getCellById(cellId);
      let currentZIndex = cell.getProp('zIndex')
      if(currentZIndex)
        cell.setProp('zIndex', currentZIndex+=1);
      
      this.dispatchEvent(new CustomEvent('bring-to-front', {
        detail: {
          cell: {
            id: cell.id,
            zIndex: cell.getProp('zIndex')
          }
        }
      }));
    }
    this.contextMenu.style.display = 'none';  
  }

  public sendToBack(){
    const cellId = this.contextMenu.getAttribute('data-cell-id')
    if(this.graph && cellId){
      const cell = this.graph.getCellById(cellId);
      cell.setProp('zIndex', 1);

      this.dispatchEvent(new CustomEvent('send-to-back', {
        detail: {
          cell: {
            id: cell.id,
            zIndex: cell.getProp('zIndex')
          }
        }
      }));
    }
    this.contextMenu.style.display = 'none'; 
  }
  
  public moveDownOneLevel() {
    const cellId = this.contextMenu.getAttribute('data-cell-id')
    if(this.graph && cellId){
      const cell = this.graph.getCellById(cellId);
      let currentZIndex = cell.getProp('zIndex')
      if(currentZIndex && currentZIndex > 1)
        cell.setProp('zIndex', currentZIndex-=1);

      this.dispatchEvent(new CustomEvent('send-to-back', {
        detail: {
          cell: {
            id: cell.id,
            zIndex: cell.getProp('zIndex')
          }
        }
      }));
    }
    this.contextMenu.style.display = 'none'; 
  }

  /*
  * End of other methods
  */

 /*
 * These methods will be deleted very soon and implemented in Java, because they cover business rules that are not necessary or of interest. 
 */

  public establishHierarchyThroughEdges() {
    if (this.graph) {
        const edges = this.graph.getEdges();
        
        edges.forEach(edge => {
          const source = edge.getSourceNode(); 
          const target = edge.getTargetNode(); 

          if(source && target){
            const parentSource = source.getParent();
            const parentTarget = target.getParent();
            
            if(parentSource && parentTarget)
              parentSource.addChild(parentTarget);
          } 
        });
    }
  }

  public adjustNodeWidth(id: string, reserveSpace: number, childSpacing: number, heightIncrease: number){
    if (this.graph) {
      const currentNode = this.graph.getCellById(id);
      if(currentNode){
        const children = currentNode.getChildren();
        let omega = reserveSpace;
        let maxChildHeight = 0;

        if(children && children.length > 0){
          const childrenSize = children.length;
          let totalWidth = 0;

          if(childrenSize != 1)
            omega = childSpacing*(children.length -1) + reserveSpace;
          
          children.forEach(child => {
            if(child.getBBox().height > maxChildHeight)
              maxChildHeight = child.getBBox().height;
            totalWidth += child.getBBox().width;
          });

          currentNode.prop({
            size: {
              width: totalWidth + omega,
              height: maxChildHeight + heightIncrease
            }
          });
        }
      }
    }
  }

  public adjustNodeHeight(id: string, childSpacing: number){
    if(this.graph){
      const currentNode = this.graph.getCellById(id);
      if(currentNode){
        const children = currentNode.getChildren();

        if(children && children.length > 0){
          const childrenSize = children.length;
          const omega = childSpacing*(childrenSize + 1); 
          let totalHeight = 0;
          let maxChildWidth = 0;

          children.forEach( child => {
            if(child.getBBox().width > maxChildWidth)
              maxChildWidth = child.getBBox().width;
            totalHeight += child.getBBox().height;
          });

          currentNode.prop({
            size: {
              width: maxChildWidth*3,
              height: totalHeight + omega
            }
          });
        }
      }
    }
  }

  public orderChildrenByName(idContainer: string) {
    if(this.graph){
      const container = this.graph.getCellById(idContainer);
      const children = container.getChildren();
      const orderedChildren = [];

      if(children){
        for (let i = 0; i < children.length; i++) {
          const child = children[i];
          const label: string = child.getAttrByPath('label/text') || '';
          let inserted = false;
          for (let j = 0; j < orderedChildren.length; j++) {
            let currentLabel: string = orderedChildren[j].getAttrByPath('label/text') as string || '';
            if (label < currentLabel) {
              orderedChildren.splice(j, 0, child);
              inserted = true;
              break;
            }
          }
          if (!inserted) 
            orderedChildren.push(child); 
        }
        container.setChildren(orderedChildren);
      }
    }
  }

  
  public executeTree(containerId: string, spacing: number) {
    if (this.graph) {
      const container = this.graph.getCellById(containerId);
      if (container){
        const children = container.getChildren();
        const edges = this.graph.getEdges();
        let targets: (string | undefined)[] = [];
        
        if (children && edges && edges.length > 0) {
          children.forEach(child => {
            const source = child.id;
            edges.forEach(edge => {
              const targetNode = edge.getTargetNode();
              if (targetNode && targetNode.id) {
                if (edge.getSourceNode()?.id === source) {
                  targets.push(targetNode.id);
                }
              }
            });
          });
          
          let currentY = container.getBBox().y;

          if(targets && targets.length > 0){
            let lastParentId = ' ';
            targets.forEach(target => {
              if (target) {
                const currentNode = this.graph?.getCellById(target);
                if (currentNode) {
                  const parent = currentNode.getParent();
                  if (parent) {
                    if(lastParentId !== parent.id){  
                      parent.setProp({
                        position: {
                          x: container.getBBox().x + container.getBBox().width + spacing,
                          y: currentY
                        }
                      });

                      currentY +=  parent.getBBox().height + this.getNeededSpace(parent.id);
                      this.setTreePosition(parent.id , parent.getBBox().x, spacing);
                    }

                    lastParentId = parent.id;
                  } 
                }
              }
            });
          }
        }
      }
    }
  }

  public setTreePosition(containerId: string, initialX: number, spacing: number, visited: Set<string> = new Set(),depth = 0) {
    let lastY = 0;
    if (this.graph) {
      const container = this.graph.getCellById(containerId);
      if (!container) return 0;

      lastY = container.getBBox().y + container.getBBox().height;

      const children = container.getChildren();
      const edges = this.graph.getEdges();
      let targets: (string | undefined)[] = [];
      
      if (children && edges && edges.length > 0) {
        children.forEach(child => {
          if(!visited.has(child.id)){
            const source = child.id;
            visited.add(source);
            edges.forEach(edge => {
            const targetNode = edge.getTargetNode();
            if (targetNode && targetNode.id) {
              if (edge.getSourceNode()?.id === source) 
                targets.push(targetNode.id);
            }
          });
          }
        });

        initialX = container.getBBox().x + container.getBBox().width + spacing;
        let currentY = container.getBBox().y;

        if(targets && targets.length > 0){
          targets.forEach(target => {
            if (target) {
              const currentNode = this.graph?.getCellById(target);
              if (currentNode) {
                const parent = currentNode.getParent();
                if (parent) {
                  parent.setProp({
                    position: {
                      x: initialX,
                      y: currentY
                    }
                  })
                  const childLastY = this.setTreePosition(parent.id, parent.getBBox().x, spacing, visited ,depth + 1);
                  currentY = childLastY;
                } 
              }
            }
          });
        }
      }
    }
    return lastY;
  }

  public getNeededSpace(containerId: string, visited: Set<string> = new Set()) {
    let maxHeight = 0; 
    let currentMaxHeight = 0;
    if (this.graph) {
      const container = this.graph.getCellById(containerId);
      if (!container) return 0;

      maxHeight = container.getBBox().height;
      
      const children = container.getChildren();
      const edges = this.graph.getEdges();
      let targets: (string | undefined)[] = [];
      
      if (children && edges && edges.length > 0) {
        children.forEach(child => {
          if(!visited.has(child.id)){
            const source = child.id;
            visited.add(source);
            edges.forEach(edge => {
              const targetNode = edge.getTargetNode();
              if (targetNode && targetNode.id) {
                if (edge.getSourceNode()?.id === source)
                  targets.push(targetNode.id);
              }
            });
          }
        });

        if(targets && targets.length > 0){
          targets.forEach(target => {
            if (target) {
              const currentNode = this.graph?.getCellById(target);
              if (currentNode) {
                const parent = currentNode.getParent();
                if (parent) {
                  currentMaxHeight += this.getNeededSpace(parent.id, visited);
                  if(currentMaxHeight > maxHeight)
                    maxHeight = currentMaxHeight;
                } 
              }
            }
          });
        }
      }
    }

    return maxHeight;
  }

  public centerChildrenHorizontally(id: string, startX: number, childSpacing: number){
    if (this.graph) {
      const currentNode = this.graph.getCellById(id);
      if(currentNode){
        const children = currentNode.getChildren();
        const currentNodeBBox = currentNode.getBBox();
        const parentCenterY = currentNodeBBox.y + currentNodeBBox.height / 2; 
        if (children) {
          let currentX = currentNodeBBox.x + startX; 
  
          children.forEach(child => {
            const bbox = child.getBBox();
        
            child.setProp({
                position: {
                  x: currentX,
                  y: parentCenterY - bbox.height / 2 
                }
            });
  
            currentX += bbox.width + childSpacing; 
          });
        }
      }
    } 
  }

  public centerChildrenVertically(id: string, childSpacing: number){
    if (this.graph) {
      const currentNode = this.graph.getCellById(id);
      if(currentNode){
        const children = currentNode.getChildren();
        const currentNodeBBox = currentNode.getBBox();
        const parentCenterX = currentNodeBBox.x + currentNodeBBox.width / 2; 

        if (children) {
          let currentY = currentNodeBBox.y + childSpacing; 

          children.forEach(child => {
            const bbox = child.getBBox();
        
            child.setProp({
                position: {
                  x: parentCenterX - bbox.width / 2,
                  y: currentY 
                }
            });

            currentY += bbox.height + childSpacing; 
          });
        }
      }
    } 
  }

  public setPositionAbsoluteParent(parentsId: string){
    if(this.graph){
       const parsedIds = JSON.parse(parentsId); 
      let currentX = 20;
      for(let i = 0 ; i < parsedIds.length ; i++){
        const nodeParent = this.graph.getCellById(parsedIds[i]);
        if(nodeParent){
          nodeParent.setProp({position : { x: currentX }});
          currentX = currentX + nodeParent.getBBox().width + 180;
        }
      }
    }
  }

  
  public setNodeLabelStateProperty(){
    this.nodes_label_state = !this.nodes_label_state;
  }

  public updateNodesLabelState(){
    if(this.graph){
      let nodes = this.graph.getNodes();
      if(nodes.length > 0){
        if(this.nodes_label_state){
          nodes.forEach((node) => {
            node.setAttrs({
              label:{
                visibility: 'hidden',
              }
            });
          });
          this.setNodeLabelStateProperty();
        }else{
          nodes.forEach((node) => {
            node.setAttrs({
              label:{
                visibility: 'visible',
              }
            });
          });
          this.setNodeLabelStateProperty();
        }
      }
    }
  }

  public setNodesLabelColorProperty(){
    this.nodes_label_color_toggle = !this.nodes_label_color_toggle;
  }

  public updateNodesLabelColor(){
    if(this.graph){
      let nodes = this.graph.getNodes();
      if(nodes.length > 0){
        if(!this.nodes_label_color_toggle){
          nodes.forEach((node) => {
            node.setAttrs({
              label:{
                fill: '#000000',
                refY : '100%' ,
                filter: {
                  name: 'outline',
                  args: {
                    color: this.nodes_label_bgColor, 
                    width: 3, 
                    margin: 2, 
                    opacity: 1.0
                  }
                }
              }
            });
          });
          this.setNodesLabelColorProperty();
        }else{
          nodes.forEach((node) => {
            node.setAttrs({
              label:{
                fill: '#000000',
                filter: null,
              },
            });
          });
          this.setNodesLabelColorProperty();
        }
      }
    }
  }

  /*
  * End of  methods that will be deleted very soon and implemented in Java. 
  */

  protected render(): unknown {
    return html`
      <div id="canvas" style="height: 100%; width: 100%;"></div>
  
      ${this.minimap_div ? html`
        <div id="minimap"></div>
      ` : ''}
  
      ${this.context_menu_div? html`
        <div id="contextMenu">
          <ul>
            <li id="bringToFront">Bring to Front</li>
            <li id="sendToBack">Send to Back</li>
            <li id="moveUp">Move up one level</li>
            <li id="moveDown">Move down one level</li>
          </ul>
        </div>
      ` : ''}
    `;
  }
}

declare global {
  interface HTMLElementTagNameMap {
    'x-6': X6;
  }
}
