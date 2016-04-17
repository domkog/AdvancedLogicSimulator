package at.fishkog.als.ui.common.sidebar;

import java.util.ArrayList;

import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValue.MetaAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class MetaTableUpdater {
	private TableView<MetaValue<?>> table;
	
	public MetaTableUpdater (TableView<MetaValue<?>> table) {
		this.table = table;
		
	}
	
	public void showData(Data comp) {
		ArrayList<MetaValue<?>> compData = comp.getMetaValues();
		
		this.updateTableItem(compData);
	}
	
	/*public void showData(ArrayList<Data> comps) {
		HashMap<String, MetaValue<?>> returnData = null;
		
		for(int i=0; i<comps.size();i++) {
			HashMap<String, MetaValue<?>> compData = comps.get(i).getMetaValues();
			
			if(i==0) {
				returnData = compData;
				
			}

			Iterator<Entry<String, MetaValue<?>>> it = returnData.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<String, MetaValue<?>> pair = it.next();
		        
		        if(!compData.containsKey((Object) pair.getKey())) {
		        	returnData.remove(pair.getKey());
		        	
		        }
		        it.remove();
			}
		}
		this.updateTableItem(returnData);
	}*/
	
	private void updateTableItem(ArrayList<MetaValue<?>> compData) {
		ObservableList<MetaValue<?>> allData = FXCollections.observableArrayList();
		for(MetaValue<?>  meta: compData) {
			if(meta.access != null && meta.access != MetaAccess.HIDDEN) allData.add(meta);
		}
		/*Iterator<Entry<String, MetaValue<?>>> it = compData.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, MetaValue<?>> pair = it.next();
	        Map<String, MetaValue<?>> dataRow = new HashMap<>();
	        
	        dataRow.put((String) pair.getKey(), (MetaValue<?>)pair.getValue());
	        
	        allData.add(dataRow);
	        it.remove();
	    }*/
	    
	    this.table.setItems(allData);
		
	}
}
