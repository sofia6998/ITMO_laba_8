import java.util.ListResourceBundle;

/**
 * Created by sofia on 18.06.17.
 */
public class resource_sw extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                // LOCALIZE THE SECOND STRING OF EACH ARRAY (e.g., "OK")
                {"wrongDataKey","Data angetts felaktigt."},
                {"ghostKey","spöken"},
                {"problemsKey","Problem?"},
                {"changeToKey","ändra till"},
                {"nameKey","namn"},
                {"ageKey","ålder"},
                {"heightKey","tillväxt"},
                {"adultKey","vuxna"},
                {"sortKey","sortera"},
                {"importKey","importera"},
                {"removeLowerKey","Ta bort lägre"},
                {"deleteAllKey","klar"},
                {"sureMsgKey","Vill du radera allt?"},
                {"confirmKey","bekräfta"},
                {"allDeletedKey","Gjort!"},
                {"addKey","Lägg till"},
                {"deleteKey","radera"},
                {"goKey","gå!"},
                {"ruKey","ryska"},
                {"fiKey","finsk"},
                {"swKey","svenska"},
                {"spKey","spansk"},
                // END OF MATERIAL TO LOCALIZE
        };
    }
}
