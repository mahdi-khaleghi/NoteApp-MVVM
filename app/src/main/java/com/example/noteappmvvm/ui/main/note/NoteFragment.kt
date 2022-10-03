package com.example.noteappmvvm.ui.main.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.noteappmvvm.data.model.NoteEntity
import com.example.noteappmvvm.databinding.FragmentNoteBinding
import com.example.noteappmvvm.utils.BUNDLE_ID
import com.example.noteappmvvm.utils.getIndexFromList
import com.example.noteappmvvm.utils.setupListWithAdapter
import com.example.noteappmvvm.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {
    //Binding
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var entity: NoteEntity

    //Other
    private val viewModel: NoteViewModel by viewModels()
    private var category = ""
    private var priority = ""
    private var noteId = 0
    private var isEdit = false
    private var categoriesList: MutableList<String> = mutableListOf()
    private var prioritiesList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Bundle
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        isEdit = noteId > 0
        //InitViews
        binding?.apply {
            //Close
            closeImg.setOnClickListener { dismiss() }
            //Spinners Category
            viewModel.loadCategoriesData()
            viewModel.categoriesList.observe(viewLifecycleOwner) {
                categoriesList.addAll(it)
                categoriesSpinner.setupListWithAdapter(it) { itItem -> category = itItem }
            }
            //Spinners Priority
            viewModel.loadPrioritiesData()
            viewModel.prioritiesList.observe(viewLifecycleOwner) {
                prioritiesList.addAll(it)
                prioritySpinner.setupListWithAdapter(it) { itItem -> priority = itItem }
            }
            //Note Data
            if (isEdit) {
                viewModel.getData(noteId)
                viewModel.noteData.observe(viewLifecycleOwner) { itData ->
                    itData.data?.let { data ->
                        titleEdt.setText(data.title)
                        descEdt.setText(data.desc)
                        categoriesSpinner.setSelection(
                            categoriesList.getIndexFromList(data.category),
                            true
                        )
                        prioritySpinner.setSelection(
                            prioritiesList.getIndexFromList(data.priority),
                            true
                        )
                    }
                }
            }
            //Click
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = noteId
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority

                if (title.isNotEmpty() && desc.isNotEmpty())
                    viewModel.saveEditNote(isEdit, entity)

                dismiss()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

}