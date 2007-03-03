import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;


public class Catalog extends JPanel implements DocumentListener, PageListener, ShapeModelListener{
	
	private HashMap<String, DefaultMutableTreeNode> stringToNode;
	private HashMap<DefaultMutableTreeNode, String> nodeToString;
	private Document doc;
	private JPopupMenu menu;
	private String src;
	
	//Page and shape tree
	private DefaultMutableTreeNode root, node;
	private JTree tree;
	private DefaultTreeModel model;
	
	//Resource tree
	private DefaultMutableTreeNode rroot, rnode;
	private JTree rtree;
	private DefaultTreeModel rmodel;
	
	public Catalog(Document d){
		doc = d;
		stringToNode = new HashMap<String, DefaultMutableTreeNode>();
		nodeToString = new HashMap<DefaultMutableTreeNode, String>();
		menu = new JPopupMenu();
		JMenuItem delete = new JMenuItem(new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				if(src.equals("tree")){
					deleteSelected(tree, root, model);
				}else{
					deleteSelected(rtree, rroot, rmodel);
				}
			}
		});
		delete.setArmed(true);
		delete.setText("Delete");
		menu.add(delete);
		
		root = new DefaultMutableTreeNode("Project");
		tree = new JTree(root);
		model = (DefaultTreeModel) tree.getModel();
		
		rroot = new DefaultMutableTreeNode("Resources");
		rtree = new JTree(rroot);
		rmodel = (DefaultTreeModel) rtree.getModel();
		
		tree.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if(e.getButton()!=e.BUTTON1){
					menu.show(tree, e.getX(), e.getY());
					tree.setSelectionRow(tree.getRowForLocation(e.getX(), e.getY()));
					src = "tree";
				}
				
				if(e.getClickCount()==2) {
					System.out.println("load");	
				}
			}
		
		});
		
		rtree.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
		
				if(e.getButton()!=e.BUTTON1){
					menu.show(rtree, e.getX(), e.getY());
					rtree.setSelectionRow(rtree.getRowForLocation(e.getX(), e.getY()));
					src = "rtree";
				}
			}
		
		});
	
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JScrollPane pane1 = new JScrollPane(tree);
		pane1.setPreferredSize(new Dimension(150, 400));
		this.add(pane1);
		
		JScrollPane pane2 = new JScrollPane(rtree);
		pane2.setPreferredSize(new Dimension(150, 200));
		this.add(pane2);
	}
	
	//PRIVATE UTILITY
	private void deleteSelected(JTree t, DefaultMutableTreeNode r, DefaultTreeModel m){
		TreePath path = t.getSelectionPath();
		if(path!=null){
			DefaultMutableTreeNode selected = (DefaultMutableTreeNode) path.getLastPathComponent();
			if(r==root){
				switch(selected.getLevel()){
					case 0: break;
					case 1: doc.deletePage(nodeToString.get(selected)); break;
					case 2: DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selected.getParent();
							doc.getPage(nodeToString.get(parent)).deleteShapeModel(nodeToString.get(selected)); break;
					default : break;
				}
			}else{
				if(selected.getLevel()==1) /*del resource*/;
					
			}
		}
	}
	
	
	
	//Page level
	public void addShape(Page p, String name){
		DefaultMutableTreeNode page = stringToNode.get(p.getName());
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name);
		stringToNode.put(name, newNode);
		nodeToString.put(newNode, name);
		model.insertNodeInto(newNode, page, page.getChildCount());
	}
	
	public void removeShape(Page p, String name){
		DefaultMutableTreeNode rm = stringToNode.get(name);
		model.removeNodeFromParent(rm);
	}
	
	public void changeNamePage(Page p){
		
	}
	
	//Document level
	public void addPage(String name){
		int n = root.getChildCount();
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name);
		stringToNode.put(name, newNode);
		nodeToString.put(newNode, name);
		model.insertNodeInto(newNode , root, n);
	}
	
	public void removePage(String name){
		DefaultMutableTreeNode rm = stringToNode.get(name);
		model.removeNodeFromParent(rm);
	}
	
	public void addResource(String name){
		int n = rroot.getChildCount();
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name);
		stringToNode.put(name, newNode);
		nodeToString.put(newNode, name);
		rmodel.insertNodeInto(newNode , rroot, n);
	}
	
	public void removeResource(String name){
		DefaultMutableTreeNode rm = stringToNode.get(name);
		rmodel.removeNodeFromParent(rm);
	}
	
	//ShapeModel level
	public void changeNameShape(ShapeModel model){
		
	}
	
	
	
	
	//--------------------------------LISTENER RESPONSE
	public void documentUpdated(String itemName, boolean removed){
		if(itemName.contains(".")){ //it's a resource
			if(removed){
				removeResource(itemName);
			}else{
				addResource(itemName);
			}
		}else{ //it's a page
			if(removed){
				removePage(itemName);
			}else{
				addPage(itemName);
			}
		}
	}
	
	public void pageChanged(Page p, String shapeName, boolean removed, boolean pgNameChange){
		if(pgNameChange){
			changeNamePage(p);
		}else if(removed){
			removeShape(p, shapeName);
		}else{
			addShape(p, shapeName);
		}
	}
	
	public void modelChanged(ShapeModel model){}
	public void onlyNameChanged(ShapeModel model){
		changeNameShape(model);
	}
}
