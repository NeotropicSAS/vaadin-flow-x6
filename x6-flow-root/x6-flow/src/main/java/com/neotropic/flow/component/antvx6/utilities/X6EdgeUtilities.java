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
import com.neotropic.flow.component.antvx6.objects.Vertex;
import java.util.ArrayList;
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
}
