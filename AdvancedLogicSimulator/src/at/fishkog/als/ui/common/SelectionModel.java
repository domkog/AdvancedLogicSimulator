package at.fishkog.als.ui.common;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Node;

public class SelectionModel {
	Set<Node> selection = new HashSet<>();
	
	public void add(Node node) {
		 node.setStyle("-fx-effect: dropshadow(three-pass-box, red, 3, 3, 0, 0);");
         selection.add(node);
		
	}
	
	public void remove(Node node) {
		node.setStyle("-fx-effect: null");
		selection.remove(node);
		
	}
	
	public void clear() {
		while( !selection.isEmpty()) {
            remove( selection.iterator().next());
            
        }
		
	}
	
	public boolean contains(Node node){
		return this.selection.contains(node);
		
	}
	
	public Set<Node> getSelection(){
		return this.selection;
		
	}
}