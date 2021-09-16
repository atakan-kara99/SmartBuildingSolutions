//variables for the overview diagram
var sizeOfAllStatus = 0;
//counter of how many okay for overview
var okOverview = 0;
var projects = [];

//Darkmode color
var color;
var borderColorDM;
if (darkMode) {
  borderColorDM = Chart.defaults.color;
  color = "#ffffff";
} else {
  color = Chart.defaults.color;
    borderColorDM = "#ffffff";
}

//calculate diffrent datasets.
//not to confuse with another diffrent data variable
function createData() {

  //counting the frequency of stati
  for (i = 0; i < listLength; i++) {

    var ok = 0;
    var n = listOfListOfStatus[i].length;
    sizeOfAllStatus += n;

    for (j = 0; j < n; j++)
      if (listOfListOfStatus[i][j] == "OK") {
        ok++;
        okOverview++;
      }

    if (multipleCharts) {
      //update label
      document.getElementById("label" + i).innerHTML = Math.round(ok * 100 / n) + "%";

      //create multiple charts for the diffrent projects

      var datasets = [
        {
          label: "OK",
          data: [ok],
          backgroundColor: [
            "#00c384"
          ],
          borderColor: borderColorDM,
          borderWidth: 1
        },
        {
          label: "Andere",
          data: [n - ok],
          backgroundColor: [
            "#fc6d6d"
          ],
          borderColor: borderColorDM,
          borderWidth: 1
        }
      ];

      //create Chart Object
      var ctx = document.getElementById("pro" + i).getContext('2d');

      var project =
        new Chart(ctx, {
          type: "bar",
          //calculated beforehand
          data: {
            labels: ["Fortschritt"],
            datasets
          },
          options: {
            plugins: {
              legend: {
                display: false,
              }
            },
            tooltips: {
              enabled: false // hides the tooltip.
            },
            indexAxis: 'y',
            responsive: true,
            maintainAspectRatio: false,
            scales: {
              x: {
                display: false,
                stacked: true,
                grid: {
                  display: false
                },
                max: n
              },
              y: {
                display: false,
                stacked: true,
              }
            }
          }
        });

      projects.push(project);
    }
  }
}

if (listLength > 0) {
  createData();

  //Auf den canvas referenzieren
  var overview = document.getElementById("diagram").getContext('2d');

  var overviewChart =
    new Chart(overview, {
      type: "doughnut",
      //calculated beforehand
      data: {
        labels: ["Andere", "OK"],
        datasets: [{
          label: "Fortschritt",
          data: [sizeOfAllStatus - okOverview, okOverview],
          backgroundColor: [
            "#fc6d6d",
            "#00c384"
          ],
          borderColor: borderColorDM,
          borderWidth: 1
        }]
      },
      options: {
        plugins: {
          legend: {
            reverse: true,
            labels: {
              color: color
            },
            onClick:
              function (e) {
                e.stopPropagation();
              }
            }
          },
        responsive: true,
        maintainAspectRatio: false
      }
    });
}
