package com.example.livros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private OnEditListener onEdit;
    private OnDeleteListener onDelete;
    private Context context;
    private int position;

    public interface OnEditListener {
        void onEdit(Book book, int position);
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }

    public BookAdapter(List<Book> bookList, OnEditListener onEdit, OnDeleteListener onDelete) {
        this.bookList = bookList;
        this.onEdit = onEdit;
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);

        holder.txtTitle.setText(book.getTitle());
        holder.txtAuthor.setText(book.getAuthor());
        holder.txtGenre.setText(book.getGenre());
        holder.txtYear.setText(String.valueOf(book.getYear()));
        holder.txtNotes.setText(book.getNotes());

        holder.itemView.setOnClickListener(v -> onEdit.onEdit(book, position));
        holder.itemView.setOnLongClickListener(v -> {
            onDelete.onDelete(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtAuthor, txtGenre, txtYear, txtNotes;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtNotes = itemView.findViewById(R.id.txtNotes);
        }
    }
}


