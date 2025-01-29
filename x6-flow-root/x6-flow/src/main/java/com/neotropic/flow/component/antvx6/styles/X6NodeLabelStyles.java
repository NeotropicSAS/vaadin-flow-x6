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

import com.neotropic.flow.component.antvx6.constants.X6Constants;

/**
 * Represents the label styles of a node
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6NodeLabelStyles {
    private String fontColor;
    private double fontSize;
    private String fontFamily;
    private String labelPosition;
    private String visibility;
    
    public X6NodeLabelStyles(){
        this.fontColor = "black";
        this.fontSize = 14;
        this.fontFamily = "Arial";
        this.labelPosition = X6Constants.LABEL_NODE_POSITION_DEFAULT;
        this.visibility = X6Constants.LABEL_NODE_VISIBLE;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getLabelPosition() {
        return labelPosition;
    }

    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    
}
