/*
 * Copyright 2024 Neotropic SAS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neotropic.flow.component.antvx6.objects;

import com.neotropic.flow.component.antvx6.constants.X6Constants;
import com.neotropic.flow.component.antvx6.styles.X6LabelStyles;
import com.neotropic.flow.component.antvx6.styles.X6NodeStyles;

/**
 * Represents a basic node in the X6 graph model.
 * This abstract class serves as a base for {@link X6Node}, {@link X6NodeBackground} and {@link X6NodeText}.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public abstract class X6AbstractNode extends X6Cell { 
    private String shape;
    private String imgUrl;
    private boolean movable; 
    private String parentId;
    private String labelText;
    private X6LabelStyles labelStyles;
    private X6NodeStyles nodeStyles;
    
    public X6AbstractNode(){
        super();
        this.setCellType(X6Constants.CELL_NODE);
        this.labelStyles = new X6LabelStyles();
        this.nodeStyles = new X6NodeStyles();
    }
    
    public X6AbstractNode(String id, double x, double y,double width, double height, String shape){
        super(id, x, y, width, height);
        this.setCellType(X6Constants.CELL_NODE);
        this.shape = shape;
        this.imgUrl = "";
        this.movable = true;
        this.parentId = "";
        this.labelText = "";
        this.labelStyles = new X6LabelStyles();
        this.nodeStyles = new X6NodeStyles();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public X6LabelStyles getLabelStyles() {
        return labelStyles;
    }

    public void setLabelStyles(X6LabelStyles labelStyles) {
        this.labelStyles = labelStyles;
    }

    public X6NodeStyles getNodeStyles() {
        return nodeStyles;
    }

    public void setNodeStyles(X6NodeStyles nodeStyles) {
        this.nodeStyles = nodeStyles;
    }
    
    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getShape() {
        return this.shape; 
    }

    @Override
    public String getCellType() {
        return super.getCellType(); 
    }
    
    @Override
    public void setGeometry(Geometry geometry) {
        super.setGeometry(geometry); 
    }

    @Override
    public Geometry getGeometry() {
        return super.getGeometry(); 
    }

    @Override
    public void setId(String id) {
        super.setId(id); 
    }

    @Override
    public String getId() {
        return super.getId(); 
    }
    
}
