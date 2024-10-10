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
 * Represents a cell in the X6 graph model.
 * This abstract class serves as a base for {@link X6Node} and {@link X6NodeBackground}.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public abstract class X6Cell {
    private String id;
    private Geometry geometry;
    private String shape;
    private String imgUrl;
    private String colorFill;
    private boolean movable;
    private boolean port;
    private int zIndex;
    
    public X6Cell(){
        this.geometry = new Geometry();
    }
    
    public X6Cell(String id, String shape, double x, double y,double width, double height, boolean movable) {
        this.id = id;
        this.shape = shape;
        this.geometry = new Geometry(x, y, width, height);
        this.imgUrl = "";
        this.colorFill = "";
        this.movable = movable;
        this.port = false;
        this.zIndex = 1;
    }
    
    public X6Cell(String id, String shape, double x, double y,double width, double height, String imgUrl, boolean port, int zIndex) {
        this.id = id;
        this.shape = shape;
        this.geometry = new Geometry(x, y, width, height);
        this.imgUrl = imgUrl;
        this.colorFill = "";
        this.movable = true;
        this.port = port;
        this.zIndex = zIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getColorFill() {
        return colorFill;
    }

    public void setColorFill(String colorFill) {
        this.colorFill = colorFill;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public boolean isPort() {
        return port;
    }

    public void setPort(boolean port) {
        this.port = port;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }
    
    @Override
    public String toString() {
        return "X6Cell{" + "id=" + id + ", geometry=" + geometry + ", shape=" + shape + ", imgUrl=" + imgUrl + '}';
    }
}
