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
import java.util.List;

/**
 * Represents a connection between two or more nodes in the X6 graph with one label.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6Edge extends X6Cell{
    private String idSource;
    private String idTarget;
    private List<Vertex> vertices;
    private List<X6EdgeLabel> labels;
    private X6EdgeStyles styles;
    
    public X6Edge(){
        super();
        super.setCellType(X6Constants.CELL_EDGE);
        this.vertices = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.styles = new X6EdgeStyles();
    }
    
    //Create an edge without labels
    public X6Edge(String id, String idSource, String idTarget){
        super(id);
        this.idSource = idSource;
        this.idTarget = idTarget;
        super.setCellType(X6Constants.CELL_EDGE);
        this.vertices = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.styles = new X6EdgeStyles();
    }
    
    //Create an edge with one label
    public X6Edge(String id, String idSource, String idTarget, String label){
        super(id);
        this.idSource = idSource;
        this.idTarget = idTarget;
        super.setCellType(X6Constants.CELL_EDGE);
        this.vertices = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.styles = new X6EdgeStyles();
        addLabel(label, 0.5);
    }
    
    //Create and edge with multiple labels
    public X6Edge(String id, String idSource, String idTarget, List<X6EdgeLabel> labels){
        super(id);
        this.idSource = idSource;
        this.idTarget = idTarget;
        super.setCellType(X6Constants.CELL_EDGE);
        this.vertices = new ArrayList<>();
        this.labels = labels;
        this.styles = new X6EdgeStyles();
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

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<X6EdgeLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<X6EdgeLabel> labels) {
        this.labels = labels;
    }

    public X6EdgeStyles getEdgeStyles() {
        return styles;
    }

    public void setEdgeStyles(X6EdgeStyles styles) {
        this.styles = styles;
    }
    
    public void addLabel(String label, double distance){
        if(labels != null)
            labels.add(new X6EdgeLabel(label, distance));
    }
    
    public X6EdgeLabel getLabelAt(int labelPos){
        if(labels != null && labels.size() > labelPos && labels.get(labelPos) != null)
            return labels.get(labelPos);
        return null;
    }
    
}