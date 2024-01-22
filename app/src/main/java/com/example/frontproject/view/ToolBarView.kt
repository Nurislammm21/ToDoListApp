package com.example.frontproject.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.frontproject.R
import com.example.frontproject.databinding.EditTodolistToolbarViewBinding
import com.example.frontproject.extensions.setAttrs

class ToolBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = EditTodolistToolbarViewBinding.inflate(LayoutInflater.from(context),this)
    var saveClick: (() -> Unit)? = null
    var deleteClick: (() -> Unit)? = null

    init{
        setAttrs(attrs, R.styleable.ToolBarView){
            with(binding){
                txtTitle.text = it.getString(R.styleable.ToolBarView_toolbar_title)
                backBtn.isVisible = it.getBoolean(R.styleable.ToolBarView_toolbar_back_btn_visibility,false)

                backBtn.setOnClickListener{
                    findNavController().popBackStack()
                }
            }

        }
        binding.saveBtn.setOnClickListener{
            saveClick?.invoke()
            findNavController().popBackStack()
            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()
        }
        binding.deleteBtn.setOnClickListener {
            deleteClick?.invoke()
            findNavController().popBackStack()
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
        }

    }

}