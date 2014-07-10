function prefix()
	return "digraph trace {\n"
end

function configuration(configuration)
	return 
		configuration.id .. '\t[label="' ..
		configuration['{SM}1'].state ..
		'"];\n'
end

function transition(source, actions, target)
	-- only prints label for {SM}1 sends.
	local label = ""
	--for index, value in pairs(actions) do
    --	if value.behavior == "{SM}1" and value.commType == '!' then 
    --		label = label .. tostring(value)
    --	end
   -- end
	return 
		tostring(source.id) .. ' -> ' .. tostring(target.id) .. 
		' [label="' .. tostring(label) .. '"];\n'
end

function suffix() 
	return "}\n"
end