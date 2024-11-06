package br.com.livrokotlin.listadecompras

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.livrokotlin.listadecompras.model.ItemModel
import br.com.livrokotlin.listadecompras.viewmodel.ItemsViewModel
import br.com.livrokotlin.listadecompras.viewmodel.ItemsViewModelFactory
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private val viewModel: ItemsViewModel by viewModels {
        ItemsViewModelFactory(applicationContext)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView);
        val itemsAdapter = ItemsAdapter()
        recyclerView.adapter=itemsAdapter

        val button = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editText)

        button.setOnClickListener {
            if (editText.text.isEmpty()) {
                editText.error = "Adicione um produto"
                return@setOnClickListener
            }
            val item = ItemModel(
                name = editText.text.toString(),
                onRemove = {
                    itemsAdapter.removeItem(it)
                }
            )
            itemsAdapter.addItem(item)
            editText.text.clear()
        }
    }
}