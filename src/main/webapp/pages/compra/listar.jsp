<%@ taglib uri="/WEB-INF/tlds/c.tld"   prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>

<script type="text/javascript">
$(document).ready(function () {
    $('ul.nav > li').removeClass('active');
    $('#menuCompra').addClass('active');
});
</script>

<script type="text/javascript">

$(document).ready(function(){
	$("#materiaPrimaSelect").on('change', function() {
		if ( this.value != -1 ){
			$.ajax({
		        url: 'getMateriaPrima.htm',
		        type: "get",
		        data : {
		            ID : this.value
		        },
		        success: function (materiaPrima) {
		        	$("#cantidad").removeAttr( "disabled" );
		        	$("#precio").removeAttr( "disabled" );
		        	$("#unidades").removeAttr( "disabled" );
		            $("#tipoMedidaSpan").html(materiaPrima.tipoMedida.abreviatura);
		            $("#cantidad").val(materiaPrima.medida);
		            $("#precio").val(materiaPrima.precio.toFixed(2));
		            $("#precioMateriaPrima").val(materiaPrima.precio.toFixed(2));
		            $("#unidades").val(materiaPrima.unidades);
		        },
		        error: function () {
		            alert("Se ha producido un error");
		        }
		    });
		}else{
			$("#unidades").val('');
			$("#tipoMedidaSpan").html('');
			$("#precio").val('');
			$("#cantidad").val('');
			$("#cantidad").attr( "disabled","disabled" );
			$("#precio").attr( "disabled","disabled" );
			$("#unidades").attr( "disabled","disabled" );
		}
		
		$(".alert").hide();
		$( ".alert #message" ).empty();
		
	});
	
	$("#unidades").keyup(function(evt) {
		
		if ( this.value != '' ){
			if ( evt.which != 190 ){
				$.ajax({
			        url: 'getPrecioXUnidad.htm',
			        type: "get",
			        data : {
			        	unidades : this.value,
			            materiaPrimaID : $("#materiaPrimaSelect").val()
			        },
			        success: function (materiaPrima) {
			            $("#cantidad").val(materiaPrima.medida);
			            $("#precio").val(materiaPrima.precio.toFixed(2));
			        },
			        error: function () {
			            alert("Se ha producido un error");
			        }
			    });
			}
		}else{
			$("#precio").val('');
			$("#cantidad").val('');
		}		
	});
	
	$("#cantidad").keyup(function(evt) {
		
		if ( this.value != '' ){		
			if ( evt.which != 190 ){
				$.ajax({
			        url: 'getPrecioXMedida.htm',
			        type: "get",
			        data : {
			        	medida : this.value,
			            materiaPrimaID : $("#materiaPrimaSelect").val()
			        },
			        success: function (materiaPrima) {
			            $("#unidades").val(materiaPrima.unidades);
			            $("#precio").val(materiaPrima.precio.toFixed(2));
			        },
			        error: function () {
			            alert("Se ha producido un error");
			        }
			    });
			}
		}else{
			$("#precio").val('');
			$("#unidades").val('');
		}		
	});
	
	$("#precio").on('change', function() {
		
		precioAnterior = $("#precioMateriaPrima").val();
		
		$.ajax({
	        url: 'getMateriaPrimaActualizada.htm',
	        type: "get",
	        data : {
	        	precio : this.value,
	        	medida : $("#cantidad").val(),
	            materiaPrimaID : $("#materiaPrimaSelect").val(),
	            actualizar: "NO"
	        },
	        success: function (materiaPrima) {
	        	
		        	$( ".alert #message" ).append( "El precio de la Materia Prima: <strong>" + materiaPrima.descripcion + "</strong>, va a modificarse de $" + precioAnterior + " a <strong> $" + materiaPrima.precio.toFixed(2) + "</strong>." );
		    		$(".alert").show();
		    		
		    		$("#btnGuardar").attr( "disabled","disabled" );
		    		$("#cantidad").attr( "disabled","disabled" );
		    		$("#precio").attr( "disabled","disabled" );
		    		$("#unidades").attr( "disabled","disabled" );  
		    		
		    		$("#btnAceptarActualizar").focus();
	            
	        },
	        error: function () {
	            alert("Se ha producido un error");
	        }
	    });
	});
	
});

function cancelarActualizarStock(){
	
	$(".alert").hide();
	$( ".alert #message" ).empty();
	
	$("#btnGuardar").removeAttr( "disabled" );
	$("#cantidad").removeAttr( "disabled" );
	$("#precio").removeAttr( "disabled" );
	$("#unidades").removeAttr( "disabled" );
	
	document.getElementById("btnGuardar").focus();
}

function aceptarActualizarStock(){
	
	$.ajax({
        url: 'getMateriaPrimaActualizada.htm',
        type: "get",
        data : {
        	precio :	$("#precio").val(),
        	medida : 	$("#cantidad").val(),
            materiaPrimaID : $("#materiaPrimaSelect").val(),
            actualizar: "YES"
        },
        success: function (materiaPrima) {
        	
        	$(".alert").hide();
        	$( ".alert #message" ).empty();
        	
        	$("#btnGuardar").removeAttr( "disabled" );
        	$("#cantidad").removeAttr( "disabled" );
        	$("#precio").removeAttr( "disabled" );
        	$("#unidades").removeAttr( "disabled" );
        	
        	document.getElementById("btnGuardar").focus();
            
        },
        error: function () {
            alert("Se ha producido un error");
        }
    });
	
	
}

</script>

<div class="alert alert-warning" style="display:none;">
  <h4>Atenci&oacute;n!</h4>
  <p id="message"></p>
  <p>
	<button type="button" class="btn btn-danger" id="btnAceptarActualizar" onclick="aceptarActualizarStock()">Aceptar</button> 
	<button type="button" class="btn btn-default" onclick="cancelarActualizarStock();">Cancelar</button>
  </p>
</div>

<input type="hidden" id="precioMateriaPrima" value="" />

<form id="formulario" name="formulario" class="form-inline" style="padding:0 10px 10px;" method="post" action="saveCompra.htm">
  
  <div class="form-group">
	<select class="form-control" id="materiaPrimaSelect" name="materiaPrima.id">
		<option value="-1" >Seleccione una Materia Prima</option>
		<c:forEach var="materiaPrima" items="${model.materiaPrimaList}" >
			<option value="${materiaPrima.id}"><c:out value="${materiaPrima.descripcion}"/></option>
		</c:forEach>
	</select>
  </div>
  
  <div class="form-group">
	    <label class="sr-only" for="unidades">Cantidad</label>
	    <input type="text" class="form-control" id="unidades" name="unidades" placeholder="Cantidad" disabled>
  </div>
  
  <div class="form-group">
  	<div class="input-group">
	    <label class="sr-only" for="cantidad">Medida</label>
	    <input type="text" class="form-control" id="cantidad" name="cantidad" placeholder="Medida" disabled>
	    <span id="tipoMedidaSpan" class="input-group-addon"></span>
    </div>
  </div>
  
  <div class="form-group">
	  <div class="input-group">
	    <label class="sr-only" for="precio">Pr&eacute;cio</label>
	    <input type="text" class="form-control" id="precio" name="precio" placeholder="Precio" disabled>
	    <span id="precioSpan" class="input-group-addon">$</span>
	  </div>
  </div>
  
  <button type="submit" id="btnGuardar" class="btn btn-primary">Guardar</button>
  
</form>

<table class="table table-bordered">
	<tr>
	  <th class="active">ID</th>
	  <th class="active">Fecha</th>
	  <th class="active">Materia Prima</th>
	  <th class="active">Cantidad</th>
	  <th class="active">Precio ($)</th>
	  <th class="active"></th>
	</tr>
	
	<c:forEach var="compra" items="${model.compras}" >
		<tr>
		  <td><c:out value="${compra.id}"/></td>
		  <td><fmt:formatDate value="${compra.fecha}" type="both" pattern="dd/MM/yyyy"/></td>
		  <td><c:out value="${compra.materiaPrima.descripcion}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${compra.cantidad}"/> <c:out value="${compra.materiaPrima.tipoMedida.abreviatura}"/></td>
		  <td><c:out value="${compra.precio}"/></td>
		  <td style="width:63px; text-align: center;">
            <a class="btn btn-small" href="removeCompra.htm?ID=${compra.id}" ><i class="glyphicon glyphicon-remove"></i></a>
          </td>
		</tr>
	</c:forEach>
	
</table>