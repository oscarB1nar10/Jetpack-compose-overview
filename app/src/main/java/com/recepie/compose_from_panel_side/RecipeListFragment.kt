package com.recepie.compose_from_panel_side

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class RecipeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recipe_lis, container, false)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            Text("This is a compose view inside a layout")
            Spacer(modifier = Modifier.padding(10.dp))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Neat")
            Spacer(modifier = Modifier.padding(10.dp))
        }
        return view
    }

}