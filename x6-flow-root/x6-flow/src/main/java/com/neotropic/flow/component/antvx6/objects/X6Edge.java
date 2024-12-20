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
package com.neotropic.flow.component.antvx6.objects;

import com.neotropic.flow.component.antvx6.constants.X6Constants;
import com.neotropic.flow.component.antvx6.styles.X6EdgeStyles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a connection between two or more nodes in the X6 graph.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6Edge extends X6Cell{
    private String idSource;
    private String idTarget;
    private String label;
    private X6EdgeStyles edgeStyles;
    private List<Vertex> vertices;
    
    public X6Edge(){
        super();
        super.setCellType(X6Constants.CELL_EDGE);
        this.vertices = new ArrayList<>();
        this.edgeStyles = new X6EdgeStyles();
    }

    public X6Edge(String id, String idSource, String idTarget, String label) {
        super(id);
        super.setCellType(X6Constants.CELL_EDGE);
        this.idSource = idSource;
        this.idTarget = idTarget;
        this.label = label;
        this.vertices = new ArrayList<>();
        this.edgeStyles = new X6EdgeStyles();
    }

    @Override
    public String getId() {
        return super.getId(); 
    }

    @Override
    public void setId(String id) {
        super.setId(id); 
    }

    @Override
    public void setStyles(HashMap<String, String> styles) {
        super.setStyles(styles); 
    }

    @Override
    public HashMap<String, String> getStyles() {
        return super.getStyles(); 
    }

    @Override
    public void setCellType(String cellType) {
        super.setCellType(cellType); 
    }

    @Override
    public String getCellType() {
        return super.getCellType(); 
    }

    @Override
    public void setTools(List<String> tools) {
        super.setTools(tools); 
    }

    @Override
    public List<String> getTools() {
        return super.getTools(); 
    }
    
    public String getIdSource() {
        return idSource;
    }

    public void setIdSource(String idSource) {
        this.idSource = idSource;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public X6EdgeStyles getEdgeStyles() {
        return edgeStyles;
    }

    public void setEdgeStyles(X6EdgeStyles edgeStyles) {
        this.edgeStyles = edgeStyles;
    }
    
    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }
    
    public void addVertex(Vertex vertex){
        this.vertices.add(vertex);
    }
    
}
