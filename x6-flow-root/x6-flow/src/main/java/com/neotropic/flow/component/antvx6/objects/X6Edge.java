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
import java.util.HashMap;
import java.util.List;

/**
 * Represents a connection between two or more nodes in the X6 graph with one label.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6Edge extends X6EdgeBasic{
    private String label;
    
    public X6Edge(){
        super();
    }

    public X6Edge(String id, String idSource, String idTarget, String label) {
        super(id, idSource, idTarget);
        super.setCellType(X6Constants.CELL_EDGE);
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    @Override
    public void addVertex(Vertex vertex) {
        super.addVertex(vertex); 
    }

    @Override
    public void setVertices(List<Vertex> vertices) {
        super.setVertices(vertices); 
    }

    @Override
    public List<Vertex> getVertices() {
        return super.getVertices(); 
    }

    @Override
    public void setEdgeStyles(X6EdgeStyles edgeStyles) {
        super.setEdgeStyles(edgeStyles); 
    }

    @Override
    public X6EdgeStyles getEdgeStyles() {
        return super.getEdgeStyles(); 
    }

    @Override
    public void setIdTarget(String idTarget) {
        super.setIdTarget(idTarget); 
    }

    @Override
    public String getIdTarget() {
        return super.getIdTarget(); 
    }

    @Override
    public void setIdSource(String idSource) {
        super.setIdSource(idSource); 
    }

    @Override
    public String getIdSource() {
        return super.getIdSource(); 
    }
    
}
