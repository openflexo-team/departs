function prefix()
	return "digraph trace {\n"
end

last_id = 0
configuration_labels = {}

function add_label(id, label)
	labels = configuration_labels[id];
	if labels == nil then
		-- sets to given label
		configuration_labels[id] = {label}
	else
		-- adds new label to the end (if not already at the end)
		if labels[#labels] ~= label then
			table.insert(labels, label)
		end
	end
end 

function configuration(configuration)
	-- prints nothing, the transition does the work
	return ""
end

function transition(source, actions, target)
	-- only prints label for {SM}1 sends.
	local label = ""
	for index, value in pairs(actions) do
    	if value.behavior == "{SM}1" and value.commType == '!' then 
    		label = label .. tostring(value)
    	end
    end

    if label ~= "" then
    	source_id = last_id
    	last_id = last_id + 1
    	target_id = last_id

    	add_label(source_id, source['{SM}1'].state)
    	add_label(target_id, target['{SM}1'].state)

		return 
			source_id .. ' -> ' .. target_id .. 
			' [label="' .. label .. '"];\n'
    else
    	return "" 
    end
end

function suffix() 
	result = ""
	for index, value in pairs(configuration_labels) do
		result = result .. index .. '\t[label="' .. table.concat(value, ";") .. '"];\n'
	end
	result = result .. "}\n"
	return result
end