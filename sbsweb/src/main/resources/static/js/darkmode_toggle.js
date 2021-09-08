// Author: Luca Anthony Schwarz (sunfl0w)

// Toggles style based on the state of the darkmode toggle switch
document.getElementById('darkmode-toggle').onchange = function(element) {
    if (element.target.checked) {
        changeStyleCSS(true);
    } else {
        changeStyleCSS(false);
    }
};

// Changes the style css file and the toggle switch according to the stored mode value
window.onload = function() {
    if (sessionStorage.getItem("darkmode") == "enabled") {
        changeStyleCSS(true);
        document.getElementById('darkmode-toggle').setAttribute("checked", "");
    } else {
        changeStyleCSS(false);
        document.getElementById('darkmode-toggle').removeAttribute("checked")
    }
};

// Changes the relevant link in the style.html file to either the light- or darkmode css file
function changeStyleCSS(darkmodeOn) {
    var currentLink = document.getElementById("style");
    if (darkmodeOn == true) {
        currentLink.setAttribute("href", "/css/sbs_bootstrap_dark.css");
        sessionStorage.setItem("darkmode", "enabled");
    } else {
        currentLink.setAttribute("href", "/css/sbs_bootstrap_light.css");
        sessionStorage.setItem("darkmode", "disabled");
    }
}