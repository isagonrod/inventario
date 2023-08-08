package isamix.inventario.crud;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import isamix.inventario.R;

public class FuncionamientoApp extends AppCompatActivity {
    TextView tvTitle, tvMessage;
    String textMessage =
            "1. MENÚ PRINCIPAL SUPERIOR:\n\t" +
                    "En la parte superior hay dos iconos, uno para acceder a esta ayuda/información " +
                    "sobre la aplicación, y otro para acceder a la lista de la compra desde cualquier sitio.\n\n" +
            "2. MENÚ DESPLEGABLE Y PANTALLA PRINCIPAL:\n\t" +
                    "Acceso a la gestión de los distintos productos o enseres " +
                    "que puedas tener por casa y quieras incluir en la aplicación para tener un control sobre ellos.\n\n" +
            "3. GESTIÓN DE PRODUCTOS:\n\t" +
                    "Aparecen tres botones superiores ('Añadir nueva categoría', 'Agregar nuevo producto' " +
                    "y 'Ver lista completa de productos') que hacen lo que sus propios nombres indican.\n\t" +
                    "A continuación, en la parte inferior aparecerán en forma de botón todas las categorías " +
                    "que tengas creadas hasta el momento, con o sin productos asociados. Si tienen productos " +
                    "asociados, no se podrán eliminar; pero si no los tienen, dejando el botón pulsado, " +
                    "se podrá eliminar la categoría.\n\t" +
                    "Tanto si se entra por categoría o en la lista completa, aparece un listado de productos " +
                    "con tres botones cada uno para incluirlo en la lista de la compra, editarlo o eliminarlo.\n\t" +
                    "Si se deja pulsado sobre un producto se accederá a la vista del mismo, con todos " +
                    "los datos asociados al mismo y dos botones más (editar y borrar).\n\t" +
                    "Si se pulsa una sola vez, se restará 1 la cantidad de producto que tiene; " +
                    "cuando llegue a 0 se incluirá automáticamente en la lista de la compra.\n\n" +
            "4. GESTIÓN DE LIBROS:\n\t" +
                    "Al igual que la de productos, aparecen tres botones en la parte superior " +
                    "('Añadir nuevo género', 'Agregar nuevo libro' y 'Ver lista completa de libros').\n\t" +
                    "A continuación aparecerán botones con los géneros literarios introducidos (que " +
                    "funciona igual que las categorías de los productos).\n\t" +
                    "Los listados de libros, igual que los de productos, funcionan igual. La única " +
                    "diferencia es que estos no se podrán añadir a la lista de la compra, por lo que " +
                    "esa opción no estará disponible.\n\n" +
            "5. GESTIÓN DE JUEGOS:\n\t" +
                    "Funciona exactamente igual que la gestión de libros.\n\n" +
            "6. GESTIÓN MULTIMEDIA:\n\t" +
                    "Aparecen dos opciones (películas y discos de música). Al pulsar " +
                    "sobre cualquiera de las dos aparecerá un botón en la parte superior para poder " +
                    "agregar un nuevo recurso multimedia (ya sea una película o un disco).\n\t" +
                    "A continuación aparecerá el listado con los recursos multimedia agregados y cada " +
                    "uno de ellos con dos botones asociados (editar y borrar).\n\n" +
            "7. LISTA DE LA COMPRA:\n\t" +
                    "Se puede acceder a ella o bien desde la pantalla principal de la " +
                    "aplicación o bien desde el icono que aparece en la parte superior junto al menú " +
                    "desplegable.\n\t" +
                    "En esta lista solo se pueden incluir PRODUCTOS (ni libros, ni juegos " +
                    "ni películas ni discos de música).\n\t" +
                    "El número que se escriba en dicha lista se sumará al total que había, después de " +
                    "pulsar el producto (aparecerá marcado en amarillo) y luego el botón de 'Terminar compra'.\n\n" +
            "8. INFO EXTRA:\n\t" +
                    "En la gestión de productos, para ver la información completa del producto, " +
                    "hay que dejar pulsado sobre el mismo. En el resto (libros, juegos, películas y discos), " +
                    "solo con pulsar se acceder a dicha información.\n\n" +
            "9. ¡ATENCIÓN!\n\t" +
                    "Si se pulsa una vez sobre un producto, se descontará 1 de la cantidad introducida " +
                    "al crearlo.\n";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funcionamiento_app);

        tvTitle = findViewById(R.id.tvTitleFunc);
        tvMessage = findViewById(R.id.tvMessage);
        tvMessage.setText(textMessage);
    }
}
