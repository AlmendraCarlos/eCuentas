<%@ taglib uri="/WEB-INF/tlds/c.tld"   prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="resources/jQuery/AutoComplete/jquery.autocomplete.min.js"></script>
<link   rel="stylesheet" href='resources/css/main.css' type="text/css" />

<script type="text/javascript">
$(document).ready(function () {
    $('ul.nav > li').removeClass('active');
    $('#menuVenta').addClass('active');
});
</script>

<script type="text/javascript">

$(document).ready(function(){
	
	/* 	ESTO ERA PARA EL COMBOBOX AHORA USO AUTOCOMPLETADO
	 	$("#productoSelect").on('change', function() {
			if ( this.value != -1 ){
				$.ajax({
			        url: 'getProducto.htm',
			        type: "get",
			        data : {
			            ID : this.value
			        },
			        success: function (producto) {
			        	$("#cantidad").removeAttr( "disabled" );
			        	$("#precio").removeAttr( "disabled" );
			        	$("#unidades").removeAttr( "disabled" );
			            $("#tipoMedidaSpan").html(producto.tipoMedida.abreviatura);
			            $("#unidades").val(1);
			            $("#cantidad").val(producto.medida);
			            $("#precio").val(producto.precioSugerido.toFixed(2));
			            $("#costo").val(producto.precioCosto.toFixed(2));
			        },
			        error: function () {
			            alert("Se ha producido un error");
			        }
			    });
			}else{
				$("#tipoMedidaSpan").html('');
				$("#precio").val('');
				$("#cantidad").val('');
				$("#unidades").val('');
				$("#unidades").attr( "disabled","disabled" );
				$("#cantidad").attr( "disabled","disabled" );
				$("#precio").attr( "disabled","disabled" );
			}		
		}); 
		
	*/
	
	
	$("#cantidad").keyup(function(evt) {
		
		if ( this.value != '' ){		
			if ( evt.which != 190 ){
				$.ajax({
			        url: 'getPrecioXCantidad.htm',
			        type: "get",
			        data : {
			        	unidades : $("#unidades").val(),
			            cantidad : $("#cantidad").val(),
			            productoID : $("#producto").val()
			        },
			        success: function (producto) {
			            $("#cantidad").val(producto.medida);
			            $("#precio").val(producto.precioSugerido.toFixed(2));
			        },
			        error: function () {
			            alert("Se ha producido un error");
			        }
			    });
			}
		}else{
			$("#precio").val('');
		}		
	});
	
	$("#unidades").keyup(function(evt) {
		
		if ( this.value != '' ){		
			if ( evt.which != 190 ){
				$.ajax({
			        url: 'getPrecioXCantidad.htm',
			        type: "get",
			        data : {
			        	unidades : $("#unidades").val(),
			            cantidad : $("#cantidad").val(),
			            productoID : $("#producto").val()
			        },
			        success: function (producto) {
			            $("#cantidad").val(producto.medida);
			            $("#precio").val(producto.precioSugerido.toFixed(2));
			        },
			        error: function () {
			            alert("Se ha producido un error");
			        }
			    });
			}
		}else{
			$("#precio").val('');
		}		
	});
	
	
	$('#w-input-search').autocomplete({
		serviceUrl: 'getProductos.htm',
		paramName: "pName",
		delimiter: ",",
	    transformResult: function(response) {
 
			return {      	
			  //must convert json to javascript object before process
			  suggestions: $.map($.parseJSON(response), function(item) {
	 
			      return { value: item.descripcion, data: item.id };
			      
			   })
	 
			 };
        },
		onSelect: function (suggestion) {
			$.ajax({
		        url: 'getProducto.htm',
		        type: "get",
		        data : {
		            ID : suggestion.data
		        },
		        success: function (producto) {
		        	$("#cantidad").removeAttr( "disabled" );
		        	$("#precio").removeAttr( "disabled" );
		        	$("#unidades").removeAttr( "disabled" );
		        	
		        	$("#producto").val(producto.id);
		            $("#tipoMedidaSpan").html(producto.tipoMedida.abreviatura);
		            $("#unidades").val(1);
		            $("#cantidad").val(producto.medida);
		            $("#precio").val(producto.precioSugerido.toFixed(2));
		            $("#costo").val(producto.precioCosto.toFixed(2));
		        },
		        error: function () {
		            alert("Se ha producido un error");
		        }
		    });
	    }
 
	 });
	
});

</script>

<form id="formulario" name="formulario" class="form-inline" style="padding:0 10px 10px;" method="post" action="saveVenta.htm">

  <input type="hidden" name="costo" id="costo" value="${model.producto.precioCosto}">
  
  <input type="hidden" name="producto.id" id="producto" value="${model.producto.id}">

  <div class="form-group">
	    <label class="sr-only" for="w-input-search">Productos</label>
	    <input type="text" class="form-control" style="width: 200px" id="w-input-search" placeholder="Productos">
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
 
  <div class="form-group">
  	<button type="submit" class="btn btn-primary">Guardar</button>
  </div>
  
</form>

<table class="table table-bordered">
	<tr>
	  <th class="active">ID</th>
	  <th class="active">Fecha</th>
	  <th class="active">Tipo Producto</th>
	  <th class="active">Producto</th>
	  <th class="active">Cantidad</th>
	  <th class="active">Tamaño</th>
	  <th class="active">Precio ($)</th>
	  <th class="active"></th>
	</tr>
	
	<c:forEach var="venta" items="${model.ventas}" >
		<tr>
		  <td><c:out value="${venta.id}"/></td>
		  <td><fmt:formatDate value="${venta.fecha}" type="both" pattern="dd/MM/yyyy"/></td>
		  <td><c:out value="${venta.producto.tipoProducto.descripcion}"/></td>
		  <td><c:out value="${venta.producto.descripcion}"/></td>
		  <td><c:out value="${venta.unidades}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${venta.cantidad}"/> <c:out value="${venta.producto.tipoMedida.abreviatura}"/></td>
		  <td><c:out value="${venta.precio}"/></td>
		  <td style="width:63px; text-align: center;">
            <a class="btn btn-small" href="removeVenta.htm?ID=${venta.id}" ><i class="glyphicon glyphicon-remove"></i></a>
          </td>
		</tr>
	</c:forEach>
	
</table>