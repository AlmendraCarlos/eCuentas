<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" 	  prefix="spring"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" 					  prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" 					  prefix="fmt"%>


<script type="text/javascript">

	$('#materiaPrimaSelect').on('change', function() {
		if ( this.value != -1 ){
			$.ajax({
		        url: 'getTipoMedida.htm',
		        type: "get",
		        data : {
		            ID : this.value
		        },
		        success: function (retorno) {
		        	$("#cantidad").removeAttr( "disabled" );
		            $("#tipoMedidaSpan").html(retorno);
		        },
		        error: function () {
		            alert("Se ha producido un error");
		        }
		    });
		}else{
			$("#tipoMedidaSpan").html('');
			$("#cantidad").attr( "disabled","disabled" );
		}		
	});
	
	function crearConfirmar(url) {

	    var formulario = $('#formulario').serialize();

	    $.ajax({
	        url: url,
	        data: formulario,
	        type: "post",
	        cache: false,
	        success: function (retorno) {
	        	
	        	$("#contenido_modal").html(retorno);
	            
	        },
	        error: function () {
	            alert("Se ha producido un error");
	        }
	    });

	    return false;
	}
	
	function quitarConfirmar(url,id) {

	    $.ajax({
	    	url: url,
	        type: "get",
	        data : {
	            ID : id
	        },
	        success: function (retorno) {
	        	
	        	$("#contenido_modal").html(retorno);
	            
	        },
	        error: function () {
	            alert("Se ha producido un error");
	        }
	    });

	    return false;
	}
	
</script>

<div class="modal-header">

	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	
	<h4 class="modal-title">Ingredientes</h4>
	
</div>

<div class="modal-body">

	<spring:hasBindErrors name="ingredientes">
		<div class="form-group">
			<div id="hasErrors" class="alert alert-danger">
				<c:forEach items="${errors.allErrors}" var="error">
			        <strong><c:out value="${error.field}"/> : </strong><spring:message message="${error}"/></br>
			    </c:forEach>
			</div>
		</div>
	</spring:hasBindErrors>

	<form id="formulario" name="formulario" class="form-horizontal">
	
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		
		<input type="hidden" name="producto.id" id="producto" value="${model.producto.id}">
	
		<div class="form-group">
			<div class="col-xs-6">
				<select class="form-control" id="materiaPrimaSelect" name="materiaPrima.id">
					<option value="-1" >Seleccione Materia Prima</option>
					<c:forEach var="materiaPrima" items="${model.materiaPrimaList}" >
								<option value="${materiaPrima.id}"><c:out value="${materiaPrima.descripcion}"/></option>						
					</c:forEach>
				</select>
			</div>
		</div>
			
		<div class="form-group">
			<div class="col-xs-4">
				<div class="input-group">
					   <input name="cantidad" id="cantidad" type="text" class="form-control" placeholder="Cantidad" value="" disabled/>
				   	   <span id="tipoMedidaSpan" class="input-group-addon"></span>
				</div>
			</div>
		</div>
		 
		<div class="form-group">
			<div class="col-xs-4">
				<button class="btn btn-primary" type="button" onclick="return crearConfirmar('addIngrediente.htm');"  data-toggle="modal">Agregar</button>
			</div>
		</div>
		
	</form>

	<c:if test="${not empty model.ingredientes}">
		
		<c:set var="total" value="0" />
	
		<table class="table table-bordered">
			<tr>
			  <th class="active">Nombre</th>
			  <th class="active">Cantidad</th>
			  <th class="active">Precio ($)</th>
			  <th class="active"></th>
			</tr>
			
			<c:forEach var="ingrediente" items="${model.ingredientes}" >
				<tr>
				  <td><c:out value="${ingrediente.materiaPrima.descripcion}"/></td>
				  <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${ingrediente.cantidad}" /> <c:out value="${ingrediente.materiaPrima.tipoMedida.abreviatura}"/></td>
				  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${( ingrediente.materiaPrima.precio * ingrediente.cantidad ) / ingrediente.materiaPrima.medida}"/></td>
				  <td style="width:52px; text-align: center;">
		            <a class="btn btn-small" onclick="return quitarConfirmar('removeingrediente.htm',${ingrediente.id});" ><i class="glyphicon glyphicon-remove"></i></a>
		          </td>
				</tr>
				<c:set var="total" value="${(( ingrediente.materiaPrima.precio * ingrediente.cantidad ) / ingrediente.materiaPrima.medida) + total}" />
			</c:forEach>
			<tr>
				  <td colspan="2"><strong>TOTAL</strong></td>
				  <td colspan="2"><strong><fmt:formatNumber type="number" maxFractionDigits="2" value="${total}"/></strong></td>
				</tr>
		</table>
	</c:if>

</div>
<div class="modal-footer">
	<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cerrar</button>
</div>