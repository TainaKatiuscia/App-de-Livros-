package com.example.livros;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Book> bookList;
    private BookAdapter bookAdapter;
    private TextView emptyMessage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList, this, new BookAdapter.OnBookActionListener() {
            @Override
            public void onEditBook(int position) {
                showEditBookDialog(position);
            }

            @Override
            public void onDeleteBook(int position) {
                deleteBook(position);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        emptyMessage = findViewById(R.id.emptyMessage);
        checkEmptyList();

        FloatingActionButton btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(view -> showAddBookDialog());
    }

    private void showAddBookDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_book, null);

        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtAuthor = dialogView.findViewById(R.id.edtAuthor);
        EditText edtGenre = dialogView.findViewById(R.id.edtGenre);
        EditText edtYear = dialogView.findViewById(R.id.edtYear);
        EditText edtNotes = dialogView.findViewById(R.id.edtNotes);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Adicionar Livro");
        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String title = edtTitle.getText().toString().trim();
            String author = edtAuthor.getText().toString().trim();
            String genre = edtGenre.getText().toString().trim();
            String yearStr = edtYear.getText().toString().trim();
            String notes = edtNotes.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || yearStr.isEmpty()) {
                Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int year = Integer.parseInt(yearStr);
                bookList.add(new Book(title, author, genre, year, notes));
                bookAdapter.notifyItemInserted(bookList.size() - 1);
                checkEmptyList();
                Toast.makeText(this, "Livro adicionado com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "O ano deve ser um número válido!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void showEditBookDialog(int position) {
        Book book = bookList.get(position);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_book, null);

        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtAuthor = dialogView.findViewById(R.id.edtAuthor);
        EditText edtGenre = dialogView.findViewById(R.id.edtGenre);
        EditText edtYear = dialogView.findViewById(R.id.edtYear);
        EditText edtNotes = dialogView.findViewById(R.id.edtNotes);

        // Preencher campos com dados existentes
        edtTitle.setText(book.getTitle());
        edtAuthor.setText(book.getAuthor());
        edtGenre.setText(book.getGenre());
        edtYear.setText(String.valueOf(book.getYear()));
        edtNotes.setText(book.getNotes());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Editar Livro");
        builder.setPositiveButton("Salvar", (dialog, which) -> {
            String title = edtTitle.getText().toString().trim();
            String author = edtAuthor.getText().toString().trim();
            String genre = edtGenre.getText().toString().trim();
            String yearStr = edtYear.getText().toString().trim();
            String notes = edtNotes.getText().toString().trim();

            if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || yearStr.isEmpty()) {
                Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int year = Integer.parseInt(yearStr);
                bookList.set(position, new Book(title, author, genre, year, notes));
                bookAdapter.notifyItemChanged(position);
                checkEmptyList();
                Toast.makeText(this, "Livro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "O ano deve ser um número válido!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void deleteBook(int position) {
        bookList.remove(position);
        bookAdapter.notifyItemRemoved(position);
        checkEmptyList();
        Toast.makeText(this, "Livro removido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private void checkEmptyList() {
        if (bookList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
        } else {
            emptyMessage.setVisibility(View.GONE);
        }
    }
}
