package com.oceanx.myorders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import android.widget.ImageView

class PlaceholderFragment : Fragment(R.layout.fragment_placeholder) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val iconRes = requireArguments().getInt(ARG_ICON)
        val titleRes = requireArguments().getInt(ARG_TITLE)
        val messageRes = requireArguments().getInt(ARG_MESSAGE)

        view.findViewById<ImageView>(R.id.placeholderIcon).setImageResource(iconRes)
        view.findViewById<MaterialTextView>(R.id.placeholderTitle).setText(titleRes)
        view.findViewById<MaterialTextView>(R.id.placeholderMessage).setText(messageRes)
    }

    companion object {
        private const val ARG_ICON = "arg_icon"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_MESSAGE = "arg_message"

        fun newInstance(iconRes: Int, titleRes: Int, messageRes: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ICON, iconRes)
                    putInt(ARG_TITLE, titleRes)
                    putInt(ARG_MESSAGE, messageRes)
                }
            }
        }
    }
}
