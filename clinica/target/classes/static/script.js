document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("generarOdontologo").addEventListener("click", function() {
        const matricula = document.getElementById("matricula").value.trim();
        const nombre = document.getElementById("nombre").value.trim();
        const apellido = document.getElementById("apellido").value.trim();

        if (matricula === '' || nombre === '' || apellido === '') {
            alert('Por favor, complete todos los campos antes de generar el JSON.');
            return;
        }

        if (matricula.length < 10 || matricula.length > 50) {
            alert('La matrícula debe tener entre 10 y 50 caracteres.');
            return;
        }

        if (nombre.length < 2 || nombre.length > 50) {
            alert('El nombre del odontólogo debe tener entre 2 y 50 caracteres.');
            return;
        }

        if (apellido.length < 2 || apellido.length > 50) {
            alert('El apellido del odontólogo debe tener entre 2 y 50 caracteres.');
            return;
        }

        const odontologo = {
            matricula: matricula,
            nombre: nombre,
            apellido: apellido
        };

        const jsonData = JSON.stringify(odontologo);

        const blob = new Blob([jsonData], { type: "application/json" });
        const url = 'http://localhost:8080/odontologos/registrar'; // Ruta correcta para la solicitud POST

        const a = document.createElement("a");
        a.href = url;
        a.download = "odontologo.json";
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);

        // Configuración de la solicitud POST
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        });
    });
});
