package at.fishkog.als.sim.component;

import at.fishkog.als.sim.component.categories.ComponentCategory;
import at.fishkog.als.sim.data.BitWidth;
import at.fishkog.als.sim.data.Bounds;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Connector.Type;
import at.fishkog.als.sim.data.Location;
import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValue.MetaAccess;
import at.fishkog.als.sim.data.meta.MetaValueListener;

//Base class that all components need to implement
public abstract class BasicComponent extends Component {
	
	public Location location;
	public Bounds bounds;
	
	public MetaValue<Integer> connectorCount;
	
	public int extensionWidth;
	public int extensionHeight;
	
	public BasicComponent(int id, ComponentCategory category, String name, int x, int y,int width, int height, int numInputs, String desc, int bitwidth_num, boolean isNegated) {
		super(id,category,name);
		
		location = new Location(x, y);
		bounds = new Bounds(width, height);
		bounds.height.access = MetaAccess.HIDDEN;
		bounds.width.access = MetaAccess.HIDDEN;
		this.bitwidth = new BitWidth(bitwidth_num);		
		
		connectorCount = new MetaValue<Integer>("Connectors", 0);
		
		this.createConnectors(numInputs);
		
		/*Add MetaDatas*/
		this.metaData.add(basicAttributes.getMetaData(), location.getMetaData(), bounds.getMetaData(), bitwidth.getMetaData());
		this.metaData.add(connectorCount);			
		
		basicAttributes.getOrientation().addListener(new MetaValueListener<Integer>() {
			@Override
			public void change(Integer from, Integer to) {
				node.setRotate(to * 90);
				
			}
		});
		
		location.x.addListener(new MetaValueListener<Integer>() {
			@Override
			public void change(Integer from, Integer to) {
				node.setTranslateX(to);
				
			}
		});
		
		location.y.addListener(new MetaValueListener<Integer>() {
			@Override
			public void change(Integer from, Integer to) {
				node.setTranslateY(to);
				
			}
		});
		
		connectorCount.addListener(new MetaValueListener<Integer>() {
			@Override
			public void change(Integer from, Integer to) {
				if(from>to) {
					for(int i=0; i<to-from; i++) {
						removeConnector(inputs.get(from - i));
						
					}
				} else {
					for (int i=0; i>from-to; i++){
						addConnector(new Connector(BasicComponent.this, "in" + from + i, Type.INPUT, 0, 0, bitwidth, false));
						
					}
				}
				
				rerenderComponents();
			}
		});
		
	}
	
	public BasicComponent(int id, ComponentCategory category, String name, int x, int y, int numInputs, int bitwidth, boolean isNegated) {
		this(id, category, name,x, y, 45,45, numInputs, "", bitwidth, isNegated);
		
	}

	public void addConnector(Connector c) {
		super.addConnector(c);		
		this.connectorCount.setValueSilent(this.inputs.size());
	}

	public void removeConnector(Connector c) {
		super.removeConnector(c);
		this.connectorCount.setValueSilent(this.inputs.size());

	}
	
	public void setNumInputs(int num) {
		this.connectors.clear();		
		this.createConnectors(num);
	}
	
	private void createConnectors(int num) {
		//TODO add connector states
		this.addConnector(new Connector(this, "out1", Type.OUTPUT, 0, 0, this.bitwidth, false));
		for(int i = 0; i < num; i++) this.addConnector(new Connector(this, "in" + i, Type.INPUT, 0, 0, this.bitwidth, false));
		
		
	}
	
	public abstract BasicComponent createNew(int x, int y, int numInputs, int bitwidth);
}
