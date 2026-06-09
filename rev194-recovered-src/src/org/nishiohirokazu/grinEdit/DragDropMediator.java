package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

public class DragDropMediator implements DropTargetListener {
	Hashtable handlers = new Hashtable();
	
	public void dragEnter(DropTargetEvent e) {
		e.detail = DND.DROP_COPY;
	}

	public void drop(DropTargetEvent evt){
		String[] files = (String[]) evt.data;
		for(int i = 0; i < files.length; i++){
			String file = files[i];
			String[] items = file.split("\\.");
			String ext = items[items.length - 1];
			
			IDragDropHandler handler =  (IDragDropHandler) handlers.get(ext);
			if(handler != null){
				handler.process(file);
			}else{
				System.out.println(ext + "‚Æ‚¢‚¤Šg’£Žq‚Í“o˜^‚³‚ê‚Ä‚¢‚Ü‚¹‚ñ");
			}
		}
	}


	public void dragLeave(DropTargetEvent arg0) {}
	public void dragOperationChanged(DropTargetEvent arg0) {}
	public void dragOver(DropTargetEvent arg0) {}
	public void dropAccept(DropTargetEvent arg0) {}
}
