import java.beans.*;
import java.util.*;

public class Page {

	private static int pgNum = 0;
	
	private String name;
	private ArrayList<PageListener> listeners;
	private ArrayList<ShapeModel> models;
	
	
	public Page(){
		pgNum++;
		listeners = new ArrayList<PageListener>();
		this.name = "page"+pgNum;
		models = new ArrayList<ShapeModel>();
	}
	
	public void addShapeModel(ShapeModel sm){
		models.add(sm);
		fireChanged(this, sm.getName(), false, false);
	}
	
	public void deleteShapeModel(ShapeModel sm){
		models.remove(sm);
		fireChanged(this, sm.getName(), true, false);
	}
	
	public ArrayList<ShapeModel> getModels(){
		return models;
	}
	
	public void setModels(ArrayList<ShapeModel> m){
		models = m;
	}
	
	public void addPageListener(PageListener obj){
		listeners.add(obj);
	}
	
	private void fireChanged(Page p, String shapeName, boolean removed, boolean pgNameChange){
		for(PageListener pl : listeners){
			pl.pageChanged(p, shapeName, removed, pgNameChange);
		}
	}
	
	public void setName(String name){
		if(this.name.equals("page1")) return;
		this.name = name;
		fireChanged(this, null, false, true);
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean equals(Object other){
		if(this==other) return true;
		if(!(other instanceof Page)) return false;
		Page p = (Page)other;
		return (this.name.equals(p.name));
	}
	
}
