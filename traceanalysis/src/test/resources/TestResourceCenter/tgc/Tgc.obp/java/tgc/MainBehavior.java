package tgc;

import org.xid.basics.serializer.Boost;
import obp.explorer.runtime.core.Transition;
import obp.explorer.runtime.SymbolsTable;
import obp.explorer.runtime.ReferencedArgument;
import obp.explorer.runtime.ProcessBehavior;
import obp.explorer.runtime.Configuration;
import obp.explorer.runtime.BehaviorConfiguration;
import java.util.Arrays;

@SuppressWarnings("all")
public class MainBehavior extends ProcessBehavior {

	private static short idSeed = 1;

	public MainBehavior(SymbolsTable symbols) {
		super("{main}"+(idSeed++), symbols);
	}

	public ReferencedArgument<int[]> getGB_1Ref() {
return 		new ReferencedArgument<int[]>(id) {
			@Override
			public int[] get(Configuration configuration) {
				MainConfiguration me = (MainConfiguration) configuration.behaviorConfigurations[ownerId];
				return me.gB_1;
			}

			@Override
			public void set(Configuration configuration, int[] value) {
				MainConfiguration me = (MainConfiguration) configuration.behaviorConfigurations[ownerId];
				me.gB_1 = value;
			}

			@Override
			public  Class<int[]> getArgumentType() {
				return int[].class;
			}

		}

;	}

	public ReferencedArgument<int[]> getToContextRef() {
return 		new ReferencedArgument<int[]>(id) {
			@Override
			public int[] get(Configuration configuration) {
				MainConfiguration me = (MainConfiguration) configuration.behaviorConfigurations[ownerId];
				return me.toContext;
			}

			@Override
			public void set(Configuration configuration, int[] value) {
				MainConfiguration me = (MainConfiguration) configuration.behaviorConfigurations[ownerId];
				me.toContext = value;
			}

			@Override
			public  Class<int[]> getArgumentType() {
				return int[].class;
			}

		}

;	}

	private final Transition[] transitions = new Transition[0];

	@Override
	public Transition[] getTransitions(Configuration configuration) {
		return transitions;
	}

	@Override
	public BehaviorConfiguration createInitialConfiguration(Configuration conf) {
		MainConfiguration me = new MainConfiguration();
		//Initial action code.
		me.gB_1 = new int[] {};
		me.toContext = new int[] {};
		return me;
	}

	public String toStringConfiguration(BehaviorConfiguration behaviorConfiguration) {
		MainConfiguration me = (MainConfiguration) behaviorConfiguration;
		StringBuilder text = new StringBuilder();
		text.append("component: ");
		text.append("'");
		text.append(name);
		text.append("' [");
		text.append("\n\t- GB_1=");
		text.append(Arrays.toString(me.gB_1));
		text.append("\n\t- toContext=");
		text.append(Arrays.toString(me.toContext));
		text.append("\n]");
		return text.toString();
	}

	public String toDotStringConfiguration(BehaviorConfiguration behaviorConfiguration) {
		MainConfiguration me = (MainConfiguration) behaviorConfiguration;
		StringBuilder text = new StringBuilder();
		text.append("'");
		text.append(name);
		text.append("' [");
		text.append(" GB_1=");
		text.append(Arrays.toString(me.gB_1));
		text.append(" toContext=");
		text.append(Arrays.toString(me.toContext));
		text.append("]");
		return text.toString();
	}

	@Override
	public BehaviorConfiguration readConfiguration(Boost boost) {
		MainConfiguration me = new MainConfiguration();
		me.gB_1 = new int[100];
		me.gB_1 = new int[boost.readInt()];
		for (int i=0; i<me.gB_1.length; i++) {
			me.gB_1[i] = boost.readInt();
		}
		me.toContext = new int[100];
		me.toContext = new int[boost.readInt()];
		for (int i=0; i<me.toContext.length; i++) {
			me.toContext[i] = boost.readInt();
		}
		return me;
	}

	@Override
	public void writeConfiguration(Boost boost, BehaviorConfiguration processConfiguration) {
		MainConfiguration me = (MainConfiguration) processConfiguration;
		boost.writeInt(me.gB_1.length);
		for (int i=0; i<me.gB_1.length; i++) {
			boost.writeInt(me.gB_1[i]);
		}
		boost.writeInt(me.toContext.length);
		for (int i=0; i<me.toContext.length; i++) {
			boost.writeInt(me.toContext[i]);
		}
	}

}

