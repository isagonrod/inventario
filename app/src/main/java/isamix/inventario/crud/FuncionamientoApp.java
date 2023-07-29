package isamix.inventario.crud;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import isamix.inventario.R;

public class FuncionamientoApp extends AppCompatActivity {
    TextView tvTitle, tvMessage;
    String textMessage =
            "1. Elige qué lista quieres ver y/o gestionar. Verás una ventana con las categorías existentes, y si se pulsa dentro de cada una, se verá una lista específica de la misma.\n\n" +
            "2. Dentro de la gestión de productos puedes crear nuevos productos, nuevas categorías o ver la lista completa de productos.\n\n" +
            "3. En la vista de las listas, cada item tiene varias opciones:\n\n" +
                    "\t\t 3.1. Si se deja pulsado, se abre una nueva ventana con los detalles del mismo.\n\n" +
                    "\t\t 3.2. Si se pulsa una vez, se restará 1 de la cantidad total de las existencias (en cada se ser un producto).\n\n" +
                    "\t\t 3.3. Si se pulsa el botón del carrito, se añadirá a la lista de la compra.\n\n" +
                    "\t\t 3.4. Si se pulsa el botón del lápiz, se abrirá una nueva ventana para poder editar los datos del producto.\n\n" +
                    "\t\t 3.5. Si se pulsa el botón de la papelera, se eliminará el producto.\n\n" +
            "4. Hay varios tipos de gestión diferentes: de productos (que se pueden incluir en la lista de la compra) por un lado, y de libros, juegos y multimedia (que tienen unos datos específicos por el tipo de productos que son y además no se pueden incluir en la lista de la compra).";

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
