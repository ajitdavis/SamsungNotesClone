package com.example.samsungnotesclone.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.samsungnotesclone.R
import com.example.samsungnotesclone.databinding.FragmentFirstBinding
import com.example.samsungnotesclone.model.DatabaseProvider
import com.example.samsungnotesclone.model.Note
import com.example.samsungnotesclone.model.NoteDao
import com.example.samsungnotesclone.model.NotesDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID


class NotesFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var notesDao: NoteDao
    private lateinit var notesDb: NotesDb

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        notesDb = DatabaseProvider.getInstance(requireContext())
        notesDao = notesDb.noteDao()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: String? = arguments?.getString("id")

        id?.let { id ->
            Log.d("TAG", "onViewCreated: id not null")
            CoroutineScope(Dispatchers.IO).launch {
                val note = notesDao.getNoteById(id)
                withContext(Dispatchers.Main) {
                    binding.notesTitle.setText(note?.title)
                    binding.notesContent.setText(note?.content)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val currentFragment =
                        parentFragmentManager.findFragmentById(R.id.fragmentContainer)
                    if (currentFragment is NotesFragment) {
                        findNavController().popBackStack()
                        requireActivity().findViewById<View>(R.id.fragmentContainer).apply {
                            if (visibility == View.VISIBLE) {
                                addToDb(id)
                                visibility = View.GONE
                            }
                        }
                    }

                }
            })
    }

    private fun addToDb(id: String?) {
        val title = binding.notesTitle.text.toString()
        val content = binding.notesContent.text.toString()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            if (id == null) {
                Log.d("TAG", "add to db: id is null")
                val uniqueID = UUID.randomUUID().toString()
                val note = Note(id = uniqueID, title = title, content = content)
                notesDao.insert(note)
            } else {
                val existingNote = notesDao.getNoteById(id)

                if (existingNote == null || existingNote.title != title || existingNote.content != content) {
                    Log.d("TAG", "add to db: id is not null && content is different")
                    val note = Note(id = id, title = title, content = content)
                    notesDao.insert(note)
                }
            }
        }
    }
}