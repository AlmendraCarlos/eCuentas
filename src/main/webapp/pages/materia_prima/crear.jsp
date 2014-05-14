<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" 	  prefix="spring"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" 					  prefix="c"%>

<script type="text/javascript">

function crearConfirmar(url) {

    var formulario = $('#materiaPrima').serialize();

    $.ajax({
        url: url,
        data: formulario,
        type: "post",
        cache: false,
        success: function (retorno) {
            
        	$("#contenido_modal").html(retorno);
            
        	if ($("#hasErrors").length <= 0){
        		 //No Hay errores - div hasErrors no existe
        		 location.href = "materiaPrimaList.htm";        		 
        	}
        },
        error: function () {
            alert("Se ha producido un error");
        }
    });

    return false;
}

</script>

<form id="materiaPrima"  name="materiaPrima" class="form-horizontal" method="post" action="saveMateriaPrima.htm">

<div class="modal-header">

	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	
	<h4 class="modal-title">Materia Prima</h4>
	
</div>

<div class="modal-body">

		
		<spring:hasBindErrors name="materiaPrima">
			<div class="form-group">
				<div id="hasErrors" class="alert alert-danger">
					<c:forEach items="${errors.allErrors}" var="error">
				        <strong><c:out value="${error.field}"/> : </strong><spring:message message="${error}"/><br/>
				    </c:forEach>
				</div>
			</div>
		</spring:hasBindErrors>

		<input type="hidden" name="id" value="${model.materiaPrima.id}" />
		
        <div class="form-group">
		   <input name="descripcion" type="text" class="form-control" placeholder="Descripci&oacute;n" value="${model.materiaPrima.descripcion}"/>
		</div>
		
		<div class="form-group">
			<label for="tipoMedidaSelect">Tipo de medida y tamaño de la materia prima</label>
			<select class="form-control" id="tipoMedidaSelect" name="tipoMedida.id" >
				<option value="-1" >Seleccione una opci&oacute;n</option>
				<c:forEach var="tipoMedida" items="${model.tiposMedida}" >
					<c:choose> 
						<c:when test="${tipoMedida.id eq model.materiaPrima.tipoMedida.id}"> 
							<option value="${tipoMedida.id}" selected="selected"><c:out value="${tipoMedida.descripcion}"/></option>
						</c:when> 
						<c:otherwise>
							<option value="${tipoMedida.id}"><c:out value="${tipoMedida.descripcion}"/></option>
						</c:otherwise>						
					</c:choose> 
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group">
		   <input name="medida" type="text" class="form-control" placeholder="Tamaño" value="${model.materiaPrima.medida}"/>
		   <span class="help-block">Medida maxima por unidad, si la compra es por pack ingrese la medida total.</span>
		</div>	
		
		<div class="form-group">
		   <label for="medidaMinimaStock">Stock</label>
		   <input name="medidaMinimaStock" id="medidaMinimaStock" type="text" class="form-control" placeholder="Cantidad Minima en Stock" value="${model.materiaPrima.medidaMinimaStock}"/>
		   <span class="help-block">Cantidad minima que debe tener en stock, de acuerdo a la medida ingresada previamente.</span>
		</div>	
		
		<div class="form-group">
		   <input name="medidaDisponibleStock" type="text" class="form-control" placeholder="Cantidad Disponible en Stock" value="${model.materiaPrima.medidaDisponibleStock}"/>
		   <span class="help-block">Cantidad disponible en stock en este momento.</span>
		</div>
		
		<div class="form-group">
		   <label for="precio">Precio</label>
		   <input name="precio" id="precio" type="text" class="form-control" placeholder="Precio" value="${model.materiaPrima.precio}"/>
		   <span class="help-block">Ingrese el précio por unidad o por pack segun la medida anteriormente ingresada.</span>
		</div>
		
		<div class="radio">
		  <label>
		    <input type="radio" name="tipoMateriaPrima.id" id="optionsRadios1" value="1" checked>
		    Es un Ingrediente (Se descuenta del stock por unidad y medida del producto vendido)
		  </label>
		</div>
		<div class="radio">
		  <label>
		    <input type="radio" name="tipoMateriaPrima.id" id="optionsRadios2" value="2">
		    Es otra materia prima (solo se descuenta del stock por unidad de producto vendido)
		  </label>
		</div>

</div>
<div class="modal-footer">
    <button class="btn btn-primary" type="button"  onclick="return crearConfirmar('saveMateriaPrima.htm');"  data-toggle="modal">Guardar</button>
	<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancelar</button>
</div>

</form>