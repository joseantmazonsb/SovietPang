package listeners;

import java.util.EventListener;

public interface BooleanListener extends EventListener{
	public void booleanPropertyChanged(BooleanEvent e);
}
