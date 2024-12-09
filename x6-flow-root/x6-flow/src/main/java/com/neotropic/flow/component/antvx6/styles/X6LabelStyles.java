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
public class X6LabelStyles {
    private String labelTextColor;
    private double labelFontSize;
    private String labelFontFamily;
    private String labelPosition;
    private String labelVisibility;
    
    public X6LabelStyles(){
        this.labelTextColor = "black";
        this.labelFontSize = 14;
        this.labelFontFamily = "Arial";
        this.labelPosition = X6Constants.LABEL_NODE_POSITION_DEFAULT;
        this.labelVisibility = X6Constants.LABEL_NODE_VISIBLE;
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

    public String getLabelPosition() {
        return labelPosition;
    }

    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    public String getLabelVisibility() {
        return labelVisibility;
    }

    public void setLabelVisibility(String labelVisibility) {
        this.labelVisibility = labelVisibility;
    }

}
