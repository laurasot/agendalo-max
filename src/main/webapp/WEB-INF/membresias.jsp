<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
      <link rel="stylesheet" type="text/css" href="/css/membresias.css">
      <title>Membresias</title>
    </head> 

    <body>
      <nav class="navbar navbar-expand-lg ">
        <div class="container-fluid">
          <a id="nombrePagina" class="navbar-brand" href="/"><span id="agendalo">Agéndalo</span><span
            id="max">Max</span></a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <!--mostrar boton de crear empresa solo si no tiene ninguna empresa -->
                <c:choose>
                  <c:when test="${usuario.empresa == null}">
                    <!-- Modal -->
                    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false"
                      tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h1 class="modal-title fs-5" id="staticBackdropLabel">Crear Empresa</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            Poner info de las caracteristicas de la empresa, cuales son los derechos y deberes del
                            propietario
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <a class="btn btn-black" href="/planes">Aceptar</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </c:when>
                </c:choose>
              </li>
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
            <div class="nav-item dropdown" id="usuario-nombre">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <c:out value="${usuario.nombre}"/>
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="/horas/usuario/${usuario.id}">Horas agendadas</a></li>
              <c:if test="${usuario.getEmpresa() != null}">
                <li><a class="dropdown-item" href="/plan/${usuario.getEmpresa().getId()}">Tu empresa</a></li>
              </c:if>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#">Editar perfil</a></li>
              <li><a class="dropdown-item" href="/logout">Log out</a></li>
              </ul>
            </div>
          </div>
        </div>
    </nav>

      <div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="/imagenes/undraw_decide_re_ixfw.svg" class="d-block w-100" alt="...">
          </div>
          <div class="carousel-item">
            <img src="/imagenes/undraw_select_re_3kbd.svg" class="d-block w-100" alt="...">
          </div>
          <div class="carousel-item">
            <img src="/imagenes/undraw_selected_options_re_vtjd.svg" class="d-block w-100" alt="...">
          </div>
          <div class="carousel-item">
            <img src="/imagenes/undraw_contrast_re_hc7k.svg" class="d-block w-100" alt="...">
          </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying"
          data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying"
          data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
      <div id="container-c">
        <!-- <div class="container-cards"> -->
          <!-- <div class="card alienarDisplay" style="width: 18rem;"> -->
            <div id="cartafree">
              <div class="card-body">
                <h5 class="card-title fw-bold">Plan Free</h5>
                ¡Completamente Gratis!
                <p class="card-text"> 
                  Crea y comparte ese servicio que puedes ofrecer.
                  <div class="button-crear fw-bold">
                    <a href="/planes/new">Crear empresa</a>
                  </div>
                </p>
                
              </div>
          </div>
          <!-- <div class="container-cards"> -->
          <!-- <div class="card alinearDisplay" style="width: 18rem;"> -->
            <div id="cartapre">
            <div class="card-body">
              <h5 class="card-title fw-bold">Plan Premium</h5>
              Por 5000$/mes
              <p class="card-text">Añade la cantidad de servicios que desees sin anuncios.
                <div class="button-crear fw-bold">
                  <a href="/planes/new">Crear empresa</a>
                </div>
              </p>
            </div>
          </div>
          <!-- </div> -->
        <!-- </div> -->
      </div>
      </div>
      <!-- Footer -->
      <footer class="text-center text-lg-start bg-white text-muted">
        <!-- Section: Social media -->
        <section class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom" style="background-color:  rgb(177, 165, 253);">
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
        <div class="text-center p-4" style="background-color:rgb(177, 165, 253);">
          www.agendalomax.cl © 2022 :
          <a class="text-reset fw-bold" href="https://mdbootstrap.com/"> Encuentra tu servicio y pide cita</a>
        </div>
      </div>
    </div>
  </nav>

  <div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="/imagenes/undraw_decide_re_ixfw.svg" class="d-block w-100" alt="...">
      </div>
      <div class="carousel-item">
        <img src="/imagenes/undraw_select_re_3kbd.svg" class="d-block w-100" alt="...">
      </div>
      <div class="carousel-item">
        <img src="/imagenes/undraw_selected_options_re_vtjd.svg" class="d-block w-100" alt="...">
      </div>
      <div class="carousel-item">
        <img src="/imagenes/undraw_contrast_re_hc7k.svg" class="d-block w-100" alt="...">
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying"
      data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying"
      data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>

  <div id="container-c">
    <div id="cartafree">
      <div class="card-body">
        <h5 class="card-title">Plan Gratis</h5>
        <p class="card-text"> 
          En nuestro Plan Gratuito podrás contar con
          <div class="button-crear">
            <a href="/planes/new">Crear empresa</a>
          </div>
        </p>
        
      </div>
    </div>
    <div id="cartapre">
      <div class="card-body">
        <h5 class="card-title">Plan Premium</h5>
        <p class="card-text">En nuestro Plan Premium
          <div class="button-crear">
            <a href="/planes/new">Crear empresa</a>
          </div>
        </p>
      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer class="text-center text-lg-start bg-white text-muted">
    <!-- Section: Social media -->
    <section class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
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
              <i class="fas fa-gem me-3 text-secondary"></i>Agendalomax
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
    <div class="text-center p-4" style="background-color: rgb(189, 179, 254);">
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