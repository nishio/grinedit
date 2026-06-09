/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/30
 *
 */
package org.nishiohirokazu.grinEdit;

import java.util.Vector;

import org.nishiohirokazu.graph.RenderableEdge;
import org.nishiohirokazu.graph.RenderableVertex;

/**
 * Manage selection of vertexes.
 * Don't edit Vertex.selected directly.
 * @author nishio
 */
public class Selection {
    
    private Vector vertexList=new Vector();
    private Vector edgeList=new Vector();
    private Vector marginalList=new Vector();
    private Mediator med;

    /**
     * @param mediator
     */
    public Selection(Mediator mediator) {
        this.med=mediator;
    }
    public void set(Vector l){
        for (int i = 0; i < vertexList.size(); i++) {
            RenderableVertex v = (RenderableVertex) vertexList.get(i);
            v.selected=false;
        }
        vertexList=l;
        for (int i = 0; i < vertexList.size(); i++) {
            RenderableVertex v = (RenderableVertex) vertexList.get(i);
            v.selected=true;
        }
        edgeList=new Vector();
        marginalList=new Vector();
        Vector allEdges=med.getEdges();
        for (int i = 0; i < allEdges.size(); i++) {
            RenderableEdge e = (RenderableEdge) allEdges.get(i);
            if(e.v1.selected && e.v2.selected){
                edgeList.add(e);
                e.selected = true;
            }else if(e.v1.selected || e.v2.selected){
                marginalList.add(e);
                e.selected = false;
            }else{
                e.selected = false;
            }
        }
    }
    public Vector getVertex(){
        return vertexList;
    }
    public Vector getEdges(){
        return edgeList;
    }
    public Vector getMarginalEdges(){
        return marginalList;
    }
    


}
