<script type="text/javascript">

$(document).ready(function () {
	
    $('ul.nav > li').removeClass('active');
    $('#menuHome').addClass('active');
 
    
    $.ajax({
        url: 'getGraficoVentasXMes.htm',
        type: "get",
        data : {
            ID : this.value
        },
        success: function (grafico) {
        	
            Array.prototype.mapProperty = function(property) {
             	return this.map(function (obj) {
            		return obj[property];
             	});
             };
        	
        	var barChartData = {
            		labels : grafico.mapProperty('fecha'),
            		datasets : [
            			{
            				fillColor : "rgba(220,220,220,0.5)",
            				strokeColor : "rgba(220,220,220,1)",
            				data : grafico.mapProperty('compra')
            			},
            			{
            				fillColor : "rgba(151,187,205,0.5)",
            				strokeColor : "rgba(151,187,205,1)",
            				data : grafico.mapProperty('venta')
            			},
            			{
            				fillColor : "rgba(100, 200, 100, 0.5)",
            				strokeColor : "rgba(100, 200, 100, 1)",
            				data : grafico.mapProperty('ganancia')
            			}
            		]
            	}
          	
          	
            var myLine = new Chart(document.getElementById("myChart").getContext("2d")).Bar(barChartData);
        	
        },
        error: function () {
            alert("Se ha producido un error");
        }
    });
    
    
});

</script>


<script src="resources/ChartJS/Chart.js"></script>


<center><canvas id="myChart" width="1000" height="400"></canvas></center>

<br/>
<br/>
<br/>