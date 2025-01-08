package com.neotropic.flow.component.antvx6.demo.pages.basicExamples;

import com.neotropic.flow.component.antvx6.AntvX6;
import com.neotropic.flow.component.antvx6.constants.X6Constants;
import com.neotropic.flow.component.antvx6.demo.components.HeaderComponent;
import com.neotropic.flow.component.antvx6.demo.components.NoteComponent;
import com.neotropic.flow.component.antvx6.demo.factory.X6Factory;
import com.neotropic.flow.component.antvx6.objects.Geometry;
import com.neotropic.flow.component.antvx6.objects.X6Edge;
import com.neotropic.flow.component.antvx6.objects.X6EdgeBasic;
import com.neotropic.flow.component.antvx6.objects.X6Node;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.util.UUID;

/**
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
@Route("/basic/examples/edges")
public class ExampleEdges extends VerticalLayout{
    private X6Factory factory;
    private static String HEADER_TEXT = "X6 Edge";
    private static String DRAW_EDGE_BASIC = "Draw Basic Edge";
    private static String DRAW_EDGE_WITH_LABEL = "Draw Edge with a unique label";
    private static String DRAW_EDGE_MULTIPLE_LABELS = "Draw Edge with multiple labels";
    private static String DRAW_EDGE_THROUGH_PORT = "Draw Edge through ports";
    private static String DESCRIPTION = "The X6 add-on supports two types of edges (soon 3) with different responsibilities.";
    private static String NOTE = "Whenever a change is made to the canvas (add nodes, edges, change styles, etc.), it must be done through an x6 or vaadin event, to update the current view.";
    private static String DESCRIPTION_DRAW_EDGE_BASIC = "It's the base edge for the edges supported by the add-on. This edge represents a connection without any type of label.";
    private static String DESCRIPTION_DRAW_EDGE_WITH_LABEL = "It's an edge which handles a single label on it.";
    private static String DESCRIPTION_DRAW_EDGE_MULTIPLE_LABELS = "We are still working on this module, and an update will be released soon.";
    private static String DESCRIPTION_DRAW_EDGE_THROUGH_PORT = "We can create edges through nodes that manage ports (Only nodes of type X6Node have the characteristic of managing a connection port).";
  
    public ExampleEdges(){
        this.factory = new X6Factory();
        
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.START); 
        
        createHeader();
        createExamples();
    }
    
    /*Elements UI*/ 
    
    private void createHeader(){
        HeaderComponent header = new HeaderComponent(HEADER_TEXT, DESCRIPTION);
        NoteComponent note = new NoteComponent(NOTE);
        add(header, note);
    }
    
    private void createExamples(){
        VerticalLayout lytDrawBasicEdge = createAddBasicEdge();
        VerticalLayout lytDrawEdge = createAddX6Edge();
        VerticalLayout lytDrawEdgeMultipleLabels = createAddX6EdgeMultipleLabels();
        VerticalLayout lytPort = connectionThroughPort();
        add(lytDrawBasicEdge, lytDrawEdge, lytDrawEdgeMultipleLabels, lytPort);
    }
    
    private VerticalLayout createAddBasicEdge(){
        VerticalLayout lytBasicCanvas = new VerticalLayout();
        AntvX6 basicCanvas = this.factory.getBasicCanvas(600, 600, X6Constants.GRAPH_BACKGROUND_COLOR);
        
        //Through an event, draw the elements
        basicCanvas.addGraphCreatedListener(evt -> {
            // Node source
            X6Node source = new X6Node();
            source.setId(UUID.randomUUID().toString());
            source.setGeometry(new Geometry(100, 100, 50, 50));
            source.setShape(X6Constants.SHAPE_RECT);
            
            // Node target
            X6Node target = new X6Node();
            target.setId(UUID.randomUUID().toString());
            target.setGeometry(new Geometry(300, 100, 50, 50));
            target.setShape(X6Constants.SHAPE_RECT);
            
            //Create the basic edge
            X6EdgeBasic edge = new X6EdgeBasic(UUID.randomUUID().toString(), source.getId(), target.getId());
            
            //Add the elements
            basicCanvas.drawNode(source);
            basicCanvas.drawNode(target);
            basicCanvas.drawEBasicEdge(edge);
        });
        
        lytBasicCanvas.add(new H4(DRAW_EDGE_BASIC), new Paragraph(DESCRIPTION_DRAW_EDGE_BASIC) ,basicCanvas);
        return lytBasicCanvas;
    }
    
    private VerticalLayout createAddX6Edge(){
        VerticalLayout lytBasicCanvas = new VerticalLayout();
        AntvX6 basicCanvas = this.factory.getBasicCanvas(600, 600, X6Constants.GRAPH_BACKGROUND_COLOR);
       
        //Through an event, we create the elements
        basicCanvas.addGraphCreatedListener(evt -> {
            // Node source
            X6Node source = new X6Node();
            source.setId(UUID.randomUUID().toString());
            source.setGeometry(new Geometry(100, 100, 50, 50));
            source.setShape(X6Constants.SHAPE_RECT);
            
            // Node target
            X6Node target = new X6Node();
            target.setId(UUID.randomUUID().toString());
            target.setGeometry(new Geometry(300, 100, 50, 50));
            target.setShape(X6Constants.SHAPE_RECT);
            
            //Create an edge with labels
            X6Edge edge = new X6Edge(UUID.randomUUID().toString(), source.getId(), target.getId(), "label-connection");
            
            //Add the elements
            basicCanvas.drawNode(source);
            basicCanvas.drawNode(target);
            basicCanvas.drawEdge(edge);
        });
        
        lytBasicCanvas.add(new H4(DRAW_EDGE_WITH_LABEL), new Paragraph(DESCRIPTION_DRAW_EDGE_WITH_LABEL) ,basicCanvas);
        return lytBasicCanvas;
    }
    
    private VerticalLayout createAddX6EdgeMultipleLabels(){
        VerticalLayout lytBasicCanvas = new VerticalLayout();
        
        lytBasicCanvas.add(new H4(DRAW_EDGE_MULTIPLE_LABELS), new Paragraph(DESCRIPTION_DRAW_EDGE_MULTIPLE_LABELS));
        return lytBasicCanvas;
    }
    
    private VerticalLayout connectionThroughPort(){
        VerticalLayout lytBasicCanvas = new VerticalLayout();
        AntvX6 basicCanvas = this.factory.getBasicCanvas(600, 600, X6Constants.GRAPH_BACKGROUND_COLOR);
        //through an event, we create the elements
        basicCanvas.addGraphCreatedListener(evt -> {
            // Node source
            X6Node source = new X6Node();
            source.setId(UUID.randomUUID().toString());
            source.setGeometry(new Geometry(100, 100, 50, 50));
            source.setShape(X6Constants.SHAPE_RECT);
            source.setPort(true);
            
            // Node target
            X6Node target = new X6Node();
            target.setId(UUID.randomUUID().toString());
            target.setGeometry(new Geometry(300, 100, 50, 50));
            target.setShape(X6Constants.SHAPE_RECT);
            target.setPort(true);
            
            //Add the nodes
            basicCanvas.drawNode(source);
            basicCanvas.drawNode(target);
            
            //Start event that detects when two nodes connect, these method delete the edge that was created
            basicCanvas.initEventNodesConnected();
            
            //If a connection was created then we can add our edge
            basicCanvas.addNodesConnectedListener(evtEdge -> {
                //Create the edge (whatever of edge you want)
                X6Edge edge = new X6Edge(UUID.randomUUID().toString(), evtEdge.getIdSource(), evtEdge.getIdTarget(), "new-connection");
                //add the edge
                basicCanvas.drawEdge(edge);
            });
            
        });
        
        lytBasicCanvas.add(new H4(DRAW_EDGE_THROUGH_PORT ), new Paragraph(DESCRIPTION_DRAW_EDGE_THROUGH_PORT) ,basicCanvas);
        return lytBasicCanvas;
    }
    
    /*End of elementes UI*/ 
}
