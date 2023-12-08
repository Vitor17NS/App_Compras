package com.example.p2

import java.text.DecimalFormat
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.p2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idSalvar.setOnClickListener(this)
//        binding.idExibir.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        if (v.id == R.id.id_salvar) {
            botaoInclui() }
/*        else if (v.id == R.id.id_salvar) {
            botaoInclui()
        }*/
    }

    private fun botaoInclui() {
        val db = DBHelper(this, null)
        val name = binding.idDescricao.text.toString()
        val valor = binding.idUnitario.text.toString()
        val quantidade = binding.idQuantidade.text.toString()
        db.addProd(name, valor, quantidade)

        Toast.makeText(this, "$name adicioando no DB.", Toast.LENGTH_LONG).show()
        binding.idDescricao.text.clear()
        binding.idUnitario.text.clear()
        binding.idQuantidade.text.clear()

        botaoListar()
        db.deleteAllData()
    }

    @SuppressLint("Range")
    private fun botaoListar() {
        val db = DBHelper(this, null)
        val cursor = db.getProd()

        val decimalFormat = DecimalFormat("#,##0.00")

        cursor!!.moveToFirst()
        do {
            val valorString = cursor.getString(cursor.getColumnIndex(DBHelper.VALOR_PROD))
            val quantidadeString = cursor.getString(cursor.getColumnIndex(DBHelper.QTDE_PROD))

            val valorNumerico = valorString.toDoubleOrNull() ?: 0.0
            val quantidadeNumerica = quantidadeString.toDoubleOrNull() ?: 0.0

            val produto = valorNumerico * quantidadeNumerica

            // Formatar para exibir com duas casas decimais
            val valorFormatado = decimalFormat.format(valorNumerico)
            val produtoFormatado = decimalFormat.format(produto)

            binding.idTotal.append("R$ $valorFormatado\n")
            binding.idResultado.append("R$ $produtoFormatado\n")

        } while (cursor.moveToNext())
        cursor.close()
    }

}