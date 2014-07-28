-- ----------------------------------------------------------------------------
-- Trace printer example
-- ----------------------------------------------------------------------------
-- Jean-Charles Roger (jean-charles.roger@ensta-bretagne.fr)
-- ----------------------------------------------------------------------------
-- This LUA file can be used to create an adhoc output for a OBP Explorer
-- trace. OBPe will call the functions and append the result into on string
-- representation. 
--
-- For one given trace: '0 -a-> 3 -b-> 5 -c-> 6', the calls will be:
-- - prefix()
-- - configuration(0)
-- - action(0, a, 3)
-- - configuration(3)
-- - action(3, b, 5)
-- - configuration(5)
-- - action(5, c, 6)
-- - configuration(6)
-- - suffix()
--
-- You can find a quick reference guide to LUA here:
-- http://lua-users.org/files/wiki_insecure/users/thomasl/luarefv51single.pdf
-- ----------------------------------------------------------------------------

-- Prefix for output.
function prefix(context)
	return "digraph trace {\n"
end

-- Creates a string for a configuration. If result is 'nil', the default string
-- will be added to the output. To create an empty description, just return an
-- empty string ("").
--
-- Notes:
-- This function is called once for each configuration present in the trace. 
-- The calls are ordered by apparition in the trace. The configuration id
-- sequence is increasing. Considering two calls: configuration(c1) and configuration(c2)
-- if c1 is called first, then c1.id < c2.id.
function configuration(configuration)
	return 
		configuration.id .. '\t[label="' ..
		configuration['{SM}1'].state ..
		'"];\n'
end

-- Creates a string description for a transition. If result is 'nil' the 
-- default string will be add to output. To create an empty description, just 
-- return an empty string ("").
--
-- Notes:
-- This function is called once for each transition in the trace. For one call
-- to another the target configuration becomes the source of the next one.
function transition(source, actions, target) 
	return 
		tostring(source.id) .. ' -> ' .. tostring(target.id) .. 
		' [label="' .. tostring(actions) .. '"];\n'
end

-- Suffix for output.
function suffix(context) 
	return "}\n"
end