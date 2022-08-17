/*
 * This file contains teh javascript functions for the website
 */

 // JS function to open the B2B link in a new window/tab
function newTabB2B() {
            window.open(
            "/api", "_blank");
        }
 // JS function with variables, loop, and function/onClick event
function hatWarble(){

    // get the div to put the warbles in
    var block = document.getElementById("warbleGoesHere");

    // get the number of warbles to add from the form
    var number = document.getElementById("numWarbles").value;

    // for loop to iterate through the number of warbles
    for (let index = 0; index < number; index++) {

        // create the column div wrapper for the row
        var wrapper = document.createElement("div");
        wrapper.classList.add("col");
        wrapper.style = "margin-top: 20px;"

        // create the picture element
        var pic = document.createElement("img");
        pic.src = "../images/hat_warble.gif";
        pic.alt = "Paul Rudd doing the hat warble";
        pic.style = "width: 100px"

        // put the picture in the col div
        wrapper.appendChild(pic);

        // add the col div to the row
        block.appendChild(wrapper);

    }

}