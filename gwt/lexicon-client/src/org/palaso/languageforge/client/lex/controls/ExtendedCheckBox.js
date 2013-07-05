function stylingCheckBox(checkbox, checkboxLabel) {
	if (checkbox.className.indexOf("ext_checkboxOuttaHere") == -1) {
		checkbox.className += " ext_checkboxOuttaHere";
		checkboxLabel.className += " ext_checkboxOuttaHere";
		var checkboxArea = document.createElement("div");
		var newLabel = document.createElement('label');
		var newLabelText;
		if (checkboxLabel.firstChild) {
			newLabelText = document
					.createTextNode(checkboxLabel.firstChild.data);
		} else {
			newLabelText = document.createTextNode("");
		}

		newLabel.appendChild(newLabelText);

		if (checkbox.checked) {
			checkbox.checked = true;
			checkboxArea.checked = true;
			checkboxArea.className = "ext_checkboxAreaChecked";
			if (newLabel) {
				newLabel.className += " ext_checkboxAreaCheckedLabel"
			}
		} else {
			checkbox.checked = false;
			checkboxArea.checked = false;
			checkboxArea.className = "ext_checkboxArea";
		}
		checkboxArea.id = "myCheckbox" + checkbox.id;
		newLabel.id = "myCheckboxLabel" + checkbox.id;

		checkbox.parentNode.insertBefore(checkboxArea, checkbox);
		checkbox._ca = checkboxArea;

		checkboxLabel.parentNode.insertBefore(newLabel, checkboxLabel);
		checkbox._cl = newLabel;

		checkboxArea.onclick = new Function('rechangeCheckboxes("'
				+ checkbox.id + '")');
		// if (checkboxLabel) {
		// checkboxLabel.onclick = new Function('changeCheckboxes("'
		// + checkbox.id + '")');
		// }
	}
	return true;
}

// checking checkboxes
function checkCheckboxes(who, action) {
	checkbox = document.getElementById(who);
	var what = checkbox._ca;
	if (action == true) {
		what.className = "ext_checkboxAreaChecked";
		what.checked = true;
	}
	if (action == false) {
		what.className = "ext_checkboxArea";
		what.checked = false;
	}
	if (checkbox._cl) {
		if (checkbox.checked) {
			if (checkbox._cl.className.indexOf("ext_checkboxAreaCheckedLabel") < 0) {
				checkbox._cl.className += " ext_checkboxAreaCheckedLabel";
			}
		} else {
			checkbox._cl.className = checkbox._cl.className.replace(
					"ext_checkboxAreaCheckedLabel", "");
		}
	}

}

// rechanging checkboxes
function rechangeCheckboxes(who) {
	checkbox = document.getElementById(who);

	var tester = false;
	if(checkbox.checked == true) {
		tester = false;
	}
	else {
		tester = true;
	}
	checkbox.checked = tester;
	checkCheckboxes(who, tester);
	(function($) {
		$(checkbox).trigger('click');
		checkbox.checked = !checkbox.checked;
	})(jQuery);
	
}

function changeCheckboxes(who) {
	checkbox = document.getElementById(who);
	setTimeout(function() {
		if (checkbox.checked) {
			checkCheckboxes(who, true);
		} else {
			checkCheckboxes(who, false);
		}
	}, 10);
}
