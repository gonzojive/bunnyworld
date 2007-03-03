import java.beans.*;
import java.util.ArrayList;


public class ShapeModel {

	private static int shNum = 0;
	private ArrayList<ShapeModelListener> listeners;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private String name;
	private String script;
	private String text;
	private String font;
	private String imageName;
	
	private boolean movable;
	private boolean visible;
	private boolean bold;
	private boolean italic;
	
	
	public ShapeModel(){
		shNum++;
		listeners = new ArrayList<ShapeModelListener>();
		setX(10);
		setY(10);
		setWidth(20);
		setHeight(20);
		setName("shape"+shNum);
		setScript("");
		setText("");
		setFont("");
		setImageName("");
		setMovable(false);
		setVisible(true);
		setBold(false);
		setItalic(false);
	}
	
	
	
	public void addShapeModelListener(ShapeModelListener obj){
		listeners.add(obj);
	}
	
	private void fireAllChanged(ShapeModel model){
		for(ShapeModelListener sml : listeners){
			sml.modelChanged(model);
		}
	}
	
	private void fireNameChanged(ShapeModel model){
		for(ShapeModelListener sml : listeners){
			sml.onlyNameChanged(model);
		}
	}
	
	
	//-------------------------------GETTERS
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public String getName(){
		return name;
	}
	
	public String getScript(){
		return script;
	}
	
	public String getText(){
		return text;
	}
	
	public String getFont(){
		return font;
	}
	
	public String getImageName(){
		return imageName;
	}
	
	public boolean getMovable(){
		return movable;
	}
	
	public boolean getVisible(){
		return visible;
	}
	
	public boolean getBold(){
		return bold;
	}
	
	public boolean getItalic(){
		return italic;
	}
	
	//---------------------------------SETTERS
	public void setX(int x){
		this.x = x;
		fireAllChanged(this);
	}
	
	public void setY(int y){
		this.y = y;
		fireAllChanged(this);
	}
	
	public void setWidth(int w){
		this.width = w;
		fireAllChanged(this);
	}
	
	public void setHeight(int h){
		this.height = h;
		fireAllChanged(this);
	}
	
	public void setName(String name){
		this.name = name;
		fireNameChanged(this);
	}
	
	public void setScript(String scr){
		this.script = scr;
		fireAllChanged(this);
	}
	
	public void setText(String txt){
		this.text = txt;
		fireAllChanged(this);
	}
	
	public void setFont(String fnt){
		this.font = fnt;
		fireAllChanged(this);
	}
	
	public void setImageName(String img){
		this.imageName = img;
		fireAllChanged(this);
	}
	
	public void setMovable(boolean b){
		movable=b;
		fireAllChanged(this);
	}
	
	public void setVisible(boolean b){
		visible=b;
		fireAllChanged(this);
	}
	
	public void setBold(boolean b){
		bold=b;
		fireAllChanged(this);
	}
	
	public void setItalic(boolean b){
		italic=b;
		fireAllChanged(this);
	}

	
	@Override
	public boolean equals(Object other){
		if(this==other) return true;
		if(!(other instanceof ShapeModel)) return false;
		ShapeModel sm = (ShapeModel)other;
		return (this.name.equals(sm.name));
	}
	
	@Override
	public String toString(){
		return ("name: "+name+'\n'+
				"x: "+x+'\n'+
				"y: "+y+'\n'+
				"width: "+width+'\n'+
				"heigth: "+height+'\n'+
				"script: "+script+'\n'+
				"text: "+text+'\n'+
				"font: "+font+'\n'+
				"image: "+imageName+'\n'+
				"movable: "+movable+'\n'+
				"visible: "+visible+'\n'+
				"bold: "+bold+'\n'+
				"italic: "+italic);
	}
}
