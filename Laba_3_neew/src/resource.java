import java.util.ListResourceBundle;

/**
 * Created by sofia on 18.06.17.
 */
public class resource extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                // LOCALIZE THE SECOND STRING OF EACH ARRAY (e.g., "OK")
                {"wrongDataKey", "wrong data"},
                {"ghostKey", "ghost"},
                {"problemsKey", "problems?"},
                {"changeToKey", "change to"},
                {"nameKey", "name"},
                {"ageKey", "age"},
                {"heightKey", "height"},
                {"adultKey", "adult"},
                {"sortKey", "sort"},
                {"importKey", "import"},
                {"removeLowerKey", "remove lover"},
                {"deleteAllKey", "clear"},
                {"sureMsgKey", "Want  to delete?"},
                {"confirmKey", "confirm"},
                {"allDeletedKey", "Done!"},
                {"addKey", "add"},
                {"deleteKey", "delete"},
                {"goKey", "Go!"},
                {"ruKey", "russian"},
                {"fiKey", "finnish"},
                {"swKey", "swedish"},
                {"spKey", "spanish"},
                // END OF MATERIAL TO LOCALIZE
        };
    }
}
