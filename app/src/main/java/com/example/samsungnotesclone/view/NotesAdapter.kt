package com.example.samsungnotesclone.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.samsungnotesclone.databinding.ItemNoteBinding
import com.example.samsungnotesclone.model.Note

class NotesAdapter(private val onClick: (Note) -> Unit, private val selectionModeChanged: (Boolean) -> Unit) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {

    var isSelectionMode = false
    val selectedNotes = mutableListOf<Note>()

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.noteItemTitle.text = note.title
            binding.noteItemContent.text = note.content
            binding.checkBox.visibility = if(isSelectionMode) View.VISIBLE else View.GONE
            binding.checkBox.isChecked = selectedNotes.contains(note)

            itemView.setOnClickListener {
                if(isSelectionMode) {
                    toggelCheckBox(note, adapterPosition)
                } else {
                    onClick(note)
                }

            }

            itemView.setOnLongClickListener {
                isSelectionMode = true
                selectionModeChanged(true)
                notifyDataSetChanged()
                true
            }

            binding.checkBox.setOnCheckedChangeListener(null)
            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    selectedNotes.add(note)
                } else {
                    selectedNotes.remove(note)
                }
            }
        }

        private fun toggelCheckBox(note: Note, bindingAdapterPosition: Int) {
            if(binding.checkBox.isChecked) {
                selectedNotes.remove(note)
            } else {
                selectedNotes.add(note)
            }
            notifyItemChanged(bindingAdapterPosition)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    fun exitSelectionMode() {
        selectionModeChanged(false)
        isSelectionMode = false
        selectedNotes.clear()
        notifyDataSetChanged()
    }

    fun selectAll() {
        if (!isSelectionMode) {
            isSelectionMode = true
            selectionModeChanged(true)
        }

        selectedNotes.clear()
        for (i in 0 until itemCount) {
            selectedNotes.add(getItem(i))
        }
        notifyDataSetChanged()
    }

}