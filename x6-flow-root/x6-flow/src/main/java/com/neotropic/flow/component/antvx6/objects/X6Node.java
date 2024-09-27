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

/**
 * Represents a node in the X6 graph.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6Node extends X6Cell{
    private String labelText;
    private String labelColor;
    
    public X6Node(){
        super();
    }

    public X6Node(String id, String shape, double x, double y,double width, double height, String imgUrl, String labelText, String labelColor) {
        super(id, shape, x, y ,width, height, imgUrl);
        this.labelText = labelText;
        this.labelColor = labelColor;
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
    public String getShape() {
        return super.getShape();
    }

    @Override
    public void setShape(String shape) {
        super.setShape(shape);
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
    public String getImgUrl() {
        return super.getImgUrl();
    }

    @Override
    public void setImgUrl(String imgUrl) {
        super.setImgUrl(imgUrl);
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }
}
