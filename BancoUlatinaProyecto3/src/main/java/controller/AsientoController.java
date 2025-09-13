package controller;

import model.Asiento;
import service.ServiceAsiento;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AsientoController implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Asiento> asientos;

    // Getter para la lista de asientos
    public List<Asiento> getAsientos() {
        if (asientos == null) {
            ServiceAsiento service = new ServiceAsiento();
            asientos = service.listarAsientos();
        }
        return asientos;
    }
}
