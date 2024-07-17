let precioTotal = 0;
let precioEnvioTotal = 0;

document.addEventListener("DOMContentLoaded", async function() {
    const url = "http://localhost:8080/carrito";

    // Función para eliminar un elemento del carrito
    async function eliminarElemento(nombreArticulo) {
        try {
            const deleteUrl = `http://localhost:8080/carrito?nombreArticulo=${nombreArticulo}`;
            const response = await fetch(deleteUrl, {
                method: "DELETE",
                headers: {"Content-Type": "application/json"}
            });

            if (response.ok) {
                console.log(`Elemento con nombre ${nombreArticulo} eliminado exitosamente`);
            } else {
                console.error(`Error al intentar eliminar elemento con nombre ${nombreArticulo}`);
            }
        } catch (error) {
            console.error("Error en la solicitud DELETE:", error);
        }
    }

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Error al obtener los datos del carrito");
        }
        const carrito = await response.json();

        const productosLista = document.getElementById('productosLista');
        productosLista.innerHTML = ''; // Limpiar contenido anterior si es necesario

        carrito.forEach((item, index) => {
            const itemDiv = document.createElement('div');
            itemDiv.className = `item${index + 1} card`;

            // Actualizar precio total y precio de envío total
            precioTotal += item.precio;
            precioEnvioTotal += item.precioEnvio;

            itemDiv.innerHTML = `
                <div class="card-header">
                    <a class="nameSeller">${item.nombreVendedor}</a>
                </div>
                <div class="card-body">
                    <div class="row g-0">
                        <div class="col-1 imgItem">
                            <img src="resources/images/example.png" class="custom-img">
                        </div>
                        <div class="itemInfo col-lg-8 col-md-6 d-flex flex-column">
                            <div class="descrItem overflow-hidden">
                                <a class="descr">${item.descripcion}</a>
                            </div>
                            <div class="opItem navbar mt-auto">
                                <ul class="navbar-nav d-flex flex-row justify-content-start custom-nav-prod pl-0">
                                    <li class="nav-item">
                                        <button type="button" class="btn btn-custom btn-delete btn-link" id="buttonDelete${item.id}" data-id="${item.id}">Eliminar</button>
                                    </li>
                                    <!-- Otros botones -->
                                </ul>
                            </div>
                        </div>
                        <div class="col-3 d-flex align-items-center justify-content-end">
                            <a class="priceProd">$${item.precio}</a>
                        </div>
                    </div>
                    <hr>
                    <div class="shipping row">
                        <div class="col-9">
                            <h6>Envio</h6>
                        </div>
                        <div class="col-3 d-flex justify-content-end">
                            <a class="priceShipping">$${item.precioEnvio}</a>
                        </div>
                    </div>
                </div>
            `;

            productosLista.appendChild(itemDiv);

            // EventListener para el botón eliminar
            const buttonDelete = document.getElementById(`buttonDelete${item.id}`);
            buttonDelete.addEventListener("click", async () => {
                await eliminarElemento(item.nombreArticulo);
                itemDiv.remove(); // Actualizar el front eliminando el div respectivo al elemento que acabo de borrar
                // También actualiza los precios al eliminar un elemento del carrito
                actualizarPrecios();
            });
        });

        // Función para actualizar los precios en la interfaz
        function actualizarPrecios() {
            const productosPrecioElement = document.getElementById('productosPrecio');
            const enviosPrecioElement = document.getElementById('enviosPrecio');
            const totalPrecioElement = document.getElementById('totalPrecio');

            productosPrecioElement.textContent = `$${precioTotal.toFixed(2)}`;
            enviosPrecioElement.textContent = `$${precioEnvioTotal.toFixed(2)}`;
            totalPrecioElement.textContent = `$${(precioTotal + precioEnvioTotal).toFixed(2)}`;
        }

        // Llamar a la función para asegurar que los precios se muestren correctamente al cargar la página
        actualizarPrecios();

    } catch (error) {
        console.error("Error en la solicitud:", error);
    }
});
