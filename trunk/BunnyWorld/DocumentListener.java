/**
 *	Any class which needs to hear about page/resource creation and deletion should
 *	implement this interface and it's one method. 
 * @author Alex Blackstock
 *
 */

public interface DocumentListener {
	//The boolean value tells the recipient whether the named item in being added or removed. True means removed.
	public void documentUpdated(String itemName, boolean removed);
}
