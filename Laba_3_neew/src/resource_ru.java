import java.util.ListResourceBundle;

/**
 * Created by sofia on 18.06.17.
 */
public class resource_ru extends ListResourceBundle{
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                // LOCALIZE THE SECOND STRING OF EACH ARRAY (e.g., "OK")
                {"wrongDataKey","Данные введены неверно"},
                {"ghostKey","Приведения"},
                {"problemsKey","Проблемы?"},
                {"changeToKey","поменять на"},
                {"nameKey","имя"},
                {"ageKey","возраст"},
                {"heightKey","рост"},
                {"adultKey","взрослые"},
                {"sortKey","сортировать"},
                {"importKey","импорт"},
                {"removeLowerKey","удалить младше"},
                {"deleteAllKey","очистить"},
                {"sureMsgKey","Вы хотите удалить все записи?"},
                {"confirmKey","подтвердить"},
                {"allDeletedKey","Очищено!"},
                {"addKey","добавить"},
                {"deleteKey","удалить"},
                {"goKey","го!"},
                {"ruKey","русский"},
                {"fiKey","финский"},
                {"swKey","шведский"},
                {"spKey","испанский"},
                // END OF MATERIAL TO LOCALIZE
        };
    }
}
