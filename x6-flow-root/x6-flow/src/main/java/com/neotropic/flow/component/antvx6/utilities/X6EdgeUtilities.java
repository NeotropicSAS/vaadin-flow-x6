/*
 * Copyright 2025 Neotropic SAS.
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
package com.neotropic.flow.component.antvx6.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neotropic.flow.component.antvx6.constants.X6Constants;
import com.neotropic.flow.component.antvx6.objects.Vertex;
import com.neotropic.flow.component.antvx6.objects.X6EdgeBasic;
import com.neotropic.flow.component.antvx6.styles.X6EdgeStyles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Utilities to X6 edges
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
public class X6EdgeUtilities {
    
    /**
    * Converts a JSON string representing a list of vertices into a list of Vertex objects.
    *
    * @param verticesJSONformat A JSON string containing the vertex data.
    */
    public static List<Vertex>  JSONtoVertices(String verticesJSONformat) {
        JsonArray jsonArray = JsonParser.parseString(verticesJSONformat).getAsJsonArray();
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject vertexObject = jsonArray.get(i).getAsJsonObject();
            double x = vertexObject.get("x").getAsDouble();
            double y = vertexObject.get("y").getAsDouble();
            vertices.add(new Vertex(x, y));
        }
        return vertices;
    }
    
    /**
    * Maps the styles defined in the class to the edge's styles HashMap.
    * 
    * @param edge the X6EdgeBasic instance whose styles will be mapped
    */
    public static void setStylesToMap(X6EdgeBasic edge){
        if(edge == null)
            return;
        
        if(edge.getEdgeStyles() == null)
            edge.setEdgeStyles(new X6EdgeStyles());
        
        if(edge.getStyles() == null)
            edge.setStyles(new HashMap<>());
        
        //Copy X6EdgeStyles to hashMap<String, String>
        //Stroke color
        String strokeColor = edge.getEdgeStyles().getStrokeColor();
        if (strokeColor == null || strokeColor.isBlank()) {
            strokeColor = "black";
            edge.getEdgeStyles().setStrokeColor(strokeColor);
        }
        edge.setStyle(X6Constants.STYLE_STROKECOLOR, strokeColor);
        
        //Dash
        double dash = edge.getEdgeStyles().getDash();
        edge.getEdgeStyles().setDash(dash);
        edge.setStyle(X6Constants.STYLE_DASHED, dash + "");
        
        //Border radius
        int borderRadius = edge.getEdgeStyles().getBorderRadious();
        edge.getEdgeStyles().setBorderRadious(borderRadius);
        edge.setStyle(X6Constants.STYLE_ROUNDED, borderRadius + "");
        
        //Stroke width
        double strokeWidth = edge.getEdgeStyles().getStrokeWidth();
        edge.setStyle(X6Constants.STYLE_STROKEWIDTH, strokeWidth + "");
        
        //Font size
        double fontSize = edge.getEdgeStyles().getLabelFontSize();
        edge.setStyle(X6Constants.STYLE_FONTSIZE, fontSize + "");
        
        //Font color
        String fontColor = edge.getEdgeStyles().getLabelTextColor();
        if (fontColor == null || fontColor.isBlank()) {
            fontColor = "black";
            edge.getEdgeStyles().setLabelTextColor(fontColor);
        }
        edge.setStyle(X6Constants.STYLE_FONTCOLOR, fontColor);
        
        //Font family
        String fontFamily = edge.getEdgeStyles().getLabelFontFamily();
        if (fontFamily == null || fontFamily.isBlank()) {
            fontFamily = "Arial";
            edge.getEdgeStyles().setLabelFontFamily(fontFamily);
        }
        edge.setStyle(X6Constants.STYLE_FONTFAMILY, fontFamily);
    }
    
    /**
    * Updates the styles of the class object from the provided styles HashMap.
    *
    * @param edge the X6BasicEdge instance to update with the styles.
    * @param stylePropertyMap a HashMap containing style properties.
    */
    public static void setEdgeStyles(X6EdgeBasic edge, HashMap<String, String> stylePropertyMap){
        if (stylePropertyMap.containsKey(X6Constants.STYLE_STROKECOLOR) && !stylePropertyMap.get(X6Constants.STYLE_STROKECOLOR).isBlank())
            edge.getEdgeStyles().setStrokeColor(stylePropertyMap.get(X6Constants.STYLE_STROKECOLOR));
        else
            edge.getEdgeStyles().setStrokeColor(stylePropertyMap.get("black"));

        if (stylePropertyMap.containsKey(X6Constants.STYLE_DASHED) && !stylePropertyMap.get(X6Constants.STYLE_DASHED).isBlank()) {
            if (!"0".equals(stylePropertyMap.get(X6Constants.STYLE_DASHED)) && !"0.0".equals(stylePropertyMap.get(X6Constants.STYLE_DASHED)))
                edge.getEdgeStyles().setDash(5);
            else
                edge.getEdgeStyles().setDash(0);
        }else
            edge.getEdgeStyles().setDash(0);

        if (stylePropertyMap.containsKey(X6Constants.STYLE_ROUNDED) && !stylePropertyMap.get(X6Constants.STYLE_ROUNDED).isBlank()) {
            if ("0".equals(stylePropertyMap.get(X6Constants.STYLE_ROUNDED)))
                edge.getEdgeStyles().setBorderRadious(0);
            else
                edge.getEdgeStyles().setBorderRadious(12); 
        }else
            edge.getEdgeStyles().setBorderRadious(0);

        if (stylePropertyMap.containsKey(X6Constants.STYLE_STROKEWIDTH) && !stylePropertyMap.get(X6Constants.STYLE_STROKEWIDTH).isBlank()){
            double strokeWidth = Double.parseDouble(stylePropertyMap.get(X6Constants.STYLE_STROKEWIDTH));
            edge.getEdgeStyles().setStrokeWidth(strokeWidth);
        }else
            edge.getEdgeStyles().setStrokeWidth(1.0);
        
        if (stylePropertyMap.containsKey(X6Constants.STYLE_FONTSIZE) && !stylePropertyMap.get(X6Constants.STYLE_FONTSIZE).isBlank()) {
            double fontSize = Double.parseDouble(stylePropertyMap.get(X6Constants.STYLE_FONTSIZE));
            edge.getEdgeStyles().setLabelFontSize(fontSize);
        }else
            edge.getEdgeStyles().setLabelFontSize(14.0);

        if (stylePropertyMap.containsKey(X6Constants.STYLE_FONTCOLOR) && !stylePropertyMap.get(X6Constants.STYLE_FONTCOLOR).isBlank())
            edge.getEdgeStyles().setLabelTextColor(stylePropertyMap.get(X6Constants.STYLE_FONTCOLOR));
        else
            edge.getEdgeStyles().setLabelTextColor("black");
        
        if (stylePropertyMap.containsKey(X6Constants.STYLE_FONTFAMILY) && !stylePropertyMap.get(X6Constants.STYLE_FONTFAMILY).isBlank())
            edge.getEdgeStyles().setLabelFontFamily(stylePropertyMap.get(X6Constants.STYLE_FONTFAMILY));
        else
            edge.getEdgeStyles().setLabelFontFamily("Helvetica");
    }
}
