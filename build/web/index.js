$(document).ready(function() {
    //$('#intro_main').hide();
    $('#stats_container').hide();
    $('#create_new_population_content').hide();
    $('#buttons_div').hide();
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
        $('#buttons_div').show();
        $('#next_button').hide();
        $('#export_button').hide();
        $('#start_over_button').show();
    });
    $('#example_population_button').click(function(event) {
        event.preventDefault();
        console.log('loading example population...');
        $('#start_over_div').show();
        init_stats(true);
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
        init_stats(false);
    });
    $('#add_characteristic_button').click(function(event) {
        event.preventDefault();
        add_to_characteristic_list();
    });
    
    $('#add_specimen_button').click(function(event) {
       event.preventDefault();
       add_specimen();
    });
    $('#start_over_button').click(function(event) {
        event.preventDefault();
       window.location.reload(); 
    });
    
    $('#next_button').click( function() {
        $.ajax({
            type: 'POST',
            url: 'webresources/api',
            data: JSON.stringify(window.current_population),
            success: function(data) { console.log(data); draw_stats(data); },
            contentType: "application/json",
            dataType: 'json'
        });
    });
};

function exportJson(el) {
    var json = "text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(window.current_population));
    el.setAttribute("href", "data:" + json);
    el.setAttribute("download", "data.json");    
}

var fill_characteristics_list = function() {
    console.log('Trying to fill characteristics list for specimen...');
    if (typeof window.characteristic_list_details === 'undefined') {
        // no user defined characteristics
        $('#user_characteristics_for_specimen').hide();
    } else {
        var index;
        var list = window.characteristic_list_details;
        for (index = 0; index < list.length; ++index) {
            var name = list[index].name;
            $('#user_characteristics_list').append(
                    '<tr><td>' +
                    '<h5>Name: ' + name + '</h5>' +
                    '<div class="btn-group" data-toggle="buttons">' +
                        '<label class="btn btn-primary active">' +
                            '<input type="radio" name="options'+ index +'" id="'+ index +'_stronglyDom" checked>Strongly dom.</input>' +
                        '</label>' +
                        '<label class="btn btn-primary">' +
                            '<input type="radio" name="options'+ index +'" id="'+ index +'_weaklyDom">Weakly dom.</input>' +
                        '</label>' +
                        '<label class="btn btn-primary">' +
                            '<input type="radio" name="options'+ index +'" id="'+ index +'_Rec">Recessive</input>' +
                        '</label>' +
                    '</div>' +
                    '</td></tr>'
                    );
            
        }
        console.log($('#user_characteristics_list'));
    }
};

var add_to_characteristic_list = function() {
    // $('#characteristic_name').value didn't work for some reason... investigate
    var name = document.getElementById('characteristic_name').value;
    var dominant_name = document.getElementById('dominant_name').value;
    var recessive_name = document.getElementById('recessive_name').value;
    

    list_item = '<tr><td>' + name + '</td><td>' + dominant_name + '</td><td>' + recessive_name + '</td></tr>';
    list = $('#characteristic_list');
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
    // Append object to global list 
    window.characteristic_list_details.push(some_js_object);
};

var add_specimen = function() {
    if (typeof window.number_of_specimen === 'undefined') {
        window.number_of_specimen = 0;
    }
    var characteristics_list = [];
    if (typeof window.characteristic_list_details === 'undefined') {
        // no user defined characteristics
    } else {
        var index;
        var list = window.characteristic_list_details;
        for (index = 0; index < list.length; ++index) {
            var name = list[index].name;
            var strongly_dom = document.getElementById(index + '_stronglyDom').checked;
            var weakly_dom = document.getElementById(index + '_weaklyDom').checked;
            var recessive = document.getElementById(index + '_Rec').checked;
            characteristic = {
                id: index,
                name: name,
                stronglyDominant: strongly_dom,
                weaklyDominant: weakly_dom,
                recessive: recessive
            };
            characteristics_list.push(characteristic);
        }
    }
    
    specimen_object = {
        id: number_of_specimen,
        is_male: document.getElementById('male_op').checked,
        characteristics: characteristics_list
    };
    console.log(specimen_object);
    
    if (typeof window.specimen_list === 'undefined') {
        window.specimen_list = [];
    }
    window.specimen_list.push(specimen_object);
    ++window.number_of_specimen;
    var snd = document.getElementById('specimen_number_div');
    snd.innerHTML = "<h5>Number of specimen: " + window.number_of_specimen + "</h5>";
};

var init_stats = function(is_example) {
    // Hide other sections
    $('#intro_main').hide();
    $('#create_new_population_content').hide();
    $('#buttons_div').show();
    $('#next_button').show();
    $('#export_button').show();
    $('#start_over_button').show();

    if (is_example) {
        var baseURL = "webresources/api";
        $.getJSON(baseURL, draw_stats);
    } else {
        alert("get data");
    }
    $('#stats_container').show();
};

var testGET = function() {
    $.get("webresources/api", function( data ) {
        console.log("test GET success");
        console.log(data);
    });
};

var testPOST = function() {
    $.ajax({
        type: 'POST',
        url: 'webresources/api',
        data: formToJSON(),
        success: function(data) { console.log(data); },
        contentType: "application/json",
        dataType: 'json'
    });
};

var formToJSON = function () {
     return JSON.stringify({
                    "name": "IDK"
                    }); 
};

var draw_stats = function(obj) {
    window.current_population = obj;
    console.log(obj.name);
    $('#title')[0].innerHTML = "Population name: " + obj.name;
    $('#specimen_alive')[0].innerHTML = "Specimen alive: " + (obj.specimenNumberToDate - obj.specimenDead);
    $('#specimen_dead')[0].innerHTML = "Secimen dead: " + obj.specimenDead;
    $('#specimen_total')[0].innerHTML = "Specimen total: " + obj.specimenNumberToDate;
    draw_table(obj);
    draw_gender_pie_chart(obj.males.length, obj.females.length);
    draw_line_chart(obj.males.length, obj.females.length);
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
    var headings = ['gender', 'age', 'life expectancy'];
    var specimenNb = 0;
    var sumAge = 0;

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
        sumAge += specimen.age;
        ++specimenNb;
    };

    for (i = 0; i < obj.males.length; i++) {
        add_specimen_to_table(obj.males[i]);
    }

    for (i = 0; i < obj.females.length; i++) {
        add_specimen_to_table(obj.females[i]);
    }
    $('#average_age')[0].innerHTML = "Average age: " + Number((sumAge/specimenNb).toFixed(1));
    myTableDiv.appendChild(table);
};

var draw_line_chart = function(males, females) {
    if (typeof window.labels_list === 'undefined') {
        window.labels_list = [];
        window.labels_list.push(window.current_population.ageCycles);
    }
    window.labels_list.push(window.current_population.ageCycles);
    if (typeof window.male_history === 'undefined') {
        window.male_history = [];
        window.male_history.push(males);
    }
    window.male_history.push(males);
    if (typeof window.female_history === 'undefined') {
        window.female_history = [];
        window.female_history.push(females);
    }
    window.female_history.push(females);
    var data = {
        labels: window.labels_list,
        datasets: [
            {
                label: "Females",
                fillColor: "rgba(220,100,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: window.female_history
            },
            {
                label: "Males",
                fillColor: "rgba(100,200,100,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(80,90,100,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: window.male_history
            }
        ]
    };
    
    var ctx = $("#line_chart").get(0).getContext("2d");
    ctx.clearRect(0,0,400,400);
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
    ctx.clearRect(0, 0, 400, 400);
    ctx.beginPath();
    new Chart(ctx).Doughnut(data, chart_options);
    $('#males_number')[0].innerHTML = "Males: " + males;
    $('#females_number')[0].innerHTML = "Females: " + females;
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
