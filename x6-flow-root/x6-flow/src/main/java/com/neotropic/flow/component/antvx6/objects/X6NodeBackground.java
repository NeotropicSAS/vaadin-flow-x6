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

import com.neotropic.flow.component.antvx6.styles.X6LabelStyles;
import com.neotropic.flow.component.antvx6.styles.X6NodeStyles;

/**
 * Represents a background node in the X6 graph.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6NodeBackground extends X6AbstractNode {

    public X6NodeBackground(){
        super();
    }
    
    public X6NodeBackground(String id, double x, double y,double width, double height, String shape){
        super(id, x, y, width, height, shape);
    }

    @Override
    public void setNodeStyles(X6NodeStyles nodeStyles) {
        super.setNodeStyles(nodeStyles);
    }

    @Override
    public X6NodeStyles getNodeStyles() {
        return super.getNodeStyles();
    }

    @Override
    public void setLabelStyles(X6LabelStyles labelStyles) {
        super.setLabelStyles(labelStyles);
    }

    @Override
    public X6LabelStyles getLabelStyles() {
        return super.getLabelStyles();
    }

    @Override
    public void setLabelText(String labelText) {
        super.setLabelText(labelText); 
    }

    @Override
    public String getLabelText() {
        return super.getLabelText(); 
    }
    
    @Override
    public void setParentId(String parentId) {
        super.setParentId(parentId); 
    }

    @Override
    public String getParentId() {
        return super.getParentId(); 
    }
    
    @Override
    public void setMovable(boolean movable) {
        super.setMovable(movable); 
    }

    @Override
    public boolean isMovable() {
        return super.isMovable(); 
    }

    @Override
    public void setImgUrl(String imgUrl) {
        super.setImgUrl(imgUrl); 
    }

    @Override
    public String getImgUrl() {
        return super.getImgUrl(); 
    }

    @Override
    public void setShape(String shape) {
        super.setShape(shape); 
    }

    @Override
    public String getShape() {
        return super.getShape(); 
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
