var myVar;
var myVar1;

function myFunction() {
    myVar = showPage();
    myVar1 = setTimeout(removeDisplay, 9999999);
}

function showPage() {


    document.getElementById("loader").style.display = "block";

}
function removeDisplay(){
    document.getElementById("loader").style.display = "none";

}
