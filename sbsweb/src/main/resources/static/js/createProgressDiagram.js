//calculate diffrent datasets.
//not to confuse with another diffrent data variable
function createData() {

  var data = [];
  var ok    = 0;
  var other = 0;
  n = listOfStatus.length;

  //counting the frequency of stati
  for (i = 0; i < n; i++) {
    if(listOfStatus[i] == "OK"){
      ok++;
    }
  }
  <!--
  <script type="text/javascript " defer th:src="@{/js/createProgrssDiagram.js} "></script>
-->
  //push to the data
  data.push(ok);
  data.push(other);

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
    ["OK","andere"],
    datasets
  };
}

//calculate the progress
const data = createData();


//erstellt ein neues Chart Objekt zu einem Objekt von list
function createNewChart(ctx) {

  return new Chart(ctx, {
    type: "doughnut",
    //calculated beforehand
    data,
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  });
}

//Auf den canvas referenzieren
var ctx = document.getElementById("diagram").getContext('2d');

var myChart = createNewChart(ctx, 0);
