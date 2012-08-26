var mensaje = {
    init: function(mensaje){
        var elem = document.createElement('h1');
        elem.appendChild(document.createTextNode(mensaje));
        document.getElementsByTagName('body')[0].appendChild(elem);
        elem.setAttribute('style', 'position: absolute; margin: 0; background-color: #99F; width: 100%;top: -40px; text-align: center;');
        elem.setAttribute('id', 'msj');
    },
    activar: function(){
        intervalo = setInterval(function(){
            m = document.getElementById('msj');
            viejoTop = parseInt(m.style.top);
            if(viejoTop <= 0){
                nuevoTop = viejoTop+2;
                m.style.top = nuevoTop + 'px';
            }else{
                m.style.top = '0px';
                clearInterval(intervalo);
                setTimeout(function(){
                    intervalo = setInterval(function(){
                        m = document.getElementById('msj');
                        viejoTop = parseInt(m.style.top);
                        if(viejoTop >= -50){
                            nuevoTop = viejoTop-2;
                            m.style.top = nuevoTop + 'px';
                        }else{
                            m.style.top = '-50px';
                            clearInterval(intervalo);
                        }
                    },10);
                }, 2000);
            }
        }, 10);
    },
    cambiarMensaje: function(mensaje){
        document.getElementById('msj').innerHTML = mensaje;
    }
}

window.onload = function(){
    mensaje.init('HolaMundo');
}