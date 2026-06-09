/*
 * Decompiled with CFR 0.152.
 */
package org.nishiohirokazu.grinEdit.mouseOperation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.nishiohirokazu.grinEdit.mouseOperation.NewMouseHandler;

public class NewMouseMediator
implements MouseListener {
    private void invoke(NewMouseHandler target, String methodName, MouseEvent e) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        Class[] signature = new Class[]{MouseEvent.class};
        Method m = target.getClass().getMethod(methodName, signature);
        Object[] m_params = new Object[]{e};
        m.invoke((Object)target, m_params);
    }

    public boolean feedMouseEvent(MouseEvent e) {
        return false;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
