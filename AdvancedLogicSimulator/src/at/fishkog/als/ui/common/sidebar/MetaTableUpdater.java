package at.fishkog.als.ui.common.sidebar;

import java.util.ArrayList;

import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValue.MetaAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MetaTableUpdater {
	private TableView<MetaValue<?>> table;
	
	public MetaTableUpdater (TableView<MetaValue<?>> metaTable) {
		this.table = metaTable;
		
	}
	
	public void showData(Data data) {
		ArrayList<MetaValue<?>> compData = data.getMetaData().metaValues;
		
		this.updateTableItem(compData);
	}
	
	
	private void updateTableItem(ArrayList<MetaValue<?>> compData) {
		this.table.getItems().clear();
		for(MetaValue<?>  meta: compData) {
			if(meta.access != null && meta.access != MetaAccess.HIDDEN){				
				
			}
		}		
	}
}
