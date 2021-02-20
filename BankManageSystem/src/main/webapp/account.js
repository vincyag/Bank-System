/**
 * 
 */
function openTransaction(evt, taskName) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the link that opened the tab
  document.getElementById(taskName).style.display = "block";
  evt.currentTarget.className += " active";
}

var isConfirm = false;
var btnPressed = function(name) {
 isConfirm = 'Confirm' === name;
};

var validate = function() {
 if(isConfirm) {
 	if(document.forms["service"]["amount"].value <= 0) {
		alert("Unable to process request. Enter amount greater than 0.");
		return false;
	}
	return true;
 }
 return true;
};

function validateForm() {
	if(document.forms["service"]["amount"].value <= 0) {
		alert("Unable to process request. Enter amount greater than 0.");
		return false;
	}
	return true;
}