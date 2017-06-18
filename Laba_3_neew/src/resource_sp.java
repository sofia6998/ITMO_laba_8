import java.util.ListResourceBundle;

/**
 * Created by sofia on 18.06.17.
 */
public class resource_sp extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                // LOCALIZE THE SECOND STRING OF EACH ARRAY (e.g., "OK")
                {"wrongDataKey","información incorrecta."},
                {"ghostKey","Fantasmas"},
                {"problemsKey","problemas?"},
                {"changeToKey","cambiar a"},
                {"nameKey","nombre"},
                {"ageKey","edad"},
                {"heightKey","crecimiento"},
                {"adultKey","adulto"},
                {"sortKey","ordenar"},
                {"importKey","importar"},
                {"removeLowerKey","Quitar más abajo"},
                {"deleteAllKey","claro"},
                {"sureMsgKey","Quieres borrar todo?"},
                {"confirmKey","confirmar"},
                {"allDeletedKey","Hecho!"},
                {"addKey","añadir"},
                {"deleteKey","borrar"},
                {"goKey","ir!"},
                {"ruKey","ruso"},
                {"fiKey","finlandés"},
                {"swKey","sueco"},
                {"spKey","Español"},
                // END OF MATERIAL TO LOCALIZE
        };
    }
}

