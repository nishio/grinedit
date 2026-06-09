///**
// * $Id$
// * @version $Revision$
// * @author Nishio Hirokazu
// * 2005/03/25
// *
// */
//package org.nishiohirokazu.grinEdit.mouseOperation;
//
//import java.awt.Color;
//import java.lang.reflect.Method;
//
//import org.nishiohirokazu.graph.RenderableVertex;
//import org.nishiohirokazu.grinEdit.Mediator;
//
///**
// * ダブルクリック時に何かをするMouseOperationのサンプル
// * @author nishio
// *
// */
//public class MO_RedBackgroundWhenDoubleClicked extends MouseOperation {
//    public String getName(){
//        return "RedBackground";
//    }
//
//    @Override
//    public void mouseDoubleClick(int x, int y, int i) {
//		RenderableVertex target = (RenderableVertex) Mediator.getInstance().getNearestVertex(x, y);
//        if(target != null){
//        	Class[] paramsTypes = {Object.class}; 
//        	try {
//				Method setBgcolor = target.getClass().getMethod("setBgcolor", paramsTypes);
//				Object[] paramValues = {Color.RED};
//				setBgcolor.invoke(target, paramValues);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//        }
//    }
//}
