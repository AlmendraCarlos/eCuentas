<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" 	  prefix="spring"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" 					  prefix="c"%>

<script type="text/javascript">

function crearConfirmar(url) {

    var formulario = $('#producto').serialize();

    $.ajax({
        url: url,
        data: formulario,
        type: "post",
        cache: false,
        success: function (retorno) {
            
        	$("#contenido_modal").html(retorno);
            
        	if ($("#hasErrors").length <= 0){
        		 //No Hay errores - div hasErrors no existe
        		 location.href = "productos.htm";        		 
        	}
        },
        error: function () {
            alert("Se ha producido un error");
        }
    });

    return false;
}

</script>

<form id="producto"  name="producto" class="form-horizontal" method="post" action="saveProducto.htm">

<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />

<div class="modal-header">

	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	
	<h4 class="modal-title">Nuevo Producto</h4>
	
</div>

<div class="modal-body">

		
		<spring:hasBindErrors name="producto">
			<div class="form-group">
				<div id="hasErrors" class="alert alert-danger">
					<c:forEach items="${errors.allErrors}" var="error">
				        <strong><c:out value="${error.field}"/> : </strong><spring:message message="${error}"/><br/>
				    </c:forEach>
				</div>
			</div>
		</spring:hasBindErrors>

		<input type="hidden" name="id" value="${model.producto.id}" />
		
        <div class="form-group">
		   <input name="descripcion" type="text" class="form-control" placeholder="Descripci&oacute;n" value="${model.producto.descripcion}"/>
		</div>
		
		<div class="form-group">
		   <textarea name="receta" class="form-control" rows="5" placeholder="Receta" >${model.producto.receta}</textarea>
		</div>
		
		<div class="form-group">
		   <textarea name="comentarios" class="form-control" rows="3" placeholder="Comentarios">${model.producto.comentarios}</textarea>
		</div>
		
		<div class="form-group">
			<label for="tipoMedidaSelect">Medida y Tamaño del Producto</label>
			<select class="form-control" id="tipoMedidaSelect" name="tipoMedida.id" >
				<option value="-1" >Seleccione una opci&oacute;n</option>
				<c:forEach var="tipoMedida" items="${model.tiposMedida}" >
					<c:choose> 
						<c:when test="${tipoMedida.id eq model.producto.tipoMedida.id}"> 
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
		   <input name="medida" type="text" class="form-control" placeholder="Tamaño" value="${model.producto.medida}"/>
		</div>
		
		<div class="form-group">
			<label for="tipoProductoSelect">Tipo de Producto</label>
			<select class="form-control" id="tipoProductoSelect" name="tipoProducto.id" >
				<option value="-1" >Seleccione una opci&oacute;n</option>
				<c:forEach var="tipoProducto" items="${model.tiposProducto}" >
					<c:choose> 
						<c:when test="${tipoProducto.id eq model.producto.tipoProducto.id}"> 
							<option value="${tipoProducto.id}" selected="selected"><c:out value="${tipoProducto.descripcion}"/></option>
						</c:when> 
						<c:otherwise>
							<option value="${tipoProducto.id}"><c:out value="${tipoProducto.descripcion}"/></option>
						</c:otherwise>						
					</c:choose> 
				</c:forEach>
			</select>
		</div>
		
		<div class="form-group">
		   <label for="ganancia">Ganancia</label>
		   <input name="ganancia" id="ganancia" type="text" class="form-control" placeholder="% Ganáncia" value="${model.producto.ganancia}"/>
		   <span class="help-block">Porcentaje de ganacncia que espera ganar</span>
		</div>
</div>
<div class="modal-footer">
    <button class="btn btn-primary" type="button"  onclick="return crearConfirmar('saveProducto.htm');"  data-toggle="modal">Guardar</button>
	<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">Cancelar</button>
</div>

</form>