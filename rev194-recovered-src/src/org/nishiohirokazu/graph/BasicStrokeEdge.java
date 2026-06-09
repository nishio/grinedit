/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.nishiohirokazu.awt.ColorHolder
 *  org.nishiohirokazu.graph.LinearEdge
 *  org.nishiohirokazu.graph.RenderableVertex
 *  org.nishiohirokazu.grinEdit.UtilCast
 */
package org.nishiohirokazu.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.Hashtable;
import org.nishiohirokazu.awt.ColorHolder;
import org.nishiohirokazu.graph.LinearEdge;
import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.UtilCast;

public class BasicStrokeEdge
extends LinearEdge {
    private Color color;
    private Stroke stroke = new BasicStroke(4.0f);
    private double width;

    public BasicStrokeEdge() {
        this.color = Color.BLACK;
    }

    public BasicStrokeEdge(RenderableVertex v1, RenderableVertex v2) {
        super(v1, v2);
        this.color = Color.BLACK;
    }

    public BasicStrokeEdge(RenderableVertex v1, RenderableVertex v2, int r, int g, int b) {
        super(v1, v2);
        this.color = ColorHolder.get((int)r, (int)g, (int)b);
    }

    public void render(Object target) {
        double[] p1 = this.v1.screenPos;
        double[] p2 = this.v2.screenPos;
        Graphics g = (Graphics)target;
        if (this.selected) {
            g.setColor(ColorHolder.SELECTED_EDGE);
        } else {
            g.setColor(this.color);
        }
        g.drawLine((int)p1[0], (int)p1[1], (int)p2[0], (int)p2[1]);
        Graphics2D g2 = (Graphics2D)g;
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(this.stroke);
        g2.draw(new Line2D.Double(p1[0], p1[1], p2[0], p2[1]));
        g2.setStroke(oldStroke);
    }

    public void setWidth(Object w) {
        this.width = UtilCast.o2double((Object)w);
        this.stroke = new BasicStroke((float)this.width);
    }

    public Hashtable getParams() {
        Hashtable result = super.getParams();
        result.put("width", this.width);
        return result;
    }
}
