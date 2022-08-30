package alura.com.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import alura.com.ceep.R;
import alura.com.ceep.model.Nota;

import static alura.com.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static alura.com.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static alura.com.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private int posicaoRecebida = POSICAO_INVALIDA;
    private EditText titulo;
    private EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERA);
            Nota nota = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(nota);
        }
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (menuSalvaNota(item)) {
            Nota notaNova = criaNota();
            retornaNota(notaNova);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    @NotNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private boolean menuSalvaNota(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_ic_salva;
    }
}
