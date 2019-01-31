package listeners;

import java.util.EventObject;

public class BooleanEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private boolean oldValue;
	private boolean newValue;
	
	public BooleanEvent(Object source, boolean oldValue, boolean newValue) {
		super(source);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public boolean getOldValue() {
		return oldValue;
	}

	public void setOldValue(boolean oldValue) {
		this.oldValue = oldValue;
	}

	public boolean getNewValue() {
		return newValue;
	}

	public void setNewValue(boolean newValue) {
		this.newValue = newValue;
	}
}
