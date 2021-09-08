// Author: Luca Anthony Schwarz (sunfl0w)

// Toggles style based on the state of the darkmode toggle switch by sending a POST-Request
document.getElementById('darkmode-toggle').onchange = function(element) {
    var request = new XMLHttpRequest();
    request.open("POST", "/set_style", false);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify({
        darkmodeEnabled: element.target.checked
    }));
    location.reload();
};