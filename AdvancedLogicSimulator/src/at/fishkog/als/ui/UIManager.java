package at.fishkog.als.ui;

import java.util.ArrayList;
import java.util.stream.Collectors;

import at.fishkog.als.AdvancedLogicSimulator;

public class UIManager {

	public ArrayList<UI> uis;
	
	public UI currentUI;
	
	public UIManager() {
		uis = new ArrayList<>();
	}
	
	public void register(UI ui) {
		//Check if UI is registered yet
		if(getUI(ui.getID()) != null) throw new UIRegistrationException("Theres always an UI registered with this ID! (" + ui.getID() + ")");
		
		ui.init();
		uis.add(ui);
	}
	
	public void unregister(UI ui) {
		if(getUI(ui.getID()) == null) return;
		if(currentUI != null && currentUI.getID().equalsIgnoreCase(ui.getID()))
			throw new UIRegistrationException("Unable to unregister an UI that is currently displayed!"); 
		
		ui.dispose();
		uis.remove(ui);
	}
	
	public void show(UI ui) {
		if(currentUI != null) currentUI.hide();
		ui.show();
		AdvancedLogicSimulator.instance.stage.setScene(ui.getScene());
	}
	
	public void show(String id) {
		this.show(this.getUI(id));
	}
	
	public UI getUI(String id) {
		ArrayList<UI> result = uis.stream().filter((UI ui) -> ui.getID().equalsIgnoreCase(id)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public void dispose() {
		if(currentUI != null) currentUI.hide();
		uis.forEach((ui) -> ui.dispose());
		uis.clear();
	}
	
}
