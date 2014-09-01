package tgc;

import obp.explorer.runtime.BehaviorConfiguration;
import java.util.Arrays;

@SuppressWarnings("all")
public class MainConfiguration extends BehaviorConfiguration {

	public int[] gB_1;

	public int[] toContext;

	//Create initial configuration
	public MainConfiguration() {
		this.gB_1 = new int[]{};
		this.toContext = new int[]{};
	}

	//Create configuration as a copy
	public MainConfiguration(MainConfiguration other) {
		this.gB_1 = new int[other.gB_1.length];
		for (int i=0; i<other.gB_1.length; i++) {
			this.gB_1[i] = other.gB_1[i];
		}
		this.toContext = new int[other.toContext.length];
		for (int i=0; i<other.toContext.length; i++) {
			this.toContext[i] = other.toContext[i];
		}
	}

	@Override
	public BehaviorConfiguration createCopy() {
		return new MainConfiguration(this);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof MainConfiguration) {
			MainConfiguration other = (MainConfiguration) object;
			if (!Arrays.equals(gB_1, other.gB_1)) return false;
			if (!Arrays.equals(toContext, other.toContext)) return false;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int total = 17;
		total = total * 37 + Arrays.hashCode(gB_1);
		total = total * 37 + Arrays.hashCode(toContext);
		return total;
	}

}

