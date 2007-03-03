/**
 *	Any class which needs to hear about shapes being added or removed should
 *	implement this interface (also if you have to hear about renaming pages). 
 * @author Alex Blackstock
 *
 */

public interface PageListener {
	public void pageChanged(Page p, String shapeName, boolean removed, boolean pgNameChange);
}