package tgc;

import obp.explorer.runtime.SymbolsTable;
import obp.explorer.runtime.Program;
import obp.explorer.runtime.Component;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class TgcRoot implements Program {

	public Map<String, String> getMetaInformations() {
		return Collections.emptyMap();
	}

	private Map<String, Integer> ioQueueSizeMap;

	public int getIoQueueSize(String name) {
		if ( ioQueueSizeMap == null ) {
			ioQueueSizeMap = new HashMap<String, Integer>();
			ioQueueSizeMap.put("GB_1", 100 );
			ioQueueSizeMap.put("toContext", 100 );
		}
		Integer size = ioQueueSizeMap.get(name);
		if ( size == null ) {
			throw new IllegalArgumentException("Queue named '"+ name +"' doesn't exists.");
		}
		return size.intValue();
	}

	public static final int No_signal = 0;

	public static final int Enter1 = 1;

	public static final int Enter2 = 2;

	public static final int Exit1 = 3;

	public static final int Exit2 = 4;

	public static final int Open = 5;

	public static final int Close = 6;

	public static final int Gate_up = 7;

	public static final int Gate_down = 8;

	public static final int Light_on = 9;

	public static final int Light_off = 10;

	public static final int Tick = 11;

	private Component root;

	@Override
	public Component getRoot(SymbolsTable symbols) {
		if (root == null) {
			root = new MainComposition(symbols);
		}
		return root;
	}

}

