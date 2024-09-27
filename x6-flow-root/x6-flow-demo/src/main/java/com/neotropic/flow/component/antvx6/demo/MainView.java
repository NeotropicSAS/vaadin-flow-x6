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
        AntvX6 x6 = new AntvX6();
        add(x6);
        
        String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTaZj-3Gf3IOMX44LXAIpUVhghfvXsCEId-EYmMtnvoKLnsMLUQxmuj4EH8aUfYiJoMn9o&usqp=CAU";
        X6Node node = new X6Node("1", X6Constants.SHAPE_IMAGE,0, 0, 32 , 32 ,url,"router","white");
        
        x6.addGraphCreatedListener(event -> {
            x6.drawNodeObjectView(node);
        });
    }        

}

