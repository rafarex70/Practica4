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
    var query = 'mutation { createEolicPlant(city:"'+document.getElementById("city").value+'") { id, city, planning } }';
    $.ajax({
        method: 'post',
        url: "http://localhost:3000/graphql",
        data: JSON.stringify({"query": query}),
        contentType: 'application/json',
        success: function(response){
            console.log(response);
            document.getElementById("progress").innerText = "Send 0%";
        },
        error: function(d){
            console.log("Error: "+d.error);
            alert("Error: "+d.error);
        }
    });
}
