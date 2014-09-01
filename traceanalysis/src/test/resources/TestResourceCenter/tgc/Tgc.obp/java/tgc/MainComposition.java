package tgc;

import obp.explorer.runtime.core.Channel;
import obp.explorer.runtime.SymbolsTable;
import obp.explorer.runtime.Composition;
import obp.explorer.runtime.Component;

@SuppressWarnings("all")
public class MainComposition extends Composition {

	private final Component[] components;

	private final Channel[] channels;

	public MainComposition(SymbolsTable symbols) {
		MainBehavior me = new MainBehavior(symbols);
		//Initializes variables
		me.createInitialConfiguration(null);
		
		channels = new Channel[] {  };
		
		components = new Component[] {
				me,
				new GBBehavior(symbols, me.getGB_1Ref(), me.getToContextRef())
			};
		
	}

	@Override
	public Component[] getComponents() {
		return components;
	}

	@Override
	public Channel[] getChannels() {
		return channels;
	}

}

