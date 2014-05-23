<%@ taglib uri="/WEB-INF/tlds/c.tld" 				prefix="c"%>

<nav class="navbar navbar-default" role="navigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <a class="navbar-brand" href="#">eCuentas</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li id="menuHome" 	class="active"><a href="home.htm">Home</a></li>
        <li id="menuCompra"><a href="compras.htm">Compra</a></li>
        <li id="menuVenta"><a href="ventas.htm">Venta</a></li>
        <li class="dropdown" id="menuAdministracion">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Administraci&oacute;n <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="productos.htm">Productos</a></li>
            <li><a href="materiaPrimaList.htm">Materia Prima</a></li>
            <li><a href="#"></a></li>
            <li class="divider"></li>
            <li><a href="#">Tipos de Producto</a></li>
            <li><a href="#">Tipos de Medidas</a></li>
          </ul>
        </li>
        <li id="logout"><a href="javascript:formSubmit()" />Logout</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<c:url value="/j_spring_security_logout" var="logoutUrl" />

<!-- csrt for log out-->
<form action="${logoutUrl}" method="post" id="logoutForm">
  <input type="hidden" 
	name="${_csrf.parameterName}"
	value="${_csrf.token}" />
</form>
 
<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>