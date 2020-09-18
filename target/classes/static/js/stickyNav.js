window.onscroll = function() {
    myFunction1()
};

var navbar = document.getElementById("navbar");
var sticky = navbar.offsetTop;

function myFunction1() {
    if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}

window.addEventListener('scroll', stickyNavigation);
