console.log("Hello");

//variables for the overview diagram
var sizeOfAllStatus = listOfListOfStatus.length;
//counter of how many okay for overview
var okOverview = 0;

var projects = [];

//calculate diffrent datasets.
//not to confuse with another diffrent data variable
function createData() {

  //counting the frequency of stati
  for (i = 0; i < listOfListOfStatus.length; i++) {

    var ok = 0;
    var n  = listOfListOfStatus[i].length;

    for(j = 0; j < n; j++)
      if (listOfListOfStatus[i][j] == "OK") {
        ok++;
        okOverview++;
      }

      //create multiple charts for the diffrent projects

      var datasets = [
        {
        label: "OK",
        data: [ok],
        backgroundColor: [
          "#56d798"
        ],
        borderWidth: 1
      },
      {
        label: "Andere",
        data: [n-ok],
        backgroundColor: [
          "#ff8397"
        ],
        borderWidth: 1
      }
      ];

      //create Chart Object
      var ctx = document.getElementById("pro"+i).getContext('2d');

      var project =
        new Chart(ctx,
                  { type: "bar",
                    //calculated beforehand
                    data: {
                      labels: ["Fortschritt"],
                      datasets
                    },
                    options: {
                      plugins:{
                        legend:{
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
                  }
                );

        projects.push(project);
  }
}

createData();

//Auf den canvas referenzieren
var overview = document.getElementById("diagram").getContext('2d');

var overviewChart =
  new Chart(overview,
            { type: "doughnut",
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
                responsive: true,
                maintainAspectRatio: false,
                legend: { position: 'bottom'},
              }
            });
