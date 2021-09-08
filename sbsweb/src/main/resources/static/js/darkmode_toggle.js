document.getElementById('darkmode-toggle').onchange = function(element) {
    if (element.target.checked) {
        changeStyleCSS(true);
    } else {
        changeStyleCSS(false);
    }
};

function changeStyleCSS(darkmodeOn) {
    var currentLink = document.getElementById("style");
    if (darkmodeOn == true) {
        currentLink.setAttribute("href", "/css/sbs_bootstrap_dark.css");
    } else {
        currentLink.setAttribute("href", "/css/sbs_bootstrap_light.css");
    }
}