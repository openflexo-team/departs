package tgc;

import org.xid.basics.serializer.Boost;
import obp.explorer.runtime.obs.AtomicAction;
import obp.explorer.runtime.fiacre.FiacreQueueUtil;
import obp.explorer.runtime.core.Transition;
import obp.explorer.runtime.core.Channel;
import obp.explorer.runtime.SymbolsTable;
import obp.explorer.runtime.ReferencedArgument;
import obp.explorer.runtime.ProcessBehavior;
import obp.explorer.runtime.ExplorationContext;
import obp.explorer.runtime.Configuration;
import obp.explorer.runtime.BehaviorConfiguration;
import java.util.List;
import java.util.Arrays;

@SuppressWarnings("all")
public class GBBehavior extends ProcessBehavior {

	private static short idSeed = 1;

	public static final short Idle = 1;

	public static final short Busy1 = 2;

	public static final short Busy2 = 3;

	public static final short Busy12 = 4;

	public static final short Tempo = 5;

	public static final short Safe = 6;

	private final ReferencedArgument<int[]> inputRef;

	private final ReferencedArgument<int[]> outputRef;

	public GBBehavior(SymbolsTable symbols, ReferencedArgument<int[]> input, ReferencedArgument<int[]> output) {
		super("{GB}"+(idSeed++), symbols);
		this.inputRef = input;
		this.outputRef = output;
	}

	private final String[] stateNames = new String[] {"idle", "busy1", "busy2", "busy12", "tempo", "safe"};

	public int getStateId(String name) {
		for (short i=0; i<stateNames.length; i++) {
			if ( stateNames[i].equals(name) ) return (i+1);
		}
		throw new IllegalArgumentException("State '"+ name +"' doesn't exist in process '"+ this.name +"'.");
	}

	public String getStateName(short id) {
		return stateNames[id-1];
	}

	private final Transition transition0 = 	new Transition(id) {
		@Override
		public void updateModifiedBehaviors(Configuration configuration, ExplorationContext context, boolean[] modifiedBehaviors) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] output = outputRef.get(configuration);
			int[] input = inputRef.get(configuration);
			modifiedBehaviors[outputRef.ownerId] = true;
			modifiedBehaviors[inputRef.ownerId] = true;
		}

		@Override
		public boolean guard(Configuration configuration, ExplorationContext context) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			if (me.state != Idle) return false;
			return true;
		}

		@Override
		public boolean action(ExplorationContext context, List<AtomicAction> actions, Configuration configuration) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] output = outputRef.get(configuration);
			int[] input = inputRef.get(configuration);
			try {
				me.tmp_input = input;
				while (( !( (me.tmp_input.length == 0))) && (me.received == TgcRoot.No_signal)) {
					me.tmp_received =  FiacreQueueUtil.first(me.tmp_input);
					me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					if (((me.tmp_received == TgcRoot.Enter1) || (me.tmp_received == TgcRoot.Enter2)) || (me.tmp_received == TgcRoot.Close)) {
						me.received = me.tmp_received;
					}
				}
				if (me.received == TgcRoot.No_signal) {
					me.tmp_input = new int[] {};
					me.tmp_saved = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					me.received = TgcRoot.No_signal;
				} else {
					input = me.tmp_input;
					me.tmp_saved = new int[] {};
					me.tmp_input = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					if (me.received == TgcRoot.Enter1) {
						output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Gate_down, actions);
						output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Light_on, actions);
						me.received = TgcRoot.No_signal;
						me.state = Busy1;
						if (true) return true;
					} else {
						if (me.received == TgcRoot.Enter2) {
							output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Gate_down, actions);
							output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Light_on, actions);
							me.received = TgcRoot.No_signal;
							me.state = Busy2;
							if (true) return true;
						} else {
							if (me.received == TgcRoot.Close) {
								output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Gate_down, actions);
								output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Light_on, actions);
								me.received = TgcRoot.No_signal;
								me.state = Safe;
								if (true) return true;
							}
						}
					}
				}
				me.state = Idle;
				if (true) return true;
			} finally {
				outputRef.set(configuration, output);
				inputRef.set(configuration, input);
			}
			if (true) { return true; } else { return true; }
		}

	}

;

	private final Transition transition1 = 	new Transition(id) {
		@Override
		public void updateModifiedBehaviors(Configuration configuration, ExplorationContext context, boolean[] modifiedBehaviors) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			modifiedBehaviors[inputRef.ownerId] = true;
		}

		@Override
		public boolean guard(Configuration configuration, ExplorationContext context) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			if (me.state != Busy1) return false;
			return true;
		}

		@Override
		public boolean action(ExplorationContext context, List<AtomicAction> actions, Configuration configuration) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			try {
				me.tmp_input = input;
				while (( !( (me.tmp_input.length == 0))) && (me.received == TgcRoot.No_signal)) {
					me.tmp_received =  FiacreQueueUtil.first(me.tmp_input);
					me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					if ((me.tmp_received == TgcRoot.Enter2) || (me.tmp_received == TgcRoot.Exit1)) {
						me.received = me.tmp_received;
					} else {
						if ((me.tmp_received == TgcRoot.Open) || (me.tmp_received == TgcRoot.Close)) {
							me.tmp_saved = FiacreQueueUtil.enqueue(me.tmp_saved,me.tmp_received);
						}
					}
				}
				if (me.received == TgcRoot.No_signal) {
					me.tmp_input = new int[] {};
					me.tmp_saved = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					me.received = TgcRoot.No_signal;
				} else {
					input = me.tmp_saved;
					while ( !( (me.tmp_input.length == 0))) {
						input = FiacreQueueUtil.enqueue(id, input, FiacreQueueUtil.first(me.tmp_input), actions);
						me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					}
					me.tmp_saved = new int[] {};
					me.tmp_input = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					if (me.received == TgcRoot.Enter2) {
						me.received = TgcRoot.No_signal;
						me.state = Busy12;
						if (true) return true;
					} else {
						if (me.received == TgcRoot.Exit1) {
							me.received = TgcRoot.No_signal;
							me.state = Tempo;
							if (true) return true;
						}
					}
				}
				me.state = Busy1;
				if (true) return true;
			} finally {
				inputRef.set(configuration, input);
			}
			if (true) { return true; } else { return true; }
		}

	}

;

	private final Transition transition2 = 	new Transition(id) {
		@Override
		public void updateModifiedBehaviors(Configuration configuration, ExplorationContext context, boolean[] modifiedBehaviors) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			modifiedBehaviors[inputRef.ownerId] = true;
		}

		@Override
		public boolean guard(Configuration configuration, ExplorationContext context) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			if (me.state != Busy2) return false;
			return true;
		}

		@Override
		public boolean action(ExplorationContext context, List<AtomicAction> actions, Configuration configuration) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			try {
				me.tmp_input = input;
				while (( !( (me.tmp_input.length == 0))) && (me.received == TgcRoot.No_signal)) {
					me.tmp_received =  FiacreQueueUtil.first(me.tmp_input);
					me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					if ((me.tmp_received == TgcRoot.Enter1) || (me.tmp_received == TgcRoot.Exit2)) {
						me.received = me.tmp_received;
					} else {
						if ((me.tmp_received == TgcRoot.Open) || (me.tmp_received == TgcRoot.Close)) {
							me.tmp_saved = FiacreQueueUtil.enqueue(me.tmp_saved,me.tmp_received);
						}
					}
				}
				if (me.received == TgcRoot.No_signal) {
					me.tmp_input = new int[] {};
					me.tmp_saved = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					me.received = TgcRoot.No_signal;
				} else {
					input = me.tmp_saved;
					while ( !( (me.tmp_input.length == 0))) {
						input = FiacreQueueUtil.enqueue(id, input, FiacreQueueUtil.first(me.tmp_input), actions);
						me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					}
					me.tmp_saved = new int[] {};
					me.tmp_input = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					if (me.received == TgcRoot.Enter1) {
						me.received = TgcRoot.No_signal;
						me.state = Busy12;
						if (true) return true;
					} else {
						if (me.received == TgcRoot.Exit2) {
							me.received = TgcRoot.No_signal;
							me.state = Tempo;
							if (true) return true;
						}
					}
				}
				me.state = Busy2;
				if (true) return true;
			} finally {
				inputRef.set(configuration, input);
			}
			if (true) { return true; } else { return true; }
		}

	}

;

	private final Transition transition3 = 	new Transition(id) {
		@Override
		public void updateModifiedBehaviors(Configuration configuration, ExplorationContext context, boolean[] modifiedBehaviors) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			modifiedBehaviors[inputRef.ownerId] = true;
		}

		@Override
		public boolean guard(Configuration configuration, ExplorationContext context) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			if (me.state != Busy12) return false;
			return true;
		}

		@Override
		public boolean action(ExplorationContext context, List<AtomicAction> actions, Configuration configuration) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			try {
				me.tmp_input = input;
				while (( !( (me.tmp_input.length == 0))) && (me.received == TgcRoot.No_signal)) {
					me.tmp_received =  FiacreQueueUtil.first(me.tmp_input);
					me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					if ((me.tmp_received == TgcRoot.Exit1) || (me.tmp_received == TgcRoot.Exit2)) {
						me.received = me.tmp_received;
					} else {
						if ((me.tmp_received == TgcRoot.Open) || (me.tmp_received == TgcRoot.Close)) {
							me.tmp_saved = FiacreQueueUtil.enqueue(me.tmp_saved,me.tmp_received);
						}
					}
				}
				if (me.received == TgcRoot.No_signal) {
					me.tmp_input = new int[] {};
					me.tmp_saved = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					me.received = TgcRoot.No_signal;
				} else {
					input = me.tmp_saved;
					while ( !( (me.tmp_input.length == 0))) {
						input = FiacreQueueUtil.enqueue(id, input, FiacreQueueUtil.first(me.tmp_input), actions);
						me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					}
					me.tmp_saved = new int[] {};
					me.tmp_input = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					if (me.received == TgcRoot.Exit1) {
						me.received = TgcRoot.No_signal;
						me.state = Busy2;
						if (true) return true;
					} else {
						if (me.received == TgcRoot.Exit2) {
							me.received = TgcRoot.No_signal;
							me.state = Busy1;
							if (true) return true;
						}
					}
				}
				me.state = Busy12;
				if (true) return true;
			} finally {
				inputRef.set(configuration, input);
			}
			if (true) { return true; } else { return true; }
		}

	}

;

	private final Transition transition4 = 	new Transition(id) {
		@Override
		public void updateModifiedBehaviors(Configuration configuration, ExplorationContext context, boolean[] modifiedBehaviors) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] output = outputRef.get(configuration);
			int[] input = inputRef.get(configuration);
			modifiedBehaviors[outputRef.ownerId] = true;
			modifiedBehaviors[inputRef.ownerId] = true;
		}

		@Override
		public boolean guard(Configuration configuration, ExplorationContext context) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			if (me.state != Tempo) return false;
			return true;
		}

		@Override
		public boolean action(ExplorationContext context, List<AtomicAction> actions, Configuration configuration) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] output = outputRef.get(configuration);
			int[] input = inputRef.get(configuration);
			try {
				me.tmp_input = input;
				while (( !( (me.tmp_input.length == 0))) && (me.received == TgcRoot.No_signal)) {
					me.tmp_received =  FiacreQueueUtil.first(me.tmp_input);
					me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					if ((((me.tmp_received == TgcRoot.Enter1) || (me.tmp_received == TgcRoot.Enter2)) || (me.tmp_received == TgcRoot.Close)) || (me.tmp_received == TgcRoot.Tick)) {
						me.received = me.tmp_received;
					}
				}
				if (me.received == TgcRoot.No_signal) {
					me.tmp_input = new int[] {};
					me.tmp_saved = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					me.received = TgcRoot.No_signal;
				} else {
					input = me.tmp_input;
					me.tmp_saved = new int[] {};
					me.tmp_input = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					if (me.received == TgcRoot.Enter1) {
						me.received = TgcRoot.No_signal;
						me.state = Busy1;
						if (true) return true;
					} else {
						if (me.received == TgcRoot.Enter2) {
							me.received = TgcRoot.No_signal;
							me.state = Busy2;
							if (true) return true;
						} else {
							if (me.received == TgcRoot.Close) {
								me.received = TgcRoot.No_signal;
								me.state = Safe;
								if (true) return true;
							} else {
								if (me.received == TgcRoot.Tick) {
									output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Gate_up, actions);
									output = FiacreQueueUtil.enqueue(id, output,TgcRoot.Light_off, actions);
									me.received = TgcRoot.No_signal;
									me.state = Idle;
									if (true) return true;
								}
							}
						}
					}
				}
				me.state = Tempo;
				if (true) return true;
			} finally {
				outputRef.set(configuration, output);
				inputRef.set(configuration, input);
			}
			if (true) { return true; } else { return true; }
		}

	}

;

	private final Transition transition5 = 	new Transition(id) {
		@Override
		public void updateModifiedBehaviors(Configuration configuration, ExplorationContext context, boolean[] modifiedBehaviors) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			modifiedBehaviors[inputRef.ownerId] = true;
		}

		@Override
		public boolean guard(Configuration configuration, ExplorationContext context) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			if (me.state != Safe) return false;
			return true;
		}

		@Override
		public boolean action(ExplorationContext context, List<AtomicAction> actions, Configuration configuration) {
			GBConfiguration me = (GBConfiguration) configuration.behaviorConfigurations[id];
			int[] input = inputRef.get(configuration);
			try {
				me.tmp_input = input;
				while (( !( (me.tmp_input.length == 0))) && (me.received == TgcRoot.No_signal)) {
					me.tmp_received =  FiacreQueueUtil.first(me.tmp_input);
					me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					if (me.tmp_received == TgcRoot.Open) {
						me.received = me.tmp_received;
					} else {
						if ((((me.tmp_received == TgcRoot.Exit1) || (me.tmp_received == TgcRoot.Exit2)) || (me.tmp_received == TgcRoot.Enter1)) || (me.tmp_received == TgcRoot.Enter2)) {
							me.tmp_saved = FiacreQueueUtil.enqueue(me.tmp_saved,me.tmp_received);
						}
					}
				}
				if (me.received == TgcRoot.No_signal) {
					me.tmp_input = new int[] {};
					me.tmp_saved = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					me.received = TgcRoot.No_signal;
				} else {
					input = me.tmp_saved;
					while ( !( (me.tmp_input.length == 0))) {
						input = FiacreQueueUtil.enqueue(id, input, FiacreQueueUtil.first(me.tmp_input), actions);
						me.tmp_input =  FiacreQueueUtil.dequeue(me.tmp_input);
					}
					me.tmp_saved = new int[] {};
					me.tmp_input = new int[] {};
					me.tmp_received = TgcRoot.No_signal;
					if (me.received == TgcRoot.Open) {
						me.received = TgcRoot.No_signal;
						me.state = Tempo;
						if (true) return true;
					} else {
					}
				}
				me.state = Safe;
				if (true) return true;
			} finally {
				inputRef.set(configuration, input);
			}
			if (true) { return true; } else { return true; }
		}

	}

;

	private final Transition[] transitions = 	new Transition[] {
		transition0,
		transition1,
		transition2,
		transition3,
		transition4,
		transition5,
	}
;

	@Override
	public Transition[] getTransitions(Configuration configuration) {
		return transitions;
	}

	@Override
	public BehaviorConfiguration createInitialConfiguration(Configuration conf) {
		GBConfiguration me = new GBConfiguration();
		//Initial action code.
		me.received = TgcRoot.No_signal;
		me.tmp_received = TgcRoot.No_signal;
		me.tmp_input = new int[] {};
		me.tmp_saved = new int[] {};
		me.state = Idle;
		return me;
	}

	public String toStringConfiguration(BehaviorConfiguration behaviorConfiguration) {
		GBConfiguration me = (GBConfiguration) behaviorConfiguration;
		StringBuilder text = new StringBuilder();
		text.append("proc: ");
		text.append("'");
		text.append(name);
		text.append("' [");
		switch (me.state) {
		case GBBehavior.Idle: 
			text.append("@idle,");
			break;
		case GBBehavior.Busy1: 
			text.append("@busy1,");
			break;
		case GBBehavior.Busy2: 
			text.append("@busy2,");
			break;
		case GBBehavior.Busy12: 
			text.append("@busy12,");
			break;
		case GBBehavior.Tempo: 
			text.append("@tempo,");
			break;
		case GBBehavior.Safe: 
			text.append("@safe,");
			break;
		}
		text.append("\n\t- received=");
		text.append(Integer.toString(me.received));
		text.append("\n\t- tmp_received=");
		text.append(Integer.toString(me.tmp_received));
		text.append("\n\t- tmp_input=");
		text.append(Arrays.toString(me.tmp_input));
		text.append("\n\t- tmp_saved=");
		text.append(Arrays.toString(me.tmp_saved));
		text.append("\n]");
		return text.toString();
	}

	public String toDotStringConfiguration(BehaviorConfiguration behaviorConfiguration) {
		GBConfiguration me = (GBConfiguration) behaviorConfiguration;
		StringBuilder text = new StringBuilder();
		text.append("'");
		text.append(name);
		text.append("' [");
		switch (me.state) {
		case GBBehavior.Idle: 
			text.append("@idle,");
			break;
		case GBBehavior.Busy1: 
			text.append("@busy1,");
			break;
		case GBBehavior.Busy2: 
			text.append("@busy2,");
			break;
		case GBBehavior.Busy12: 
			text.append("@busy12,");
			break;
		case GBBehavior.Tempo: 
			text.append("@tempo,");
			break;
		case GBBehavior.Safe: 
			text.append("@safe,");
			break;
		}
		text.append(" received=");
		text.append(Integer.toString(me.received));
		text.append(" tmp_received=");
		text.append(Integer.toString(me.tmp_received));
		text.append(" tmp_input=");
		text.append(Arrays.toString(me.tmp_input));
		text.append(" tmp_saved=");
		text.append(Arrays.toString(me.tmp_saved));
		text.append("]");
		return text.toString();
	}

	@Override
	public BehaviorConfiguration readConfiguration(Boost boost) {
		GBConfiguration me = new GBConfiguration();
		me.state = boost.readShort();
		me.received = boost.readInt();
		me.tmp_received = boost.readInt();
		me.tmp_input = new int[100];
		me.tmp_input = new int[boost.readInt()];
		for (int i=0; i<me.tmp_input.length; i++) {
			me.tmp_input[i] = boost.readInt();
		}
		me.tmp_saved = new int[100];
		me.tmp_saved = new int[boost.readInt()];
		for (int i=0; i<me.tmp_saved.length; i++) {
			me.tmp_saved[i] = boost.readInt();
		}
		return me;
	}

	@Override
	public void writeConfiguration(Boost boost, BehaviorConfiguration processConfiguration) {
		GBConfiguration me = (GBConfiguration) processConfiguration;
		boost.writeShort(me.state);
		boost.writeInt(me.received);
		boost.writeInt(me.tmp_received);
		boost.writeInt(me.tmp_input.length);
		for (int i=0; i<me.tmp_input.length; i++) {
			boost.writeInt(me.tmp_input[i]);
		}
		boost.writeInt(me.tmp_saved.length);
		for (int i=0; i<me.tmp_saved.length; i++) {
			boost.writeInt(me.tmp_saved[i]);
		}
	}

}

