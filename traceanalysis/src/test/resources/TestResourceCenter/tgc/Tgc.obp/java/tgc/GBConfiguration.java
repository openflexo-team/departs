package tgc;

import obp.explorer.runtime.BehaviorConfiguration;
import java.util.Arrays;

@SuppressWarnings("all")
public class GBConfiguration extends BehaviorConfiguration {

	public short state;

	public int received;

	public int tmp_received;

	public int[] tmp_input;

	public int[] tmp_saved;

	//Create initial configuration
	public GBConfiguration() {
		this.received = 0;
		this.tmp_received = 0;
		this.tmp_input = new int[]{};
		this.tmp_saved = new int[]{};
	}

	//Create configuration as a copy
	public GBConfiguration(GBConfiguration other) {
		this.state = other.state;
		this.received = other.received;
		this.tmp_received = other.tmp_received;
		this.tmp_input = new int[other.tmp_input.length];
		for (int i=0; i<other.tmp_input.length; i++) {
			this.tmp_input[i] = other.tmp_input[i];
		}
		this.tmp_saved = new int[other.tmp_saved.length];
		for (int i=0; i<other.tmp_saved.length; i++) {
			this.tmp_saved[i] = other.tmp_saved[i];
		}
	}

	@Override
	public BehaviorConfiguration createCopy() {
		return new GBConfiguration(this);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof GBConfiguration) {
			GBConfiguration other = (GBConfiguration) object;
			if (state != other.state) return false;
			if (received != other.received) return false;
			if (tmp_received != other.tmp_received) return false;
			if (!Arrays.equals(tmp_input, other.tmp_input)) return false;
			if (!Arrays.equals(tmp_saved, other.tmp_saved)) return false;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int total = 17;
		total = total * 37 + state;
		total = total * 37 + received;
		total = total * 37 + tmp_received;
		total = total * 37 + Arrays.hashCode(tmp_input);
		total = total * 37 + Arrays.hashCode(tmp_saved);
		return total;
	}

}

