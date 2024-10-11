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

package com.neotropic.flow.component.antvx6.demo;

import com.neotropic.flow.component.antvx6.AntvX6;
import com.neotropic.flow.component.antvx6.constants.X6Constants;
import com.neotropic.flow.component.antvx6.objects.Geometry;
import com.neotropic.flow.component.antvx6.objects.X6Node;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
public class MainView extends VerticalLayout {
   
    public MainView() {
        
        /* Create the canvas */
        AntvX6 x6Canvas = new AntvX6();
                
        /* set the dimensions of the canvas (600x600 px). */ 
        x6Canvas.setGrapthWidth(X6Constants.GRAPH_WIDTH);
        x6Canvas.setGraptHeight(X6Constants.GRAPH_HEIGHT);
        x6Canvas.setGrid(true);
        
        /* Add the canvas to the view */
        add(x6Canvas);

        
        /*Create a node object from x6 and set its properties */
        X6Node router = new X6Node();
        router.setId("router-01");
        router.setGeometry(new Geometry(100 ,100 ,32 ,32));
        router.setShape(X6Constants.SHAPE_IMAGE);
        router.setImgUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaZj-3Gf3IOMX44LXAIpUVhghfvXsCEId-EYmMtnvoKLnsMLUQxmuj4EH8aUfYiJoMn9o&usqp=CAU");
        router.setMovable(true);
        router.setPort(true);
        router.setzIndex(1);
        router.setLabelText("router");
        router.setLabelPosition(X6Constants.LABEL_NODE_POSITION_BOTTOM);
        
        x6Canvas.addGraphCreatedListener(evt -> {
            /* When the graph has been created, we draw the node. */
            x6Canvas.drawNode(router);
        });
    }        

}

