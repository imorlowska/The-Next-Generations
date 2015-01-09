$(document).ready(function() {
    //$('#intro_main').hide();
    $('#stats_container').hide();
    $('#create_new_population_content').hide();
    init_intro();
});

var init_intro = function() {
    $('#new_population_button').click(function() {
        // Hide intro page
        $('#intro_main').hide();
        // Show 'new population' page
        $('#create_new_population_content').show();
        $('#create_1').show();
        $('#create_2').hide();
        $('#create_3').hide();
    });
    $('#example_population_button').click(function() {
        event.preventDefault();
        console.log('loading example population...');
        init_stats();
    });
    $('#population_button_1').click(function(event) {
        event.preventDefault();
        // TODO validate and process input
        //$('#create_1').hide();
        $('#create_2').show();
        //$('#create_3').hide();
        document.getElementById("population_button_1").disabled = true;
    });
    $('#population_button_2').click(function(event) {
        event.preventDefault();
        // TODO validate and process input
        //$('#create_1').hide();
        //$('#create_2').show();
        $('#create_3').show();
        document.getElementById("add_characteristic_button").disabled = true;
        fill_characteristics_list();
        document.getElementById("population_button_2").disabled = true;
    });
    $('#population_button_3').click(function(event) {
        event.preventDefault();
        // TODO validate and process input
        //$('#create_1').hide();
        //$('#create_2').hide();
        alert('do something with stats now...');
        init_stats();
    });
    $('#add_characteristic_button').click(function(event) {
        event.preventDefault();
        add_to_characteristic_list();
    });
};

var fill_characteristics_list = function() {
    console.log('Trying to fill characteristics list for specimen...');
}
var add_to_characteristic_list = function() {
    // $('#characteristic_name').value didn't work for some reason... investigate
    var name = document.getElementById('characteristic_name').value;
    var dominant_name = document.getElementById('dominant_name').value;
    var recessive_name = document.getElementById('recessive_name').value;
    
    console.log('adding: ' + name + ', ' + dominant_name + ', ' + recessive_name);

    list_item = '<tr><td>' + name + '</td><td>' + dominant_name + '</td><td>' + recessive_name + '</td></tr>';
    list = $('#characteristic_list');
    console.log(list_item);
    list.append(list_item);
    // Copy the form data into a global variable
    
    // Create global variable if doesn't exist
    // TODO test if this works
    if (typeof window.characteristic_list_details === 'undefined') {
        window.characteristic_list_details = [];
    }
    // FIXME real JS list append
    // Create JS object to hold all data
    some_js_object = {
        name: name,
        dom_name: dominant_name,
        rec_name: recessive_name
    };
    console.log(some_js_object);
    // Append object to global list 
    window.characteristic_list_details.push(some_js_object);
    console.log(window.characteristic_list_details);
};

var init_stats = function() {
    // Hide other sections
    $('#intro_main').hide();
    $('#create_new_population_content').hide();

    // Init controls
    init_controls();
   
    var baseURL = "webresources/api";
    $.getJSON(baseURL, draw_stats);
    $('#stats_container').show();
};

var init_controls = function() {
    $('#pause_button').hide();
   
    $('#play_button').click(function(e) {
        $('#play_button').hide();
        $('#pause_button').show();    
    });
    
    $('#pause_button').click(function(e) {
        $('#pause_button').hide();
        $('#play_button').show();
    });
    
    $('#next_button').click(function(e) {
        $('#pause_button').hide();
        $('#play_button').show();
    });
};

var draw_stats = function(obj) {
    console.log(obj.name);
    $('#title')[0].innerHTML = "Population name: " + obj.name;
    draw_table(obj);
    draw_gender_pie_chart(obj.males.length, obj.females.length);
    draw_line_chart([2,3,4,5,3,6,7], [4,2,4,3,2,1,5]);
};

var draw_table = function(obj) {
    var myTableDiv = document.getElementById("tab");
    if (myTableDiv.children.length > 0) {
        myTableDiv.removeChild(myTableDiv.firstChild);
    }
    var table = document.createElement('TABLE');
    table.setAttribute('class', 'table table-striped table-hover');
    table.class = 'table';
    var tableBody = document.createElement('TBODY');
    table.border = '1';
    table.appendChild(tableBody);
    var headings = ['id', 'alive', 'gender', 'age', 'life expectancy'];

    var tr = document.createElement('TR');
    tableBody.appendChild(tr);
    for (i = 0; i < headings.length; i++) {
        var th = document.createElement('TH');
        th.width = '75';
        th.appendChild(document.createTextNode(headings[i]));
        tr.appendChild(th);
    }
    
    var add_specimen_to_table = function(specimen, specimenID) {
        var tr = document.createElement('TR');
        // Specimen ID
        var td = document.createElement('TD');
        td.appendChild(document.createTextNode(specimenID));
        tr.appendChild(td);
        // Alive?
        var td = document.createElement('TD');
        td.appendChild(document.createTextNode(specimen.alive));
        tr.appendChild(td);
        // Gender
        var td = document.createElement('TD');
        var gender = specimen.male ? 'male' : 'female';
        td.appendChild(document.createTextNode(gender));
        tr.appendChild(td);
        // Age
        var td = document.createElement('TD');
        td.appendChild(document.createTextNode(specimen.age));
        tr.appendChild(td);
        // Life expectancy
        var td = document.createElement('TD');
        td.appendChild(document.createTextNode(specimen.lifeExp));
        tr.appendChild(td);
 
        tableBody.appendChild(tr);
    };

    for (i = 0; i < obj.males.length; i++) {
        add_specimen_to_table(obj.males[i], i);
    }

    for (i = 0; i < obj.females.length; i++) {
        add_specimen_to_table(obj.females[i], obj.males.length + i);
    }

    myTableDiv.appendChild(table);
};

var draw_line_chart = function(values, more_values) {
    var data = {
        labels: ["January", "February", "March", "April", "May", "June", "July"],
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(220,100,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: values
            },
            {
                label: "My First dataset",
                fillColor: "rgba(100,200,100,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(80,90,100,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: more_values
            }
        ]
    };
    var ctx = $("#line_chart").get(0).getContext("2d");
    var myNewChart = new Chart(ctx).Line(data);
};

var draw_gender_pie_chart = function(males, females) {
    console.log(males + ', ' + females);
    var data = [
        {
            value: males,
            color: "#46BFBD",
            highlight: "#5AD3D1",
            label: "Male"
        },
        {
            value: females,
            color:"#F7464A",
            highlight: "#FF5A5E",
            label: "Female"
        }
    ];
    var ctx = $("#gender_chart").get(0).getContext("2d");
    new Chart(ctx).Doughnut(data, chart_options);
};

chart_options = {
    ///Boolean - Whether grid lines are shown across the chart
    scaleShowGridLines : true,
    //String - Colour of the grid lines
    scaleGridLineColor : "rgba(0,0,0,.05)",
    //Number - Width of the grid lines
    scaleGridLineWidth : 1,
    //Boolean - Whether the line is curved between points
    bezierCurve : true,
    //Number - Tension of the bezier curve between points
    bezierCurveTension : 0.4,
    //Boolean - Whether to show a dot for each point
    pointDot : true,
    //Number - Radius of each point dot in pixels
    pointDotRadius : 4,
    //Number - Pixel width of point dot stroke
    pointDotStrokeWidth : 1,
    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
    pointHitDetectionRadius : 20,
    //Boolean - Whether to show a stroke for datasets
    datasetStroke : true,
    //Number - Pixel width of dataset stroke
    datasetStrokeWidth : 2,
    //Boolean - Whether to fill the dataset with a colour
    datasetFill : true,
    //String - A legend template
    legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
};
