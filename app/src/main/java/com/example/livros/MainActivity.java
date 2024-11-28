package com.example.livros;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "MainActivity";
    private List<Book> bookList;
    private BookAdapter bookAdapter;
    private TextView emptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            bookList = new ArrayList<>();
            bookAdapter = new BookAdapter(bookList, this::showEditBookDialog, this::deleteBook);

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(bookAdapter);

            emptyMessage = findViewById(R.id.emptyMessage);
            checkEmptyList();

            @SuppressLint("WrongViewCast") FloatingActionButton btnAddBook = findViewById(R.id.btnAddBook);
            if (btnAddBook == null) {
                Log.e(TAG, "btnAddBook não encontrado no layout.");
                return;
            }

            btnAddBook.setOnClickListener(view -> showAddBookDialog());
        } catch (Exception e) {
            Log.e(TAG, "Erro na inicialização: ", e);
            Toast.makeText(this, "Erro ao iniciar o app", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddBookDialog() {
        showBookDialog(null, -1);
    }

    private void showEditBookDialog(Book book, int position) {
        showBookDialog(book, position);

    }

    private void showBookDialog(Book book, int position) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_book, null);

        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtAuthor = dialogView.findViewById(R.id.edtAuthor);
        EditText edtGenre = dialogView.findViewById(R.id.edtGenre);
        EditText edtYear = dialogView.findViewById(R.id.edtYear);
        EditText edtNotes = dialogView.findViewById(R.id.edtNotes);

        if (book != null) {
            edtTitle.setText(book.getTitle());
            edtAuthor.setText(book.getAuthor());
            edtGenre.setText(book.getGenre());
            edtYear.setText(String.valueOf(book.getYear()));
            edtNotes.setText(book.getNotes());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle(book == null ? "Adicionar Livro" : "Editar Livro");
        builder.setPositiveButton(book == null ? "Salvar" : "Atualizar", (dialog, which) -> {
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
                if (book == null) {
                    bookList.add(new Book(title, author, genre, year, notes));
                } else {
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setYear(year);
                    book.setNotes(notes);
                    bookList.set(position, book);
                }

                bookAdapter.notifyDataSetChanged();
                checkEmptyList();
                Toast.makeText(this, book == null ? "Livro adicionado!" : "Livro atualizado!", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Livro excluído!", Toast.LENGTH_SHORT).show();
    }

    private void checkEmptyList() {
        if (bookList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
        } else {
            emptyMessage.setVisibility(View.GONE);
        }
    }

}
