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
package com.neotropic.flow.component.antvx6.styles;

/**
 * Represents the styles of an edge
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6EdgeStyles {
    private String labelTextColor;
    private double labelFontSize;
    private String labelFontFamily;
    private String strokeColor;
    private double strokeWidth;
    private double dash;
    private int borderRadious;
    private int zIndex;
    
    public X6EdgeStyles(){
        this.labelTextColor = "black";
        this.labelFontSize = 12;
        this.labelFontFamily = "Arial";
        this.strokeColor = "black";
        this.strokeWidth = 1;
        this.dash = 0;
        this.borderRadious = 0;
        this.zIndex = 1;
    }

    public String getLabelTextColor() {
        return labelTextColor;
    }

    public void setLabelTextColor(String labelTextColor) {
        this.labelTextColor = labelTextColor;
    }

    public double getLabelFontSize() {
        return labelFontSize;
    }

    public void setLabelFontSize(double labelFontSize) {
        this.labelFontSize = labelFontSize;
    }

    public String getLabelFontFamily() {
        return labelFontFamily;
    }

    public void setLabelFontFamily(String labelFontFamily) {
        this.labelFontFamily = labelFontFamily;
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

    public double getDash() {
        return dash;
    }

    public void setDash(double dash) {
        this.dash = dash;
    }

    public int getBorderRadious() {
        return borderRadious;
    }

    public void setBorderRadious(int borderRadious) {
        this.borderRadious = borderRadious;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

}
