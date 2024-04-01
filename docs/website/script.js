
const home_nav = document.getElementById("home_nav");
const about_nav = document.getElementById("about_nav");
const postmortem_nav = document.getElementById("postmortem_nav");

const home_page = document.getElementById("home_page");
const about_page = document.getElementById("about_us_page");
const postm_page = document.getElementById("postmortem_page");

const allPages = [home_page, about_page, postm_page];

const overview_button = document.getElementById("overview_video")

/* Change Pages */

//hide all pages
function hideAllPages() {
    allPages.forEach(page => {
        page.classList.add("hidden");
    });
}


//show a specific page
function showPage(page) {
    hideAllPages();
    page.classList.remove("hidden")
}

//handles navigation bar events
home_nav.addEventListener('click', function () {
    showPage(home_page);
});

about_nav.addEventListener('click', function () {
    showPage(about_page);
});

postmortem_nav.addEventListener('click', function () {
    showPage(postm_page);
});

overview_button.addEventListener('click', function () {
   
});
// end of Change Pages


/* Initial */
showPage(home_page)