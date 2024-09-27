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

import {LitElement, customElement, html, css, property } from 'lit-element';
import { Graph } from '@antv/x6';
import{ Snapline }from'@antv/x6-plugin-snapline';
import { Transform } from '@antv/x6-plugin-transform';
import { Export } from '@antv/x6-plugin-export'; 
import { Selection } from '@antv/x6-plugin-selection'
import { Scroller } from '@antv/x6-plugin-scroller' 

/**
 * Represents a 2D coordinate with x and y values.
 */
interface Coordinate{
  x: number;
  y: number;
}

/**
 * Represents the dimensions of a rectangular area.
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
* Represents the cell in the x6 graph model.
* it serves as a base for X6Node and X6NodeBackground.
*/
interface X6Cell{
  id: string;
  shape: string;
  geometry: Geometry;
  imgUrl: string;
}

/**
 * Represents the background of a node in a graph.
 */
interface X6NodeBackground extends X6Cell{

}

/**
 * Represents a node in a graph.
 */
interface X6Node extends X6Cell{
  labelText: string;
  labelColor: string;
}

/**
 * Represents an edge connecting two nodes in a graph.
 */
interface X6Edge{
  id: string;
  idSource: string;
  idTarget: string;
  label: string;
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

  `;

  /**
  * The width of the graph.
  * @type {number}
  */
  @property()
  graph_width: number = 0;

  /**
   * The height of the graph.
   * @type {number}
   */
  @property()
  graph_height: number = 0;

  /**
  * The background color of the graph.
  * @type {string}
  * @default '#edf6f9'
  */
  @property()
  graph_background_color: string = '#edf6f9';

  /**
  * The ID of the node background image for the graph.
  * @type {string}
  */
  @property()
  graph_node_background_id: string = '';

  /**
  * Whether to show the grid on the graph.
  * @type {boolean}
  * @default true
  */
  @property()
  graph_grid: boolean = true;

  /**
  * Whether panning is enabled on the graph.
  * @type {boolean}
  * @default false
  */
  @property()
  graph_panning: boolean = false;

  /**
  * Whether mouse wheel zooming is enabled on the graph.
  * @type {boolean}
  * @default false
  */
  @property()
  graph_mouse_wheel: boolean = false;

  /**
  * Whether to display node labels.
  * @type {boolean}
  * @default true
  */
  @property()
  nodes_label_state: boolean = true;

  /**
  * Toggles the color of node labels.
  * @type {boolean}
  * @default false
  */
  @property()
  nodes_label_color_toggle: boolean = false;

  /**
  * The background color of node labels.
  * @type {string}
  * @default '#15ed32'
  */
  @property()
  nodes_label_bgColor: string = '#15ed32';
  
  /**
  * The padding for exporting the graph as a JPEG image.
  * @type {number}
  * @default 20
  */
  @property()
  padding_export_graph_JPEG: number = 20;

  /**
  * The zoom level for the graph.
  * @type {number}
  * @default -0.1
  */
  @property()
  graph_zoom: number = -0.1;

  /**
  * The graph instance.
  * @type {Graph | null}
  * @private
  */
  private graph: Graph | null = null;

  /**
  * The transform plugin instance.
  * @type {Transform | null}
  * @private
  */
  private transformPlugin: Transform | null = null;

  /**
  * The scroller plugin instance.
  * @type {Scroller | null}
  * @private
  */
  private scrollerPlugin: Scroller | null = null;
  
  firstUpdated() {
    const container = this.shadowRoot!.getElementById('container');
    if (container) {
      this.graph = new Graph({
        container,
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

      this.graph.zoom(this.graph_zoom);

      /**
      * Initializes the plugins used for graph manipulation and interaction.
      */
      this.scrollerPlugin = new Scroller({
        enabled:true,
        pannable: true,
        pageVisible: true,
        pageBreak: true,
        width:600,
        height: 600,
        pageWidth: 620,
        pageHeight: 620,
        padding: 0,
        autoResize : true , 
        
      });

      this.graph.use(this.scrollerPlugin);
      this.graph.setScrollbarPosition(600,600);
      
      this.graph.use(new Export());

      this.graph.use(new Snapline({enabled: true}));

      this.transformPlugin = new Transform({
        rotating: false,
        resizing: {
          enabled: true,
          orthogonal: true,
          minWidth: 20,
          minHeight: 20,
          preserveAspectRatio: false,
        },
      });

      this.graph.use(this.transformPlugin);

      this.graph.use(new Selection({
        enabled:true,
        multiple : false , 
        movable : true , 
        showNodeSelectionBox : true , 
      })); 

      this.createGhost();

      /**
      * Initializes the event listeners for various cell interactions.
      * This includes selecting cells, drawing edges, and managing node positions and dimensions.
      */
      this.eventCellSelected();
      this.eventDrawEdgeObjectView();
      this.eventGetNodeNewPosition();
      this.eventGetNodeBackgroundNewDimensions();
      this.eventResizeNodeBackgroundDblClick();

      /**
      * Dispatches a custom event indicating that the graph has been created.
      *
      * @fires graph-created
      * @param {Object} detail - Contains additional information about the event.
      * @param {string} detail.status - The status of the graph creation.'.
      */
      this.dispatchEvent(new CustomEvent('graph-created', {
        detail: {
          status: 'success'
        }
      }));
    }
  }

  /**
  * Clears the graph by removing all cells and creating a ghost node.
  * Dispatches an event to indicate that the graph has been cleaned.
  * 
  * @fires graph-cleaned
  * @param {Object} detail - Contains the state of the cleaning operation.
  * @param {string} detail.state - The state of the operation.
  */
  public cleanGraph(){
    if(this.graph){
      this.graph.clearCells();
      this.createGhost();
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
      if(nodeCell){
        this.graph.centerCell(nodeCell);
      }
    }
  }

  /**
  * Refreshes the graph by clearing it and restoring its previous state.
  * 
  * This method creates a backup of the current graph, clears it, 
  * and then re-populates it using the backup data.
  * Dispatches an event to indicate that the graph has been refreshed.
  * 
  * @fires graph-refreshed
  * @param {Object} detail - Contains the state of the refresh operation.
  * @param {string} detail.state - The state of the operation.
  */
  public refreshGraph(){
    if(this.graph){
      const backup = this.graph.toJSON();
      this.cleanGraph();
      this.graph.fromJSON(backup);
      this.dispatchEvent(new CustomEvent('graph-refreshed', {
        detail: {
          state: 'success'
        }
      }));
    }
  }

  /**
  * Selects a node in the graph and centers the view on it.
  * 
  * @param {X6Node} node - The node to select, identified by its ID.
  */
  public selectNode(node: X6Node){
    if(this.graph){
      const nodeCell = this.graph.getCellById(node.id);
      if(nodeCell && node.id !== this.graph_node_background_id){
        this.graph.select(nodeCell);
        this.graph.centerCell(nodeCell);
      }
    }
  }

  /**
  * Sets up an event listener for when a cell is selected in the graph.
  * 
  * If the selected cell is not the background node, it dispatches a 
  * custom event with the cell's ID and type (node or edge).
  * 
  * @fires cell-selected
  * @param {Object} detail - Contains information about the selected cell.
  * @param {string} detail.cell.id - The ID of the selected cell.
  * @param {string} detail.cell.cellType - The type of the selected cell ('node' or 'edge').
  */
  public eventCellSelected(){
    if(this.graph){
      this.graph.on('cell:selected',({cell}) => {   
        if(cell.id !== this.graph_node_background_id){
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
              }
            }
          }));
        }else
          // If the background node is selected, it unselects it.
          this.graph?.unselect(cell);
      })
    }
  }

  /**
  * Sets up an event listener for when the background node is resized.
  * 
  * When the background node is resized, it dispatches a custom event 
  * with the new dimensions (width and height) of the background node.
  * 
  * @fires background-resized
  * @param {Object} detail - Contains the new dimensions of the background node.
  * @param {string} detail.node.id - The ID of the resized background node.
  * @param {number} detail.node.width - The new width of the background node.
  * @param {number} detail.node.height - The new height of the background node.
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

  /**
  * Sets up an event listener for when a node is moved in the graph.
  * 
  * When a node is moved, it dispatches a custom event with the new 
  * position (x and y coordinates) of the moved node.
  * 
  * @fires node-moved
  * @param {Object} detail - Contains the new position of the moved node.
  * @param {string} detail.node.id - The ID of the moved node.
  * @param {number} detail.node.x - The new x-coordinate of the moved node.
  * @param {number} detail.node.y - The new y-coordinate of the moved node.
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
  * Creates a background node in the graph using the specified parameters.
  * 
  * This method adds a node that serves as the background, with properties 
  * such as position, dimensions, and shape based on the provided 
  * X6NodeBackground object.
  * 
  * @param {X6NodeBackground} x6NodeBackground - The background node configuration.
  */
  public createBackground(x6NodeBackground : X6NodeBackground){
    if(this.graph){
      // If another background existed, remove it.
      if(this.graph_node_background_id  !== ''){
          const oldBackground = this.graph.getCellById(this.graph_node_background_id);
          this.graph_node_background_id  = '';
          if(oldBackground)
            this.graph.removeCell(oldBackground);
      }

      this.graph_node_background_id = x6NodeBackground.id;
      this.graph.addNode({
        id:  x6NodeBackground.id,
        shape: x6NodeBackground.shape,
        x: x6NodeBackground.geometry.coordinates.x,
        y: x6NodeBackground.geometry.coordinates.y,
        width: x6NodeBackground.geometry.dimensions.width,
        height: x6NodeBackground.geometry.dimensions.height,
        imageUrl:
          x6NodeBackground.imgUrl,
        zIndex:1
      });
    }
  }

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

  /**
  * Draws a node in the graph's object view using the specified properties.
  * 
  * This method adds a node with attributes such as position, dimensions, 
  * visibility of the label, and port configuration.
  * 
  * @param {X6Node} node - The configuration for the node to be drawn.
  */
  public drawNodeObjectView(node : X6Node){
    if(this.graph){
      // If the label text is empty, the label is set to be hidden.
      let visibility = 'visible';
      if(node.labelText.length == 0)
        visibility = 'hidden'

      this.graph.addNode({
        id: node.id,
        shape: node.shape,
        x: node.geometry.coordinates.x,
        y: node.geometry.coordinates.y,
        width: node.geometry.dimensions.width ,
        height: node.geometry.dimensions.height,
        imageUrl:
          node.imgUrl,
        label: node.labelText,
        attrs: {
          label: {
            fontSize: 12,
            refX: 0.5,
            refY : '100%' , 
            refY2 : 4 , 
            textAnchor: 'middle',
            textVerticalAnchor : 'top' , 
            visibility: visibility,
          },
        },
        ports: {
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
            ],
          },
          zIndex: 2
      });
    }
  }

  /**
  * Draws an edge in the graph's object view using the specified properties.
  * 
  * This method adds an edge connecting two nodes, defined by their 
  * source and target IDs.
  * 
  * @param {X6Edge} edge - The configuration for the edge to be drawn.
  */
  public drawEdgeObjectView(edge: X6Edge){
    if(this.graph){
      this.graph.addEdge(
        {
          id: edge.id,
          source : { cell: edge.idSource},
          target: {cell : edge.idTarget},
          attrs: {
            line: {
              sourceMarker: null,
              targetMarker: null,
            }
          },  
          label:
            {
              attrs: {
                text: {
                  text: edge.label,
                  fontSize: 12,
                  fill: '#000000',
                  textAnchor: 'middle',
                  textVerticalAnchor: 'middle',
                },
                rect: {
                  ref: 'text',
                  refX: -4,
                  refY: -2,
                  refWidth: '100%',
                  refHeight: '100%',
                  refWidth2: 8,
                  refHeight2: 5,
                  stroke: '#000000',
                  strokeWidth: '1',
                  rx: 5,
                  ry: 5,
                },
              },
              position: {
                distance: 0.5,
                offset: 0 ,
              }
            }
        }
      );
    }
  }

  /**
  * Sets up an event listener for when an edge is connected in the graph.
  * 
  * When an edge is connected, it sets the attributes for the edge.
  * dispatches a custom event with details about the newly created edge.
  * 
  * @fires edge-created
  * @param {Object} detail - Contains information about the created edge.
  * @param {string} detail.edge.id - The ID of the created edge.
  * @param {string} detail.edge.idSource - The ID of the source node for the edge.
  * @param {string} detail.edge.idTarget - The ID of the target node for the edge.
  */
  public eventDrawEdgeObjectView(){
    if(this.graph){
      this.graph.on('edge:connected', ({ edge }) => {
        edge.setAttrs(
          {
            line: {
              sourceMarker: null,
              targetMarker: null,
            }
          }
        )

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

      });
    }
  }

  /**
  * Toggles the state of the node label visibility property.
  */
  public setNodeLabelStateProperty(){
    this.nodes_label_state = !this.nodes_label_state;
  }

  /**
  * Updates the visibility state of the labels for all nodes in the graph.
  * 
  * If the current label state is true, it hides the labels of all nodes; 
  * otherwise, it makes the labels visible.
  */
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

  /**
  * Toggles the state of the node label color property.
  */
  public setNodesLabelColorProperty(){
    this.nodes_label_color_toggle = !this.nodes_label_color_toggle;
  }

  /**
  * Updates the color and appearance of the labels for all nodes in the graph.
  * 
  * If the color toggle state is false, it sets the label fill color to black 
  * and applies an outline effect using the specified background color. If the 
  * toggle state is true, it resets the label appearance to remove the outline.
  */
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

  /**
  * Sets up an event listener for double-click events on the background node.
  * 
  * When the background node is double-clicked, it triggers the creation of a 
  * resizing widget for that background no using the transform plugin.
  */
  public eventResizeNodeBackgroundDblClick(){
    if(this.graph){
      this.graph.on('node:dblclick', ({node}) => {
        if(node.id === this.graph_node_background_id)
          this.transformPlugin?.createWidget(node); // Create resizing widget
      });
    }
  }

  /**
  * Creates a ghost( It's a non-visible and non-manipulable node) to manage the center of the canvas.
  * This node acts as a reference point for positioning the Scroller's bars of the graph.
  */
  public createGhost(){
    if(this.graph){
      const center = this.graph.addNode({
        x: 300,
        y: 300,
        width: 32,
        height: 32,
        shape: 'rect',
        visible: false,
      });

      this.centerGraph(center.id);
    }
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

  /** 
  * The following methods are not used in the add-on; they are intended solely
  * for development and testing purposes within the web component. 
  */

  /**
  * Sets up an event listener for double-click events on edges in the graph.
  * 
  * When an edge is double-clicked, this method checks if the edge already 
  * has an 'edge-editor' tool. If not, it adds the tool with a specified 
  * background color. It then dispatches a custom event indicating that the 
  * edge has been updated.
  * 
  * @fires edge-updated
  * @param {Object} detail - Contains information about the edge update.
  * @param {string} detail.node - Indicates the state of the edge update..
  */
  public eventEditEdgeDblCLick(){
    if(this.graph){
      this.graph.on('edge:dblclick', ({ edge }) => {
        if (!edge.hasTool('edge-editor')) {
          edge.addTools({
              name: 'edge-editor',
              args: {
                attrs: {
                  backgroundColor: '#fff',
                },
              },
          });
        }

        const event = new CustomEvent('edge-updated', {
          detail: { node: 'updated' },
        });
    
        this.dispatchEvent(event);
      });
    }
  }

  /**
  * Sets up an event listener for double-click events on the blank area of the graph.
  * 
  * When the blank area is double-clicked, this method calls the `createRect` 
  * method to add a new rectangle node to the graph.
  */
  public eventCreateNodeDblCLick(){
    if(this.graph){
      this.graph.on('blank:dblclick',() => {
        this.createRect();
      })
    }
  }  

  /**
  * Creates a new rectangle node in the graph at a fixed position.
  * 
  * The new node has a specific size, shape, label, and styling attributes.
  * It also includes a port for connections. After the node is created, it 
  * is centered in the graph.
  */
  public createRect() {
    if (this.graph) {
      let newRec = this.graph.addNode({
        x: 300,
        y: 300,
        width: 32,
        height: 32,
        shape: 'rect',
        label: 'label example of guyana francesa in africa far away',
        attrs: {
          body: {
            fill: '#219ebc',
            stroke: '#219ebc',
          },
          label: {
            fontFamily: 'cursive',
            fontSize: 12,
            refX: 0.5,
            refY : '100%' , 
            refY2 : 4 , 
            textAnchor: 'middle',
            textVerticalAnchor : 'top' , 
          },
        },
        ports: {
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
            ],
          },
          zIndex: 2
      });

      this.centerGraph(newRec.id);
    }
  }

  /**
  * Updates the line color of all edges in the graph.
  * 
  * This method iterates through all edges and sets their stroke color to the 
  * specified color. If there are no edges in the graph, no changes are made.
  * 
  * @param {string} color - The new color to be applied to the edges' line.
  */
  public updateEdgeLineColor(color: string){
    if(this.graph){
      let edges = this.graph.getEdges();
      if(edges.length > 0){
        edges.forEach((edge) => {
          edge.setAttrs({
            line:{
              stroke: color, // Set the stroke color of the edge
            }
          });
        });
      }
    }
  }

  /**
  * Updates the background color of all edges in the graph.
  * 
  * This method iterates through all edges and sets their background fill 
  * color to the specified color. If there are no edges in the graph, no 
  * changes are made.
  * 
  * @param {string} color - The new background color to be applied to the edges.
  */
  public updateEdgeBgColor(color: string){
    if(this.graph){
      let edges = this.graph.getEdges();
      if(edges.length > 0){
        edges.forEach((edge) => {
          edge.setAttrs({
            rect:{
              fill: color, // Set the fill color of the edge's background
            }
          });
        });
      }
    }
  }

  /**
  * Updates the stroke color of all edges in the graph.
  * 
  * This method iterates through all edges and sets their stroke color to 
  * the specified color. If there are no edges in the graph, no changes are 
  * made.
  * 
  * @param {string} color - The new stroke color to be applied to the edges.
  */
  public updateEdgeStrokeColor(color: string){
    if(this.graph){
      let edges = this.graph.getEdges();
      if(edges.length > 0){
        edges.forEach((edge) => {
          edge.setAttrs({
            rect:{
              stroke:color,
            }
          });
        });
      }
    }
  }

  protected render(): unknown {
    return html`
      <div id="container"></div>
      `;
  }

}

declare global {
  interface HTMLElementTagNameMap {
    'x-6': X6;
  }
}
