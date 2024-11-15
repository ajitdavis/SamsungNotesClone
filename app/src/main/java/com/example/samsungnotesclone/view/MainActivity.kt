package com.example.samsungnotesclone.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.samsungnotesclone.R
import com.example.samsungnotesclone.databinding.ActivityMainBinding
import com.example.samsungnotesclone.viewmodel.NotesViewmodel

class MainActivity : AppCompatActivity() {

    lateinit var notesAdapter: NotesAdapter
    private lateinit var binding: ActivityMainBinding
    private val notesViewModel: NotesViewmodel by viewModels()
    var isSelectionMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "All Notes"

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        val recyclerView = binding.recyclerView
        notesAdapter = NotesAdapter(onClick = { note ->
            launchNotesFragment(navController, note.id)
        }, selectionModeChanged = { isSelectionMode ->
            this.isSelectionMode = isSelectionMode
            invalidateOptionsMenu() // Refresh the menu to show/hide the select all option
            binding.bottomAppBar.isVisible = isSelectionMode
            binding.addButton.isVisible = !isSelectionMode
        })
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = notesAdapter
        }
        notesViewModel.notesList.observe(this) {
            it?.let {
                notesAdapter.submitList(it)
            }
        }

        binding.addButton.setOnClickListener { view ->
            launchNotesFragment(navController)
        }

        binding.deleteButton.setOnClickListener {
            deleteSelectedNotes()
        }
    }

    private fun deleteSelectedNotes() {
        val selectedNotes = notesAdapter.selectedNotes
        if (selectedNotes.isNotEmpty()) {
            notesViewModel.deleteNotes(selectedNotes)
            notesAdapter.exitSelectionMode()
        }
    }

    private fun launchNotesFragment(navController: NavController, id: String? = null) {
        val bundle = Bundle().apply {
            putString("id", id)
        }
        binding.fragmentContainer.visibility = View.VISIBLE
        navController.navigate(R.id.NotesFragment, bundle)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val selectAllItem = menu.findItem(R.id.action_select_all)
        selectAllItem.isVisible = isSelectionMode // Show checkbox only in selection mode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_select_all -> {
                notesAdapter.selectAll() // Implement this function in your adapter
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (notesAdapter.isSelectionMode) {
            notesAdapter.exitSelectionMode()
        } else {
            super.onBackPressed()
        }
    }

}