<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@600&family=Playfair+Display:ital,wght@1,500&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@1000&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Anton&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="/css/index.css">
  <title>Home</title>
</head>

<body>
  <!-- Barra Menu -->
  <nav class="navbar navbar-expand-lg ">
    <div class="container-fluid">
      <a id="nombrePagina" class="navbar-brand" href="/">
        <span id="agendalo">Agéndalo</span>
        <span id="max">Max</span>
      </a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <!--mostrar boton de crear empresa solo si no tiene ninguna empresa -->
            <c:choose>
              <c:when test="${usuario.getEmpresa() == null && usuario != null}">
                <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#staticBackdrop" id="crea-empresa">
                  Crear Empresa
                </button>
                <!-- Modal -->
                <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false"
                  tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                  <div class="modal-dialog modal-lg">
                    <div class="modal-content" id="modalEmpresa">
                      <div class="modal-body d-flex text-box text-light">
                          <img class="me-4" src="/imagenes/marca.png" alt="">
                        <div class="text-box text-light">
                          <h2 class="fw-bold mt-3">Crea una Empresa y accede a:</h2>
                          <div class=" d-flex"><img class="iconosTic" src="/imagenes/cheque(2).png" alt="">
                            <p class="ms-2">Una Empresa que ofrece un servicio, o más (premium) </p>
                          </div>
                          <div class=" d-flex"><img class="iconosTic" src="/imagenes/cheque(2).png" alt="">
                            <p class="ms-2">Un panel de administración de tus horas</p>
                          </div>
                          <div class=" d-flex"><img class="iconosTic" src="/imagenes/cheque(2).png" alt="">
                            <p class="ms-2">Creación de tu horario</p>
                        </div>
                        </div>
                      
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn botones" data-bs-dismiss="modal">Cancelar</button>
                        <a class="btn botones" id="aceptaEmpresa" href="/planes">Aceptar</a>
                      </div>
                    </div>
                  </div>
                </div>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${usuario == null}">
                <a class="btn botones loginBoton"  href="/login">Login</a>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${usuario == null}">
                <a class="btn botones loginBoton" href="/registration">Registrarse</a>
              </c:when>
            </c:choose>
          </li>
          <c:choose>
            <c:when test="${!empresa.empresafree && usuario.empresa != null}">
              <li class="nav-item text-danger mt-2">Cuenta premium!</li>
            </c:when>
          </c:choose>
        </ul>
        <!-- Buscadores de Servicios-->
        <form class="d-flex" role="search" method="POST" action="/search" id="barrita">
          <select class="me-2 form-select" name="selectReg" id="selectReg">
            <option value="0">Región</option>
            <c:forEach items="${regiones}" var="region">
                <option value="${region.id}">${region.nombre}</option>
            </c:forEach>
          </select>
          <select class="me-2 form-select" name="selectCiud" id="selectCiud">
            <option value="0">Ciudad</option>
          </select>
          <input class="form-control me-2" type="search" name="servicio" placeholder="Inserte servicio" aria-label="Search">
          <button class="btn botones" type="submit">Buscar</button>
        </form>


        <c:choose>
              <c:when test="${usuario != null}">
                <div class="nav-item dropdown" id="usuario-nombre">
                  <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <c:out value="${usuario.nombre}"/>
                  </a>
                  <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="/horas/usuario/${usuario.id}">Horas agendadas</a></li>
                    <li><a class="dropdown-item" href="/perfil/${usuario.id}">Editar perfil</a></li>
                    <c:if test="${usuario.getEmpresa() != null}">
                      <li><a class="dropdown-item" href="/plan/${usuario.getEmpresa().getId()}">Tu empresa</a></li>
                    </c:if>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="/logout">Log out</a></li>
                  </ul>
                </div>
              </c:when>
        </c:choose>
        
      </div>
    </div>
</nav>

    <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="false">
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="/imagenes/tienda.png" class="d-block w-100" alt="tienda">
            <div class="carousel-caption d-none text-black d-md-block">
              <h5 class="esta-cosa fw-bold">¿Buscas que las personas conozcan lo que tienes para ofrecer?</h5>
              <p class="esta-cosa fw-bold">¡Aquí podrán encontrarte!</p>
            </div>
          </div>
      <div class="carousel-item">
        <img src="/imagenes/cooperacion.png" class="d-block w-100" alt="negocios">
        <div class="carousel-caption d-none text-black d-md-block fw-bold">
          <h5 class="esta-cosa fw-bold">Podrás encontrar</h5>
          <p>estos y muchos más servicios.</p>
        </div>
      </div> 
      <div class="carousel-item">
        <img src="/imagenes/trade.png" class="d-block w-100" alt="comercio">
        <div class="carousel-caption d-none text-black d-md-block fw-bold">
          <h5 class="esta-cosa fw-bold">Podrás encontrar</h5>
          <p>estos y muchos más servicios.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="/imagenes/hair-salon.png" class="d-block w-100" alt="barbería">
        <div class="carousel-caption d-none text-black d-md-block fw-bold">
          <h5 class="esta-cosa fw-bold">Podrás encontrar</h5>
          <p>estos y mucho más servicios.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="/imagenes/hairstyle.png" class="d-block w-100" alt="peluquería">
        <div class="carousel-caption d-none text-black d-md-block fw-bold">
          <h5 class="esta-cosa fw-bold">Podrás encontrar</h5>
          <p>estos y muchos más servicios.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="/imagenes/nail-polish.png" class="d-block w-100" alt="salón de uñas">
        <div class="carousel-caption d-none text-black d-md-block fw-bold">
          <h5 class="esta-cosa fw-bold">Podrás encontrar</h5>
          <p>estos y muchos más servicios.</p>
        </div> 
      </div>
      <div class="carousel-item">
        <img src="/imagenes/take-away.png" class="d-block w-100" alt="entrega de comida">
        <div class="carousel-caption d-none text-black d-md-block fw-bold">
          <h5 class="esta-cosa fw-bold">Podrás encontrar</h5>
          <p>estos y muchos más servicios.</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="/imagenes/healthcare.png" class="d-block w-100" alt="salud">
        <div class="carousel-caption d-none text-black d-md-block fw-bold">
          <h5 class="esta-cosa fw-bold">Podrás encontrar</h5>
          <p>estos y muchos más servicios.</p>
        </div>
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>

  <div class="cositas row g-2 text-center ">
      <div class="col-1">
        <div class="icons">
          <div class="p-3 fw-bold">Encuentra tu servicio</div>
            <img src="/imagenes/buscar.png" alt="buscar">
            <p>¡Tu servicio aquí disponible!</p>
        </div>
      </div>
      <div class="col-2">
        <div class="icons">
          <div class="p-3 fw-bold">Date ese gusto</div>
            <img src="/imagenes/pulgares-hacia-arriba.png" alt="Like">
            <p >¡Disfruta tu servicio en un par de clicks!</p>
        </div>
      </div>
    <div class="col-3">
        <div class="p-3 fw-bold">Toma tu hora</div>
          <div class="icons">
          <img src="/imagenes/reloj.png" alt="Like">
          <p>¡Agenda la hora de preferencia para mayor comodidad!</p>
        </div>
      </div>
      <div class="col-4">
        <div class="icons">  
          <div class="p-3 fw-bold">Busca en tu sector</div>
          <img src="/imagenes/mapa.png" alt="Like">
          <p>¡Descubre que servicio están cerca de ti!</p>
        </div>
      </div>
    </div>
  </div>
  <section class="filtro fw-bold">
    <h1 class="esta-cosa fw-bold">ÚNETE A AGÉNDALOMAX</h1>
    <p>ACOMODA TU TIEMPO CON LOS MEJORES SERVICIOS DE CHILE</p>
  </section>
  <!-- Footer -->
  <footer class="text-center text-lg-start bg-white text-muted">
    <!-- Section: Social media -->
    <section id="footer-1" class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom" style="background-color:rgb(182, 179, 254);">
      <!-- Left -->

      <div class="sub-texto">
        <span>Conectate con nosotros en redes sociales</span>
      </div>
      <!-- Left -->

      <!-- Right -->
      <div class="">
        <a href="" class="link-secondary">
          <i class="fab fa-facebook-f"><img src="/imagenes/facebook.png" alt="facebook"></i>
        </a>
        <a href="" class="link-secondary">
          <i class="fab fa-twitter"><img src="/imagenes/twitter.png" alt="twitter"></i>
        </a>
        <a href="" class="link-secondary">
          <i class="fab fa-google"><img src="/imagenes/google-plus.png" alt="google"></i>
        </a>
        <a href="" class="link-secondary">
          <i class="fab fa-instagram"><img src="/imagenes/instagram.png" alt="instagram"></i>
        </a>
        <a href="" class="link-secondary">
          <i class="fab fa-linkedin"><img src="/imagenes/linkedin.png" alt="linkedin"></i>
        </a>
        <a href="" class="link-secondary">
          <i class="fab fa-github"><img src="/imagenes/github.png" alt="github"></i>
        </a>
      </div>
      <!-- Right -->
    </section>
    <!-- Section: Social media -->

    <!-- Section: Links  -->
    <section class="section-part">
      <div class="container text-center text-md-start mt-5">
        <!-- Grid row -->
        <div class="row mt-3">
          <!-- Grid column -->
          <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
            <!-- Content -->
            <h6 class="text-uppercase fw-bold mb-4">
              <i class="fas fa-gem me-3 text-secondary"></i>Agéndalomax
            </h6>
            <p>
              Nos encargamos de agendar tus horas con el servicio que brindes o necesites.
            </p>
          </div>
          <!-- Grid column -->

          <!-- Grid column -->
          <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
            <!-- Links -->
            <h6 class="text-uppercase fw-bold mb-4">
              Información
            </h6>
            <p>
              <a href="#!" class="text-reset">Sobre nosotros</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Privacidad</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Marco legal</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Terminos y condiciones</a>
            </p>
          </div>
          <!-- Grid column -->

          <!-- Grid column -->
          <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
            <!-- Links -->
            <h6 class="text-uppercase fw-bold mb-4">
              Links útiles
            </h6>
            <p>
              <a href="#!" class="text-reset">Ayuda</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Configuración</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Trabaja con nosotros</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Otros</a>
            </p>
          </div>
          <!-- Grid column -->

          <!-- Grid column -->
          <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
            <!-- Links -->
            <h6 class="text-uppercase fw-bold mb-4">Contacto</h6>
            <p><i class="fas fa-home me-3 text-secondary"></i> Santiago, ST 10012, CL</p>
            <p>
              <i class="fas fa-envelope me-3 text-secondary"></i>
              agendalomax@example.com
            </p>
            <p><i class="fas fa-phone me-3 text-secondary"></i> + 01 234 567 89</p>
            <p><i class="fas fa-print me-3 text-secondary"></i> + 01 234 567 80</p>
          </div>
          <!-- Grid column -->
        </div>
        <!-- Grid row -->
      </div>
    </section>
    <!-- Section: Links  -->

    <!-- Copyright -->
    <div class="text-center p-4" style="background-color:rgb(182, 179, 254);">
      www.agendalomax.cl © 2022 :
      <a class="text-reset fw-bold" href="https://mdbootstrap.com/"> Encuentra tu servicio y pide cita</a>
    </div>
    <!-- Copyright -->
  </footer>
  <!-- Footer -->
  <script>
    //Mapeo de variable para archivo servicio.js
    var regionesConAscii = '<c:out value="${regionesJson}"/>'
  </script>
  <script type="text/javascript" src="/js/servicio.js"></script>
</body>

</html>
