import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;


public class Document{
	//private ResourceCollection rc;
	private ArrayList<Page> pages;
	private ArrayList<DocumentListener> listeners;
	
	/**
	 * Default Ctor - use it whenever
	 */
	public Document(){
		pages = new ArrayList<Page>();
		listeners = new ArrayList<DocumentListener>();
	}
	
	/**
	 * Ctor called if there's a file passed in at the command line
	 */
	public Document(String path){
		pages = new ArrayList<Page>();
		listeners = new ArrayList<DocumentListener>();
		load(path);
	}
	
	public void addPage(Page p){
		pages.add(p);
		fireChanged(p.getName(), false);
	}
	
	public void deletePage(Page p){
		pages.remove(p);
		fireChanged(p.getName(), true);
	}
	
	public void deletePage(String name){
		Page found = null;
		for(Page pg : pages){
			if(pg.getName()==name){
				found = pg;
				break;
			}
		}
		if(found!=null){
			pages.remove(found);
			fireChanged(found.getName(), true);
		}
	}
	
	public void addResource(){
		JFileChooser choose = new JFileChooser();
		choose.showOpenDialog(null);
		File file = null;
		file = choose.getSelectedFile();
		if(file != null) /*rc.addResource(file)*/;
		//fireChanged(rc.getName(), false);
	}
	
	public void deleteResource(){
		//ask val about this one
	}
	
	public ArrayList<Page> getPages(){
		return pages;
	}
	
	public Page getPage(String name){
		for(Page pg : pages){
			if(pg.getName().equals(name)) return pg;
		}
		return null;
	}
	
	public Page getPage(Page match){
		for(Page pg : pages){
			if(pg.equals(match)) return pg;
		}
		return null;
	}
	
	public void addDocListener(DocumentListener obj){
		listeners.add(obj);
	}
	
	private void fireChanged(String itemName, boolean removed){
		for(DocumentListener dl : listeners){
			dl.documentUpdated(itemName, removed);
		}
	}
	
	public void open(){
		
		JFileChooser choose = new JFileChooser();
		choose.showOpenDialog(null);
		File file = null;
		file = choose.getSelectedFile();
		
		if(file != null){
			Page[] arr = null;
			try {
				XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(
						new FileInputStream(file)));
	
				arr = (Page[]) xmlIn.readObject();
				xmlIn.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			for(int i=0; i<arr.length; i++){
				addPage(arr[i]);
			}
			
			String base = file.getAbsolutePath();
			int indexofslash = base.lastIndexOf(file.separator);
			base = base.substring(0, indexofslash);
			File updated = new File(base);
			//rc.load(updated);
		}
	}
	
	private void load(String path){
		File file = new File(path);
		Page[] arr = null;
		try {
			XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(
					new FileInputStream(file)));

			arr = (Page[]) xmlIn.readObject();
			xmlIn.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<arr.length; i++){
			pages.add(arr[i]);
		}
		
		String base = file.getAbsolutePath();
		int indexofslash = base.lastIndexOf(file.separator);
		base = base.substring(0, indexofslash);
		File updated = new File(base);
		//rc.load(updated);
	}
	
	/**
	 * Dumps XML page/shapemodel data to file then calls a save on the resources collection
	 * The file passed to RC is the directory where resources should be saved directly to
	 */
	public void save(){
		
		JFileChooser choose = new JFileChooser();
		choose.showSaveDialog(null);
		File file = choose.getSelectedFile();
		file.mkdir();
		String path = file.getAbsolutePath();
		
		
		Page[] arr = new Page[pages.size()];
		for(int i=0; i<pages.size(); i++){
			arr[i] = pages.get(i);
		}
		
		try {
			XMLEncoder xmlOut = new XMLEncoder(
					new BufferedOutputStream(
							new FileOutputStream(path+file.separator+"index.xml")));

			xmlOut.writeObject(arr);
			xmlOut.close();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
		//rc.save(file);
	}
}
