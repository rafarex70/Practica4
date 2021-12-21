$(document).ready(function(){
    var query = '{ eolicPlants{ id, city, planning }}';
    $.ajax({
        method: 'post',
        url: "http://localhost:3000/graphql",
        data: JSON.stringify({"query": query}),
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            var event_data = '';
            $.each(response.data.eolicPlants, function(index, value){
                event_data += '<tr>';
                event_data += '<td>'+value.id+'</td>';
                event_data += '<td>'+value.city+'</td>';
                event_data += '<td>'+value.planning+'</td>';
                event_data += '</tr>';
            });
            $("#list_table_json").append(event_data);
        },
        error: function(d){
            console.log("Error: "+d.error);
            alert("Error: "+d.error);
        }
    });
});

async function create() {
    spinner();
    var query = 'mutation { createEolicPlant(city:"'+document.getElementById("city").value+'") { id, city, planning } }';
    $.ajax({
        method: 'post',
        url: "http://localhost:3000/graphql",
        data: JSON.stringify({"query": query}),
        contentType: 'application/json',
        success: await function(response){
            console.log(response);
            alert("Insert eolic plant"
                +"\n ID: "+response.data.createEolicPlant.id
                +"\n city: "+response.data.createEolicPlant.city
                +"\n planning: "+response.data.createEolicPlant.planning+"");
            location.reload(); 
        },
        error: function(d){
            /*console.log("Error: "+d.error;*/
            alert("Error: "+d.error);
        }
    });
}

function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("list_table_json");
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
        // Start by saying there should be no switching:
        shouldSwitch = false;
        /* Get the two elements you want to compare,
        one from current row and one from the next: */
        x = rows[i].getElementsByTagName("TD")[n];
        y = rows[i + 1].getElementsByTagName("TD")[n];
        /* Check if the two rows should switch place,
        based on the direction, asc or desc: */
        if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
            // If so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
        }
        } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
            // If so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
        }
        }
    }
    if (shouldSwitch) {
        /* If a switch has been marked, make the switch
        and mark that a switch has been done: */
        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
        switching = true;
        // Each time a switch is done, increase this count by 1:
        switchcount ++;
    } else {
        /* If no switching has been done AND the direction is "asc",
        set the direction to "desc" and run the while loop again. */
        if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
        }
    }
    }
}

function spinner() {
    document.getElementById("Submit").style.display = "none";
    document.getElementsByClassName("loader")[0].style.display = "block";
}