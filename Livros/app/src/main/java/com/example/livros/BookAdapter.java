package com.example.livros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final List<Book> bookList;
    private final Context context;
    private final OnBookActionListener listener;

    public BookAdapter(List<Book> bookList, Context context, OnBookActionListener listener) {
        this.bookList = bookList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.txtBookInfo.setText(context.getString(
                R.string.book_info_format,
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getYear()
        ));

        holder.btnEdit.setOnClickListener(v -> listener.onEditBook(position));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteBook(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView txtBookInfo;
        ImageButton btnEdit, btnDelete;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBookInfo = itemView.findViewById(R.id.txtBookInfo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnBookActionListener {
        void onEditBook(int position);
        void onDeleteBook(int position);
    }
}
