import java.util.ListResourceBundle;

/**
 * Created by sofia on 18.06.17.
 */
public class resource_fi extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                // LOCALIZE THE SECOND STRING OF EACH ARRAY (e.g., "OK")
                {"wrongDataKey","Vääriä tietoja."},
                {"ghostKey","aave"},
                {"problemsKey","Ongelmia?"},
                {"changeToKey","muutos"},
                {"nameKey","nimi"},
                {"ageKey","ikä"},
                {"heightKey","korkeus"},
                {"adultKey","aikuiset"},
                {"sortKey","järjestellä"},
                {"importKey","tuonti"},
                {"removeLowerKey","Poista alempi"},
                {"deleteAllKey","asia selvä"},
                {"sureMsgKey","Haluatko poistaa kaikki?"},
                {"confirmKey","vahvistaa"},
                {"allDeletedKey","Tehty!"},
                {"addKey","lisätä"},
                {"deleteKey","poistaa"},
                {"goKey","mennä!"},
                {"ruKey","ryssä"},
                {"fiKey","suomi"},
                {"swKey","ruotsi"},
                {"spKey","espanja"},
                // END OF MATERIAL TO LOCALIZE
        };
    }
}
