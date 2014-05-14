<%@ taglib uri="/WEB-INF/tlds/c.tld" 	prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" 	prefix="fmt"%>

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
	<a onclick="return crear('addproducto.htm');" href="#" class="btn btn-primary">Nuevo Producto</a>
</div>

<table class="table table-bordered">
	<tr>
	  <th class="active">ID</th>
	  <th class="active">Tipo de Producto</th>
	  <th class="active">Nombre</th>
	  <th class="active">Tamaño</th>
	  <th class="active">Costo ($)</th>
	  <th class="active">Precio Sugerido ($)</th>
	  <th class="active"></th>
	</tr>
	
	<c:forEach var="producto" items="${model.productos}" >
		<tr>
		  <td><c:out value="${producto.id}"/></td>
		  <td><c:out value="${producto.tipoProducto.descripcion}"/></td>
		  <td><c:out value="${producto.descripcion}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${producto.medida}"/> <c:out value="${producto.tipoMedida.abreviatura}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${producto.precioCosto}"/></td>
		  <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${producto.precioSugerido}"/></td>
		  <td style="width:156px; text-align: center;">
		    <a class="btn btn-small" onclick="return editar(${producto.id},'ingredientes.htm?ID=')" ><i class="glyphicon glyphicon-list"></i></a>&nbsp;
            <a class="btn btn-small" onclick="return editar(${producto.id},'editproducto.htm?ID=')" ><i class="glyphicon glyphicon-pencil"></i></a>&nbsp;
            <a class="btn btn-small" href="removeproducto.htm?ID=${producto.id}" ><i class="glyphicon glyphicon-remove"></i></a>
          </td>
		</tr>
	</c:forEach>
	
</table>