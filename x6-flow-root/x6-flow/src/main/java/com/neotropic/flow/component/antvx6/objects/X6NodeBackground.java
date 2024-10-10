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
 * Represents a background node in the X6 graph.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6NodeBackground extends X6Cell {
    
    public X6NodeBackground(){
        super();
    }

    public X6NodeBackground(String id, String shape, double x, double y,double width, double height, String imgUrl) {
        super(id, shape, x, y ,width, height, imgUrl,false, 1);
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
    
    @Override
    public String getColorFill(){
        return super.getColorFill();
    }
    
    @Override
    public void setColorFill(String colorFill){
        super.setColorFill(colorFill);
    }
    
    @Override
    public boolean isMovable(){
        return super.isMovable();
    }
    
    @Override
    public void setMovable(boolean movable){
        super.setMovable(true);
    }
    
    @Override
    public boolean isPort(){
        return super.isPort();
    }
    
    @Override
    public void setPort(boolean port){
        super.setPort(port);
    }
    
    @Override
    public int getzIndex(){
        return super.getzIndex();
    }
    
    @Override
    public void setzIndex(int zIndex){
        super.setzIndex(zIndex);
    }
}
