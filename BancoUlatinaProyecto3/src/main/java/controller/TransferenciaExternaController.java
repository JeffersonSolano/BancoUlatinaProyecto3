package controller;

import model.TransferenciaExterna;
import service.ServiceTransferenciaExterna;
import java.io.Serializable;
import java.util.List;
import java.sql.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class TransferenciaExternaController implements Serializable {
    private static final long serialVersionUID = 1L;

    private TransferenciaExterna transferenciaNueva = new TransferenciaExterna();
    private List<TransferenciaExterna> transferencias;

    public TransferenciaExterna getTransferenciaNueva() {
        return transferenciaNueva;
    }

    public void setTransferenciaNueva(TransferenciaExterna transferenciaNueva) {
        this.transferenciaNueva = transferenciaNueva;
    }

    public List<TransferenciaExterna> getTransferencias() {
        if (transferencias == null) {
            ServiceTransferenciaExterna service = new ServiceTransferenciaExterna();
            transferencias = service.listarTransferencias();
        }
        return transferencias;
    }

    // Acción para agregar transferencia externa
    public String agregarTransferencia() {
        ServiceTransferenciaExterna service = new ServiceTransferenciaExterna();

        transferenciaNueva.setFecha(new Date(System.currentTimeMillis()));

        boolean exito = service.agregarTransferencia(transferenciaNueva);

        transferencias = service.listarTransferencias();
        transferenciaNueva = new TransferenciaExterna(); // limpiar formulario

        FacesMessage msg = exito ?
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Transferencia registrada correctamente") :
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo realizar la transferencia (saldo insuficiente o error)");

        FacesContext.getCurrentInstance().addMessage(null, msg);

        return null; // mantener en la misma página
    }
}
