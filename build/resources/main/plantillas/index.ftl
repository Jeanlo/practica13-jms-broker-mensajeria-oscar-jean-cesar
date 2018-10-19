<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Práctica 13 JMS - OCJ</title>

<#-- Recursos propios -->
    <link rel="stylesheet" href="/estilos/estilo.css">
    <script src="js/jquery-3.2.1.min.js"></script>

<#-- Google Charts -->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<#-- Font Awesome 5 -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
          integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">

<#-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>

<#-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
</head>
<body class="bg-info">
<div class="container-fluid">
    <div class="row">
        <div class="col">
            <div class="row">
                <div class="col-12 p-0">
                    <nav class="navbar navbar-expand-lg navbar-light bg-light boxed-shadow">
                        <a class="navbar-brand" href="/">Práctica 13 - OCJ</a>
                        <button class="navbar-toggler" type="button" datos-toggle="collapse"
                                datos-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                                aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav mr-auto">
                            </ul>
                            <img class="rounded-circle boxed-shadow" src="/imagenes/perfil.png"
                                 alt="foto de usuario" width="64px" height="64px">
                        </div>
                    </nav>
                </div>
            </div>
            <div class="col-12 p-2">
                <input type="hidden" id="registros" value='${registros}'>
                <div id="graficaLinea1" style="width: 90%; height: 500px;" class="mx-auto my-1 boxed-shadow"></div>
                <div id="graficaLinea2" style="width: 90%; height: 500px;" class="mx-auto my-1 boxed-shadow"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script>
    var webSocket;
    var tiempoReconectar = 5000;

    var datosCliente1 = [['FechaGeneracion', 'Temperatura', 'Humedad']];
    var datosCliente2 = [['FechaGeneracion', 'Temperatura', 'Humedad']];

    $(document).ready(function () {
        var datos = JSON.parse($("#registros").val());

        for (var i = 0; i < datos.registros.length; i++) {
            if (datos.registros[i].idDispositivo === 1) {
                datosCliente1.push([
                    datos.registros[i].fechaGeneracion,
                    datos.registros[i].temperatura,
                    datos.registros[i].humedad
                ]);
            }

            if (datos.registros[i].idDispositivo === 2) {
                datosCliente2.push([
                    datos.registros[i].fechaGeneracion,
                    datos.registros[i].temperatura,
                    datos.registros[i].humedad
                ]);
            }
        }

        google.charts.load('current', {'packages': ['corechart']});

        google.charts.setOnLoadCallback(dibujarGrafica1);
        google.charts.setOnLoadCallback(dibujarGrafica2);
    });

    function recibirInformacionServidor(mensaje) {
        var dato = JSON.parse(mensaje.data);

        if (dato.idDispositivo === 1) {
            google.charts.load('current', {'packages': ['corechart']});
            google.charts.setOnLoadCallback(dibujarGrafica1);

            datosCliente1.push([
                dato.fechaGeneracion,
                dato.temperatura,
                dato.humedad
            ]);
        } else if (dato.idDispositivo === 2) {
            google.charts.load('current', {'packages': ['corechart']});
            google.charts.setOnLoadCallback(dibujarGrafica2);

            datosCliente2.push([
                dato.fechaGeneracion,
                dato.temperatura,
                dato.humedad
            ]);
        }
    }

    function dibujarGrafica1() {
        var datos = google.visualization.arrayToDataTable(datosCliente1);

        var opciones = {
            title: 'Sensor 1',
            curveType: 'function',
            legend: {position: 'bottom'}
        };

        var grafica = new google.visualization.LineChart(document.getElementById('graficaLinea1'));
        grafica.draw(datos, opciones);
    }

    function dibujarGrafica2() {
        var datos = google.visualization.arrayToDataTable(datosCliente2);

        var opciones = {
            title: 'Sensor 2',
            curveType: 'function',
            legend: {position: 'bottom'}
        };

        var grafica = new google.visualization.LineChart(document.getElementById('graficaLinea2'));

        grafica.draw(datos, opciones);
    }

    function conectar() {
        webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/mensajeServidor");
        webSocket.onmessage = function (datos) {
            recibirInformacionServidor(datos);
        };
    }

    function verificarConexion() {
        if (!webSocket || webSocket.readyState == 3) {
            conectar();
        }
    }

    setInterval(verificarConexion, tiempoReconectar);
</script>