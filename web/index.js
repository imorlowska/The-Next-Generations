$(document).ready(function() {
    //$('#intro_container').hide();
    $('#stats_container').hide();
    $('#create_population_container').hide();
});

var init_intro = function() {
    $('#intro_main').hide();

    $('#population_nav').click = function() {
        $('#intro_main').hide();
        $('#population_nav_contents').show();
    }
};

var add_to_characteristic_list = function() {
    var name = $('#characteristic_name').value;
    // etc!

    list_item = "!!! " + name;

    list = $('#characteristic_list');
    list.appendChild(list_item);
    // Copy the form data into a global variable
    
    // Create global variable if doesn't exist
    // TODO test if this works
    if (type(window.characteristic_list_details) === 'undefined') {
        window.characteristic_list_details = [];
    }
    // FIXME real JS list append
    // Create JS object to hold all data
    some_js_object = {
        name: name
    }
    // Append object to global list 
    window.characteristic_list_details.append(some_js_object);
};

var show_stats = function() {
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
    
    var baseURL = "webresources/api";
    console.log(baseURL);
    $.getJSON(baseURL, draw);
    $('#stats_container').show();
};

var draw = function(obj) {
    console.log(obj.name);
    $('#title')[0].innerHTML = "Population name: " + obj.name;
    draw_table(obj);
    draw_gender_pie_chart(obj.males.length, obj.females.length);
    draw_line_chart([2,3,4,5,3,6,7], [4,2,4,3,2,1,5]);
};

var draw_table = function(obj) {
    var myTableDiv = document.getElementById("tab");
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
    
    var add_specimen_to_table = function(specimen) {
        var tr = document.createElement('TR');
        // Specimen ID
        var td = document.createElement('TD');
        td.appendChild(document.createTextNode(specimen.specimenID));
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
        add_specimen_to_table(obj.males[i]);
    }

    for (i = 0; i < obj.females.length; i++) {
        add_specimen_to_table(obj.females[i]);
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
    var myNewChart = new Chart(ctx).Doughnut(data, chart_options);
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
