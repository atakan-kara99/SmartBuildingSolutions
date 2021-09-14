//variables for the overview diagram
var sizeOfAllStatus = 0;
//counter of how many okay for overview
var okOverview = 0;

var projects = [];

//calculate diffrent datasets.
//not to confuse with another diffrent data variable
function createData() {

  //counting the frequency of stati
  for (i = 0; i < listLength; i++) {
    console.log(i);

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

      var datasets = [{
          label: "OK",
          data: [ok],
          backgroundColor: [
            "#56d798"
          ],
          borderWidth: 1
        },
        {
          label: "Andere",
          data: [n - ok],
          backgroundColor: [
            "#ff8397"
          ],
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
                text: 'Legend Title'
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

  var color;

  if (darkMode) {
    color = "#ffffff";
  } else {
    color = Chart.defaults.color;
  }

  var overviewChart =
    new Chart(overview, {
      type: "doughnut",
      //calculated beforehand
      data: {
        labels: ["OK", "andere"],
        datasets: [{
          label: "Fortschritt",
          data: [okOverview, sizeOfAllStatus - okOverview],
          backgroundColor: [
            "#56d798",
            "#ff8397"
          ],
          borderWidth: 1
        }]
      },
      options: {
        plugins: {
          legend: {
            labels: {
              color: color
            }
          }
        },
        responsive: true,
        maintainAspectRatio: false,
        legend: {
          position: 'bottom'
        },
      }
    });
}
