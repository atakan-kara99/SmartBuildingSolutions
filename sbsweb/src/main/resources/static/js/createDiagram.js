      //Configs for diffrent chart types
      var bar = {
        type: "bar",
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      };

      var line = {
        type: "line",
        options: {
          responsive: true,
          maintainAspectRatio: false,
          scales: {
            yAxes: [{
              beginAtZero: true
            }]
          }
        }
      };

      var radar = {
        type: "radar",
        options: {
          responsive: true,
          maintainAspectRatio: false,
          elements: {
            line: {
              borderWidth: 3
            }
          }
        }
      };

      var doughnut = {
        type: "doughnut",
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
      };

      var pie = {
        type: "pie",
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
      };

      //array of all chart type configs
      var configs = [bar, line, radar, doughnut, pie];



      //calculate diffrent datasets
      function createData() {

        var data = [];
        var counter = {};

        //init the counter values
        for (i = 0; i < labels.length; i++) {
          counter[labels[i]] = 0;
        }

        //counting the frequency of stati
        for (i = 0; i < listOfStatus.length; i++) {
          counter[listOfStatus[i]] += 1;
        }

        //push to the data
        for (i = 0; i < labels.length; i++) {
          data.push(counter[labels[i]]);
        }

        var datasets = [{
          label: name,
          data,
          backgroundColor: [
            "#f38b4a",
            "#6970d5",
            "#56d798",
            "#ff8397"
          ],
          borderWidth: 1
        }];

        return {
          labels,
          datasets
        };
      }

      const data = createData();


      //erstellt ein neues Chart Objekt zu einem Objekt von list
      function createNewChart(ctx, chartIndex) {

        return new Chart(ctx, {
          type: configs[chartIndex].type,
          //calculated beforehand
          data,
          options: configs[chartIndex].options
        });
      }

      //Auf den canvas referenzieren
      var ctx = document.getElementById("diagram").getContext('2d');

      var myChart = createNewChart(ctx, 0);


      //change chart type
      //get id from drop selectBox
      const chartType = document.getElementById('chartType');
      //eventlistener
      chartType.addEventListener('change', changeChartType);
      //function to change the type
      function changeChartType() {

        //to change the chart type, you have to delete it first, because
        //the presettings for the current diagram won't disappear, then
        //create it anew
        myChart.destroy();
        myChart = createNewChart(ctx, chartType.value);
      }
