// custom forms script
var maxVisibleOptions = 20;
var all_selects = false;
var active_select = null;
var selectText = "Please select";

var ie = (function() {
	var undef, v = 3, div = document.createElement('div'), all = div
			.getElementsByTagName('i');
	while (div.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->',
			all[0])
		;
	return v > 4 ? v : undef;
}());

// replace selects
function stylingSelect(selector) {
	// return;

	selector.replaced = false;
	selector.className = selector.className
			.replace('ext_comboboxOuttaHere', '');
	// selector._optionsDiv._parent.parentNode.removeChild(selector._optionsDiv._parent);
	// selector._optionsDiv.parentNode.removeChild(selector._optionsDiv);

	if (!selector.replaced) {
		selector._number = selector.id;
		// create and build div structure
		var selectArea = document.createElement("div");
		var left = document.createElement("span");
		left.className = "ext_comboboxleft";
		selectArea.appendChild(left);

		var disabled = document.createElement("span");
		disabled.className = "disabled";
		selectArea.appendChild(disabled);

		selector._disabled = disabled;
		var center = document.createElement("span");
		var button = document.createElement("a");
		var text = document.createTextNode("please select");
		center.id = "mySelectText" + selector.id;

		selectArea.style.width = "100%";
		if (selector.parentNode.className.indexOf("type2") != -1) {
			button.href = "javascript:showOptions('" + selector.id + "',true)";
		} else {
			button.href = "javascript:showOptions('" + selector.id + "',false)";
		}

		button.className = "ext_comboboxselectButton";
		selectArea.className = "ext_comboboxselectArea";
		selectArea.className += " " + selector.className;
		selectArea.id = "sarea" + selector.id;
		center.className = "ext_comboboxcenter";
		center.appendChild(text);
		selectArea.appendChild(center);
		selectArea.appendChild(button);

		// insert select div
		selector.parentNode.insertBefore(selectArea, selector);
		// build & place options div

		var optionsDiv = document.createElement("div");
		var optionsList = document.createElement("ul");
		var optionsListHolder = document.createElement("div");

		optionsListHolder.className = "ext_comboboxselect-center";
		optionsListHolder.innerHTML = "<div class='ext_comboboxselect-center-right'></div>";
		optionsDiv.innerHTML += "<div class='ext_comboboxselect-top'><div class='ext_comboboxselect-top-left'></div><div class='ext_comboboxselect-top-right'></div></div>";

		optionsListHolder.appendChild(optionsList);
		optionsDiv.appendChild(optionsListHolder);

		selector._optionsDiv = optionsDiv;
		selector._options = optionsList;
		optionsList.id = "optionsULTag" + selector.id;

		optionsDiv.style.width = "auto";
		optionsDiv._parent = selectArea;

		optionsDiv.className = "ext_comboboxoptionsDivInvisible";
		optionsDiv.id = "optionsDiv" + selector.id;

		if (selector.className.length) {
			optionsDiv.className += ' ext_comboboxdrop-' + selector.className;
		}

		populateSelectOptions(selector);
		optionsDiv.innerHTML += "<div class='ext_comboboxselect-bottom'><div class='ext_comboboxselect-bottom-left'></div><div class='ext_comboboxselect-bottom-right'></div></div>";
		document.body.appendChild(optionsDiv);
		selector.replaced = true;
		all_selects = true;
		// too many options
		if (selector.options.length > maxVisibleOptions
				&& optionsDiv.className.indexOf("ext_comboboxoptionsDivScroll") == -1) {
			optionsDiv.className += ' ext_comboboxoptionsDivScroll';
		}

		// hide the select field
		if (selector.className.indexOf('default') != -1) {
			selectArea.style.display = 'none';
		} else {
			selector.className += " ext_comboboxOuttaHere";
		}

	}
	all_selects = true;
}

function updateSize(selectFieldId, stWidth) {
	// stWidth = document.getElementById(selectFieldId).width;
	// selectArea = document.getElementById("sarea" + selectFieldId);
	// selectArea.style.width = stWidth + "px";
	// optionsDiv = document.getElementById("optionsDiv" + selectFieldId);
	// optionsDiv.style.width = stWidth + "px";
}

function refreshSelectOptions(selectFieldId) {
	me = document.getElementById(selectFieldId);
	optionsUL = document.getElementById("optionsULTag" + selectFieldId);
	optionsUL.innerHTML = "";
	optionsDiv = document.getElementById("optionsDiv" + selectFieldId);
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

		optionLink.href = "javascript:showOptions('" + me._number
				+ "'); selectMe('" + me.id + "'," + w + ",'" + me._number
				+ "');";
		if (me.options[w].title.indexOf('image') != -1) {
			optionLink.appendChild(optionTxt);
			optionLink.appendChild(optionSpan);
		} else {
			optionLink.appendChild(optionTxt);
		}
		optionHolder.appendChild(optionLink);
		optionsUL.appendChild(optionHolder);
		// check for pre-selected items
		if (me.options[w].selected) {
			selectMe(me.id, w, me._number, true);
		}
	}
	// too many options
	if (me.options.length > maxVisibleOptions
			&& optionsDiv.className.indexOf("ext_comboboxoptionsDivScroll") == -1) {
		optionsDiv.className += ' ext_comboboxoptionsDivScroll';
	}
}

// collecting select options
function populateSelectOptions(me) {
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

		optionLink.href = "javascript:showOptions('" + me._number
				+ "'); selectMe('" + me.id + "'," + w + ",'" + me._number
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
			selectMe(me.id, w, me._number, true);
		}
	}
	if (me.disabled) {
		me._disabled.style.display = "block";
	} else {
		me._disabled.style.display = "none";
	}
}

function updateSelectSelectedValue(selectFieldId) {
	me = document.getElementById(selectFieldId);
	if (me != null) {
		selectMe(me.id, me.selectedIndex, me._number, true);
	}
}

// selecting me
function selectMe(selectFieldId, linkNo, selectNo, quiet) {
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
	if (!quiet && all_selects) {

		if (ie === undefined) {
			(function($) {
				$(selectField).trigger('change');
			})(jQuery);
		} else {
			selectField.fireEvent("onchange");
		}
	}
}

// showing options
function showOptions(g) {
	_elem = document.getElementById("optionsDiv" + g);
	var divArea = document.getElementById("sarea" + g);
	if (active_select && active_select != _elem) {
		active_select.className = active_select.className.replace(
				'ext_comboboxoptionsDivVisible',
				' ext_comboboxoptionsDivInvisible');
		active_select.style.height = "auto";
	}
	if (_elem.className.indexOf("ext_comboboxoptionsDivInvisible") != -1) {
		_elem.style.left = "-9999px";
		_elem.style.top = findPosY(divArea) + divArea.offsetHeight + 'px';
		_elem.className = _elem.className.replace(
				'ext_comboboxoptionsDivInvisible', '');
		_elem.className += " ext_comboboxoptionsDivVisible";
		/*
		 * if (_elem.offsetHeight > 200) { _elem.style.height = "200px"; }
		 */
		_elem.style.left = findPosX(divArea) + 'px';

		active_select = _elem;
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
function hideSelectOptions(e) {
	if (active_select) {
		if (!e)
			e = window.event;
		var _target = (e.target || e.srcElement);
		if (!isElementBefore(_target, 'selectArea')
				&& !isElementBefore(_target, 'optionsDiv')) {
			hideActiveSelectDrop();
			if (document.documentElement) {
				document.documentElement.onclick = function() {
				};
			} else {
				window.onclick = null;
			}
		}
	}
}

function isElementBefore(_el, _class) {
	var _parent = _el;
	do {
		_parent = _parent.parentNode;
	} while (_parent && _parent.className != null
			&& _parent.className.indexOf(_class) == -1)
	return _parent.className && _parent.className.indexOf(_class) != -1;
}
function hideActiveSelectDrop() {
	if (active_select) {
		active_select.style.height = "auto";
		active_select.className = active_select.className.replace(
				'ext_comboboxoptionsDivVisible', '');
		active_select.className = active_select.className.replace(
				'ext_comboboxoptionsDivInvisible', '');
		active_select._parent.className = active_select._parent.className
				.replace('ext_comboboxselectAreaActive', '')
		active_select.className += " ext_comboboxoptionsDivInvisible";
		active_select = false;
	}
}
function findPosY(obj) {
	if (obj.getBoundingClientRect) {
		var scrollTop = window.pageYOffset
				|| document.documentElement.scrollTop
				|| document.body.scrollTop;
		var clientTop = document.documentElement.clientTop
				|| document.body.clientTop || 0;
		return Math.round(obj.getBoundingClientRect().top + scrollTop
				- clientTop);
	} else {
		var posTop = 0;
		while (obj.offsetParent) {
			posTop += obj.offsetTop;
			obj = obj.offsetParent;
		}
		return posTop;
	}
}

function findPosX(obj) {
	if (obj.getBoundingClientRect) {
		var scrollLeft = window.pageXOffset
				|| document.documentElement.scrollLeft
				|| document.body.scrollLeft;
		var clientLeft = document.documentElement.clientLeft
				|| document.body.clientLeft || 0;
		return Math.round(obj.getBoundingClientRect().left + scrollLeft
				- clientLeft);
	} else {
		var posLeft = 0;
		while (obj.offsetParent) {
			posLeft += obj.offsetLeft;
			obj = obj.offsetParent;
		}
		return posLeft;
	}
};