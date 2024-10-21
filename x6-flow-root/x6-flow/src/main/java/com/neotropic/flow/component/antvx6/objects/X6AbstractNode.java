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

/**
 * Represents a basic node in the X6 graph model.
 * This abstract class serves as a base for {@link X6Node}, {@link X6NodeBackground} and {@link X6NodeText}.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public abstract class X6AbstractNode extends X6Cell { 
    private String imgUrl;
    private String fillColor;
    private String strokeColor;
    private double strokeWidth;
    private String labelText;
    private String labelPosition;
    private boolean movable;
    private int zIndex;
    private String parentId;
    
    public X6AbstractNode(){
        super();
    }
    
    public X6AbstractNode(String id, double x, double y,double width, double height, String shape){
        super(id, x, y, width, height, shape);
        this.imgUrl = "";
        this.fillColor = "white";
        this.strokeColor = "black";
        this.strokeWidth = 1;
        this.labelText = "";
        this.labelPosition = X6Constants.LABEL_NODE_POSITION_DEFAULT;
        this.movable = true;
        this.zIndex = 0;
        this.parentId = "";
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public String getLabelPosition() {
        return labelPosition;
    }

    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public void setBorderRadius(int borderRadius) {
        super.setBorderRadius(borderRadius);
    }

    @Override
    public int getBorderRadius() {
        return super.getBorderRadius(); 
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
