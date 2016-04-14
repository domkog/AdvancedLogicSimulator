package at.fishkog.als.ui.common.sidebar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class MetaTableUpdater {
	private TableView table;
	
	public MetaTableUpdater (TableView table) {
		this.table = table;
		
	}
	
	public void showData(Data comp) {
		HashMap<String, MetaValue<?>> compData = comp.getMetaValues();
		
		this.updateTableItem(compData);
	}
	
	public void showData(ArrayList<Data> comps) {
		HashMap<String, MetaValue<?>> returnData = null;
		
		for(int i=0; i<comps.size();i++) {
			HashMap<String, MetaValue<?>> compData = comps.get(i).getMetaValues();
			
			if(i==0) {
				returnData = compData;
				
			}

			Iterator it = returnData.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        
		        if(!compData.containsKey((Object) pair.getKey())) {
		        	returnData.remove(pair.getKey());
		        	
		        }
		        it.remove();
			}
		}
		this.updateTableItem(returnData);
	}
	
	private void updateTableItem(HashMap<String, MetaValue<?>> compData) {
		ObservableList<Map> allData = FXCollections.observableArrayList();
		Iterator it = compData.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Map<String, MetaValue<?>> dataRow = new HashMap<>();
	        
	        dataRow.put((String) pair.getKey(), (MetaValue<?>)pair.getValue());
	        
	        allData.add(dataRow);
	        it.remove();
	    } 
	    
	    this.table.setItems(allData);
		
	}
}
