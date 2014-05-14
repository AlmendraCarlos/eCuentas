<%@ taglib uri="/WEB-INF/tlds/c.tld"   prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt"%>

<script type="text/javascript">
$(document).ready(function () {
    $('ul.nav > li').removeClass('active');
    $('#menuAdministracion').addClass('active');
});
</script>

<script type="text/javascript">

function crear(url) {
    $.ajax({
        url: url,
        type: "get",
        cache: false,
        success: function (retorno) {
            $("#contenido_modal").html(retorno);
            $('#modal').modal('show');
        },
        error: function () {
            alert("Se ha producido un error");
        }
    });

    return false;
}


function editar(id, url) {

    $.ajax({
        url: url + id,
        type: "get",
        cache: false,
        success: function (retorno) {
            $("#contenido_modal").html(retorno);
            $('#modal').modal('show');
        },
        error: function () {
            alert("Se ha producido un error");
        }
    });

    return false;
}

</script>

<div style="padding:0 10px 10px;">
	<a onclick="return crear('addMateriaPrima.htm');" href="#" class="btn btn-primary">Nueva Materia Prima</a>
</div>

<table class="table table-bordered">
	<tr>
	  <th class="active">ID</th>
	  <th class="active">Nombre</th>
	  <th class="active">Tipo</th>
	  <th class="active">Cantidad x Presentación</th>
	  <th class="active">Precio ($)</th>
	  <th class="active">Cantidad en Stock</th>
	  <th class="active"></th>
	</tr>
	
	<c:forEach var="materiaPrima" items="${model.materiaPrimaList}" >
	
	
		<c:choose> 
			<c:when test="${materiaPrima.medidaDisponibleStock <= materiaPrima.medidaMinimaStock}"> 
				<tr class="danger">
			</c:when> 
			<c:otherwise>
				<tr>
			</c:otherwise>		
		</c:choose> 
		  <td><c:out value="${materiaPrima.id}"/></td>
		  <td><c:out value="${materiaPrima.descripcion}"/></td>
		  <td><c:out value="${materiaPrima.tipoMateriaPrima.descripcion}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${materiaPrima.medida}"/> <c:out value="${materiaPrima.tipoMedida.abreviatura}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${materiaPrima.precio}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="1" value="${materiaPrima.medidaDisponibleStock}"/> <c:out value="${materiaPrima.tipoMedida.abreviatura}"/></td>
		  <td style="width:126px; text-align: center;">
            <a class="btn btn-small" onclick="return editar(${materiaPrima.id},'editMateriaPrima.htm?ID=')" ><i class="glyphicon glyphicon-pencil"></i></a>&nbsp;
            <a class="btn btn-small" href="removeMateriaPrima.htm?ID=${materiaPrima.id}" ><i class="glyphicon glyphicon-remove"></i></a>
          </td>
		</tr>
	
	</c:forEach>
	
</table>