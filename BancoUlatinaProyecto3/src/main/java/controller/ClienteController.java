package controller;

import model.Cliente;
import service.ServiceCliente;
import java.util.List;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ClienteController implements Serializable {
    private static final long serialVersionUID = 1L;

    private Cliente clienteNuevo = new Cliente(); // Objeto para el formulario
    private List<Cliente> clientes;               // Lista de clientes existentes

    // Getter y setter para clienteNuevo
    public Cliente getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(Cliente clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    // Getter para la lista de clientes
    public List<Cliente> getClientes() {
        if (clientes == null) {
            ServiceCliente servicioCliente = new ServiceCliente();
            clientes = servicioCliente.listarClientes();
        }
        return clientes;
    }

    // Método para agregar cliente con verificación KYC
    public String agregarCliente() {
        ServiceCliente servicioCliente = new ServiceCliente(); // ✅ crear aquí

        // Verificar si ya existe
        if (servicioCliente.existeCliente(clienteNuevo.getCedula())) {
            FacesMessage msg = new FacesMessage("El cliente ya existe en la base de datos.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null; // permanecer en la misma página
        }

        // Verificar si está en lista restrictiva
        if (servicioCliente.estaEnListaRestrictiva(clienteNuevo.getCedula())) {
            FacesMessage msg = new FacesMessage("El cliente está en lista de embargo/restricción. No se puede agregar.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null; // permanecer en la misma página
        }

        // Insertar si pasa las validaciones
        servicioCliente.agregarCliente(clienteNuevo);

        // 🔹 Recargar la lista para que se vea reflejado en la tabla
        clientes = servicioCliente.listarClientes();

        clienteNuevo = new Cliente(); // limpiar formulario

        // 🔹 Mensaje de éxito
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Éxito",
                "Cliente agregado correctamente.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return null; // mantener en la misma página
    }
}
