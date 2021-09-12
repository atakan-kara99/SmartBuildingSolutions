//calculate diffrent datasets.
//not to confuse with another diffrent data variable
function createData() {

  var data = [];
  var ok = 0;
  n = listOfStatus.length;

  //counting the frequency of stati
  for (i = 0; i < n; i++) {
    if (listOfStatus[i] == "OK") {
      ok++;
    }
  }

  //push to the data
  data.push(ok);
  data.push(n - ok);

  var datasets = [{
    label: "Fortschritt",
    data,
    backgroundColor: [
      "#56d798",
      "#ff8397"
    ],
    borderWidth: 1
  }];

  return {
    labels: ["OK", "andere"],
    datasets
  };
}

//calculate the progress
const data = createData();

//Auf den canvas referenzieren
var ctx = document.getElementById("diagram").getContext('2d');

var myChart =
  new Chart(ctx,
            { type: "doughnut",
              //calculated beforehand
              data,
              options: {
                responsive: true,
                maintainAspectRatio: false,
                legend: { position: 'bottom'},
              }
            });
