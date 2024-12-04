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


/**
 * Represents a text node in the X6 graph.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6NodeText extends X6AbstractNode{
    // Position relative to its node parent
    private String labelPositionRelative;
    
    public X6NodeText(){
        super();
    }
    
    public X6NodeText(String id, double x, double y, double width, double height, String shape) {
        super(id, x, y, width, height, shape);
    }
    
    public String getLabelPositionRelative(){
        return labelPositionRelative;
    }
    
    public void setLabelPositionRelative(String labelPositionRelative){
        this.labelPositionRelative = labelPositionRelative;
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
    public Geometry getGeometry() {
        return super.getGeometry(); 
    }

    @Override
    public void setGeometry(Geometry geometry) {
        super.setGeometry(geometry); 
    }

    @Override
    public String getShape() {
        return super.getShape(); 
    }

    @Override
    public void setShape(String shape) {
        super.setShape(shape); 
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
    public void setzIndex(int zIndex) {
        super.setzIndex(zIndex); 
    }

    @Override
    public int getzIndex() {
        return super.getzIndex(); 
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
    public void setLabelPosition(String labelPosition) {
        super.setLabelPosition(labelPosition); 
    }

    @Override
    public String getLabelPosition() {
        return super.getLabelPosition(); 
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
    public void setStrokeWidth(double strokeWidth) {
        super.setStrokeWidth(strokeWidth); 
    }

    @Override
    public double getStrokeWidth() {
        return super.getStrokeWidth(); 
    }

    @Override
    public void setStrokeColor(String strokeColor) {
        super.setStrokeColor(strokeColor); 
    }

    @Override
    public String getStrokeColor() {
        return super.getStrokeColor(); 
    }

    @Override
    public void setFillColor(String fillColor) {
        super.setFillColor(fillColor); 
    }

    @Override
    public String getFillColor() {
        return super.getFillColor(); 
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
    public int getBorderRadius() {
        return super.getBorderRadius(); 
    }

    @Override
    public void setBorderRadius(int borderRadius) {
        super.setBorderRadius(borderRadius); 
    }
  
}