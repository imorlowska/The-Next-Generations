$(document).ready(function() {
    //$('#intro_main').hide();
    $('#stats_container').hide();
    $('#create_new_population_content').hide();
    $('#buttons_div').hide();
    $('#load_population_content').hide();
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
    $('#load_population_button').click(function(event){
        event.preventDefault();
        $('#intro_main').hide();
        $('#load_population_content').show();
        $('#buttons_div').show();
        $('#next_button').hide();
        $('#export_button').hide();
        $('#start_over_button').show();
    });
    $('#load_button_next').click(function(event) {
        event.preventDefault();
        var input_json = document.getElementById("pasted_population_json").value;
        var parsed = JSON.parse(input_json);
        //console.log(parsed);
        init_loaded_stats(parsed);
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
            success: function(data) { draw_stats(data); },
            contentType: "application/json",
            dataType: 'json'
        });
    });
};

function exportJson(el) {
    var json = "text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(window.current_population));
    el.setAttribute("href", "data:" + json);
    el.setAttribute("download", window.current_population.name + ".json");    
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
    
    if (typeof window.specimen_list === 'undefined') {
        window.specimen_list = [];
    }
    window.specimen_list.push(specimen_object);
    ++window.number_of_specimen;
    var snd = document.getElementById('specimen_number_div');
    snd.innerHTML = "<h5>Number of specimen: " + window.number_of_specimen + "</h5>";
};
var init_loaded_stats = function(obj) {
    $('#buttons_div').show();
    $('#next_button').show();
    $('#export_button').show();
    $('#start_over_button').show();
    $('#load_population_content').hide();
    
    draw_stats(obj);
    
    $('#stats_container').show();
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
        fillPopulationAndDrawStats();
    }
    $('#stats_container').show();
};

var fillPopulationAndDrawStats = function() {
    var life_exp_val = document.getElementById('population_form_exp').value;
    var males = [];
    var females = [];
    var index = 0;
    for (index = 0; index < window.specimen_list.length; ++index) {
        var spec = window.specimen_list[index];
        if (spec.is_male) {
            spec_obj = {
                specimenID: spec.id,
                male: true,
                age: 0,
                lifeExp:life_exp_val,
                alive: true
            };
            males.push(spec_obj);
        } else {
            spec_obj = {
                specimenID: spec.id,
                male: false,
                age: 0,
                lifeExp:life_exp_val,
                alive: true
            };
            females.push(spec_obj);
        }
    }
    population = {
        name: document.getElementById('population_form_name').value,
        specimenNumberToDate: 0,
        specimenDead: 0,
        averageLifeExp: life_exp_val,
        ageCycles:0,
        males: males,
        females: females
    };
    
    
    draw_stats(population);
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
    $('#title')[0].innerHTML = "Population name: " + obj.name;
    $('#specimen_alive')[0].innerHTML = "Specimen alive: " + (obj.males.length + obj.females.length);
    $('#specimen_dead')[0].innerHTML = "Secimen dead: " + obj.specimenDead;
    $('#specimen_total')[0].innerHTML = "Specimen total: " + (obj.males.length + obj.females.length + obj.specimenDead);
    draw_table(obj);
    draw_gender_pie_chart(obj.males.length, obj.females.length);
    draw_line_chart(obj.males.length, obj.females.length);
    fill_characteristics_stats(obj);
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
    var averageAge;
    if (specimenNb > 0) {
        averageAge = Number((sumAge/specimenNb).toFixed(1));
    } else {
        averageAge = "all have died";
    }
    $('#average_age')[0].innerHTML = "Average age: " + averageAge;
    myTableDiv.appendChild(table);
};

var draw_line_chart = function(males, females) {
    if (typeof window.labels_list === 'undefined') {
        window.labels_list = [];
        window.labels_list.push(window.current_population.ageCycles);
        ++window.current_population.ageCycles;
    }
    window.labels_list.push(window.current_population.ageCycles);
    if (typeof window.male_history === 'undefined') {
        window.male_history = [];
        window.male_history.push(0);
    }
    window.male_history.push(males);
    if (typeof window.female_history === 'undefined') {
        window.female_history = [];
        window.female_history.push(0);
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
    // If chart with old data already exists, destroy it and re-create with new data
    if (typeof window.gender_line_chart !== 'undefined') {
        window.gender_line_chart.destroy();
    }
    var ctx = $("#line_chart").get(0).getContext("2d");
    ctx.clearRect(0,0,400,400);
    ctx.beginPath();
    window.gender_line_chart = new Chart(ctx).Line(data);
};

var draw_gender_pie_chart = function(males, females) {
    if (males + females > 0) {
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
    } else {
        var data = [
            {
                value: window.current_population.specimenDead,
                color: "#E81919",
                highlight: "#FF0000",
                label: "All dead"
            }
        ];
    }
    // If chart with old data already exists, destroy it and re-create with new data
    if (typeof window.gender_pie_chart !== 'undefined') {
        window.gender_pie_chart.destroy();
    }
    var ctx = $("#gender_chart").get(0).getContext("2d");
    ctx.clearRect(0, 0, 400, 400);
    ctx.beginPath();
    window.gender_pie_chart = new Chart(ctx).Doughnut(data, chart_options);
    $('#males_number')[0].innerHTML = "Males: " + males;
    $('#females_number')[0].innerHTML = "Females: " + females;
};

var fill_characteristics_stats = function(object) {
    init_characteristic_stats_if_needed(object);
    update_characteristic_stats(object);
};

var init_characteristic_stats_if_needed = function(object) {
    if (typeof window.characteristic_stats === 'undefined') {
        window.characteristic_stats = [];
        if (object.males.length > 0) {
            if (!(typeof object.males[0].genotype === 'undefined')
                && (!(typeof object.males[0].genotype.characteristics === 'undefined'))
                && object.males[0].genotype.characteristics.length > 0) {
                var list = object.males[0].genotype.characteristics;
                var index;
                for (index = 0; index < list.length; ++index) {
                    characteristic_obj = {
                        name: list[index].name,
                        recName: list[index].recessiveName,
                        domName: list[index].dominantName,
                        recHist: [0],
                        sDomHist: [0],
                        wDomHist: [0],
                        currRec: 0,
                        currSDom: 0,
                        currWDom: 0                        
                    };
                    window.characteristic_stats.push(characteristic_obj);
                }
            } else {
                console.log("No user characteristics");
            }
        } else if (object.females.length > 0) {
            if (!(typeof object.females[0].genotype === 'undefined')
                && (!(typeof object.females[0].genotype.characteristics === 'undefined'))
                && object.females[0].genotype.characteristics.length > 0) {
                var list = object.females[0].genotype.characteristics;
                var index;
                for (index = 0; index < list.length; ++index) {
                    characteristic_obj = {
                        name: list[index].name,
                        recName: list[index].recessiveName,
                        domName: list[index].dominantName,
                        recHist: [0],
                        sDomHist: [0],
                        wDomHist: [0],
                        currRec: 0,
                        currSDom: 0,
                        currWDom: 0                        
                    };
                    window.characteristic_stats.push(characteristic_obj);
                }
            } else {
                console.log("No user characteristics");
            }
        } else {
            // no user characteristics since no specimen
            console.log("No user characteristics");
        }
        if (window.characteristic_stats.length > 0) {
            $('#user_characteristics_stats')[0].innerHTML += 
                    "<h2> User defined characteristics: </h2>";
        }
        var index;
        for (index = 0; index < window.characteristic_stats.length; ++ index) {
            var curr = window.characteristic_stats[index];
            $('#user_characteristics_stats')[0].innerHTML +=
                    "<h3>" + (index+1) + ". "+ curr.name + "</h3>" +
                    "<div class='row'>" + 
                        '<div class="col-sm-6">' +
                            '<h4>Current state</h4>' +
                            '<canvas id="'+ index +'_characteristic_chart" width="300" height="300"></canvas>' +
                            '<h5 id="'+index+'_rec_number">'+curr.recName+'(recessive): 0</h5>' +
                            '<h5 id="'+index+'_s_dom_number">'+curr.domName+'(strongly dominant): 0</h5>' +
                            '<h5 id="'+index+'_w_dom_number">'+curr.domName+'(weakly dominant): 0</h5>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                            '<h4>Characteristic History Line Chart</h4>' +
                            '<canvas id="'+index+'_char_line_chart" width="300" height="300"></canvas>' +
                        '</div>' +
                    '</div>';
        }
    }
};

var update_characteristic_stats = function(object) {
    var index;
    var list = window.characteristic_stats;
    for (index = 0; index < list.length; ++index) {
        list[index].currRec = 0;
        list[index].currSDom = 0;
        list[index].currWDom = 0;
        var index_males;
        for (index_males = 0; index_males < object.males.length; ++index_males) {
            var index_char;
            var my_man = object.males[index_males];
            for (index_char = 0; index_char < my_man.genotype.characteristics.length; ++index_char) {
                // go through characteristics to find data
                if (my_man.genotype.characteristics[index_char].name === list[index].name) {
                    if (my_man.genotype.characteristics[index_char].recessive) {
                        list[index].currRec += 1;
                        //console.log("Foud rec for char " + list[index].name);
                    } else if (my_man.genotype.characteristics[index_char].stronglyDominant) {
                        list[index].currSDom += 1;
                        //console.log("Foud s dom for char " + list[index].name);
                    } else { //weakly dominant
                        if (!my_man.genotype.characteristics[index_char].dominant) {
                            //or screwed up
                            alert("Characteristic is not rec, strong dom or weak dom. Something went wrong!");
                        } else {
                            list[index].currWDom += 1;
                            //console.log("Foud w dom for char " + list[index].name);
                        }
                    }
                }
            }
        }
        var index_females;
        for (index_females = 0; index_females < object.females.length; ++index_females) {
            var index_char;
            var my_woman = object.females[index_females];
            for (index_char = 0; index_char < my_woman.genotype.characteristics.length; ++index_char) {
                // go through characteristics to find data
                if (my_woman.genotype.characteristics[index_char].name === list[index].name) {
                    if (my_woman.genotype.characteristics[index_char].recessive) {
                        list[index].currRec += 1;
                        //console.log("Foud rec for char " + list[index].name);
                    } else if (my_woman.genotype.characteristics[index_char].stronglyDominant) {
                        list[index].currSDom += 1;
                        //console.log("Foud s dom for char " + list[index].name);
                    } else { //weakly dominant
                        if (!my_woman.genotype.characteristics[index_char].dominant) {
                            //or screwed up
                            alert("Characteristic is not rec, strong dom or weak dom. Something went wrong!");
                        } else {
                            list[index].currWDom += 1;
                            //console.log("Foud w dom for char " + list[index].name);
                        }
                    }
                }
            }
        }
        list[index].recHist.push(list[index].currRec);
        list[index].sDomHist.push(list[index].currSDom);
        list[index].wDomHist.push(list[index].currWDom);
        draw_characteristic_stats(index, list[index]);
    }
};

var draw_characteristic_stats = function(index, characteristic) {
    draw_doughnut_characteristic_stat(index, characteristic);
    draw_line_chart_characteristic_stat(index, characteristic);
};

var draw_doughnut_characteristic_stat = function(index, char) {
    if ((char.currRec + char.currSDom + char.currWDom) > 0) {
        var data = [
            {
                value: char.currRec,
                color: "#40FF00",
                highlight: "#82FA58",
                label: char.recName
            },
            {
                value: char.currSDom,
                color:"#FF4000",
                highlight: "#FF5A5E",
                label: char.domName + " (strongly dom)"
            },
            {
                value: char.currWDom,
                color:"#FFFF00",
                highlight: "#FA8258",
                label: char.domName + " (weakly dom)"
            }
        ];
    } else {
        var data = [
            {
                value: max(1, window.current_population.specimenDead),
                color: "#E81919",
                highlight: "#FF0000",
                label: "All dead"
            }
        ];
    }
    if (typeof window.characteristic_pie_chart === 'undefined') {
        window.characteristic_pie_chart = [];
    }
    // If chart with old data already exists, destroy it and re-create with new data
    if (typeof window.characteristic_pie_chart[index] !== 'undefined') {
        window.characteristic_pie_chart[index].destroy();
    }
    var ctx = $("#"+index+"_characteristic_chart").get(0).getContext("2d");
    ctx.clearRect(0, 0, 300, 300);
    ctx.beginPath();
    window.characteristic_pie_chart[index] = new Chart(ctx).Doughnut(data, chart_options);
    $('#'+index+'_rec_number')[0].innerHTML = char.recName+'(recessive): ' + char.currRec;
    $('#'+index+'_s_dom_number')[0].innerHTML = char.domName+'(strongly dominant): ' + char.currSDom;
    $('#'+index+'_w_dom_number')[0].innerHTML = char.domName+'(weakly dominant): ' + char.currWDom;
};

var max = function(val1, val2) {
    if (val1 > val2) {
        return val1;
    } else {
        return val2;
    }
};

var draw_line_chart_characteristic_stat = function(index, char) {
    var data = {
        labels: window.labels_list,
        datasets: [
            {
                label: char.recName,
                fillColor: "rgba(220,100,220,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: char.recHist
            },
            {
                label: char.domName + "(s)",
                fillColor: "rgba(100,200,100,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(80,90,100,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: char.sDomHist
            },
            {
                label: char.domName + "(w)",
                fillColor: "rgba(100,200,100,0.2)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(80,90,100,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: char.wDomHist
            }
        ]
    };
    if (typeof window.characteristic_line_chart === 'undefined') {
        window.characteristic_line_chart = [];
    }
    if (typeof window.characteristic_line_chart[index] !== 'undefined') {
        window.characteristic_line_chart[index].destroy();
    }
    var ctx = $('#'+index+'_char_line_chart').get(0).getContext("2d");
    ctx.clearRect(0,0,300,300);
    ctx.beginPath();
    window.characteristic_line_chart[index] = new Chart(ctx).Line(data);
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
