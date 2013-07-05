// custom forms script
var cell_maxVisibleOptions = 20;
var cell_all_selects = false;
var cell_active_select = null;
var cell_selectText = "Please select";


// collecting select options
function cell_populateSelectOptions(me) {
	me._options.innerHTML = "";

	for ( var w = 0; w < me.options.length; w++) {
		var optionHolder = document.createElement('li');
		var optionLink = document.createElement('a');
		var optionTxt;
		if (me.options[w].title.indexOf('image') != -1) {
			optionTxt = document.createElement('img');
			optionSpan = document.createElement('span');
			optionTxt.src = me.options[w].title;
			optionSpan = document.createTextNode(me.options[w].text);
		} else {
			optionTxt = document.createTextNode(me.options[w].text);
		}

		// hidden default option
		if (me.options[w].className.indexOf('default') != -1) {
			optionHolder.style.display = 'none'
		}

		optionLink.href = "javascript:cell_showOptions('" + me._number
				+ "'); cell_selectMe('" + me.id + "'," + w + ",'" + me._number
				+ "');";
		if (me.options[w].title.indexOf('image') != -1) {
			optionLink.appendChild(optionTxt);
			optionLink.appendChild(optionSpan);
		} else {
			optionLink.appendChild(optionTxt);
		}
		optionHolder.appendChild(optionLink);
		me._options.appendChild(optionHolder);
		// check for pre-selected items
		if (me.options[w].selected) {
			cell_selectMe(me.id, w, me._number, true);
		}
	}
	if (me.disabled) {
		me._disabled.style.display = "block";
	} else {
		me._disabled.style.display = "none";
	}
}

function cell_updateSelectSelectedValue(selectFieldId) {
	me = document.getElementById(selectFieldId);
	if (me != null) {
		cell_selectMe(me.id, me.selectedIndex, me._number, true);
	}
}

// selecting me
function cell_selectMe(selectFieldId, linkNo, selectNo, quiet) {
	selectField = document.getElementById(selectFieldId);
	if (selectField == null) {
		return;
	}
	for ( var k = 0; k < selectField.options.length; k++) {
		if (k == linkNo) {
			selectField.options[k].selected = true;
		} else {
			selectField.options[k].selected = false;
		}
	}
	// show selected option
	textVar = document.getElementById("mySelectText" + selectNo);
	var newText;
	var optionSpan;
	if (selectField.options[linkNo].title.indexOf('image') != -1) {
		newText = document.createElement('img');
		newText.src = selectField.options[linkNo].title;
		optionSpan = document.createElement('span');
		optionSpan = document.createTextNode(selectField.options[linkNo].text);
	} else {
		newText = document.createTextNode(selectField.options[linkNo].text);
	}
	if (selectField.options[linkNo].title.indexOf('image') != -1) {
		if (textVar.childNodes.length > 1)
			textVar.removeChild(textVar.childNodes[0]);
		textVar.replaceChild(newText, textVar.childNodes[0]);
		textVar.appendChild(optionSpan);
	} else {
		if (textVar.childNodes.length > 1)
			textVar.removeChild(textVar.childNodes[0]);
		textVar.replaceChild(newText, textVar.childNodes[0]);
	}
	if (!quiet) {
		(function($) {
			$(selectField).trigger('change');
		})(jQuery);
	}
}

// showing options
function cell_showOptions(g) {
	_elem = document.getElementById("optionsDiv" + g);
	_elem._parent = document.getElementById(g);
	var divArea = document.getElementById("sarea" + g);
	if (cell_active_select && cell_active_select != _elem) {
		cell_active_select.className = cell_active_select.className.replace(
				'ext_comboboxoptionsDivVisible',
				' ext_comboboxoptionsDivInvisible');
		cell_active_select.style.height = "auto";
	}
	if (_elem.className.indexOf("ext_comboboxoptionsDivInvisible") != -1) {
		_elem.style.left = "-9999px";
		// _elem.style.top = findPosY(divArea) + 'px';
		_elem.className = _elem.className.replace(
				'ext_comboboxoptionsDivInvisible', '');
		_elem.className += " ext_comboboxoptionsDivVisible";
		/*
		 * if (_elem.offsetHeight > 200) { _elem.style.height = "200px"; }
		 */
		// _elem.style.left = findPosX(divArea) + 'px';
		_elem.style.left = "";
		cell_active_select = _elem;
		if (_elem._parent.className.indexOf('ext_comboboxselectAreaActive') < 0) {
			_elem._parent.className += ' ext_comboboxselectAreaActive';
		}
		if (document.documentElement) {
			document.documentElement.onclick = hideSelectOptions;
		} else {
			window.onclick = hideSelectOptions;
		}
	} else if (_elem.className.indexOf("ext_comboboxoptionsDivVisible") != -1) {
		hideActiveSelectDrop();
	}
}