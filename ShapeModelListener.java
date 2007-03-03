/**
 *	Any class which needs to hear about all changes to all shapes should
 *	implement this interface. If it helps, I can make it send a more specific
 *	message about what changed.  Let me know what's best.
 * @author Alex Blackstock
 *
 */

public interface ShapeModelListener {
	public void modelChanged(ShapeModel model);
	public void onlyNameChanged(ShapeModel model);
}